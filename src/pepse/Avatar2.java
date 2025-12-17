//package pepse.world.avatar;
//
//import danogl.GameObject;
//import danogl.collisions.Collision;
//import danogl.gui.ImageReader;
//import danogl.gui.UserInputListener;
//import danogl.util.Vector2;
//
//import java.awt.event.KeyEvent;
//
//public class Avatar2 extends GameObject {
//    private static final Vector2 AVATAR_DIMENSIONS = new Vector2(60, 100);
//    private static final float GRAVITY = 800;
//    private static final int VELOCITY_X = 400;
//    private static final float VELOCITY_Y = -650;
//
//    private final UserInputListener inputListener;
//    private final Energy energy;
//
//    private boolean isOnGround = true;
//
//    public Avatar(Vector2 topLeftCorner, UserInputListener inputListener, ImageReader imageReader) {
//        super(topLeftCorner.subtract(AVATAR_DIMENSIONS.add(new Vector2(0, 40))), AVATAR_DIMENSIONS, imageReader.readImage("assets/avatar/idle_0.png", true));
//        this.energy = new Energy();
//        this.inputListener = inputListener;
//
//        physics().preventIntersectionsFromDirection(Vector2.ZERO);
//        transform().setAccelerationY(GRAVITY);
//    }
//
//    @Override
//    public void onCollisionEnter(GameObject other, Collision collision) {
//        if (other.getTag().equals("ground")) {
//            transform().setVelocityY(0);
//            isOnGround = true;
//        }
//    }
//
//    @Override
//    public void update(float deltaTime) {
//        super.update(deltaTime);
//
//        float xVel = 0;
//        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
//            xVel -= VELOCITY_X;
//        }
//        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
//            xVel += VELOCITY_X;
//        }
//
//        if (xVel != 0) {
//            if (isOnGround) {
//                if (energy.hasEnoughEnergy(2)) {
//                    energy.consumeEnergy(2);
//                    transform().setVelocityX(xVel);
//                }
//                else {
//                    transform().setVelocityX(0);
//                }
//            }
//            else {
//                transform().setVelocityX(xVel);
//            }
//        }
//        else {
//            transform().setVelocityX(0);
//        }
//
//        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE)){
//            if (isOnGround && energy.hasEnoughEnergy(20)){
//                transform().setVelocityY(VELOCITY_Y);
//                energy.consumeEnergy(20);
//                isOnGround = false;
//            }
//            if (isFalling() && energy.hasEnoughEnergy(50)){
//                transform().setVelocityY(VELOCITY_Y);
//                energy.consumeEnergy(50);}
//        }
//
//        else if (isOnGround && getVelocity().equals(Vector2.ZERO)) {
//            energy.restoreEnergy(1);
//        }
//    }
//
//    private boolean isFalling(){
//        return getVelocity().y() > 0;
//    }
//}
