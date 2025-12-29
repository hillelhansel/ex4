package pepse.world.avatar;

import danogl.gui.UserInputListener;

/**
 * Defines the behavior for the avatar's different states (e.g., Idle, Run, Jump).
 */
public interface AvatarState {

    /**
     * Called when the avatar transitions into this state.
     * @param avatar The avatar instance entering this state.
     */
    void onEnter(Avatar avatar);

    /**
     * Updates the avatar's logic for the current frame while in this state.
     * @param input  The input listener for reading user actions.
     * @param avatar The avatar instance to update.
     */
    void update(UserInputListener input, Avatar avatar);
}