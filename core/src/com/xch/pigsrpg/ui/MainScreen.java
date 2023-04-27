package com.xch.pigsrpg.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.xch.pigsrpg.core.B2dModel;
import com.xch.pigsrpg.core.GameLogic;
import com.xch.pigsrpg.core.KeyBoardController;
import com.badlogic.gdx.Screen;
import com.xch.pigsrpg.core.Pigsrpg;
import com.xch.pigsrpg.graphics.DoorRenderer;
import com.xch.pigsrpg.graphics.HumanKingRenderer;

import javax.imageio.stream.ImageInputStream;

import static com.xch.pigsrpg.graphics.HumanKingRenderer.drawHuman;
import static com.xch.pigsrpg.graphics.DoorRenderer.drawDoor;

public class MainScreen implements Screen {
    private static Pigsrpg parent;
    private static B2dModel model;
    private static GameLogic gameLogic;
    protected static OrthographicCamera cam;
    protected OrthographicCamera hudcam;
    private final KeyBoardController controller;
    private final Box2DDebugRenderer debugRenderer;
    protected SpriteBatch sb;
    private static MapRenderer mapRenderer;
    private final MapObject human, door1, door2;
    private static final int GAME_RUNNING = 0;
    private static final int GAME_PAUSED = 1;
    private static final int GAME_OVER = 2;
    private static final int GAME_LEVEL_END = 3;
    public static int gameState = 0;
    private static float stateTime, totalTimeCount;
    private final BitmapFont font = new BitmapFont();
    public MainScreen(Pigsrpg pigsrpg){
        // init
        parent = pigsrpg;
        cam = parent.getCamera();
        hudcam = parent.getHudCamera();
        sb = parent.getSpriteBatch();
        controller = new KeyBoardController();
        model = new B2dModel(controller, cam, parent.assMan);
        gameLogic = new GameLogic(controller, parent, model);
        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

        // assets
        parent.assMan.queueAddImages();
        parent.assMan.manager.finishLoading();

        // renderer
        HumanKingRenderer humanKingRenderer = new HumanKingRenderer(parent);
        DoorRenderer doorRenderer = new DoorRenderer(parent);

        // map
        TiledMap map = parent.assMan.manager.get((String.format("map/%s", parent.maps.get(parent.levelMap))));
        MapLayer layer = map.getLayers().get("对象层 1");
        MapObjects objects = layer.getObjects();
        human = objects.get("humanking2");
        door1 = objects.get("door1");
        door2 = objects.get("door2");
        cam.position.x = (float) human.getProperties().get("x");
        cam.position.y = (float) human.getProperties().get("y")+96;

        mapRenderer = new OrthogonalTiledMapRenderer(map, sb);
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
        mapRenderer.setView(cam);
        mapRenderer.render();

        // date
        stateTime += delta;
        totalTimeCount += delta;

        //debug render
        debugRenderer.render(model.world, cam.combined);

        // Draw Sb
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        drawDoor(stateTime, sb, model, door1, door2);
        drawHuman(stateTime, sb, model, gameLogic, gameState);

        font.draw(sb, "FPS = " + 1 / (delta), cam.position.x - 100, cam.position.y + 100);
        sb.end();

        // Cam
        cam.position.x = model.player.getPosition().x-19;
        cam.position.y = model.player.getPosition().y-14;
        cam.update();

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
        sb.dispose();
        font.dispose();
    }

    private void setGameRunning(float delta) {
        // Logic
        model.logicStep(delta);
        gameLogic.logicStep(delta);
    }

    private void setGamePaused(float delta) {
        gameLogic.logicStep(delta);
    }
    private void setGameOver(float delta) {
        parent.levelMap += 1;
        parent.changeScreen(Pigsrpg.MENU);
    }
    private void setGameLevelEnd(float delta) {
        parent.changeScreen(Pigsrpg.MENU);
    }

    public static void resetStateTime(){
        stateTime = 0f;
    }
}