package com.packtpub.libgdx.flyordie.screens;

import com.badlogic.gdx.Game;
import com.packtpub.libgdx.flyordie.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.packtpub.libgdx.flyordie.game.WorldController;
import com.packtpub.libgdx.flyordie.game.WorldRenderer;

/**
 * This is handles the events the could take place
 * when the game is running.
 * 
 * Gregory L Whitman
 * 
 */
public class GameScreen extends AbstractGameScreen
{
	private static final String TAG = GameScreen.class.getName();
	
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	
	private boolean paused;
	
	/**
	 * Constructor that sets the game for the game screen
	 * @param game
	 */
	public GameScreen (Game game)
	{
		super(game);
	}
	
	/**
	 * render the screen 
	 */
	@Override
	public void render (float deltaTime)
	{
		// Do not update game world when paused.
		if (!paused)
		{
			// Update game world by the time that has passed
			// since last rendered frame.
			worldController.update(deltaTime);
		}
		
		// Sets the clear screen color to : Cornflower Blue
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed /
				255.0f, 0xff / 255.0f);
		
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Render game world to screen
		worldRenderer.render();
	}
	
	/**
	 * resize the screen
	 */
	@Override
	public void resize(int width, int height)
	{
		worldRenderer.resize(width, height);
	}
	
	/**
	 * show the game screen
	 */
	@Override
	public void show()
	{
		GamePreferences.instance.load();
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
		Gdx.input.setCatchBackKey(true);
	}
	
	/**
	 * hide the screen
	 */
	@Override
	public void hide()
	{
		worldController.dispose();
		worldRenderer.dispose();
		Gdx.input.setCatchBackKey(false);
	}
	
	/**
	 * pause the game and show that to the screen
	 */
	@Override
	public void pause()
	{
		paused = true;
	}
	
	/**
	 * resume the screen after pause
	 */
	@Override
	public void resume()
	{
		super.resume();
		
		// Only called on Android!!!
		paused = false;
	}
}
