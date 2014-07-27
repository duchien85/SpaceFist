package com.spacefist.components;

import com.spacefist.GameData;
import com.spacefist.components.abst.PhysicsComponent;
import com.spacefist.entities.Entity;

/**
 * Updates The game objects position using its properties.
 */
public class Physics implements PhysicsComponent {
    public Physics() { }

    public void update(GameData gameData, Entity obj) {
        obj.setX(obj.getX() + (int) obj.getVelocity().x);
        obj.setY(obj.getY() + (int) obj.getVelocity().y);
    }
}
