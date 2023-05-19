package com.xch.pigsrpg.core;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetManager {
    public final com.badlogic.gdx.assets.AssetManager manager = new com.badlogic.gdx.assets.AssetManager();
    // Skin
    public final String skin = "UI/golden/golden-ui-skin.json";
    // Image
    public final String door = "door/door.atlas";
    public final String humanking = "human/humanking.atlas";
    public final String pig = "pig/pig.atlas";
    public final String pig_bomb = "pig/pig&bomb.atlas";
    public final String pig_throw = "pig/pig&throw.atlas";
    public final String pig_match = "pig/pig&match.atlas";
    public final String heart = "heart/heart.atlas";
    public final String diamond = "diamond/diamond.atlas";
    public final String cannon = "cannon/cannon.atlas";
    public final String bomb = "cannon/bomb.atlas";
    public final String box = "box/box.atlas";
    // Background
    public final String loading = "loading/loading.atlas";
    // Sounds
    public final String hammering = "sound/hammering.wav";
    public final String pick = "sound/pick.wav";
    // Music
    public final String music = "music/Rolemusic.mp3";
    // UI
    public final String ui = "UI/golden/golden-ui-skin.atlas";
    public String map1 = "map/1.tmx";
    public AssetManager(){
        manager.setLoader(TiledMap.class, new TmxMapLoader());
    }

    public void queueAddImages(){
        manager.load(humanking, TextureAtlas.class);

        manager.load(door, TextureAtlas.class);
        manager.load(heart, TextureAtlas.class);
        manager.load(diamond, TextureAtlas.class);

        manager.load(box, TextureAtlas.class);
        manager.load(bomb, TextureAtlas.class);
        manager.load(cannon, TextureAtlas.class);

        manager.load(pig, TextureAtlas.class);
        manager.load(pig_bomb, TextureAtlas.class);
        manager.load(pig_match, TextureAtlas.class);
        manager.load(pig_throw, TextureAtlas.class);
    }

    public void queueAddLoadingImages(){
        manager.load(loading, TextureAtlas.class);
    }

    public void queueAddSounds(){
        manager.load(hammering, Sound.class);
        manager.load(pick, Sound.class);

    }

    public void queueAddMusic(){
        manager.load(music, Music.class);
    }

    public void queueAddSkin(){
        SkinParameter params = new SkinParameter(ui);
        manager.load(skin, Skin.class, params);
    }

    public void queueAddMap(String map){
        manager.load(map1, TiledMap.class);
    }


    public void queueAddFonts(){
    }

    public void queueAddParticleEffects(){
    }
}
