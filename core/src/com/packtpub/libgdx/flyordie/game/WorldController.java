package com.packtpub.libgdx.flyordie.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.packtpub.libgdx.flyordie.util.CameraHelper;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.flyordie.game.objects.Brick;
import com.packtpub.libgdx.flyordie.game.objects.Clouds;
import com.packtpub.libgdx.flyordie.game.objects.DoublePoint;
import com.packtpub.libgdx.flyordie.game.objects.Goal;
import com.packtpub.libgdx.flyordie.game.objects.GoldCoin;
import com.packtpub.libgdx.flyordie.game.objects.Pipe;
import com.packtpub.libgdx.flyordie.util.Constants;
import com.packtpub.libgdx.flyordie.screens.MenuScreen;
import com.packtpub.libgdx.flyordie.game.Assets;
import com.packtpub.libgdx.flyordie.util.AudioManager;
import com.packtpub.libgdx.flyordie.game.objects.Bird;

/**
 * This class will control when events
 * take place in the world and update everything
 * @author Greg Whitman
 *
 */
public class WorldController extends InputAdapter implements Disposable 
{
	//an instance of the game object
	private Game game;
	
	public World b2world;
	
	private static final String TAG = WorldController.class.getName(); 

	// Values used in game
    public CameraHelper cameraHelper;
    public Level level;
    public int lives;
    public int score;
    public float livesVisual;
    public float scoreVisual;
    
    private float timeLeftGameOverDelay;
    
    int timeLeft = 0;
    float timeLeftDoublePointsup; 
    
    boolean goalReached;
    
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();
    
    Body b;

    /**
     * Initialization of level loads
     * it it
     */
    private void initLevel () 
    {
        score = 0;
        scoreVisual = score;
        level = new Level(Constants.LEVEL_01);
        cameraHelper.setTarget(level.bird);
        initPhysics();
    }
    
    private void initPhysics() 
    {
    	
    	if (b2world != null) b2world.dispose();
    	
    	b2world = new World(new Vector2(0, -9.81f), true);
    	
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
    		//fixtureDef.isSensor = true;
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
    		bodyDef.type = BodyType.StaticBody;
    		bodyDef.position.set(goldcoin.position);
    		Body body = b2world.createBody(bodyDef);
    		goldcoin.body = body;
    		
    		PolygonShape polygonShape = new PolygonShape();
    		coinOrigin.x = goldcoin.bounds.width / 2.0f;
    		coinOrigin.y = goldcoin.bounds.height / 2.0f;
    		polygonShape.setAsBox(goldcoin.bounds.width / 2.0f, goldcoin.bounds.height / 2.0f, coinOrigin, 0);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = polygonShape;
    		fixtureDef.isSensor = true;
    		body.createFixture(fixtureDef);
    		polygonShape.dispose();
    	}
    	
    	// Double points
    	Vector2 dpOrigin = new Vector2();
    	
    	for (DoublePoint doublepoint : level.doublepoints)
    	{
    		BodyDef bodyDef = new BodyDef();
    		bodyDef.type = BodyType.StaticBody;
    		bodyDef.position.set(doublepoint.position);
    		Body body = b2world.createBody(bodyDef);
    		doublepoint.body = body;
    		
    		PolygonShape polygonShape = new PolygonShape();
    		dpOrigin.x = doublepoint.bounds.width / 2.0f;
    		dpOrigin.y = doublepoint.bounds.height / 2.0f;
    		polygonShape.setAsBox(doublepoint.bounds.width / 2.0f, doublepoint.bounds.height / 2.0f, dpOrigin, 0);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = polygonShape;
    		fixtureDef.isSensor = true;
    		body.createFixture(fixtureDef);
    		polygonShape.dispose();
    	}
    	
    	// Pipes
    	Vector2 goalOrigin = new Vector2();
    	
    	for (Goal goal : level.goals)
    	{
    		BodyDef bodyDef = new BodyDef();
    		bodyDef.type = BodyType.StaticBody;
    		bodyDef.position.set(goal.position);
    		Body body = b2world.createBody(bodyDef);
    		goal.body = body;
    		
    		PolygonShape polygonShape = new PolygonShape();
    		goalOrigin.x = goal.bounds.width / 2.0f;
    		goalOrigin.y = goal.bounds.height / 2.0f;
    		polygonShape.setAsBox(goal.bounds.width / 2.0f, goal.bounds.height / 2.0f, goalOrigin, 0);
    		
    		FixtureDef fixtureDef = new FixtureDef();
    		fixtureDef.shape = polygonShape;
    		fixtureDef.isSensor = true;
    		body.createFixture(fixtureDef);
    		polygonShape.dispose();
    	}
    	
    	// Clouds
    	Vector2 cloudOrigin = new Vector2();
    	Clouds cloud = level.clouds;
    	BodyDef bodyDef = new BodyDef();
    	bodyDef.type = BodyType.StaticBody;
    	bodyDef.position.set(cloud.position);
    	Body body = b2world.createBody(bodyDef);
    	level.clouds.body = body;
    	
    	PolygonShape polygonShape = new PolygonShape();
    	cloudOrigin.x = (cloud.bounds.width / 2.0f);
    	cloudOrigin.y = (cloud.bounds.height / 1.8f);
    	polygonShape.setAsBox(cloud.bounds.width / 2.0f, cloud.bounds.height / 2.0f, cloudOrigin, 0);
    	
    	int x = (int) (level.clouds.bounds.width / 2.0f);
    	int y = (int) (level.clouds.bounds.width / 2.0f);
    	polygonShape.setAsBox(x, y);
    	
    	FixtureDef fixtureDef = new FixtureDef();
    	fixtureDef.shape = polygonShape;
    	fixtureDef.isSensor = true;
    	body.createFixture(fixtureDef);
    	polygonShape.dispose();
    	
    	// Bird
    	Vector2 birdOrigin = new Vector2();
    	Bird bird = level.bird;
    	BodyDef Def = new BodyDef();
    	Def.type = BodyType.DynamicBody;
    	Def.position.set(bird.position);
    	
    	//b = b2world.createBody(Def);
    	b = b2world.createBody(Def);
    	level.bird.body = b;
    	
    	PolygonShape shape = new PolygonShape();
    	birdOrigin.x = (bird.bounds.width / 2.0f);
    	birdOrigin.y = (bird.bounds.height / 1.8f);
    	polygonShape.setAsBox(bird.bounds.width / 2.0f, bird.bounds.height / 2.0f, birdOrigin, 0);
    	
    	FixtureDef fixture = new FixtureDef();
    	fixture.shape = shape;
    	fixture.restitution = .25f;
    	fixture.friction = 0f;
    	b.createFixture(fixture);
    	polygonShape.dispose();
    	
    	
	}

	/**
     * Sets up worldController
     */
	public WorldController(Game game) 
	{
		this.game = game;
		init();
	}
	
	/**
	 * allows us to switch back to the menu screen
	 */
	private void backToMenu()
	{
		//switch to menu screen
		game.setScreen(new MenuScreen(game));
	}
	
	/**
	 * Initial values for worldController
	 */
	private void init() 
	{
		goalReached = false;
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		lives = Constants.LIVES_START;
		livesVisual = lives;
		timeLeftGameOverDelay = 0;
		initLevel();

	}
	
	/**
	 * Updates the world on changes that occurred
	 * @param deltaTime
	 */
	public void update(float deltaTime) 
	{
		if (isGameOver() || goalReached)
		{
			Gdx.input.setInputProcessor(null);
			level.update(deltaTime);
			testCollisions();
			b2world.step(deltaTime, 8, 3);
			cameraHelper.update(deltaTime);
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0)
			{
				//backToMenu();
				return; 
			}
		}
		else
		{
			handleInputGame(deltaTime);
			
			if (timeLeftDoublePointsup <= 0)
			{
				
			}
			else
			{
				timeLeftDoublePointsup-= deltaTime;
			}
			
			b.applyForceToCenter(2, 0, true); //3
			b.setLinearDamping(1);
			
			level.update(deltaTime);
			testCollisions();
			b2world.step(deltaTime, 8, 3);
			cameraHelper.update(deltaTime);
	
			if (scoreVisual < score)
			{
				scoreVisual = Math.min(score,  scoreVisual + 250 * deltaTime);
			}
		}
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
	    		 
	    	 } 
	    	 else if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
	    	 {

	    	 } 
	    	 else 
	    	 {
	        
	    	 }
	    	 // Bird Jump
	    	 if ( Gdx.input.isKeyJustPressed(Keys.SPACE) )//Gdx.input.isKeyPressed(Keys.SPACE))
	    	 {
	    		 //long lastPressProcessed = 0;
	    		 
         		 //if(System.currentTimeMillis() - lastPressProcessed > 5000) 
	    		 //{
         			 //Do your work here...
	    		     //lastPressProcessed = System.currentTimeMillis();
	    		     
	    		     AudioManager.instance.play(Assets.instance.sounds.jump);

	    		     //System.out.println("Jump");
	    		     b.applyForceToCenter(0, 450, true); // 0, 100, true
	    		     //b.applyLinearImpulse(0, 1, .5f, .5f, true);

	    		 //}
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
	 * Method that checks to see if the 
	 * game has ended 
	 * @return boolean that returns true if game is done,
	 * false otherwise
	 */
	public boolean isGameOver ()
	{
		   return lives <= 0;
	}
	
	/**
	 * Method that checks for collisions between the bird 
	 * the other game objects such as the rock, goldCoin, and DP 
	 * by checking if their rectangular bounding boxes overlap. If so a
	 * helper method will be called to take some action in the game.  
	 */
	private void testCollisions () 
	{
	     r1.set(level.bird.position.x, level.bird.position.y,
	    		 level.bird.bounds.width+.03f, level.bird.bounds.height);
	     
	     // Test collision: Pipes
	     for (Pipe pipe : level.pipes) 
	     {
	    	 r2.set(pipe.position.x, pipe.position.y, pipe.bounds.width,
	    			 pipe.bounds.height);
	    	 if (!r1.overlaps(r2)) continue;
	    	 
	    	 onCollisionBirdWithPipe(pipe);
	     }
	     
	     // Test collision: GoldCoins
	     for (GoldCoin goldcoin : level.goldcoins)
	     {
	    	 if (goldcoin.collected) continue;
	    	 r2.set(goldcoin.position.x, goldcoin.position.y,
	    			 goldcoin.bounds.width, goldcoin.bounds.height);
	    	 if (!r1.overlaps(r2)) continue;
	    	 onCollisionBirdWithGoldCoin(goldcoin);
	    	 break;
	     }
	     
	     // Test collision: Dp
	     for (DoublePoint doublepoint : level.doublepoints) 
	     {
	    	 if (doublepoint.collected) continue;
	    	 r2.set(doublepoint.position.x, doublepoint.position.y,
	    			 doublepoint.bounds.width, doublepoint.bounds.height);
	    	 if (!r1.overlaps(r2)) continue;
	    	 onCollisionBirdWithDoublePoint(doublepoint);
	    	 break;
	     }
	     
	     // Test collision: Goal
	     for (Goal goal : level.goals)
	     {
	    	 r2.set(goal.position.x, goal.position.y,
	    			 goal.bounds.width, goal.bounds.height);
	    	 if (!r1.overlaps(r2)) continue;
	    	 onCollisionBirdWithGoal(goal);
	    	 break;
	     }
	     
	     // Test collision: Brick
	     for (Brick brick : level.bricks) 
	     {
	    	 r2.set(brick.position.x, brick.position.y, brick.bounds.width,
	    			 brick.bounds.height);
	    	 if (!r1.overlaps(r2)) continue;
	    	 
	    	 onCollisionBirdWithBrick(brick);
	     }
	 }
	
	/**
	 * Effect of collision
	 * @param pipe
	 */
	private void onCollisionBirdWithPipe(Pipe pipe) 
	{
		AudioManager.instance.play(Assets.instance.sounds.smack);
		lives--;
	}
	
	/**
	 * Effect of collision
	 * @param brick
	 */
	private void onCollisionBirdWithBrick(Brick brick) 
	{
		//AudioManager.instance.play(Assets.instance.sounds.smack);
		lives--;
	}
	
	/**
	 * Effect of collision
	 * @param goldcoin
	 */
	private void onCollisionBirdWithGoldCoin(GoldCoin goldcoin)
	{
		
		goldcoin.collected = true;
		AudioManager.instance.play(Assets.instance.sounds.pickupCoin);
		
		if(timeLeftDoublePointsup <= 0)
		{	
			score += goldcoin.getScore();
	    	//Gdx.app.log(TAG, "Gold coin collected");
		}
		else
		{
			score = (score + (goldcoin.getScore() * 2));
			System.out.println("GOLD COIN WITH X2 POINTS");
		}
	}
	
	/**
	 * Effect of collision
	 * @param doublepoint
	 */
	private void onCollisionBirdWithDoublePoint(DoublePoint doublepoint)
	{
		doublepoint.collected = true;
		AudioManager.instance.play(Assets.instance.sounds.pickupCoin);
		
		//Gdx.app.log(TAG, "Double point collected");
		
		timeLeftDoublePointsup = Constants.DOUBLEPOINTS_POWERUP_DURATION;
	}
	
	/**
	 * Effect of collision
	 * @param goal
	 */
	private void onCollisionBirdWithGoal(Goal goal)
	{
		timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_FINISHED;
		//System.out.println("REACHED THE GOAL");
		goalReached = true;
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

