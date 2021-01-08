package net.jmecn.tut.movement.debug.trail;

import com.jme3.math.Vector3f;

/**
 * @title TrailEntity
 * @author yanmaoyuan
 * @date 2021年1月8日
 * @version 1.0
 */
public class TrailEntity {

    protected Vector3f up = new Vector3f(0, 1, 0);

    protected Vector3f look = new Vector3f(0, 0, -1);

    protected Vector3f position = new Vector3f(0, 0, 0);

    protected float lifeTime = 0f;
}