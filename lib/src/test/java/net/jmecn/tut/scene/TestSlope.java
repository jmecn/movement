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
public class TestSlope extends SimpleApplication {

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
        p.setY(3);
        p.setZ(6);
        p.setBuildSide(true);
        for (int i = 0; i <= 6; i++) {
            p.setSteps(6 + i);
            Geometry geom = new Geometry("pst" + i, p.build());

            if (n % 2 == 0) {
                geom.setMaterial(yellow);
            } else {
                geom.setMaterial(white);
            }
            n++;

            geom.setLocalTranslation(i * 7, 0, 0);
            rootNode.attachChild(geom);

            Geometry geom2 = new Geometry("psl" + i, p.buildSlope());
            
            if (n % 2 == 0) {
                geom2.setMaterial(yellow);
            } else {
                geom2.setMaterial(white);
            }
            n++;
            
            geom2.setLocalTranslation(i * 7 + 3, 0, 0);
            rootNode.attachChild(geom2);
        }

        Plane plane = new Plane();
        plane.setX(120);
        plane.setY(50);
        plane.setAxis(Axis.Y);
        Geometry geom = new Geometry("gy", plane.build());
        geom.setMaterial(green);
        geom.setLocalTranslation(0, 0, 0);
        rootNode.attachChild(geom);

        flyCam.setEnabled(true);
        flyCam.setMoveSpeed(100f);

        // lighting
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White.mult(0.5f));
        dl.setDirection(new Vector3f(-3, -2, 3).normalizeLocal());
        rootNode.addLight(dl);

        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(0.5f));
        rootNode.addLight(al);

    }

    public static void main(String[] args) {
        TestSlope app = new TestSlope();
        app.start();
    }

}