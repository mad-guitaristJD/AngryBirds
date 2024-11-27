package com.jaideep.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Sling {
    public Sprite slingSprite;
    
    public Sprite createSling(){
        Texture slingTexture = new Texture("sling.png");
        slingSprite = new Sprite(slingTexture);
        slingSprite.setSize(100, 150);
        slingSprite.setPosition(75, 40);
        return slingSprite;
    }
    
    public Array<Vector2> calculateTrajectory(Vector2 initialTouchPosition, Vector2 currentTouchPosition, World world, Body body, Array<Vector2> trajectoryDots) {
        
        // Calculate the drag vector and scale for velocity
        Vector2 dragVector = initialTouchPosition.cpy().sub(currentTouchPosition);
        Vector2 initialVelocity = dragVector.scl(0.25f); // Adjust scaling factor
        
        // Physics parameters
        float timeStep = 0.1f; // Time interval for calculating positions
        int maxSteps = 5;     // Number of dots to display
        Vector2 gravity = world.getGravity(); // Gravity in the physics world
        
        Vector2 startPosition = new Vector2(
                body.getPosition().x,
                body.getPosition().y
        );
        
        for (int i = 0; i < maxSteps; i++) {
            float t = i * timeStep;
            float x = startPosition.x + initialVelocity.x * t;
            float y = startPosition.y + initialVelocity.y * t + 0.5f * gravity.y * t * t;
            
            // Stop adding dots if below ground
            if (y < 0) break;
            
            trajectoryDots.add(new Vector2(x * 100, y * 100)); // Convert to screen space
        }
        return trajectoryDots;
    }
}
