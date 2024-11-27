package com.jaideep.angrybirds.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public class Blocks {
    public Body body;
    public Sprite sprite;
    
    public Blocks(Body body, Sprite sprite) {
        this.body = body;
        this.sprite = sprite;
    }
    
    public static void updateBlockPosition(Blocks block) {
        block.sprite.setPosition(
                block.body.getPosition().x * 100 - block.sprite.getWidth() / 2,
                block.body.getPosition().y * 100 - block.sprite.getHeight() / 2
        );
        block.sprite.setRotation((float) Math.toDegrees(block.body.getAngle()));
    }
}
