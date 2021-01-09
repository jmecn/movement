package net.jmecn.tut.movement.esc.component;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

/**
 * @title Position
 * @author yanmaoyuan
 * @date 2021年1月9日
 * @version 1.0
 */
public class Position implements EntityComponent {

    private Vector3f location;

    public Position() {
        location = new Vector3f(0f, 0f, 0f);
    }

    public Position(Vector3f position) {
        this.location = new Vector3f(position);
    }

    public Position(float x, float y, float z) {
        location = new Vector3f(x, y, z);
    }

    public Vector3f getLocation() {
        return location;
    }
}