package com.packtpub.libgdx.flyordie.game.objects;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.flyordie.game.Assets;

/**
 * Class for the object brick border that will
 * constrain the player and kill the player if touched
 * 
 * @author Greg Whitman
 *
 */
public class Brick extends AbstractGameObject
{ 
     private TextureRegion reg;   
     private int length;
     
     public Brick() 
     {
    	 init();
     }
   
     /**
      * Sets up initial values for the bricks
      */
     private void init () 
     {
    	 dimension.set(1, 1);//1.5f
    	 reg = Assets.instance.brick.brickwall;
  
    	 // Start length of this rock
    	 setLength(1);
     }
     
     /**
      * sets length for the walls
      * @param length
      */
     public void setLength (int length) 
     {
    	 this.length = length;
     }
     
     /**
      * Allows possibility to make longer
      * @param amount
      */
     public void increaseLength (int amount) 
     {
    	 setLength(length + amount);
     }
   
     @Override
     public void render (SpriteBatch batch) 
     {
    	 float relX = 0;
    	 float relY = 0;
    	 
    	 //Scaling scale.x*2.0f is close
    	 // Draws all the bricks
    	 relX -= dimension.x;
    	 batch.draw(reg.getTexture(), position.x + relX, position.y +
    			 relY, origin.x, origin.y, dimension.x, dimension.y,
    			 scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
    			 reg.getRegionWidth(), reg.getRegionHeight(), false, false);
     }
}