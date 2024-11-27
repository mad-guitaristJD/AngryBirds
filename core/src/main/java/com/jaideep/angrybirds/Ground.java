package com.jaideep.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Ground {
    private Body supportBox;
    public Sprite groundSprite;
    
    public Ground(){
        // Load the ground texture
        Texture groundTexture = new Texture("base.png");
        groundSprite = new Sprite(groundTexture);
        groundSprite.setSize(1200, 80); // Adjust the size of the ground as needed
        groundSprite.setPosition(0, 0); // Place the ground at the bottom of the screen
    }
    
    public Sprite getGroundSprite(){
        return groundSprite;
    }
    
    public void createGround(World world) {
        // Define the ground body
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(400 / 100f, 50 / 100f); // Centered horizontally at y = 50px
        
        Body groundBody = world.createBody(groundBodyDef);
        
        // Define the ground shape
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(800 / 100f, 10 / 100f); // Width: 800px, Height: 20px (scaled to meters)
        
        // Attach the ground shape to the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundBox;
        fixtureDef.density = 0.2f;
        fixtureDef.friction = 1f;
        groundBody.createFixture(fixtureDef);
        
        // Dispose of the shape
        groundBox.dispose();
    }
    
    public void createBaseUnderBird(Body body, World world) {
        // Define the body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        
        // Set the position of the invisible box (adjust as needed)
        float birdX = body.getPosition().x;
        float birdY = body.getPosition().y;
        bodyDef.position.set(birdX, birdY - 0.5f); // Place it slightly below the bird
        
        // Create the body in the world
        supportBox = world.createBody(bodyDef);
        
        // Define the box shape
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(0.2f, 0.6f); // Width and height of the invisible box
        
        // Define the fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 0; // No mass since it's static
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0; // No bounce
        
        // Attach the shape to the body
        supportBox.createFixture(fixtureDef);
        
        // Dispose of the shape after use
        boxShape.dispose();
    }
    
    
}
