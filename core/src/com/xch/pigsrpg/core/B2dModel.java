package com.xch.pigsrpg.core;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.xch.pigsrpg.ui.MainScreen;

import java.util.Objects;

public class B2dModel {
    public World world;
    private OrthographicCamera camera;
    private KeyBoardController controller;
    private B2dAssetManager assMan;
    public boolean isSwimming = false;
    public Body player;
    private Sound hammering;
    private MapObject human, door2;
    public TiledMapTileLayer Walllayer;
    public boolean human_run = false, human_attack = false, human_jump = false, human_din = false, human_dout = true;
    private boolean left = true, right = true, up = true, jump_delay = false, left_delay = false, right_delay = false;
    public int human_jump_count = 0;
    private float delay = 0, jumpTimeCounter;
    public static final int HAMMERING_SOUND = 0;
    public B2dModel(KeyBoardController cont, OrthographicCamera cam, B2dAssetManager assetManager){
        this.assMan = assetManager;
        controller = cont;
        camera = cam;
        world = new World(new Vector2(0,-1500f), true);
        //world.setContactListener(new B2dContactListener(this));
        loadAssets();
        //createFloor();

        // get our body factory singleton and store it in bodyFactory
        BodyFactory bodyFactory = BodyFactory.getInstance(world);

        // add a player
        player = bodyFactory.makeBoxPolyBody((float) human.getProperties().get("x"), (float) human.getProperties().get("y")+14, 38, 28, BodyFactory.HUMAN, BodyType.DynamicBody,false);
        player.setBullet(true);
        MainScreen.resetStateTime();
    }

    private void loadAssets() {
        // load loading images and wait until finished
        assMan.queueAddMap();
        assMan.queueAddImages();
        assMan.manager.finishLoading();
        hammering = assMan.manager.get("sound/hammering.wav");
        TiledMap map = assMan.manager.get("map/1.tmx");
        MapLayer objectLayer = map.getLayers().get("对象层 1");
        Walllayer = (TiledMapTileLayer) map.getLayers().get("map");
        MapObjects objects = objectLayer.getObjects();
        human = objects.get("humanking2");
        door2 = objects.get("door2");
    }

    public boolean pointIntersectsBody(Body body, Vector2 mouseLocation){
        Vector3 mousePos = new Vector3(mouseLocation,0); //convert mouseLocation to 3D position
        camera.unproject(mousePos); // convert from screen potition to world position
        if(body.getFixtureList().first().testPoint(mousePos.x, mousePos.y)){
            return true;
        }
        return false;
    }
    public void playSound(int sound){
        switch(sound){
            case HAMMERING_SOUND:
                hammering.play();
                break;
        }
    }

    private void humanRun(boolean mode, float delta) { //run:true; jump:false
//        final float speed = 6250;
        if(controller.left && left){
            right_delay = true;
            if (player.getLinearVelocity().x > 0) {
                player.setLinearVelocity(0, player.getLinearVelocity().y);
            }
            if (player.getLinearVelocity().x >= -100 && mode) {
                player.applyForceToCenter(-100, 0, true);
            }
            else{
                player.setLinearVelocity(-100, player.getLinearVelocity().y);
            }
            if (mode) human_run = true;
            right = true;
            //System.out.println("Left");
        }
        else if(controller.right && right){
            left_delay = true;
            if (player.getLinearVelocity().x < 0) {
                player.setLinearVelocity(0, player.getLinearVelocity().y);
            }
            if (player.getLinearVelocity().x <= 100 && mode) {
                player.applyForceToCenter(100, 0, true);
            }
            else {
                player.setLinearVelocity(100, player.getLinearVelocity().y);
            }
            if (mode) human_run = true;
            left = true;
            //System.out.println("Right");
        }
        else if(!controller.left && !controller.right){
            if (mode) {
                human_run = false;
            }
            player.setLinearVelocity(0, player.getLinearVelocity().y);
            //System.out.println("Stop");
        }
    }

    private void humanLogic(float delta) {
        //下 地判断
        if (!((boolean) (Walllayer.getCell((int) ((player.getPosition().x - 5) / 32) + (int) Walllayer.getProperties().get("width_min"), (int) ((player.getPosition().y - 18) / 32) + (int) Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))) {
            if (jump_delay) {
                if (delay >= delta / 60) {
                    delay = 0;
                    jump_delay = false;
                }
                delay += delta;
            } else if (human_jump_count == 1) {
                player.setLinearVelocity(player.getLinearVelocity().x, 0);
                player.setGravityScale(0f);
                //是否贴地
                controller.isSpaceDown = false;
                human_jump = false;
                human_jump_count = 0;
            } else {
                player.setLinearVelocity(player.getLinearVelocity().x, 0);
                player.setGravityScale(0f);
            }
            //System.out.println("Down done!");
        } else {
            player.setGravityScale(1f);
            human_run = false;
            human_jump = true;
            human_jump_count = 1;
        }
        //进门
        if ((Math.abs((player.getPosition().x) - (float) door2.getProperties().get("x")) <= 15) && !human_jump && !human_run && !human_din && controller.entry) {
            MainScreen.resetStateTime();
            player.setLinearVelocity(0,0);
            human_din = true;
        }
        //左 墙判断
        if (!((boolean) (Walllayer.getCell((int) ((player.getPosition().x - 20) / 32) + (int) Walllayer.getProperties().get("width_min"), (int) ((player.getPosition().y - 7) / 32) + (int) Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))) {
            if (left_delay) {
                if (delay >= delta / 60) {
                    delay = 0;
                    left_delay = false;
                    //System.out.println("delay------");
                }
                delay += delta;
            } else {
                player.setLinearVelocity(0, player.getLinearVelocity().y);
                left = false;
                //System.out.println("Left done!");
            }
        } else {
            left = true;
        }
        //右 墙判断
        if (!((boolean) (Walllayer.getCell((int) ((player.getPosition().x + 22) / 32) + (int) Walllayer.getProperties().get("width_min"), (int) ((player.getPosition().y - 7) / 32) + (int) Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))) {
            if (right_delay) {
                if (delay >= delta * 10) {
                    delay = 0;
                    right_delay = false;
                    //System.out.println("delay------");
                }
                delay += delta;
            } else {
                player.setLinearVelocity(0, player.getLinearVelocity().y);
                right = false;
                //System.out.println("Right done!");
            }
        } else {
            right = true;
        }
        // 出门后才可运动
        if (!human_dout) {
            //起跳  空格+非跳跃状态
            if (!human_din) {
                if (controller.jump && !human_jump) {
                    human_run = false;
                    player.setGravityScale(1f);
                    player.applyForceToCenter(0, 6000, true);
                    human_jump_count = 2;
                    controller.isSpaceDown = true;
                    jumpTimeCounter = delta * 50;
                    human_jump = true;
                    jump_delay = true;
                    //System.out.println("Jump");
                }
                //跳跃高度控制 跳跃中+空格持续按下
                if (human_jump && controller.isSpaceDown) {
                    if (jumpTimeCounter > 0) {
                        player.applyForceToCenter(0, 6000, true);
                        jumpTimeCounter -= delta;
                    }
                }
                //跳跃中状态 跑或攻击
                if (human_jump) {
                    //跳跃中攻击
                    humanRun(false, delta);
                }
                //攻击
                if ((controller.attack || (controller.attack && human_jump)) && !human_attack) {
                    playSound(0);
                    MainScreen.resetStateTime();
                    human_run = false;
                    human_attack = true;
                    player.setLinearVelocity(0, 0);
                    //System.out.println("Attack");
                }
                //平地状态左右跑 非跳 非攻击
                if (!human_jump && !human_attack) {
                    humanRun(true, delta);
                }
            }
        }
    }

    public void logicStep(float delta){
        humanLogic(delta);
        world.step(delta,3,3);
    }

    private double Distance(int x1,int x2,int y1,int y2){
        double distance = Math.sqrt(Math.pow(Math.abs(x1-x2), 2)+Math.pow(Math.abs(y1-y2), 2));
        return distance;
    }

}
