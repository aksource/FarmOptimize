package FarmOptimize;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class foBlockStem extends BlockStem
{
    private Block fruitType;
    public foBlockStem(Block par2Block)
    {
        super(par2Block);
        this.fruitType = par2Block;
    }
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);

        if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
        {
            float var6 = this.getGrowthModifier(par1World, par2, par3, par4);

			int speed = (this == Blocks.pumpkin_stem ? FarmOptimize.growSpeedPunpkin :
				this == Blocks.melon_stem ? FarmOptimize.growSpeedWaterMelon : 100);
            if (speed == 0 || par5Random.nextInt((int)(0.25 / var6 * speed) + 1) == 0)
            {
                int var7 = par1World.getBlockMetadata(par2, par3, par4);

                if (var7 < 7)
                {
                    ++var7;
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var7,3);
                }
                else
                {
                    if (par1World.getBlock(par2 - 1, par3, par4) == this.fruitType)
                    {
                        return;
                    }

                    if (par1World.getBlock(par2 + 1, par3, par4) == this.fruitType)
                    {
                        return;
                    }

                    if (par1World.getBlock(par2, par3, par4 - 1) == this.fruitType)
                    {
                        return;
                    }

                    if (par1World.getBlock(par2, par3, par4 + 1) == this.fruitType)
                    {
                        return;
                    }

                    int var8 = par5Random.nextInt(4);
                    int var9 = par2;
                    int var10 = par4;

                    if (var8 == 0)
                    {
                        var9 = par2 - 1;
                    }

                    if (var8 == 1)
                    {
                        ++var9;
                    }

                    if (var8 == 2)
                    {
                        var10 = par4 - 1;
                    }

                    if (var8 == 3)
                    {
                        ++var10;
                    }

                    Block var11 = par1World.getBlock(var9, par3 - 1, var10);

                    boolean isSoil = (var11 != null && var11.canSustainPlant(par1World, var9, par3 - 1, var10, ForgeDirection.UP, this));
                    if (par1World.getBlock(var9, par3, var10) == Blocks.air && (isSoil || var11 == Blocks.dirt || var11 == Blocks.grass))
                    {
                        par1World.setBlock(var9, par3, var10, this.fruitType,0,3);
                    }
                }
            }
        }
    }

    private float getGrowthModifier(World par1World, int par2, int par3, int par4)
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
