package com.xch.pigsrpg.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xch.pigsrpg.ui.*;

import java.util.ArrayList;

public class Pigsrpg extends Game {
    // Game Setting
    public static final String TITLE = "King and Pigs";
    public static int V_WIDTH = 640;
    public static int V_HEIGHT = 480;
    public static int FPS = 60;
    public static float V_SCALE = 0.5f;
    private AppPreferences preferences = new AppPreferences();
    // Screen
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    public MainScreen mainScreen;
    private EndScreen endScreen;
    private MapSelectScreen mapSelectScreen;
    // Screen_def
    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;
    public final static int MapSelect = 4;
    // Cam & Sb
    private SpriteBatch sb;
    private OrthographicCamera cam;
    private OrthographicCamera hudcam;
    // Asset
    public static AssetManager assMan = new AssetManager();
    // BGM
    public Music playingSong;
    // Stage
    public int levelMap = 0;
    public ArrayList<String> maps = new ArrayList<String>();
    // Change Screen
    public void changeScreen(int screen){
        switch(screen){
            case MENU:
                if(menuScreen == null) menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case PREFERENCES:
                if (preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
                this.setScreen(preferencesScreen);
                break;
            case MapSelect:
                if (mapSelectScreen == null) mapSelectScreen = new MapSelectScreen(this);
                this.setScreen(mapSelectScreen);
                break;
            case APPLICATION:
                mainScreen = new MainScreen(this);
                this.setScreen(mainScreen);
                break;
            case ENDGAME:
                endScreen = new EndScreen(this);
                this.setScreen(endScreen);
                break;
        }
    }
    @Override
    public void create() {
        // init
        sb = new SpriteBatch();
        cam = new OrthographicCamera(Pigsrpg.V_WIDTH, Pigsrpg.V_HEIGHT);
        hudcam = new OrthographicCamera(Pigsrpg.V_WIDTH + 200,Pigsrpg.V_HEIGHT + 200);

        // LoadingScreen
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);

        // Background
        assMan.queueAddMusic();
        assMan.manager.finishLoading();
        playingSong = assMan.manager.get("music/Rolemusic.mp3");
        playingSong.setLooping(true);
        playingSong.setVolume(0.2f);
        playingSong.play();

    }

    @Override
    public void dispose(){
        playingSong.dispose();
        assMan.manager.dispose();
        sb.dispose();
    }
    public SpriteBatch getSpriteBatch(){
        return sb;
    }
    public OrthographicCamera getCamera(){
        return cam;
    }
    public OrthographicCamera getHudCamera(){
        return hudcam;
    }
    public AppPreferences getPreferences() {
        return this.preferences;
    }
}
