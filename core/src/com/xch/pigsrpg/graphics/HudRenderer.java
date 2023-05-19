package com.xch.pigsrpg.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.core.Pigsrpg;
import com.xch.pigsrpg.logic.HumanKingLogic;

import java.util.ArrayList;
import java.util.List;

public class HudRenderer {
    private final Pigsrpg parent;
    private static TextureRegion livebarTex;
    private List<TextureRegion> numberTex = new ArrayList<TextureRegion>();
    private final Animation smallheartAnimation, heartSHitAnimation;
    private final Animation smalldiamondAnimation;
    private final TextureAtlas heartAtlas, diamondAtlas;
    private static TextureRegion currentFrame;
    private int playerheart_temp = 3;
    public HudRenderer (Pigsrpg pigsrpg) {
        parent = pigsrpg;

        // heart
        heartAtlas = parent.assMan.manager.get(parent.assMan.heart);
        livebarTex = heartAtlas.findRegion("livebar");
        smallheartAnimation = new Animation(0.15f, heartAtlas.findRegions("smallheart"), Animation.PlayMode.LOOP);
        heartSHitAnimation = new Animation(0.1f, heartAtlas.findRegions("smallhit"), Animation.PlayMode.NORMAL);

        // diamond
        diamondAtlas = parent.assMan.manager.get(parent.assMan.diamond);
        smalldiamondAnimation = new Animation(0.15f, diamondAtlas.findRegions("smalldiamond"), Animation.PlayMode.LOOP);
        for (int i = 0; i < 10; i ++) {
            numberTex.add(diamondAtlas.findRegion(String.format("%d", i)));
        }
    }

    public void drawHud (float stateTime, SpriteBatch sb, HumanKingLogic humanKingLogic, OrthographicCamera cam, B2dModel model) {
        // livebar
        float hud_x = cam.position.x - (cam.viewportWidth *parent.V_SCALE) / 2;
        float hud_y = cam.position.y + (cam.viewportHeight*parent.V_SCALE) / 3;
        sb.draw(livebarTex, hud_x, hud_y);
        switch (humanKingLogic.playerHeart) {
            case 3:
                currentFrame = (TextureRegion) smallheartAnimation.getKeyFrame(stateTime);
                sb.draw(currentFrame, hud_x + 37, hud_y + 10);
            case 2:
                if (playerheart_temp == 3 && playerheart_temp != humanKingLogic.playerHeart) {
                    currentFrame = (TextureRegion) heartSHitAnimation.getKeyFrame(stateTime);
                    sb.draw(currentFrame, hud_x + 37, hud_y + 10);
                    if (heartSHitAnimation.isAnimationFinished(stateTime)) playerheart_temp -= 1;
                }
                currentFrame = (TextureRegion) smallheartAnimation.getKeyFrame(stateTime);
                sb.draw(currentFrame, hud_x + 26, hud_y + 10);
            case 1:
                if (playerheart_temp == 2 && playerheart_temp != humanKingLogic.playerHeart) {
                    currentFrame = (TextureRegion) heartSHitAnimation.getKeyFrame(stateTime);
                    sb.draw(currentFrame, hud_x + 26, hud_y + 10);
                    if (heartSHitAnimation.isAnimationFinished(stateTime)) playerheart_temp -= 1;
                }
                currentFrame = (TextureRegion) smallheartAnimation.getKeyFrame(stateTime);
                sb.draw(currentFrame, hud_x + 15, hud_y + 10);
            default:
                if (playerheart_temp == 1 && playerheart_temp != humanKingLogic.playerHeart) {
                    currentFrame = (TextureRegion) heartSHitAnimation.getKeyFrame(stateTime);
                    sb.draw(currentFrame, hud_x + 15, hud_y + 10);
                    if (heartSHitAnimation.isAnimationFinished(stateTime)) playerheart_temp -= 1;
                }
        }
        // diamond
        currentFrame = (TextureRegion) smalldiamondAnimation.getKeyFrame(stateTime);
        sb.draw(currentFrame, hud_x+17, hud_y-5);
        sb.draw(numberTex.get(humanKingLogic.playerDiamond/10), hud_x+35, hud_y-1);
        sb.draw(numberTex.get(humanKingLogic.playerDiamond%10), hud_x+40, hud_y-1);
    }

    public void reload() {
        playerheart_temp = 3;
    }
}
