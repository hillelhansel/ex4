package pepse.world.avatar;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;

import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.utils.Constants;
import pepse.utils.GameObjectsTags;
import pepse.world.avatar.states.IDLEState;
import pepse.world.avatar.states.JumpState;
import pepse.world.avatar.states.RunState;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

/**
 * Represents the player-controlled character in the game.
 */
public class Avatar extends GameObject implements AvatarController {
    private static final Vector2 AVATAR_DIMENSIONS = new Vector2(60, 100);
    private static final float GRAVITY = 800;

    private final Consumer<Boolean> onAvatarMove;

    private final Animation animation;
    private final Energy energy;
    private final UserInputListener input;

    private final AvatarState idleState = new IDLEState();
    private final AvatarState runState  = new RunState();
    private final AvatarState jumpState = new JumpState();
    private AvatarState currentState;

    private float lastPositionX;

    /**
     * Creates a new Avatar instance.
     * Initializes the physics, animations, and input listeners for the character.
     * @param topLeftCorner  The initial starting position of the avatar.
     * @param input          The listener for reading user keyboard input.
     * @param imageReader    Used to load the avatar's animation assets.
     * @param onEnergyChange A callback to update the UI whenever energy levels change.
     * @param onAvatarMove   A callback triggered when the avatar crosses a chunk boundary
     */
    public Avatar(Vector2 topLeftCorner,
                  UserInputListener input,
                  ImageReader imageReader,
                  Consumer<Float> onEnergyChange,
                  Consumer<Boolean> onAvatarMove) {
        super(topLeftCorner.subtract(AVATAR_DIMENSIONS.add(new Vector2(0, 40))),
                AVATAR_DIMENSIONS,
                imageReader.readImage("assets/idle_0.png", true));

        this.input = input;
        this.energy = new Energy(onEnergyChange);
        this.currentState = idleState;
        this.animation = new Animation(imageReader);
        this.onAvatarMove = onAvatarMove;

        transform().setAccelerationY(GRAVITY);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        setTag(GameObjectsTags.AVATAR.toString());
        this.lastPositionX = getCenter().x();
    }

    /**
     * Handles collisions with other game objects.
     * Specifically detects collisions with the ground to stop vertical movement.
     * @param other     The object the avatar collided with.
     * @param collision Information regarding the collision point and normal.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals(GameObjectsTags.GROUND.toString()) && collision.getNormal().y() < 0) {
            transform().setVelocityY(0);
        }
    }

    /**
     * Updates the avatar's logic for the current frame.
     * Manages state transitions, updates the current state's logic, and checks
     * if the avatar has moved enough to trigger world generation.
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        AvatarState desiredState = decideState();
        changeState(desiredState);
        currentState.update(input, this);

        if(getCenter().x() > lastPositionX + Constants.CHUNK_SIZE) {
            onAvatarMove.accept(true);
            lastPositionX += Constants.CHUNK_SIZE;
        }

        if(getCenter().x() < lastPositionX - Constants.CHUNK_SIZE) {
            onAvatarMove.accept(false);
            lastPositionX -= Constants.CHUNK_SIZE;
        }
    }

    /**
     * Retrieves the specific animation renderable for a given state.
     * @param animationName The type of animation required.
     * @return The Renderable object for the requested animation.
     */
    @Override
    public Renderable getAnimation(Animation.AnimationType animationName) {
        return animation.getAnimation(animationName);
    }

    /**
     * Checks if the avatar is currently falling.
     * @return True if the vertical velocity is positive, false otherwise.
     */
    @Override
    public boolean isFalling() {
        return getVelocity().y() > 0;
    }

    /**
     * Checks if the avatar is currently standing on a solid surface.
     * Determined by checking if the vertical velocity is effectively zero.
     * @return True if the avatar is on the ground, false otherwise.
     */
    @Override
    public boolean isOnGround() {
        return Math.abs(getVelocity().y()) < 0;
    }

    /**
     * Attempts to consume a specific amount of energy in an atomic operation.
     * Checks if the avatar has enough energy, and if so, reduces it.
     * @param amount The amount of energy to consume.
     * @return True if the energy was successfully consumed, False if there wasn't enough energy.
     */
    @Override
    public boolean tryConsumeEnergy(float amount) {
        if(energy.hasEnoughEnergy(amount)) {
            energy.consumeEnergy(amount);
            return true;
        }
        return false;
    }

    /**
     * Increases the avatar's energy level by the specified amount.
     * @param amount The amount of energy to restore.
     */
    @Override
    public void restoreEnergy(float amount) {
        energy.addEnergy(amount);
    }

    /**
     * Updates the avatar's current visual representation.
     * @param animation The animation type to set as the active renderer.
     */
    @Override
    public void setRenderer(Animation.AnimationType animation) {
        renderer().setRenderable(getAnimation(animation));
    }

    /**
     * Sets the avatar's velocity on the Y axis.
     * @param velocity The velocity to set.
     */
    @Override
    public void setVelocityY(float velocity) {
        transform().setVelocityY(velocity);
    }

    /**
     * Sets the avatar's velocity on the X axis.
     * @param velocity The velocity to set.
     */
    @Override
    public void setVelocityX(float velocity) {
        transform().setVelocityX(velocity);
    }

    /**
     * Sets whether the avatar's image should be flipped horizontally.
     * @param flipped True to flip the image (usually for facing left),
     * False to render it normally (usually for facing right).
     */
    @Override
    public void setFlippedHorizontally(boolean flipped) {
        renderer().setIsFlippedHorizontally(flipped);
    }

    private AvatarState decideState() {
        boolean spacePressed = input.isKeyPressed(KeyEvent.VK_SPACE);
        boolean leftPressed = input.isKeyPressed(KeyEvent.VK_LEFT);
        boolean rightPressed = input.isKeyPressed(KeyEvent.VK_RIGHT);

        boolean isMovingHorizontally = (leftPressed || rightPressed) && !(leftPressed && rightPressed);

        if (spacePressed) {
            if (isOnGround() && hasEnergy(Constants.ONE_JUMP_ENERGY_COST)) {
                return jumpState;
            }
            if (!isOnGround() && hasEnergy(Constants.DOUBLE_JUMP_ENERGY_COST)) {
                return jumpState;
            }
        }

        if (!isOnGround()) {
            if (isMovingHorizontally) {
                return runState;
            }
            return jumpState;
        }

        if (isMovingHorizontally && hasEnergy(Constants.RUN_ENERGY_COST)) {
            return runState;
        }

        return idleState;
    }

    private void changeState(AvatarState newState) {
        if (currentState == newState){
            return;
        }
        currentState = newState;
        currentState.onEnter(this);
    }

    private boolean hasEnergy(float amount) {
        return energy.hasEnoughEnergy(amount);
    }
}
