package com.javaflappybird;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class FinishMenu extends FXGLMenu {

    private final Helper.HelperImage tableView;
    private final Helper.HelperImage replayBtn;
    private final Helper.HelperImage gameOver;
    private final Helper.HelperImage homeBtn;

    private final Text textScore = new Text();
    private final Text textTopScore = new Text();
    private final Text score = new Text();
    private final Text topScore = new Text();

    private double yTable;
    private double yReplayBtn;
    private double yGameOver;
    private double yHomeBtn;

    private double xTextScore;
    private double xTextTopScore;
    private double xScore;
    private double xTopScore;


    private double xEndTextScore;
    private double xEndScore;
    private double xEndTextTopScore;
    private double xEndTopScore;

    private boolean tableDone = false;

    public FinishMenu() {
        super(MenuType.GAME_MENU);
        Pane contentRoot = getContentRoot();
        tableView = new Helper.HelperImage("image/tableScore.png",1);
        replayBtn = new Helper.HelperImage("image/btnReplay.png",4);
        gameOver = new Helper.HelperImage("image/gameOver.png",6);
        homeBtn = new Helper.HelperImage("image/btnHome.png",5);

        Font fontTopScore = Font.loadFont(String.valueOf(getClass().getResource("font/font-result.ttf")), 40);

        textScore.setFont(fontTopScore);
        textTopScore.setFont(fontTopScore);
        score.setFont(fontTopScore);
        topScore.setFont(fontTopScore);


        // event
        replayBtn.getImageView().setOnMouseClicked((MouseEvent e) -> fireNewGame());

        homeBtn.getImageView().setOnMouseClicked((MouseEvent e) -> getController().gotoMainMenu());



        contentRoot.getChildren().addAll(tableView.getImageView(), replayBtn.getImageView());
        contentRoot.getChildren().addAll(gameOver.getImageView(),homeBtn.getImageView());
        contentRoot.getChildren().addAll(textScore,score,textTopScore,topScore);

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
            if (xTextScore < xEndTextScore) {
                textScore.setX(xTextScore);
                xTextScore+=8;
            }
            else {
                textScore.setX(xEndTextScore);
            }
            if (xScore < xEndScore ) {
                score.setX(xScore);
                xScore+=8;
            }
            else {
                score.setX(xEndScore);
            }

            if (xTextTopScore > xEndTextTopScore) {
                textTopScore.setX(xTextTopScore);
                xTextTopScore-=8;
            }
            else {
                textTopScore.setX(xEndTextTopScore);
            }

            if (xTopScore > xEndTopScore) {
                topScore.setX(xTopScore);
                xTopScore-=8;
            }
            else {
                topScore.setX(xEndTopScore);
            }
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

    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println(FXGL.getWorldProperties().getInt("score"));


        textScore.setText("- SCORE -");
        score.setText(String.valueOf(FXGL.getWorldProperties().getInt("score")));
        textTopScore.setText("< TOP SCORE >");
        topScore.setText(String.valueOf(FXGL.getWorldProperties().getInt("topScore")));


        xEndTextScore = getAppWidth() / 2f - textScore.getLayoutBounds().getWidth() / 2;
        xEndScore = getAppWidth() / 2f - score.getLayoutBounds().getWidth() / 2;
        xEndTextTopScore = getAppWidth() / 2f - textTopScore.getLayoutBounds().getWidth() / 2;
        xEndTopScore = getAppWidth() / 2f - topScore.getLayoutBounds().getWidth() / 2;

        textScore.setY(240);
        score.setY(280);
        textTopScore.setY(320);
        topScore.setY(360);

        xTextScore = -textScore.getLayoutBounds().getWidth();
        xScore = -score.getLayoutBounds().getWidth();
        xTextTopScore = getAppHeight();
        xTopScore = getAppHeight();

        textScore.setX(xScore);
        score.setX(xScore);
        textTopScore.setX(xTextTopScore);
        topScore.setX(xTopScore);


        textScore.setX(-textScore.getLayoutBounds().getWidth());


        tableDone = false;
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
