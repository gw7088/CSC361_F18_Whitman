package com.packtpub.libgdx.flyordie.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.packtpub.libgdx.flyordie.util.CameraHelper;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.flyordie.game.objects.Brick;
import com.packtpub.libgdx.flyordie.game.objects.Clouds;
import com.packtpub.libgdx.flyordie.game.objects.DoublePoint;
import com.packtpub.libgdx.flyordie.game.objects.GoldCoin;
import com.packtpub.libgdx.flyordie.game.objects.Pipe;
import com.packtpub.libgdx.flyordie.util.Constants;
import com.packtpub.libgdx.flyordie.game.objects.Bird;

/**
 * This class will control when events
 * take place in the world and update everything
 * @author Greg Whitman
 *
 */
public class WorldController extends InputAdapter implements Disposable 
{
	public World b2world;
	
	private static final String TAG = WorldController.class.getName(); 

	// Values used in game
    public CameraHelper cameraHelper;
    public Level level;
    public int lives;
    public int score;
    
    //BodyDef Def = new BodyDef();
    //Body b;

    /**
     * Initialization of level loads
     * it it
     */
    private void initLevel () 
    {
        score = 0;
        level = new Level(Constants.LEVEL_01);
        cameraHelper.setTarget(level.bird);
        initPhysics();
    }
    
    private void initPhysics() 
    {
    	
    	if (b2world != null) b2world.dispose();
    	
    	b2world = new World(new Vector2(0, -1f), true);
    	
    	// Pipes
    	Vector2 pipeOrigin = new Vector2();
    	
    	for (Pipe pipe : level.pipes)
    	{
    		BodyDef bodyDef = new BodyDef();
    		bodyDef.type = BodyType.KinematicBody;
    		bodyDef.position.set(pipe.position);
    		Body body = b2world.createBody(bodyDef);
    		pipe.body = body;
    		
    		PolygonShape polygonShape = new PolygonShape();
    		pipeOrigin.x = pipe.bounds.width / 2.0f;
    		pipeOrigin.y = pipe.bounds.height / 2.0f;
    		polygonShape.setAsBox(pipe.bounds.width / 2.0f, pipe.bounds.height / 2.0f, pipeOrigin, 0);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = polygonShape;
    		fixtureDef.restitution = 0f;
    		body.createFixture(fixtureDef);
    		polygonShape.dispose();
    	}
    		
    	// Border
    	Vector2 brickOrigin = new Vector2();
    	
    	for (Brick brick : level.bricks)
    	{
    		BodyDef bodyDef = new BodyDef();
    		bodyDef.type = BodyType.KinematicBody;
    		bodyDef.position.set(brick.position);
    		Body body = b2world.createBody(bodyDef);
    		brick.body = body;
    		
    		PolygonShape polygonShape = new PolygonShape();
    		//polygonShape.setAsBox(brick.origin.x, brick.origin.y);
    		brickOrigin.x = brick.bounds.width / 2.0f;
    		brickOrigin.y = brick.bounds.height / 2.0f;
    		polygonShape.setAsBox(brick.bounds.width / 2.0f, brick.bounds.height / 2.0f, brickOrigin, 0);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = polygonShape;
    		body.createFixture(fixtureDef);
    		polygonShape.dispose();
    	}
    	
    	// Coins
    	Vector2 coinOrigin = new Vector2();
    	
    	for (GoldCoin goldcoin : level.goldcoins)
    	{
    		BodyDef bodyDef = new BodyDef();
    		bodyDef.type = BodyType.KinematicBody;
    		bodyDef.position.set(goldcoin.position);
    		Body body = b2world.createBody(bodyDef);
    		goldcoin.body = body;
    		
    		PolygonShape polygonShape = new PolygonShape();
    		coinOrigin.x = goldcoin.bounds.width / 2.0f;
    		coinOrigin.y = goldcoin.bounds.height / 2.0f;
    		polygonShape.setAsBox(goldcoin.bounds.width / 2.0f, goldcoin.bounds.height / 2.0f, coinOrigin, 0);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = polygonShape;
    		body.createFixture(fixtureDef);
    		polygonShape.dispose();
    	}
    	
    	// Double points
    	Vector2 dpOrigin = new Vector2();
    	
    	for (DoublePoint doublepoint : level.doublepoints)
    	{
    		BodyDef bodyDef = new BodyDef();
    		bodyDef.type = BodyType.KinematicBody;
    		bodyDef.position.set(doublepoint.position);
    		Body body = b2world.createBody(bodyDef);
    		doublepoint.body = body;
    		
    		PolygonShape polygonShape = new PolygonShape();
    		dpOrigin.x = doublepoint.bounds.width / 2.0f;
    		dpOrigin.y = doublepoint.bounds.height / 2.0f;
    		polygonShape.setAsBox(doublepoint.bounds.width / 2.0f, doublepoint.bounds.height / 2.0f, dpOrigin, 0);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = polygonShape;
    		body.createFixture(fixtureDef);
    		polygonShape.dispose();
    	}
    	
    	// Clouds
    	Clouds cloud = level.clouds;
    	BodyDef bodyDef = new BodyDef();
    	bodyDef.type = BodyType.DynamicBody;
    	bodyDef.position.set(cloud.position);
    	Body body = b2world.createBody(bodyDef);
    	level.clouds.body = body;
    	
    	PolygonShape polygonShape = new PolygonShape();
    	int x = (int) (level.clouds.bounds.width / 2.0f);
    	int y = (int) (level.clouds.bounds.width / 2.0f);
    	polygonShape.setAsBox(x, y);
    	
    	FixtureDef fixtureDef = new FixtureDef();
    	fixtureDef.shape = polygonShape;
    	body.createFixture(fixtureDef);
    	polygonShape.dispose();
    	
    	// Bird
    	Bird bird = level.bird;
    	BodyDef Def = new BodyDef();
    	Def.type = BodyType.KinematicBody;
    	Def.position.set(bird.position);
    	
    	//b = b2world.createBody(Def);
    	Body b = b2world.createBody(Def);
    	level.bird.body = b;
    	
    	PolygonShape shape = new PolygonShape();
    	int x2 = (int) (level.bird.bounds.width / 2.0f);
    	int y2 = (int) (level.bird.bounds.width / 2.0f);
    	polygonShape.setAsBox(x2, y2);
    	
    	FixtureDef fixture = new FixtureDef();
    	fixture.shape = shape;
    	b.createFixture(fixture);
    	polygonShape.dispose();
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
	 * Updates the world on changes that occurred
	 * @param deltaTime
	 */
	public void update(float deltaTime) 
	{
		handleDebugInput(deltaTime);
		cameraHelper.update(deltaTime);
		

		handleInputGame(deltaTime);
		level.update(deltaTime);
		//testCollisions();
		b2world.step(deltaTime, 8, 3);
		cameraHelper.update(deltaTime);
	}
	
	/**
	 * Method that handles user input when a key is pressed
	 * to take some action within the game
	 * @param deltaTime a float that represents the time span
	 * between the current frame and last rendered frame
	 */
	private void handleInputGame (float deltaTime) 
	{
	     if (cameraHelper.hasTarget(level.bird)) 
	     {
	       // Player Movement
	       if (Gdx.input.isKeyPressed(Keys.LEFT)) 
	       {
	    	   System.out.println("Left");
	       } 
	       else if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
	       {
	    	   System.out.println("Right");
	       } 
	       else 
	       {
	        
	       }
	       // Bird Jump
	       if (Gdx.input.isKeyPressed(Keys.SPACE))
	       {
	    	   System.out.println("Jump");
	       } 
	       else 
	       {
	         
	       }
	     } 
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
	
	/**
     * Method that frees memory that was once 
     * allocated to objects and other things used
     * by Box2d
     */
    @Override
    public void dispose() 
    {
    	if (b2world != null) b2world.dispose();
    }
}

