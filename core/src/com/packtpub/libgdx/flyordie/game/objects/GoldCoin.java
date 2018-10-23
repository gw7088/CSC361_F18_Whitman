package com.packtpub.libgdx.flyordie.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.flyordie.game.Assets;

/**
 * Class that represents the concrete gold object
 * @author Greg Whitman
 *
 */
public class GoldCoin extends AbstractGameObject
{
	//instance that will hold the gold coin texture
	private TextureRegion regGoldCoin;
	
	//flag that determines whether the bunny has collected the coin
    public boolean collected;
    
    /**
     * Constructor that calls the helper method init()
     */
    public GoldCoin () 
    {
      init();
    }
    
    /**
     * Method that sets the coin's dimension in the
     * game to be .5 meters wide and .5 meters high, 
     * imports the image for the gold coin, sets a 
     * rectangular bounding box for collision detection, 
     * and sets the collected flag to false
     */
    private void init () 
    {
      dimension.set(0.5f, 0.5f);
      regGoldCoin = Assets.instance.goldCoin.goldCoin;
      collected = false;
    }
    
    /**
     * Method that positions the gold coin in the scene to be  
     * later rendered by the GPU so long as it was not collected by 
     * the bunny
     */
    public void render (SpriteBatch batch) 
    {
      if (collected) return;
      TextureRegion reg = null;
      reg = regGoldCoin;
      batch.draw(reg.getTexture(), position.x, position.y,
    		  origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
    		  rotation, reg.getRegionX(), reg.getRegionY(),
    		  reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    }
    
    /**
     * Method that returns the player's score after
     * collecting the gold coin
     * @return an int representing the player's score
     */
    public int getScore() 
    {
        return 100;
    } 
}
