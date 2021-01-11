package net.jmecn.tut.movement.shape;

import com.jme3.scene.Mesh;

/**
 * @title Stair
 * @author yanmaoyuan
 * @date 2021年1月10日
 * @version 1.0
 */
public class Stair implements MeshBuilder {

    /**
     * Set the number of steps to define on the stairs. The default value is 6. Valid values range from 2 to 64.
     */
    private int steps = 6;

    /**
     * Enable this option to draw polygons on the sides of the stairs. This is enabled by default. You can disable this
     * option if the sides of your stairs are not visible to the camera (for example, if your stairs are built into a
     * wall).
     */
    private boolean buildSide = true;

    /**
     * Set the degree of curvature on the stairs in degrees, where 0 makes straight stairs and 360 makes stairs in a
     * complete circle. Keep in mind that you might need to increase the number of stairs to compensate as you increase
     * this value. The default value is 0. Valid values range from 0 to 360.
     */
    private int curvature = 0;

    /**
     * Set the width (dimensions in the x-axis) of the stairs in meters. You can use the Width slider or enter an exact
     * value in the X text box. The default value is 2. Valid values range from 0.01 to 10.
     */
    private float x = 2f;

    /**
     * Set the height (dimensions in the y-axis) of the stairs in meters. You can use the Height slider or enter an
     * exact value in the Y text box. The default value is 2.5. Keep in mind that you may need to increase the number of
     * stairs to compensate as you increase this value. Valid values range from 0.01 to 10.
     */
    private float y = 2.5f;

    /**
     * Set the depth (dimensions in the z-axis) of the stairs in meters. You can use the Depth slider or enter an exact
     * value in the Z text box. Keep in mind that you may need to increase the number of stairs to compensate as you
     * increase this value. The default value is 4. Valid values range from 0.01 to 10.
     */
    private float z = 4f;

    /**
     * @return the steps
     */
    public int getSteps() {
        return steps;
    }

    /**
     * @return the buildSide
     */
    public boolean isBuildSide() {
        return buildSide;
    }

    /**
     * @return the curvature
     */
    public int getCurvature() {
        return curvature;
    }

    /**
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * @return the z
     */
    public float getZ() {
        return z;
    }

    /**
     * @return the width
     */
    public float getWidth() {
        return x;
    }

    /**
     * @return the height
     */
    public float getHeight() {
        return y;
    }

    /**
     * @return the depth
     */
    public float getDepth() {
        return z;
    }

    /**
     * Set the number of steps to define on the stairs. The default value is 6. Valid values range from 2 to 64.
     * 
     * @param steps the steps to set
     */
    public void setSteps(int steps) {
        if (steps < 2 || steps > 64) {
            throw new IllegalArgumentException();
        }
        this.steps = steps;
    }

    /**
     * Enable this option to draw polygons on the sides of the stairs. This is enabled by default. You can disable this
     * option if the sides of your stairs are not visible to the camera (for example, if your stairs are built into a
     * wall).
     * 
     * @param buildSide the buildSide to set
     */
    public void setBuildSide(boolean buildSide) {
        this.buildSide = buildSide;
    }

    /**
     * Set the degree of curvature on the stairs in degrees, where 0 makes straight stairs and 360 makes stairs in a
     * complete circle. Keep in mind that you might need to increase the number of stairs to compensate as you increase
     * this value. The default value is 0. Valid values range from 0 to 360.
     * 
     * @param curvature the curvature to set
     */
    public void setCurvature(int curvature) {
        if (curvature < 0 || curvature > 360) {
            throw new IllegalArgumentException();
        }
        this.curvature = curvature;
    }

    /**
     * Set the width (dimensions in the x-axis) of the stairs in meters. You can use the Width slider or enter an exact
     * value in the X text box. The default value is 2. Valid values range from 0.01 to 10.
     * 
     * @param x the x to set
     */
    public void setX(float x) {
        if (x < 0.01f || x > 10f) {
            throw new IllegalArgumentException();
        }
        this.x = x;
    }

    /**
     * Set the height (dimensions in the y-axis) of the stairs in meters. You can use the Height slider or enter an
     * exact value in the Y text box. The default value is 2.5. Keep in mind that you may need to increase the number of
     * stairs to compensate as you increase this value. Valid values range from 0.01 to 10.
     * 
     * @param y the y to set
     */
    public void setY(float y) {
        if (y < 0.01f || y > 10f) {
            throw new IllegalArgumentException();
        }
        this.y = y;
    }

    /**
     * Set the depth (dimensions in the z-axis) of the stairs in meters. You can use the Depth slider or enter an exact
     * value in the Z text box. Keep in mind that you may need to increase the number of stairs to compensate as you
     * increase this value. The default value is 4. Valid values range from 0.01 to 10.
     * 
     * @param z the z to set
     */
    public void setZ(float z) {
        if (z < 0.01f || z > 10f) {
            throw new IllegalArgumentException();
        }
        this.z = z;
    }

    /**
     * Set the width (dimensions in the x-axis) of the stairs in meters. You can use the Width slider or enter an exact
     * value in the X text box. The default value is 2. Valid values range from 0.01 to 10.
     * 
     * @param width the width to set
     */
    public void setWidth(float width) {
        setX(width);
    }

    /**
     * Set the height (dimensions in the y-axis) of the stairs in meters. You can use the Height slider or enter an
     * exact value in the Y text box. The default value is 2.5. Keep in mind that you may need to increase the number of
     * stairs to compensate as you increase this value. Valid values range from 0.01 to 10.
     * 
     * @param height the height to set
     */
    public void setHeight(float height) {
        setY(height);
    }

    /**
     * Set the depth (dimensions in the z-axis) of the stairs in meters. You can use the Depth slider or enter an exact
     * value in the Z text box. Keep in mind that you may need to increase the number of stairs to compensate as you
     * increase this value. The default value is 4. Valid values range from 0.01 to 10.
     * 
     * @param depth the depth to set
     */
    public void setDepth(float depth) {
        setZ(depth);
    }

    @Override
    public Mesh build() {
        // TODO Auto-generated method stub
        return null;
    }
}
