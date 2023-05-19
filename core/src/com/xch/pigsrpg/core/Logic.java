package com.xch.pigsrpg.core;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.logic.BoxLogic;
import com.xch.pigsrpg.logic.GameLogic;
import com.xch.pigsrpg.logic.HumanKingLogic;
import com.xch.pigsrpg.maps.Map;
import com.xch.pigsrpg.ui.MainScreen;
import jdk.tools.jmod.Main;

import javax.swing.*;

public class Logic {
    public HumanKingLogic humanKingLogic;
    private MainScreen mainScreen;
    public BoxLogic boxLogic;
    private Map map;
    public Logic (KeyBoardController cont, Map mp, MainScreen ms) {
        map = mp;
        mainScreen = ms;
        boxLogic = new BoxLogic(cont, map, mainScreen);
        humanKingLogic = new HumanKingLogic(cont, map, mainScreen);
    }

    public void allLogic (float delta) {
        humanKingLogic.Logic(delta);
        boxLogic.logic(delta);
    }

    public void reload () {
        humanKingLogic.reload();
    }
}
