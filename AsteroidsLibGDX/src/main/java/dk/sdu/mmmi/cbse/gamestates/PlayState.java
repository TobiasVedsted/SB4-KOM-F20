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
    
    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {

        sr = new ShapeRenderer();

        bulletsPlayer = new ArrayList<Bullet>();
        bulletsEnemy = new ArrayList<Bullet>();
        
        player = new Player(bulletsPlayer);
        enemy = new Enemy(bulletsEnemy);
        asteroids = new ArrayList<Asteroid>();
        asteroids.add(new Asteroid(100, 100, Asteroid.medium));
        

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
