package net.jmecn.tut.movement.debug.trail;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.util.TempVars;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @title TrailRenderer
 * @author yanmaoyuan
 * @date 2021年1月7日
 * @version 1.0
 */
public class TrailRenderer extends AbstractControl {

    private boolean isStarted = false;

    private Camera camera;

    private Vector3f lastPosition;

    // screen coordinate
    private Vector3f look;
    private Vector3f up;

    private float minVertexDistance = 0.5f;

    // control gen trail speed
    private float trailPerSecond = 20;
    private float coolDown = 1.0f / trailPerSecond;
    private float time = 0f;

    private float lifeTime = 5f;

    // control max trail count
    private int maxCount = 1000;

    private float lineWidth = 1f;

    private LinkedList<TrailEntity> queue;

    private LinkedList<TrailEntity> trailPool;

    // geometries
    private Geometry geom;

    private TrailMesh mesh;

    private Material mat;

    public TrailRenderer(Node rootNode, AssetManager assetManager, Camera camera) {
        this.camera = camera;

        look = new Vector3f();
        up = new Vector3f();

        queue = new LinkedList<>();
        trailPool = new LinkedList<>();

        mesh = new TrailMesh(maxCount);
        mesh.setWidth(lineWidth);

        mat = new Material(assetManager, "/Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);

        geom = new Geometry("Trail1");
        geom.setMesh(mesh);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
    }

    public void setTrailPerSecond(int trailPerSecond) {
        if (trailPerSecond <= 0) {
            throw new IllegalArgumentException("trailPerSecond must be greater than 0");
        }

        this.trailPerSecond = trailPerSecond;
        this.coolDown = 1f / trailPerSecond;
    }

    public void setMinVertexDistance(float minVertexDistance) {
        if (trailPerSecond <= 0) {
            throw new IllegalArgumentException("minVertexDistance must be greater than 0");
        }

        this.minVertexDistance = minVertexDistance;
    }

    public void setColor(ColorRGBA color) {
        if (color == null) {
            throw new IllegalArgumentException("color is null");
        }
        this.mat.setColor("Color", color);
    }

    public void setLineWidth(float lineWidth) {
        if (lineWidth <= 0) {
            throw new IllegalArgumentException("lineWidth must be greater than 0");
        }

        this.lineWidth = lineWidth;
        mesh.setWidth(lineWidth);
        mesh.update(queue);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    protected void controlUpdate(float tpf) {
        time += tpf;

        Vector3f position = spatial.getWorldTranslation();
        if (!isStarted) {
            addTail(position);
            isStarted = true;
            return;
        }

        boolean updated = false;
        if (queue.size() > 0) {
            Iterator<TrailEntity> itor = queue.iterator();
            while(itor.hasNext()) {
                TrailEntity e = itor.next();
                e.lifeTime += tpf;
                if (e.lifeTime >= lifeTime) {
                    itor.remove();
                    trailPool.add(e);
                    updated = true;
                }
            }
        }

        if (position.distance(lastPosition) < minVertexDistance || time < coolDown) {
            if (updated) {
                updateGeometry();
            }
            return;
        }

        addTail(position);

        lastPosition.set(position);
    }

    private void addTail(Vector3f position) {
        if (lastPosition == null) {
            lastPosition = new Vector3f(position);
        }

        // clear cool down time
        time = 0f;

        // get screen coordinate from camera
        camera.getUp(this.up);
        camera.getDirection(this.look).negateLocal();

        TempVars vars = TempVars.get();

        // calculate coordinate
        Vector3f up = vars.vect1;
        Vector3f look = vars.vect2;
        
        if (isStarted) {
            Vector3f dir = vars.vect3;
            lastPosition.subtract(position, dir);
            dir.normalizeLocal();
            dir.cross(this.look, up);
            
            if (up.equals(Vector3f.ZERO)) {
                up.set(this.up);
            } else {
                up.normalizeLocal();
            }

            up.cross(dir, look);
        } else {
            up.set(this.up);
            look.set(this.look);
        }

        TrailEntity e = null;
        if (trailPool.isEmpty()) {
            e = new TrailEntity();
        } else {
            e = trailPool.removeFirst();
        }

        e.position.set(position);
        e.up.set(up);
        e.look.set(look);
        e.lifeTime = 0f;

        queue.add(e);

        updateGeometry();

        lastPosition.set(position);

        vars.release();
    }

    private void updateGeometry() {
        while (queue.size() > maxCount) {
            TrailEntity trailEntity = queue.removeFirst();
            trailPool.add(trailEntity);
        }

        mesh.update(queue);

        geom.updateModelBound();
    }
}