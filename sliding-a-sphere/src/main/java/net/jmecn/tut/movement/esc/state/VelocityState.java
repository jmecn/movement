package net.jmecn.tut.movement.esc.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

import net.jmecn.tut.movement.esc.component.Target;
import net.jmecn.tut.movement.esc.component.Velocity;

/**
 * @title VelocityState
 * @author yanmaoyuan
 * @date 2021年1月9日
 * @version 1.0
 */
public class VelocityState extends AbstractAppState {

    private InputState inputState;

    private EntityData ed;
    private EntitySet  entities;

    // velocity
    private Vector3f desiredVelocity = new Vector3f(0f, 0f, 0f);
    private float    maxSpeed        = 10f;
    private float    maxAcceleration = 30f;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {

        inputState = stateManager.getState(InputState.class);

        // 筛选用于显示的实体
        ed = app.getStateManager().getState(EntityDataState.class).getEntityData();
        entities = ed.getEntities(Velocity.class, Target.class);
    }

    @Override
    public void update(float tpf) {
        if (inputState == null) {
            return;
        }

        Vector2f playerInput = inputState.getPlayerInput();

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

    private float moveToward(float start, float end, float step) {
        if (start < end) {
            start = Math.min(start + step, end);
        } else if (start > end) {
            start = Math.max(start - step, end);
        }

        return start;
    }
}
