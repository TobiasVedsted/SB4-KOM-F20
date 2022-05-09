package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {

        PlayerPlugin playerPlugin = new PlayerPlugin();
        bundleContext.registerService(IGamePluginService.class, playerPlugin, null);

        PlayerControlSystem playerControlSystem = new PlayerControlSystem();
        bundleContext.registerService(IEntityProcessingService.class, playerControlSystem, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }

}

