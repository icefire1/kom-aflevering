/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.playerinitialiser;

import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;

/**
 *
 * @author simon
 */
public class Initialiser {
    private ServiceReference reference;
    private IGamePluginService service;
    
    protected void activate(ComponentContext context)
    {
        System.out.println("Activate player from initialiser");
        if (reference != null)
        {
            service = (IGamePluginService) context.locateService("IPlayerService", reference);
        }
    }
    
    public void gotService(ServiceReference reference)
    {
        this.reference = reference;
        System.out.println("Got reference: " + reference);
    }
    
    public void lostService(ServiceReference reference)
    {
        this.reference = null;
        System.out.println("Lost reference: " + reference);
    }
}
