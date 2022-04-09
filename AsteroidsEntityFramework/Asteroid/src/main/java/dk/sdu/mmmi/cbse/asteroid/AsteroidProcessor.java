/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

/**
 *
 * @author Vedsted
 */
public class AsteroidProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);

            int numPoints;
            float speed;

            if (asteroid.getRadius() == 5) {
                numPoints = 8;
                speed = (float) Math.random() * 70f + 100f;
            } else if (asteroid.getRadius() == 10) {
                numPoints = 10;
                speed = (float) Math.random() * 50f + 60f;
            } else {
                numPoints = 12;
                speed = (float) Math.random() * 20 + 30f;
            }

            movingPart.setMaxSpeed(speed);
            movingPart.setUp(true);

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);

            setShape(asteroid, numPoints);
        }
    }
    
    private void setShape(Entity entity, int numPoints) {
        PositionPart position = entity.getPart(PositionPart.class);
        float[] shapex = new float[numPoints];
        float[] shapey = new float[numPoints];
        float radians = position.getRadians();
        float x = position.getX();
        float y = position.getY();
        float radius = entity.getRadius();

        float angle = 0;

        for (int i = 0; i < numPoints; i++) {
            shapex[i] = x + (float) Math.cos(angle + radians) * radius;
            shapey[i] = y + (float) Math.sin(angle + radians) * radius;
            angle += 2 * Math.PI / numPoints;
        }

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
