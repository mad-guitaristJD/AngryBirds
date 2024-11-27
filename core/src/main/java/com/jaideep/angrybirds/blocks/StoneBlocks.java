package com.jaideep.angrybirds.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.jaideep.angrybirds.screens.Blocks;

public class StoneBlocks extends Blocks implements BlockInterface {
    public static transient Texture blockTexture;
    
    public StoneBlocks(Body body, Sprite sprite) {
        super(body, sprite);
    }
    
    
    public static Blocks createStoneBlock(float x, float y, boolean isHori, World world) {
        // Load the block texture
        blockTexture = new Texture("blocks/StoneBlock.png");
        Sprite blockSprite = new Sprite(blockTexture);
        
        float blockWidth = 31;
        float blockHeight = 179;
        blockSprite.setSize(blockWidth, blockHeight);
        
        // Define body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if (isHori) bodyDef.angle = 99f;
        
        bodyDef.position.set(x / 100f, y / 100f);
        
        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(blockWidth / 2 / 100f, blockHeight / 2 / 100f);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockShape;
        fixtureDef.density = 3f; // Increased density for stability
        fixtureDef.friction = 2f; // Higher friction for better interaction
        fixtureDef.restitution = 0f; // Minimal bounce
        
        Body blockBody = world.createBody(bodyDef);
        blockBody.createFixture(fixtureDef);
        
        blockShape.dispose();
        return new Blocks(blockBody, blockSprite);
    }
}
