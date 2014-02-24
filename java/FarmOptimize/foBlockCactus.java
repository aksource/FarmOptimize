package FarmOptimize;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.BlockCactus;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class foBlockCactus extends BlockCactus
{
    public foBlockCactus()
    {
        super();
    }
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.isAirBlock(par2, par3 + 1, par4))
        {
            int var6;

            for (var6 = 1; par1World.getBlock(par2, par3 - var6, par4) == this; ++var6)
            {
                ;
            }

            if (var6 < FarmOptimize.CactusLimit)
            {
                int var7 = par1World.getBlockMetadata(par2, par3, par4);

                if (var7 >= FarmOptimize.CactusSpeed)
                {
                    par1World.setBlock(par2, par3 + 1, par4, this, 0, 3);
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, 0,3);
                }
                else
                {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 + 1,3);
                }
            }
        }
    }

	@SubscribeEvent
	public void useBonemeal(BonemealEvent event)
	{
		if(!event.world.isRemote && event.block == this && canBlockStay(event.world, event.x, event.y, event.z))
		{
            int size;
            for (size = event.y; canBlockStay(event.world, event.x, size, event.z); --size)
            {
                ;
            }
            int min = size + 1;
			int top = 0;
            for (size = min; (size - min + 1) < FarmOptimize.CactusUsedBonemealLimit; size++)
            {
                if(event.world.isAirBlock(event.x, size + 1, event.z))
				{
                    event.world.setBlock(event.x, size + 1, event.z, this,0,3);
					top++;
				}
            }
			if(top > 0)
			{
				event.setResult(Event.Result.ALLOW);
			}
		}
	}

}
