package pepse.world.avatar.states;

import danogl.gui.UserInputListener;
import pepse.utils.Constants;
import pepse.world.avatar.Animation;
import pepse.world.avatar.Avatar;
import pepse.world.avatar.AvatarState;

import java.awt.event.KeyEvent;

/**
 * Manages the jumping state of the avatar.
 */
public class JumpState implements AvatarState {
    private static final float JUMP_VELOCITY = -650;
    private boolean spaceWasPressed;

    /**
     * Transitions the avatar into the jump state.
     * Resets the horizontal velocity to ensure a clean jump start and updates
     * the visual representation to the jump animation.
     * @param avatar The avatar instance entering this state.
     */
    @Override
    public void onEnter(Avatar avatar) {
        avatar.setVelocityX(0);
        avatar.setRenderer(Animation.AnimationType.JUMP);
        spaceWasPressed = false;
    }

    /**
     * Updates the avatar's jumping logic based on user input.
     * Detects space key presses to trigger either a ground jump or a double jump,
     * consuming the appropriate amount of energy if available.
     * @param input  The input listener for detecting key presses.
     * @param avatar The avatar instance to update.
     */
    @Override
    public void update(UserInputListener input, Avatar avatar) {
        boolean spacePressed = input.isKeyPressed(KeyEvent.VK_SPACE);

        if (spacePressed && !spaceWasPressed) {

            if (avatar.isOnGround() && avatar.hasEnergy(Constants.ONE_JUMP_ENERGY_COST)) {
                avatar.consumeEnergy(Constants.ONE_JUMP_ENERGY_COST);
                avatar.setVelocityY(JUMP_VELOCITY);
            }

            else if (avatar.hasEnergy(Constants.DOUBLE_JUMP_ENERGY_COST) && avatar.isFalling()) {
                avatar.consumeEnergy(Constants.DOUBLE_JUMP_ENERGY_COST);
                avatar.setVelocityY(JUMP_VELOCITY);
            }
        }

        spaceWasPressed = spacePressed;
    }
}