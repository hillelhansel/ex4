package pepse.world.avatar;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;

import danogl.util.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Avatar extends GameObject {

    private static final Vector2 AVATAR_DIMENSIONS = new Vector2(60, 100);
    private static final float GRAVITY = 800;

    private final UserInputListener input;
    private final Energy energy;
    private final AvatarState idleState = new IDLEState();
    private final AvatarState runState  = new RunState();
    private final AvatarState jumpState = new JumpState();

    private AvatarState currentState;

    public Avatar(Vector2 topLeftCorner, UserInputListener input, ImageReader imageReader) {
        super(topLeftCorner.subtract(AVATAR_DIMENSIONS.add(new Vector2(0, 40))),
                AVATAR_DIMENSIONS,
                imageReader.readImage("assets/avatar/idle_0.png", true));

        this.input = input;
        this.energy = new Energy();
        this.currentState = idleState;

        transform().setAccelerationY(GRAVITY);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals("ground")) {
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
                    && hasEnergy(JumpState.DOUBLE_JUMP_ENERGY_COST)
                    && isFalling()) {
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

    public boolean hasEnergy(int amount) {
        return energy.getEnergy() >= amount;
    }

    public void consumeEnergy(int amount) {
        energy.consumeEnergy(amount);
    }

    public void restoreEnergy(int amount) {
        energy.addEnergy(amount);
    }
}
