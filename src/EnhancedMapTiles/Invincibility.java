package EnhancedMapTiles;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Level.Player;
import Utils.Point;

import java.awt.image.BufferedImage;

public class Invincibility extends PowerUp{
    private boolean collected = false;

    public Invincibility(Point Location) {
        super(ImageLoader.load("Lazer.png"), Location);

        this.initialize();
    }

    @Override
    public void update(Player player) {
        if (intersects(player)) {
            if(collected) {
                //do nothing if already collected
            } else {
                collected = true;
                this.invincible(player);

            }
        }
    }


    public void invincible(Player player){
        player.setInvincible(true);
        //Timer to set back jump after set amount of time
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        player.setInvincible(false);
                    }
                },
                10000 //set time here
        );
    }




    public void draw(GraphicsHandler graphicsHandler) {
        if(collected == false) {
            super.draw(graphicsHandler);
        }
    }
}
