package com.jaideep.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaideep.angrybirds.AngryBirds;
import com.jaideep.angrybirds.Assets;

import java.io.Serializable;

public class Pause implements Screen, Serializable {
    private Stage stage;
    private Viewport viewport;
    private TextButton home;
    private TextButton resume;
    private TextButton save;
    private TextButton exit;
    private Table buttonsTable;
    private Table labelTable;
    private AngryBirds game;
    private transient Texture background;
    private Label pauseLabel;
    private int level;

    public Pause(AngryBirds game, int level){
        this.game = game;
        this.level = level;
    }


    @Override
    public void show() {
        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        stage = new Stage(viewport);
        buttonsTable = new Table();
        labelTable = new Table();
        buttonsTable.setFillParent(true);
        labelTable.setFillParent(true);

        background = new Texture("pausebg.jpg");
        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(background)));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        home = new TextButton("HOME", AngryBirds.getTextButtonStyle());
        home.addListener(AngryBirds.clickListenerChangeScreen);

        resume = new TextButton("RESUME", AngryBirds.getTextButtonStyle());
        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(level==1){
                    GameScreen loadedGame = GameScreen.loadGame(game);
                    if (loadedGame != null) {
                        game.setScreen(loadedGame);
                    } else {
                        // Handle if no saved game exists or loading fails
                        game.setScreen(new GameScreen(game));
                    }
                }
                else if(level==2) game.setScreen(new LevelTwo(game));
                else if(level==3) game.setScreen(new LevelThree(game));
            }
        });

        save = new TextButton("SAVE", AngryBirds.getTextButtonStyle());
        save.addListener(AngryBirds.clickListenerChangeScreen);

        exit = new TextButton("EXIT", AngryBirds.getTextButtonStyle());
        exit.addListener(AngryBirds.clickListenerChangeScreen);

        buttonsTable.add(home).width(200).height(50).pad(10);
        buttonsTable.row();
        buttonsTable.add(resume).width(200).height(50).pad(10);
        buttonsTable.row();
        buttonsTable.add(save).width(200).height(50).pad(10);
        buttonsTable.row();
        buttonsTable.add(exit).width(200).height(50).pad(10);


        buttonsTable.left().padLeft(75).padTop(50);
        stage.addActor(buttonsTable);

        pauseLabel = new Label("GAME PAUSED", AngryBirds.getLabelStlye("clear"));
        labelTable.add(pauseLabel);
        labelTable.left().padBottom(340).padLeft(50);
        stage.addActor(labelTable);

        Gdx.input.setInputProcessor(stage);
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

    }

    @Override
    public void dispose() {

    }
}
