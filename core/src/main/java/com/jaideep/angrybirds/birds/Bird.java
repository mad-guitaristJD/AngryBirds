package com.jaideep.angrybirds.birds;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Bird {
    
    // Method to launch the bird with a parabolic trajectory
    public static void launchBird(Body birdBody, Vector2 impulse) {
        birdBody.setLinearVelocity(0, 0); // Reset velocity
        birdBody.applyLinearImpulse(impulse.scl(0.25f), birdBody.getWorldCenter(), true); // Adjust the scaling factor
    }
    
    public static void updateBirdPosition(Sprite sprite, Body body) {
        sprite.setPosition(
                body.getPosition().x * 100 - sprite.getWidth() / 2,
                body.getPosition().y * 100 - sprite.getHeight() / 2
        );
    }
    
    public static boolean resetBirdPosition(Body body, Sprite slingSprite, boolean isBirdMoving) {
        float yOffset = 35f; // Adjust this value to set how much higher the bird should be
        body.setTransform(
                (slingSprite.getX() + slingSprite.getWidth() / 2) / 100f,
                (slingSprite.getY() + slingSprite.getHeight() / 2 + yOffset) / 100f,
                0
        );
        body.setLinearVelocity(0, 0); // Stop any movement
        return false; // Allow new input after reset
    }
}
