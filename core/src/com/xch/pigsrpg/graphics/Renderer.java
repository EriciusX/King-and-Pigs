package com.xch.pigsrpg.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.core.Logic;
import com.xch.pigsrpg.core.Pigsrpg;
import com.xch.pigsrpg.maps.Map;
import com.xch.pigsrpg.ui.MainScreen;

import java.security.PrivateKey;

public class Renderer {
    private Pigsrpg parent;
    private DoorRenderer doorRenderer;
    private HumanKingRenderer humanKingRenderer;
    private BoxRenderer boxRenderer;
    private HudRenderer hudRenderer;
    private CannonRenderer cannonRenderer;
    private PigRenderer pigrenderer;
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
        boxRenderer = new BoxRenderer(parent);
        cannonRenderer = new CannonRenderer(parent, this, map);
        pigrenderer = new PigRenderer(parent, this, map);
    }

    public void render (float delta) {
        boxRenderer.drawBox(delta, sb, model, logic);
        cannonRenderer.drawCannon(delta, sb, model, logic);
        doorRenderer.drawDoor(delta, sb, logic.humanKingLogic, map.door1, map.door2);
        humanKingRenderer.drawHuman(delta, sb, logic.humanKingLogic, model ,mainScreen.gameState);
        pigrenderer.drawPig(delta, sb, model, logic);
        hudRenderer.drawHud(delta, sb, logic.humanKingLogic, cam, model);
    }

    public void draw (TextureRegion textureRegion, float x, float y, boolean flipX) {
        Texture texture = textureRegion.getTexture();
        sb.draw(textureRegion.getTexture(), x, y, texture.getWidth(), texture.getHeight(), textureRegion.getRegionX(), textureRegion.getRegionY(), texture.getWidth(), texture.getHeight(), flipX, false);
    }

    public void reload () {
        doorRenderer.reload();
        hudRenderer.reload();
    }
}
