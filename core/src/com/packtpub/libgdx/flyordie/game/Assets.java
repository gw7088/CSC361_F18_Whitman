package com.packtpub.libgdx.flyordie.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.flyordie.util.Constants;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
/*
 * By Greg Whitman
 * Class that contains the asset manager 
 */
public class Assets implements Disposable, AssetErrorListener 
{
    public class AssetBottomPipe
    {
    	public final AtlasRegion bottomPipe;
    	
    	public AssetBottomPipe (TextureAtlas atlas)
    	{
    		bottomPipe = atlas.findRegion("bottomPipe");
    	}
    }
    
    public class AssetTopPipe
    {
    	public final AtlasRegion topPipe;
    	
    	public AssetTopPipe (TextureAtlas atlas)
    	{
    		topPipe = atlas.findRegion("topPipe");
    	}
    }
    
    public class AssetGoldCoin 
    {
    	public final AtlasRegion goldCoin;
    	
    	public AssetGoldCoin (TextureAtlas atlas)
    	{
    		goldCoin = atlas.findRegion("item_gold_coin");
    	}
    }
    
    public class AssetPlayer
    {
    	public final AtlasRegion character;
    	
    	public AssetPlayer (TextureAtlas atlas)
    	{
    		character = atlas.findRegion("frame-1");
    	}
    }
    
    public class AssetPipe
    {
    	public final AtlasRegion p;
    	
    	public AssetPipe (TextureAtlas atlas)
    	{
    		p = atlas.findRegion("pipe-green");
    	}
    }
	
    public AssetPipe green;
	public AssetPlayer bird;
	public AssetBottomPipe bottom;
	public AssetGoldCoin goldCoin;
	public AssetTopPipe top;
	public AssetLevelDecoration levelDecoration;
	
	public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;
    // singleton: prevent instantiation from other classes
    private Assets () {}
    public void init (AssetManager assetManager) 
    {
        this.assetManager = assetManager;
        
        // set asset manager error handler
        assetManager.setErrorListener(this);
        
        // load texture atlas
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,
        TextureAtlas.class);
        
         // start loading assets and wait until finished
         assetManager.finishLoading();
         Gdx.app.debug(TAG, "# of assets loaded: "
        	+ assetManager.getAssetNames().size);
         for (String a : assetManager.getAssetNames())
         Gdx.app.debug(TAG, "asset: " + a);
     
         TextureAtlas atlas =
            assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
            // enable texture filtering for pixel smoothing
    	    for (Texture t : atlas.getTextures()) 
    	    {
    	          t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	    }
    	    
    	    // create game resource objects
    	    green = new AssetPipe(atlas);
    	    bird = new AssetPlayer(atlas);
    	    top = new AssetTopPipe(atlas);
    	    goldCoin = new AssetGoldCoin(atlas);
    	    bottom = new AssetBottomPipe(atlas);
    	    levelDecoration = new AssetLevelDecoration(atlas);
    	 
   }
   @Override
   public void dispose () 
   {
     assetManager.dispose();
   }
   
   public class AssetLevelDecoration
   {
       public final AtlasRegion cloud01;
       public final AtlasRegion cloud02;
       public final AtlasRegion cloud03;
       public AssetLevelDecoration (TextureAtlas atlas) 
       {
           cloud01 = atlas.findRegion("cloud01");
           cloud02 = atlas.findRegion("cloud02");
           cloud03 = atlas.findRegion("cloud03");
       } 
   }
   public void error (String filename, Class type, Throwable throwable) 
   {
	   Gdx.app.error(TAG, "Couldn't load asset '"
			+ filename + "'", (Exception)throwable);
   }
   
   @Override
   public void error(AssetDescriptor asset, Throwable throwable) 
   {
	   Gdx.app.error(TAG, "Couldn't load asset '" +
            asset.fileName + "'", (Exception)throwable);

   }
    
}
