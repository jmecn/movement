package net.jmecn.tut.scene;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

import net.jmecn.tut.scene.shape.Plane;
import net.jmecn.tut.scene.shape.Plane.Axis;
import net.jmecn.tut.scene.shape.Stair;

/**
 * @title TestMeshBuilder
 * @author yanmaoyuan
 * @date 2021年1月11日
 * @version 1.0
 */
public class TestStair extends SimpleApplication {

    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 0.9f, 1f));

        cam.setLocation(new Vector3f(48.559017f, 70.967674f, 57.164276f));
        cam.setRotation(new Quaternion(-0.06531386f, 0.8960698f, -0.41592395f, -0.14071321f));

        TextureKey tk = new TextureKey("Textures/grid.png");
        tk.setGenerateMips(true);
        tk.setAnisotropy(4);
        Texture texture = assetManager.loadTexture(tk);
        texture.setWrap(WrapMode.Repeat);

        Material yellow = new Material(assetManager, Materials.LIGHTING);
        yellow.setColor("Diffuse", ColorRGBA.Yellow);
        yellow.setColor("Ambient", ColorRGBA.Yellow);
        yellow.setColor("Specular", ColorRGBA.White);
        yellow.setTexture("DiffuseMap", texture);
        yellow.setBoolean("UseMaterialColors", true);
        
        Material white = new Material(assetManager, Materials.LIGHTING);
        white.setColor("Diffuse", ColorRGBA.White);
        white.setColor("Ambient", ColorRGBA.White);
        white.setColor("Specular", ColorRGBA.White);
        white.setTexture("DiffuseMap", texture);
        white.setBoolean("UseMaterialColors", true);
        
        Material green = new Material(assetManager, Materials.LIGHTING);
        green.setColor("Diffuse", new ColorRGBA(0.7f, 1f, 0.7f, 1f));
        green.setColor("Ambient", new ColorRGBA(0.7f, 1f, 0.7f, 1f));
        green.setColor("Specular", ColorRGBA.White);
        green.setTexture("DiffuseMap", texture);
        green.setBoolean("UseMaterialColors", true);

        int n = 0;
        Stair p = new Stair();
        p.setX(3);
        p.setY(10);
        p.setZ(10);
        p.setBuildSide(true);
        for (int i = 2; i < 10; i++) {
            p.setSteps(i);
            Geometry geom = new Geometry("p"+i, p.build());

            if (n % 2 == 0) {
                geom.setMaterial(yellow);
            } else {
                geom.setMaterial(white);
            }
            n++;

            geom.setLocalTranslation(i*3, 0, 0);
            rootNode.attachChild(geom);
        }

        Plane plane = new Plane();
        plane.setX(69);
        plane.setY(45);
        plane.setAxis(Axis.Y);
        Geometry geom = new Geometry("gy", plane.build());
        geom.setMaterial(green);
        geom.setLocalTranslation(-3, 0, 25);
        rootNode.attachChild(geom);

        flyCam.setEnabled(true);
        flyCam.setMoveSpeed(100f);

        // lighting
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White.mult(0.3f));
        dl.setDirection(new Vector3f(-1, -5, 1).normalizeLocal());
        rootNode.addLight(dl);
        
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(0.7f));
        rootNode.addLight(al);

    }

    public static void main(String[] args) {
        TestStair app = new TestStair();
        app.start();
    }

}
