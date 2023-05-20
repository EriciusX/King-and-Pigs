package com.xch.pigsrpg.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.core.Logic;
import com.xch.pigsrpg.core.Pigsrpg;
import com.xch.pigsrpg.logic.BoxLogic;
import com.xch.pigsrpg.logic.HumanKingLogic;
import com.xch.pigsrpg.maps.Map;

public class BoxRenderer {
    private final Pigsrpg parent;
    private static TextureRegion idleTex, hitTex, p1Tex, p2Tex, p3Tex, p4Tex;
    private final TextureAtlas boxAtlas, heartAtlas, diamondAtlas;
    private final Animation bigdiamondAnimation, diamondHitAnimation;
    private final Animation bigheartAnimation, heartBHitAnimation;
    private TextureRegion currentFrame;
    private float stateTime;
    public BoxRenderer (Pigsrpg pigsrpg) {
        parent = pigsrpg;
        // box
        boxAtlas = parent.assMan.manager.get(parent.assMan.box);
        idleTex = boxAtlas.findRegion("idle");
        hitTex = boxAtlas.findRegion("hit");
        p1Tex = boxAtlas.findRegion("boxp1");
        p2Tex = boxAtlas.findRegion("boxp2");
        p3Tex = boxAtlas.findRegion("boxp3");
        p4Tex = boxAtlas.findRegion("boxp4");
        // heart
        heartAtlas = parent.assMan.manager.get(parent.assMan.heart);
        bigheartAnimation = new Animation(0.15f, heartAtlas.findRegions("bigheart"), Animation.PlayMode.LOOP);
        heartBHitAnimation = new Animation(0.1f, heartAtlas.findRegions("bighit"), Animation.PlayMode.NORMAL);
        // diamond
        diamondAtlas =  parent.assMan.manager.get(parent.assMan.diamond);
        bigdiamondAnimation = new Animation(0.15f, diamondAtlas.findRegions("bigdiamond"), Animation.PlayMode.LOOP);
        diamondHitAnimation = new Animation(0.12f, diamondAtlas.findRegions("bighit"), Animation.PlayMode.NORMAL);
        stateTime = 0;
    }

    public void drawBox (float delta, SpriteBatch sb, B2dModel model, Logic logic) {
        // box
        for (int i = 0; i < model.boxBody.size(); i ++) {
            sb.draw(idleTex, model.boxBody.get(i).getPosition().x - 11, model.boxBody.get(i).getPosition().y - 7);
        }
        for (int i = 0; i < model.boxDestroyBody.size(); i ++) {
            sb.draw(hitTex, model.boxDestroyBody.get(i).getPosition().x - 11, model.boxDestroyBody.get(i).getPosition().y - 7);
            parent.mainScreen.world.destroyBody(model.boxDestroyBody.get(i));
        }
        model.boxDestroyBody.clear();

        // dropped objects
        for (int i = 0; i < model.boxpieces.size(); i ++) {
            switch (i) {
                case 0:
                    sb.draw(p1Tex, model.boxpieces.get(i).getPosition().x - 5, model.boxpieces.get(i).getPosition().y - 5);
                    break;
                case 1:
                    sb.draw(p2Tex, model.boxpieces.get(i).getPosition().x - 5, model.boxpieces.get(i).getPosition().y - 5);
                    break;
                case 2:
                    sb.draw(p3Tex, model.boxpieces.get(i).getPosition().x - 5, model.boxpieces.get(i).getPosition().y - 5);
                    break;
                case 3:
                    sb.draw(p4Tex, model.boxpieces.get(i).getPosition().x - 5, model.boxpieces.get(i).getPosition().y - 5);
                    break;
            }
        }
        for (int i = 0; i < model.diamondBody.size(); i ++) {
            currentFrame = (TextureRegion) bigdiamondAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.diamondBody.get(i).getPosition().x-7, model.diamondBody.get(i).getPosition().y-7);
        }
        for (int i = 0; i < model.heartBody.size(); i ++) {
            currentFrame = (TextureRegion) bigheartAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.heartBody.get(i).getPosition().x-7, model.heartBody.get(i).getPosition().y-7);
        }

        // dropped objects picked
        for (int i = 0; i < model.diamondDestroyBody.size(); i ++) {
            currentFrame = (TextureRegion) diamondHitAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.diamondDestroyBody.get(i).getPosition().x-7, model.diamondDestroyBody.get(i).getPosition().y-7);
            if(diamondHitAnimation.isAnimationFinished(stateTime)) {
                if (HumanKingLogic.playerDiamond != 99) HumanKingLogic.playerDiamond += 1;
                logic.boxLogic.destrayDroppedObjects(model.diamondDestroyBody.get(i));
                model.diamondDestroyBody.remove(model.diamondDestroyBody.get(i));
                logic.boxLogic.playSound();
            }
        }
        for (int i = 0; i < model.heartDestroyBody.size(); i ++) {
            currentFrame = (TextureRegion) heartBHitAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, model.heartDestroyBody.get(i).getPosition().x - 7, model.heartDestroyBody.get(i).getPosition().y - 7);
            if (heartBHitAnimation.isAnimationFinished(stateTime)) {
                if (HumanKingLogic.playerHeart != 3) HumanKingLogic.playerHeart += 1;
                logic.boxLogic.destrayDroppedObjects(model.heartDestroyBody.get(i));
                model.heartDestroyBody.remove(model.heartDestroyBody.get(i));
                logic.boxLogic.playSound();
            }
        }
        stateTime += delta;
    }
}
