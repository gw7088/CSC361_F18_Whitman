package com.packtpub.libgdx.flyordie.game.objects;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.flyordie.game.Assets;

/**
 * Obj pipe that the bird must fly
 * through and not touch
 * @author Greg Whitman
 *
 */
public class Pipe extends AbstractGameObject
{
	 // Texture for the bird/player
     private TextureRegion regShaft;
     private int length;
     
     // Constructs pipe
     public Pipe() 
     {
    	 init();
     }
   
     /**
      * Pipes initail settings
      */
     private void init () 
     {
    	 dimension.set(1, 1);//1.5f
    	 regShaft = Assets.instance.pipe.shaft;
  
    	 // Start length of this rock
    	 setLength(1);
     }
     
     /**
      * Pipes length
      * @param length
      */
     public void setLength (int length) 
     {
    	 this.length = length;
     }
     
     /**
      * Can increase size of obj
      * @param amount
      */
     public void increaseLength (int amount) 
     {
    	 setLength(length + amount);
     }
   
     @Override
     public void render (SpriteBatch batch) 
     {
    	 TextureRegion reg = null;
    	 float relX = 0;
    	 float relY = 0;
    	 
    	 // Draw Shafts of pipes
    	 reg = regShaft;
    	 relX -= dimension.x / 2;
    	 batch.draw(reg.getTexture(), position.x + relX, position.y +
    			 relY, origin.x, origin.y, dimension.x / 4, dimension.y,
    			 scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
    			 reg.getRegionWidth(), reg.getRegionHeight(), false, false);
     }
}