package net.jmecn.tut.movement.control;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector2f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * @title MovingSphere
 * @author yanmaoyuan
 * @date 2021年1月8日
 * @version 1.0
 */
public class MovingSphere extends AbstractControl implements ActionListener {

    private final static String MOVE_LEFT     = "MOVE_LEFT";
    private final static String MOVE_RIGHT    = "MOVE_RIGHT";
    private final static String MOVE_FORWARD  = "MOVE_FORWARD";
    private final static String MOVE_BACKWARD = "MOVE_BACKWARD";

    private static String[] mappings = new String[] { MOVE_LEFT, MOVE_RIGHT, MOVE_FORWARD, MOVE_BACKWARD, };

    private Vector2f axis = new Vector2f(0f, 0f);

    private float speed = 5f;

    private boolean left     = false;
    private boolean right    = false;
    private boolean forward  = false;
    private boolean backward = false;

    private InputManager inputManager;

    public void registerInput(InputManager inputManager) {
        this.inputManager = inputManager;

        inputManager.addMapping(MOVE_LEFT, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(MOVE_RIGHT, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping(MOVE_FORWARD, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(MOVE_BACKWARD, new KeyTrigger(KeyInput.KEY_S));

        inputManager.addListener(this, mappings);
    }

    /**
     * Unregister this controller from its input manager.
     */
    public void unregisterInput() {
        if (inputManager == null) {
            return;
        }

        for (String s : mappings) {
            if (inputManager.hasMapping(s)) {
                inputManager.deleteMapping(s);
            }
        }

        inputManager.removeListener(this);
    }

    @Override
    protected void controlUpdate(float tpf) {
        float step = tpf * speed;
        if (left) {
            axis.x -= step;
        }
        if (right) {
            axis.x += step;
        }
        if (forward) {
            axis.y += step;
        }
        if (backward) {
            axis.y -= step;
        }

        // ClampMagnitude
        float length = axis.length();
        if (length >= 1f) {
            axis.divideLocal(length);
        }

        spatial.setLocalTranslation(axis.x, 0.5f, -axis.y);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
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

}