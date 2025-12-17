package pepse.world.avatar;

import danogl.gui.UserInputListener;

import java.awt.event.KeyEvent;

public class RunState implements AvatarState {

    private static final float SPEED = 400;

    @Override
    public void onEnter(Avatar avatar) {
//        avatar.renderer().setRenderable(
//                AnimationLibrary.run()
//        );
    }

    @Override
    public void update(UserInputListener input, Avatar avatar) {
        float xVel = 0;

        if (input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            xVel += SPEED;
        }
        if (input.isKeyPressed(KeyEvent.VK_LEFT)) {
            xVel -= SPEED;
        }

        avatar.transform().setVelocityX(xVel);

        if (avatar.isOnGround() && xVel != 0 && avatar.hasEnergy(2)) {
            avatar.consumeEnergy(2);
        }
    }

    @Override
    public String getAnimationName(Avatar avatar) {
        return "run";
    }
}
