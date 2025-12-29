package pepse.world.avatar.states;

import danogl.gui.UserInputListener;
import pepse.utils.Constants;
import pepse.world.avatar.Animation;
import pepse.world.avatar.Avatar;
import pepse.world.avatar.AvatarState;

import java.awt.event.KeyEvent;

/**
 * Manages the running state of the avatar.
 */
public class RunState implements AvatarState {
    private static final float SPEED = 400;

    /**
     * Transitions the avatar into the running state.
     * Updates the avatar's visual representation to the running animation.
     * @param avatar The avatar instance entering this state.
     */
    @Override
    public void onEnter(Avatar avatar) {
        avatar.renderer().setRenderable(avatar.getAnimation(Animation.AnimationType.RUN));
    }

    /**
     * Updates the avatar's movement and physics based on user input.
     * Sets horizontal velocity according to arrow key presses, flips the image
     * to match direction, and consumes energy if movement occurs on the ground.
     * @param input  The input listener for detecting key presses.
     * @param avatar The avatar instance to update.
     */
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