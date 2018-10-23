package com.packtpub.libgdx.flyordie.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.packtpub.libgdx.flyordie.util.CameraHelper;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.flyordie.game.objects.Pipe;
import com.packtpub.libgdx.flyordie.util.Constants;

/**
 * This class will control when events
 * take place in the world and update everything
 * @author Greg Whitman
 *
 */
public class WorldController extends InputAdapter 
{
	private static final String TAG = WorldController.class.getName(); 

	// Values used in game
    public CameraHelper cameraHelper;
    public Level level;
    public int lives;
    public int score;

    /**
     * Initialization of level loads
     * it it
     */
    private void initLevel () 
    {
        score = 0;
        level = new Level(Constants.LEVEL_01);
    }
    
    /**
     * Sets up worldController
     */
	public WorldController() 
	{
		init();
	}
	
	/**
	 * Initial values for worldController
	 */
	private void init() 
	{
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		lives = Constants.LIVES_START; 
		initLevel();

	}
	
	/**
	 * Updates the world on changes that occured
	 * @param deltaTime
	 */
	public void update(float deltaTime) 
	{
		handleDebugInput(deltaTime);
		cameraHelper.update(deltaTime);
	}
	
	/**
	 * Debug mode, enabling to look around world
	 * @param deltaTime
	 */
	private void handleDebugInput(float deltaTime)
	{
		if (Gdx.app.getType() != ApplicationType.Desktop) return;
	       
	    // Camera Controls (move)
	    float camMoveSpeed = 5 * deltaTime;
	    float camMoveSpeedAccelerationFactor = 5;
	    if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *=
	      camMoveSpeedAccelerationFactor;
	    if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
	    if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed,
	         0);
	    if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
	    if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0,
	      -camMoveSpeed);
	    if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
	      cameraHelper.setPosition(0, 0);

	    // Camera Controls (zoom)
	    float camZoomSpeed = 1 * deltaTime;
	    float camZoomSpeedAccelerationFactor = 5;
	    if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *=
	      camZoomSpeedAccelerationFactor;
	    if (Gdx.input.isKeyPressed(Keys.COMMA))
	      cameraHelper.addZoom(camZoomSpeed);
	    if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(
	      -camZoomSpeed);
	    if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
	}
	
	/**
	 * Moves the camera
	 * @param x
	 * @param y
	 */
	private void moveCamera (float x, float y) 
	{
	       x += cameraHelper.getPosition().x;
	       y += cameraHelper.getPosition().y;
	       cameraHelper.setPosition(x, y);
	}

	@Override
    public boolean keyUp (int keycode) 
	{
      // Reset game world
      if (keycode == Keys.R) 
      {
    	  	init();
    	  	Gdx.app.debug(TAG, "Game world resetted");
      }
      return false;
	}
}

