package com.packtpub.libgdx.flyordie.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.flyordie.game.Assets.AssetMusic;
import com.packtpub.libgdx.flyordie.game.Assets.AssetSounds;
import com.packtpub.libgdx.flyordie.util.Constants;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;

/*
 * By Greg Whitman
 * Class that contains the asset manager 
 */
public class Assets implements Disposable, AssetErrorListener 
{
	// Allows for fonts for the GUI
	public AssetFonts fonts;
	public AssetSounds sounds;
	public AssetMusic music;	
	
	/**
	 * class that will handle the loading of the sound files for each sound type
	 * @author Denny Fleagle
	 *
	 */
	public class AssetSounds
	{
		public final Sound jump;
		public final Sound pickupCoin;
		public final Sound smack;

		
		/**
		 * Constructor that will handle the loading/assigning of the sound files
		 * @param am
		 */
		public AssetSounds(AssetManager am)
		{
			jump = am.get("sounds/Flap.wav", Sound.class);
			pickupCoin = am.get("sounds/Pickup_Coin.wav", Sound.class);
			smack = am.get("sounds/Smack.wav", Sound.class);
		}
	}
	
	/**
	 * Class that will handle the loading of the music assets
	 * @author Denny Fleagle
	 *
	 */
	public class AssetMusic
	{
		public final Music song01;
		
		/**
		 * Constructor will assign the music files for the given sound
		 * @param am
		 */
		public AssetMusic(AssetManager am)
		{
			song01 = am.get("music/Epic_Music.mp3", Music.class);
		}
	}
	
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
	
		public final AtlasRegion head;
    	public final Animation animNormal;
    	public final Animation animCopterTransform; 
    	public final Animation animCopterTransformBack; 
    	public final Animation animCopterRotate;

    	
    	public AssetBunny (TextureAtlas atlas)
    	{
    		head = atlas.findRegion("bunny_head");
    		
    		Array<AtlasRegion> regions = null;
    	    AtlasRegion region = null;
    	    
    	    // Animation: Bunny Normal
    	    regions = atlas.findRegions("anim_bunny_normal");
    	    animNormal = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP_PINGPONG);
    	    
    	    // Animation: Bunny Copter - knot ears
    	    regions = atlas.findRegions("anim_bunny_copter");
    	    animCopterTransform = new Animation(1.0f / 10.0f, regions);
    	    
    	    // Animation: Bunny Copter - unknot ears
    	    regions = atlas.findRegions("anim_bunny_copter");
    	    animCopterTransformBack = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.REVERSED);
    	    
    	    // Animation: Bunny Copter - rotate ears
    	    regions = new Array<AtlasRegion>();
    	    regions.add(atlas.findRegion("anim_bunny_copter", 4));
    	    regions.add(atlas.findRegion("anim_bunny_copter", 5));
    	    animCopterRotate = new Animation(1.0f / 15.0f, regions);

    	}
	
	*/
	
    /**
     * Assigns assets from the atlas to a
     * region gives it a texture
     */
    public class AssetPlayer
    {
    	public final AtlasRegion character;
    	public final Animation animNormal;

		public AssetPlayer (TextureAtlas atlas)
    	{
    		
    		character = atlas.findRegion("frame-1");
			
    		Array<AtlasRegion> regions = null;
    		AtlasRegion region = null;
    		
    		regions = new Array<AtlasRegion>();
    		regions.add(atlas.findRegion("frame-1"));
    	    regions.add(atlas.findRegion("frame-2"));
    	    regions.add(atlas.findRegion("frame-3"));
    	    regions.add (atlas.findRegion("frame-4"));
    	    animNormal = new Animation(1.0f / 15.0f, regions);
    		
    		
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
        
        // Sounds
        assetManager.load("sounds/Flap.wav", Sound.class);
        assetManager.load("sounds/Smack.wav", Sound.class);
        assetManager.load("sounds/Pickup_Coin.wav", Sound.class);
        
        // Music
        assetManager.load("music/Epic_Music.mp3", Music.class);
        
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
    	    sounds = new AssetSounds(assetManager);
    	    music = new AssetMusic(assetManager);
    	 
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
   public void error (String filename, Class<?> type, Throwable throwable) 
   {
	   Gdx.app.error(TAG, "Couldn't load asset '"
			+ filename + "'", (Exception)throwable);
   }
   
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
