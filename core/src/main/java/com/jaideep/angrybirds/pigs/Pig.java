package com.jaideep.angrybirds.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.jaideep.angrybirds.screens.WinScreen;

public class Pig {
    private Body body;
    private transient Sprite sprite;
    private int hitCount; // Counter for hits
    
    public Pig(Body body, Sprite sprite) {
        this.body = body;
        this.sprite = sprite;
        this.hitCount = 0; // Initialize hit counter
    }
    
    public Body getBody() {
        return body;
    }
    
    public Sprite getSprite() {
        return sprite;
    }
    
    public void incrementHitCount() {
        hitCount++;
    }
    
    public int getHitCount() {
        return hitCount;
    }
    
    public Pig(){};
    
    public static void updatePigPosition(Sprite sprite, Body body) {
        sprite.setPosition(
                body.getPosition().x * 100 - sprite.getWidth() / 2,
                body.getPosition().y * 100 - sprite.getHeight() / 2
        );
    }
    
    
    
}
