package com.xch.pigsrpg.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xch.pigsrpg.ui.*;

import javax.security.auth.login.Configuration;

public class Pigsrpg extends Game {
    //游戏设置
    public static final String TITLE = "King and Pigs";
    public static int V_WIDTH = 720;
    public static int V_HEIGHT = 480;
    public static int FPS = 60;
    public static float V_SCALE = 0.5f;
    public static final float STEP = 1 / (float)FPS;
    //屏幕
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private EndScreen endScreen;
    //屏幕
    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;
    //相机和精灵画笔
    private SpriteBatch sb;
    private OrthographicCamera cam;
    private OrthographicCamera hudcam;
    //背景音乐
    public Music playingSong;
    //关卡选择
    public static int levelMap = 1;
    //切换屏幕
    public void changeScreen(int screen){
        switch(screen){
            case MENU:
                if(menuScreen == null) menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case PREFERENCES:
                if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
                this.setScreen(preferencesScreen);
                break;
            case APPLICATION:
                if(mainScreen == null) mainScreen = new MainScreen(this);
                this.setScreen(mainScreen);
                break;
            case ENDGAME:
                if(endScreen == null) endScreen = new EndScreen(this);
                this.setScreen(endScreen);
                break;
        }
    }

    //游戏设置
    private AppPreferences preferences = new AppPreferences();
    public AppPreferences getPreferences() {
        return this.preferences;
    }
    //资源管理器
    public B2dAssetManager assMan = new B2dAssetManager();
    @Override
    public void create() {
        //初始化精灵画笔、相机
        sb = new SpriteBatch();
        cam = new OrthographicCamera(Pigsrpg.V_WIDTH, Pigsrpg.V_HEIGHT);
        hudcam = new OrthographicCamera(Pigsrpg.V_WIDTH + 200,Pigsrpg.V_HEIGHT + 200);
        //加载loading画面
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
        //放背景音乐
        assMan.queueAddMusic();
        assMan.manager.finishLoading();
        playingSong = assMan.manager.get("sound/Rolemusic.mp3");
        playingSong.setLooping(true);
        playingSong.setVolume(0.5f);
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

}
