/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.projectile;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import static dk.sdu.mmmi.cbse.common.data.EntityType.PLAYER;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Map;

/**
 *
 * @author simon
 */
public class ProjectileProcessor implements IEntityProcessingService{

    private long timeStamp;
    private final float radius = 3;
    private long lifeTime = 1500;
    private long fireRate = 200;

    @Override
    public void process(GameData gameData, Map<String, Entity> world, Entity entity) {
        if (gameData.getKeys().isDown(GameKeys.SPACE) && System.currentTimeMillis() - timeStamp > fireRate) {

            timeStamp = System.currentTimeMillis();
            for (Map.Entry<String, Entity> entrySet : world.entrySet()) {
                Entity value = entrySet.getValue();

                if (value.getType() == PLAYER) {
                    Entity player = value;
                    createBullet(world, player);
                }
            }
        }

        if (entity.getType() == EntityType.BULLET) {

            // Removes the bullet if certain conditions is met.
            if (System.currentTimeMillis() - entity.getSpawnTime() > lifeTime) {
                world.remove(entity.getID());
            }

            float x = entity.getX();
            float y = entity.getY();
            float dt = gameData.getDelta();
            float dx = entity.getDx();
            float dy = entity.getDy();

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

    private void createBullet(Map<String, Entity> world, Entity player) {
        Entity bullet = new Entity();
        bullet.setType(EntityType.BULLET);
        bullet.setRadius(radius);
        bullet.setRadians(player.getRadians());

        float offset = player.getRadius() + radius + 1;
        bullet.setX((float) (player.getX() + Math.cos(player.getRadians()) * offset));
        bullet.setY((float) (player.getY() + Math.sin(player.getRadians()) * offset));
        bullet.setMaxSpeed(350);

        bullet.setDx((float) (Math.cos(player.getRadians()) * bullet.getMaxSpeed()));
        bullet.setDy((float) (Math.sin(player.getRadians()) * bullet.getMaxSpeed()));
        
        bullet.setShapeX(new float[4]);
        bullet.setShapeY(new float[4]);
        
        world.put(bullet.getID(), bullet);
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        float x = entity.getX();
        float y = entity.getY();
        float radians = entity.getRadians();

        shapex[0] = (float) (x + 1);
        shapey[0] = (float) (y + 0);

        shapex[1] = (float) (x + 0);
        shapey[1] = (float) (y + 1);

        shapex[2] = (float) (x + -1);
        shapey[2] = (float) (y + 0);

        shapex[3] = (float) (x + 0);
        shapey[3] = (float) (y + -1);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
    
}
