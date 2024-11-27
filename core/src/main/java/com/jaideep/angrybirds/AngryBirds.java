package com.jaideep.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jaideep.angrybirds.screens.*;

import java.util.HashMap;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AngryBirds extends Game {
    public static final float V_WIDTH = 1200;
    public static final float V_HEIGHT = 480;
    public static Screen playScreen;
    private Screen gameScreen;
    public static Assets assets;
    public transient SpriteBatch batch;
    public Music music;
    public static Sound click;
    public static Skin skin;
    public static HashMap<String, Screen> screens = new HashMap<>();
    public static ClickListener clickListenerChangeScreen;
    public static TextButton.TextButtonStyle textButtonStyle; //created for the method getTextButtonStyle
    public static Label.LabelStyle textLabelStyle; //created for the method getLabelStlye

    @Override
    public void create() {
        assets = new Assets();
        assets.loadAll();
        assets.getAssetManager().finishLoading();
        skin = assets.getAssetManager().get(Assets.SKIN);
        batch = new SpriteBatch();
        
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/song.mp3"));
        music.setLooping(true);
        music.setVolume(1f);
        music.play();

        click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
        
        AngryBirds game = this;
        
        clickListenerChangeScreen = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TextButton textButton = (TextButton) event.getListenerActor();
                click.play();
                switch (textButton.getText().toString()){
                    case "PLAY": //PlayScreen
                        setScreen(new LevelScreen(game));
                        break;
                    case "LOAD":
                        setScreen(new LoadScreen(game));
                        break;
                    case "EXIT": //PlayScreen
                        Gdx.app.exit();
                        break;

                        //Load button on the playScreen to be added here later ---------------------------------------->

                    case "LEVEL 2\nMEDIUM"://All the levels present on the LevelScreen
                        screens.put("level2", new LevelTwo(game));
                        setScreen(screens.get("level2"));
                        music.stop();
                        break;
                    case "LEVEL 3\nHARD":
                        screens.put("level3", new LevelThree(game));
                        setScreen(screens.get("level3"));
                        music.stop();
                        break;
                    case "LEVEL 1\nEASY":
                        screens.put("level1", new GameScreen(game));
                        setScreen(screens.get("level1"));
                        music.stop();
                        break;
                    case "BACK": //Back button to come from loadScreen to home page
                    case "<=": //Back button to come back to home page form levelScreen
                        setScreen(new PlayScreen(game));
                        break;

//                    case "||": //game page to pause page
//                        setScreen(new Pause(game));
//                        music.play();
//                        break;
                    case "HOME": //from pause page to home page
                        setScreen(new PlayScreen(game));
                        music.play();
                        break;
//                    case "RESUME": //from pause page back to the game page
//                        setScreen(new GameScreen(game));
//                        music.stop();
//                        break;

                    case "NEXT":
                        setScreen(new GameScreen(game));
                        music.stop();
                        break;

                }
            }
        };

        textButtonStyle = new TextButton.TextButtonStyle(); //added for the method getTextButtonStyle
        textLabelStyle = new Label.LabelStyle();
        
        playScreen = new PlayScreen(this);
        screens.put("playScreen", playScreen);
        setScreen(screens.get("playScreen"));
    }

    @Override
    public void render() {
        super.render();
    }

    public static TextButton.TextButtonStyle getTextButtonStyle(){ // ONLY FOR THE BACK BUTTON
        textButtonStyle.up = skin.newDrawable("default-round", Color.valueOf("75fa64"));
        textButtonStyle.down = skin.newDrawable("default-round", Color.valueOf("619e39"));
        textButtonStyle.over = skin.newDrawable("default-round", Color.valueOf("619e39"));
        textButtonStyle.font = skin.getFont("default-font");
        return textButtonStyle;
    }

    public static Label.LabelStyle getLabelStlye(String hexCodeofColor){
        Drawable textBackground;
        if(hexCodeofColor.equals("clear")){
            textBackground = skin.newDrawable("default-round", Color.CLEAR);
        }
        else {
            textBackground = skin.newDrawable("default-round", Color.valueOf(hexCodeofColor));
        }
        textBackground.setLeftWidth(20f);
        textBackground.setRightWidth(20f);
        textBackground.setTopHeight(5f);
        textBackground.setBottomHeight(5f);

        textLabelStyle.background = textBackground;
        textLabelStyle.font = skin.getFont("white-font");
        return textLabelStyle;
    }



    @Override
    public void dispose() {
        batch.dispose();
    }
}
