package net.jmecn.tut.movement;

import com.jme3.app.DetailedProfilerState;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

import net.jmecn.tut.movement.trail.TrailRenderer;

/**
 * @title TestTrail
 * @author yanmaoyuan
 * @date 2021年1月8日
 * @version 1.0
 */
public class TestTrail extends SimpleApplication {

    private TrailRenderer trailRenderer;

    private Geometry target;

    private Vector3f pos = new Vector3f();

    private float radius = 20f;

    private float a = 0.5f;

    private float v = 1f;

    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);

        if (radius > 0f) {
            float time = timer.getTimeInSeconds() * FastMath.PI;
            float sin = FastMath.sin(time);
            float cos = FastMath.cos(time);

            pos.set(target.getLocalTranslation());
            pos.y += tpf * v;
            pos.x = sin * radius;
            pos.z = cos * radius;

            radius -= tpf;
            v += tpf * a;
            target.setLocalTranslation(pos);
        }
    }

    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(new ColorRGBA(0.8f, 0.8f, 0.8f, 1f));

        stateManager.attach(new DetailedProfilerState());

        flyCam.setMoveSpeed(100f);

        // lighting
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White.mult(0.7f));
        dl.setDirection(new Vector3f(-3, -4, -5).normalizeLocal());
        rootNode.addLight(dl);
        
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(0.3f));
        rootNode.addLight(al);

        // camera
        cam.setLocation(new Vector3f(60.543606f, 81.33967f, 176.3714f));
        cam.setRotation(new Quaternion(-0.008452326f, 0.9888665f, -0.06188533f, -0.13506193f));

        // geometry
        Sphere mesh = new Sphere(32, 24, 0.5f);
        target = new Geometry("sphere");
        target.setMesh(mesh);

        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", ColorRGBA.DarkGray);
        mat.setColor("Ambient", ColorRGBA.DarkGray);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 32);
        mat.setBoolean("UseMaterialColors", true);
        target.setMaterial(mat);

        // trail
        trailRenderer = new TrailRenderer(rootNode, assetManager, cam);
        trailRenderer.setColor(ColorRGBA.Red);
        trailRenderer.setMinVertexDistance(1f);
        trailRenderer.setLineWidth(0.1f);
        trailRenderer.setTrailPerSecond(30);

        target.addControl(trailRenderer);

        rootNode.attachChild(target);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        TestTrail app = new TestTrail();
        app.start();
    }

}
