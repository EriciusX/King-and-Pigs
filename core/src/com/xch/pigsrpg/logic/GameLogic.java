package com.xch.pigsrpg.logic;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.core.KeyBoardController;
import com.xch.pigsrpg.core.Pigsrpg;
import com.xch.pigsrpg.graphics.HumanKingRenderer;
import com.xch.pigsrpg.ui.MainScreen;

public class GameLogic {
    private Pigsrpg parent;
    private KeyBoardController controller;
    private B2dModel model;
    private MainScreen mainScreen;
    private float delay = 0;
    public static boolean gameFinish = false;
    public GameLogic(KeyBoardController cont, Pigsrpg pigsrpg, B2dModel md, MainScreen ms) {
        model = md;
        mainScreen = ms;
        parent = pigsrpg;
        controller = cont;
    }

    private void gameLogic(float delta) {
        delay += delta;
        if (controller.escape && mainScreen.gameState == 1 && delay >= delta * 20) {
            HumanKingRenderer.idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
            HumanKingRenderer.runAnimation.setPlayMode(Animation.PlayMode.LOOP);
            mainScreen.gameState = 0;
            delay = 0;
        }
        else if (controller.escape && mainScreen.gameState == 0 && delay >= delta * 20){
            HumanKingRenderer.idleAnimation.setPlayMode(Animation.PlayMode.NORMAL);
            HumanKingRenderer.runAnimation.setPlayMode(Animation.PlayMode.NORMAL);
            mainScreen.gameState = 1;
            delay = 0;
        }
        else if (gameFinish){
            if (parent.levelMap == 1) {
                mainScreen.gameState = 3;
            }
            else {
                parent.levelMap += 1;
                mainScreen.gameState = 2;
            }
        }
    }

    public void logicStep(float delta){
        gameLogic(delta);
    }

    public void reload() {
        gameFinish = false;
        delay = 0;
    }
}
