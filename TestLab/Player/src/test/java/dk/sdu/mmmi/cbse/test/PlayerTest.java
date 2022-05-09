/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.cbse.test;

import dk.sdu.mmmi.cbse.commonlibrary.data.Entity;
import dk.sdu.mmmi.cbse.commonlibrary.data.GameData;
import dk.sdu.mmmi.cbse.commonlibrary.data.MovingPart;
import dk.sdu.mmmi.cbse.commonlibrary.data.PositionPart;
import dk.sdu.mmmi.cbse.commonlibrary.data.World;
import dk.sdu.mmmi.cbse.player.Player;
import dk.sdu.mmmi.cbse.player.PlayerPlugin;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author Vedsted
 */
public class PlayerTest {

    @Test
    public void testPlayerMovement() throws InterruptedException {
        GameData gameData = new GameData();
        World world = new World();

        gameData.setDisplayWidth(800);
        gameData.setDisplayHeight(600);
        gameData.setDelta(0.015f);

        PlayerPlugin playerPlugin = new PlayerPlugin();
        playerPlugin.start(gameData, world);

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);

            float startX = positionPart.getX();
            float startY = positionPart.getY();

            movingPart.setUp(true);
            for (int i = 0; i < 1000; i++) {
                if (i >= 500) {
                    movingPart.setRight(true);
                }
                movingPart.process(gameData, player);
                System.out.println(positionPart.getX() + " " + positionPart.getY());
                positionPart.process(gameData, player);
                System.out.println(positionPart.getX() + " " + positionPart.getY());
            }
            movingPart.setUp(false);
            movingPart.process(gameData, player);
            positionPart.process(gameData, player);

            float endX = positionPart.getX();
            float endY = positionPart.getY();
            System.out.println("Start X Positon: " + startX + " End X Position " + endX);
            System.out.println("Start Y Positon: " + startY + " End Y Position " + endY);
            assertFalse(startY == endY);
            assertFalse(startX == endX);

        }

    }

}
