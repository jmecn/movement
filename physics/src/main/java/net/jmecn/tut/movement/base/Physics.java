package net.jmecn.tut.movement.base;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.BulletAppState.ThreadingType;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;

import net.jmecn.tut.movement.trail.TrailRenderer;
import net.jmecn.tut.scene.shape.Prism;

/**
 * @title SlidingASphere
 * @author yanmaoyuan
 * @date 2021年1月8日
 * @version 1.0
 */
public class Physics extends SimpleApplication implements ActionListener {

    private final static String MOVE_LEFT     = "MOVE_LEFT";
    private final static String MOVE_RIGHT    = "MOVE_RIGHT";
    private final static String MOVE_FORWARD  = "MOVE_FORWARD";
    private final static String MOVE_BACKWARD = "MOVE_BACKWARD";

    private static String[] mappings = new String[] { MOVE_LEFT, MOVE_RIGHT, MOVE_FORWARD, MOVE_BACKWARD, };

    private boolean left     = false;
    private boolean right    = false;
    private boolean forward  = false;
    private boolean backward = false;

    // playerInput
    private Vector2f playerInput    = new Vector2f(0f, 0f);
    private float    inputSensitive = 5f;

    // velocity
    private Vector3f desiredVelocity = new Vector3f(0f, 0f, 0f);
    private float    maxSpeed        = 10f;
    private float    maxAcceleration = 30f;

    private Vector3f displacement = new Vector3f();

    // allowedArea
    private Vector4f allowedArea = new Vector4f(-4.5f, -4.5f, 4.5f, 4.5f);

    private float bounciness = 0.9f;

    // physics
    PhysicsSpace pSpace = null;

    private Geometry target;

    private Vector3f position = new Vector3f(0f, 0.5f, 0f);
    private Vector3f velocity = new Vector3f(0f, 0f, 0f);

    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(new ColorRGBA(0.8f, 0.8f, 0.8f, 1f));

        flyCam.setEnabled(false);

        cam.setLocation(new Vector3f(0, 30, 0));
        cam.lookAtDirection(new Vector3f(0, -1, 0), Vector3f.UNIT_Z.negate());

        // lighting
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White.mult(0.3f));
        dl.setDirection(new Vector3f(-3, -4, -5).normalizeLocal());
        rootNode.addLight(dl);

        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(0.7f));
        rootNode.addLight(al);

        initPhysics();

        initGround();

        initSphere();

        initShape();
        // input
        registerInput();
    }

    private void initPhysics() {
        BulletAppState bullet = new BulletAppState();
        bullet.setThreadingType(ThreadingType.PARALLEL);
        bullet.setDebugEnabled(true);
        stateManager.attach(bullet);

        pSpace = bullet.getPhysicsSpace();
    }

    private void initGround() {
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
    }

    private void initSphere() {
        target = new Geometry("sphere", new Sphere(32, 24, 0.5f));

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

        target.setLocalTranslation(position);
        rootNode.attachChild(target);
    }

    private void initShape() {
        Mesh mesh = new Prism().build();
        CollisionShape shape = new MeshCollisionShape(mesh);
        PhysicsRigidBody rigidBody = new PhysicsRigidBody(shape, 0f);
        
        pSpace.add(rigidBody);
    }

    @Override
    public void simpleUpdate(float tpf) {
        float step = tpf * inputSensitive;
        if (!left && !right) {
            playerInput.x = 0;
        } else {
            if (left) {
                playerInput.x -= step;
            }
            if (right) {
                playerInput.x += step;
            }
        }
        if (!forward && !backward) {
            playerInput.y = 0;
        } else {
            if (forward) {
                playerInput.y -= step;
            }
            if (backward) {
                playerInput.y += step;
            }
        }

        // ClampMagnitude
        float length = playerInput.length();
        if (length >= 1f) {
            playerInput.divideLocal(length);
        }

        // Velocity
        desiredVelocity.set(playerInput.x, 0f, playerInput.y);
        desiredVelocity.multLocal(maxSpeed);

        float maxSpeedChange = maxAcceleration * tpf;
        velocity.x = moveToward(velocity.x, desiredVelocity.x, maxSpeedChange);
        velocity.z = moveToward(velocity.z, desiredVelocity.z, maxSpeedChange);

        velocity.mult(tpf, displacement);

        position.addLocal(displacement);

        if (position.x < allowedArea.x) {
            position.x = allowedArea.x;
            velocity.x = -velocity.x * bounciness;
        } else if (position.x > allowedArea.z) {
            position.x = allowedArea.z;
            velocity.x = -velocity.x * bounciness;
        }
        if (position.z < allowedArea.y) {
            position.z = allowedArea.y;
            velocity.z = -velocity.z * bounciness;
        } else if (position.z > allowedArea.w) {
            position.z = allowedArea.w;
            velocity.z = -velocity.z * bounciness;
        }
        target.setLocalTranslation(position);
    }

    float moveToward(float start, float end, float step) {
        if (start < end) {
            start = Math.min(start + step, end);
        } else if (start > end) {
            start = Math.max(start - step, end);
        }

        return start;
    }

    public void registerInput() {
        inputManager.addMapping(MOVE_LEFT, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(MOVE_RIGHT, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping(MOVE_FORWARD, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(MOVE_BACKWARD, new KeyTrigger(KeyInput.KEY_S));

        inputManager.addListener(this, mappings);
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        switch (name) {
            case MOVE_LEFT:
                left = isPressed;
                break;
            case MOVE_RIGHT:
                right = isPressed;
                break;
            case MOVE_FORWARD:
                forward = isPressed;
                break;
            case MOVE_BACKWARD:
                backward = isPressed;
                break;
        }

    }

    public static void main(String[] args) {
        Physics app = new Physics();
        app.start();
    }

}
