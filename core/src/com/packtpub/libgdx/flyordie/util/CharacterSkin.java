package com.packtpub.libgdx.flyordie.util;
import com.badlogic.gdx.graphics.Color;

/**
 * enum class to hold the skins 
 * @author Greg Whitman
 *
 */
public enum CharacterSkin
{
	//enumerated types for the skins
	WHITE("White", 1.0f, 1.0f, 1.0f),
	GRAY("Gray", 0.7f, 0.7f, 0.7f),
	BROWN("Brown", 0.7f, 0.5f, 0.3f);
	
	//the name of the skin
	private String name;
	
	//the color of the skin
	private Color color = new Color();
	
	/**
	 * rich constructor for setting attributes of the skin
	 * @param name
	 * @param r
	 * @param g
	 * @param b
	 */
	private CharacterSkin(String name, float r, float g, float b)
	{
		this.name = name;
		color.set(r,g,b,1.0f);
	}
	
	/**
	 * Override the default tostring method and return name
	 */
	@Override
	public String toString()
	{
		return name;
	}
	
	/**
	 * method to get the color of the skin
	 * @return
	 */
	public Color getColor()
	{
		return color;
	}
}
