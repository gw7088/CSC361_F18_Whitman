package com.packtpub.libgdx.flyordie.util;

/**
 * Keeps track of things that don't change
 * @author Greg Whitman
 *
 */
public class Constants 
{
	// Visible game world is 5 meters wide
	public static final float VIEWPORT_WIDTH = 10.0f;  // 5.0f
	
	// Visible game world is 5 meters tall
	public static final float VIEWPORT_HEIGHT =	11.0f; // 5.0f
	
	// GUI width
	public static final float VIEWPORT_GUI_WIDTH = 800.0f;
	
	// GUI height
	public static final float VIEWPORT_GUI_HEIGHT = 480.0f;
	
	// Location of description file for texture atlas
	public static final String TEXTURE_ATLAS_OBJECTS =
		"images/flyordie.atlas";
	
	// Location of image file for level 01
	public static final String LEVEL_01 = "levels/level-01.png";
	
	// Amount of extra lives at level start
	public static final int LIVES_START = 1;

	// How long x2 points lasts
	public static final float DOUBLEPOINTS_POWERUP_DURATION = 5;

	public static final float TIME_DELAY_GAME_FINISHED = 3;
	
	 // Game preferences file
	public static final String PREFERENCES = "flyordie.prefs";
}