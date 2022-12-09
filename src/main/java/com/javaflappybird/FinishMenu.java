package com.javaflappybird;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class FinishMenu extends FXGLMenu {
    private final Helper.HelperImage tableView;
    private final Helper.HelperImage replayBtn;
    private final Helper.HelperImage gameOver;
    private final Helper.HelperImage homeBtn;

    private final TextMove textScoreMove = new TextMove("- SCORE -",240,true);
    private final TextMove textTopScoreMove = new TextMove("- TOP 1 -",340,false);
    private TextMove scoreMove;
    private TextMove topScoreMove;

    private double yTable;
    private double yReplayBtn;
    private double yGameOver;
    private double yHomeBtn;
    private boolean tableDone = false;

    public FinishMenu() {
        super(MenuType.GAME_MENU);
        Pane contentRoot = getContentRoot();
        tableView = new Helper.HelperImage("image/tableScore.png",1);
        replayBtn = new Helper.HelperImage("image/btnReplay.png",4);
        gameOver = new Helper.HelperImage("image/gameOver.png",6);
        homeBtn = new Helper.HelperImage("image/btnHome.png",5);


        replayBtn.getImageView().setOnMouseClicked((MouseEvent e) -> fireNewGame());
        homeBtn.getImageView().setOnMouseClicked((MouseEvent e) -> getController().gotoMainMenu());

        contentRoot.getChildren().addAll(tableView.getImageView(), replayBtn.getImageView());
        contentRoot.getChildren().addAll(gameOver.getImageView(),homeBtn.getImageView());
        contentRoot.getChildren().addAll(textScoreMove.getSurface(),textTopScoreMove.getSurface());
    }

    @Override
    protected void onUpdate(double tpf) {
        super.onUpdate(tpf);
        double endYTableScore = 160;
        double endYReplayBtn = 500;
        double endYGameOver = 60;
        double endYHomeBtn = 500;

        if (!tableDone) {
            if (yTable < endYTableScore) {
                tableView.getImageView().setY(yTable);
                yTable+=8;
            }
            else {
                tableDone = true;
            }
        }
        else {
            textScoreMove.move();
            textTopScoreMove.move();
            scoreMove.move();
            topScoreMove.move();
        }
            if (yReplayBtn > endYReplayBtn) {
                replayBtn.getImageView().setY(yReplayBtn);
                yReplayBtn-=6;
            }

            if (yHomeBtn > endYHomeBtn) {
                homeBtn.getImageView().setY(yHomeBtn);
                yHomeBtn-=6;
            }

            if (yGameOver < endYGameOver && tableDone) {
                gameOver.getImageView().setY(yGameOver);
                yGameOver+=8;
            }
    }

    private static class TextMove {
        private final double xCenter; // x sau khi move đến.
        private double xMove;
        private final Text surface;
        private final boolean isLeftRight;

        private TextMove(String text,int y,boolean isLeftRight) {
            surface = new Text();
            surface.setText(text);
            surface.setFont(Helper.getFont("font/fontScore.ttf", 42));
            xCenter = FXGL.getAppWidth() / 2f - surface.getLayoutBounds().getWidth() / 2f;
            this.isLeftRight = isLeftRight;

            if (isLeftRight){
                xMove = - surface.getLayoutBounds().getWidth();
            }
            else{
                xMove = FXGL.getAppWidth() + surface.getLayoutBounds().getWidth();
            }

            surface.setX(xMove);
            surface.setY(y);
        }

        public Text getSurface(){
            return surface;
        }

        public void move() {
            if(isLeftRight){
                moveLeftRight();
            }
            else {
                moveRightLeft();
            }
        }
        private void moveLeftRight(){
            if (xMove < xCenter) {
                surface.setX(xMove);
                xMove+=10;
            }
            else {
                surface.setX(xCenter);
            }
        }

        private void moveRightLeft(){
            if (xMove > xCenter) {
                surface.setX(xMove);
                xMove-=10;
            }
            else {
                surface.setX(xCenter);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tableDone = false;
        scoreMove = new TextMove(String.valueOf(FXGL.getWorldProperties().getInt("score")),300,true);
        topScoreMove = new TextMove(String.valueOf(FXGL.getWorldProperties().getInt("topScore")),400,true);
        getContentRoot().getChildren().addAll(scoreMove.getSurface(),topScoreMove.getSurface());

        yTable = -tableView.getHeight();
        tableView.setCenterX(getAppWidth(),yTable);

        yReplayBtn = getAppHeight() - replayBtn.getHeight();
        replayBtn.setCenterX(getAppWidth() + 120,yReplayBtn);

        yHomeBtn = getAppHeight() - homeBtn.getHeight();
        homeBtn.setCenterX(getAppWidth() - 140,yHomeBtn);

        yGameOver = -gameOver.getHeight();
        gameOver.setCenterX(getAppWidth(),yGameOver);
    }
}
