package com.xch.pigsrpg.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xch.pigsrpg.body.B2dContactListener;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.core.Logic;
import com.xch.pigsrpg.core.Pigsrpg;
import com.xch.pigsrpg.logic.HumanKingLogic;
import com.xch.pigsrpg.maps.Map;
import com.xch.pigsrpg.ui.MainScreen;
import jdk.tools.jmod.Main;

public class Renderer {
    private Pigsrpg parent;
    private DoorRenderer doorRenderer;
    private HumanKingRenderer humanKingRenderer;
    private SpriteBatch sb;
    private Logic logic;
    private Map map;
    private MainScreen mainScreen;
    private B2dModel model;
    public Renderer (Pigsrpg pigsrpg, SpriteBatch spriteBatch, Logic lgc, Map mp, B2dModel md, MainScreen ms) {
        logic = lgc;
        map = mp;
        mainScreen = ms;
        model = md;
        sb = spriteBatch;
        parent = pigsrpg;
        doorRenderer = new DoorRenderer(parent);
        humanKingRenderer = new HumanKingRenderer(parent);
    }

    public void render (float stateTime) {
        doorRenderer.drawDoor(stateTime, sb, logic.humanKingLogic, map.door1, map.door2);
        humanKingRenderer.drawHuman(stateTime, sb, logic.humanKingLogic, model ,mainScreen.gameState);
    }

    public void reload () {
        doorRenderer.reload();
    }
}
