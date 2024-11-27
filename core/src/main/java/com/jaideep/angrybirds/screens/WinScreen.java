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

import java.lang.reflect.GenericDeclaration;


public class WinScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private AngryBirds game;
    private Texture background;
    private Label winlabel;
    private Label scoreLabel;
    private TextButton nextButton;
    private TextButton homeButton;
    private TextButton exitButton;
    private Table labelTable;
    private Table buttonsTable;
    private int finalScore;
    private int prvLevel;

    public WinScreen(AngryBirds game, int finalScore, int prvLevel){
        this.game = game;
        this.finalScore = finalScore;
        this.prvLevel = prvLevel;
    }

    @Override
    public void show() {
        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        stage = new Stage(viewport);

        background = new Texture("winscreen.png");
        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(background)));
        backgroundImage.setFillParent(true);
        backgroundImage.setPosition(0,0);
        stage.addActor(backgroundImage);

        winlabel = new Label("YOU WIN!!!", AngryBirds.getLabelStlye("clear"));
        labelTable = new Table();
        labelTable.add(winlabel).top().center();
        labelTable.setFillParent(true);
        scoreLabel = new Label(String.format("SCORE : %06d", finalScore), AngryBirds.getLabelStlye("clear"));
        labelTable.row();
        labelTable.add(scoreLabel).top().center().padBottom(300);

        stage.addActor(labelTable);

        buttonsTable = new Table();
        buttonsTable.setFillParent(true);

        nextButton = new TextButton("NEXT", AngryBirds.getTextButtonStyle());
        buttonsTable.add(nextButton).center().width(200).height(50).padTop(100);
        nextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(prvLevel==1) game.setScreen(new LevelTwo(game));
                else if(prvLevel==2) game.setScreen(new LevelThree(game));
                else if(prvLevel==3) game.setScreen(new GameScreen(game));
            }
        });
        buttonsTable.row();

        homeButton = new TextButton("HOME", AngryBirds.getTextButtonStyle());
        buttonsTable.add(homeButton).width(200).height(50).padTop(10);
        buttonsTable.row();
        homeButton.addListener(AngryBirds.clickListenerChangeScreen);

        exitButton = new TextButton("EXIT", AngryBirds.getTextButtonStyle());
        buttonsTable.add(exitButton).width(200).height(50).padTop(10);
        exitButton.addListener(AngryBirds.clickListenerChangeScreen);

        stage.addActor(buttonsTable);

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
