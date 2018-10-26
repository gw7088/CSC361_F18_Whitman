package com.packtpub.libgdx.flyordie;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.packtpub.libgdx.flyordie.game.Assets;
import com.packtpub.libgdx.flyordie.screens.MenuScreen;
import com.packtpub.libgdx.flyordie.util.AudioManager;
import com.packtpub.libgdx.flyordie.util.GamePreferences;
/**
 * This is the main class for the canyonbunny game
 * @author Denny Fleagle
 *
 */
public class CSC361_F18_Whitman extends Game
{
	/**
	 * start the game will create a new instance of the game
	 * through the setScreen method of the game.
	 */
	@Override
	public void create()
	{
		//set libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		//load assets
		Assets.instance.init(new AssetManager());
		
		// Load preferences for audio settings and start playing music
		GamePreferences.instance.load();
		//AudioManager.instance.play(Assets.instance.music.song01);
		
		//start game at menu screen
		setScreen(new MenuScreen(this));
	}
	
}
