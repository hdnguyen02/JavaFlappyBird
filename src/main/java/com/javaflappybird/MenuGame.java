package com.javaflappybird;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.Objects;

/**
 * @author hdnguyen7702
 * */

public class MenuGame extends FXGLMenu {
    private boolean isOnMusic = true;
    private final Text textTopScore = new Text();
    public MenuGame()  {
        super(MenuType.MAIN_MENU);
        var children = getContentRoot().getChildren();
        textTopScore.setFont(Helper.getFont("font/fontScore.ttf", 48));
        textTopScore.setFill(Color.DARKBLUE);

        var backgroundView = new ImageView(String.valueOf(getClass().getResource("image/background.png")));
        backgroundView.setFitWidth(1260);
        backgroundView.setFitHeight(700);

        var buttonStart = new Helper.HelperImage("image/buttonStart.png",5);
        buttonStart.setCenterX(getAppWidth(),530);

        var soundButton = new Helper.HelperImage("image/musicOn.png",6).setXY(30,6);

        var soundBase = FXGL.getAssetLoader().loadMusic(Objects.requireNonNull(getClass().getResource("sound/base.mp3")));
        FXGL.getAudioPlayer().loopMusic(soundBase);

        var titleView = new Helper.HelperImage("image/title.png",10);

        titleView.setCenterX(getAppWidth(),180);

        soundButton.getImageView().setOnMouseClicked((MouseEvent e) -> {
            if (this.isOnMusic) {
                soundButton.changeView("image/musicOff.png");
                FXGL.getAudioPlayer().pauseAllMusic();
            }
            else {
                soundButton.changeView("image/musicOn.png");
                FXGL.getAudioPlayer().resumeAllMusic();
            }
            this.isOnMusic = !this.isOnMusic;
        });

        buttonStart.getImageView().setOnMouseClicked((MouseEvent e) -> this.fireNewGame());

        children.addAll(backgroundView, buttonStart.getImageView());
        children.addAll(soundButton.getImageView(),titleView.getImageView());
        children.addAll(textTopScore);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String topScore = Helper.getTopScore();
        textTopScore.setText("top score : " + topScore);
        textTopScore.setX( FXGL.getAppWidth() / 2f - textTopScore.getLayoutBounds().getWidth() / 2);
        textTopScore.setY(420);
    }
}