package com.xch.pigsrpg.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.xch.pigsrpg.body.B2dContactListener;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.core.Logic;
import com.xch.pigsrpg.graphics.Renderer;
import com.xch.pigsrpg.logic.GameLogic;
import com.xch.pigsrpg.core.KeyBoardController;
import com.badlogic.gdx.Screen;
import com.xch.pigsrpg.core.Pigsrpg;
import com.xch.pigsrpg.maps.Map;

public class MainScreen implements Screen {
    private final Pigsrpg parent;
    public final B2dModel model;
    private final B2dContactListener listener;
    private final GameLogic gameLogic;
    protected OrthographicCamera cam;
    protected OrthographicCamera hudcam;
    private final KeyBoardController controller;
    private final Box2DDebugRenderer debugRenderer;
    protected SpriteBatch sb;
    private static final int GAME_RUNNING = 0;
    private static final int GAME_PAUSED = 1;
    private static final int GAME_OVER = 2;
    private static final int GAME_LEVEL_END = 3;
    public int gameState = 0;
    private float stateTime, totalTimeCount;
    private final Map map;
    public final World world;
    private final Logic logic;
    private final Renderer renderer;
    private final BitmapFont font = new BitmapFont();
    public MainScreen(Pigsrpg pigsrpg){
        // init
        parent = pigsrpg;
        parent.assMan.queueAddImages();
        parent.assMan.manager.finishLoading();

        cam = parent.getCamera();
        hudcam = parent.getHudCamera();
        sb = parent.getSpriteBatch();
        controller = new KeyBoardController();
        map = new Map(parent, sb);
        world = new World(new Vector2(0,-1500f), true);
        model = new B2dModel(cam, parent.assMan, world, map);
        gameLogic = new GameLogic(controller, parent, this);
        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

        // logic
        logic = new Logic(controller, map, this);

        // renderer
        renderer = new Renderer(parent, sb, logic, map, model, this, cam);

        // map
        cam.position.x = (float) map.human.getProperties().get("x");
        cam.position.y = (float) map.human.getProperties().get("y")+96;

        listener = new B2dContactListener(logic, model, world);
        world.setContactListener(listener);

        cam.zoom = Pigsrpg.V_SCALE;
        cam.update();
    }

    @Override
    public void show() {
        stateTime = 0f;
        totalTimeCount = 0f;
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        // background
        Gdx.gl.glClearColor(0.24f,0.219f,0.317f,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // mapRender
        map.mapRender(cam);

        // date
        stateTime += delta;
        totalTimeCount += delta;

        // Draw Sb
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        renderer.render(delta);

        font.draw(sb, "FPS " + 1 / (delta), cam.position.x - 100, cam.position.y + 100);
        sb.end();

        // Cam
        cam.position.x = model.humanking.getPosition().x-19;
        cam.position.y = model.humanking.getPosition().y-14;
        cam.update();

        //debug render
        debugRenderer.render(world, cam.combined);

        switch  (gameState) {
            case GAME_RUNNING:
                setGameRunning(delta);
                break;
            case GAME_PAUSED:
                setGamePaused(delta);
                break;
            case GAME_OVER:
                setGameOver(delta);
                break;
            case GAME_LEVEL_END:
                setGameLevelEnd(delta);
                break;
        }
    }

    @Override
    public void resize(int width, int height) {

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
        gameState = 0;
        stateTime = 0;
        logic.reload();
        gameLogic.reload();
        renderer.reload();

        // Manual Dispose
        world.dispose();
        debugRenderer.dispose();
    }

    private void setGameRunning(float delta) {
        // Logic
        world.step(delta,3,3);
        logic.allLogic(delta);
        gameLogic.logicStep(delta);
    }

    private void setGamePaused(float delta) {
        gameLogic.logicStep(delta);
    }

    private void setGameOver(float delta) {
        this.dispose();
        parent.changeScreen(Pigsrpg.MENU);
    }

    private void setGameLevelEnd(float delta) {
        this.dispose();
        parent.changeScreen(Pigsrpg.MENU);
    }
}