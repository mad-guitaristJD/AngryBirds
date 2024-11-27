package com.jaideep.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaideep.angrybirds.*;
import com.jaideep.angrybirds.birds.RedBird;
import com.jaideep.angrybirds.blocks.GlassBlocks;
import com.jaideep.angrybirds.pigs.*;
import com.jaideep.angrybirds.birds.*;

import java.io.*;

public class GameScreen implements Screen, Serializable {
    private static final long serialVersionUID = 1L;
    private AngryBirds game;
    private Array<Blocks> blocks = new Array<>();
    private Array<Pig> pigs = new Array<>();
    private final Array<Body> bodiesToRemove = new Array<>();
    
    
    // Bird Drag Variables
    private boolean isDragging = false;
    private Vector2 initialTouchPosition = new Vector2();
    private Vector2 currentTouchPosition = new Vector2();
    
    // Physics
    private transient World world;
    private Box2DDebugRenderer debugRenderer;
    
    // Rendering
    private transient SpriteBatch batch;
    private Viewport viewport;
    private Stage stage;
    
    // Birds and Ground
    private Sprite redBirdSprite, groundSprite;
    private Body redBirdBody;
    private boolean isBirdMoving = false;
    
    //Pigs
    private Sprite pigSprite1, pigSprite2;
    private Body pigBody1, pigBody2;
    
    // Sling
    Sling sling;
    private transient Texture dotTexture;
    private Array<Vector2> trajectoryDots = new Array<>();
    private Sprite slingSprite;
    
    // UI
    private Label chancesLabel;
    private Label scoreLabel;
    private transient Texture background;
    private int chances;
    private int score;
    
    
    public GameScreen(AngryBirds game) {
        this.game = game;
        this.world = new World(new Vector2(0, -9.8f), true);
        this.debugRenderer = new Box2DDebugRenderer();
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                handleCollision(contact);
            }
            @Override
            public void endContact(Contact contact) {}
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
    }
    
    public void saveGame() {
        try (FileOutputStream fileOut = new FileOutputStream("game_state.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this); // Serialize the GameScreen object
            System.out.println("Game state saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save game state.");
        }
    }
    
    public static GameScreen loadGame(AngryBirds game) {
        try (FileInputStream fileIn = new FileInputStream("game_state.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            GameScreen loadedGame = (GameScreen) in.readObject();
            loadedGame.game = game; // Reassign the game reference
            System.out.println("Game state loaded successfully.");
            return loadedGame;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load game state.");
            return null;
        }
    }
    
    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(1200, 480);
        stage = new Stage(viewport);
        
        InputAdapter dragInput = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // Check if the touch is near the bird's position
                Vector2 touchPoint = viewport.unproject(new Vector2(screenX, screenY));
                if (redBirdSprite.getBoundingRectangle().contains(touchPoint.x, touchPoint.y) && !isBirdMoving) {
                    isDragging = true;
                    initialTouchPosition.set(touchPoint);
                    currentTouchPosition.set(touchPoint);
                }
                return true;
            }
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging) {
                    currentTouchPosition.set(viewport.unproject(new Vector2(screenX, screenY)));
                    trajectoryDots.clear();
                    trajectoryDots = sling.calculateTrajectory(initialTouchPosition, currentTouchPosition, world, redBirdBody, trajectoryDots);
                }
                return true;
            }
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging) {
                    isDragging = false;
                    trajectoryDots.clear();
                    Vector2 dragVector = initialTouchPosition.cpy().sub(currentTouchPosition);
                    Bird.launchBird(redBirdBody, dragVector.scl(0.25f));
                    isBirdMoving = true;
                }
                return true;
            }
        };
        
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(dragInput);
        Gdx.input.setInputProcessor(inputMultiplexer);
        
        // Making sling
        sling = new Sling();
        slingSprite = sling.createSling();
        dotTexture = new Texture("dot.png");
        
        // Making red bird
        redBirdSprite = new Sprite(new Texture("characters/red.png"));
        redBirdSprite.setSize(50, 50);
        redBirdBody = RedBird.createDynamicBody(100, 80, 25, world);
        redBirdSprite.setPosition(
                slingSprite.getX() + slingSprite.getWidth() / 2 - redBirdSprite.getWidth() / 2,
                slingSprite.getY() + slingSprite.getHeight() / 2 - redBirdSprite.getHeight() / 2
        );
        redBirdBody.setTransform(
                (slingSprite.getX() + slingSprite.getWidth() / 2) / 100f,
                (slingSprite.getY() + slingSprite.getHeight() / 2) / 100f,
                0
        );
        
        // Making pigs
        Texture pigTexture = new Texture("characters/piggy.png");
        // Create Pig 1
        pigSprite1 = new Sprite(pigTexture);
        pigSprite1.setSize(50, 50);
        pigBody1 = Piggy.createDynamicBody(500, 100, 25, world);
        // Create Pig 2
        pigSprite2 = new Sprite(pigTexture);
        pigSprite2.setSize(50, 50);
        pigBody2 = Piggy.createDynamicBody(600, 81, 25, world);
        
        // Creating Ground
        Ground ground = new Ground();
        groundSprite = ground.getGroundSprite();
        ground.createGround(world);
        ground.createBaseUnderBird(redBirdBody, world);
        
        // Creating boundary
        Boundary boundary = new Boundary();
        boundary.createScreenBoundaries(world);
        
        // Adding blocks
        addBlocks();
        
        // Add UI components
        TextButton pauseButton = new TextButton("Pause", AngryBirds.getTextButtonStyle());
        pauseButton.setPosition(viewport.getWorldWidth() - 1150, viewport.getWorldHeight() - 50);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveGame(); // Save game state
                game.setScreen(new Pause(game, 1)); // Switch to the pause screen
            }
        });
        stage.addActor(pauseButton);
        
        chances = 2;
        CreateGameScreenUI UImaker = new CreateGameScreenUI(chances, viewport);
        background = UImaker.getBackground();
        scoreLabel = UImaker.getScoreLabel();
        stage.addActor(scoreLabel);
        chancesLabel = UImaker.getChancesLabel();
        stage.addActor(chancesLabel);
        score = UImaker.getScore();
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Step physics simulation
        world.step(1 / 60f, 6, 2);
        
        for (Body body : bodiesToRemove) {
            world.destroyBody(body);
        }
        bodiesToRemove.clear(); // Clear the queue
        
        if (redBirdBody.getLinearVelocity().len() == 0 && isBirdMoving) {
            if(chances==1){
                game.setScreen(new LoseScreen(game, 1));
            }
            isBirdMoving = Bird.resetBirdPosition(redBirdBody, slingSprite, isBirdMoving); // Reset bird position when its velocity is zero
            chances--;
            chancesLabel.setText("Chances: " + chances);
        }
        
        // Update bird sprites
        Bird.updateBirdPosition(redBirdSprite, redBirdBody);
        
        // Update pig sprites
        Pig.updatePigPosition(pigSprite1, pigBody1);
        Pig.updatePigPosition(pigSprite2, pigBody2);
        
        // Update block sprites
        for (Blocks block : blocks) {
            Blocks.updateBlockPosition(block);
        }
        
        // Render all sprites
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        slingSprite.draw(batch); // Draw the sling
        groundSprite.draw(batch);
        redBirdSprite.draw(batch);
        for (Blocks block : blocks) {
            block.sprite.draw(batch);
        }
        if (pigSprite1.getColor().a > 0) {
            pigSprite1.draw(batch);
        }
        if (pigSprite2.getColor().a > 0) {
            pigSprite2.draw(batch);
        }
        // Render trajectory dots
        for (Vector2 dotPosition : trajectoryDots) {
            batch.draw(dotTexture, dotPosition.x - 5, dotPosition.y - 5, 10, 10); // Adjust size and position
        }
        // Render drag line if dragging
        if (isDragging) {
            batch.setColor(Color.RED);
            batch.draw(
                    new TextureRegion(background),
                    currentTouchPosition.x,
                    currentTouchPosition.y,
                    5, 5
            );
            batch.setColor(Color.WHITE); // Reset to default color
        }
        batch.end();
        
        // Render UI
        stage.act(delta);
        stage.draw();
        
        // Debug rendering
        debugRenderer.render(world, viewport.getCamera().combined);
    }
    
    
    
    private void handleCollision(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();
        
        if (isPigCollision(bodyA, bodyB)) {
            markPigForRemoval(bodyA, bodyB);
        }
    }
    
    private boolean isPigCollision(Body bodyA, Body bodyB) {
        return (bodyA == pigBody1 || bodyA == pigBody2 || bodyB == pigBody1 || bodyB == pigBody2) &&
                (bodyA == redBirdBody || bodyB == redBirdBody || isBlock(bodyA) || isBlock(bodyB));
    }
    
    private boolean isBlock(Body body) {
        for (Blocks block : blocks) {
            if (block.body == body) {
                return true;
            }
        }
        return false;
    }
    
    private void markPigForRemoval(Body bodyA, Body bodyB) {
        if (bodyA == pigBody1 || bodyB == pigBody1) {
            removePig(pigBody1, pigSprite1);
        }
        if (bodyA == pigBody2 || bodyB == pigBody2) {
            removePig(pigBody2, pigSprite2);
        }
    }
    
    private void removePig(Body pigBody, Sprite pigSprite) {
        pigSprite.setAlpha(0); // Make the sprite invisible
        
        // Find and remove the corresponding Pig object
        for (int i = 0; i < pigs.size; i++) {
            Pig pig = pigs.get(i);
            if (pig.getBody() == pigBody) {
                pigs.removeIndex(i); // Remove the Pig from the array
                break;
            }
        }
        
        bodiesToRemove.add(pigBody); // Queue body for removal
        score += 1000; // Increment score
        scoreLabel.setText("Score: " + score); // Update score label
        
        // Only transition to the win screen if all pigs are removed
        if (score==2000) {  // Check if there are no pigs left
            game.setScreen(new WinScreen(game, score,1)); // Transition to Win Screen
        }
    }
    
    
    private void addBlocks() {
        // Change accordingly
        Blocks block = GlassBlocks.createGlassBlock(550, 120, false, world);
        blocks.add(block);
        block = GlassBlocks.createGlassBlock(650, 120, false, world);
        blocks.add(block);
        block = GlassBlocks.createGlassBlock(600, 240, true, world);
        blocks.add(block);
    }
    
    
    
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    
    @Override
    public void pause() {}
    
    @Override
    public void resume() {}
    
    @Override
    public void hide() {}
    
    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        batch.dispose();
        stage.dispose();
    }
    
    
    
    
}
