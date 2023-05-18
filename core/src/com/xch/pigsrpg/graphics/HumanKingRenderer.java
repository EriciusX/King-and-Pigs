package com.xch.pigsrpg.graphics;

import com.badlogic.gdx.graphics.g2d.*;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.logic.GameLogic;
import com.xch.pigsrpg.core.Pigsrpg;
import com.xch.pigsrpg.logic.HumanKingLogic;


public class HumanKingRenderer {
    private static TextureRegion jumpTex;
    private static TextureRegion jumpingTex;
    private static TextureRegion downTex;
    public static Animation idleAnimation, runAnimation, attackAnimation, dinAnimation, doutAnimation, deadAnimation;
    private final TextureAtlas humanAtlas;
    private static TextureRegion currentFrame;
    
    public HumanKingRenderer (Pigsrpg pigsrpg) {
        Pigsrpg parent = pigsrpg;
        humanAtlas = parent.assMan.manager.get(parent.assMan.humanking);
        jumpTex = humanAtlas.findRegion("Jump");
        jumpingTex = humanAtlas.findRegion("Fall");
        downTex = humanAtlas.findRegion("Ground");
        idleAnimation = new Animation(0.12f, humanAtlas.findRegions("idle"), Animation.PlayMode.LOOP);
        runAnimation = new Animation(0.1f, humanAtlas.findRegions("run"), Animation.PlayMode.LOOP);
        attackAnimation = new Animation(0.09f, humanAtlas.findRegions("attack"), Animation.PlayMode.NORMAL);
        dinAnimation = new Animation(0.15f, humanAtlas.findRegions("din"), Animation.PlayMode.NORMAL);
        doutAnimation = new Animation(0.15f, humanAtlas.findRegions("dout"), Animation.PlayMode.NORMAL);
        deadAnimation = new Animation(0.5f, humanAtlas.findRegions("dead"), Animation.PlayMode.NORMAL);
    }

    public void drawHuman(float stateTime, SpriteBatch sb, HumanKingLogic humanKingLogic, B2dModel model, int gameState){
        // dout
        if (humanKingLogic.human_dout) {
            if (DoorRenderer.doorState == 1) {
                currentFrame = (TextureRegion) doutAnimation.getKeyFrame(stateTime);
                sb.draw(currentFrame, model.humanking.getPosition().x - 28, model.humanking.getPosition().y - 28);
                if (doutAnimation.isAnimationFinished(stateTime)) {
                    DoorRenderer.doorState = 2;
                    humanKingLogic.human_dout = false;
                }
            }
        }
        // run
        else if (humanKingLogic.human_run) {
            currentFrame = (TextureRegion) runAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.humanking.getPosition().x - 28, model.humanking.getPosition().y - 28);
        }
        // jump
        else if (humanKingLogic.human_jump){
            if (humanKingLogic.human_jump_count == 2) {
                sb.draw(jumpTex, model.humanking.getPosition().x - 28, model.humanking.getPosition().y - 28);
                humanKingLogic.human_jump_count -= 1;
            }else if (humanKingLogic.human_jump_count == 1) {
                if (humanKingLogic.human_attack) {
                    currentFrame = (TextureRegion) attackAnimation.getKeyFrame(stateTime);
                    sb.draw(currentFrame, model.humanking.getPosition().x - 28, model.humanking.getPosition().y - 28);
                    if(attackAnimation.isAnimationFinished(stateTime)) {
                        humanKingLogic.human_attack = false;
                        humanKingLogic.destraySensor();
                    }
                }
                else {
                    sb.draw(jumpingTex, model.humanking.getPosition().x - 28, model.humanking.getPosition().y - 28);
                }
            }
            else if (humanKingLogic.human_jump_count == 0) {
                sb.draw(downTex, model.humanking.getPosition().x - 28, model.humanking.getPosition().y - 28);
                humanKingLogic.human_jump = false;
            }
        }
        // attack
        else if (humanKingLogic.human_attack) {
            currentFrame = (TextureRegion) attackAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.humanking.getPosition().x - 28, model.humanking.getPosition().y - 28);
            if(attackAnimation.isAnimationFinished(stateTime)) {
                humanKingLogic.human_attack = false;
                humanKingLogic.destraySensor();
            }
        }
        // din
        else if (humanKingLogic.human_din) {
            if (DoorRenderer.doorState == 4) {
                currentFrame = (TextureRegion) dinAnimation.getKeyFrame(stateTime);
                sb.draw(currentFrame, model.humanking.getPosition().x - 28, model.humanking.getPosition().y - 28);
                if (dinAnimation.isAnimationFinished(stateTime)) {
                    DoorRenderer.doorState = 5;
                    humanKingLogic.human_din = false;
                    GameLogic.gameFinish = true;
                }
            }
            else {
                sb.draw(jumpingTex, model.humanking.getPosition().x - 28, model.humanking.getPosition().y - 28);
            }
        }
        // dead
        else if (humanKingLogic.human_dead) {
            currentFrame = (TextureRegion) deadAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.humanking.getPosition().x - 28, model.humanking.getPosition().y - 28);
            if (deadAnimation.isAnimationFinished(stateTime)) {
                humanKingLogic.human_dead = false;
                GameLogic.gameFinish = true;
            }
        }
        // idle
        else {
            if (gameState == 1) { idleAnimation.setPlayMode(Animation.PlayMode.NORMAL); }
            currentFrame = (TextureRegion) idleAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.humanking.getPosition().x - 28, model.humanking.getPosition().y - 28);
        }
    }
}
