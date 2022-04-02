/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Vedsted
 */
public class Enemy extends SpaceObject {

    private final int maxBullets = 4;
    private ArrayList<Bullet> bullets;

    private Random random = new Random();
    private int behavior;

    private float[] flamex;
    private float[] flamey;

    private float maxSpeed;
    private float acceleration;
    private float deceleration;
    private float acceleratingTimer;
    
    private float deadTimer;
    private float deadTime;
    private Line2D.Float[] hitLines;
    private Point2D.Float[] hitLinesVector;
    private int health;
    private int orgHealth;
    
    private boolean dead;
    private boolean deadDead;

    public Enemy(ArrayList<Bullet> bullets, int health) {

        this.bullets = bullets;
        this.health = health;
        orgHealth = health;

        x = Game.WIDTH / 1.5f;
        y = Game.HEIGHT / 1.5f;

        maxSpeed = 200;
        acceleration = 200;
        deceleration = 10;

        shapex = new float[4];
        shapey = new float[4];
        flamex = new float[3];
        flamey = new float[3];

        radians = 3.1415f / 2;
        rotationSpeed = 3;
        
        dead = false;
        deadTimer = 0;
        deadTime = 2;

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
    
    public void hit() {
        health--;
        System.out.println("E" + health);
    }
    
    public void dead() {
        if (dead) {
            return;
        }

        dead = true;
        dx = dy = 0;

        hitLines = new Line2D.Float[4];
        for (int i = 0, j = hitLines.length - 1; i < hitLines.length; j = i++) {
            hitLines[i] = new Line2D.Float(shapex[i], shapey[i], shapex[j], shapey[j]);
        }

        hitLinesVector = new Point2D.Float[4];
        hitLinesVector[0] = new Point2D.Float(
                MathUtils.cos(radians + 1.5f),
                MathUtils.sin(radians + 1.5f)
        );
        hitLinesVector[1] = new Point2D.Float(
                MathUtils.cos(radians - 1.5f),
                MathUtils.sin(radians - 1.5f)
        );
        hitLinesVector[2] = new Point2D.Float(
                MathUtils.cos(radians + 2.8f),
                MathUtils.sin(radians + 2.8f)
        );
        hitLinesVector[3] = new Point2D.Float(
                MathUtils.cos(radians - 2.8f),
                MathUtils.sin(radians - 2.8f)
        );

    }

    public int getHealth() {
        return health;
    }
    
    public boolean isDead() {
        return dead;
    }

    public boolean isDeadDead() {
        return deadDead;
    }
    
    public void reset() {
        x = Game.WIDTH / 1.5f;
        y = Game.HEIGHT / 1.5f;
        setShape();
        health = orgHealth;
        System.out.println("E" + health);
        dead = deadDead = false;
    }

    public void update(float dt) {

        if (dead) {
            deadTimer += dt;
            if (deadTimer > deadTime){
                System.out.println("E deaddead");
                deadDead = true;
                deadTimer = 0;
            }
            for (int i = 0; i < hitLines.length; i++) {
                hitLines[i].setLine(
                        hitLines[i].x1 + hitLinesVector[i].x * 10 * dt,
                        hitLines[i].y1 + hitLinesVector[i].y * 10 * dt,
                        hitLines[i].x2 + hitLinesVector[i].x * 10 * dt,
                        hitLines[i].y2 + hitLinesVector[i].y * 10 * dt
                );
            }
            return;
        }
        
        behavior = random.nextInt(6);
        

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

            case 5:
                //SHooting
                if (bullets.size() == maxBullets) {
                    return;
                }
                bullets.add(new Bullet(x, y, radians));
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

        if (dead) {
            for (int i = 0; i < hitLines.length; i++) {
                sr.line(
                        hitLines[i].x1,
                        hitLines[i].y1,
                        hitLines[i].x2,
                        hitLines[i].y2
                );
            }
            sr.end();
            return;
        }
        
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
