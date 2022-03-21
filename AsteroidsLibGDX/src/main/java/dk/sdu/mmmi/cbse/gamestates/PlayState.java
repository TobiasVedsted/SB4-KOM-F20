package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.entities.Bullet;
import dk.sdu.mmmi.cbse.entities.Enemy;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;
import java.util.ArrayList;

public class PlayState extends GameState {

    private ShapeRenderer srPlayer;
    private ShapeRenderer srEnemy;

    private Player player;
    private Enemy enemy;
    private ArrayList<Bullet> bulletsPlayer;
    private ArrayList<Bullet> bulletsEnemy;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {

        srPlayer = new ShapeRenderer();
        srEnemy = new ShapeRenderer();

        bulletsPlayer = new ArrayList<Bullet>();
        bulletsEnemy = new ArrayList<Bullet>();
        
        player = new Player(bulletsPlayer);
        enemy = new Enemy(bulletsEnemy);

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

    }

    public void draw() {
        player.draw(srPlayer);
        enemy.draw(srEnemy);
        
        for(int i = 0; i < bulletsPlayer.size(); i++) {
            bulletsPlayer.get(i).draw(srPlayer);
        }
        
        for(int i = 0; i < bulletsEnemy.size(); i++) {
            bulletsEnemy.get(i).draw(srEnemy);
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
