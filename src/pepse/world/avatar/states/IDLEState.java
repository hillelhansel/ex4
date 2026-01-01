package pepse.world.avatar.states;

import danogl.gui.UserInputListener;
import pepse.utils.Constants;
import pepse.world.avatar.Animation;
import pepse.world.avatar.Avatar;
import pepse.world.avatar.AvatarState;

/**
 * Represents the idle state of the avatar.
 */
public class IDLEState implements AvatarState {

    /**
     * Initializes the idle state by switching the avatar's visual representation
     * to the idle animation.
     * @param avatar The avatar instance transitioning to this state.
     */
    @Override
    public void onEnter(Avatar avatar) {
        avatar.setRenderer(Animation.AnimationType.IDLE);
    }

    /**
     * Updates the avatar's physics and energy logic.
     * Stops horizontal velocity and restores energy according to the idle rate.
     * @param input  The input listener for reading user actions.
     * @param avatar The avatar instance to update.
     */
    @Override
    public void update(UserInputListener input, Avatar avatar) {
        avatar.restoreEnergy(Constants.IDLE_ENERGY_RESTORE);
        avatar.setVelocityX(0);
    }
}