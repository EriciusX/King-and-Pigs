package com.xch.pigsrpg.core;

import com.badlogic.gdx.physics.box2d.Body;
import com.xch.pigsrpg.logic.*;
import com.xch.pigsrpg.maps.Map;
import com.xch.pigsrpg.ui.MainScreen;

public class Logic {
    public HumanKingLogic humanKingLogic;
    private MainScreen mainScreen;
    public BoxLogic boxLogic;
    public CannonLogic cannonLogic;
    public PigLogic pigLogic;
    private Map map;
    public Logic (KeyBoardController cont, Map mp, MainScreen ms) {
        map = mp;
        mainScreen = ms;
        boxLogic = new BoxLogic(cont, map, mainScreen);
        cannonLogic = new CannonLogic(cont, map, mainScreen);
        humanKingLogic = new HumanKingLogic(cont, map, mainScreen);
        pigLogic = new PigLogic(cont, map, mainScreen);
    }

    public void allLogic (float delta) {
        humanKingLogic.logic(delta);
        cannonLogic.logic(delta);
        boxLogic.logic(delta);
    }

    public void destrayObjects(Body body) {
        mainScreen.world.destroyBody(body);
    }

    public void reload () {
        humanKingLogic.reload();
        cannonLogic.reload();
    }
}
