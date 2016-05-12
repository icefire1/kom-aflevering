/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.obstacle;

import dk.sdu.mmmi.cbse.common.data.Entity;
import static dk.sdu.mmmi.cbse.common.data.EntityType.ASTEROIDS;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author simon
 */
@ServiceProvider (service = IGamePluginService.class)
public class ObstaclePlugin implements IGamePluginService {

    private Map<String, Entity> world;
    private Entity obstacle;
    
    @Override
    public void start(GameData gameData, Map<String, Entity> world) {
        System.out.println("Start obstacle");
        this.world = world;
        // Add entities to the world
        obstacle = createObstacle(gameData);
        world.put(obstacle.getID(), obstacle);
    }

    @Override
    public void stop(GameData gameData) {
        // Remove entities
        world.remove(obstacle.getID());
    }

    private Entity createObstacle(GameData gameData) {
        Entity entity = new Entity();
        entity.setPosition(gameData.getDisplayWidth() / 2 + 50, gameData.getDisplayHeight() / 2 + 50);
        
        entity.setShapeX(new float[4]);
        entity.setShapeY(new float[4]);
        entity.setRadius(4);
        entity.setType(ASTEROIDS);
        
        return entity;
    }

    
}
