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
import com.xch.pigsrpg.core.KeyBoardController;
import com.badlogic.gdx.Screen;
import com.xch.pigsrpg.core.Pigsrpg;
import java.awt.*;

public class MainScreen implements Screen {
    private Pigsrpg parent;
    private static B2dModel model;
    protected static OrthographicCamera cam;
    protected OrthographicCamera hudcam;
    private KeyBoardController controller;
    private static Box2DDebugRenderer debugRenderer;
    private TextureAtlas atlas;
    protected static SpriteBatch sb;
    private static TextureRegion jumpTex;
    private static TextureRegion jumpingTex;
    private static TextureRegion downTex;
    private static Animation idleAnimation;
    private static Animation runAnimation;
    private static Animation attackAnimation;
    private static Animation dinAnimation;
    private static Animation doutAnimation;
    private static TextureRegion currentFrame;
    private TiledMap map;
    private static MapRenderer mapRenderer;
    private MapObjects objects;
    private MapObject human;
    private static final int GAME_RUNNING = 0;
    private static final int GAME_PAUSED = 1;
    private static final int GAME_OVER = 2;
    private static final int GAME_LEVEL_END = 3;
    private int gameState = 0;
    private static float stateTime, totalTimeCount;
    private static BitmapFont font = new BitmapFont();
    public MainScreen(Pigsrpg pigsrpg){
        // init
        parent = pigsrpg;
        cam = parent.getCamera();
        hudcam = parent.getHudCamera();
        sb = parent.getSpriteBatch();
        controller = new KeyBoardController();
        model = new B2dModel(controller, cam, parent.assMan);
        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

        // assets
        parent.assMan.queueAddImages();
        parent.assMan.manager.finishLoading();

        // animation
        atlas = parent.assMan.manager.get("human/humanking.atlas");
        jumpTex = atlas.findRegion("Jump");
        jumpingTex = atlas.findRegion("Fall");
        downTex = atlas.findRegion("Ground");
        idleAnimation = new Animation(0.12f, atlas.findRegions("idle"), Animation.PlayMode.LOOP);
        runAnimation = new Animation(0.1f, atlas.findRegions("run"), Animation.PlayMode.LOOP);
        attackAnimation = new Animation(0.09f, atlas.findRegions("attack"), Animation.PlayMode.NORMAL);
        dinAnimation = new Animation(0.15f, atlas.findRegions("din"), Animation.PlayMode.NORMAL);
        doutAnimation = new Animation(0.15f, atlas.findRegions("dout"), Animation.PlayMode.NORMAL);

        // map
        map = parent.assMan.manager.get(("".format("map/%d.tmx", parent.levelMap)));
        MapLayer layer = map.getLayers().get("对象层 1");
        objects = layer.getObjects();
        human = objects.get("humanking2");
        cam.position.x = (float) human.getProperties().get("x");
        cam.position.y = (float) human.getProperties().get("y")+96;

        mapRenderer = new OrthogonalTiledMapRenderer(map, sb);
        cam.zoom = Pigsrpg.V_SCALE;
        cam.update();
    }

    private static void drawHuman(){
        // run
        if (model.human_run) {
            currentFrame = (TextureRegion) runAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.player.getPosition().x - 28, model.player.getPosition().y - 28);
        }
        // jump
        else if (model.human_jump){
            if (model.human_jump_count == 2) {
                sb.draw(jumpTex, model.player.getPosition().x - 28, model.player.getPosition().y - 28);
                model.human_jump_count -= 1;
            }else if (model.human_jump_count == 1) {
                if (model.human_attack) {
                    currentFrame = (TextureRegion) attackAnimation.getKeyFrame(stateTime);
                    sb.draw(currentFrame, model.player.getPosition().x - 28, model.player.getPosition().y - 28);
                    if(attackAnimation.isAnimationFinished(stateTime)){
                        model.human_attack = false;
                    }
                }
                else {
                    sb.draw(jumpingTex, model.player.getPosition().x - 28, model.player.getPosition().y - 28);
                }
            }
            else if (model.human_jump_count == 0) {
                sb.draw(downTex, model.player.getPosition().x - 28, model.player.getPosition().y - 28);
                model.human_jump = false;
            }
        }
        // attack
        else if (model.human_attack) {
            currentFrame = (TextureRegion) attackAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.player.getPosition().x - 28, model.player.getPosition().y - 28);
            if(attackAnimation.isAnimationFinished(stateTime)){
                model.human_attack = false;
            }
        }
        // din
        else if (model.human_din) {
            currentFrame = (TextureRegion) dinAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.player.getPosition().x - 28, model.player.getPosition().y - 28);
            if(dinAnimation.isAnimationFinished(stateTime)){
                model.human_din = false;
            }
        }
        // dout
        else if (model.human_dout) {
            currentFrame = (TextureRegion) doutAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.player.getPosition().x - 28, model.player.getPosition().y - 28);
            if(doutAnimation.isAnimationFinished(stateTime)){
                model.human_dout = false;
            }
        }
        // idle
        else {
            currentFrame = (TextureRegion) idleAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.player.getPosition().x - 28, model.player.getPosition().y - 28);
        }
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

        // Cam
        cam.position.x = model.player.getPosition().x-19;
        cam.position.y = model.player.getPosition().y-14;
        cam.update();

        // Draw Sb
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        drawHuman();

        font.draw(sb, "FPS = " + 1 / (delta), cam.position.x - 100, cam.position.y + 100);
        sb.end();

        switch  (gameState) {
            case GAME_RUNNING:
                setGameRunning(delta);
            case GAME_PAUSED:
                setGamePaused(delta);
            case GAME_OVER:
                setGameOver(delta);
            case GAME_LEVEL_END:
                setGameLevelEnd(delta);
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
    }

    public static void setGameRunning(float delta) {
        // Logic
        model.logicStep(delta);
    }

    public static void setGamePaused(float delta) {

    }
    public static void setGameOver(float delta) {

    }
    public static void setGameLevelEnd(float delta) {

    }

    public static void resetStateTime(){
        stateTime = 0f;
    }
}