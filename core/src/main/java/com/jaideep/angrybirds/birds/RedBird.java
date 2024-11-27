package com.jaideep.angrybirds.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class RedBird extends Bird{
    private transient Texture redBird;
    private Image bird;

    public RedBird(String path){
        redBird = new Texture(path);
    }

    public Image getImage(){
        bird = new Image(new TextureRegionDrawable(new TextureRegion(redBird)));
        bird.setSize(50,50);
        return bird;
    }
    
    public static Body createDynamicBody(float x, float y, float radius, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 100f, y / 100f); // Scale down for Box2D units (meters)
        
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / 100f); // Scale down for Box2D units (meters)
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 2.5f;
        fixtureDef.restitution = 0f;
        
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setLinearDamping(1.2f);
        shape.dispose();
        
        return body;
    }


}
