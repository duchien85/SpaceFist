package com.spacefist.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.spacefist.GameData;
import com.spacefist.components.abst.InputComponent;
import com.spacefist.entities.Entity;
import com.spacefist.entities.Ship;

/**
 * Tells the ship to move in response to user input.
 */
public class ShipInput implements InputComponent {
    private boolean spaceDown = false;
    private boolean aDown     = false;
    private boolean dDown     = false;

    public void update(GameData gameData, Entity obj) {
        Ship ship = (Ship) obj;

        boolean w     = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean a     = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean s     = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean d     = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean space = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        if (w) {
            ship.forward();
        }

        if (!a && aDown) {
            aDown = false;
            ship.reset();
        }

        if (a) {
            aDown = true;
            ship.left();
        }

        if (!d && dDown) {
            dDown = false;
            ship.reset();
        }

        if (d) {
            dDown = true;
            ship.right();
        }

        if (s) {
            ship.backward();
        }

        if (space) {
            if (!spaceDown) {
                spaceDown = true;
                ship.fire();
            }
        }

        if (!space) {
            spaceDown = false;
        }
    }
}
