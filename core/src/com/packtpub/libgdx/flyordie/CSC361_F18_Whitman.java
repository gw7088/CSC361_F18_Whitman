package com.packtpub.libgdx.flyordie;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.packtpub.libgdx.flyordie.game.WorldController;
import com.packtpub.libgdx.flyordie.game.WorldRenderer;
import com.badlogic.gdx.assets.AssetManager;
import com.packtpub.libgdx.flyordie.game.Assets;
/**
 * This is the main class for the Fly Or Die game
 * @author Greg Whitman
 *
 */
public class CSC361_F18_Whitman extends ApplicationAdapter 
{
	SpriteBatch batch;
	Texture img;
	private static final String TAG = CSC361_F18_Whitman.class.getName();
	
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	private boolean paused;
	
	/**
	 * Logic when the game is created
	 */
	@Override
	public void create () 
	{		
		// Set Libgdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.instance.init(new AssetManager());
		// Initialize controller and renderer
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		// Game world is active on start
		paused = false;
	}

	/**
	 * The main method that will render the game over 
	 * and over while the game is played
	 */
	@Override
	public void render () 
	{		
		// Do not update game world when paused
		if(!paused)
		{
			// Update game world by the time that has passed
			// since last rendered frame.
			worldController.update(Gdx.graphics.getDeltaTime());
		}
		
		// Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f, 0xed/255.0f, 0xff/255.0f);
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Render game world to screen
		worldRenderer.render();
	}
		
	/**
	 * Method to allow resizing the world
	 */
	@Override 
	public void resize (int width, int height) 
	{
		worldRenderer.resize(width, height);
	}
	
	/**
	 * Logic to pause the game
	 */
	@Override 
	public void pause() 
	{
		paused = true;
	}
	
	/**
	 * Logic to resume playing the game
	 */
	@Override 
	public void resume() 
	{
		Assets.instance.init(new AssetManager());
		paused = false;
	}
	
	/**
	 * Method that will dispose resources to prevent 
	 * memory issues with the underlying C code.
	 */
	@Override 
	public void dispose() 
	{
		worldRenderer.dispose();
		Assets.instance.dispose();
	}
}
