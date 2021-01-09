package net.jmecn.tut.movement.esc.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

import net.jmecn.tut.movement.esc.component.Position;
import net.jmecn.tut.movement.esc.component.Velocity;

/**
 * @title MoveState
 * @author yanmaoyuan
 * @date 2021年1月9日
 * @version 1.0
 */
public class MoveState extends AbstractAppState {

    private Vector3f displacement = new Vector3f();

    // allowedArea
    private Vector4f allowedArea = new Vector4f(-4.5f, -4.5f, 4.5f, 4.5f);

    private float bounciness = 0.9f;

    private EntityData ed;
    private EntitySet  entities;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        ed = app.getStateManager().getState(EntityDataState.class).getEntityData();
        entities = ed.getEntities(Velocity.class, Position.class);
    }

    @Override
    public void update(float tpf) {
        entities.applyChanges();
        entities.forEach(it -> {
            Velocity v = ed.getComponent(it.getId(), Velocity.class);
            Position p = ed.getComponent(it.getId(), Position.class);

            Vector3f velocity = v.getVelocity();
            Vector3f position = p.getLocation();

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

            ed.setComponent(it.getId(), new Position(position));
        });
    }

}
