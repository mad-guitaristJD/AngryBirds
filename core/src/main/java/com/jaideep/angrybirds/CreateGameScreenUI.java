package com.jaideep.angrybirds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CreateGameScreenUI {
    private Texture background;
    private Label scoreLabel;
    private int score;
    private Label chancesLabel;
    private int chances;
    private BitmapFont font;
    private Label.LabelStyle labelStyle;
    
    public CreateGameScreenUI(int chances, Viewport viewport){
        this.chances = chances;
        this.score = 0;
        
        font = new BitmapFont();
        
        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;
        
        scoreLabel = new Label("Score: " + score, labelStyle);
        scoreLabel.setPosition(viewport.getWorldWidth() - 150, viewport.getWorldHeight() - 30);
        
        chancesLabel = new Label("Chances: " + chances, labelStyle);
        chancesLabel.setPosition(viewport.getWorldWidth() - 150, viewport.getWorldHeight() - 50);
        
        background = new Texture("background.png");
    }
    
    public Label getScoreLabel(){
        return scoreLabel;
    }
    public Label getChancesLabel(){
        return chancesLabel;
    }
    public Texture getBackground(){
        return background;
    }
    public int getScore(){
        return score;
    }
    public int getChances(){
        return chances;
    }
}
