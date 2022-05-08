/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author Someone
 */
public class LifePart implements EntityPart {

    private int life;
    private boolean isDead = false;
    private float respawnTimer;

    public LifePart(int life, float respawnTimer) {
        this.life = life;
        this.respawnTimer = respawnTimer;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public float getRespawnTimer() {
        return respawnTimer;
    }

    public void setRespawnTimer(float respawnTimer) {
        this.respawnTimer = respawnTimer;
    }  
    
    public void reduceRespawnTimer(float delta){
        this.respawnTimer -= delta;
    }
    
    public void takeDamage(int damage){
        life--;
        System.out.println("1 damage taken");
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        
    }
}
