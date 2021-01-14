package net.jmecn.tut.scene.shape;

import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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
        Mesh mesh = new Mesh();
        mesh.setStatic();

        if (curvature == 0) {
            straightStair(mesh);
        } else {
            curvatureStair(mesh);
        }

        mesh.updateCounts();
        mesh.updateBound();

        return mesh;
    }

    private Mesh straightStair(Mesh mesh) {
        int dv = 2;
        int quadCount = steps * 2;
        if (buildSide) {
            quadCount += steps * 2 + 2;
            dv += 2;
        }

        int vcount = quadCount * 4;
        int icount = quadCount * 6;
        FloatBuffer vertFB = BufferUtils.createFloatBuffer(vcount * 3);
        FloatBuffer normFB = BufferUtils.createFloatBuffer(vcount * 3);
        FloatBuffer texFB = BufferUtils.createFloatBuffer(vcount * 2);
        IntBuffer idxFB = BufferUtils.createIntBuffer(icount);

        float dy = y / steps;
        float dz = z / steps;

        for (int i = 0; i < steps; i++) {
            int idx = i * dv * 4;

            float x0 = 0;
            float x1 = x;

            float y0 = i * dy;
            float y1 = y0 + dy;

            float z0 = i * dz;
            float z1 = z0 + dz;

            // top
            // v0 x0, y1, -z0
            // v1 x1, y1, -z0
            // v2 x0, y1, -z1
            // v3 x1, y1, -z1
            vertFB.put(x0).put(y1).put(-z0);
            vertFB.put(x1).put(y1).put(-z0);
            vertFB.put(x0).put(y1).put(-z1);
            vertFB.put(x1).put(y1).put(-z1);

            texFB.put(x0).put(z0);
            texFB.put(x1).put(z0);
            texFB.put(x0).put(z1);
            texFB.put(x1).put(z1);

            normFB.put(0).put(1).put(0);
            normFB.put(0).put(1).put(0);
            normFB.put(0).put(1).put(0);
            normFB.put(0).put(1).put(0);

            int t0 = idx + 0;
            int t1 = idx + 1;
            int t2 = idx + 2;
            int t3 = idx + 3;

            idxFB.put(t0).put(t1).put(t2);
            idxFB.put(t1).put(t3).put(t2);

            // front
            // v0 0, y0, -z0
            // v1 x, y0, -z0
            // v2 0, y1, -z0
            // v3 x, y1, -z0
            vertFB.put(x0).put(y0).put(-z0);
            vertFB.put(x1).put(y0).put(-z0);
            vertFB.put(x0).put(y1).put(-z0);
            vertFB.put(x1).put(y1).put(-z0);

            texFB.put(x0).put(y0);
            texFB.put(x1).put(y0);
            texFB.put(x0).put(y1);
            texFB.put(x1).put(y1);

            normFB.put(0).put(0).put(1);
            normFB.put(0).put(0).put(1);
            normFB.put(0).put(0).put(1);
            normFB.put(0).put(0).put(1);

            int f0 = idx + 4;
            int f1 = idx + 5;
            int f2 = idx + 6;
            int f3 = idx + 7;

            idxFB.put(f0).put(f1).put(f2);
            idxFB.put(f1).put(f3).put(f2);

            if (buildSide) {
                // right/inner side
                // v0 x1 0 z0
                // v1 x1 0 z1
                // v2 x1 y1 z0
                // v3 x1 y1 z1
                vertFB.put(x1).put(0).put(-z0);
                vertFB.put(x1).put(0).put(-z1);
                vertFB.put(x1).put(y1).put(-z0);
                vertFB.put(x1).put(y1).put(-z1);

                texFB.put(z0).put(0);
                texFB.put(z1).put(0);
                texFB.put(z0).put(y1);
                texFB.put(z1).put(y1);

                normFB.put(1).put(0).put(0);
                normFB.put(1).put(0).put(0);
                normFB.put(1).put(0).put(0);
                normFB.put(1).put(0).put(0);

                int r0 = idx + 8;
                int r1 = idx + 9;
                int r2 = idx + 10;
                int r3 = idx + 11;

                idxFB.put(r0).put(r1).put(r2);
                idxFB.put(r1).put(r3).put(r2);

                // left/outer side
                // v0 x0 0 z0
                // v1 x0 0 z1
                // v2 x0 y1 z0
                // v3 x0 y1 z1
                vertFB.put(x0).put(0).put(-z0);
                vertFB.put(x0).put(0).put(-z1);
                vertFB.put(x0).put(y1).put(-z0);
                vertFB.put(x0).put(y1).put(-z1);

                texFB.put(z0).put(0);
                texFB.put(z1).put(0);
                texFB.put(z0).put(y1);
                texFB.put(z1).put(y1);

                normFB.put(-1).put(0).put(0);
                normFB.put(-1).put(0).put(0);
                normFB.put(-1).put(0).put(0);
                normFB.put(-1).put(0).put(0);

                int l0 = idx + 12;
                int l1 = idx + 13;
                int l2 = idx + 14;
                int l3 = idx + 15;

                idxFB.put(l0).put(l2).put(l1);
                idxFB.put(l1).put(l2).put(l3);

            }

        }

        if (buildSide) {
            int idx = steps * dv * 4;

            // bottom
            vertFB.put(0).put(0).put(0);
            vertFB.put(x).put(0).put(0);
            vertFB.put(0).put(0).put(-z);
            vertFB.put(x).put(0).put(-z);

            texFB.put(0).put(0);
            texFB.put(x).put(0);
            texFB.put(0).put(z);
            texFB.put(x).put(z);

            normFB.put(0).put(-1).put(0);
            normFB.put(0).put(-1).put(0);
            normFB.put(0).put(-1).put(0);
            normFB.put(0).put(-1).put(0);

            int b0 = idx + 0;
            int b1 = idx + 1;
            int b2 = idx + 2;
            int b3 = idx + 3;

            idxFB.put(b0).put(b2).put(b1);
            idxFB.put(b1).put(b2).put(b3);

            // end
            vertFB.put(0).put(0).put(-z);
            vertFB.put(x).put(0).put(-z);
            vertFB.put(0).put(y).put(-z);
            vertFB.put(x).put(y).put(-z);

            texFB.put(0).put(0);
            texFB.put(x).put(0);
            texFB.put(0).put(y);
            texFB.put(x).put(y);

            normFB.put(0).put(0).put(-1);
            normFB.put(0).put(0).put(-1);
            normFB.put(0).put(0).put(-1);
            normFB.put(0).put(0).put(-1);

            int e0 = idx + 4;
            int e1 = idx + 5;
            int e2 = idx + 6;
            int e3 = idx + 7;

            idxFB.put(e0).put(e2).put(e1);
            idxFB.put(e1).put(e2).put(e3);
        }

        // VB
        mesh.setBuffer(Type.Position, 3, vertFB);
        mesh.setBuffer(Type.Normal, 3, normFB);
        mesh.setBuffer(Type.TexCoord, 2, texFB);
        mesh.setBuffer(Type.Index, 3, idxFB);

        return mesh;
    }

    private Mesh curvatureStair(Mesh mesh) {
        int dv = 2;
        int quadCount = steps * 2;
        if (buildSide) {
            quadCount += steps * 3 + 1;
            dv += 3;
        }

        int vcount = quadCount * 4;
        int icount = quadCount * 6;
        FloatBuffer vertFB = BufferUtils.createFloatBuffer(vcount * 3);
        FloatBuffer normFB = BufferUtils.createFloatBuffer(vcount * 3);
        FloatBuffer texFB = BufferUtils.createFloatBuffer(vcount * 2);
        IntBuffer idxFB = BufferUtils.createIntBuffer(icount);

        float dy = y / steps;

        float innerRadius = z * FastMath.RAD_TO_DEG / curvature;
        float outerRadius = x + innerRadius;

        float radian = FastMath.DEG_TO_RAD * curvature;
        float stepRadian = radian / steps;

        float dzr = z / steps;
        float dzl = outerRadius * stepRadian;

        float sin = FastMath.sin(stepRadian);
        float cos = FastMath.cos(stepRadian);

        Matrix3f rotate = new Matrix3f(cos, 0, -sin, 0, 1, 0, sin, 0, cos);

        Vector3f v0 = new Vector3f(-outerRadius, 0, 0);
        Vector3f v1 = new Vector3f(-innerRadius, 0, 0);

        Vector3f v2 = new Vector3f();
        Vector3f v3 = new Vector3f();

        // normal vector point to the center
        Vector3f n0 = new Vector3f(1, 0, 0);
        Vector3f n1 = new Vector3f();

        for (int i = 0; i < steps; i++) {
            int idx = i * dv * 4;

            rotate.mult(v0, v2);
            rotate.mult(v1, v3);
            rotate.mult(n0, n1);

            float x0 = 0;
            float x1 = x;

            float y0 = i * dy;
            float y1 = y0 + dy;

            float zr0 = i * dzr;
            float zr1 = zr0 + dzr;

            float zl0 = i * dzl;
            float zl1 = zl0 + dzl;

            // top
            // v0 x0, y1, -z0
            // v1 x1, y1, -z0
            // v2 x0, y1, -z1
            // v3 x1, y1, -z1
            vertFB.put(v0.x + outerRadius).put(y1).put(v0.z);
            vertFB.put(v1.x + outerRadius).put(y1).put(v1.z);
            vertFB.put(v2.x + outerRadius).put(y1).put(v2.z);
            vertFB.put(v3.x + outerRadius).put(y1).put(v3.z);

            texFB.put(x0).put(0);
            texFB.put(x1).put(0);
            texFB.put(x0).put(dzr);
            texFB.put(x1).put(dzr);

            normFB.put(0).put(1).put(0);
            normFB.put(0).put(1).put(0);
            normFB.put(0).put(1).put(0);
            normFB.put(0).put(1).put(0);

            int t0 = idx + 0;
            int t1 = idx + 1;
            int t2 = idx + 2;
            int t3 = idx + 3;

            idxFB.put(t0).put(t1).put(t2);
            idxFB.put(t1).put(t3).put(t2);

            // front
            // v0 0, y0, -z0
            // v1 x, y0, -z0
            // v2 0, y1, -z0
            // v3 x, y1, -z0
            vertFB.put(v0.x + outerRadius).put(y0).put(v0.z);
            vertFB.put(v1.x + outerRadius).put(y0).put(v1.z);
            vertFB.put(v0.x + outerRadius).put(y1).put(v0.z);
            vertFB.put(v1.x + outerRadius).put(y1).put(v1.z);

            texFB.put(x0).put(y0);
            texFB.put(x1).put(y0);
            texFB.put(x0).put(y1);
            texFB.put(x1).put(y1);

            normFB.put(-n0.z).put(0).put(n0.x);
            normFB.put(-n0.z).put(0).put(n0.x);
            normFB.put(-n0.z).put(0).put(n0.x);
            normFB.put(-n0.z).put(0).put(n0.x);

            int f0 = idx + 4;
            int f1 = idx + 5;
            int f2 = idx + 6;
            int f3 = idx + 7;

            idxFB.put(f0).put(f1).put(f2);
            idxFB.put(f1).put(f3).put(f2);

            if (buildSide) {

                // right/inner side
                // v0 x1 0 z0
                // v1 x1 0 z1
                // v2 x1 y1 z0
                // v3 x1 y1 z1
                vertFB.put(v1.x + outerRadius).put(0).put(v1.z);
                vertFB.put(v3.x + outerRadius).put(0).put(v3.z);
                vertFB.put(v1.x + outerRadius).put(y1).put(v1.z);
                vertFB.put(v3.x + outerRadius).put(y1).put(v3.z);

                texFB.put(zr0).put(0);
                texFB.put(zr1).put(0);
                texFB.put(zr0).put(y1);
                texFB.put(zr1).put(y1);

                normFB.put(n0.x).put(0).put(n0.z);
                normFB.put(n1.x).put(0).put(n1.z);
                normFB.put(n0.x).put(0).put(n0.z);
                normFB.put(n1.x).put(0).put(n1.z);

                int r0 = idx + 8;
                int r1 = idx + 9;
                int r2 = idx + 10;
                int r3 = idx + 11;

                idxFB.put(r0).put(r1).put(r2);
                idxFB.put(r1).put(r3).put(r2);

                // left/outer side
                // v0 x0 0 z0
                // v1 x0 0 z1
                // v2 x0 y1 z0
                // v3 x0 y1 z1
                vertFB.put(v0.x + outerRadius).put(0).put(v0.z);
                vertFB.put(v2.x + outerRadius).put(0).put(v2.z);
                vertFB.put(v0.x + outerRadius).put(y1).put(v0.z);
                vertFB.put(v2.x + outerRadius).put(y1).put(v2.z);

                texFB.put(zl0).put(0);
                texFB.put(zl1).put(0);
                texFB.put(zl0).put(y1);
                texFB.put(zl1).put(y1);

                normFB.put(-n0.x).put(0).put(-n0.z);
                normFB.put(-n1.x).put(0).put(-n1.z);
                normFB.put(-n0.x).put(0).put(-n0.z);
                normFB.put(-n1.x).put(0).put(-n1.z);

                int l0 = idx + 12;
                int l1 = idx + 13;
                int l2 = idx + 14;
                int l3 = idx + 15;

                idxFB.put(l0).put(l2).put(l1);
                idxFB.put(l1).put(l2).put(l3);

                // bottom
                vertFB.put(v0.x + outerRadius).put(0).put(v0.z);
                vertFB.put(v1.x + outerRadius).put(0).put(v1.z);
                vertFB.put(v2.x + outerRadius).put(0).put(v2.z);
                vertFB.put(v3.x + outerRadius).put(0).put(v3.z);

                texFB.put(x0).put(zr0);
                texFB.put(x1).put(zr0);
                texFB.put(x0).put(zr1);
                texFB.put(x1).put(zr1);

                normFB.put(0).put(-1).put(0);
                normFB.put(0).put(-1).put(0);
                normFB.put(0).put(-1).put(0);
                normFB.put(0).put(-1).put(0);

                int b0 = idx + 16;
                int b1 = idx + 17;
                int b2 = idx + 18;
                int b3 = idx + 19;

                idxFB.put(b0).put(b2).put(b1);
                idxFB.put(b1).put(b2).put(b3);
            }

            v0.set(v2);
            v1.set(v3);
            n0.set(n1);
        }

        if (buildSide) {
            // end
            vertFB.put(v2.x + outerRadius).put(0).put(v2.z);
            vertFB.put(v3.x + outerRadius).put(0).put(v3.z);
            vertFB.put(v2.x + outerRadius).put(y).put(v2.z);
            vertFB.put(v3.x + outerRadius).put(y).put(v3.z);

            texFB.put(0).put(0);
            texFB.put(x).put(0);
            texFB.put(0).put(y);
            texFB.put(x).put(y);

            normFB.put(n0.z).put(0).put(-n0.x);
            normFB.put(n0.z).put(0).put(-n0.x);
            normFB.put(n0.z).put(0).put(-n0.x);
            normFB.put(n0.z).put(0).put(-n0.x);

            int idx = steps * dv * 4;
            int e0 = idx + 0;
            int e1 = idx + 1;
            int e2 = idx + 2;
            int e3 = idx + 3;

            idxFB.put(e0).put(e2).put(e1);
            idxFB.put(e1).put(e2).put(e3);
        }

        // VB
        mesh.setBuffer(Type.Position, 3, vertFB);
        mesh.setBuffer(Type.Normal, 3, normFB);
        mesh.setBuffer(Type.TexCoord, 2, texFB);
        mesh.setBuffer(Type.Index, 3, idxFB);

        return mesh;
    }

    public Mesh buildSlope() {
        Mesh mesh = new Mesh();
        mesh.setStatic();

        if (curvature == 0) {
            straightSlope(mesh);
        } else {
            curvatureStair(mesh);
        }

        mesh.updateCounts();
        mesh.updateBound();

        return mesh;
    }

    private Mesh straightSlope(Mesh mesh) {
        Vector3f n = new Vector3f(0, z, y);
        float length = n.length();
        n.normalizeLocal();

        float dz = z / steps;
        float halfDz = dz * 0.5f;

        int quadCount = 7;
        int vcount = quadCount * 4 + 2;
        int icount = quadCount * 6;
        FloatBuffer vertFB = BufferUtils.createFloatBuffer(vcount * 3);
        FloatBuffer normFB = BufferUtils.createFloatBuffer(vcount * 3);
        FloatBuffer texFB = BufferUtils.createFloatBuffer(vcount * 2);
        IntBuffer idxFB = BufferUtils.createIntBuffer(icount);

        int idx = 0;

        // front
        vertFB.put(0).put(0).put(halfDz);
        vertFB.put(x).put(0).put(halfDz);
        vertFB.put(0).put(y).put(-z + halfDz);
        vertFB.put(x).put(y).put(-z + halfDz);

        texFB.put(0).put(0);
        texFB.put(1).put(0);
        texFB.put(0).put(length);
        texFB.put(1).put(length);

        normFB.put(0).put(n.y).put(n.z);
        normFB.put(0).put(n.y).put(n.z);
        normFB.put(0).put(n.y).put(n.z);
        normFB.put(0).put(n.y).put(n.z);

        int f0 = idx + 0;
        int f1 = idx + 1;
        int f2 = idx + 2;
        int f3 = idx + 3;
        idx += 4;

        idxFB.put(f0).put(f1).put(f2);
        idxFB.put(f1).put(f3).put(f2);

        // top
        vertFB.put(0).put(y).put(-z + halfDz);
        vertFB.put(x).put(y).put(-z + halfDz);
        vertFB.put(0).put(y).put(-z);
        vertFB.put(x).put(y).put(-z);

        texFB.put(0).put(1 - halfDz);
        texFB.put(1).put(1 - halfDz);
        texFB.put(0).put(1);
        texFB.put(1).put(1);

        normFB.put(0).put(1).put(0);
        normFB.put(0).put(1).put(0);
        normFB.put(0).put(1).put(0);
        normFB.put(0).put(1).put(0);

        int t0 = idx + 0;
        int t1 = idx + 1;
        int t2 = idx + 2;
        int t3 = idx + 3;
        idx += 4;

        idxFB.put(t0).put(t1).put(t2);
        idxFB.put(t1).put(t3).put(t2);

        // right/inner side
        vertFB.put(x).put(0).put(-z + halfDz);
        vertFB.put(x).put(0).put(-z);
        vertFB.put(x).put(y).put(-z + halfDz);
        vertFB.put(x).put(y).put(-z);

        texFB.put(1 - halfDz).put(0);
        texFB.put(1).put(0);
        texFB.put(1 - halfDz).put(y);
        texFB.put(1).put(y);

        normFB.put(1).put(0).put(0);
        normFB.put(1).put(0).put(0);
        normFB.put(1).put(0).put(0);
        normFB.put(1).put(0).put(0);

        int r0 = idx + 0;
        int r1 = idx + 1;
        int r2 = idx + 2;
        int r3 = idx + 3;
        idx += 4;

        idxFB.put(r0).put(r1).put(r2);
        idxFB.put(r1).put(r3).put(r2);

        vertFB.put(x).put(0).put(halfDz);
        vertFB.put(x).put(0).put(-z + halfDz);
        vertFB.put(x).put(y).put(-z + halfDz);

        texFB.put(0).put(0);
        texFB.put(z).put(0);
        texFB.put(z).put(y);

        normFB.put(1).put(0).put(0);
        normFB.put(1).put(0).put(0);
        normFB.put(1).put(0).put(0);
        
        int r4 = idx + 0;
        int r5 = idx + 1;
        int r6 = idx + 2;
        idx += 3;

        idxFB.put(r4).put(r5).put(r6);
        
        // left/outer side
        vertFB.put(0).put(0).put(-z + halfDz);
        vertFB.put(0).put(0).put(-z);
        vertFB.put(0).put(y).put(-z + halfDz);
        vertFB.put(0).put(y).put(-z);

        texFB.put(1 - halfDz).put(0);
        texFB.put(1).put(0);
        texFB.put(1 - halfDz).put(y);
        texFB.put(1).put(y);
        
        normFB.put(-1).put(0).put(0);
        normFB.put(-1).put(0).put(0);
        normFB.put(-1).put(0).put(0);
        normFB.put(-1).put(0).put(0);
        
        int l0 = idx + 0;
        int l1 = idx + 1;
        int l2 = idx + 2;
        int l3 = idx + 3;
        idx += 4;

        idxFB.put(l0).put(l2).put(l1);
        idxFB.put(l1).put(l2).put(l3);

        vertFB.put(0).put(0).put(halfDz);
        vertFB.put(0).put(0).put(-z + halfDz);
        vertFB.put(0).put(y).put(-z + halfDz);

        texFB.put(0).put(0);
        texFB.put(z).put(0);
        texFB.put(z).put(y);

        normFB.put(-1).put(0).put(0);
        normFB.put(-1).put(0).put(0);
        normFB.put(-1).put(0).put(0);
        
        int l4 = idx + 0;
        int l5 = idx + 1;
        int l6 = idx + 2;
        idx += 3;

        idxFB.put(l4).put(l6).put(l5);

        // bottom
        vertFB.put(0).put(0).put(halfDz);
        vertFB.put(x).put(0).put(halfDz);
        vertFB.put(0).put(0).put(-z);
        vertFB.put(x).put(0).put(-z);

        texFB.put(0).put(0);
        texFB.put(x).put(0);
        texFB.put(0).put(z);
        texFB.put(x).put(z);

        normFB.put(0).put(-1).put(0);
        normFB.put(0).put(-1).put(0);
        normFB.put(0).put(-1).put(0);
        normFB.put(0).put(-1).put(0);

        int b0 = idx + 0;
        int b1 = idx + 1;
        int b2 = idx + 2;
        int b3 = idx + 3;
        idx += 4;

        idxFB.put(b0).put(b2).put(b1);
        idxFB.put(b1).put(b2).put(b3);

        // end
        vertFB.put(0).put(0).put(-z);
        vertFB.put(x).put(0).put(-z);
        vertFB.put(0).put(y).put(-z);
        vertFB.put(x).put(y).put(-z);

        texFB.put(0).put(0);
        texFB.put(x).put(0);
        texFB.put(0).put(y);
        texFB.put(x).put(y);

        normFB.put(0).put(0).put(-1);
        normFB.put(0).put(0).put(-1);
        normFB.put(0).put(0).put(-1);
        normFB.put(0).put(0).put(-1);

        int e0 = idx + 0;
        int e1 = idx + 1;
        int e2 = idx + 2;
        int e3 = idx + 3;
        idx += 4;

        idxFB.put(e0).put(e2).put(e1);
        idxFB.put(e1).put(e2).put(e3);

        // VB
        mesh.setBuffer(Type.Position, 3, vertFB);
        mesh.setBuffer(Type.Normal, 3, normFB);
        mesh.setBuffer(Type.TexCoord, 2, texFB);
        mesh.setBuffer(Type.Index, 3, idxFB);
        return mesh;
    }
}
