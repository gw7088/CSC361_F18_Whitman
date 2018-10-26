package com.packtpub.libgdx.flyordie.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.packtpub.libgdx.flyordie.game.Assets;
import com.packtpub.libgdx.flyordie.util.AudioManager;
import com.packtpub.libgdx.flyordie.util.Constants;
//import com.packtpub.libgdx.flyordie.util.CharacterSkin;
import com.packtpub.libgdx.flyordie.util.GamePreferences;
//import com.packtpub.libgdx.flyordie.util.AudioManager;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * This sets up the rough menu screen and sets up some basic
 * event handling.
 * 
 * @author Gregory Whitman
 *
 */
public class MenuScreen extends AbstractGameScreen
{
	 private static final String TAG = MenuScreen.class.getName();
	 private Stage stage;
     private Skin skinCanyonBunny;
     private Skin skinLibgdx;
     // menu
     private Image imgBackground;
     private Image imgLogo;
     private Image imgInfo;
     private Image imgCoins;
     private Image imgBunny;
     private Button btnMenuPlay;
     private Button btnMenuOptions;
     // options
     private Window winOptions;
     private TextButton btnWinOptSave;
     private TextButton btnWinOptCancel;
     private CheckBox chkSound;
     private Slider sldSound;
     private CheckBox chkMusic;
     private Slider sldMusic;
     //private SelectBox<CharacterSkin> selCharSkin;
     private Image imgCharSkin;
     private CheckBox chkShowFpsCounter;
     // debug
    /* private final float DEBUG_REBUILD_INTERVAL = 5.0f;
     private boolean debugEnabled = false;*/
     private float debugRebuildStage;
	
     /**
      * Constructor that is passed in the Game Class 
      * which implements the application interface allowing
      * different screens to be loaded in 
      * @param game an object from the Game Class
      */
	 public MenuScreen (Game game)
	 {
		super(game);
	 }
	 
	 /**
	  * add animation to the menu button.
	  * @param visible
	  */
	 private void showMenuButtons(boolean visible)
	 {
		 float moveDuration = 1.0f;
		 Interpolation moveEasing = Interpolation.swing;
		 float delayOptionsButton = 0.25f;
		 
		 float moveX = 300 * (visible ? -1 : 1);
		 float moveY = 0 * (visible ? -1 : 1); 		//ummmm multiplication by 0 is always 0 so why the extra stuff???
		 final Touchable touchEnabled = visible ? Touchable.enabled : Touchable.disabled;
		 btnMenuPlay.addAction(moveBy(moveX, moveY, moveDuration, moveEasing));
		 
		 btnMenuOptions.addAction(sequence(delay(delayOptionsButton), moveBy(moveX, moveY, moveDuration, moveEasing)));
		 
		 SequenceAction seq = sequence();
		 
		 //TODO the book may have a typo here.... no braces for if statement
		 if(visible)
			 seq.addAction(delay(delayOptionsButton + moveDuration));
		 seq.addAction(run(new Runnable()
		 {
			 public void run()
			 {
		        btnMenuPlay.setTouchable(touchEnabled);;
		        btnMenuOptions.setTouchable(touchEnabled);
			 }
	     }));
		 stage.addAction(seq);
	 }
	 
	 /**
	  * add animation to the option window. 
	  * @param visible
	  * @param animated
	  */
	 private void showOptionsWindow(boolean visible, boolean animated)
	 {
		 float alphaTo = visible ? 0.8f : 0.0f;
		 float duration = animated ? 1.0f : 0.0f;
		 Touchable touchEnabled = visible ? Touchable.enabled : Touchable.disabled;
		 winOptions.addAction(sequence(touchable(touchEnabled), alpha(alphaTo, duration)));
		 
	 }
	
	 /*
	  * A private method that loads in the specific
	  * settings for a game such as sound, volume, 
	  * music, character skins and whether to show the 
	  * FPS Counter.
	  */
	 private void loadSettings() 
	 {
	     GamePreferences prefs = GamePreferences.instance;
	     prefs.load();
	     chkSound.setChecked(prefs.sound);
	     sldSound.setValue(prefs.volSound);
	     chkMusic.setChecked(prefs.music);
	     sldMusic.setValue(prefs.volMusic);
	     //selCharSkin.setSelectedIndex(prefs.charSkin);
	     onCharSkinSelected(prefs.charSkin);
	     chkShowFpsCounter.setChecked(prefs.showFpsCounter);
	 }
	 /**
	 * Private method that saves the 
	 * game preferences that the user decided 
	 * for the game
	 */
	 private void saveSettings() 
	 {
	     GamePreferences prefs = GamePreferences.instance;
	     prefs.sound = chkSound.isChecked();
	     prefs.volSound = sldSound.getValue();
	     prefs.music = chkMusic.isChecked();
	     prefs.volMusic = sldMusic.getValue();
	     //prefs.charSkin = selCharSkin.getSelectedIndex();
	     prefs.showFpsCounter = chkShowFpsCounter.isChecked();
	     prefs.save();
	  }
	 	
	  /**
	   * Helper method that is called when the user
	   * wants a different character representing him.  
	   * @param index an int that represents which skin to use for the character
	   */
	  private void onCharSkinSelected(int index) 
	  {
	     //CharacterSkin skin = CharacterSkin.values()[index];
	     //imgCharSkin.setColor(skin.getColor());
	  }
	 
	  /**
	   * Private method is called when the save
	   * button of the option window is called. 
	   * Then two helper methods are called to save
	   * the game settings and not display the window
	   * options any more
	   * 
	   * Saves the audio settings
	   */
	  private void onSaveClicked() 
	  {
	     saveSettings();
	     onCancelClicked();
	     AudioManager.instance.onSettingsUpdated();
	  }
	  
	  /**
	   * Private method that is called when the 
	   * the cancel and/or save button is pressed by 
	   * the user. It will then hide the window option 
	   * pane and will then display the main two button menu's;
	   * namely, play and menuoptions
	   */
	  private void onCancelClicked() 
	  {
	     showMenuButtons(true);
	     showOptionsWindow(false, true);
	     AudioManager.instance.onSettingsUpdated();
	  }
	  
	  /**
	   * Private method that is called when
	   * the menu options button is clicked. If 
	   * so the current game preferences are loaded 
	   * in and both the main menu's play and option
	   * disappear and the window pane is displayed 
	   * in their place 
	   */
	  private void onOptionsClicked() 
	  {
		     loadSettings();
		     showMenuButtons(false);
		     showOptionsWindow(true, true);
	  }
	  
	  /**
	   * Private method that is called when the user
	   * presses the main menu's play button. After which
	   * the game object calls the hide() and show() methods to
	   * hide the current screen and show the other screen, which 
	   * in this case would be the playable game.
	   */
	  private void onPlayClicked()
	  {
		  game.setScreen(new GameScreen(game));
	  }
	  
	  /**
	   * Method that builds the audio portion of
	   * option window's UI that houses the game's audio 
	   * settings. A new table object is which a labels, 
	   * checkboxes and movable sliders are contained within  
	   * @return a Table object that contains various widgets
	   */
	  private Table buildOptWinAudioSettings () 
	  {
		     Table tbl = new Table();
		     // + Title: "Audio"
		     tbl.pad(10, 10, 0, 10);
		     tbl.add(new Label("Audio", skinLibgdx, "default-font",
		             Color.ORANGE)).colspan(3);
		     tbl.row();
		     tbl.columnDefaults(0).padRight(10);
		     tbl.columnDefaults(1).padRight(10);
		     // + Checkbox, "Sound" label, sound volume slider
		     chkSound = new CheckBox("", skinLibgdx);
		     tbl.add(chkSound);
		     tbl.add(new Label("Sound", skinLibgdx));
		     sldSound = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
		     tbl.add(sldSound);
		     tbl.row();
		     // + Checkbox, "Music" label, music volume slider
		     chkMusic = new CheckBox("", skinLibgdx);
		     tbl.add(chkMusic);
		     tbl.add(new Label("Music", skinLibgdx));
		     sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
		     tbl.add(sldMusic);
		     tbl.row();
		     return tbl;
	}
	/**
	  Method that builds the character selection portion of
	  the option window's UI. A new table layout widget
	  is created in which labels, a selectbox and an image
	  widget are contained within.  
	  @return a Table object that contains various widgets 
	 
	 */
	private Table buildOptWinSkinSelection () 
	{
		     Table tbl = new Table();
		     // + Title: "Character Skin"
		     tbl.pad(10, 10, 0, 10);
		     tbl.add(new Label("Character Skin", skinLibgdx, "default-font", Color.ORANGE)).colspan(2);
		     tbl.row();
		     
		     // + Drop down box filled with skin items
		     //selCharSkin = new SelectBox<CharacterSkin>(skinLibgdx);
		     //selCharSkin.setItems(CharacterSkin.values());
		     //selCharSkin.addListener(new ChangeListener() {
		    //	 @Override
		    //	 public void changed(ChangeEvent event, Actor actor) 
		    //	 {
		    //		 onCharSkinSelected(((SelectBox<CharacterSkin>)actor).getSelectedIndex());
		    //	 }
		    //  });
		     
		    // tbl.add(selCharSkin).width(120).padRight(20);
		     // + Skin preview image
		    // imgCharSkin = new Image(Assets.instance.bunny.head);
		     //tbl.add(imgCharSkin).width(50).height(50);
		     return tbl;
	}
	
	/**
	  Method that builds the debug portion of
	  the option window's UI. A new table layout widget
	  is created in which labels, and a checkbox 
	  widget are contained within. Allowing the user to select
	  the preference of showing the FPS Counter while playing the 
	  game.  
	  @return a Table object that contains various widgets 
	 */
	private Table buildOptWinDebug () 
	{
	     Table tbl = new Table();
	     // + Title: "Debug"
	     tbl.pad(10, 10, 0, 10);
	     tbl.add(new Label("Debug", skinLibgdx, "default-font", Color.RED)).colspan(3);
	     tbl.row();
	     tbl.columnDefaults(0).padRight(10);
	     tbl.columnDefaults(1).padRight(10);
	     // + Checkbox, "Show FPS Counter" label
	     chkShowFpsCounter = new CheckBox("", skinLibgdx);
	     tbl.add(new Label("Show FPS Counter", skinLibgdx));
	     tbl.add(chkShowFpsCounter);
	     tbl.row();
	     return tbl; 
	}
	
	 /**
	  Method that builds the button portion of
	  the option window's UI that will contain two buttons-
	  a save and cancel button. A new table layout widget
	  is created in which labels and a textbutton 
	  widget are contained within. Allowing the user to select
	  to save the new game settings or cancel opening up the 
	  option's window in the first place.  
	  @return a Table object that contains various widgets 
	 */
	 private Table buildOptWinButtons () 
	 {
	     Table tbl = new Table();
	     // + Separator
	     Label lbl = null;
	     lbl = new Label("", skinLibgdx);
	     lbl.setColor(0.75f, 0.75f, 0.75f, 1);
	     lbl.setStyle(new LabelStyle(lbl.getStyle()));
	     lbl.getStyle().background = skinLibgdx.newDrawable("white");
	     tbl.add(lbl).colspan(2).height(1).width(220).pad(0, 0, 0, 1);
	     tbl.row();
	     lbl = new Label("", skinLibgdx);
	     lbl.setColor(0.5f, 0.5f, 0.5f, 1);
	     lbl.setStyle(new LabelStyle(lbl.getStyle()));
	     lbl.getStyle().background = skinLibgdx.newDrawable("white");
	     tbl.add(lbl).colspan(2).height(1).width(220).pad(0, 1, 5, 0);
	     tbl.row();
	     // + Save Button with event handler
	     btnWinOptSave = new TextButton("Save", skinLibgdx);
	     tbl.add(btnWinOptSave).padRight(30);
	     btnWinOptSave.addListener(new ChangeListener() {
	       @Override
	       public void changed (ChangeEvent event, Actor actor) 
	       {
	         onSaveClicked();
	       }
	     });
	     // + Cancel Button with event handler
	     btnWinOptCancel = new TextButton("Cancel", skinLibgdx);
	     tbl.add(btnWinOptCancel);
	     btnWinOptCancel.addListener(new ChangeListener() {
	       @Override
	       public void changed (ChangeEvent event, Actor actor) 
	       {
	         onCancelClicked();
	       }
	     });
	     return tbl; 
	}
	  
	
	/**
	 * Method that is called after the method Show(). It makes two 
	 * calls to openGL's interface to clear the given screen 
	 * and then so long as no debugging tools are enabled, 
	 * the stage calls each of the actors in the scene to take some 
	 * action and then calls the draw method to render the UI on the 
	 * user's display
	 */
	@Override
	public void render(float deltaTime)
	{
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isTouched())
		{
			//game.setScreen(new GameScreen(game));
		}
		/*if (debugEnabled) 
		{
	         debugRebuildStage -= deltaTime;
	         if (debugRebuildStage <= 0) 
	         {
	           debugRebuildStage = DEBUG_REBUILD_INTERVAL;
	           rebuildStage();
	         }
	    }*/
	    stage.act(deltaTime);
	    stage.draw();
	    stage.setDebugAll(false);

	}
	
	/**
	 * Key method that buids the 2d scene graph in which the stage widget 
	 * is the root of the graph and uses a stack and table layout
	 * widget to add various actors to the overall stage.In which the UI is 
	 * ready to be rendered. First two new skin textures are created one for the 
	 * the main menu UI and another for the window option UI. Then each layer of 
	 * the UI is constructed through helper methods and a table is sent back. After
	 * clearing the stage's previous root child and new stack widgetgroup is created 
	 * to overlay/position each of the previous tables ontop of each other 
	 */
	private void rebuildStage () 
	{
	    	//skinCanyonBunny = new Skin(Gdx.files.internal(Constants.SKIN_CANYONBUNNY_UI),
	    	//				  new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
	    	
	    	//skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),
	    	//		     new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));
	       
	       // build all layers
	       Table layerBackground = buildBackgroundLayer();
	       Table layerObjects = buildObjectsLayer();
	       Table layerLogos = buildLogosLayer();
	       Table layerControls = buildControlsLayer();
	       Table layerOptionsWindow = buildOptionsWindowLayer();
	       
	       // assemble stage for menu screen
	       stage.clear();
	       Stack stack = new Stack();
	       stage.addActor(stack);
	       stack.setSize(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT);
	       stack.add(layerBackground);
	       stack.add(layerObjects);
	       stack.add(layerLogos);
	       stack.add(layerControls);
	       //stage.addActor(layerOptionsWindow);
	 }
	
	 /**
	  * Private method that builds the background image for
	  * main menu's UI
	  * @return a Table object that is a layout widget
	  */
	 private Table buildBackgroundLayer () 
	 {
	       Table layer = new Table();
	       // + Background
	       //imgBackground = new Image(skinCanyonBunny, "background");
	       //layer.add(imgBackground);
	       return layer;
	 }
	 /**
	  * Private method that builds the gold coin and bunny
	  * head images for the main menu's UI
	  * @return a Table object that is a layout widget
	  */
	 private Table buildObjectsLayer () 
	 {
	       Table layer = new Table();
	       // + Coins
	       //imgCoins = new Image(skinCanyonBunny, "coins");
	       //layer.addActor(imgCoins);
	       //imgCoins.setOrigin(imgCoins.getWidth() / 2, imgCoins.getHeight() / 2);
	       //imgCoins.addAction(sequence(moveTo(135, -20), scaleTo(0, 0), fadeOut(0), delay(2.5f), 
	    	//	   parallel(moveBy(0, 100, 0.5f, Interpolation.swingOut), 
	    	//	   scaleTo(1.0f, 1.0f, 0.25f, Interpolation.linear), alpha(1.0f, 0.5f))));
	       
	       // + Bunny
	       //imgBunny = new Image(skinCanyonBunny, "bunny");
	       //layer.addActor(imgBunny);
	       //imgBunny.addAction(sequence(moveTo(655, 510),
	    	//	   delay(4.0f), 
	    	//	   moveBy(-70, -100, 0.5f, Interpolation.fade),
	    	//	   moveBy(-100, -50, 0.5f, Interpolation.fade), 
	    	//	   moveBy(-150, -300, 1.0f, Interpolation.elasticIn)));
	       
	       return layer;
	 }
	 
	 /**
	  * Private method that builds the orange
	  * canyonbunny head and LibGDX logo seen
	  * at the top and bottom left side of the main menu's UI
	  * @return a Table object that is a layout widget
	  */
	 private Table buildLogosLayer () 
	 {
	       Table layer = new Table();
	       layer.left().top();
	       // + Game Logo
	       //imgLogo = new Image(skinCanyonBunny, "logo");
	       //layer.add(imgLogo);
	       //layer.row().expandY();
	       // + Info Logos
	       //imgInfo = new Image(skinCanyonBunny, "info"); layer.add(imgInfo).bottom();
//	       if (debugEnabled) layer.debug();
	       return layer;
	 }
	 
	 /**
	  * Private method that builds the two interactive buttons
	  * for main menu's UI that are seen in the bottom right hand
	  * corner of the screen 
	  * @return a Table object that is a layout widget
	  */
	 private Table buildControlsLayer () 
	 {
	       Table layer = new Table();
	       layer.right().bottom();
	       
	       // + Play Button
	       //btnMenuPlay = new Button(skinCanyonBunny, "play");
	       //layer.add(btnMenuPlay);
	       //btnMenuPlay.addListener(new ChangeListener()
	       //{
	    	//   @Override
	    	//   public void changed(ChangeEvent event, Actor actor)
	    	//   {
	    	//	   onPlayClicked();
	    	//   }
	 		//});
	       
	       layer.row();
	       
	       // + Options Button
	       //btnMenuOptions = new Button(skinCanyonBunny, "options");
	       layer.add(btnMenuOptions);
	       //btnMenuOptions.addListener(new ChangeListener()
		   //{
	    	//   @Override
	    	//   public void changed(ChangeEvent event, Actor actor)
	    	//   {
	    	//	   onOptionsClicked();
	    	 //  }
	       //});
	       
	      /* if (debugEnabled)
	       {
	    	   layer.debug();
	       }*/
	       
	       
	       return layer;
	 }
	 /**
	  * private method that builds the pop-out option window
	  * and layers one table ontop of each other from top to bottom
	  * @returna Table object that is a layout widget
	  */
	 private Table buildOptionsWindowLayer () 
	 {
		 //winOptions = new Window("Options", skinLibgdx);
	     // + Audio Settings: Sound/Music CheckBox and Volume Slider
	     //winOptions.add(buildOptWinAudioSettings()).row();
	     // + Character Skin: Selection Box (White, Gray, Brown)
	    // winOptions.add(buildOptWinSkinSelection()).row();
	     // + Debug: Show FPS Counter
	     //winOptions.add(buildOptWinDebug()).row();
	     // + Separator and Buttons (Save, Cancel)
	    // winOptions.add(buildOptWinButtons()).pad(10, 0, 10, 0);
	     // Make options window slightly transparent
	     //winOptions.setColor(1, 1, 1, 0.8f);
	     // Hide options window by default
	     //showOptionsWindow(false, false);
//	     if (debugEnabled) winOptions.debug();
	     // Let TableLayout recalculate widget sizes and positions
	     //winOptions.pack();
	     // Move options window to bottom right corner
	     //winOptions.setPosition(Constants.VIEWPORT_GUI_WIDTH - winOptions.getWidth() - 50, 50);
	     return winOptions;
	 }
	 

	/**
	 * Method that is called to resize the displayable game world
	 * through getting the stage's viewport which handles the stage's 
	 * camera  
	 */
	@Override 
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height, true);
	}
	
	/**
	 * Method that is routinely called by the Game class in which 
	 * the game's main menu is displayed. A new Stage is created and 
	 * is set to handle user input while shown to the user. Then the
	 * main menu is created through the helper method of rebuildStage(). 
	 */
	@Override 
	public void show()
	{
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
	}
	
	/**
	 * Method that is routinely called by the Game class in which the 
	 * game's main menu is hidden and the memory allocated to it disposed of, 
	 * such as the skins and widgets held in memory  
	 */
	@Override
	public void hide()
	{
		stage.dispose();
		skinCanyonBunny.dispose();
		skinLibgdx.dispose();
	}
	/**
	 * non-implemented method inherited from AbstractScreen
	 */
	@Override
	public void pause()
	{
		
	}
}
