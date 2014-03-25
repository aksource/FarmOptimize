package FarmOptimize.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by A.K. on 14/03/15.
 */
@IFMLLoadingPlugin.MCVersion("1.7.2")
public class FarmOptimizeCorePlugin implements IFMLLoadingPlugin {

    public static int SugarcaneSpeed;
    public static int SugarcaneLimit;
    public static boolean SugarcaneGrowWater;
    public static int CactusSpeed;
    public static int CactusLimit;
    public static int growSpeedPumpkin;
    public static int growSpeedWaterMelon;
    public static int growSpeedCrops;
    public static int growSpeedCarrot;
    public static int growSpeedPotato;
    public static int growSpeedSapling;
    public static int MushroomSpeed;
    public static int MushroomLimit;
    public static byte MushroomArea;
    public static int growSpeedNetherWart;
    public static int growSpeedCocoa;
    public static int growSpeedVine;
    public static Logger logger = Logger.getLogger("FarmOptimize");

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                "FarmOptimize.asm.BlockCactusTransformer",
                "FarmOptimize.asm.BlockCocoaTransformer",
                "FarmOptimize.asm.BlockCropsTransformer",
                "FarmOptimize.asm.BlockMushroomTransformer",
                "FarmOptimize.asm.BlockNetherWartTransformer",
                "FarmOptimize.asm.BlockReedTransformer",
                "FarmOptimize.asm.BlockSaplingTransformer",
                "FarmOptimize.asm.BlockStemTransformer",
                "FarmOptimize.asm.BlockVineTransformer"
        };
    }

    @Override
    public String getModContainerClass() {
        return "FarmOptimize.asm.FarmOptimizeCoreModContainer";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        if (data.containsKey("mcLocation"))
        {
            File mcLocation = (File) data.get("mcLocation");
            File configLocation = new File(mcLocation, "config");
            File configFile = new File(configLocation, "FarmOptimizeCore.cfg");
            initConfig(configFile);
        }
    }

    private void initConfig(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();
        SugarcaneSpeed = config.get(Configuration.CATEGORY_GENERAL, "SugarcaneSpeed", 15, "0:noWait  15:default, min = 0, max = 15").getInt();
        SugarcaneSpeed = (SugarcaneSpeed < 0) ? 0: (SugarcaneSpeed > 15)? 15:SugarcaneSpeed;
        SugarcaneLimit = config.get(Configuration.CATEGORY_GENERAL, "SugarcaneLimit", 3, "min = 1, max = 250").getInt();
        SugarcaneLimit = (SugarcaneLimit < 1) ? 1: (SugarcaneLimit > 250)?250:SugarcaneLimit;
        SugarcaneGrowWater = config.get(Configuration.CATEGORY_GENERAL, "SugarcaneGrowWater", false).getBoolean(false);
        CactusSpeed = config.get(Configuration.CATEGORY_GENERAL, "CactusSpeed", 15, "0:noWait  15:default, min = 0, max = 15").getInt();
        CactusSpeed = (CactusSpeed <0)?0:(CactusSpeed>15)?15:CactusSpeed;
        CactusLimit = config.get(Configuration.CATEGORY_GENERAL, "CactusLimit", 3, "min = 1, max = 250").getInt();
        CactusLimit = (CactusLimit < 1) ? 1: (CactusLimit > 250)?250:CactusLimit;
        growSpeedPumpkin = config.get(Configuration.CATEGORY_GENERAL, "growSpeedPumpkin", 100, "0:noWait  1:fast  100:default, min = 0, max = 100").getInt();
        growSpeedPumpkin = (growSpeedPumpkin <0)?0:(growSpeedPumpkin >100)?100: growSpeedPumpkin;
        growSpeedWaterMelon = config.get(Configuration.CATEGORY_GENERAL, "growSpeedWaterMelon", 100, "0:noWait  1:fast  100:default, min = 0, max = 100").getInt();
        growSpeedWaterMelon = (growSpeedWaterMelon <0)?0:(growSpeedWaterMelon>100)?100:growSpeedWaterMelon;
        growSpeedCrops = config.get(Configuration.CATEGORY_GENERAL, "growSpeedCrops", 100, "0:noWait  1:fast  100:default, min = 0, max = 100").getInt();
        growSpeedCrops = (growSpeedCrops <0)?0:(growSpeedCrops>100)?100:growSpeedCrops;
        growSpeedCarrot = config.get(Configuration.CATEGORY_GENERAL, "growSpeedCarrot", 100, "0:noWait  1:fast  100:default, min = 0, max = 100").getInt();
        growSpeedCarrot = (growSpeedCarrot <0)?0:(growSpeedCarrot>100)?100:growSpeedCarrot;
        growSpeedPotato = config.get(Configuration.CATEGORY_GENERAL, "growSpeedPotato", 100, "0:noWait  1:fast  100:default, min = 0, max = 100").getInt();
        growSpeedPotato = (growSpeedPotato <0)?0:(growSpeedPotato>100)?100:growSpeedPotato;
        growSpeedSapling = config.get(Configuration.CATEGORY_GENERAL, "growSpeedSapling", 7, "0:noWait  1:fast  7:default, min = 0, max = 7").getInt();
        growSpeedSapling = (growSpeedSapling <0)?0:(growSpeedSapling>7)?7:growSpeedSapling;
        MushroomSpeed = config.get(Configuration.CATEGORY_GENERAL, "MushroomSpeed", 25, "0:noWait  1:fast  25:default, min = 0, max = 25").getInt();
        MushroomSpeed = (MushroomSpeed <0)?0:(MushroomSpeed>25)?25:MushroomSpeed;
        MushroomLimit = config.get(Configuration.CATEGORY_GENERAL, "MushroomLimit", 5, "area in mushroomLimit  5:default, min = 1, max = 81").getInt();
        MushroomLimit = (MushroomLimit <1)?1:(MushroomLimit>81)?81:MushroomLimit;
        MushroomArea = (byte)config.get(Configuration.CATEGORY_GENERAL, "MushroomArea", 4, "mushroom search area  4:default, min = 0, max = 4").getInt();
        MushroomArea = (MushroomArea <0)?0:(MushroomArea>4)?4:MushroomArea;
        growSpeedNetherWart = config.get(Configuration.CATEGORY_GENERAL, "growSpeedNetherWart", 10, "0:noWait  1:fast  10:default, min = 0, max = 10").getInt();
        growSpeedNetherWart = (growSpeedNetherWart <0)?0:(growSpeedNetherWart>10)?10:growSpeedNetherWart;
        growSpeedCocoa = config.get(Configuration.CATEGORY_GENERAL, "growSpeedCocoa", 5, "0:noWait  1:fast  5:default, min = 0, max = 5").getInt();
        growSpeedCocoa = (growSpeedCocoa <0)?0:(growSpeedCocoa>5)?5:growSpeedCocoa;
        growSpeedVine = config.get(Configuration.CATEGORY_GENERAL, "growSpeedVine", 4, "0:noWait  4:default  -1:noGrow, min = -1, max = 64").getInt();
        growSpeedVine = (growSpeedVine <-1)?-1:(growSpeedVine>64)?64:growSpeedVine;
        config.save();
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
