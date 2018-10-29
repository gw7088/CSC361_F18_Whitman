package com.packtpub.libgdx.flyordie.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.flyordie.util.AudioManager;
import com.packtpub.libgdx.flyordie.util.CharacterSkin;
import com.packtpub.libgdx.flyordie.util.GamePreferences;
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
	
	//enum for the direction a bird is facing
	public enum VIEW_DIRECTION { LEFT, RIGHT };
	
	//the bird texture
	private TextureRegion regHead;
	
	//instance variable for the direction the bird is facing
	public VIEW_DIRECTION viewDirection;
	
	public boolean hasDoublePointsup;
	
	//instance variable to determine time left for x2's power
	public float timeLeftDoublePoints;
	
	//Jump boolean
	public boolean jump;
	
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
		
		jump = false;
		
		//center image on game object
		//origin.set(dimension.x /2, dimension.y /2);
		
		//Bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);

		//power-ups
		hasDoublePointsup = false;
		timeLeftDoublePoints = 0;
	}
	
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
	public void isJumping()
	{
		if(jump == true)
		{
			AudioManager.instance.play(Assets.instance.sounds.jump);
		}
		else
			jump = false;
	}
	*/
	
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
	 * method handles the drawing of the bird object
	 */
	@Override
	public void render(SpriteBatch batch)
	{
		TextureRegion reg = null;
		
		batch.setColor(CharacterSkin.values() [GamePreferences.instance.charSkin].getColor());
		
		//set special color when game object has a x2 power-up
		if(hasDoublePointsup)
		{
			batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
		}
		
		//draw image
		reg = regHead;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, // position.y - 5.5f
				dimension.x, dimension.y, scale.x, scale.y, rotation, 
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), 
				reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT, false);
		
		//reset color to white
		batch.setColor(1,1,1,1);	
	}
}
