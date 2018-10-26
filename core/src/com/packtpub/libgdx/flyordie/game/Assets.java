package com.packtpub.libgdx.flyordie.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

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
	// Allows for fonts for the GUI
	public AssetFonts fonts;
	
	/**
     *  Assigns assets from the atlas to a
     * region gives it a texture
     */
	public class AssetGoal 
	{
		public final AtlasRegion goal;
	    
	    public AssetGoal (TextureAtlas atlas)
	    {
	    	goal = atlas.findRegion("finsih1");
	    }
	}
	
	/**
     *  Assigns assets from the atlas to a
     * region gives it a texture
     */
	public class AssetBrick 
	{
		public final AtlasRegion brickwall;
	    
	    public AssetBrick (TextureAtlas atlas)
	    {
	    	brickwall = atlas.findRegion("brickBorder");
	    }
	}
	
	/**
	 * Assigns assets from the atlas to a
	 * region gives it a texture
	 */
	public class AssetDoublePoint 
	{
		public final AtlasRegion doublePoint;
    	
		public AssetDoublePoint (TextureAtlas atlas)
		{
			doublePoint = atlas.findRegion("DoublePoints");
		}
	}
	
	/**
	 * Assigns assets from the atlas to a
	 * region gives it a texture
	 */
	public class AssetGoldCoin 
	{
		public final AtlasRegion goldCoin;
    	
		public AssetGoldCoin (TextureAtlas atlas)
		{
			goldCoin = atlas.findRegion("item_gold_coin");
		}
	}
    
    /**
     * Assigns assets from the atlas to a
     * region gives it a texture
     */
    public class AssetPlayer
    {
    	public final AtlasRegion character;
    	
    	public AssetPlayer (TextureAtlas atlas)
    	{
    		character = atlas.findRegion("frame-1");
    	}
    }
    
    /**
     * Assigns assets from the atlas to a
     * region gives it a texture
     */
    public class AssetPipe
    {
    	public final AtlasRegion shaft;
    	public final AtlasRegion top;
    	public final AtlasRegion bottom;
    	
    	public AssetPipe (TextureAtlas atlas)
    	{
    		shaft = atlas.findRegion("green_shaft");
    		top = atlas.findRegion("Top_Pipe");
    		bottom = atlas.findRegion("Bottom_pipe");
    	}
    }

    // All the assets names
    public AssetGoal goal;
    public AssetDoublePoint doublePoint;
    public AssetBrick brick;
    public AssetPipe pipe;
	public AssetPlayer bird;
	public AssetGoldCoin goldCoin;
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
    	    
    	    fonts = new AssetFonts();
    	    // create game resource objects
    	    goal = new AssetGoal(atlas);
    	    doublePoint = new AssetDoublePoint(atlas);
    	    brick = new AssetBrick(atlas);
    	    bird = new AssetPlayer(atlas);
    	    goldCoin = new AssetGoldCoin(atlas);
    	    pipe = new AssetPipe(atlas);
    	    levelDecoration = new AssetLevelDecoration(atlas);
    	 
   }
   @Override
   public void dispose () 
   {
     assetManager.dispose();
     fonts.defaultSmall.dispose();
     fonts.defaultNormal.dispose();
     fonts.defaultBig.dispose();
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
   
   /**
	* Allows for text to be displayed for GUI
	* and game over screen
    */
   public class AssetFonts
   {
   		public final BitmapFont defaultSmall;
   		public final BitmapFont defaultNormal;
   		public final BitmapFont defaultBig;
   	
   		public AssetFonts()
   		{
   			// Create three fonts using Libgdx's 15px bitmap font
   			defaultSmall = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
   			defaultNormal = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
   			defaultBig = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
   		
   			//set font sizes
   			defaultSmall.getData().setScale(0.75f);
   			defaultNormal.getData().setScale(1.0f);
   			defaultBig.getData().setScale(2.0f);
   		
   			//enable linear textrue filtering for smooth fonts
   			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear,  TextureFilter.Linear);
   			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear,  TextureFilter.Linear);
   			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
   		}
   }   
}
