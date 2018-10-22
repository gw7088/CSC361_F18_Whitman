package com.packtpub.libgdx.flyordie.game.objects;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.flyordie.game.Assets;

public class Pipe extends AbstractGameObject
{
	 private TextureRegion regTop;
     private TextureRegion regShaft;
     private int length;
     
     public Pipe() 
     {
    	 init();
     }
   
     private void init () 
     {
    	 dimension.set(1, 1.5f);
    	 regTop = Assets.instance.pipe.top;
    	 regShaft = Assets.instance.pipe.shaft;
    	 // Start length of this rock
    	 setLength(1);
     }
     
     public void setLength (int length) 
     {
    	 this.length = length;
     }
     
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
    	 
    	 // Draw Top of pipe
    	 reg = regTop;
    	 relX -= dimension.x / 4;
    	 batch.draw(reg.getTexture(), position.x + relX, position.y +
    			 relY, origin.x, origin.y, dimension.x / 4, dimension.y,
    			 scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
    			 reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    	 
    	 // Draw Shafts
    	 relX = 0;
    	 reg = regShaft;
    	 for (int i = 0; i < length; i++) 
    	 {
    		 batch.draw(reg.getTexture(), position.x + relX, position.y
    				 +  relY, origin.x, origin.y, dimension.x, dimension.y,
    				 scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
    				 reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    		 		 relX += dimension.x;
    	 }
     }
}
   
   
   
   
   