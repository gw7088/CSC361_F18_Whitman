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
	public AssetFonts fonts;
	
	/**
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
    */
    
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
    
    /**
    public class AssetPipeShaft
    {
    	public final AtlasRegion shaft;
    	
    	public AssetPipeShaft (TextureAtlas atlas)
    	{
    		shaft = atlas.findRegion("green_shaft");
    	}
    }
    
    public class AssetPipeTop
    {
    	public final AtlasRegion top;
    	
    	public AssetPipeTop (TextureAtlas atlas)
    	{
    		top = atlas.findRegion("green_Top");
    	}
    }
    */
    
    public class AssetPipe
    {
    	public final AtlasRegion shaft;
    	public final AtlasRegion top;
    	
    	public AssetPipe (TextureAtlas atlas)
    	{
    		shaft = atlas.findRegion("green_shaft");
    		top = atlas.findRegion("green_Top");
    	}
    }
	
    //public AssetPipeTop tip;
    //public AssetPipeShaft shaft;
    public AssetPipe pipe;
	public AssetPlayer bird;
	//public AssetBottomPipe bottom;
	public AssetGoldCoin goldCoin;
	//public AssetTopPipe top;
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
    	    //green = new AssetPipe(atlas);
    	    bird = new AssetPlayer(atlas);
    	    //top = new AssetTopPipe(atlas);
    	    goldCoin = new AssetGoldCoin(atlas);
    	    //bottom = new AssetBottomPipe(atlas);
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
