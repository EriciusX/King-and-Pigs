package com.xch.pigsrpg.ui;

import com.xch.pigsrpg.core.Pigsrpg;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class PreferencesScreen implements Screen {
    private Pigsrpg parent;
    private final Stage stage;
    private Skin skin;
    public PreferencesScreen(Pigsrpg pigsrpg){
        parent = pigsrpg;
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        skin = parent.assMan.manager.get(parent.assMan.skin);
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        //volume
        final Slider volumeMusicSlider = new Slider(0f,1f,0.1f,false, skin);
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                parent.playingSong.setVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled( enabled );
                if (musicCheckbox.isChecked()) {
                    parent.playingSong.play();
                }
                else {
                    parent.playingSong.stop();
                }
                return false;
            }
        });

        //sound
        final Slider soundMusicSlider = new Slider(0f,1f,0.1f,false, skin);
        soundMusicSlider.setValue(parent.getPreferences().getSoundVolume());
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setSoundVolume(soundMusicSlider.getValue());
                return false;
            }
        });

        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked( parent.getPreferences().isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                parent.getPreferences().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        //back to menu
        final TextButton backButton = new TextButton("Back", skin); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Pigsrpg.MENU);
            }
        });

        // titles
        final Label titleLabel = new Label("Preferences", skin);
        final Label volumeMusicLabel = new Label("Music Volume", skin);
        final Label volumeSoundLabel = new Label( "Sound Volume", skin);
        final Label musicOnOffLabel = new Label( "Music Effect", skin);
        final Label soundOnOffLabel = new Label( "Sound Effect", skin);

        // table
        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider).width(300);
        table.row();
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row();
        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider).width(300);
        table.row();
        table.add(soundOnOffLabel).left();
        table.add(soundEffectsCheckbox);
        table.row();
        table.add(backButton).colspan(2).fillX();
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        Gdx.gl.glClearColor(0f, 0.2f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        skin.dispose();
        stage.dispose();
    }
}