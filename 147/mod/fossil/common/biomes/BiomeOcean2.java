package mod.fossil.common.biomes;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenOcean;

public class BiomeOcean2 extends BiomeGenBase
{
    public BiomeOcean2(int par1)
    {
        super(par1);
        this.spawnableCreatureList.clear();
        this.minHeight = -0.3F;
        this.maxHeight = 0.2F;
    }
}