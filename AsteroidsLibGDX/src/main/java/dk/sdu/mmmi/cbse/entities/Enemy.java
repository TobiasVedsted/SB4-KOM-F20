/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;
import java.util.Random;

/**
 *
 * @author Vedsted
 */
public class Enemy extends SpaceObject {

    private Random random = new Random();
    private int behavior;

    private float[] flamex;
    private float[] flamey;

    private float maxSpeed;
    private float acceleration;
    private float deceleration;
    private float acceleratingTimer;

    public Enemy() {

        x = Game.WIDTH / 2;
        y = Game.HEIGHT / 2;

        maxSpeed = 300;
        acceleration = 200;
        deceleration = 10;

        shapex = new float[4];
        shapey = new float[4];
        flamex = new float[3];
        flamey = new float[3];

        radians = 3.1415f / 2;
        rotationSpeed = 3;

    }

    private void setShape() {
        shapex[0] = x + MathUtils.cos(radians) * 8;
        shapey[0] = y + MathUtils.sin(radians) * 8;

        shapex[1] = x + MathUtils.cos(radians - 4 * 3.1415f / 5) * 8;
        shapey[1] = y + MathUtils.sin(radians - 4 * 3.1145f / 5) * 8;

        shapex[2] = x + MathUtils.cos(radians + 3.1415f) * 5;
        shapey[2] = y + MathUtils.sin(radians + 3.1415f) * 5;

        shapex[3] = x + MathUtils.cos(radians + 4 * 3.1415f / 5) * 8;
        shapey[3] = y + MathUtils.sin(radians + 4 * 3.1415f / 5) * 8;
    }

    private void setFlame() {
        flamex[0] = x + MathUtils.cos(radians - 5 * 3.1415f / 6) * 5;
        flamey[0] = y + MathUtils.sin(radians - 5 * 3.1415f / 6) * 5;

        flamex[1] = x + MathUtils.cos(radians - 3.1415f) * (6 + acceleratingTimer * 50);
        flamey[1] = y + MathUtils.sin(radians - 3.1415f) * (6 + acceleratingTimer * 50);

        flamex[2] = x + MathUtils.cos(radians + 5 * 3.1415f / 6) * 5;
        flamey[2] = y + MathUtils.sin(radians + 5 * 3.1415f / 6) * 5;
    }

    public void update(float dt) {

        behavior = random.nextInt(5);

        // deceleration
        float vec = (float) Math.sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        // set position
        x += dx * dt;
        y += dy * dt;

        // set shape
        setShape();

        switch (behavior) {
            // accelerating
            case 1:
            case 2:
                dx += MathUtils.cos(radians) * acceleration * dt;
                dy += MathUtils.sin(radians) * acceleration * dt;
                acceleratingTimer += dt;
                if (acceleratingTimer > 0.1f) {
                    acceleratingTimer = 0;
                } else {
                    acceleratingTimer = 0;
                }
                setFlame();
                break;

            case 3:
                // Turn left            
                radians += rotationSpeed * dt;
                break;

            case 4:
                // Turn right
                radians -= rotationSpeed * dt;
                break;

            default:
            // Drift
        }

        // screen wrap
        wrap();

    }

    public void draw(ShapeRenderer sr) {

        sr.setColor(1, 1, 1, 1);

        sr.begin(ShapeRenderer.ShapeType.Line);

        for (int i = 0, j = shapex.length - 1;
                i < shapex.length;
                j = i++) {

            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);

        }

        for (int i = 0, j = flamex.length - 1;
                i < flamex.length;
                j = i++) {

            sr.line(flamex[i], flamey[i], flamex[j], flamey[j]);

        }

        sr.end();

    }

}
