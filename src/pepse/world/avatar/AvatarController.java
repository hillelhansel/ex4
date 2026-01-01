package pepse.world.avatar;

import danogl.gui.rendering.Renderable;

/**
 * Interface for controlling the Avatar.
 * Exposes only the necessary actions required by the AvatarStates,
 * allowing them to manipulate the Avatar's physics and visuals without direct access to the full class.
 */
public interface AvatarController {

    /**
     * Retrieves the specific animation renderable for a given animation type.
     * @param animationName The type of animation required.
     * @return The Renderable object corresponding to the requested animation.
     */
    Renderable getAnimation(Animation.AnimationType animationName);

    /**
     * Checks if the avatar is currently falling.
     * @return True if the vertical velocity is positive, false otherwise.
     */
    boolean isFalling();

    /**
     * Checks if the avatar is currently standing on a solid surface.
     * Determined by checking if the vertical velocity is effectively zero.
     * @return True if the avatar is on the ground, false otherwise.
     */
    boolean isOnGround();

    /**
     * Attempts to consume a specific amount of energy in an atomic operation.
     * Checks if the avatar has enough energy, and if so, reduces it.
     * @param amount The amount of energy to consume.
     * @return True if the energy was successfully consumed, False if there wasn't enough energy.
     */
    boolean tryConsumeEnergy(float amount);

    /**
     * Increases the avatar's energy level by the specified amount.
     * @param amount The amount of energy to restore.
     */
    void restoreEnergy(float amount);

    /**
     * Updates the avatar's current visual representation.
     * @param animation The animation type to set as the active renderer.
     */
    void setRenderer(Animation.AnimationType animation);

    /**
     * Sets the avatar's velocity on the Y axis.
     * @param velocity The velocity to set.
     */
    void setVelocityY(float velocity);

    /**
     * Sets the avatar's velocity on the X axis.
     * @param velocity The velocity to set.
     */
    void setVelocityX(float velocity);

    /**
     * Sets whether the avatar's image should be flipped horizontally.
     * @param flipped True to flip the image (usually for facing left),
     * False to render it normally (usually for facing right).
     */
    void setFlippedHorizontally(boolean flipped);
}