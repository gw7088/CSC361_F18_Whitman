package com.packtpub.libgdx.flyordie.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com. packtpub.libgdx.flyordie.game.Assets;;

/**
 * abstract game screen contains the methods that we'll use to display
 * the game to the gui
 * @author Denny Fleagle
 *
 */
public abstract class AbstractGameScreen implements Screen
{
	protected Game game;
	
	/**
	 * constructor to assign game 
	 * @param game
	 */
	public AbstractGameScreen(Game game)
	{
		this.game = game;
	}
	
	/**
	 * method used for rendering objects to the gui
	 */
	public abstract void render(float deltaTime);
	
	/**
	 * method for resizing the gui
	 */
	public abstract void resize(int width, int height);
	
	/**
	 * method for showing the gui
	 */
	public abstract void show();
	
	/**
	 * method for hiding the gui 
	 */
	public abstract void hide();
	
	/**
	 * method for pausing the gui
	 */
	public abstract void pause();
	
	/**
	 * method that allows us to resume the gui
	 */
	public void resume()
	{
		Assets.instance.init(new AssetManager());
	}
	
	/**
	 * method that allows us to dispose of the gui
	 */
	public void dispose()
	{
		Assets.instance.dispose();
	}
}
