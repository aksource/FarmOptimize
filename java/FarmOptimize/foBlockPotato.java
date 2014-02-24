package FarmOptimize;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPotato;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class foBlockPotato extends BlockPotato
{
    public foBlockPotato()
    {
        super();
    }
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.checkAndDropBlock(par1World, par2, par3, par4);

        if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
        {
            int var6 = par1World.getBlockMetadata(par2, par3, par4);

            if (var6 < 7)
            {
                float var7 = this.getGrowthRate(par1World, par2, par3, par4);
				int speed = FarmOptimize.growSpeedPotato;
//		        switch (blockID)
//		        {
//		            case 59:
//		                 speed = FarmOptimize.growSpeedCrops; break;
//		            case 141:
//		                 speed = FarmOptimize.growSpeedCarrot; break;
//		            case 142:
//		                 speed; break;
//		            default:
//		                 speed = 100;
//		        }
                if (speed == 0 || par5Random.nextInt((int)(25.0F / var7) + 1) == 0)
                {
                    ++var6;
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var6,3);
                }
            }
        }
    }
    private float getGrowthRate(World par1World, int par2, int par3, int par4)
    {
        float var5 = 1.0F;
        Block var6 = par1World.getBlock(par2, par3, par4 - 1);
        Block var7 = par1World.getBlock(par2, par3, par4 + 1);
        Block var8 = par1World.getBlock(par2 - 1, par3, par4);
        Block var9 = par1World.getBlock(par2 + 1, par3, par4);
        Block var10 = par1World.getBlock(par2 - 1, par3, par4 - 1);
        Block var11 = par1World.getBlock(par2 + 1, par3, par4 - 1);
        Block var12 = par1World.getBlock(par2 + 1, par3, par4 + 1);
        Block var13 = par1World.getBlock(par2 - 1, par3, par4 + 1);
        boolean var14 = var8 == this || var9 == this;
        boolean var15 = var6 == this || var7 == this;
        boolean var16 = var10 == this || var11 == this || var12 == this || var13 == this;

        for (int var17 = par2 - 1; var17 <= par2 + 1; ++var17)
        {
            for (int var18 = par4 - 1; var18 <= par4 + 1; ++var18)
            {
                Block var19 = par1World.getBlock(var17, par3 - 1, var18);
                float var20 = 0.0F;

                if (var19 != null && var19.canSustainPlant(par1World, var17, par3 - 1, var18, ForgeDirection.UP, this))
                {
                    var20 = 1.0F;

                    if (var19.isFertile(par1World, var17, par3 - 1, var18))
                    {
                        var20 = 3.0F;
                    }
                }

                if (var17 != par2 || var18 != par4)
                {
                    var20 /= 4.0F;
                }

                var5 += var20;
            }
        }

        if (var16 || var14 && var15)
        {
            var5 /= 2.0F;
        }

        return var5;
    }
}
