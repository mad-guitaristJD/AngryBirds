package com.jaideep.angrybirds.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaideep.angrybirds.AngryBirds;
import com.jaideep.angrybirds.Assets;

public class LevelScreen implements Screen {
    private AngryBirds game;
    private Skin skin;
    private Label topLabel;
    private AssetManager assetManager;
    private Viewport viewport;
    private Stage stage;
    private Table labelTable; // shows the heading
    private Table levelTable; //Keeps the level buttons
    private Table backTable; // keeps the back button
    private Texture background;

    private Screen gameScreen; // takes level to game screen needs to changed later
                               // takes to the same screen
                               // change for level



    public LevelScreen (AngryBirds game){
        this.game = game;
        assetManager = AngryBirds.assets.getAssetManager();
        skin = assetManager.get(Assets.SKIN);
        gameScreen = new GameScreen(game);
        AngryBirds.screens.put("gameScreen", gameScreen);
    }

    @Override
    public void show() {
        background = new Texture("firstScreen.jpg");
        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(background)));
        backgroundImage.setFillParent(true);


        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        stage = new Stage(viewport);

        topLabel = new Label("SELECT LEVEL", AngryBirds.getLabelStlye("75fa64"));


        labelTable = new Table();
        labelTable.setFillParent(true);
        labelTable.top();
        labelTable.add(topLabel).center().padTop(10);
        labelTable.row();

        backTable = new Table();
        backTable.setFillParent(true);
        backTable.top().left();
        TextButton backButton = new TextButton("<=", AngryBirds.getTextButtonStyle());
        backButton.getLabelCell().pad(100);
        backButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                AngryBirds.click.play();
                game.setScreen(AngryBirds.screens.get("playScreen"));
            }
        });
        backTable.add(backButton).width(30).height(30);
        backTable.padLeft(10).padTop(10);


        levelTable = new Table();
        levelTable.top().padTop(labelTable.getPrefHeight());
        levelTable.setFillParent(true);

        stage.addActor(backgroundImage);
        stage.addActor(labelTable);
        stage.addActor(levelTable);
        stage.addActor(backTable);

        addButton("LEVEL 1\nEASY").addListener(AngryBirds.clickListenerChangeScreen);      //these need to be changed once
        addButton("LEVEL 2\nMEDIUM").addListener(AngryBirds.clickListenerChangeScreen);    //the new levels are introduced
        addButton("LEVEL 3\nHARD").addListener(AngryBirds.clickListenerChangeScreen);      //and should have diff actions
        
        
        Gdx.input.setInputProcessor(stage);
    }

    private TextButton addButton(String text){ //USED FOR MULTIPLE LEVEL BUTTON
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("default-round", Color.valueOf("75fa64"));
        textButtonStyle.down = skin.newDrawable("default-round", Color.valueOf("619e39"));
        textButtonStyle.over = skin.newDrawable("default-round", Color.valueOf("619e39"));
        textButtonStyle.font = skin.getFont("white-font");
        TextButton textButton = new TextButton(text, textButtonStyle);
        levelTable.add(textButton).width(220).height(350).padTop(30).padLeft(10).padRight(10);
        if (levelTable.getChildren().size % 3 == 0) {
            levelTable.row();
        }
        return textButton;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();


    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
    
    @Override
    public void hide() {
        // Dispose of the stage and other resources to prevent the buttons from still being clickable
        stage.dispose();
        Gdx.input.setInputProcessor(null);  // Clear the input processor
    }

    @Override
    public void dispose() {
        stage.dispose();
        this.dispose();
    }
}
