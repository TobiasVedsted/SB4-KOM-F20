/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.commonlibrary.data.Entity;
import dk.sdu.mmmi.cbse.commonlibrary.data.GameData;
import dk.sdu.mmmi.cbse.commonlibrary.data.MovingPart;
import dk.sdu.mmmi.cbse.commonlibrary.data.PositionPart;
import dk.sdu.mmmi.cbse.commonlibrary.data.World;
import dk.sdu.mmmi.cbse.commonlibrary.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Vedsted
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class AsteroidPlugin implements IGamePluginService{
    
    public AsteroidPlugin(){
        
    }
    
    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid1 = createAsteroid(gameData, 1);
        world.addEntity(asteroid1);
        Entity asteroid2 = createAsteroid(gameData, 2);
        world.addEntity(asteroid2);
        Entity asteroid3 = createAsteroid(gameData, 3);
        world.addEntity(asteroid3);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }
    
    
    private Entity createAsteroid(GameData gameData, int type) {
        Entity asteroid = new Asteroid();
        float speed;
        float radians = (float) Math.random() * 2 * 3.1415f;
        
        switch (type) {
            case 1:
                speed = (float) Math.random() * 70f + 100f;
                asteroid.setRadius(5);
                break;
            case 2:
                speed = (float) Math.random() * 50f + 60f;
                asteroid.setRadius(10);
                break;
            case 3:
                speed = (float) Math.random() * 20 + 30f;
                asteroid.setRadius(20);
                break;
            default:
                speed = (float) Math.random() * 20 + 30f;
                asteroid.setRadius(20);
                break;
        }

        
        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(30, 30, radians));

        return asteroid;
    }
}
