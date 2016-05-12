/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.obstacle;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import static dk.sdu.mmmi.cbse.common.data.EntityType.PLAYER;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Map;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author simon
 */
@ServiceProvider(service = IEntityProcessingService.class)
public class ObstacleProcessor implements IEntityProcessingService {

    static Random random = new Random();
    private float rotationSpeed = 15;
    private long timeStamp = 0;
    private long spawnDelay = 5000;
    public static final int SMALL = 4;
    public static final int MEDIUM = 8;
    public static final int LARGE = 12;

    @Override
    public void process(GameData gameData, Map<String, Entity> world, Entity entity) {
        if (System.currentTimeMillis() - timeStamp > spawnDelay) {
            float x = (float) (Math.random() * gameData.getDisplayWidth());
            float y = (float) (Math.random() * gameData.getDisplayWidth());
            float radians = (float) (Math.random() * 2 * 3.1415f);
            createObstacle(world, LARGE, x, y, radians);
            timeStamp = System.currentTimeMillis();
        }

        if (entity.getType() == EntityType.ASTEROIDS) {

            // Removes the bullet if certain conditions is met. TODO
            for (Entity e : world.values()) {
                if (e.getType() == EntityType.BULLET) {
                    if (entity.intersects(e)) {
                        int size = SMALL;
                        if (entity.getRadius() == LARGE) {
                            size = MEDIUM;
                        }
                        if (entity.getRadius() != SMALL) {
                            float x = entity.getX();
                            float y = entity.getY();
                            createObstacle(world, size, (float) (x + (Math.random() * entity.getRadius()*1.5)), (float) (y + (Math.random() * entity.getRadius()*1.5)), entity.getRadians());
                            createObstacle(world, size, (float) (x + (Math.random() * entity.getRadius()*1.5)), (float) (y + (Math.random() * entity.getRadius()*1.5)), entity.getRadians());
                        }
                        world.remove(entity.getID());
                        world.remove(e.getID());
                    }
                }
                
                if (e.getType() == EntityType.PLAYER) {
                    if (entity.intersects(e)) {
                        world.remove(e.getID());
                    }
                }
            }

            float x = entity.getX();
            float y = entity.getY();
            float dt = gameData.getDelta();
            float dx = entity.getDx();
            float dy = entity.getDy();

            //entity.setRadians(entity.getRadius() + (rotationSpeed * dt));
            x += dx * dt;
            y += dy * dt;
            
            if(x > gameData.getDisplayWidth()){
                x = 0;
            }else if(x < 0){
                x = gameData.getDisplayWidth();
            }
          
            if(y > gameData.getDisplayHeight()){
                y = 0;
            }else if(y < 0){
                y = gameData.getDisplayHeight();
            }

            entity.setPosition(x, y);
            updateShape(entity);
        }
    }

    private void updateShape(Entity e) {
        float angle = 0;
        int numPoints = e.getShapeX().length;
        float radians = e.getRadians();
        float x = e.getX();
        float y = e.getY();
        float[] shapex = e.getShapeX();
        float[] shapey = e.getShapeY();
        float radius = e.getRadius();
        for (int i = 0; i < numPoints; i++) {
            shapex[i] = (float) (x + Math.cos(angle + radians) * radius);
            shapey[i] = (float) (y + Math.sin(angle + radians) * radius);
            angle += 2 * 3.1415f / numPoints;
        }
    }

    private void createObstacle(Map<String, Entity> world, int size, float x, float y, float radians) {
        Entity obstacle = new Entity();
        obstacle.setType(EntityType.ASTEROIDS);
        obstacle.setRadius(size);
        
        int numPoints = 0;
        if(size == SMALL) {
            numPoints = 8;
        }
        else if(size == MEDIUM) {
            numPoints = 10;
        }
        else if(size == LARGE) {
            numPoints = 12;
        }

        obstacle.setX(x);
        obstacle.setY(y);
        float speed = 80f;
        speed += (((float) LARGE - (float) size) / (float)LARGE);
        obstacle.setMaxSpeed(speed);

        double dx = Math.cos(radians) * obstacle.getMaxSpeed();
        double dy = Math.sin(radians) * obstacle.getMaxSpeed();
        obstacle.setRadians(radians);
        obstacle.setDx((float)dx);
        obstacle.setDy((float)dy);
        
        obstacle.setShapeX(new float[4]);
        obstacle.setShapeY(new float[4]);
        
        world.put(obstacle.getID(), obstacle);
    }

}
