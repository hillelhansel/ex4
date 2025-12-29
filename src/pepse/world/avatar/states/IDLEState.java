package pepse.world.avatar.states;

import danogl.gui.UserInputListener;
import pepse.world.avatar.Animation;
import pepse.world.avatar.Avatar;
import pepse.world.avatar.AvatarState;

public class IDLEState implements AvatarState {

    @Override
    public void onEnter(Avatar avatar) {
        avatar.renderer().setRenderable(avatar.getAnimation(Animation.AnimationType.IDLE));
    }

    @Override
    public void update(UserInputListener input, Avatar avatar) {
        avatar.restoreEnergy(1f);
        avatar.transform().setVelocityX(0);
    }
}

