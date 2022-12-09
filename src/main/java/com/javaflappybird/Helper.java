package com.javaflappybird;

import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


public class Helper {

    public static Font getFont(String path,int size) {
        return Font.loadFont(String.valueOf(App.class.getResource(path)), size);
    }

    public static Sound getSound(String path) {
        return FXGL.getAssetLoader().loadSound(Objects.requireNonNull(App.class.getResource(path)));
    }


    public static String getTopScore(){
        String topScore = null;
        try {
            FileReader fr = new FileReader("top.txt");
            BufferedReader br = new BufferedReader(fr);
            topScore = br.readLine();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topScore;
    }

    public static void setTopScore(int score) {
        try {
            PrintWriter pw = new PrintWriter("top.txt", StandardCharsets.UTF_8);
            pw.print(score);
            pw.flush();
            pw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class HelperImage {
        private final ImageView view;
        private final  Image image;
        private final double scale;


        public HelperImage(String path,double scale){
           image = new Image(String.valueOf(getClass().getResource(path)));
           this.scale = scale;
           view = new ImageView(image);
           scaleView(this.scale);
        }

        public HelperImage setXY(double x,double y){
            view.setX(x);
            view.setY(y);
            return this;
        }


        public double getWidth() {
            return image.getWidth() / scale;
        }

        public double getHeight() {
            return image.getHeight() / scale;
        }

        public void setCenterX(double originX,double y) {
            double centerX = originX / 2 - getWidth() / 2;
            setXY(centerX,y);
        }

        public void changeView(String path) {
            var newImage =new Image(String.valueOf(getClass().getResource(path)));
            this.view.setImage(newImage);
        }

        public void scaleView(double scale){
            double width = image.getWidth();
            double height = image.getHeight();
            view.setFitWidth( width / scale );
            view.setFitHeight(height / scale);
        }
        public ImageView getImageView(){
            return this.view ;
        }



    }
}
