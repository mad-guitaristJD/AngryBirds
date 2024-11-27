package com.jaideep.angrybirds.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaideep.angrybirds.AngryBirds;
import com.jaideep.angrybirds.Assets;

public class PlayScreen implements Screen {
    public static Screen levelScreen;
    public static Screen loadScreen;
    private AngryBirds game;
    private Viewport viewport;
    private Stage stage;
    private AssetManager assetManager ;
    private Table mainTable;
    private Skin skin;
    private Texture firstScreen;


    public PlayScreen (AngryBirds game){
        
        this.game = game;
        assetManager = AngryBirds.assets.getAssetManager();
        skin = assetManager.get(Assets.SKIN);
        levelScreen = new LevelScreen(game);
        AngryBirds.screens.put("levelScreen", levelScreen);
        loadScreen = new LoadScreen(game);
        AngryBirds.screens.put("loadScreen", loadScreen);
    }

    @Override
    public void show() {
        viewport = new FitViewport(AngryBirds.V_WIDTH, AngryBirds.V_HEIGHT);
        stage = new Stage(viewport);

        mainTable = new Table();
        mainTable.setFillParent(true);
        firstScreen = new Texture("firstScreen.jpg");
        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(firstScreen)));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        stage.addActor(mainTable);
        addButton("PLAY").addListener(AngryBirds.clickListenerChangeScreen);
        addButton("LOAD").addListener(AngryBirds.clickListenerChangeScreen);
        addButton("EXIT").addListener(AngryBirds.clickListenerChangeScreen);

        Gdx.input.setInputProcessor(stage);
    }

    private TextButton addButton(String text) {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("default-round", Color.valueOf("75fa64"));
        textButtonStyle.down = skin.newDrawable("default-round", Color.valueOf("619e39"));
        textButtonStyle.over = skin.newDrawable("default-round", Color.valueOf("619e39"));
        textButtonStyle.font = skin.getFont("white-font");
        TextButton textButton = new TextButton(text, textButtonStyle);
        mainTable.add(textButton).width(200).height(50).padBottom(20);
        mainTable.row();

        return textButton;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
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
//        levelScreen.dispose();
//        firstScreen.dispose();
    }
}
