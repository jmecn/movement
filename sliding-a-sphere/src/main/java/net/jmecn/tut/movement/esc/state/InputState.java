package net.jmecn.tut.movement.esc.state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector2f;

/**
 * @title InputState
 * @author yanmaoyuan
 * @date 2021年1月9日
 * @version 1.0
 */
public class InputState extends BaseAppState implements ActionListener {

    private InputManager inputManager;

    private final static String MOVE_LEFT     = "MOVE_LEFT";
    private final static String MOVE_RIGHT    = "MOVE_RIGHT";
    private final static String MOVE_FORWARD  = "MOVE_FORWARD";
    private final static String MOVE_BACKWARD = "MOVE_BACKWARD";

    private static String[] MAPPINGS = {
        MOVE_LEFT,
        MOVE_RIGHT,
        MOVE_FORWARD,
        MOVE_BACKWARD,
    };

    private boolean left     = false;
    private boolean right    = false;
    private boolean forward  = false;
    private boolean backward = false;

    private Vector2f playerInput = new Vector2f(0f, 0f);

    private Vector2f readOnly = new Vector2f();

    private float inputSensitive = 5f;

    public Vector2f getPlayerInput() {
        // return the copy of playerInput
        // so no one can modify the original value
        return readOnly.set(playerInput);
    }

    @Override
    protected void initialize(Application app) {
        this.inputManager = app.getInputManager();
    }

    @Override
    protected void cleanup(Application app) {
        for (String mapping : MAPPINGS) {
            if (inputManager.hasMapping(mapping)) {
                inputManager.deleteMapping(mapping);
            }
        }
    }

    @Override
    protected void onEnable() {
        inputManager.addMapping(MOVE_LEFT, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(MOVE_RIGHT, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping(MOVE_FORWARD, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(MOVE_BACKWARD, new KeyTrigger(KeyInput.KEY_S));

        inputManager.addListener(this, MAPPINGS);
    }

    @Override
    protected void onDisable() {
        for (String mapping : MAPPINGS) {
            if (inputManager.hasMapping(mapping)) {
                inputManager.deleteMapping(mapping);
            }
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
    }

}
