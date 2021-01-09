package net.jmecn.tut.movement.esc;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;

import net.jmecn.tut.movement.esc.state.EntityDataState;
import net.jmecn.tut.movement.esc.state.MoveState;
import net.jmecn.tut.movement.esc.state.PlayerInputState;
import net.jmecn.tut.movement.esc.state.SceneState;

/**
 * @title SlidingASphere
 * @author yanmaoyuan
 * @date 2021年1月9日
 * @version 1.0
 */
public class SlidingASphere extends SimpleApplication {

    public SlidingASphere() {
        super(new StatsAppState(), new DebugKeysAppState(), new EntityDataState());
    }

    @Override
    public void simpleInitApp() {
        stateManager.attachAll(new SceneState(), new PlayerInputState(), new MoveState());
    }

    public static void main(String[] args) {
        SlidingASphere app = new SlidingASphere();
        app.start();
    }
}
