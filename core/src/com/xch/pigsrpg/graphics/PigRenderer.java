package com.xch.pigsrpg.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.core.Logic;
import com.xch.pigsrpg.core.Pigsrpg;
import com.xch.pigsrpg.maps.Map;

public class PigRenderer {
    private Pigsrpg parent;
    private Renderer renderer;
    private Map map;
    private final TextureAtlas pigAtlas, pigBombAtlas, pigThrowAtlas;
    private final TextureRegion jumpTex, jumpingTex, downTex;
    private final Animation idleNAnimation, runNAnimation, attackNAnimation, deadAnimation, hitAnimation;
    private final Animation idleTAnimation, runTAnimation, throwBoxAnimation, pickBoxAnimation;
    private final Animation idleBAnimation, runBAnimation, throwBombAnimation, pickBombAnimation;
    private TextureRegion currentFrame;
    private float hitStateTime;
    public PigRenderer (Pigsrpg pigsrpg, Renderer render, Map mp) {
        parent = pigsrpg;
        renderer = render;
        map = mp;
        // pig
        pigAtlas = parent.assMan.manager.get(parent.assMan.pig);
        jumpTex = pigAtlas.findRegion("jump");
        jumpingTex = pigAtlas.findRegion("fall");
        downTex = pigAtlas.findRegion("ground");
        idleNAnimation = new Animation(0.12f, pigAtlas.findRegions("idle"), Animation.PlayMode.LOOP);
        runNAnimation = new Animation(0.1f, pigAtlas.findRegions("run"), Animation.PlayMode.LOOP);
        attackNAnimation = new Animation(0.05f, pigAtlas.findRegions("attack"), Animation.PlayMode.NORMAL);

        deadAnimation = new Animation(0.5f, pigAtlas.findRegions("dead"), Animation.PlayMode.NORMAL);
        hitAnimation = new Animation(0.3f, pigAtlas.findRegions("hit"), Animation.PlayMode.NORMAL);
        // pig with Throw
        pigThrowAtlas = parent.assMan.manager.get(parent.assMan.pigThrowBox);
        idleTAnimation = new Animation(0.12f, pigThrowAtlas.findRegions("idle"), Animation.PlayMode.LOOP);
        runTAnimation = new Animation(0.1f, pigThrowAtlas.findRegions("run"), Animation.PlayMode.LOOP);
        throwBoxAnimation = new Animation(0.05f, pigThrowAtlas.findRegions("throw"), Animation.PlayMode.NORMAL);
        pickBoxAnimation = new Animation(0.05f, pigThrowAtlas.findRegions("pick"), Animation.PlayMode.NORMAL);
        // pig with Bomb
        pigBombAtlas = parent.assMan.manager.get(parent.assMan.pigThrowBomb);
        idleBAnimation = new Animation(0.12f, pigBombAtlas.findRegions("idle"), Animation.PlayMode.LOOP);
        runBAnimation = new Animation(0.1f, pigBombAtlas.findRegions("run"), Animation.PlayMode.LOOP);
        throwBombAnimation = new Animation(0.05f, pigBombAtlas.findRegions("throw"), Animation.PlayMode.NORMAL);
        pickBombAnimation = new Animation(0.05f, pigBombAtlas.findRegions("pickbomb"), Animation.PlayMode.NORMAL);
        hitStateTime = 0;
    }

    public void drawPig (float delta, SpriteBatch sb, B2dModel model, Logic logic) {
        // pig with match hit
        for (int i = 0; i < model.pigMatchDeadBody.size(); i ++) {
            currentFrame = (TextureRegion) hitAnimation.getKeyFrame(hitStateTime);
            judgDirection(map.pigMatchName.get(0), currentFrame);
            sb.draw(currentFrame, model.pigMatchDeadBody.get(i).getPosition().x-13, model.pigMatchDeadBody.get(i).getPosition().y-9);
            if (hitAnimation.isAnimationFinished(hitStateTime)) {
                if (i == model.pigMatchDeadBody.size()) hitStateTime = 0;
                logic.destrayObjects(model.pigMatchDeadBody.get(i));
                model.pigMatchDeadBody.remove(model.pigMatchDeadBody.get(i));
            }
            hitStateTime += delta;
        }
    }

    private void judgDirection (String name, TextureRegion textureRegion) {
        if (name.contains("r")) textureRegion.flip(!textureRegion.isFlipX(), false);
        if (name.contains("l")) textureRegion.flip(textureRegion.isFlipX(), false);
    }
}
