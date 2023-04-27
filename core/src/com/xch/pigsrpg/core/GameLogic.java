package com.xch.pigsrpg.core;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.xch.pigsrpg.ui.MainScreen;
import com.xch.pigsrpg.graphics.HumanKingRenderer;

public class GameLogic {
    private Pigsrpg parent;
    private KeyBoardController controller;
    private B2dModel model;
    private float delay = 0;
    public boolean gameFinish = false;
    public GameLogic(KeyBoardController cont, Pigsrpg pa, B2dModel md) {
        parent = pa;
        model = md;
        controller = cont;
    }

    private void gameLogic(float delta) {
        delay += delta;
        if (controller.escape && MainScreen.gameState == 1 && delay >= delta * 20) {
            HumanKingRenderer.idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
            HumanKingRenderer.runAnimation.setPlayMode(Animation.PlayMode.LOOP);
            MainScreen.gameState = 0;
            delay = 0;
        }
        else if (controller.escape && MainScreen.gameState == 0 && delay >= delta * 20){
            HumanKingRenderer.idleAnimation.setPlayMode(Animation.PlayMode.NORMAL);
            HumanKingRenderer.runAnimation.setPlayMode(Animation.PlayMode.NORMAL);
            MainScreen.gameState = 1;
            delay = 0;
        }
        else if (gameFinish){
            if (parent.levelMap == 1) {
                MainScreen.gameState = 3;
            }
            else {
                parent.levelMap += 1;
                MainScreen.gameState = 2;
            }
        }
    }

    public void logicStep(float delta){
        gameLogic(delta);
    }
}
