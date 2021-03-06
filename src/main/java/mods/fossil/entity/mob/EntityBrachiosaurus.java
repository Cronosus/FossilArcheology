package mods.fossil.entity.mob;

import mods.fossil.Fossil;
import mods.fossil.client.DinoSound;
import mods.fossil.fossilAI.DinoAIAttackOnCollide;
import mods.fossil.fossilAI.DinoAIEat;
import mods.fossil.fossilAI.DinoAIFollowOwner;
import mods.fossil.fossilAI.DinoAIRideGround;
import mods.fossil.fossilAI.DinoAIWander;
import mods.fossil.fossilEnums.EnumDinoType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityBrachiosaurus extends EntityDinosaur
{
    public boolean isTamed = false;

//    final float PUSHDOWN_HARDNESS = 5.0F;
    //final EntityAIControlledByPlayer aiControlledByPlayer;
    
    public static final double baseHealth = EnumDinoType.Brachiosaurus.Health0;
    public static final double baseDamage = EnumDinoType.Brachiosaurus.Strength0;
    public static final double baseSpeed = EnumDinoType.Brachiosaurus.Speed0;
    
    public static final double maxHealth = EnumDinoType.Brachiosaurus.HealthMax;
    public static final double maxDamage = EnumDinoType.Brachiosaurus.StrengthMax;
    public static final double maxSpeed = EnumDinoType.Brachiosaurus.SpeedMax;

    public EntityBrachiosaurus(World var1)
    {
        super(var1, EnumDinoType.Brachiosaurus);
        this.updateSize();
        /*
         * EDIT VARIABLES PER DINOSAUR TYPE
         */
        this.adultAge = EnumDinoType.Brachiosaurus.AdultAge;
        // Set initial size for hitbox. (length/width, height)
        this.setSize(1.5F, 2.0F);
        // Size of dinosaur at day 0.
        this.minSize = 1.0F;
        // Size of dinosaur at age Adult.
        this.maxSize = 5.8F;
        
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        //this.tasks.addTask(2, this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3F));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.0F));
        this.tasks.addTask(4, new DinoAIAttackOnCollide(this, 1.1D, true));
        this.tasks.addTask(5, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(6, new DinoAIWander(this, 1.0D));
        this.tasks.addTask(7, new DinoAIEat(this, 100));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        tasks.addTask(1, new DinoAIRideGround(this, 1)); // mutex all
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        //this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
    }

    /**
     * Return the AI task for player control.
     */
    /*
    public EntityAIControlledByPlayer getAIControlledByPlayer()
    {
        return this.aiControlledByPlayer;
    }
    */

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(EnumDinoType.Brachiosaurus.Speed0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(EnumDinoType.Brachiosaurus.Health0);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(EnumDinoType.Brachiosaurus.Strength0);
    }

    /**
     * Returns the texture's file path as a String.
     */
    @Override
    public String getTexture()
    {
        if (this.isModelized())
        {
            return super.getTexture();
        }

        switch (this.getSubSpecies())
        {
            default:
                return "fossil:textures/mob/Brachiosaurus.png";
        }
    }
    
    public int getVerticalFaceSpeed()
    {
        return this.isSitting() ? 70 : super.getVerticalFaceSpeed();
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer var1)
    {
        //Add special item interaction code here
        return super.interact(var1);
    }

    public EntityBrachiosaurus spawnBabyAnimal(EntityAnimal var1)
    {
        return new EntityBrachiosaurus(this.worldObj);
    }

    /**
     * Called to update the entity's position/logic.
     */
    /*
    public void onUpdate()
    {
        super.onUpdate();
        if ((this.isTeen() || this.isAdult()) && !this.isModelized() && Fossil.FossilOptions.Dino_Block_Breaking == true && this.riddenByEntity == null )//this.getDinoAge() >= 4)
        {
            this.BlockInteractive();
        }
    }
    */

    public float getEyeHeight()
    {
        return 2.0F + (float)this.getDinoAge() / 1.8F;
    }

    public float getHalfHeight()
    {
        return this.getEyeHeight() / 2.0F - 1.5F;
    }
    /*
        public int BlockInteractive()
        {
            for (int var5 = (int)Math.round(this.boundingBox.minX) - 1; var5 <= (int)Math.round(this.boundingBox.maxX) + 1; ++var5)
            {
                for (int var9 = 0; var9 <= (int)this.getHalfHeight(); ++var9)
                {
                    for (int var6 = (int)Math.round(this.boundingBox.minZ) - 1; var6 <= (int)Math.round(this.boundingBox.maxZ) + 1; ++var6)
                    {
                        int var10 = (int)Math.round(this.boundingBox.minY) + var9;
                        int var8 = this.worldObj.getBlockId(var5, var10, var6);

                        if (Block.blocksList[var8] != null)
                        {
                            float var10000 = Block.blocksList[var8].getBlockHardness(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ);

                            if (var10000 < 0.5F || (this.RiderSneak && (var10000<2.0F || var8 == Block.wood.blockID || var8 == Block.planks.blockID || var8 == Block.woodDoubleSlab.blockID || var8 == Block.woodSingleSlab.blockID)))
                            {
                                int var7 = this.GetObjectTall(var5, var10, var6);

                                if (var7 > 0 && !this.isObjectTooTall(var7 + var9))
                                {
                                    this.DestroyTower(var5, var10, var6, var7);
                                }
                            }
                        }
                    }
                }
            }
            return 0;
        }
    */
    private boolean isObjectTooTall(int var1, int var2, int var3)
    {
        return (float)this.GetObjectTall(var1, var2, var3) > this.getHalfHeight();
    }

    private boolean isObjectTooTall(int var1)
    {
        float var2 = this.getHalfHeight();
        return (float)var1 > var2;
    }

    private int GetObjectTall(int var1, int var2, int var3)
    {
        int var4;

        for (var4 = 0; !this.worldObj.isAirBlock(var1, var2 + var4, var3); ++var4)
        {
            ;
        }

        return var4;
    }

    private void DestroyTower(int var1, int var2, int var3, int var4)
    {
        boolean var5 = false;

        for (int var6 = var2; var6 <= var2 + var4; ++var6)
        {
            int var7 = this.worldObj.getBlockId(var1, var6, var3);
            this.worldObj.playAuxSFX(2001, var1, var6, var3, var7);
            this.worldObj.setBlock(var1, var6, var3, 0);
        }
    }

    public float getMountHeight()
    {
        return this.height/2;
    }
    
    public void updateRiderPosition()
    {
        if (this.riddenByEntity != null)
        {
        	 this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountHeight() + this.riddenByEntity.getYOffset(), this.posZ);
        }
    }
    
    /**
     * This gets called when a dinosaur grows naturally or through Chicken Essence.
     */
    @Override
    public void updateSize()
    {
        double healthStep;
        double attackStep;
        double speedStep;
        healthStep = (this.maxHealth - this.baseHealth) / (this.adultAge + 1);
        attackStep = (this.maxDamage - this.baseDamage) / (this.adultAge + 1);
        speedStep = (this.maxSpeed - this.baseSpeed) / (this.adultAge + 1);
        
        
    	if(this.getDinoAge() <= this.adultAge){
    		
	        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(Math.round(this.baseHealth + (healthStep * this.getDinoAge())));
	        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(Math.round(this.baseDamage + (attackStep * this.getDinoAge())));
	        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(this.baseSpeed + (speedStep * this.getDinoAge()));
	        
	        if (this.isTeen()) {
	        	this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setAttribute(0.5D);
	        }
	        else if (this.isAdult()){
	            this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setAttribute(2.0D);
	        }
	        else {
	            this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setAttribute(0.0D);
	        }
    	}
    }

}
