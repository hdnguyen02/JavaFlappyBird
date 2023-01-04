package com.javaflappybird;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;
import java.util.*;
import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author hdnguyen7702
 * */


public class App extends GameApplication {
    private Entity bird;
    private Floor controlFloor;
    private Pipe controlPipe;
    private Sound dieSound;
    private Sound impactSound;
    private Text uiScore;
    private boolean isPlay = true;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(500);
        settings.setHeight(700);
        settings.setTitle("Java Flappy Bird");
        settings.setVersion("1.0");
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new MenuGame();
            }

            @NotNull
            @Override
            public FXGLMenu newGameMenu() {
                return new FinishMenu();
            }
        });
    }


    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("isPlayMusic",true);
    }
    @Override
    public void onUpdate(double tpf) {
        if (isPlay) {
            controlFloor.onUpdate();
            controlPipe.onUpdate(getGameWorld());
            getWorldProperties().setValue("score",controlPipe.getScore());
        }

        else {
            if (bird.getY() > getAppHeight()) {
                getGameController().gotoGameMenu();
            }
        }
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameEntityFactory());
        spawn("background",0,0);
        bird = spawn("bird", 100, 0);
        controlFloor = new Floor();
        controlPipe = new Pipe();
        dieSound = Helper.getSound("sound/die.wav");
        impactSound = Helper.getSound("sound/collide.wav");
        isPlay = true;
    }

    @Override
    protected void initInput() {
        onKeyDown(KeyCode.SPACE, () -> {
            if (isPlay) {
                bird.getComponent(BirdComponent.class).jump();
            }
        });
        onBtnDown(MouseButton.PRIMARY,() -> {
            if (isPlay) {
                bird.getComponent(BirdComponent.class).jump();
            }
        });
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BIRD, EntityType.FLOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                finishGame();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BIRD, EntityType.PIPETOP) {
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                finishGame();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BIRD, EntityType.PIPEBOTTOM) {
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                finishGame();
            }
        });
    }

    private void finishGame(){
        if (isPlay){
            FXGL.getAudioPlayer().playSound(impactSound);
            FXGL.getAudioPlayer().playSound(dieSound);
            getGameScene().removeUINode(uiScore);
            rangeTop();
        }
        this.isPlay = false;

    }

    private void rangeTop(){
        int topScore = Integer.parseInt(Helper.getTopScore());
        int score = getWorldProperties().getInt("score");
        if (score > topScore) {
            topScore = score;
            Helper.setTopScore(topScore);
        }
        getWorldProperties().setValue("topScore",topScore);
    }

    @Override
    protected void initUI() {
        uiScore = new Text();
        uiScore.setTranslateX(50);
        uiScore.setTranslateY(50);

        Font fontScore = Font.loadFont(String.valueOf(getClass().getResource("font/fontScore.ttf")),48);
        uiScore.setFont(fontScore);
        uiScore.textProperty().bind(getWorldProperties().intProperty("score").asString());
        getGameScene().addUINode(uiScore); // add to the scene graph
    }

    public static void main(String[] args) {
        launch(args);
    }
}