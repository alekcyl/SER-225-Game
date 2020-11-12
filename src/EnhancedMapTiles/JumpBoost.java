package EnhancedMapTiles;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Level.Player;
import Utils.Point;

import java.awt.image.BufferedImage;

public class JumpBoost extends PowerUp{
    private boolean collected = false;

    public JumpBoost(Point Location) {
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
                this.jump(player);

            }
        }

    }

    public void jump(Player player){
        float cJump = player.getJumpDegrade();
        player.setJumpDegrade(cJump - .3f);
        //Timer to set back jump after set amount of time
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        player.setJumpDegrade(cJump); //Jump height is changed back here
                    }
                },
                7000 //set time here
        );
    }

    public void draw(GraphicsHandler graphicsHandler) {
        if(collected == false) {
            super.draw(graphicsHandler);
        }
    }


}
