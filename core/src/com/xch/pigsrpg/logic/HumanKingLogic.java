package com.xch.pigsrpg.logic;

import com.badlogic.gdx.audio.Sound;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.core.KeyBoardController;
import com.xch.pigsrpg.core.Pigsrpg;
import com.xch.pigsrpg.maps.Map;
import com.xch.pigsrpg.ui.MainScreen;
import jdk.tools.jmod.Main;

import javax.xml.parsers.SAXParser;

public class HumanKingLogic {
    public boolean human_run = false, human_attack = false, human_jump = false, human_din = false, human_dout = true, human_fall = false;
    private boolean left = true, right = true, up = true, jump_delay = false, left_delay = false, right_delay = false;
    public boolean attachBar = false, down = false;
    public int human_jump_count = 0;
    private float delay = 0, jumpTimeCounter;
    public static final int HAMMERING_SOUND = 0;
    private KeyBoardController controller;
    private Sound hammering;
    private B2dModel model;
    private Map map;
    private MainScreen mainScreen;
    public HumanKingLogic (KeyBoardController cont, B2dModel md, Map mp, MainScreen ms) {
        model = md;
        map = mp;
        mainScreen = ms;
        controller = cont;
        hammering = Pigsrpg.assMan.manager.get("sound/hammering.wav");
    }

    public void playSound(int sound){
        switch(sound){
            case HAMMERING_SOUND:
                hammering.play();
                break;
        }
    }

    private void humanRun(boolean mode, float delta) { //run:true; jump:false
        if(controller.left && left){
            right_delay = true;
            if (model.humanking.getLinearVelocity().x > 0) {
                model.humanking.setLinearVelocity(0, model.humanking.getLinearVelocity().y);
            }
            if (model.humanking.getLinearVelocity().x >= -100 && mode) {
                model.humanking.applyForceToCenter(-100, 0, true);
            }
            else{
                model.humanking.setLinearVelocity(-100, model.humanking.getLinearVelocity().y);
            }
            if (mode) human_run = true;
            right = true;
            //System.out.println("Left");
        }
        else if(controller.right && right){
            left_delay = true;
            if (model.humanking.getLinearVelocity().x < 0) {
                model.humanking.setLinearVelocity(0, model.humanking.getLinearVelocity().y);
            }
            if (model.humanking.getLinearVelocity().x <= 100 && mode) {
                model.humanking.applyForceToCenter(100, 0, true);
            }
            else {
                model.humanking.setLinearVelocity(100, model.humanking.getLinearVelocity().y);
            }
            if (mode) human_run = true;
            left = true;
            //System.out.println("Right");
        }
        else if(!controller.left && !controller.right){
            if (mode) {
                human_run = false;
            }
            model.humanking.setLinearVelocity(0, model.humanking.getLinearVelocity().y);
            //System.out.println("Stop");
        }
    }

    public void Logic(float delta) {
        //下 地判断
        if (!((boolean) (map.Walllayer.getCell((int) ((model.humanking.getPosition().x - 5) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((model.humanking.getPosition().y - 18) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))
            || attachBar) {
            System.out.print("2\n");
            down = controller.down;
            if (jump_delay) {  // Jump Interval
                if (delay >= delta) {
                    delay = 0;
                    jump_delay = false;
                }
                delay += delta;
            } else if (human_jump_count == 1) {  // Jump Finishing
                model.humanking.setLinearVelocity(model.humanking.getLinearVelocity().x, 0);
                model.humanking.setGravityScale(0f);
                controller.isSpaceDown = false;
                attachBar = false;
                human_jump = false;
                human_jump_count = 0;
            } else {   // Stand On the Ground
                model.humanking.setLinearVelocity(model.humanking.getLinearVelocity().x, 0);
                model.humanking.setGravityScale(0f);
                attachBar = false;
            }
        } else {  // Free Fall
            down = controller.down;
            if (down && human_fall) model.humanking.setLinearVelocity(0,-1);
            if (model.humanking.getLinearVelocity().y == 0f) {
                model.humanking.setGravityScale(1f);
                human_fall = true;
            }
            else human_fall = false;

            if (model.humanking.getLinearVelocity().y != 0f) {
                human_jump_count = 1;
                human_run = false;
                human_jump = true;
            }
        }
        //进门
        if ((Math.abs((model.humanking.getPosition().x) - (float) map.door2.getProperties().get("x")) <= 15) && !human_jump && !human_run && !human_din && controller.entry) {
            mainScreen.resetStateTime();
            model. humanking.setLinearVelocity(0, 0);
            human_din = true;
        }
        //左 墙判断
        if (!((boolean) (map.Walllayer.getCell((int) ((model.humanking.getPosition().x - 20) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((model.humanking.getPosition().y - 7) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))) {
            if (left_delay) {
                if (delay >= delta / 60) {
                    delay = 0;
                    left_delay = false;
                    //System.out.println("delay------");
                }
                delay += delta;
            } else {
                model.humanking.setLinearVelocity(0, model.humanking.getLinearVelocity().y);
                left = false;
//                System.out.println("Left done!");
            }
        } else {
            left = true;
        }
        //右 墙判断
        if (!((boolean) (map.Walllayer.getCell((int) ((model.humanking.getPosition().x + 22) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((model.humanking.getPosition().y - 7) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))
            || attachBar) {
            if (right_delay) {
                if (delay >= delta * 10) {
                    delay = 0;
                    right_delay = false;
                    //System.out.println("delay------");
                }
                delay += delta;
            } else {
                model.humanking.setLinearVelocity(0, model.humanking.getLinearVelocity().y);
                right = false;
                //System.out.println("Right done!");
            }
        } else {
            right = true;
        }
        // 出门后才可运动
        if (!human_dout) {
            //起跳  空格+非跳跃状态
            if (controller.jump && !human_jump) {
                human_run = false;
                model.humanking.setGravityScale(1f);
                model.humanking.applyForceToCenter(0, 6000, true);
                human_jump_count = 2;
                controller.isSpaceDown = true;
                jumpTimeCounter = delta * 30;
                human_jump = true;
                jump_delay = true;
                //System.out.println("Jump");
            }
            //跳跃高度控制 跳跃中+空格持续按下
            if (human_jump && controller.isSpaceDown) {
                if (jumpTimeCounter > 0) {
                    model.humanking.applyForceToCenter(0, 6000, true);
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
                mainScreen.resetStateTime();
                human_run = false;
                human_attack = true;
                model.humanking.setLinearVelocity(0, 0);
                //System.out.println("Attack");
            }
            //平地状态左右跑 非跳 非攻击
            if (!human_jump && !human_attack) {
                humanRun(true, delta);
            }
        }
    }

    public void reload() {
        human_run = false; human_attack = false; human_jump = false; human_din = false; human_dout = true; human_fall = false;
        left = true; right = true; up = true; down = false;
        jump_delay = false; left_delay = false; right_delay = false;
        attachBar = false;
        human_jump_count = 0; delay = 0;

        model.humanking.setTransform((float) map.human.getProperties().get("x"), (float) map.human.getProperties().get("y")+14, 0);
    }
}
