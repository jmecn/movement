package net.jmecn.tut.movement.common;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Mesh.Mode;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Format;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.VertexBuffer.Usage;
import com.jme3.scene.control.AbstractControl;
import com.jme3.util.BufferUtils;
import com.jme3.util.TempVars;

import java.nio.FloatBuffer;
import java.util.LinkedList;

/**
 * @title TrailRenderer
 * @author yanmaoyuan
 * @date 2021年1月7日
 * @version 1.0
 */
public class TrailRenderer extends AbstractControl {

    private Camera camera;

    private Vector3f lastPosition;

    private Vector3f look;
    private Vector3f up;

    private float minVertexDistance = 0.1f;

    private float lineWidth = 1f;

    private float coolDown = 0.5f;

    private float time = 0f;

    private int maxCount = 60;

    private Mesh mesh;

    private Geometry geom;

    private FloatBuffer floats;

    private VertexBuffer positions;

    private LinkedList<Vector3f> queue;

    public TrailRenderer(Node rootNode, AssetManager assetManager, Camera camera) {
        this.camera = camera;

        look = new Vector3f();
        up = new Vector3f();

        queue = new LinkedList<>();

        floats = FloatBuffer.allocate(6 * maxCount);

        positions = new VertexBuffer(Type.Position);
        positions.setupData(Usage.Dynamic, 3, Format.Float, floats);

        mesh = new Mesh();
        mesh.setMode(Mode.TriangleStrip);
        mesh.setBuffer(positions);
        mesh.setStatic();

        Material mat = new Material(assetManager, "/Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
        mat.getAdditionalRenderState().setWireframe(true);

        geom = new Geometry("Trail");
        geom.setMesh(mesh);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    protected void controlUpdate(float tpf) {
        time += tpf;
        
        Vector3f position = spatial.getWorldTranslation();
        if (lastPosition == null) {
            lastPosition = new Vector3f(position);

            rotateScreenAligned(camera);

            TempVars vars = TempVars.get();
            Vector3f pa = vars.vect1;
            Vector3f pb = vars.vect2;

            pa.set(up).multLocal(lineWidth);
            pb.set(pa).negateLocal();
            Vector3f a = position.add(pa);
            Vector3f b = position.add(pb);

            queue.add(a);
            queue.add(b);

            System.out.println("add a=" + a + ", b=" + b);
            rebuild();

            vars.release();

            time = 0f;
            return;
        }

        if (time < coolDown || position.distance(lastPosition) < minVertexDistance) {
            return;
        }

        time = 0f;

        rotateScreenAligned(camera);

        TempVars vars = TempVars.get();
        Vector3f pa = vars.vect1;
        Vector3f pb = vars.vect2;
        Vector3f p = vars.vect3;
        p.set(lastPosition);
        p.subtractLocal(position);
        Vector3f normal = p.cross(look, vars.vect4).normalizeLocal();

        pa.set(normal).multLocal(lineWidth);
        pb.set(pa).negateLocal();
        Vector3f a = position.add(pa);
        Vector3f b = position.add(pb);

        queue.add(a);
        queue.add(b);

        System.out.println("add a=" + a + ", b=" + b);
        rebuild();

        vars.release();

        // record position
        lastPosition.set(position);
    }

    private void rotateScreenAligned(Camera camera) {
        camera.getUp(up);
        camera.getDirection(look).negateLocal();
    }

    private void rebuild() {
        while (queue.size() > maxCount * 2) {
            queue.removeFirst();
            queue.removeFirst();
        }

        FloatBuffer floats = BufferUtils.createFloatBuffer(queue.toArray(new Vector3f[0]));
        positions.updateData(floats);
        mesh.updateCounts();
        
        geom.updateModelBound();
    }
}