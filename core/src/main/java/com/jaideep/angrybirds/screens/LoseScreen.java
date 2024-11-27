package com.jaideep.angrybirds.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaideep.angrybirds.AngryBirds;


public class LoseScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private AngryBirds game;
    private Texture background;
    private Label loseLabel;
    private TextButton retryButton;
    private TextButton homeButton;
    private TextButton exitButton;
    private Table labelTable;
    private Table buttonsTable;
    private int level;
    
    public LoseScreen(AngryBirds game, int level){
        this.game = game;
        this.level = level;
    }
    
    @Override
    public void show() {
        // Set up the viewport and stage
        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        stage = new Stage(viewport);
        
        // Set up the background
        background = new Texture("winscreen.png");
        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(background)));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        
        // Set up the label table
        loseLabel = new Label("YOU LOSE!!!", AngryBirds.getLabelStlye("clear"));
        labelTable = new Table();
        labelTable.top().padTop(50); // Position the label near the top with padding
        labelTable.setFillParent(true);
        labelTable.add(loseLabel).center(); // Center the label horizontally
        stage.addActor(labelTable);
        
        // Set up the buttons table
        buttonsTable = new Table();
        buttonsTable.bottom().padBottom(100); // Position the buttons near the bottom with padding
        buttonsTable.setFillParent(true);
        
        // Retry button
        retryButton = new TextButton("RETRY", AngryBirds.getTextButtonStyle());
        buttonsTable.add(retryButton).center().width(200).height(50).padBottom(20); // Add spacing between buttons
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (level == 1) {
                    game.setScreen(new GameScreen(game));
                } else if (level == 2) {
                    game.setScreen(new LevelTwo(game));
                } else if (level == 3) {
                    game.setScreen(new LevelThree(game));
                }
            }
        });
        buttonsTable.row(); // Move to the next row
        
        // Home button
        homeButton = new TextButton("HOME", AngryBirds.getTextButtonStyle());
        buttonsTable.add(homeButton).width(200).height(50).padBottom(20); // Add spacing between buttons
        homeButton.addListener(AngryBirds.clickListenerChangeScreen);
        buttonsTable.row(); // Move to the next row
        
        // Exit button
        exitButton = new TextButton("EXIT", AngryBirds.getTextButtonStyle());
        buttonsTable.add(exitButton).width(200).height(50);
        exitButton.addListener(AngryBirds.clickListenerChangeScreen);
        
        // Add buttons table to the stage
        stage.addActor(buttonsTable);
        
        // Set input processor
        Gdx.input.setInputProcessor(stage);
    }
    
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }
    
    @Override
    public void pause() {
    
    }
    
    @Override
    public void resume() {
    
    }
    
    @Override
    public void hide() {
    
    }
    
    @Override
    public void dispose() {
    
    }
}
