/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.playerimplementation;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Map;

/**
 *
 * @author simon
 */
    public class PlayerImplementation implements IGamePluginService {

    @Override
    public void start(GameData gameData, Map<String, Entity> world) {
        System.out.println("Start player osgi declerative");
    }

    @Override
    public void stop(GameData gameData) {
    }
    
}
