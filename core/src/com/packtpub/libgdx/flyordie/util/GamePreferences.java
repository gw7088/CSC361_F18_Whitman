package com.packtpub.libgdx.flyordie.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

/**
 * All the values for the settings like music, and
 * sound volume etc...
 * 
 * Gregory Whitman
 *
 */
public class GamePreferences 
{
	public static final String TAG =
		GamePreferences.class.getName();
	
	public static final GamePreferences instance = 
		new GamePreferences();
	
	public boolean sound;
	public boolean music;
	public float volSound;
	public float volMusic;
	public int charSkin;
	public boolean showFpsCounter;
	
	private Preferences prefs;
	
	// Singleton: prevent instantiation from other classes
	private GamePreferences() 
	{
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
	}
	
	/**
	 * Always tries to find a valid value. And then is also clamped
	 * making sure the value is between 0-1.
	 */
	public void load()
	{
		sound = prefs.getBoolean("sound", true);
		music = prefs.getBoolean("music", true);
		
		volSound = MathUtils.clamp(prefs.getFloat("volSound", 0.5f),
			0.0f, 1.0f);
		
		volMusic = MathUtils.clamp(prefs.getFloat("volMusic", 0.5f),
			0.0f, 1.0f);
		
		charSkin = MathUtils.clamp(prefs.getInteger("charSkin", 0),
			0, 2);
		
		showFpsCounter = prefs.getBoolean("showFpsCounter", false);
	}
	
	/**
	 * Takes the current values of its public variables and puts
	 * them into the map of the preferences file.
	 */
	public void save()
	{
		// Takes the actual values for each and stores them in the file
		prefs.putBoolean("sound", sound);
		prefs.putBoolean("music", music);
		prefs.putFloat("volSound", volSound);
		prefs.putFloat("volMusic", volMusic);
		prefs.putInteger("charSkin", charSkin);
		prefs.putBoolean("showFpsCounter", showFpsCounter);
		
		// Write the change to the file
		prefs.flush();
	}
}
