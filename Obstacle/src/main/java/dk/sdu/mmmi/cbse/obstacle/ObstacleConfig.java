/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.obstacle;

import dk.sdu.mmmi.cbse.common.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author simon
 */
@Configuration
public class ObstacleConfig {
    
    @Bean
    @Scope(value = "prototype")
    public IEntityProcessingService createObstacleProcessingService() {
        return new ObstacleProcessor();
    }

    @Bean
    @Scope(value = "prototype")
    public IGamePluginService createPlayerPluginService() {
        return new ObstaclePlugin();
    }
}
