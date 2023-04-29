package com.xch.pigsrpg.core;

import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.logic.GameLogic;
import com.xch.pigsrpg.logic.HumanKingLogic;
import com.xch.pigsrpg.maps.Map;
import com.xch.pigsrpg.ui.MainScreen;
import jdk.tools.jmod.Main;

public class Logic {
    public HumanKingLogic humanKingLogic;
    private MainScreen mainScreen;
    private Map map;
    public Logic (KeyBoardController cont, B2dModel model, Map mp, MainScreen ms) {
        map = mp;
        mainScreen = ms;
        humanKingLogic = new HumanKingLogic(cont, model, map, mainScreen);
    }

    public void AllLogic (float delta) {
        humanKingLogic.Logic(delta);
    }

    public void reload () {
        humanKingLogic.reload();
    }
}
