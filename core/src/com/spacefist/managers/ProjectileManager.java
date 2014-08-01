package com.spacefist.managers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.entities.Entity;
import com.spacefist.entities.Projectile;

import java.util.ArrayList;
import java.util.List;

/// <summary>
/// Keeps track of the projectiles in the world.
/// </summary>
public class ProjectileManager extends Manager<Projectile> {

    /// <summary>
    /// Creates a new ProjectileManager instance
    /// </summary>
    /// <param name="gameData">Common game data</param>
    public ProjectileManager(GameData gameData) {
        super(gameData);
    }

    public void Update() {

        for (Projectile projectile : entities) {
            Rectangle resolution = gameData.getResolution();

            Rectangle rect = new Rectangle(
                (int)gameData.getCamera().x,
                (int)gameData.getCamera().y,
                resolution.getWidth(),
                resolution.getHeight()
            );

            // Mark offscreen live projectiles as dead and
            // only update onscreen projectiles.
            if (projectile.isAlive())
            {
                if (rect.contains(projectile.getRectangle()))
                {
                    projectile.Update();
                }
                else
                {
                    projectile.setAlive(false);
                }
            }
        }
    }

    /// <summary>
    /// Places a laser at the specified location and fires it.
    /// </summary>
    /// <param name="x">The X value of the location</param>
    /// <param name="y">The Y value of the location</param>
    public void fireLaser(int x, int y)
    {
        fireLaser(x, y, new Vector2(0, -1), false);
    }

    /// <summary>
    /// Fires a laser from the specified location in the specified direction.
    /// </summary>
    /// <param name="x">The X value of the location</param>
    /// <param name="y">The Y value of the location</param>
    /// <param name="direction">The direction to send the projectile</param>
    /// <param name="enemyLaser">Whether this laser belongs to an enemy</param>
    public void fireLaser(int x, int y, Vector2 direction, boolean enemyLaser)
    {
        gameData.getRoundData().setShotsFired(gameData.getRoundData().getShotsFired() + 1);

        float rotation = ((float) Math.toDegrees((float) Math.atan2(direction.x, direction.y)) + 90);

        // Place a new active laser at x, y
        Projectile projectile = new Projectile(
            gameData,
            gameData.getTextures().get("Laser"),
            new Vector2(x, y),
            direction,
            9,
            enemyLaser
        );

        projectile.setRotation((float) Math.toRadians(rotation));

        Add(projectile);

    }

    /// <summary>
    /// Fires a rocket cluster from the specified location
    /// </summary>
    /// <param name="x">The X component of the location</param>
    /// <param name="y">The Y component of the location</param>
    public void fireSampleWeapon(int x, int y)
    {
        /*
        TODO: Convert EnemyManager
        TODO: Add BlockManager to gameData
        List<Entity> visibleEnemies = new List<Entity>(gameData.getEnemyManager().getVisibleEnemies());
        List<SpaceBlock> visibleBlocks = gameData.getBlockManager().getVisibleBlocks();
        */

        List<Entity> visibleEnemies = new ArrayList<Entity>();
        List<Entity> visibleBlocks = new ArrayList<Entity>();

        List<Entity> onScreen = new ArrayList<Entity>();

        onScreen.addAll(visibleEnemies);
        onScreen.addAll(visibleBlocks);

        for(Entity entity : onScreen) {
            if(entity.getY() >= y) {
                onScreen.remove(entity);
            }
        }

        if (!onScreen.isEmpty())
        {
            // Mark several onscreen entities as targets
            // and send rockets to intercept them.
            for (int i = 0; i < 4; i++)
            {
                int idx = MathUtils.random(onScreen.size());

                Entity target = onScreen.get(idx);

                // Convert tinting code in ProjectileManager
                // Mark targeted entities by tinting them red
                // target.Tint = Color.Crimson;

                gameData.getRoundData().shotFired();

                Projectile projectile = new Projectile(
                    gameData,
                    gameData.getTextures().get("SampleWeapon"),
                    new Vector2(x, y),
                    new Vector2(0, -1),
                    10,
                    false
                );

                // TODO: Convert Projectile.setBehavior
                    /*
                    projectile.setBehavior(new SeekingBehavior(
                        new Vector2(0, -1),
                        new Vector2(x, y),
                        target
                    ));
                    */

                Add(projectile);
            }
        }
    }

    /*******Dongcai**********/
    public void fireBluelaser(int x, int y)
    {
        gameData.getRoundData().shotFired();

        Projectile projectile = new Projectile(
            gameData,
            gameData.getTextures().get("Mine"),
            new Vector2(x, y),
            new Vector2(0, 0),
            0,
            false
        );

        Add(projectile);
    }

    public void fireMissile(int x, int y)
    {
        gameData.getRoundData().shotFired();

        Projectile projectile = new Projectile(
            gameData,
            gameData.getTextures().get("Missile"),
            new Vector2(x, y),
            new Vector2(0, -1),
            20,
            false
        );

        Add(projectile);

        Projectile projectile1 = new Projectile(
            gameData,
            gameData.getTextures().get("Missile"),
            new Vector2(x + 50, y),
            new Vector2(0, -1),
            10,
            false
        );

        Add(projectile1);

        Projectile projectile2 = new Projectile(
            gameData,
            gameData.getTextures().get("Missile"),
            new Vector2(x - 50, y),
            new Vector2(0, -1),
            10,
            false
        );

        Add(projectile2);

    }
    /***************************************/

    /// <returns>
    /// Returns a collection of all of the live projectiles
    /// fired by the player.
    /// </returns>
    public Iterable<Projectile> PlayerProjectiles()
    {
        List<Projectile> playerProjs = new ArrayList<Projectile>();

        for(Projectile projectile : ((List<Projectile>)entities)) {
            if ((projectile.isEnemyProjectile() == false) && projectile.isAlive()) {
                playerProjs.add(projectile);
            }
        }

        return playerProjs;
    }

    /// <summary>
    /// Returns a collection of all of the live projectiles
    /// fired by an enemy.
    /// </summary>
    /// <returns></returns>
    public Iterable<Projectile> EnemyProjectiles()
    {
        List<Projectile> enemyProjs = new ArrayList<Projectile>();

        for(Projectile projectile : ((Iterable<Projectile>) entities)) {
            if (projectile.isEnemyProjectile() && projectile.isAlive()) {
                enemyProjs.add(projectile);
            }
        }

        return enemyProjs;
    }
}
