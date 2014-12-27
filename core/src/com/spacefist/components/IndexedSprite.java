package com.spacefist.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.components.abst.GraphicsComponent;
import com.spacefist.entities.Entity;
/**
 * Given a texture and a frame width and height,
 * this component will draw a portion of the texture.
 *
 * Index is used to determine which frame of the image is being drawn.
 * This is used by Ship to draw itself turning. The image contains all of the images of the ship.
 * When the ship turns, it sets the Index property of its graphics component to draw the correct frame.
 *
 * Explosion.java also uses IndexedSprite as its graphics component to draw an animation
 */
 public class IndexedSprite implements GraphicsComponent {
    private Texture texture;
    private int     width;
    private int     height;
    private int     index;

    /**
     * Provides the ability to draw the cells of a horizontal texture atlas.
     *
     * @param texture The texture atlas
     * @param width The width of each frame
     * @param height The height of each frame
     * @param index The index of the currently visible frame
     */
    public IndexedSprite(Texture texture, int width, int height, int index) {
        this.width   = width;
        this.height  = height;
        this.index   = index;
        this.texture = texture;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void draw(GameData gameData, Entity obj) {

        SpriteBatch spriteBatch = gameData.getSpriteBatch();
        spriteBatch.setColor(new Color(obj.getTint()));

        // Calculate and draw the image at an offset (this causes the image to rotate around
        // its center and not its upper left corner
        Rectangle objRect = obj.getRectangle();

        Vector2 origin   = new Vector2(
            objRect.getWidth()  / 2,
            objRect.getHeight() / 2
        );

        Vector2 position    = new Vector2(obj.getX(), obj.getY()).add(origin);
        Vector2 adjPosition = position.sub(gameData.getCamera());


        spriteBatch.draw(
                new TextureRegion(texture, width * index, 0, width, height),
                //new TextureRegion(texture),
                adjPosition.x,
                adjPosition.y,
                origin.x,
                origin.y,
                objRect.getWidth(),
                objRect.getHeight(),
                gameData.getScreenScale(),
                gameData.getScreenScale(),
                obj.getRotation()
        );

        // TODO: Add object tinting
        // obj.Tint,
        //spriteBatch.setColor(new Color(Color.WHITE));
        spriteBatch.flush();
    }

    @Override
    public void update(GameData gameData, Entity obj) { }
}

