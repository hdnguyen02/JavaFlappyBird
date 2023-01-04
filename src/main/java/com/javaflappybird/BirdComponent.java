package com.javaflappybird;

import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.entity.component.Component;
import java.util.Objects;
import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author hdnguyen7702
 * */

public class BirdComponent extends Component {
     private static final double GRAVITY = 0.06;
     private static final double SPEED = 2.5;
     private double movement = 0;
     private final Sound jumpSound = getAssetLoader().loadSound(Objects.requireNonNull(getClass().getResource("sound/jump.wav")));

     @Override
     public void onUpdate(double tpf) {
          this.movement += BirdComponent.GRAVITY;
          double newY = entity.getY() + this.movement;
          entity.setY(newY);
     }

     public void jump(){
          this.movement = -BirdComponent.SPEED;
          getAudioPlayer().playSound(jumpSound);
     }
}
