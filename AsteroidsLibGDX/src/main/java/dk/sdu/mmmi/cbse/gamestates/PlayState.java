package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.entities.Asteroid;
import dk.sdu.mmmi.cbse.entities.Bullet;
import dk.sdu.mmmi.cbse.entities.Enemy;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;
import java.util.ArrayList;

public class PlayState extends GameState {

    private ShapeRenderer sr;

    private Player player;
    private Enemy enemy;
    private ArrayList<Bullet> bulletsPlayer;
    private ArrayList<Bullet> bulletsEnemy;
    private ArrayList<Asteroid> asteroids;
    
    private int totalAsteroids; 
    private int numAsteroidsLeft;
    
            
    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {

        sr = new ShapeRenderer();

        bulletsPlayer = new ArrayList<Bullet>();
        bulletsEnemy = new ArrayList<Bullet>();
        
        player = new Player(bulletsPlayer, 3);
        enemy = new Enemy(bulletsEnemy, 3);
        asteroids = new ArrayList<Asteroid>();
        asteroids.add(new Asteroid(100, 100, Asteroid.small));
        asteroids.add(new Asteroid(50, 100, Asteroid.medium));
        asteroids.add(new Asteroid(100, 50, Asteroid.large));
        
        totalAsteroids = asteroids.size();
        numAsteroidsLeft = totalAsteroids;
       

    }
    
    private void splitAsteroids(Asteroid a) {
        numAsteroidsLeft--;
        if(a.getType() == Asteroid.large) {
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.medium));
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.medium));
        }
        if(a.getType() == Asteroid.medium) {
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.small));
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.small));
        }
    }


    public void update(float dt) {

        handleInput();

        player.update(dt);
        enemy.update(dt);
        
        for(int i = 0; i < bulletsPlayer.size(); i++) {
            bulletsPlayer.get(i).update(dt);
            if(bulletsPlayer.get(i).shouldRemove()) {
                bulletsPlayer.remove(i);
                i--;
            }
        }
        
        for(int i = 0; i < bulletsEnemy.size(); i++) {
            bulletsEnemy.get(i).update(dt);
            if(bulletsEnemy.get(i).shouldRemove()) {
                bulletsEnemy.remove(i);
                i--;
            }
        }
        
        for(int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).update(dt);
            if(asteroids.get(i).shouldRemove()) {
                asteroids.remove(i);
                i--;
            }
        }
        
        // Check collision
        checkCollisions();

    }
    
    private void checkCollisions() {
        
        // Bullet - Asteroid collision
        for (int i = 0; i < bulletsPlayer.size(); i++) {
            Bullet b = bulletsPlayer.get(i);
            for (int j = 0; j < asteroids.size(); j++) {
                Asteroid a = asteroids.get(j);
                // if asteroid contains bullet, bullet has a very precise koordinat
                if(a.contains(b.getX(), b.getY())) {
                    bulletsPlayer.remove(i);
                    i--;
                    asteroids.remove(j);
                    j--;
                    splitAsteroids(a);
                    break;
                }
            }
        }
        
        for (int i = 0; i < bulletsEnemy.size(); i++) {
            Bullet b = bulletsEnemy.get(i);
            for (int j = 0; j < asteroids.size(); j++) {
                Asteroid a = asteroids.get(j);
                // if asteroid contains bullet, bullet has a very precise koordinat
                if(a.contains(b.getX(), b.getY())) {
                    bulletsEnemy.remove(i);
                    i--;
                    asteroids.remove(j);
                    j--;
                    splitAsteroids(a);
                    break;
                }
            }
        }
        
        // Player - Asteroid collision
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid a = asteroids.get(i);
            if(a.intersects(player)) {
                player.hit();
                if(player.getHealth() == 0) {
                    player.dead();
                }
                asteroids.remove(i);
                i--;
                splitAsteroids(a);
                break;
            }
        }
        
        // Bullet - Enemy collision
        for (int i = 0; i < bulletsPlayer.size(); i++) {
            Bullet b = bulletsPlayer.get(i);
            if(enemy.contains(b.getX(), b.getY())) {
                enemy.hit();
                if(enemy.getHealth() == 0) {
                    enemy.dead();
                }
                bulletsPlayer.remove(i);
                i--;
                break;
            }
        }
        
        // Bullet - Player collision
        for (int i = 0; i < bulletsEnemy.size(); i++) {
            Bullet b = bulletsEnemy.get(i);
            if(player.contains(b.getX(), b.getY())) {
                player.hit();
                if(player.getHealth() == 0) {
                    player.dead();
                }
                bulletsEnemy.remove(i);
                i--;
                break;
            }
        }
        
    }
    
    public void draw() {
        player.draw(sr);
        enemy.draw(sr);
        
        for(int i = 0; i < bulletsPlayer.size(); i++) {
            bulletsPlayer.get(i).draw(sr);
        }
        
        for(int i = 0; i < bulletsEnemy.size(); i++) {
            bulletsEnemy.get(i).draw(sr);
        }
        
        for(int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).draw(sr);
        }
    }

    public void handleInput() {
        player.setLeft(GameKeys.isDown(GameKeys.LEFT));
        player.setRight(GameKeys.isDown(GameKeys.RIGHT));
        player.setUp(GameKeys.isDown(GameKeys.UP));
        if (GameKeys.isPressed(GameKeys.SPACE)) {
            player.shoot();
        }
        
    }

    public void dispose() {
    }

}
