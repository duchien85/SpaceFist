/***Doncai******/
package com.spacefist.weapons;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.managers.ProjectileManager;
import com.spacefist.weapons.abst.Weapon;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a cluster of three missiles.
 */
public class Missile implements Weapon {
    private ProjectileManager projectileManager;
    private GameData gameData;

    public Missile(@NotNull GameData gameData) {
        this.gameData = gameData;

        projectileManager = gameData.getProjectileManager();
    }

    /**
     * Fires a missile in the direction the ship is facing.
     */
    @Override
    public void fire() {
        Ship ship = gameData.getShip();

        float shipWidth  = ship.getRectangle().getWidth();
        float shipHeight = ship.getRectangle().getWidth();

        int projectileX = (int) ((ship.getX() + (shipWidth / 2)) - 6);
        int projectileY = (int) ((ship.getY() + (shipHeight * 1.5f)));

        projectileManager.fireMissile(projectileX, projectileY);
    }
}
/***Doncai******/