package com.xch.pigsrpg.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class B2dAssetManager {
    public final AssetManager manager = new AssetManager();
    //skin
    public final String skin = "UI/golden/golden-ui-skin.json";
    //image
    public final String humanking = "human/humanking.atlas";
    //background
    public final String loading = "loading/loading.atlas";
    // Sounds
    public final String hammering = "sound/hammering.wav";
    public final String map = "map/1.tmx";
    public B2dAssetManager(){
        manager.setLoader(TiledMap.class, new TmxMapLoader());
    }

    public void queueAddImages(){
        manager.load(humanking, TextureAtlas.class);
    }

    public void queueAddLoadingImages(){
        manager.load(loading, TextureAtlas.class);
    }

    public void queueAddSounds(){
        manager.load(hammering, Sound.class);
    }

    public void queueAddMusic(){
        manager.load("sound/Rolemusic.mp3", Music.class);
    }

    public void queueAddSkin(){
        SkinParameter params = new SkinParameter("UI/golden/golden-ui-skin.atlas");
        manager.load(skin, Skin.class, params);
    }

    public void queueAddMap(){
        manager.load(map, TiledMap.class);
    }


    public void queueAddFonts(){
    }

    public void queueAddParticleEffects(){
    }
}
