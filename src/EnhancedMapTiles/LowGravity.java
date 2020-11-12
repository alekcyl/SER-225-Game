package EnhancedMapTiles;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Level.Player;
import Utils.Point;

import java.awt.image.BufferedImage;

public class LowGravity extends PowerUp{
    private boolean collected = false;

    public LowGravity(Point Location) {
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
                this.gravity(player);

            }
        }
    }

    public void gravity(Player player){
        float cGravity = player.getTerminalVelocityY();
        player.setTerminalVelocityY(cGravity - 5f);
        //Timer to set back jump after set amount of time
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        player.setTerminalVelocityY(cGravity);
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
