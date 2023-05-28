package com.xch.pigsrpg.logic;

import com.badlogic.gdx.physics.box2d.Body;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.core.KeyBoardController;
import com.xch.pigsrpg.maps.Map;
import com.xch.pigsrpg.ui.MainScreen;

public class PigLogic {
    private MainScreen mainScreen;
    private B2dModel model;
    private Map map;
    private KeyBoardController controller;
    public PigLogic (KeyBoardController cont, Map mp, MainScreen ms) {
        map = mp;
        mainScreen = ms;
        controller = cont;
        model = mainScreen.model;
    }
}
