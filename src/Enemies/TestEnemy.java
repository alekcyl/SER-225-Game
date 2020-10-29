package Enemies;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

import java.util.HashMap;




public class TestEnemy extends Enemy {

    private float gravity = .5f;
    private float movementSpeed = 2f;
    private Direction startFacingDirection;
    private Direction facingDirection;
    private AirGroundState airGroundState;
    private float startX;
    private float startY;

    protected Stopwatch shootTimer = new Stopwatch();


    public TestEnemy(Point location, Direction facingDirection) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("BugEnemy.png"), 24, 15), "WALK_LEFT");
        this.startFacingDirection = facingDirection;
        this.startX = location.x;
        this.startY = location.y;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "WALK_LEFT";
        }
        airGroundState = AirGroundState.GROUND;

        // every 2 seconds, the fireball will be shot out
        shootTimer.setWaitTime(2000);
    }

    @Override
    public void update(Player player) {
        float moveAmountX = 0;
        float moveAmountY = 0;

            if (facingDirection == Direction.RIGHT) {
                moveAmountX += movementSpeed;
            } else {
                moveAmountX -= movementSpeed;
            }

        // move bug
        moveYHandleCollision(moveAmountY);
        moveXHandleCollision(moveAmountX);

        if(this.x > startX + 200 || this.x < startX - 200){
            if (this.facingDirection == Direction.RIGHT) {
                facingDirection = Direction.LEFT;
                currentAnimationName = "WALK_LEFT";
            } else {
                facingDirection = Direction.RIGHT;
                currentAnimationName = "WALK_RIGHT";
            }
        }

        super.update(player);

        //controls shooting of projectile
        if(shootTimer.isTimeUp()){
            int projectileY;
            int projectileX;
            float movementSpeed;

            //sets spawn location and speed of projectile
            projectileY = Math.round(getY());
            projectileX = Math.round(getX());
            movementSpeed = 2;

            FlyingProjectile projectile = new FlyingProjectile(new Point(projectileX, projectileY), movementSpeed, 3000);

            map.addEnemy(projectile);
            //delay between shots
            shootTimer.setWaitTime(2000);

        }
    }

    public void turnAround(){

    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        // if bug has collided into something while walking forward,
        // it turns around (changes facing direction)
        if (hasCollided) {
            if (direction == Direction.RIGHT) {
                facingDirection = Direction.LEFT;
                currentAnimationName = "WALK_LEFT";
            } else {
                facingDirection = Direction.RIGHT;
                currentAnimationName = "WALK_RIGHT";
            }
        }
    }

//    @Override
//    public void onEndCollisionCheckY(boolean hasCollided, Direction direction) {
//        // if bug is colliding with the ground, change its air ground state to GROUND
//        // if it is not colliding with the ground, it means that it's currently in the air, so its air ground state is changed to AIR
//        if (direction == Direction.DOWN) {
//            if (hasCollided) {
//                airGroundState = AirGroundState.GROUND;
//            } else {
//                airGroundState = AirGroundState.AIR;
//            }
//        }
//    }

    @Override
    public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 100)
                            .withScale(2)
                            .withBounds(6, 6, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 100)
                            .withScale(2)
                            .withBounds(6, 6, 12, 7)
                            .build()
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 100)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 6, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 100)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 6, 12, 7)
                            .build()
            });
        }};
    }
}
