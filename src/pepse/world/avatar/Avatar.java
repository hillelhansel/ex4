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
     * @param animationName The type of animation required (e.g., RUN, JUMP).
     * @return The Renderable object for the requested animation.
     */
    @Override
    public Renderable getAnimation(Animation.AnimationType animationName) {
        return animation.getAnimation(animationName);
    }

    /**
     * Checks if the avatar is currently falling (moving downwards).
     * @return True if the vertical velocity is positive (down), false otherwise.
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
        return Math.abs(getVelocity().y()) < 1e-3;
    }

    /**
     * Checks if the avatar possesses at least the specified amount of energy.
     * @param amount The amount of energy to check for.
     * @return True if current energy is greater than or equal to amount.
     */
    @Override
    public boolean hasEnergy(float amount) {
        return energy.hasEnoughEnergy(amount);
    }

    /**
     * Reduces the avatar's energy level by the specified amount.
     * @param amount The amount of energy to consume.
     */
    @Override
    public void consumeEnergy(float amount) {
        energy.consumeEnergy(amount);
    }

    /**
     * Increases the avatar's energy level by the specified amount.
     * @param amount The amount of energy to restore.
     */
    @Override
    public void restoreEnergy(float amount) {
        energy.addEnergy(amount);
    }

    @Override
    public void setRenderer(Animation.AnimationType animation) {
        renderer().setRenderable(getAnimation(animation));
    }

    @Override
    public void setVelocityY(float velocity) {
        transform().setVelocityY(velocity);
    }

    @Override
    public void setVelocityX(float velocity) {
        transform().setVelocityX(velocity);
    }

    @Override
    public void setFlippedHorizontally(boolean flipped) {
        renderer().setIsFlippedHorizontally(flipped);
    }

    private AvatarState decideState() {
        boolean onGround = isOnGround();
        boolean spaceKeyPressed = input.isKeyPressed(KeyEvent.VK_SPACE);
        boolean rightKeyPressed = input.isKeyPressed(KeyEvent.VK_RIGHT);
        boolean leftKeyPressed = input.isKeyPressed(KeyEvent.VK_LEFT);

        boolean hasHorizontalInput = rightKeyPressed || leftKeyPressed;

        if (!onGround) {
            if (spaceKeyPressed
                    && hasEnergy(Constants.DOUBLE_JUMP_ENERGY_COST)) {
                return jumpState;
            }

            if(leftKeyPressed && rightKeyPressed){
                return jumpState;
            }

            if (hasHorizontalInput) {
                return runState;
            }

            return jumpState;
        }

        if (spaceKeyPressed && hasEnergy(Constants.ONE_JUMP_ENERGY_COST)) {
            return jumpState;
        }

        if(leftKeyPressed && rightKeyPressed){
            return idleState;
        }

        if (hasHorizontalInput && hasEnergy(Constants.RUN_ENERGY_COST)) {
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
}