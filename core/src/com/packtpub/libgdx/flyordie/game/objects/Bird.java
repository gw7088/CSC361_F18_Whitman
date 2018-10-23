package com.packtpub.libgdx.flyordie.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.flyordie.game.Assets;
import com.packtpub.libgdx.flyordie.util.Constants;

/**
 * Concrete game object that manages the bird
 * @author Greg Whitman
 *
 */
public class Bird extends AbstractGameObject
{
	//tag the name of the class
	public static final String TAG = Bird.class.getName();
	
	//max time a bird can jump
	private final float JUMP_TIME_MAX = 0.3f;
	//min time a bird can jump
	private final float JUMP_TIME_MIN = 0.1f;
	//time a bird can fly
	private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;
	//enum for the direction a bird is facing
	public enum VIEW_DIRECTION { LEFT, RIGHT };
	//enum for what the bird is doing
	public enum JUMP_STATE { GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING };
	//the bird texture
	private TextureRegion regHead;
	
	//instance variable for the direction the bird is facing
	public VIEW_DIRECTION viewDirection;
	//instance variable for jumping time
	public float timeJumping;
	//instance variable for the state of the jump
	public JUMP_STATE jumpState;
	//instance variable to determine if the bird has a feather powerup
	public boolean hasDoublePointsup;
	//instance variable to determine time left for x2's power
	public float timeLeftDoublePoints;
	
	/**
	 * constructor for the bird
	 */
	public Bird()
	{
		init();
	}
	
	/**
	 * initialize the bird
	 */
	public void init() 
	{
		dimension.set(1, 1);
		regHead = Assets.instance.bird.character;
		
		//center image on game object
		origin.set(dimension.x /2, dimension.y /2);
		
		/**
		//Bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		//set physics values
		terminalVelocity.set(3.0f, 4.0f);
		friction.set(12.0f, 0.0f);
		acceleration.set(0.0f, -25.0f);
		//view direction
		viewDirection = VIEW_DIRECTION.RIGHT;
		//jump state
		jumpState = JUMP_STATE.FALLING;
		timeJumping = 0;
		*/
		
		//power-ups
		hasDoublePointsup = false;
		timeLeftDoublePoints = 0;
	}
	
	/**
	 * set the state of the bunny head as it regards to jumping
	 * @param jumKeyPressed

	public void setJumping(boolean jumpKeyPressed) 
	{
		switch (jumpState)
		{
			//character is standing on a platform
			case GROUNDED:
				if(jumpKeyPressed)
				{
					//start counting jump time from the beginning
					timeJumping = 0;
					jumpState = JUMP_STATE.JUMP_RISING;
				}
				break;
			//rising in the air
			case JUMP_RISING: 
				if(!jumpKeyPressed)
				{
					jumpState = JUMP_STATE.JUMP_FALLING;
				}
				break;
			//falling down
			case FALLING:
			
			//falling down after jump
			case JUMP_FALLING:
				if(jumpKeyPressed && hasFeatherPowerup)
				{
					timeJumping = JUMP_TIME_OFFSET_FLYING;
					jumpState = JUMP_STATE.JUMP_RISING;
				}
				break;
		}
	}
	*/
	
	/**
	 * Set the state of the bird as it regards the feather power up
	 * @param pickedUp
	 */
	public void setDoublePointsPowerup(boolean pickedUp) 
	{
		hasDoublePointsup = pickedUp;
		if(pickedUp)
		{
			timeLeftDoublePoints = Constants.DOUBLEPOINTS_POWERUP_DURATION;
		}
	}
	
	/**
	 * determine if the Bird has a x2 points power up
	 * @return true if it has x2 power up
	 */
	public boolean hasDoublePointsup() 
	{
		return hasDoublePointsup && timeLeftDoublePoints > 0;
	}
	
	/**
	 * override the update method so the bird can handle its 
	 * own update functionality
	 */
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		
		/**
		//if the bunny head is moving set the direction of travel
		if(velocity.x != 0)
		{
			viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}
		*/
		
		//if the bird has x2 power up
		if(timeLeftDoublePoints > 0)
		{
			timeLeftDoublePoints -= deltaTime;
			if(timeLeftDoublePoints < 0)
			{
				//disable power-up
				timeLeftDoublePoints = 0;
				setDoublePointsPowerup(false);
			}
		}
	}
	
	/**
	 * method to handle the updates when jumping and falling. 
	@Override 
	protected void updateMotionY(float deltaTime)
	{
		switch(jumpState)
		{
			case GROUNDED:
				jumpState = JUMP_STATE.FALLING;
				break;
			case JUMP_RISING:
				//keep track of jump time
				timeJumping += deltaTime;
				
				//jump time left?
				if(timeJumping <= JUMP_TIME_MAX)
				{
					//still jumping
					velocity.y = terminalVelocity.y;
				}
				break;
			case FALLING:
				break;
			case JUMP_FALLING:
				//add delta times to track jump time
				timeJumping += deltaTime;
				
				//jump to minimal height if jump key was pressed too short
				if(timeJumping > 0 && timeJumping <= JUMP_TIME_MIN)
				{
					//still jumping 
					velocity.y = terminalVelocity.y;
				}
		}
		if(jumpState != JUMP_STATE.GROUNDED) 
		{
			super.updateMotionY(deltaTime);
		}
	}
	*/

	/**
	 * method handles the drawing of the bird object
	 */
	@Override
	public void render(SpriteBatch batch)
	{
		TextureRegion reg = null;
		
		//set special color when game object has a x2 power-up
		if(hasDoublePointsup)
		{
			batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
		}
		
		//draw image
		reg = regHead;
		batch.draw(reg.getTexture(), position.x, position.y - 5.5f, origin.x, origin.y,
				dimension.x, dimension.y, scale.x, scale.y, rotation, 
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), 
				reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT, false);
		
		//reset color to white
		batch.setColor(1,1,1,1);
		
	}
	
}
