package com.packtpub.libgdx.flyordie.game.objects;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.flyordie.game.Assets;

/**
 * Clouds to add decoration to the game
 * @author Greg Whitman
 *
 */
public class Clouds extends AbstractGameObject
{
	// Cloud definition
	private float length;
    private Array<TextureRegion> regClouds;
    private Array<Cloud> clouds;
    
    /**
     * Type of obj in the world
     * @author Greg Whitman
     *
     */
    private class Cloud extends AbstractGameObject 
    {
    	private TextureRegion regCloud;
    	
    	/**
    	 * Constructor for cloud
    	 */
    	public Cloud () 
    	{
    		
    	}
    	
    	/**
    	 * Sets clouds texture/image
    	 * @param region
    	 */
    	public void setRegion (TextureRegion region) 
    	{
    		regCloud = region;
    	}
    	
    	@Override
    	public void render (SpriteBatch batch) 
    	{
    		TextureRegion reg = regCloud;
    		batch.draw(reg.getTexture(), position.x + origin.x,
    				position.y + origin.y, origin.x, origin.y, dimension.x,
    				dimension.y, scale.x, scale.y, rotation, reg.getRegionX(),
    				reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
    				false, false);
    	}
      
   }
    
    /**
     * 
     * @param length
     */
    public Clouds (float length) 
    {
    	this.length = length;
	    init();

    }

    /**
     * Sets up cloud for initial values
     */
    private void init () 
    {
	 
	    dimension.set(3.0f, 1.5f); //3.0 1.5
     
	    bounds.set(0, 0, dimension.x, dimension.y);
	    
	    regClouds = new Array<TextureRegion>();
     
	    regClouds.add(Assets.instance.levelDecoration.cloud01);     
	    regClouds.add(Assets.instance.levelDecoration.cloud02);    
	    regClouds.add(Assets.instance.levelDecoration.cloud03);
	   
	    int distFac = 5;    
	    int numClouds = (int)(length / distFac);
	   
	    clouds = new Array<Cloud>(2 * numClouds);
	   
	    for (int i = 0; i < numClouds; i++) 
	    {
	    	Cloud cloud = spawnCloud();
		    cloud.position.x = i * distFac;
		    clouds.add(cloud);
	    } 
    }
   
    /**
     * Random cloud chosen and is added
     * to be drawn
     * @return
     */
   private Cloud spawnCloud () 
   {
	   Cloud cloud = new Cloud();
	   cloud.dimension.set(dimension);
	   // select random cloud image
	   cloud.setRegion(regClouds.random());
	   // position
	   Vector2 pos = new Vector2();
	   
	   float h = 0.0f;
	   
	   h = MathUtils.random(-5.0f, 6.0f);
	   
	   pos.y = h + pos.y;
	   
	   pos.x = length + 10; // position after end of level
	   pos.y += 1.75; // base position
	   pos.y += MathUtils.random(0.0f, 0.2f)
            * (MathUtils.randomBoolean() ? 1 : -1); // random additional position
	   cloud.position.set(pos);
	   return cloud;
   }
   
   @Override
   public void render (SpriteBatch batch) 
   {
     for (Cloud cloud : clouds)
         cloud.render(batch);
   } 
}