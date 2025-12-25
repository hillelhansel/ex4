package pepse.world.avatar;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;

import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.utils.GameObjectsTags;
import pepse.world.trees.Fruit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class Avatar extends GameObject {

    private static final Vector2 AVATAR_DIMENSIONS = new Vector2(60, 100);
    private static final float GRAVITY = 800;

    private final Animation animation;

    private final Energy energy;

    private final UserInputListener input;
    private final AvatarState idleState = new IDLEState();
    private final AvatarState runState  = new RunState();
    private final AvatarState jumpState = new JumpState();
    private AvatarState currentState;


    public Avatar(Vector2 topLeftCorner, UserInputListener input, ImageReader imageReader, Consumer<Float> onEnergyChange) {
        super(topLeftCorner.subtract(AVATAR_DIMENSIONS.add(new Vector2(0, 40))),
                AVATAR_DIMENSIONS,
                imageReader.readImage("assets/avatar/idle_0.png", true));

        this.input = input;
        this.energy = new Energy(onEnergyChange);
        this.currentState = idleState;
        this.animation = new Animation(imageReader);

        transform().setAccelerationY(GRAVITY);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        setTag(GameObjectsTags.AVATAR.toString());
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals(GameObjectsTags.GROUND.toString()) && collision.getNormal().y() < 0) {
            transform().setVelocityY(0);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        AvatarState desiredState = decideState();
        changeState(desiredState);

        currentState.update(input, this);
    }

    private AvatarState decideState() {
        boolean hasHorizontalInput = hasHorizontalInput();
        boolean onGround = isOnGround();

        if (!onGround) {
            if (input.isKeyPressed(KeyEvent.VK_SPACE)
                    && hasEnergy(JumpState.DOUBLE_JUMP_ENERGY_COST)) {
                return jumpState;
            }

            if (hasHorizontalInput) {
                return runState;
            }

            return jumpState;
        }

        if (input.isKeyPressed(KeyEvent.VK_SPACE) && hasEnergy(JumpState.ONE_JUMP_ENERGY_COST)) {
            return jumpState;
        }

        if (hasHorizontalInput && hasEnergy(RunState.RUN_ENERGY_COST)) {
            return runState;
        }

        return idleState;
    }

    public Renderable getAnimation(String animationName) {
        return animation.getAnimation(animationName);
    }

    private void changeState(AvatarState newState) {
        if (currentState == newState){
            return;
        }
        currentState = newState;
        currentState.onEnter(this);
    }

    public boolean isFalling() {
        return getVelocity().y() > 0;
    }

    private boolean hasHorizontalInput() {
        return input.isKeyPressed(KeyEvent.VK_LEFT) ||
                input.isKeyPressed(KeyEvent.VK_RIGHT);
    }

    public boolean isOnGround() {
        return Math.abs(getVelocity().y()) < 1e-3;
    }

    public boolean hasEnergy(float amount) {
        return energy.getEnergy() >= amount;
    }

    public void consumeEnergy(float amount) {
        energy.consumeEnergy(amount);
    }

    public void restoreEnergy(float amount) {
        energy.addEnergy(amount);
    }
}
