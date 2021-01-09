package net.jmecn.tut.movement.trail;

import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Format;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.VertexBuffer.Usage;
import com.jme3.util.BufferUtils;
import com.jme3.util.TempVars;

import java.nio.FloatBuffer;
import java.util.List;

/**
 * @title TrailGeom
 * @author yanmaoyuan
 * @date 2021年1月8日
 * @version 1.0
 */
public class TrailMesh extends Mesh {

    private FloatBuffer  positionBuf;
    private VertexBuffer positionVB;

    private FloatBuffer  normalBuf;
    private VertexBuffer normalVB;

    private int maxCount;

    private float width;

    public TrailMesh() {
        this.maxCount = 1000;
        this.width = 1.0f;
        resetBuffers();
    }

    public TrailMesh(int maxCount) {
        this.maxCount = maxCount;
        this.width = 1.0f;
        resetBuffers();
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;

        resetBuffers();
    }

    private void resetBuffers() {
        positionBuf = BufferUtils.createFloatBuffer(6 * maxCount);
        positionVB = new VertexBuffer(Type.Position);
        positionVB.setupData(Usage.Dynamic, 3, Format.Float, positionBuf);

        normalBuf = BufferUtils.createFloatBuffer(6 * maxCount);
        normalVB = new VertexBuffer(Type.Normal);
        normalVB.setupData(Usage.Dynamic, 3, Format.Float, normalBuf);

        setMode(Mode.TriangleStrip);
        setBuffer(positionVB);
        setBuffer(normalVB);
        setStatic();
    }

    public void update(List<TrailEntity> queue) {

        int size = queue.size();

        int positionLimit = size * 6;
        positionBuf.limit(positionLimit);
        positionBuf.rewind();

        int normalLimit = size * 6;
        normalBuf.limit(normalLimit);
        normalBuf.rewind();

        // calculate side vectors
        TempVars vars = TempVars.get();

        Vector3f a = vars.vect1;
        Vector3f b = vars.vect2;

        float halfWidth = width * 0.5f;
        for (TrailEntity it : queue) {
            a.set(it.up).multLocal(halfWidth);
            b.set(a).negateLocal();
            a.addLocal(it.position);
            b.addLocal(it.position);

            positionBuf.put(a.x);
            positionBuf.put(a.y);
            positionBuf.put(a.z);
            positionBuf.put(b.x);
            positionBuf.put(b.y);
            positionBuf.put(b.z);

            normalBuf.put(it.look.x);
            normalBuf.put(it.look.y);
            normalBuf.put(it.look.z);
            normalBuf.put(it.look.x);
            normalBuf.put(it.look.y);
            normalBuf.put(it.look.z);
        }

        vars.release();

        positionVB.updateData(positionBuf);
        normalVB.updateData(normalBuf);
        updateCounts();
        updateBound();
    }
}