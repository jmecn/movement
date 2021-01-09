package net.jmecn.tut.movement.esc.component;

import com.simsilica.es.EntityComponent;

/**
 * @title Target
 * @author yanmaoyuan
 * @date 2021年1月9日
 * @version 1.0
 */
public class Target implements EntityComponent {

    private String name;

    public Target(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}