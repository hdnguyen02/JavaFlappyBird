package com.javaflappybird;

import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import java.util.ArrayList;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author hdnguyen7702
 */

public class Pipe {
    private final ArrayList<Entity[]> pipes = new ArrayList<>();
    private final boolean [] tickAddScore = new boolean[3];
    private static final int distance = 220;
    private static final int blank = 160;
    private static final int lower = 400;
    private static final int upper = 550;
    private static final int step = 50;
    private static final int width = 60;
    private static final int height = 360;
    private static final int speed = 1;
    private int score = 0;
    private final Sound addScoreSound = getAssetLoader().loadSound(Objects.requireNonNull(getClass().getResource("sound/congdiem.wav")));


    private int randomY() {
        int rand = (int)(Math.random() * (upper-lower+1));
        return rand - rand % step + lower;
    }

    private Entity [] couplePipe(double x){
        int yBottom = randomY();
        int yTop = yBottom - height - blank;

        Entity [] pipes = new Entity[2];
        pipes[0] = spawn("pipeBottom",x,yBottom);
        pipes[1] = spawn("pipeTop",x,yTop);
        pipes[0].getViewComponent().setZIndex(5);
        pipes[1].getViewComponent().setZIndex(5);
        return pipes;
    }

    public Pipe() {
        initPipeStartGame();
        initTickAddScore();
    }
    public void initPipeStartGame(){
        for (int i = 0; i < 3;i++){
            pipes.add(couplePipe(getAppWidth() + i * distance));
        }
    }

    public void initTickAddScore(){
        for (int i = 0; i < 3;i++){
            tickAddScore[i] = false;
        }
    }

    private void moveAndCheckScore(){
        for (int i = 0; i < 3;i++){
            pipes.get(i)[0].translateX(-speed);
            pipes.get(i)[1].translateX(-speed);

            if (pipes.get(i)[0].getRightX() < 172.8125 && !tickAddScore[i]) {
                this.tickAddScore[i] = true;
                score++;
                FXGL.getAudioPlayer().playSound(addScoreSound);
            }
        }
    }

    public int getScore() {
        return this.score;
    }

    private boolean isPipeOneOutScreen(){
        double xPipeOne = pipes.get(0)[0].getX();
        return xPipeOne < - width;
    }

    public void onUpdate(GameWorld gw){
        moveAndCheckScore();
        if (isPipeOneOutScreen()){
            Entity [] couplePipe =  pipes.remove(0);
            gw.removeEntities(couplePipe[0],couplePipe[1]);
            double xPipeTwo = pipes.get(1)[0].getX();
            pipes.add(couplePipe(xPipeTwo + distance));
            initTickAddScore();
        }
    }
}




