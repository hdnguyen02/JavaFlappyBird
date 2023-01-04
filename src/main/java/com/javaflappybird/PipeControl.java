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

public class PipeControl {
    private final ArrayList<Entity[]> pipes = new ArrayList<>();
    private final boolean [] tickAddScore = new boolean[3]; // biểu thị cho số cột đã được tính điểm.
    private static final int DISTANCE = 220;
    private static final int MINHEIGHT = 120;
    private static final int BLANK = 160;
    private static final int HEIGHT = 360;
    private static final int LOWER = -PipeControl.HEIGHT + PipeControl.MINHEIGHT;
    private static final int UPPER = 0;
    private static final int STEP = 20;
    private static final int WIDTH = 60;

    // previous
    private int previousY = -1000;

    private static final int SPEED = 1;
    private int score = 0;
    private final Sound addScoreSound = Helper.getSound("sound/congdiem.wav");


    private int randomY() {
        int y;
        do {
            int rand = (int)(Math.random() * (UPPER-LOWER+1));
            y = rand - rand % STEP + LOWER;
        }
        while (y == this.previousY);
        this.previousY = y;
        return y;
    }

    private Entity [] couplePipe(double x){
        int yTop = randomY();
        int yBottom = yTop + BLANK + HEIGHT;

        Entity [] pipes = new Entity[2];
        pipes[0] = spawn("pipeBottom",x,yBottom);
        pipes[1] = spawn("pipeTop",x,yTop);
        pipes[0].getViewComponent().setZIndex(5);
        pipes[1].getViewComponent().setZIndex(5);
        return pipes;
    }

    public PipeControl() {
        initPipeStartGame();
        initTickAddScore();
    }
    public void initPipeStartGame(){
        for (int i = 0; i < 3;i++){
            pipes.add(couplePipe(getAppWidth() + i * DISTANCE));
        }
    }

    public void initTickAddScore(){
        for (int i = 0; i < 3;i++){
            tickAddScore[i] = false;
        }
    }

    private void moveAndCheckScore(boolean isSound){
        for (int i = 0; i < 3;i++){
            pipes.get(i)[0].translateX(-SPEED);
            pipes.get(i)[1].translateX(-SPEED);

            if (pipes.get(i)[0].getRightX() < 172.8125 && !tickAddScore[i]) {
                this.tickAddScore[i] = true;
                score++;
                if (isSound) {
                    FXGL.getAudioPlayer().playSound(addScoreSound);
                }

            }
        }
    }

    public int getScore() {
        return this.score;
    }

    private boolean isPipeOneOutScreen(){
        double xPipeOne = pipes.get(0)[0].getX();
        return xPipeOne < - WIDTH;
    }

    public void onUpdate(GameWorld gw,boolean isSound){
        moveAndCheckScore(isSound);
        if (isPipeOneOutScreen()){
            Entity [] couplePipe =  pipes.remove(0);
            gw.removeEntities(couplePipe[0],couplePipe[1]);
            double xPipeTwo = pipes.get(1)[0].getX();
            pipes.add(couplePipe(xPipeTwo + DISTANCE));
            initTickAddScore();
        }
    }
}




