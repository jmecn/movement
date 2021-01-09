package net.jmecn.tut.movement.esc.state;

import com.jme3.app.state.AbstractAppState;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;

/**
 * @title EntityDataState
 * @author yanmaoyuan
 * @date 2021年1月9日
 * @version 1.0
 */
public class EntityDataState extends AbstractAppState {

    private EntityData entityData;

    public EntityDataState() {
        this(new DefaultEntityData());
    }

    public EntityDataState(EntityData ed) {
        this.entityData = ed;
    }

    public EntityData getEntityData() {
        return entityData;
    }

    @Override
    public void cleanup() {
        entityData.close();
        entityData = null;
    }
}