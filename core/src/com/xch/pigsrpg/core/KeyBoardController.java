package com.xch.pigsrpg.core;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class KeyBoardController implements InputProcessor {
    public boolean left, right, up, down, jump, attack, entry, escape;
    public boolean isMouse1Down, isMouse2Down,isMouse3Down;
    public boolean isDragged, isSpaceDown = false;
    public final Vector2 mouseLocation = new Vector2();
    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) // switch code base on the variable keycode
        {
            //上下左右
            case Keys.LEFT:  	// if keycode is the same as Keys.LEFT a.k.a 21
            case Keys.A:
                left = true;	// do this
                keyProcessed = true;// we have reacted to a keypress
                break;
            case Keys.RIGHT: 	// if keycode is the same as Keys.LEFT a.k.a 22
            case Keys.D:
                right = true;	// do this
                keyProcessed = true;// we have reacted to a keypress
                break;
            case Keys.UP: 		// if keycode is the same as Keys.LEFT a.k.a 19
            case Keys.W:
                up = true;		// do this
                keyProcessed = true;// we have reacted to a keypress
                break;
            case Keys.DOWN: 	// if keycode is the same as Keys.LEFT a.k.a 20
            case Keys.S:
                down = true;	// do this
                keyProcessed = true;// we have reacted to a keypress
                break;
            case Keys.SPACE:
                jump = true;
                keyProcessed = true;
                break;
            case Keys.J:
                attack = true;
                keyProcessed = true;
                break;
            case Keys.E:
                entry = true;
                keyProcessed = true;
                break;
            case Keys.ESCAPE:
                escape = true;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) // switch code base on the variable keycode
        {
            case Keys.LEFT:  	// if keycode is the same as Keys.LEFT a.k.a 21
            case Keys.A:
                left = false;	// do this
                keyProcessed = true;	// we have reacted to a keypress
                break;
            case Keys.RIGHT: 	// if keycode is the same as Keys.LEFT a.k.a 22
            case Keys.D:
                right = false;	// do this
                keyProcessed = true;	// we have reacted to a keypress
                break;
            case Keys.UP: 		// if keycode is the same as Keys.LEFT a.k.a 19
            case Keys.W:
                up = false;		// do this
                keyProcessed = true;	// we have reacted to a keypress
                break;
            case Keys.DOWN: 	// if keycode is the same as Keys.LEFT a.k.a 20
            case Keys.S:
                down = false;	// do this
                keyProcessed = true;	// we have reacted to a keypress
                break;
            case Keys.SPACE:
                jump = false;
                isSpaceDown = false;
                keyProcessed = true;
                break;
            case Keys.J:
                attack = false;
                keyProcessed = true;
                break;
            case Keys.E:
                entry = false;
                keyProcessed = true;
                break;
            case Keys.ESCAPE:
                escape = false;
                keyProcessed = true;
                break;
        }
        return keyProcessed;	//  return our peyProcessed flag
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == 0){
            isMouse1Down = true;
        }else if(button == 1){
            isMouse2Down = true;
        }else if(button == 2){
            isMouse3Down = true;
        }
        mouseLocation.set(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDragged = false;
        //System.out.println(button);
        if(button == 0){
            isMouse1Down = false;
        }else if(button == 1){
            isMouse2Down = false;
        }else if(button == 2){
            isMouse3Down = false;
        }
        mouseLocation.set(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        isDragged = true;
        mouseLocation.set(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseLocation.set(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
