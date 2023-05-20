package com.xch.pigsrpg.logic;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.xch.pigsrpg.body.B2dModel;
import com.xch.pigsrpg.core.BodyFactory;
import com.xch.pigsrpg.core.KeyBoardController;
import com.xch.pigsrpg.core.Pigsrpg;
import com.xch.pigsrpg.maps.Map;
import com.xch.pigsrpg.ui.MainScreen;

import static com.badlogic.gdx.math.MathUtils.random;

public class CannonLogic {
    private KeyBoardController controller;
    private MainScreen mainScreen;
    private Map map;
    private B2dModel model;
    public int pigMatchState;
    public boolean shooting = false;
    public boolean shootReady = false;
    public boolean cannonBallBomb = false;
    public CannonLogic (KeyBoardController cont, Map mp, MainScreen ms) {
        map = mp;
        mainScreen = ms;
        model = mainScreen.model;
        controller = cont;
        pigMatchState = 0;
    }

    public void logic (float delta) {
        // pig
        for (int i = 0; i < model.pigMatchBody.size(); i ++) {
            if (JudeFire(model.pigMatchBody.get(i)) && pigMatchState == 1 && shootReady) {
                pigMatchState = 2;
            }
        }
        // bomb
        for (int i = 0; i < model.cannonBallBody.size(); i ++) {
            Body tempBody = model.cannonBallBody.get(i);
            if (!(boolean)map.Walllayer.getCell((int) ((tempBody.getPosition().x) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((tempBody.getPosition().y - 7) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")
               || !((boolean) (map.Walllayer.getCell((int) ((tempBody.getPosition().x+7) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((tempBody.getPosition().y - 7) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))
               || !((boolean) (map.Walllayer.getCell((int) ((tempBody.getPosition().x-7) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((tempBody.getPosition().y - 7) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))) {
                if (tempBody.getLinearVelocity().x != 0 && tempBody.getLinearVelocity().y != 0) {
                    Body boomSensor = model.bodyFactory.makeBoxPolyBody(tempBody.getPosition().x, tempBody.getPosition().y, 52, 56, BodyFactory.SENSOR, BodyDef.BodyType.DynamicBody, "boomSensor");
                    model.bodyFactory.makeAllFixturesSensors(boomSensor);
                    boomSensor.setGravityScale(0.5f);
                    model.boomBody.add(boomSensor);
                }
                tempBody.setLinearVelocity(0, 0);
                tempBody.setGravityScale(0f);
                cannonBallBomb = true;
            }
        }
    }

    public void createCannonBall () {
        for (int i = 0; i < model.cannonBody.size(); i++) {

            Body body = model.bodyFactory.makeBoxPolyBody(model.cannonBody.get(i).getPosition().x + 3, model.cannonBody.get(i).getPosition().y-5, 14, 14, BodyFactory.CANNONBALL, BodyDef.BodyType.DynamicBody, "cannonball");
            model.bodyFactory.makeAllFixturesSensors(body);
            model.cannonBallBody.add(body);
            body.setGravityScale(0.05f);
            shootImpulse(body, i);
        }
    }

    public void destrayBody(Body body) {
        mainScreen.world.destroyBody(body);
    }

    public void reload () {
        pigMatchState = 0;
    }

    private void shootImpulse (Body body, int i) {
        float speed = 16.6667f;
        Vector2 impulse = new Vector2();
        if (map.cannonName.get(i).contains("r")) {
            impulse.set(100 + random.nextInt(10)*10, 0);
            impulse.scl(body.getMass() * speed);
            body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
        }
        if (map.cannonName.get(i).contains("l")) {
            impulse.set(-100 - random.nextInt(10)*10, 0);
            impulse.scl(body.getMass() * speed);
            body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
        }
    }

    private boolean JudeFire (Body body) {
        float x_dis = Math.abs(model.humanking.getPosition().x - body.getPosition().x);
        float y_dis = Math.abs(model.humanking.getPosition().y - body.getPosition().y);
        return x_dis <= 300 && y_dis <= 100;
    }
}
