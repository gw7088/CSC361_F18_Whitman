package com.packtpub.libgdx.flyordie.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * This sets up the camera that will be used to view
 * the game world
 * 
 * @author Greg Whitman
 */
public class CameraHelper 
{
	private static final String TAG = CameraHelper.class.getName();
	
	// Camera zoom values
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;
	
	// Camera positioning
	private Vector2 position;
	private float zoom;
	private Sprite target;
	
	/*
	 * Moves camera
	 */
	public CameraHelper () 
	{
		position = new Vector2();
		zoom = 1.0f;
	}
	
	/**
	 * Keeps the camera updated as it moves
	 * @param deltaTime
	 */
	public void update (float deltaTime) 
	{
		if (!hasTarget()) return;
			
		position.x = target.getX() + target.getOriginX();
		position.y = target.getY() + target.getOriginY();
	}
	
	/**
	 * Camera positioning
	 * @param x
	 * @param y
	 */
	public void setPosition (float x, float y) 
	{
		this.position.set(x, y);
	}
	
	/**
	 * Gets camearas coordinates
	 * @return
	 */
	public Vector2 getPosition () 
	{ 
		return position; 
	}
	
	/**
	 * Zoom in function
	 * @param amount
	 */
	public void addZoom (float amount) 
	{ 
		setZoom(zoom + amount); 	
	}
	
	/**
	 * Zoom out function
	 * @param zoom
	 */
	public void setZoom (float zoom) 
	{
		this.zoom = MathUtils.clamp(zoom,  MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}
	
	/**
	 * How much the camera is moved in/out
	 * @return
	 */
	public float getZoom () 
	{ 
		return zoom; 
	}
	
	/**
	 * Current target in cameras focus
	 * @param target
	 */
	public void setTarget (Sprite target ) 
	{ 
		this.target = target; 
	}
	
	/**
	 * What camera is looking at
	 * @return
	 */
	public Sprite getTarget () 
	{ 
		return target; 
	}
	
	/**
	 * If camera is tracking
	 * @return
	 */
	public boolean hasTarget () 
	{ 
		return target != null; 
	}
	
	/**
	 * Targets obj to view
	 * @param target
	 * @return
	 */
	public boolean hasTarget (Sprite target) 
	{
		return hasTarget () && this.target.equals(target);
	}
	
	/**
	 * Sets up the camera
	 * @param camera
	 */
	public void applyTo (OrthographicCamera camera) 
	{
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
}
