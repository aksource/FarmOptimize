package FarmOptimize.asm;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

import java.util.Arrays;

/**
 * Created by A.K. on 14/03/15.
 */
public class FarmOptimizeCoreModContainer extends DummyModContainer{
    public FarmOptimizeCoreModContainer() {
        super(new ModMetadata());

        ModMetadata meta = getMetadata();
        meta.modId = "FarmOptimizeCore";
        meta.name = "FarmOptimizeCore";
        meta.version = "1.0.0";
        meta.authorList = Arrays.asList("takanasayo", "A.K.");
        meta.description = "Optimize Growable Block ";
        meta.url = "";
        meta.credits = "";
        this.setEnabledState(true);
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }
}
