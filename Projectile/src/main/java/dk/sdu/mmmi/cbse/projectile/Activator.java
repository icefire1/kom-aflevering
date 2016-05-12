package dk.sdu.mmmi.cbse.projectile;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        // TODO add activation code here
        // Instantiate projectile
        System.out.println("Done deal! projectile");
        context.registerService(IEntityProcessingService.class, new ProjectileProcessor(), null);
    }

    public void stop(BundleContext context) throws Exception {
        // TODO add deactivation code here
    }

}
