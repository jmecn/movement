package net.jmecn.tut.movement.esc.state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

import net.jmecn.tut.movement.esc.component.Target;
import net.jmecn.tut.movement.esc.component.Velocity;

/**
 * @title InputState
 * @author yanmaoyuan
 * @date 2021年1月9日
 * @version 1.0
 */
public class PlayerInputState extends BaseAppState implements ActionListener {

    private InputManager inputManager;

    private EntityData ed;
    private EntitySet  entities;

    private final static String MOVE_LEFT     = "MOVE_LEFT";
    private final static String MOVE_RIGHT    = "MOVE_RIGHT";
    private final static String MOVE_FORWARD  = "MOVE_FORWARD";
    private final static String MOVE_BACKWARD = "MOVE_BACKWARD";

    private static String[] mappings = new String[] { MOVE_LEFT, MOVE_RIGHT, MOVE_FORWARD, MOVE_BACKWARD, };

    private boolean left     = false;
    private boolean right    = false;
    private boolean forward  = false;
    private boolean backward = false;

    private Vector2f playerInput = new Vector2f(0f, 0f);

    private float inputSensitive = 5f;

    // velocity
    private Vector3f desiredVelocity = new Vector3f(0f, 0f, 0f);
    private float    maxSpeed        = 10f;

    private Vector3f acceleration    = new Vector3f(0f, 0f, 0f);
    private float    maxAcceleration = 30f;

    @Override
    protected void initialize(Application app) {
        this.inputManager = app.getInputManager();

        // 筛选用于显示的实体
        ed = app.getStateManager().getState(EntityDataState.class).getEntityData();
        entities = ed.getEntities(Velocity.class, Target.class);
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
        inputManager.addMapping(MOVE_LEFT, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(MOVE_RIGHT, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping(MOVE_FORWARD, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(MOVE_BACKWARD, new KeyTrigger(KeyInput.KEY_S));

        inputManager.addListener(this, mappings);
    }

    @Override
    protected void onDisable() {
        for (String mapping : mappings) {
            inputManager.deleteMapping(mapping);
        }
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


    @Override
    public void update(float tpf) {
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

        // Acceleration
        acceleration.set(playerInput.x, 0f, playerInput.y);
        acceleration.multLocal(maxAcceleration);

        // Velocity
        desiredVelocity.set(playerInput.x, 0f, playerInput.y);
        desiredVelocity.multLocal(maxSpeed);

        float maxSpeedChange = maxAcceleration * tpf;

        entities.applyChanges();
        entities.forEach(it -> {
            Velocity v = ed.getComponent(it.getId(), Velocity.class);

            Vector3f velocity = v.getVelocity();
            velocity.x = moveToward(velocity.x, desiredVelocity.x, maxSpeedChange);
            velocity.z = moveToward(velocity.z, desiredVelocity.z, maxSpeedChange);

            ed.setComponent(it.getId(), new Velocity(velocity));
        });
    }

    float moveToward(float start, float end, float step) {
        if (start < end) {
            start = Math.min(start + step, end);
        } else if (start > end) {
            start = Math.max(start - step, end);
        }

        return start;
    }
}
