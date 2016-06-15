package FarmOptimize;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid=FarmOptimize.MOD_ID,
        name=FarmOptimize.MOD_NAME,
        version=FarmOptimize.MOD_VERSION,
        dependencies=FarmOptimize.MOD_DEPENDENCIES,
        useMetadata = true,
        acceptedMinecraftVersions = FarmOptimize.MOD_MC_VERSION)
public class FarmOptimize {

    public static final String MOD_ID = "FarmOptimize";
    public static final String MOD_NAME = "FarmOptimize";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "required-after:Forge@[11.14.0.1237,)";
    public static final String MOD_MC_VERSION = "[1.8,1.8.9]";
//	public static int SugarcaneSpeed;
//	public static int SugarcaneLimit;
	public static boolean SugarcaneGrowWater;
	public static boolean SugarcaneUsefulBonemeal;
	public static int SugarcaneUsedBonemealLimit;
//	public static int CactusSpeed;
//	public static int CactusLimit;
	public static boolean CactusUsefulBonemeal;
	public static int CactusUsedBonemealLimit;
//	public static int growSpeedPunpkin;
//	public static int growSpeedWaterMelon;
//	public static int growSpeedCrops;
//	public static int growSpeedCarrot;
//	public static int growSpeedPotato;
//	public static int growSpeedSapling;
//	public static int MushroomSpeed;
//	public static int MushroomLimit;
//	public static byte MushroomArea;
//	public static int growSpeedNetherWart;
//	public static int growSpeedCocoa;
//	public static int growSpeedVine;
	
//	public static Block reed;
//	public static Block cactus;
//	public static Block pumpkinStem;
//	public static Block melonStem;
//	public static Block crops;
//	public static Block carrot;
//	public static Block potato;
//	public static Block sapling;
//	public static Block mushroomBrown;
//	public static Block mushroomRed;
//	public static Block netherStalk;
//	public static Block cocoaPlant;
//	public static Block vine;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
//		SugarcaneSpeed = config.get(Configuration.CATEGORY_GENERAL, "SugarcaneSpeed", 15, "0:noWait  15:default, min = 0, max = 15").getInt();
//		SugarcaneSpeed = (SugarcaneSpeed < 0) ? 0: (SugarcaneSpeed > 15)? 15:SugarcaneSpeed;
//		SugarcaneLimit = config.get(Configuration.CATEGORY_GENERAL, "SugarcaneLimit", 3, "min = 1, max = 250").getInt();
//		SugarcaneLimit = (SugarcaneLimit < 1) ? 1: (SugarcaneLimit > 250)?250:SugarcaneLimit;
		SugarcaneGrowWater = config.get(Configuration.CATEGORY_GENERAL, "SugarcaneGrowWater", false).getBoolean(false);
		SugarcaneUsefulBonemeal = config.get(Configuration.CATEGORY_GENERAL, "SugarcaneUsefulBonemeal", false).getBoolean(false);
		SugarcaneUsedBonemealLimit = config.get(Configuration.CATEGORY_GENERAL, "SugarcaneUsedBonemealLimit", 3, "min = 1, max = 250").getInt();
		SugarcaneUsedBonemealLimit = (SugarcaneUsedBonemealLimit <1)?1:(SugarcaneUsedBonemealLimit >250)?250:SugarcaneUsedBonemealLimit;
//		CactusSpeed = config.get(Configuration.CATEGORY_GENERAL, "CactusSpeed", 15, "0:noWait  15:default, min = 0, max = 15").getInt();
//		CactusSpeed = (CactusSpeed <0)?0:(CactusSpeed>15)?15:CactusSpeed;
//		CactusLimit = config.get(Configuration.CATEGORY_GENERAL, "CactusLimit", 3, "min = 1, max = 250").getInt();
//		CactusLimit = (CactusLimit < 1) ? 1: (CactusLimit > 250)?250:CactusLimit;
		CactusUsefulBonemeal = config.get(Configuration.CATEGORY_GENERAL, "CactusUsefulBonemeal", false).getBoolean(false);
		CactusUsedBonemealLimit = config.get(Configuration.CATEGORY_GENERAL, "CactusUsedBonemealLimit", 3, "min = 1, max = 250").getInt();
		CactusUsedBonemealLimit = (CactusUsedBonemealLimit < 1) ? 1: (CactusUsedBonemealLimit > 250)?250:CactusUsedBonemealLimit;
//		growSpeedPunpkin = config.get(Configuration.CATEGORY_GENERAL, "growSpeedPumpkin", 100, "0:noWait  1:fast  100:default, min = 0, max = 100").getInt();
//		growSpeedPunpkin = (growSpeedPunpkin <0)?0:(growSpeedPunpkin>100)?100:growSpeedPunpkin;
//		growSpeedWaterMelon = config.get(Configuration.CATEGORY_GENERAL, "growSpeedWaterMelon", 100, "0:noWait  1:fast  100:default, min = 0, max = 100").getInt();
//		growSpeedWaterMelon = (growSpeedWaterMelon <0)?0:(growSpeedWaterMelon>100)?100:growSpeedWaterMelon;
//		growSpeedCrops = config.get(Configuration.CATEGORY_GENERAL, "growSpeedCrops", 100, "0:noWait  1:fast  100:default, min = 0, max = 100").getInt();
//		growSpeedCrops = (growSpeedCrops <0)?0:(growSpeedCrops>100)?100:growSpeedCrops;
//		growSpeedCarrot = config.get(Configuration.CATEGORY_GENERAL, "growSpeedCarrot", 100, "0:noWait  1:fast  100:default, min = 0, max = 100").getInt();
//		growSpeedCarrot = (growSpeedCarrot <0)?0:(growSpeedCarrot>100)?100:growSpeedCarrot;
//		growSpeedPotato = config.get(Configuration.CATEGORY_GENERAL, "growSpeedPotato", 100, "0:noWait  1:fast  100:default, min = 0, max = 100").getInt();
//		growSpeedPotato = (growSpeedPotato <0)?0:(growSpeedPotato>100)?100:growSpeedPotato;
//		growSpeedSapling = config.get(Configuration.CATEGORY_GENERAL, "growSpeedSapling", 7, "0:noWait  1:fast  100:default, min = 0, max = 7").getInt();
//		growSpeedSapling = (growSpeedSapling <0)?0:(growSpeedSapling>7)?7:growSpeedSapling;
//		MushroomSpeed = config.get(Configuration.CATEGORY_GENERAL, "MushroomSpeed", 25, "0:noWait  1:fast  100:default, min = 0, max = 25").getInt();
//		MushroomSpeed = (MushroomSpeed <0)?0:(MushroomSpeed>25)?25:MushroomSpeed;
//		MushroomLimit = config.get(Configuration.CATEGORY_GENERAL, "MushroomLimit", 5, "area in mushroomLimit  5:default, min = 1, max = 81").getInt();
//		MushroomLimit = (MushroomLimit <1)?1:(MushroomLimit>81)?81:MushroomLimit;
//		MushroomArea = (byte) config.get(Configuration.CATEGORY_GENERAL, "MushroomArea", 4, "mushroom search area  4:default, min = 0, max = 4").getInt();
//		MushroomArea = (MushroomArea <0)?0:(MushroomArea>4)?4:MushroomArea;
//		growSpeedNetherWart = config.get(Configuration.CATEGORY_GENERAL, "growSpeedNetherWart", 10, "0:noWait  1:fast  100:default, min = 0, max = 10").getInt();
//		growSpeedNetherWart = (growSpeedNetherWart <0)?0:(growSpeedNetherWart>10)?10:growSpeedNetherWart;
//		growSpeedCocoa = config.get(Configuration.CATEGORY_GENERAL, "growSpeedCocoa", 5, "0:noWait  1:fast  100:default, min = 0, max = 5").getInt();
//		growSpeedCocoa = (growSpeedCocoa <0)?0:(growSpeedCocoa>5)?5:growSpeedCocoa;
//		growSpeedVine = config.get(Configuration.CATEGORY_GENERAL, "growSpeedVine", 4, "0:noWait  4:default  -1:noGrow, min = -1, max = 64").getInt();
//		growSpeedVine = (growSpeedVine <-1)?-1:(growSpeedVine>64)?64:growSpeedVine;
		config.save();
//		if(SugarcaneSpeed != 15 || SugarcaneLimit != 3 || SugarcaneUsefulBonemeal || SugarcaneGrowWater)
//		{
//	    	reed = (new foBlockReed()).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockName("reeds").setBlockTextureName("reeds");
//			if(SugarcaneUsefulBonemeal) MinecraftForge.EVENT_BUS.register((foBlockReed)reed);
//            GameRegistry.registerBlock(reed, null, "reeds", "minecraft");
//		}
//
//		if(CactusSpeed != 15 || CactusLimit != 3 || CactusUsefulBonemeal)
//		{
//			cactus = (new BlockCactusCustom()).setHardness(0.4F).setStepSound(Block.soundTypeCloth).setUnlocalizedName("cactus");
//            GameRegistry.registerBlock(cactus, "cactus");
//			if(CactusUsefulBonemeal) MinecraftForge.EVENT_BUS.register((foBlockCactus)cactus);
//            try {
//                GameRegistry.addSubstitutionAlias("cactus", GameRegistry.Type.BLOCK, cactus);
//
//            } catch (ExistingSubstitutionException e) {
//                e.printStackTrace();
//            }
//        }
//
//		if(growSpeedPumpkin != 100)
//		{
//			pumpkinStem = (new foBlockStem(Blocks.pumpkin)).setHardness(0.0F).setStepSound(Block.soundTypeWood).setBlockName("pumpkinStem").setBlockTextureName("melon_stem");
//            GameRegistry.registerBlock(pumpkinStem, null, "pumpkin_stem", "minecraft");
//		}
//
//		if(growSpeedWaterMelon != 100)
//		{
//			melonStem = (new foBlockStem(Blocks.melon_block)).setHardness(0.0F).setStepSound(Block.soundTypeWood).setBlockName("pumpkinStem").setBlockTextureName("pumpkin_stem");
//            GameRegistry.registerBlock(melonStem, null, "melon_stem", "minecraft");
//		}
//
//		if(growSpeedCrops != 100)
//		{
//			crops = (new foBlockCrops()).setBlockName("crops").setBlockTextureName("wheat");
//            GameRegistry.registerBlock(crops, null, "wheat", "minecraft");
//		}
//
//		if(growSpeedCarrot != 100)
//		{
//			carrot = (new foBlockCarrot()).setBlockName("carrots").setBlockTextureName("carrots");
//            GameRegistry.registerBlock(carrot, null, "carrots", "minecraft");
//		}
//
//		if(growSpeedPotato != 100)
//		{
//			potato = (new foBlockPotato()).setBlockName("potatoes").setBlockTextureName("potatoes");
//            GameRegistry.registerBlock(potato, null, "potatoes", "minecraft");
//		}
//
//		if(growSpeedSapling != 7)
//		{
//			sapling = (new foBlockSapling()).setHardness(0.0F).setStepSound(Block.soundTypeGlass).setBlockName("sapling").setBlockTextureName("sapling");
//            GameRegistry.registerBlock(sapling, null, "sapling", "minecraft");
//		}
//
//		if(MushroomSpeed != 25 || MushroomLimit != 5 || MushroomArea != 4)
//		{
//			mushroomBrown = (new foBlockMushroom()).setHardness(0.2F).setStepSound(Block.soundTypeGrass).setLightLevel(0.125F).setBlockName("mushroom").setBlockTextureName("mushroom_brown");
//            mushroomRed =  (new foBlockMushroom()).setHardness(0.2F).setStepSound(Block.soundTypeGrass).setBlockName("mushroom").setBlockTextureName("mushroom_red");
//            GameRegistry.registerBlock(mushroomBrown, null, "brown_mushroom", "minecraft");
//            GameRegistry.registerBlock(mushroomRed, null, "red_mushroom", "minecraft");
//		}
//
//		if(growSpeedNetherWart != 10)
//		{
//			netherStalk = (new foBlockNetherStalk()).setBlockName("netherStalk").setBlockTextureName("nether_wart");
//            GameRegistry.registerBlock(netherStalk, null, "nether_wart","minecraft");
//		}
//
//		if(growSpeedCocoa != 5)
//		{
//			cocoaPlant = (new foBlockCocoa()).setHardness(0.2F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("cocoa").setBlockTextureName("cocoa");
//            GameRegistry.registerBlock(cocoaPlant, null, "cocoa", "minecraft");
//		}
//
//		if(growSpeedVine != 4)
//		{
//			vine = (new foBlockVine()).setHardness(0.2F).setStepSound(Block.soundTypeGrass).setBlockName("vine").setBlockTextureName("vine");
//            GameRegistry.registerBlock(vine, null, "vine", "minecraft");
//		}
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		if(SugarcaneUsefulBonemeal || SugarcaneGrowWater)
		{
			if(SugarcaneUsefulBonemeal) MinecraftForge.EVENT_BUS.register(this);
		}
		
		if(CactusUsefulBonemeal)
		{
			MinecraftForge.EVENT_BUS.register(this);
		}
	}

    @SubscribeEvent
    public void useBonemealToReeds(BonemealEvent event)
    {
        if(!event.world.isRemote && event.block == Blocks.reeds && Blocks.reeds.canBlockStay(event.world, event.pos))
        {
            int size;
            for (size = 0; Blocks.reeds.canBlockStay(event.world, event.pos.offset(EnumFacing.DOWN, size)); --size)
            {
                ;
            }
            int min = size + 1;
            int top = 0;
            for (size = min; (size - min + 1) < FarmOptimize.SugarcaneUsedBonemealLimit; size++)
            {
                if(isGrow(event.world, event.pos.offset(EnumFacing.UP, size)))
                {
                    event.world.setBlockState(event.pos.offset(EnumFacing.UP, size + 1), Blocks.reeds.getDefaultState(), 3);
                    top++;
                }
            }
            if(top > 0)
            {
                event.setResult(Event.Result.ALLOW);
            }
        }

        if(!event.world.isRemote && event.block == Blocks.cactus && Blocks.cactus.canBlockStay(event.world, event.pos))
        {
            int size;
            for (size = 0; Blocks.cactus.canBlockStay(event.world, event.pos.offset(EnumFacing.UP, size)); --size)
            {
                ;
            }
            int min = size + 1;
            int top = 0;
            for (size = min; (size - min + 1) < FarmOptimize.CactusUsedBonemealLimit; size++)
            {
                if(event.world.isAirBlock(event.pos.offset(EnumFacing.UP, size + 1)/*event.x, size + 1, event.z*/))
                {
                    event.world.setBlockState(event.pos.offset(EnumFacing.UP, size + 1), Blocks.cactus.getDefaultState(), 3);
                    top++;
                }
            }
            if(top > 0)
            {
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    private boolean isGrow(World par1World, BlockPos blockPos)
    {
        return par1World.isAirBlock(blockPos.offset(EnumFacing.UP)) || FarmOptimize.SugarcaneGrowWater
                && par1World.getBlockState(blockPos.offset(EnumFacing.UP)).getBlock().getMaterial().equals(Material.water);
    }
}