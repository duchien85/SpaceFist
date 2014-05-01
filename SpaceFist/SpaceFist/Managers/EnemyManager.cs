﻿using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.AI.DummyAI;
using SpaceFist.Entities.Enemies;

namespace SpaceFist.Managers
{
    public class EnemyManager : IEnumerable<Enemy>
    {
        private Random      rand;
        private List<Enemy> enemies;
        private Game        game;

        public EnemyManager(Game game)
        {
            rand        = new Random();
            this.game   = game;
            enemies     = new List<Enemy>();
        }

        public void SpawnEnemyFighters(int count)
        {
            SpawnEnemies(count, position => new EnemyFighter(game, position));
        }

        public void SpawnEnemyFreighters(int count)
        {
            SpawnEnemies(count, position => new EnemyFreighter(game, position));
        }

        private void SpawnEnemies(int count, Func<Vector2, Enemy> func){
            for (int i = 0; i < count; i++)
            {
                int randX = rand.Next(0, game.InPlayState.World.Width);

                int randY = rand.Next(
                    0, 
                    (int)MathHelper.Max(
                        game.InPlayState.World.Height * .9f, 
                        game.Resolution.Height / 2
                    )
                );
                
                float rotation = MathHelper.ToRadians(180);
                Enemy enemy = func(new Vector2(randX, randY));
                enemy.Rotation = rotation;
                enemies.Add(enemy);
            }
        }

        public void Update(){
            foreach(var enemy in enemies) {
                if(enemy.Alive) {
                    enemy.Update();
                }
            }
        }
        
        public void Draw() {
            enemies.ForEach(enemy => enemy.Draw());
        }

        public IEnumerator<Enemy> GetEnumerator()
        {
            return enemies.GetEnumerator();
        }

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        public IEnumerable<Enemy> VisibleEnemies()
        {
            var camera         = game.InPlayState.Camera;
            var backgroundRect = game.BackgroundRect;

            var screenRect = new Rectangle(
                (int)camera.X, 
                (int)camera.Y, 
                backgroundRect.Width, 
                backgroundRect.Height
            );

            var res =
                from   enemy in enemies
                where  enemy.Alive && screenRect.Intersects(enemy.Rectangle)
                select enemy;

            return res;
        }

        public IEnumerable<Entity> Collisions(Entity entity)
        {
            var res =
                from   enemy in enemies
                where  enemy.Alive       && 
                       (enemy != entity) && 
                       enemy.Rectangle.Intersects(entity.Rectangle)
                select enemy;

            return res;
        }

        public void Clear()
        {
            enemies.Clear();
        }
    }
}
