package EnhancedMapTiles;

import Engine.GraphicsHandler;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;

import java.awt.image.BufferedImage;


public class PowerUp extends EnhancedMapTile {
    private Point Location;
    private boolean collected = false;


    public PowerUp(BufferedImage image, Point Location) {
        super(image, Location.x, Location.y, TileType.PASSABLE, 3, ImageEffect.NONE);
        this.Location = Location;

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
            } else {
                collected = true;
                //action to happen when collected
            }
        }

    }


    public void draw(GraphicsHandler graphicsHandler) {
        if(collected == false) {
            super.draw(graphicsHandler);
        }
    }

}
