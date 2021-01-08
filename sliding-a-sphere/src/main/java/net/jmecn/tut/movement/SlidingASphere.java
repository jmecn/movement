package net.jmecn.tut.movement;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;

import net.jmecn.tut.movement.control.MovingSphere;
import net.jmecn.tut.movement.debug.trail.TrailRenderer;

/**
 * @title SlidingASphere
 * @author yanmaoyuan
 * @date 2021年1月8日
 * @version 1.0
 */
public class SlidingASphere extends SimpleApplication {

    private Geometry target;

    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(new ColorRGBA(0.8f, 0.8f, 0.8f, 1f));
        
        flyCam.setEnabled(false);

        cam.setLocation(new Vector3f(0, 10, 0));
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

        // geometry
        target = new Geometry("sphere", new Sphere(32, 24, 0.5f));

        mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
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

        MovingSphere movingSphere = new MovingSphere();
        movingSphere.registerInput(inputManager);
        target.addControl(movingSphere);

        ground.move(-5, 0, 5);
        ground.rotate(-FastMath.HALF_PI, 0, 0);
        rootNode.attachChild(ground);

        target.move(0f, 0.5f, 0f);
        rootNode.attachChild(target);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SlidingASphere app = new SlidingASphere();
        app.start();
    }

}
