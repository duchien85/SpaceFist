﻿using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State
{
    // GameOverState draws the game over message until the player
    // hits enter.  When the player hits enter, the player is taken to to the
    // starting splash screen.
    public class GameOverState : GameState
    {
        private Game game;

        public GameOverState(Game game)
        {
            this.game = game;
        }

        public void LoadContent()
        {

        }

        public void EnteringState()
        {

        }

        public void Update()
        {
            KeyboardState keys = Keyboard.GetState();

            if (keys.IsKeyDown(Keys.Enter))
            {
                game.CurrentState = game.SplashScreenState;
            }
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            // Draw the game over image
            game.SpriteBatch.Draw(game.GameOverTexture, game.BackgroundRect, Color.White);
            game.SpriteBatch.Draw(game.GameOverTexture, game.BackgroundRect, Color.White);
            //If PlayTime is more than 60 senconds, display both a minute and second.
            if (game.gameData.minute > 0)
            {
                game.SpriteBatch.DrawString(game.Font, "PLAYTIME: " + game.gameData.minute.ToString() + " minutes " +
                game.gameData.second.ToString()+" seconds", new Vector2(550f, 450f), Color.Red);
            }
            else {

                game.SpriteBatch.DrawString(game.Font, "PLAYTIME: " + game.gameData.second.ToString()+" seconds", new Vector2(550f, 450f), Color.Red);
            }

            game.SpriteBatch.DrawString(game.Font, "SCORE: " + game.gameData.finalScore.ToString(), new Vector2(550f, 500f), Color.Red);
           
           
        }

        public void ExitingState()
        {

        }
    }
}
