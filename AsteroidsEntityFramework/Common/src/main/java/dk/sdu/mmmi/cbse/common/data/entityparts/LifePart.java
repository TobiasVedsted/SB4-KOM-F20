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

    public LifePart(int life, float expiration) {
        this.life = life;
        this.respawnTimer = expiration;
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

    public void setRespawnTimer(float expiration) {
        this.respawnTimer = expiration;
    }  
    
    public void reduceRespawnTimer(float delta){
        this.respawnTimer -= delta;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        
    }
}
