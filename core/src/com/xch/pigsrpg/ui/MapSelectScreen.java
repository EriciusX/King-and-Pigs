package com.xch.pigsrpg.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.xch.pigsrpg.core.Pigsrpg;

public class MapSelectScreen extends InputAdapter implements Screen {
    private Pigsrpg parent;
    private Stage stage;
    private Skin skin;
    private Label titleLabel;
    public MapSelectScreen(Pigsrpg pigsrpg){
        parent = pigsrpg;
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

//        parent.assMan.queueAddSkin();
        parent.assMan.manager.finishLoading();
        skin = parent.assMan.manager.get("UI/golden/golden-ui-skin.json");
        // stage 1
        final TextButton stage1 = new TextButton("1", skin);
        stage1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Map 1");
                parent.levelMap = 0;
                parent.changeScreen(Pigsrpg.MENU);
            }
        });

        // stage 2
        final TextButton stage2 = new TextButton("2", skin);
        stage2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Map 2");
                parent.levelMap = 1;
                parent.changeScreen(Pigsrpg.MENU);
            }
        });

        // back to menu
        final TextButton backButton = new TextButton("Back", skin); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Pigsrpg.MENU);
            }
        });

        titleLabel = new Label( "Stage Select", skin );

        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        table.add(stage1).width(250);
        table.add(stage2).width(250);
        table.row();
        table.add(backButton).colspan(2).fillX();
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0.2f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }

}
