package pepse.world.avatar;

import danogl.gui.UserInputListener;

public class IDLEState implements AvatarState {

    @Override
    public void onEnter(Avatar avatar) {
//        avatar.renderer().setRenderable(
//                AnimationLibrary.idle()
//        );
    }

    @Override
    public void update(UserInputListener input, Avatar avatar) {
        avatar.restoreEnergy(1f);
        avatar.transform().setVelocityX(0);
    }

    @Override
    public String getAnimationName(Avatar avatar) {
        return "idle";
    }
}

