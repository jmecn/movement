package net.jmecn.tut.scene.shape;

import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

/**
 * @title Prism
 * @author yanmaoyuan
 * @date 2021年1月10日
 * @version 1.0
 */
public class Prism implements MeshBuilder {

    private float x = 1;

    private float y = 1;

    private float z = 1;

    private float offset = 0f;

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
     * @return the offset
     */
    public float getOffset() {
        return offset;
    }

    /**
     * Set the dimensions in the x-axis of the prism (the base of the triangle) in meters. The default value is 1. The
     * minimum value is 0.01.
     * 
     * @param x the x to set
     */
    public void setX(float x) {
        if (x < 0.01f) {
            throw new IllegalArgumentException();
        }
        this.x = x;
    }

    /**
     * Set the dimensions in the y-axis of the prism (the height of the prism) in meters. The default value is 1. The
     * minimum value is 0.01.
     * 
     * @param y the y to set
     */
    public void setY(float y) {
        if (y < 0.01f) {
            throw new IllegalArgumentException();
        }
        this.y = y;
    }

    /**
     * Set the dimensions in the z-axis of the prism (the amount you want to stretch the triangle) in meters. The
     * default value is 1. The minimum value is 0.01.
     * 
     * @param z the z to set
     */
    public void setZ(float z) {
        if (z < 0.01f) {
            throw new IllegalArgumentException();
        }
        this.z = z;
    }

    /**
     * Set the top offset in the x-axis. The default value is 0.
     * 
     * @param offset the offset to set
     */
    public void setOffset(float offset) {
        this.offset = offset;
    }

    @Override
    public Mesh build() {
        float twoX = x + x;
        float xTopL = x - offset;
        float xTopR = x + offset;
        float lenL = FastMath.sqrt(xTopL * xTopL + y * y);
        float lenR = FastMath.sqrt(xTopR * xTopR + y * y);

        // vertex
        Vector3f va = new Vector3f(-x, 0, z);
        Vector3f vb = new Vector3f(x, 0, z);
        Vector3f vc = new Vector3f(offset, y, z);
        Vector3f vd = new Vector3f(-x, 0, 0);
        Vector3f ve = new Vector3f(x, 0, 0);
        Vector3f vf = new Vector3f(offset, y, 0);

        // texCoord
        Vector2f ta = new Vector2f(0, 0);
        Vector2f tb = new Vector2f(twoX, 0);
        Vector2f tc = new Vector2f(xTopR, y);
        Vector2f td = new Vector2f(twoX, z);
        Vector2f te = new Vector2f(0, z);
        Vector2f tf = new Vector2f(z, 0);
        Vector2f tg = new Vector2f(z, lenR);
        Vector2f th = new Vector2f(0, lenR);
        Vector2f ti = new Vector2f(z, lenL);
        Vector2f tj = new Vector2f(0, lenL);

        Vector3f[] vertex = {
            // side
            va, vb, vc,
            ve, vd, vf,
            // left
            va, vc, vd,
            vd, vc, vf,
            // right
            vb, ve, vc,
            vc, ve, vf,
            // bottom
            vb, va, ve,
            va, vd, ve,
        };

        Vector2f[] texCoord = {
            // side
            ta, tb, tc,
            tb, ta, tc,
            // left
            tf, tg, ta,
            ta, tg, th,
            // right
            ta, tf, tj,
            tj, tf, ti,
            // bottom
            tb, ta, td,
            ta, te, td,
        };

        // normal
        Vector3f nFront = new Vector3f(0, 0, 1);
        Vector3f nBack = new Vector3f(0, 0, -1);
        Vector3f nLeft = new Vector3f(-y, xTopR, 0).normalizeLocal();
        Vector3f nRight = new Vector3f(y, xTopL, 0).normalizeLocal();
        Vector3f nBottom = new Vector3f(0, -1, 0);

        Vector3f[] normal = {
            // side
            nFront, nFront, nFront, 
            nBack, nBack, nBack,
            // left
            nLeft, nLeft, nLeft,
            nLeft, nLeft, nLeft,
            // right
            nRight, nRight, nRight,
            nRight, nRight, nRight,
            // bottom
            nBottom, nBottom, nBottom,
            nBottom, nBottom, nBottom,
        };

        short[] index = {
            0, 1, 2,
            3, 4, 5,
            6, 7, 8,
            9, 10, 11,
            12, 13, 14,
            15, 16, 17,
            18, 19, 20,
            21, 22, 23,
        };

        Mesh mesh = new Mesh();
        mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertex));
        mesh.setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(normal));
        mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        mesh.setBuffer(Type.Index, 3, BufferUtils.createShortBuffer(index));
        mesh.setStatic();
        mesh.updateBound();
        mesh.updateCounts();

        return mesh;
    }

}
