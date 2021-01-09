package net.jmecn.tut.movement.esc.component;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

/**
 * @title Velocity
 * @author yanmaoyuan
 * @date 2021年1月9日
 * @version 1.0
 */
public class Velocity implements EntityComponent {

    private Vector3f velocity;

    public Velocity() {
        velocity = new Vector3f(0f, 0f, 0f);
    }

    public Velocity(Vector3f velocity) {
        this.velocity = new Vector3f(velocity);
    }

    public Velocity(float x, float y, float z) {
        velocity = new Vector3f(x, y, z);
    }

    public Vector3f getVelocity() {
        return velocity;
    }
}