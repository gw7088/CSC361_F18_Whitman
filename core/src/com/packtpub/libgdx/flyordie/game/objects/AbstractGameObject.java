package com.packtpub.libgdx.flyordie.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Basic form of the objects
 * @author Gregory Whitman
 *
 */
public abstract class AbstractGameObject
{
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	
	/**
	 * All game objects should have instantiated 
	 * variables to work with.
	 */
	public AbstractGameObject()
	{
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
	}
	
	/**
	 * logic for updating game objects
	 * @param deltaTime
	 */
	public void update(float deltaTime)
	{
		
	}
	
	/**
	 * each object will handle its own rendering.
	 * @param batch
	 */
	public abstract void render(SpriteBatch batch);
}