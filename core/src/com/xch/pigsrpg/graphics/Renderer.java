package com.xch.pigsrpg.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private HudRenderer hudRenderer;
    private SpriteBatch sb;
    private Logic logic;
    private Map map;
    private MainScreen mainScreen;
    private OrthographicCamera cam;
    private B2dModel model;
    public Renderer (Pigsrpg pigsrpg, SpriteBatch spriteBatch, Logic lgc, Map mp, B2dModel md, MainScreen ms, OrthographicCamera orthographicCamera) {
        logic = lgc;
        map = mp;
        mainScreen = ms;
        model = md;
        sb = spriteBatch;
        cam = orthographicCamera;
        parent = pigsrpg;
        doorRenderer = new DoorRenderer(parent);
        humanKingRenderer = new HumanKingRenderer(parent);
        hudRenderer = new HudRenderer(parent);
    }

    public void render (float stateTime) {
        doorRenderer.drawDoor(stateTime, sb, logic.humanKingLogic, map.door1, map.door2);
        humanKingRenderer.drawHuman(stateTime, sb, logic.humanKingLogic, model ,mainScreen.gameState);
        hudRenderer.drawhud(stateTime, sb, logic.humanKingLogic, cam, model);
    }

    public void reload () {
        doorRenderer.reload();
        hudRenderer.reload();
    }
}
