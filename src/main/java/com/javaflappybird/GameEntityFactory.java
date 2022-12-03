package com.javaflappybird;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author hdnguyen7702
 */

public class GameEntityFactory implements EntityFactory  {

    @Spawns("bird")
    public Entity newBird(SpawnData data){
        String pathImage = "image/bird.png";
        var birdView = new Helper.HelperImage(pathImage,16);

        return entityBuilder(data)
                .type(EntityType.BIRD)
                .viewWithBBox(birdView.getImageView())
                .with(new BirdComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("floor")
    public Entity newFloor(SpawnData data){
        String pathImage = "image/floor.png";
        Image floorImage = new Image(String.valueOf(getClass().getResource(pathImage)));

        ImageView floorView = new ImageView(floorImage);

        return entityBuilder(data)
                .type(EntityType.FLOOR)
                .viewWithBBox(floorView)
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("pipeTop")
    public Entity newPipeTop(SpawnData data){
        return createPipe(true,data);
    }

    @Spawns("pipeBottom")
    public Entity newPipeBottom(SpawnData data){
        return createPipe(false,data);
    }

    public Entity createPipe(boolean isPipeTop,SpawnData data) {
        String pipePath = "image/pipe.png";
        ImageView pipeView = new ImageView(String.valueOf(getClass().getResource(pipePath)));
        double width = 60;
        double height = 360;
        pipeView.setFitHeight(height);
        pipeView.setFitWidth(width);
        Entity pipeEntity = entityBuilder(data)
                .viewWithBBox(pipeView)
                .with(new CollidableComponent(true))
                .build();

        if (isPipeTop) {
            pipeView.setRotate(180);
            pipeEntity.setType(EntityType.PIPETOP);
        }
        else {
            pipeEntity.setType(EntityType.PIPEBOTTOM);
        }
        return pipeEntity;
    }

    @Spawns("background")
    public Entity newBackground(SpawnData data){
        String imagePath = "image/background.png";
        ImageView backgroundView = new ImageView(String.valueOf(getClass().getResource(imagePath)));
        backgroundView.setFitHeight(700);
        backgroundView.setFitWidth(1260);

        return entityBuilder(data)
                .view(backgroundView)
                .build();
    }
}
