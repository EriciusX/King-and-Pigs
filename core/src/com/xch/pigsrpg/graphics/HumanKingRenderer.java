package com.xch.pigsrpg.graphics;

import com.badlogic.gdx.graphics.g2d.*;
import com.xch.pigsrpg.core.B2dModel;
import com.xch.pigsrpg.core.GameLogic;
import com.xch.pigsrpg.core.Pigsrpg;

public class HumanKingRenderer {
    private static TextureRegion jumpTex;
    private static TextureRegion jumpingTex;
    private static TextureRegion downTex;
    public static Animation idleAnimation, runAnimation, attackAnimation, dinAnimation, doutAnimation;
    private final TextureAtlas humanAtlas;
    private static TextureRegion currentFrame;
    
    public HumanKingRenderer (Pigsrpg pigsrpg) {
        Pigsrpg parent = pigsrpg;
        humanAtlas = parent.assMan.manager.get("human/humanking.atlas");
        jumpTex = humanAtlas.findRegion("Jump");
        jumpingTex = humanAtlas.findRegion("Fall");
        downTex = humanAtlas.findRegion("Ground");
        idleAnimation = new Animation(0.12f, humanAtlas.findRegions("idle"), Animation.PlayMode.LOOP);
        runAnimation = new Animation(0.1f, humanAtlas.findRegions("run"), Animation.PlayMode.LOOP);
        attackAnimation = new Animation(0.09f, humanAtlas.findRegions("attack"), Animation.PlayMode.NORMAL);
        dinAnimation = new Animation(0.15f, humanAtlas.findRegions("din"), Animation.PlayMode.NORMAL);
        doutAnimation = new Animation(0.15f, humanAtlas.findRegions("dout"), Animation.PlayMode.NORMAL);
    }

    public static void drawHuman(float stateTime, SpriteBatch sb, B2dModel model, GameLogic gameLogic, int gameState){
        // dout
        if (model.human_dout) {
            if (DoorRenderer.doorState == 1) {
                currentFrame = (TextureRegion) doutAnimation.getKeyFrame(stateTime);
                sb.draw(currentFrame, model.player.getPosition().x - 28, model.player.getPosition().y - 28);
                if (doutAnimation.isAnimationFinished(stateTime)) {
                    DoorRenderer.doorState = 2;
                    model.human_dout = false;
                }
            }
        }
        // run
        else if (model.human_run) {
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
            if (DoorRenderer.doorState == 4) {
                currentFrame = (TextureRegion) dinAnimation.getKeyFrame(stateTime);
                sb.draw(currentFrame, model.player.getPosition().x - 28, model.player.getPosition().y - 28);
                if (dinAnimation.isAnimationFinished(stateTime)) {
                    DoorRenderer.doorState = 5;
                    model.human_din = false;
                    gameLogic.gameFinish = true;
                }
            }
            else {
                sb.draw(jumpingTex, model.player.getPosition().x - 28, model.player.getPosition().y - 28);
            }
        }
        // idle
        else {
            if (gameState == 1) { idleAnimation.setPlayMode(Animation.PlayMode.NORMAL); }
            currentFrame = (TextureRegion) idleAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.player.getPosition().x - 28, model.player.getPosition().y - 28);
        }
    }
}
