package com.jaideep.angrybirds;

import com.badlogic.gdx.physics.box2d.*;

public class Boundary {
    
    public void createScreenBoundaries(World world) {
        float worldWidth = 1200 / 100f; // Convert to Box2D meters
        float worldHeight = 480 / 100f;
        
        // Create the ground boundary (bottom)
        createBoundary(0, 0, worldWidth, 0.1f, world);
        
        // Create the ceiling boundary (top)
        createBoundary(0, worldHeight, worldWidth, 0.1f, world);
        
        // Create the left wall boundary
        createBoundary(0, 0, 0.1f, worldHeight, world);
        
        // Create the right wall boundary
        createBoundary(worldWidth, 0, 0.1f, worldHeight, world);
    }
    
    private void createBoundary(float x, float y, float width, float height, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + width / 2, y + height / 2); // Center the boundary at its position
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2); // Define the boundary as a rectangle
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0f;
        fixtureDef.friction = 2.5f;
        fixtureDef.restitution = 0f; // Adjust if you want the bird to bounce off
        
        Body boundaryBody = world.createBody(bodyDef);
        boundaryBody.createFixture(fixtureDef);
        
        shape.dispose();
    }
}
