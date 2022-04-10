/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.commonlibrary.data.Entity;
import dk.sdu.mmmi.cbse.commonlibrary.data.GameData;
import dk.sdu.mmmi.cbse.commonlibrary.data.MovingPart;
import dk.sdu.mmmi.cbse.commonlibrary.data.PositionPart;
import dk.sdu.mmmi.cbse.commonlibrary.data.World;
import dk.sdu.mmmi.cbse.commonlibrary.services.IEntityProcessingService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Vedsted
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class EnemyControlSystem implements IEntityProcessingService{

    private int behavior;
    private Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemey : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemey.getPart(PositionPart.class);
            MovingPart movingPart = enemey.getPart(MovingPart.class);

            behavior = random.nextInt(5);

            switch (behavior) {
                // accelerating
                case 1:
                    movingPart.setRight(false);
                    movingPart.setLeft(false);
                    movingPart.setUp(true);
                    break;

                case 2:
                    // Turn left  
                    movingPart.setUp(false);
                    movingPart.setRight(false);
                    movingPart.setLeft(true);
                    break;

                case 3:
                    // Turn right
                    movingPart.setUp(false);
                    movingPart.setLeft(false);
                    movingPart.setRight(true);
                    break;

                case 4:
                    //SHooting

                    break;

                default:
                // Drift
            }

            movingPart.process(gameData, enemey);
            positionPart.process(gameData, enemey);

            updateShape(enemey);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1415f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
    
}
