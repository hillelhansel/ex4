package pepse.world.avatar.states;

import danogl.gui.UserInputListener;
import pepse.utils.Constants;
import pepse.world.avatar.Animation;
import pepse.world.avatar.Avatar;
import pepse.world.avatar.AvatarState;

import java.awt.event.KeyEvent;

public class RunState implements AvatarState {

    private static final float SPEED = 400;

    @Override
    public void onEnter(Avatar avatar) {
        avatar.renderer().setRenderable(avatar.getAnimation(Animation.AnimationType.RUN));
    }

    @Override
    public void update(UserInputListener input, Avatar avatar) {
        float xVel = 0;

        if (input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            xVel += SPEED;
            avatar.renderer().setIsFlippedHorizontally(false);
        }

        if (input.isKeyPressed(KeyEvent.VK_LEFT)) {
            xVel -= SPEED;
            avatar.renderer().setIsFlippedHorizontally(true);
        }

        avatar.transform().setVelocityX(xVel);

        if (avatar.isOnGround() && xVel != 0) {
            avatar.consumeEnergy(Constants.RUN_ENERGY_COST);
        }
    }
}
