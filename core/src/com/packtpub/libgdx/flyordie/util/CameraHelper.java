package com.packtpub.libgdx.flyordie.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.packtpub.libgdx.flyordie.game.objects.AbstractGameObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Camera helper to help move the camera
 * @author Gregory Whitman 
 *
 */
public class CameraHelper 
{
	private static final String TAG = CameraHelper.class.getName();
	
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;
	
	private Vector2 position;
	private float zoom;
	private AbstractGameObject target;
	
	/**
	 * constructor 
	 */
	public CameraHelper () 
	{
		position = new Vector2();
		zoom = 1.0f;
	}
	
	/**
	 * Update the camera helper to point at a location
	 * @param deltaTime
	 */
	public void update (float deltaTime) 
	{
		if (!hasTarget()) return;
			
		position.x = target.position.x + target.origin.x;
		position.y = target.position.y + target.origin.y;
		
		// Prevent camera from moving down too far
		//position.y = Math.max(-1f,position.y);
	}
	
	/**
	 * Set the target to point at a specific game object
	 * @param target
	 */
	public void setTarget(AbstractGameObject target)
	{
		this.target = target;
	}
	
	/**
	 * Get the target of the camera
	 * @return target
	 */
	public AbstractGameObject getTarget()
	{
		return target;
	}
	
	/**
	 * set the position of the camera
	 * @param x
	 * @param y
	 */
	public void setPosition (float x, float y) 
	{
		this.position.set(x, y);
	}
	
	/**
	 * get the position of the camera
	 * @return
	 */
	public Vector2 getPosition () { return position; }
	
	/**
	 * add zoom to the camera
	 * @param amount
	 */
	public void addZoom (float amount) { setZoom(zoom + amount); }
	
	/**
	 * Set the zoom of the camera
	 * @param zoom
	 */
	public void setZoom (float zoom) 
	{
		this.zoom = MathUtils.clamp(zoom,  MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}
	
	/**
	 * get the zoom of the camera
	 * @return
	 */
	public float getZoom () { return zoom; }
	
	/**
	 * ask if the target is null
	 * @return true if the target is null
	 */
	public boolean hasTarget () { return target != null; }
	/**
	 * ask the camera if the game object is the target
	 * @param target
	 * @return
	 */
	public boolean hasTarget(AbstractGameObject target)
	{
		return hasTarget() && this.target.equals(target);
	}
	
	/**
	 * apply the camera to 
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
