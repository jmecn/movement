package net.jmecn.tut.movement.esc.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.jmecn.tut.movement.esc.component.Position;
import net.jmecn.tut.movement.esc.component.Target;
import net.jmecn.tut.movement.esc.component.Velocity;
import net.jmecn.tut.movement.trail.TrailRenderer;

/**
 * @title SceneState
 * @author yanmaoyuan
 * @date 2021年1月9日
 * @version 1.0
 */
public class SceneState extends BaseAppState{

    private SimpleApplication app;
    private AssetManager assetManager;
    private Camera cam;

    private EntityData ed;
    private EntitySet entities;
    private Map<EntityId, Spatial> models = new HashMap<EntityId, Spatial>();

    private Node rootNode = new Node("scene");

    @Override
    protected void initialize(Application app) {
        this.app = (SimpleApplication) app;
        this.assetManager = app.getAssetManager();

        // 筛选用于显示的实体
        ed = this.app.getStateManager().getState(EntityDataState.class).getEntityData();
        entities = ed.getEntities(Position.class, Target.class);

        ViewPort viewPort = app.getViewPort();
        viewPort.setBackgroundColor(new ColorRGBA(0.8f, 0.8f, 0.8f, 1f));

        cam = app.getCamera();
        cam.setLocation(new Vector3f(0, 20, 0));
        cam.lookAtDirection(new Vector3f(0, -1, 0), Vector3f.UNIT_Z.negate());

        // lighting
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White.mult(0.3f));
        dl.setDirection(new Vector3f(-3, -4, -5).normalizeLocal());
        rootNode.addLight(dl);

        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(0.7f));
        rootNode.addLight(al);

        // ground
        Geometry ground = new Geometry("ground", new Quad(10, 10));
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", ColorRGBA.White);
        mat.setColor("Ambient", ColorRGBA.White);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 1);
        mat.setBoolean("UseMaterialColors", true);
        ground.setMaterial(mat);

        ground.setLocalTranslation(-5, 0, 5);
        ground.rotate(-FastMath.HALF_PI, 0, 0);
        rootNode.attachChild(ground);

        // create
        EntityId target = ed.createEntity();
        ed.setComponent(target, new Target("sphere"));
        ed.setComponent(target, new Position(-3f, 0.5f, 4f));
        ed.setComponent(target, new Velocity(0f, 0f, 0f));
    }

    @Override
    protected void cleanup(Application app) {
        rootNode.removeFromParent();
    }

    @Override
    protected void onEnable() {
        app.getRootNode().attachChild(rootNode);
    }

    @Override
    protected void onDisable() {
        rootNode.removeFromParent();
    }

    @Override
    public void update(float tpf) {
        if (entities.applyChanges()) {
            removeModels(entities.getRemovedEntities());
            addModels(entities.getAddedEntities());
            updateModels(entities.getChangedEntities());
        }
    }

    private void removeModels(Set<Entity> entities) {
        for (Entity e : entities) {
            Spatial s = models.remove(e.getId());
            s.removeFromParent();
        }
    }

    private void addModels(Set<Entity> entities) {
        for (Entity e : entities) {
            Spatial s = createSphere(e);
            models.put(e.getId(), s);
            updateModelSpatial(e, s);
            rootNode.attachChild(s);
        }
    }

    private void updateModels(Set<Entity> entities) {
        for (Entity e : entities) {
            Spatial s = models.get(e.getId());
            updateModelSpatial(e, s);
        }
    }

    private void updateModelSpatial(Entity e, Spatial s) {
        Position p = e.get(Position.class);
        s.setLocalTranslation(p.getLocation());
    }

    private Spatial createSphere(Entity e) {
        Geometry target = new Geometry("sphere", new Sphere(32, 24, 0.5f));

        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", ColorRGBA.DarkGray);
        mat.setColor("Ambient", ColorRGBA.DarkGray);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 32);
        mat.setBoolean("UseMaterialColors", true);
        target.setMaterial(mat);

        // trail
        TrailRenderer trailRenderer = new TrailRenderer(rootNode, assetManager, cam);
        trailRenderer.setColor(ColorRGBA.Red);
        trailRenderer.setLifeTime(5f);
        trailRenderer.setMinVertexDistance(0.1f);
        trailRenderer.setLineWidth(0.1f);
        trailRenderer.setTrailPerSecond(30);

        target.addControl(trailRenderer);

        return target;
    }
}