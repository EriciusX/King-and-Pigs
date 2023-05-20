package com.xch.pigsrpg.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.core.Logic;
import com.xch.pigsrpg.core.Pigsrpg;
import com.xch.pigsrpg.maps.Map;

import java.util.ArrayList;
import java.util.List;

public class CannonRenderer {
    private Pigsrpg parent;
    private Renderer renderer;
    private Map map;
    private final TextureAtlas cannonAtlas, pigMatchAtlas, bombAtlas;
    private final TextureRegion idleTex, cannonballTex;
    private final Animation cannonShooting, cannonBallBombAnimation;
    private final Animation lightMatchAnimation, lightCannonAnimation, matchingAnimation;
    private TextureRegion currentFrame;
    private List<Float> stateTime = new ArrayList<Float>();
    public CannonRenderer (Pigsrpg pigsrpg, Renderer render, Map mp) {
        parent = pigsrpg;
        renderer = render;
        map = mp;
        // cannon
        cannonAtlas = parent.assMan.manager.get(parent.assMan.cannon);
        idleTex = cannonAtlas.findRegion("idle");
        cannonballTex = cannonAtlas.findRegion("cannonball");
        cannonShooting = new Animation(0.1f, cannonAtlas.findRegions("shoot"), Animation.PlayMode.NORMAL);
        stateTime.add(0f);
        // bomb
        bombAtlas = parent.assMan.manager.get(parent.assMan.bomb);
        cannonBallBombAnimation = new Animation(0.1f, bombAtlas.findRegions("boom"), Animation.PlayMode.NORMAL);
        stateTime.add(0f);
        // pig with match
        pigMatchAtlas = parent.assMan.manager.get(parent.assMan.pigMatch);
        lightMatchAnimation = new Animation(0.2f, pigMatchAtlas.findRegions("lightmatch"), Animation.PlayMode.NORMAL);
        stateTime.add(0f);
        matchingAnimation = new Animation(0.2f, pigMatchAtlas.findRegions("matchon"), Animation.PlayMode.LOOP);
        stateTime.add(0f);
        lightCannonAnimation = new Animation(0.2f, pigMatchAtlas.findRegions("lightcannon"), Animation.PlayMode.NORMAL);
        stateTime.add(0f);
    }

    public void drawCannon (float delta, SpriteBatch sb, B2dModel model, Logic logic) {
        // cannon
        // idle
        for (int i = 0; i < model.cannonBody.size(); i ++) {
            if (logic.cannonLogic.shooting) {
                currentFrame = (TextureRegion) cannonShooting.getKeyFrame(stateTime.get(0));
                judgDirection(map.cannonName.get(i), currentFrame);
                sb.draw(currentFrame, model.cannonBody.get(i).getPosition().x - 22, model.cannonBody.get(i).getPosition().y - 18);
                updateStateTime(0, delta);
                if (i == model.pigMatchBody.size() - 1 && cannonShooting.isAnimationFinished(stateTime.get(0))) {
                    logic.cannonLogic.createCannonBall();
                    logic.cannonLogic.shooting = false;
                    stateTime.set(0, 0f);
                }
            } else {
                judgDirection(map.cannonName.get(i), idleTex);
                sb.draw(idleTex, model.cannonBody.get(i).getPosition().x - 22, model.cannonBody.get(i).getPosition().y - 18);
            }
        }
        // cannonball
        for (int i = 0; i < model.cannonBallBody.size(); i ++) {
            if (model.cannonBallBody.get(i).getLinearVelocity().x == 0 && model.cannonBallBody.get(i).getLinearVelocity().y == 0) {
                currentFrame = (TextureRegion) cannonBallBombAnimation.getKeyFrame(stateTime.get(1));
                sb.draw(currentFrame, model.cannonBallBody.get(i).getPosition().x - 26, model.cannonBallBody.get(i).getPosition().y - 28);
                updateStateTime(1, delta);
                if (cannonBallBombAnimation.isAnimationFinished(stateTime.get(1))) {
                    if (i == model.cannonBallBody.size() - 1) {
                        logic.cannonLogic.cannonBallBomb = false;
                        logic.cannonLogic.shootReady = true;
                        logic.cannonLogic.pigMatchState = 0;
                    }
                    stateTime.set(1, 0f);
                    logic.cannonLogic.destrayBody(model.boomBody.get(0));
                    model.boomBody.remove(model.boomBody.get(0));
                    logic.cannonLogic.destrayBody(model.cannonBallBody.get(i));
                    model.cannonBallBody.remove(model.cannonBallBody.get(i));
                }
            }
            else {
                sb.draw(cannonballTex, model.cannonBallBody.get(i).getPosition().x - 7, model.cannonBallBody.get(i).getPosition().y - 7);
            }
        }

        // pig with match
        for (int i = 0; i < model.pigMatchBody.size(); i ++) {
            if (logic.cannonLogic.pigMatchState == 0) {
                currentFrame = (TextureRegion) lightMatchAnimation.getKeyFrame(stateTime.get(2));
                judgDirection(map.pigMatchName.get(i), currentFrame);
                sb.draw(currentFrame, model.pigMatchBody.get(i).getPosition().x - 13, model.pigMatchBody.get(i).getPosition().y - 9);
                updateStateTime(2, delta);
                if (lightMatchAnimation.isAnimationFinished(stateTime.get(2))) {
                    stateTime.set(2, 0f);
                    logic.cannonLogic.pigMatchState += 1;
                }
            }
            if (logic.cannonLogic.pigMatchState == 1) {
                currentFrame = (TextureRegion) matchingAnimation.getKeyFrame(stateTime.get(3));
                judgDirection(map.pigMatchName.get(i), currentFrame);
                sb.draw(currentFrame, model.pigMatchBody.get(i).getPosition().x - 13, model.pigMatchBody.get(i).getPosition().y - 9);
                updateStateTime(3, delta);
                if (matchingAnimation.isAnimationFinished(stateTime.get(3))) {
                    stateTime.set(3, 0f);
                    logic.cannonLogic.shootReady = true;
                }
            }
            if (logic.cannonLogic.pigMatchState == 2 && logic.cannonLogic.shootReady) {
                currentFrame = (TextureRegion) lightCannonAnimation.getKeyFrame(stateTime.get(4));
                judgDirection(map.pigMatchName.get(i), currentFrame);
                sb.draw(currentFrame, model.pigMatchBody.get(i).getPosition().x - 13, model.pigMatchBody.get(i).getPosition().y - 9);
                updateStateTime(4, delta);
                if (lightCannonAnimation.isAnimationFinished(stateTime.get(4))) {
                    stateTime.set(4, 0f);
                    logic.cannonLogic.shooting = true;
                    logic.cannonLogic.pigMatchState = 0;
                    logic.cannonLogic.shootReady = false;
                }
            }
        }
    }

    private void updateStateTime (int index, float delta) {
        float x = stateTime.get(index);
        x += delta;
        stateTime.set(index , x);
    }

    private void judgDirection (String name, TextureRegion textureRegion) {
        if (name.contains("r")) textureRegion.flip(!textureRegion.isFlipX(), false);
        if (name.contains("l")) textureRegion.flip(textureRegion.isFlipX(), false);
    }
}
