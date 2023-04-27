package com.xch.pigsrpg.graphics;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapObject;
import com.xch.pigsrpg.core.B2dModel;
import com.xch.pigsrpg.core.Pigsrpg;

public class DoorRenderer {
    public static Animation doorCloseAnimation, doorOpenAnimation;
    private final TextureAtlas doorAtlas;
    private static TextureRegion doorClosing, doorOpening;
    private static TextureRegion currentFrame;
    public static int doorState = 0;
    public DoorRenderer (Pigsrpg pigsrpg) {
        Pigsrpg parent = pigsrpg;
        doorAtlas = parent.assMan.manager.get("door/door.atlas");
        doorClosing = doorAtlas.findRegion("closing");
        doorOpening = doorAtlas.findRegion("opening");
        doorCloseAnimation = new Animation(0.5f, doorAtlas.findRegions("close"), Animation.PlayMode.NORMAL);
        doorOpenAnimation = new Animation(0.15f, doorAtlas.findRegions("open"), Animation.PlayMode.NORMAL);
    }

    public static void drawDoor (float stateTime, SpriteBatch sb, B2dModel model, MapObject door1, MapObject door2) {
        if (model.human_dout) {
            if (doorState == 0) {
                currentFrame = (TextureRegion) doorOpenAnimation.getKeyFrame(stateTime);
                sb.draw(currentFrame, (float) door1.getProperties().get("x") - 14, (float) door1.getProperties().get("y"));
                sb.draw(doorClosing, (float) door2.getProperties().get("x") - 14, (float) door2.getProperties().get("y"));
                if (doorOpenAnimation.isAnimationFinished(stateTime)) {
                    doorState = 1;
                }
            }
            else {
                sb.draw(doorOpening, (float)door1.getProperties().get("x")-14, (float)door1.getProperties().get("y"));
                sb.draw(doorClosing, (float)door2.getProperties().get("x")-14, (float)door2.getProperties().get("y"));
            }
        }
        else if (model.human_din) {
            if (doorState == 3) {
                currentFrame = (TextureRegion) doorOpenAnimation.getKeyFrame(stateTime);
                sb.draw(currentFrame, (float)door2.getProperties().get("x")-14, (float)door2.getProperties().get("y"));
                sb.draw(doorClosing, (float)door1.getProperties().get("x")-14, (float)door1.getProperties().get("y"));
                if (doorOpenAnimation.isAnimationFinished(stateTime)) {
                    doorState = 4;
                }
            }
            else {
                sb.draw(doorClosing, (float)door1.getProperties().get("x")-14, (float)door1.getProperties().get("y"));
                sb.draw(doorOpening, (float)door2.getProperties().get("x")-14, (float)door2.getProperties().get("y"));
            }
        }
        else if (doorState == 2) {
            currentFrame = (TextureRegion) doorCloseAnimation.getKeyFrame(stateTime);
            sb.draw(currentFrame, (float)door1.getProperties().get("x")-14, (float)door1.getProperties().get("y"));
            sb.draw(doorClosing, (float)door2.getProperties().get("x")-14, (float)door2.getProperties().get("y"));
            if (doorCloseAnimation.isAnimationFinished(stateTime)) {
                doorState = 3;
            }
        }
        else if (doorState == 5) {
            sb.draw(doorClosing, (float)door1.getProperties().get("x")-14, (float)door1.getProperties().get("y"));
            sb.draw(doorOpening, (float)door2.getProperties().get("x")-14, (float)door2.getProperties().get("y"));
        }
        else if (doorState == 3) {
            sb.draw(doorClosing, (float)door1.getProperties().get("x")-14, (float)door1.getProperties().get("y"));
            sb.draw(doorClosing, (float)door2.getProperties().get("x")-14, (float)door2.getProperties().get("y"));
        }
    }

}
