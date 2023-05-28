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
    public static Animation idleAnimation, runAnimation, attackAnimation, dinAnimation, doutAnimation, deadAnimation, hitAnimation;
    private final TextureAtlas humanAtlas;
    private static TextureRegion currentFrame;
    private float stateTime, normalStateTime;
    private int offset_x = -32;
    private final int  offset_y = -29;
    private int playerheart_temp = 3;
    public HumanKingRenderer (Pigsrpg pigsrpg) {
        Pigsrpg parent = pigsrpg;
        humanAtlas = parent.assMan.manager.get(parent.assMan.humanking);
        jumpTex = humanAtlas.findRegion("Jump");
        jumpingTex = humanAtlas.findRegion("Fall");
        downTex = humanAtlas.findRegion("Ground");
        idleAnimation = new Animation(0.12f, humanAtlas.findRegions("idle"), Animation.PlayMode.LOOP);
        runAnimation = new Animation(0.1f, humanAtlas.findRegions("run"), Animation.PlayMode.LOOP);
        attackAnimation = new Animation(0.05f, humanAtlas.findRegions("attack"), Animation.PlayMode.NORMAL);
        dinAnimation = new Animation(0.15f, humanAtlas.findRegions("din"), Animation.PlayMode.NORMAL);
        doutAnimation = new Animation(0.15f, humanAtlas.findRegions("dout"), Animation.PlayMode.NORMAL);
        deadAnimation = new Animation(0.5f, humanAtlas.findRegions("dead"), Animation.PlayMode.NORMAL);
        hitAnimation = new Animation(0.1f, humanAtlas.findRegions("hit"), Animation.PlayMode.NORMAL);
        stateTime = 0;
        normalStateTime = 0;
    }

    public void drawHuman(float delta, SpriteBatch sb, HumanKingLogic humanKingLogic, B2dModel model, int gameState){
        // hit
        if (playerheart_temp != HumanKingLogic.playerHeart && playerheart_temp > HumanKingLogic.playerHeart) {
            currentFrame = (TextureRegion) hitAnimation.getKeyFrame(normalStateTime);
            judgDirection(humanKingLogic, currentFrame);
            sb.draw(currentFrame, model.humanking.getPosition().x + offset_x, model.humanking.getPosition().y + offset_y);
            if (hitAnimation.isAnimationFinished(normalStateTime)) {
                playerheart_temp -= 1;
                normalStateTime = 0;
            }
            normalStateTime += delta;
        }
        // dout
        else if (humanKingLogic.human_dout) {
            if (DoorRenderer.doorState == 1) {
                currentFrame = (TextureRegion) doutAnimation.getKeyFrame(normalStateTime);
                judgDirection(humanKingLogic, currentFrame);
                sb.draw(currentFrame, model.humanking.getPosition().x + offset_x, model.humanking.getPosition().y + offset_y);
                if (doutAnimation.isAnimationFinished(normalStateTime)) {
                    normalStateTime = 0;
                    DoorRenderer.doorState = 2;
                    humanKingLogic.human_dout = false;
                }
                normalStateTime += delta;
            }
        }
        // run
        else if (humanKingLogic.human_run) {
            currentFrame = (TextureRegion) runAnimation.getKeyFrame(stateTime);
            judgDirection(humanKingLogic, currentFrame);
            sb.draw(currentFrame, model.humanking.getPosition().x + offset_x, model.humanking.getPosition().y + offset_y);
            stateTime += delta;
        }
        // jump
        else if (humanKingLogic.human_jump){
            if (humanKingLogic.human_jump_count == 2) {
                judgDirection(humanKingLogic, jumpTex);
                sb.draw(jumpTex, model.humanking.getPosition().x + offset_x, model.humanking.getPosition().y + offset_y);
                humanKingLogic.human_jump_count -= 1;
            }else if (humanKingLogic.human_jump_count == 1) {
                if (humanKingLogic.human_attack) {
                    currentFrame = (TextureRegion) attackAnimation.getKeyFrame(normalStateTime);
                    judgDirection(humanKingLogic, currentFrame);
                    sb.draw(currentFrame, model.humanking.getPosition().x + offset_x, model.humanking.getPosition().y + offset_y);
                    if(attackAnimation.isAnimationFinished(normalStateTime)) {
                        humanKingLogic.human_attack = false;
                        humanKingLogic.destraySensor();
                        normalStateTime = 0;
                    }
                    normalStateTime += delta;
                }
                else {
                    judgDirection(humanKingLogic, jumpingTex);
                    sb.draw(jumpingTex, model.humanking.getPosition().x + offset_x, model.humanking.getPosition().y + offset_y);
                }
            }
            else if (humanKingLogic.human_jump_count == 0) {
                judgDirection(humanKingLogic, downTex);
                sb.draw(downTex, model.humanking.getPosition().x + offset_x, model.humanking.getPosition().y + offset_y);
                humanKingLogic.human_jump = false;
            }
        }
        // attack
        else if (humanKingLogic.human_attack) {
            currentFrame = (TextureRegion) attackAnimation.getKeyFrame(normalStateTime);
            judgDirection(humanKingLogic, currentFrame);
            sb.draw(currentFrame, model.humanking.getPosition().x + offset_x, model.humanking.getPosition().y + offset_y);
            if (attackAnimation.isAnimationFinished(normalStateTime)) {
                humanKingLogic.human_attack = false;
                humanKingLogic.destraySensor();
                normalStateTime = 0;
            }
            normalStateTime += delta;
        }
        // din
        else if (humanKingLogic.human_din) {
            if (DoorRenderer.doorState == 4) {
                currentFrame = (TextureRegion) dinAnimation.getKeyFrame(normalStateTime);
                judgDirection(humanKingLogic, currentFrame);
                sb.draw(currentFrame, model.humanking.getPosition().x + offset_x, model.humanking.getPosition().y + offset_y);
                if (dinAnimation.isAnimationFinished(normalStateTime)) {
                    normalStateTime = 0;
                    DoorRenderer.doorState = 5;
                    humanKingLogic.human_din = false;
                    GameLogic.gameFinish = true;
                }
                normalStateTime += delta;
            }
            else {
                sb.draw(jumpingTex, model.humanking.getPosition().x + offset_x, model.humanking.getPosition().y + offset_y);
            }
        }
        // dead
        else if (humanKingLogic.human_dead) {
            currentFrame = (TextureRegion) deadAnimation.getKeyFrame(stateTime);
            judgDirection(humanKingLogic, currentFrame);
            sb.draw(currentFrame, model.humanking.getPosition().x + offset_x, model.humanking.getPosition().y + offset_y);
            if (deadAnimation.isAnimationFinished(stateTime)) {
                humanKingLogic.human_dead = false;
                GameLogic.gameFinish = true;
            }
            stateTime += delta;
        }
        // idle
        else {
            if (gameState == 1) { idleAnimation.setPlayMode(Animation.PlayMode.NORMAL); }
            currentFrame = (TextureRegion) idleAnimation.getKeyFrame(stateTime);
            judgDirection(humanKingLogic, currentFrame);
            sb.draw(currentFrame, model.humanking.getPosition().x + offset_x, model.humanking.getPosition().y + offset_y);
            stateTime += delta;
        }
        if (HumanKingLogic.playerHeart > playerheart_temp) playerheart_temp = HumanKingLogic.playerHeart;
    }

    private void judgDirection (HumanKingLogic logic, TextureRegion textureRegion) {
        if (logic.direction) {
            textureRegion.flip(textureRegion.isFlipX(), false);
            offset_x = -32;
        }
        else {
            textureRegion.flip(!textureRegion.isFlipX(), false);
            offset_x = -46;
        }
    }
}
