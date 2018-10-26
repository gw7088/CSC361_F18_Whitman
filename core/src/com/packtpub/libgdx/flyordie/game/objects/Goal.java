package com.packtpub.libgdx.flyordie.game.objects;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.flyordie.game.Assets;

/**
 * Code to create the goal object 
 * @author Greg Whitman
 *
 */
public class Goal extends AbstractGameObject
{
	//will store the texture image of the goal object
	private TextureRegion regGoal;
	
	/**
	 * Main constructor that initializes the game
	 */
    public Goal () 
	{
		   init();
	}
    /**
     * Method that sets the goal object's dimension
     * to be 3meters wide and 3 meters high, its bounding
     * box in the game and its center of origin
     */
    private void init () 
    {
    	   dimension.set(1.0f, 1.0f);
    	   regGoal = Assets.instance.goal.goal;
    	   // Set bounding box for collision detection
    	   bounds.set(0, 0, dimension.x, dimension.y);
    	   //origin.set(dimension.x / 2.0f, 0.0f);
    }
    /**
     * Method that renders the sprite to the display
     */
    public void render (SpriteBatch batch) 
    {
    	   TextureRegion reg = null;
    	   reg = regGoal;
    	   batch.draw(reg.getTexture(), position.x - origin.x,
    	   position.y - origin.y, origin.x, origin.y, dimension.x,
    	   dimension.y, scale.x, scale.y, rotation,
    	   reg.getRegionX(), reg.getRegionY(),
    	   reg.getRegionWidth(), reg.getRegionHeight(),
    	   false, false);
    }
}