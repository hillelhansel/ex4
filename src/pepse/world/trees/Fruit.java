package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.utils.GameObjectsTags;
import pepse.world.Block;
import pepse.world.avatar.Avatar;

import java.awt.*;

public class Fruit extends GameObject {
    private final static int FRUIT_RADIUS = Block.SIZE;
    private static final OvalRenderable renderable = new OvalRenderable(Color.RED);
    private boolean isEaten = false;

    public Fruit(Vector2 topLeftCorner) {
        super(topLeftCorner, Vector2.ONES.mult(FRUIT_RADIUS), renderable);
        setTag(GameObjectsTags.FRUIT.toString());
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        if (other.getTag().equals(GameObjectsTags.AVATAR.toString())) {
            Avatar avatar = (Avatar) other;
            if(!isEaten) {
                eat();
                avatar.restoreEnergy(10f);
            }
        }
    }

    public void eat(){
            renderer().setOpaqueness(0f);
            new ScheduledTask(this, 30f, false, this::respawn);
            isEaten = true;
    }

    private void respawn(){
        isEaten = false;
        renderer().setOpaqueness(1f);
    }


}
