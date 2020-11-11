package EnhancedMapTiles;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Level.Player;
import Utils.Point;

import java.awt.image.BufferedImage;

public class SpeedBoost extends PowerUp{
    private boolean collected = false;


    public SpeedBoost(Point Location) {
        super(ImageLoader.load("Lazer.png"), Location); //replace later with correct sprite

        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void update(Player player) {
        if (intersects(player)) {
            if(collected) {
                //do nothing if already collected
            } else {//action to happen when collected
                collected = true;
                this.speed(player);

            }
        }

    }

    public void speed(Player player){
        float cSpeed = player.getWalkSpeed(); //saves current speed
        player.setWalkSpeed(cSpeed+ 3); //increase speed by set amount

        new java.util.Timer().schedule( //Timer to set back speed after set amount of time
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        player.setWalkSpeed(cSpeed); //speed is changed back here
                    }
                },
                5000 //set time here
        );

    }


    public void draw(GraphicsHandler graphicsHandler) {
        if(collected == false) {
            super.draw(graphicsHandler);
        }
    }

}

