package pepse.world.avatar;

import danogl.GameObject;
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

    public Avatar(Vector2 topLeftCorner,
                  UserInputListener input,
                  ImageReader imageReader) {

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

            if (input.isKeyPressed(KeyEvent.VK_SPACE) && canEnterJump()) {
                return jumpState;
            }

            if (hasHorizontalInput) {
                return runState;
            }

            return jumpState;
        }

        if (input.isKeyPressed(KeyEvent.VK_SPACE) && canEnterJump()) {
            return jumpState;
        }

        if (hasHorizontalInput && canEnterRun()) {
            return runState;
        }

        return idleState;
    }


    private void changeState(AvatarState newState) {
        if (currentState == newState) return;
        currentState = newState;
        currentState.onEnter(this);
    }

    private boolean canEnterRun() {
        return energy.getEnergy() >= 2;
    }

    private boolean canEnterJump() {
        if (isOnGround()) {
            return energy.getEnergy() >= 20;
        }
        return energy.getEnergy() >= 50;
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
        energy.reduceEnergy(amount);
    }

    public void restoreEnergy(int amount) {
        energy.addEnergy(amount);
    }
}
