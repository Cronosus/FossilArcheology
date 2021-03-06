package mods.fossil.entity.mob;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.fossil.Fossil;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;

public class EntityFailuresaurus extends EntityZombie
{
    public EntityFailuresaurus(World var1)
    {
        super(var1);
        this.experienceValue = 4;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Fossil.biofossil.itemID;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jump() {}

    /**
     * Returns the texture's file path as a String.
     */
    public String getTexture()
    {
        return "fossil:textures/mob/Failuresaurus.png";
    }
}
