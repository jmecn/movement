package net.jmecn.tut.scene.shape;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

/**
 * @title Plane
 * @author yanmaoyuan
 * @date 2021年1月11日
 * @version 1.0
 */
public class Plane implements MeshBuilder {

    public enum Axis {
        X, Y, Z
    }

    private float x = 1;

    private float y = 1;

    private Axis axis = Axis.Y;

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

    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    @Override
    public Mesh build() {

        Mesh mesh = new Mesh();
        mesh.setStatic();

        switch (axis) {
            case X:
                axisX(mesh);
                break;
            case Y:
                axisY(mesh);
                break;
            case Z:
                axisZ(mesh);
                break;
        }

        Vector2f ta = new Vector2f(0, 0);
        Vector2f tb = new Vector2f(x, 0);
        Vector2f tc = new Vector2f(0, y);
        Vector2f td = new Vector2f(x, y);
        Vector2f[] texCoord = { ta, tb, tc, tb, td, tc, };

        short[] index = { 0, 1, 2, 3, 4, 5 };

        mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        mesh.setBuffer(Type.Index, 3, BufferUtils.createShortBuffer(index));

        mesh.updateBound();
        mesh.updateCounts();
        return mesh;
    }

    private Mesh axisZ(Mesh mesh) {
        Vector3f va = new Vector3f(0, 0, 0);
        Vector3f vb = new Vector3f(x, 0, 0);
        Vector3f vc = new Vector3f(0, y, 0);
        Vector3f vd = new Vector3f(x, y, 0);
        Vector3f[] vertex = { va, vb, vc, vb, vd, vc };

        Vector3f n = new Vector3f(0, 0, 1);
        Vector3f[] normal = { n, n, n, n, n, n };

        mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertex));
        mesh.setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(normal));

        return mesh;
    }

    private Mesh axisX(Mesh mesh) {
        Vector3f va = new Vector3f(0, 0, 0);
        Vector3f vb = new Vector3f(0, 0, -x);
        Vector3f vc = new Vector3f(0, y, 0);
        Vector3f vd = new Vector3f(0, y, -x);
        Vector3f[] vertex = { va, vb, vc, vb, vd, vc };

        Vector3f n = new Vector3f(0, 1, 0);
        Vector3f[] normal = { n, n, n, n, n, n };
        mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertex));
        mesh.setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(normal));
        return mesh;
    }

    private Mesh axisY(Mesh mesh) {
        Vector3f va = new Vector3f(0, 0, 0);
        Vector3f vb = new Vector3f(x, 0, 0);
        Vector3f vc = new Vector3f(0, 0, -y);
        Vector3f vd = new Vector3f(x, 0, -y);
        Vector3f[] vertex = { va, vb, vc, vb, vd, vc };

        Vector3f n = new Vector3f(0, 1, 0);
        Vector3f[] normal = { n, n, n, n, n, n };

        mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertex));
        mesh.setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(normal));
        return mesh;
    }
}
