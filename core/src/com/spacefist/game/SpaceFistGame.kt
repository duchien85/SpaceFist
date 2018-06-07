package com.spacefist.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.spacefist.GameData
import com.spacefist.state.CreditsState
import com.spacefist.state.EndOfGameState
import com.spacefist.state.EndOfLevelState
import com.spacefist.state.GameOverState
import com.spacefist.state.InPlayState
import com.spacefist.state.LogoState
import com.spacefist.state.MenuState
import com.spacefist.state.SplashScreenState
import com.spacefist.state.abst.GameState

class SpaceFistGame : ApplicationAdapter() {
    internal var batch: SpriteBatch? = null
    var gameData: GameData? = null
        private set

    override fun create() {
        batch = SpriteBatch()

        gameData = GameData(this)

        // Creates the game states when the game starts.
        // only one state is active at any given time.
        gameData!!.splashScreenState = SplashScreenState(gameData!!)
        gameData!!.menuState = MenuState(gameData!!)
        gameData!!.logoState = LogoState(gameData!!)
        gameData!!.inPlayState = InPlayState(gameData!!)
        gameData!!.gameOverState = GameOverState(gameData!!)
        gameData!!.creditsState = CreditsState(gameData!!)
        gameData!!.endOfLevelState = EndOfLevelState(gameData!!)
        gameData!!.endOfGameState = EndOfGameState(gameData!!)

        gameData!!.currentState = gameData!!.logoState

        loadContent()
    }

    override fun render() {
        // Tell the current state to update itself
        val currentState = gameData!!.currentState

        currentState.update()

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch!!.begin()
        // Tell the current state to draw itself
        currentState.draw()
        batch!!.end()
    }

    override fun dispose() {
        gameData!!.dispose()
    }

    private fun loadTextures() {
        val textures = gameData!!.textures

        val directory = Gdx.files.absolute("images/")
        for (fileHandle in directory.list()) {
            if (fileHandle.isDirectory) {
                for (file in fileHandle.list(".png")) {
                    textures[file.nameWithoutExtension()] = Texture(file)
                }
            }
        }
    }

    private fun loadSongs() {
        assert(gameData!!.songs != null)

        val songs = gameData!!.songs
        val directory = Gdx.files.absolute("sound/songs/")

        for (fileHandle in directory.list()) {
            songs[fileHandle.nameWithoutExtension()] = Gdx.audio.newMusic(fileHandle)
        }
    }

    private fun loadSoundEffects() {
        assert(gameData!!.soundEffects != null)

        val soundEffects = gameData!!.soundEffects
        val directory = Gdx.files.absolute("sound/soundeffects/")

        for (fileHandle in directory.list()) {
            soundEffects[fileHandle.nameWithoutExtension()] = Gdx.audio.newSound(fileHandle)
        }
    }

    protected fun loadContent() {
        val height = Gdx.graphics.height
        val width = Gdx.graphics.width

        gameData!!.resolution = Rectangle(0f, 0f, width.toFloat(), height.toFloat())
        gameData!!.screenScale = 0.5f

        // Create a new SpriteBatch, which can be used to draw textures.
        gameData!!.spriteBatch = batch

        // ----------------------------- Load the games assets -----------
        gameData!!.font = BitmapFont(Gdx.files.internal("fonts/font.fnt"))

        //GameData.TitleFont = Content.Load<SpriteFont>(TITLE_FONT_ASSET);

        loadTextures()
        loadSongs()
        loadSoundEffects()


        // GameData.InPlayState.loadContent();
        gameData!!.gameOverState.loadContent()

        gameData!!.splashScreenState.loadContent()
        gameData!!.menuState.loadContent()
        gameData!!.logoState.loadContent()
        gameData!!.endOfLevelState.loadContent()

        gameData!!.currentState.enteringState()
    }

    companion object {
        private val SPRITE_FONT_ASSET = "Fonts/Raised"
        private val TITLE_FONT_ASSET = "Fonts/Title"
    }
}
