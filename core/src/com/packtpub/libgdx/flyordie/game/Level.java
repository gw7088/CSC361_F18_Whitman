package com.packtpub.libgdx.flyordie.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.flyordie.game.objects.AbstractGameObject;
import com.packtpub.libgdx.flyordie.game.objects.Brick;
import com.packtpub.libgdx.flyordie.game.objects.Clouds;
import com.packtpub.libgdx.flyordie.game.objects.Pipe;

/**
 * Sets up the level
 * Gregory Whitman
 */
public class Level 
{
	public static final String TAG = Level.class.getName();
	
	/**
	 * Different possible blocks
	 */
	public enum BLOCK_TYPE 
	{	
		EMPTY(0, 0, 0), 			// black
		SPAWN(0, 0, 255), 			// blue 
		GOLD_COIN(0, 255, 0), 		// green
		FLOOR_SHAFT(255, 255, 0), 	// yellow
		GOAL(255, 0, 0),			// red
		GAP(50, 50, 50), 			// RGB Value Specific
		BORDER(0, 255, 255),		// sky blue
		BORDER_GAP(69, 69, 69);		// RGB specific
		
		// Converts
		private int color;
		private BLOCK_TYPE (int r, int g, int b)
		{
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}
		
		/**
		 * Checks if the colors are the same
		 * @param color
		 * @return
		 */
		public boolean sameColor (int color)
		{
			return this.color == color;
		}
		
		/**
		 * Gets the current color
		 * @return
		 */
		public int getColor ()
		{
			return color;
		}
	}
	
	// objects
	public Array<Brick> bricks;
	public Array<Pipe> pipes;
	
	// decoration
	public Clouds clouds;
	
	public Level (String filename)
	{
		init(filename);
	}
	
	/**
	 * Reads in the level and makes sure the colors used to I.D the specific
	 * elements of the level are placed correctly
	 * @param filename
	 */
	private void init (String filename)
	{
		// objects
		bricks = new Array<Brick>();
		pipes = new Array<Pipe>();
		
		// load image file that represents the level data
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		// scan pixels from top-left to bottom-right
		int lastPixel = -1;
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) 
		{
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) 
			{
				AbstractGameObject obj = null;
						
				// height grows from bottom to top
				float baseHeight = pixmap.getHeight() - pixelY;
						
				// get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
						
				// find matching color value to identify block type at (x,y)
				// point and create the corresponding game object if there is
				// a match
				
				// empty space
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) 
				{
						// do nothing
				}
				else if(BLOCK_TYPE.BORDER_GAP.sameColor(currentPixel))
				{
					if (lastPixel != currentPixel) 
					{
						obj = new Brick();
						obj.position.set(pixelX, baseHeight * obj.dimension.y + -6.0f);
						bricks.add((Brick)obj);
					} 
					else 
					{
						bricks.get(bricks.size - 1).increaseLength(1);
					}
					
				}
				else if(BLOCK_TYPE.BORDER.sameColor(currentPixel))
				{
					if (lastPixel != currentPixel) 
					{
						obj = new Brick();
						//offsetHeight = -2.5f;
						obj.position.set(pixelX, baseHeight * obj.dimension.y + -6.0f);
						bricks.add((Brick)obj);
					} 
					else 
					{
						bricks.get(bricks.size - 1).increaseLength(1);
					}
					
				}
				else if(BLOCK_TYPE.GAP.sameColor(currentPixel))
				{
					
				}
				// Pipe shaft
				else if (BLOCK_TYPE.FLOOR_SHAFT.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{
						obj = new Pipe();
						//offsetHeight = -2.5f;
						obj.position.set(pixelX, baseHeight * obj.dimension.y + -6.0f);
						pipes.add((Pipe)obj);
					} 
					else 
					{
						pipes.get(pipes.size - 1).increaseLength(1);
					}
				}
				// player spawn point
				else if (BLOCK_TYPE.SPAWN.sameColor(currentPixel)) 
				{
					
				}
						
				// Goal
				else if (BLOCK_TYPE.GOAL.sameColor(currentPixel)) 
				{
					
				}
						
				// gold coin
				else if (BLOCK_TYPE.GOLD_COIN.sameColor(currentPixel)) 
				{
					
				}
				// unknown object/pixel color
				else 
				{
					int r = 0xff & (currentPixel >>> 24); //red color channel
					int g = 0xff & (currentPixel >>> 16); //green color channel
					int b = 0xff & (currentPixel >>> 8); //blue color channel
					int a = 0xff & currentPixel; //alpha channel
					Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<"
							+ pixelY + ">: r<" + r+ "> g<" + g + "> b<" + b + "> a<" + a + ">");
				}
				lastPixel = currentPixel;
			}
		}
				
		// decoration
		clouds = new Clouds(pixmap.getWidth());
		clouds.position.set(0, 2);
				
		// free memory
		pixmap.dispose();
		Gdx.app.debug(TAG, "level '" + filename + "' loaded");
	}
	
	/**
	 * Draws the level in order specified
	 * @param batch
	 */
	public void render (SpriteBatch batch)
	{	
		
		// Draw Clouds
		clouds.render(batch);
		
		// Draw Brick Border
		for (Brick brick : bricks)
			brick.render(batch);
		
		// Draw Pipes
		for (Pipe pipe : pipes)
			pipe.render(batch);
	}
	
}
