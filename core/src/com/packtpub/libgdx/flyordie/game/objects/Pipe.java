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
	 // Texture for the shaft
     private TextureRegion regShaft;
     private TextureRegion top;
     private TextureRegion bottom;
     private int length;
     
     // Constructs pipe
     public Pipe() 
     {
    	 init();
     }
   
     /**
      * Pipes initial settings
      */
     private void init () 
     {
    	 dimension.set(1, 1);//1.5f
    	 regShaft = Assets.instance.pipe.shaft;
    	 top = Assets.instance.pipe.top;
    	 bottom = Assets.instance.pipe.bottom;
  
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
    	 
    	 /**
    	  * I Tried drawing all the pipes first and then all of the pipe tip
    	  * 
    	  * 
    	 float relX = 0;
    	 float relY = 0;
         reg = bottom;
         for (int i = 0; i < length; i++) 
         {
             batch.draw(reg.getTexture(), position.x + relX, position.y
            		 +  relY, origin.x, origin.y, dimension.x, dimension.y,
            		 scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
            		 reg.getRegionWidth(), reg.getRegionHeight(), false, false);
             
             relY += dimension.y;
         }
         
         reg = top;
         batch.draw(reg.getTexture(),position.x + relX, position.y +
        		 relY, origin.x + dimension.x / 8, origin.y, dimension.x / 4,
        		 dimension.y, scale.x, scale.y, rotation + 90, reg.getRegionX(),
        		 reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
        		 true, false);
         */
    	 
    	 // Draw Shafts of pipes
    	 reg = bottom;

    	 batch.draw(reg.getTexture(), position.x, position.y,
    			 origin.x, origin.y, dimension.x, dimension.y,
    			 scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
    			 reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    	 
     }
}