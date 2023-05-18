package com.xch.pigsrpg.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.core.Pigsrpg;
import com.xch.pigsrpg.maps.Map;

public class BoxRenderer {
    private Pigsrpg parent;
    private static TextureRegion idleTex, hitTex, p1Tex, p2Tex, p3Tex, p4Tex;
    private final TextureAtlas boxAtlas;
    public BoxRenderer (Pigsrpg pigsrpg) {
        parent = pigsrpg;

        // heart
        boxAtlas = parent.assMan.manager.get(parent.assMan.box);
        idleTex = boxAtlas.findRegion("idle");
        hitTex = boxAtlas.findRegion("hit");
        p1Tex = boxAtlas.findRegion("boxp1");
        p2Tex = boxAtlas.findRegion("boxp2");
        p3Tex = boxAtlas.findRegion("boxp3");
        p4Tex = boxAtlas.findRegion("boxp4");
    }

    public void drawBox (float stateTime, SpriteBatch sb, B2dModel model) {
        for (int i = 0; i < model.boxBody.size(); i ++) {
            sb.draw(idleTex, (float)model.boxBody.get(i).getPosition().x - 11, (float)model.boxBody.get(i).getPosition().y - 7);
        }
        for (int i = 0; i < model.boxDestroyBody.size(); i ++) {
            sb.draw(hitTex, (float)model.boxDestroyBody.get(i).getPosition().x - 11, (float)model.boxDestroyBody.get(i).getPosition().y - 7);
            parent.mainScreen.world.destroyBody(model.boxDestroyBody.get(i));
        }
        model.boxDestroyBody.clear();
    }
}
