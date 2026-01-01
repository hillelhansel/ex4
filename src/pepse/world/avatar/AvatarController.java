package pepse.world.avatar;

import danogl.gui.rendering.Renderable;

public interface AvatarController {
    Renderable getAnimation(Animation.AnimationType animationName);

    /**
     * Checks if the avatar is currently falling (moving downwards).
     * @return True if the vertical velocity is positive (down), false otherwise.
     */
    boolean isFalling();

    /**
     * Checks if the avatar is currently standing on a solid surface.
     * Determined by checking if the vertical velocity is effectively zero.
     * @return True if the avatar is on the ground, false otherwise.
     */
    boolean isOnGround();

    /**
     * Checks if the avatar possesses at least the specified amount of energy.
     * @param amount The amount of energy to check for.
     * @return True if current energy is greater than or equal to amount.
     */
    boolean hasEnergy(float amount);

    /**
     * Reduces the avatar's energy level by the specified amount.
     * @param amount The amount of energy to consume.
     */
    void consumeEnergy(float amount);

    /**
     * Increases the avatar's energy level by the specified amount.
     * @param amount The amount of energy to restore.
     */
    void restoreEnergy(float amount);

    void setRenderer(Animation.AnimationType animation);

    void setVelocityY(float velocity);

    void setVelocityX(float velocity);

    void setFlippedHorizontally(boolean flipped);
}
