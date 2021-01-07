package net.jmecn.tut.movement;

import com.jme3.app.DetailedProfilerState;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;

import net.jmecn.tut.movement.common.TrailRenderer;

/**
 * @title TestTrail
 * @author yanmaoyuan
 * @date 2021年1月8日
 * @version 1.0
 */
public class TestTrail extends SimpleApplication {

    private Geometry target;

    private Vector3f pos = new Vector3f();

    @Override
    public void simpleInitApp() {
        stateManager.attach(new DetailedProfilerState());

        //flyCam.setEnabled(false);
        flyCam.setMoveSpeed(10f);

        // 
        Sphere mesh = new Sphere(32, 24, 2f);
        target = new Geometry("sphere");
        target.setMesh(mesh);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        target.setMaterial(mat);

        // 
//        ChaseCamera chase = new ChaseCamera(cam);
//        chase.setDefaultDistance(100f);
//        target.addControl(chase);

        // 
        TrailRenderer trail = new TrailRenderer(rootNode, assetManager, cam);
        target.addControl(trail);

        rootNode.attachChild(target);
    }

    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);

        pos.set(target.getLocalTranslation());
        pos.x += tpf * 3f;

        target.setLocalTranslation(pos);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setRenderer(AppSettings.LWJGL_OPENGL33);

        TestTrail app = new TestTrail();
        app.setSettings(settings);
        app.start();

    }

}
