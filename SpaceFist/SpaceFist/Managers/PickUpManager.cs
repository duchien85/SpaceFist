﻿using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using SpaceFist.Weapons;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    // Keeps track of the pickups in the world
    public class PickUpManager : IEnumerable<Pickup>
    {
        private Game         game;
        private Rectangle    screen;
        private List<Pickup> pickups;
        private Random rand = new Random();

        public PickUpManager(Game game, Rectangle screen)
        {
            this.game    = game;
            this.screen  = screen;
            this.pickups = new List<Pickup>();
        }

        public void Update()
        {
            pickups.ForEach(pickup => pickup.Update());
        }

        public void Remove(Pickup pickup)
        {
            pickups.Remove(pickup);
        }

        public IEnumerable<Pickup> Collisions(Entity entity)
        {
            var collisions =
                from pickup in pickups
                where pickup.Alive && pickup.Rectangle.Intersects(entity.Rectangle)
                select pickup;

            return collisions;
        }

        public void Draw()
        {
            pickups.ForEach(pickup => pickup.Draw());
        }

        public IEnumerator<Pickup> GetEnumerator()
        {
            return pickups.GetEnumerator();
        }
       

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return this.GetEnumerator();
        }

        public void SpawnPickups(int count, Action<int, int> spawnFunction)
        {
            var world = game.InPlayState.World;

            for (int i = 0; i < count; i++)
            {
                int randX = rand.Next(0, world.Width);
                int randY = rand.Next(0, world.Height);

                spawnFunction(randX, randY);
            }
        }

        public void SpawnExamplePickups(int count) {
            SpawnPickups(count, SpawnExamplePickup);
        }

        public void SpawnExamplePickup(int x, int y)
        {
            var pickup =
                new Pickup(
                    game,
                    game.SamplePickup,
                    game.ExplosionSound,
                    new Vector2(x, y),
                    Vector2.Zero,
                    (ship) => {
                        ship.Weapon = new SampleWeapon(game, ship);
                        return true;
                    });

            pickup.Tint = Color.Red;

            pickups.Add(pickup);
        }

        public void SpawnHealthPickups(int count)
        {
            SpawnPickups(count, SpawnHealthPickup);
        }

        public void SpawnHealthPickup(int x, int y)
        {
            var pickup =
                new Pickup(
                    game,
                    game.SamplePickup,
                    game.ExplosionSound,
                    new Vector2(x, y),
                    Vector2.Zero,
                    (ship) => {
                        if(ship.Health < 1) {
                            ship.HealthPoints = 100;
                            ship.ResetState();
                            return true;
                        }

                        return false;
                        
                    });

            pickup.Tint = Color.Pink;

            pickups.Add(pickup);
        }
    }
}
