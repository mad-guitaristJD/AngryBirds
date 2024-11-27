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

public class LoadScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private AngryBirds game;
    private TextButton load1;
    private TextButton load2;
    private TextButton load3;
    private TextButton back;
    private Table buttonsTable;
    private Texture background;
    private Label loadLabel;
    private Table loadTable;

    public LoadScreen(AngryBirds game){
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        stage = new Stage(viewport);
        buttonsTable = new Table();
        buttonsTable.setFillParent(true);
        loadTable = new Table();
        loadTable.setFillParent(true);

        background = new Texture("pausebg.jpg");
        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(background)));
        backgroundImage.setPosition(-400, -225);
        stage.addActor(backgroundImage);

        loadLabel = new Label("LOAD GAME", AngryBirds.getLabelStlye("clear"));
        loadTable.add(loadLabel);

        loadTable.top().right().padRight(160).padTop(20);
        stage.addActor(loadTable);

        load1 = new TextButton("LOAD : 1", AngryBirds.getTextButtonStyle());
        load1.addListener(AngryBirds.clickListenerChangeScreen);

        load2 = new TextButton("LOAD : 2", AngryBirds.getTextButtonStyle());
        load2.addListener(AngryBirds.clickListenerChangeScreen);

        load3 = new TextButton("LOAD : 3", AngryBirds.getTextButtonStyle());
        load3.addListener(AngryBirds.clickListenerChangeScreen);

        back = new TextButton("BACK", AngryBirds.getTextButtonStyle());
        back.addListener(AngryBirds.clickListenerChangeScreen);

        buttonsTable.add(load1).width(300).height(50).pad(20);
        buttonsTable.row();
        buttonsTable.add(load2).width(300).height(50).pad(20);
        buttonsTable.row();
        buttonsTable.add(load3).width(300).height(50).pad(20);
        buttonsTable.row();
        buttonsTable.add(back).width(300).height(50).pad(20);

        buttonsTable.top().right().padTop(75).padRight(100);
        stage.addActor(buttonsTable);


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
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
