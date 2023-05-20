package com.xch.pigsrpg.logic;

import com.badlogic.gdx.audio.Sound;
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

public class BoxLogic {
    private KeyBoardController controller;
    private MainScreen mainScreen;
    private Map map;
    private B2dModel model;
    private Sound pick;
    public BoxLogic (KeyBoardController cont, Map mp, MainScreen ms) {
        map = mp;
        mainScreen = ms;
        model = mainScreen.model;
        controller = cont;
        pick = Pigsrpg.assMan.manager.get(Pigsrpg.assMan.pick);
    }

    public void logic(float delta) {
        // random create dropped objects
        for (int i = 0; i < model.boxDestroyBody.size(); i ++) {
            for (int j = 0; j < random.nextInt(2)+1; j++) {
                Body body = model.bodyFactory.makeBoxPolyBody(model.boxDestroyBody.get(i).getPosition().x+7, model.boxDestroyBody.get(i).getPosition().y+7, 14, 14, BodyFactory.DROPPED, BodyDef.BodyType.DynamicBody, "diamond");
                model.bodyFactory.makeAllFixturesSensors(body);
                model.diamondBody.add(body);
            }
            for (int j = 0; j < random.nextInt(2); j++) {
                Body body = model.bodyFactory.makeBoxPolyBody(model.boxDestroyBody.get(i).getPosition().x+7, model.boxDestroyBody.get(i).getPosition().y+7, 14, 14, BodyFactory.DROPPED, BodyDef.BodyType.DynamicBody, "heart");
                model.bodyFactory.makeAllFixturesSensors(body);
                model.heartBody.add(body);
            }
            for (int j = 0; j < 4; j++) {
                Body body = model.bodyFactory.makeBoxPolyBody(model.boxDestroyBody.get(i).getPosition().x+5, model.boxDestroyBody.get(i).getPosition().y+5, 10, 10, BodyFactory.DROPPED, BodyDef.BodyType.DynamicBody, "boxpieces");
                model.bodyFactory.makeAllFixturesSensors(body);
                model.boxpieces.add(body);
            }
        }
        // box piece
        for (int i = 0; i < model.boxpieces.size(); i++) {
            Body tempBody = model.boxpieces.get(i);
            if (tempBody.getPosition().x > model.humanking.getPosition().x && tempBody.getLinearVelocity().x == 0) {
                tempBody.setGravityScale(0.1f);
                Vector2 impulse = new Vector2();
                impulse.set(random.nextInt(10)*10000+1000, random.nextInt(100)*1000+5000);
                impulse.scl(tempBody.getMass());
                tempBody.applyLinearImpulse(impulse, tempBody.getWorldCenter(), true);
            }
            if (tempBody.getPosition().x <= model.humanking.getPosition().x && tempBody.getLinearVelocity().x == 0) {
                tempBody.setGravityScale(0.2f);
                Vector2 impulse = new Vector2();
                impulse.set(-(random.nextInt(100)*5000+1000), random.nextInt(100)*1000+5000);
                impulse.scl(tempBody.getMass());
                tempBody.applyLinearImpulse(impulse, tempBody.getWorldCenter(), true);
            }

            if (!(boolean)map.Walllayer.getCell((int) ((tempBody.getPosition().x) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((tempBody.getPosition().y - 5) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")) {
                tempBody.setGravityScale(0f);
                tempBody.setLinearVelocity(0f, 0f);
            } else if (!((boolean) (map.Walllayer.getCell((int) ((tempBody.getPosition().x+5) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((tempBody.getPosition().y - 5) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))
                    || !((boolean) (map.Walllayer.getCell((int) ((tempBody.getPosition().x-5) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((tempBody.getPosition().y - 5) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))) {
                tempBody.setLinearVelocity(0f, tempBody.getLinearVelocity().y);
            }
        }

        // dropped Logic
        // diamond
        for (int i = 0; i < model.diamondBody.size(); i++) {
            Body tempBody = model.diamondBody.get(i);
            if (tempBody.getPosition().x > model.humanking.getPosition().x && tempBody.getLinearVelocity().x == 0) {
                tempBody.setGravityScale(0.2f);
                Vector2 impulse = new Vector2();
                impulse.set(random.nextInt(500)*1000+10000, random.nextInt(100)*1000+3000);
                impulse.scl(tempBody.getMass());
                tempBody.applyLinearImpulse(impulse, tempBody.getWorldCenter(), true);
            }
            if (tempBody.getPosition().x <= model.humanking.getPosition().x && tempBody.getLinearVelocity().x == 0) {
                tempBody.setGravityScale(0.2f);
                Vector2 impulse = new Vector2();
                impulse.set(-(random.nextInt(500)*1000+10000), random.nextInt(100)*1000+3000);
                impulse.scl(tempBody.getMass());
                tempBody.applyLinearImpulse(impulse, tempBody.getWorldCenter(), true);
            }

            if (!(boolean)map.Walllayer.getCell((int) ((tempBody.getPosition().x) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((tempBody.getPosition().y - 7) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")) {
                tempBody.setGravityScale(0f);
                tempBody.setLinearVelocity(0f, 0f);
            } else if (!((boolean) (map.Walllayer.getCell((int) ((tempBody.getPosition().x+7) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((tempBody.getPosition().y - 7) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))
                    || !((boolean) (map.Walllayer.getCell((int) ((tempBody.getPosition().x-7) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((tempBody.getPosition().y - 7) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))) {
                tempBody.setLinearVelocity(0f, tempBody.getLinearVelocity().y);
            }
        }
        // heart
        for (int i = 0; i < model.heartBody.size(); i++) {
            Body tempBody = model.heartBody.get(i);
            if (tempBody.getPosition().x > model.humanking.getPosition().x && tempBody.getLinearVelocity().x == 0) {
                tempBody.setGravityScale(0.2f);
                Vector2 impulse = new Vector2();
                impulse.set(random.nextInt(500)*1000+5000, random.nextInt(100)*1000+3000);
                impulse.scl(tempBody.getMass());
                tempBody.applyLinearImpulse(impulse, tempBody.getWorldCenter(), true);
            }
            if (tempBody.getPosition().x <= model.humanking.getPosition().x && tempBody.getLinearVelocity().x == 0) {
                tempBody.setGravityScale(0.2f);
                Vector2 impulse = new Vector2();
                impulse.set(-(random.nextInt(500)*1000+5000), random.nextInt(100)*1000+3000);
                impulse.scl(tempBody.getMass());
                tempBody.applyLinearImpulse(impulse, tempBody.getWorldCenter(), true);
            }

            if (!(boolean)map.Walllayer.getCell((int) ((tempBody.getPosition().x) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((tempBody.getPosition().y - 7) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")) {
                tempBody.setGravityScale(0f);
                tempBody.setLinearVelocity(0f, 0f);
            } else if (!((boolean) (map.Walllayer.getCell((int) ((tempBody.getPosition().x+7) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((tempBody.getPosition().y - 7) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))
                    || !((boolean) (map.Walllayer.getCell((int) ((tempBody.getPosition().x-7) / 32) + (int) map.Walllayer.getProperties().get("width_min"), (int) ((tempBody.getPosition().y - 7) / 32) + (int) map.Walllayer.getProperties().get("higth_min")).getTile().getProperties().get("possible")))) {
                tempBody.setLinearVelocity(0f, tempBody.getLinearVelocity().y);
            }
        }
    }

    public void playSound(){
        pick.play();
    }

    public void destrayDroppedObjects(Body body) {
        mainScreen.world.destroyBody(body);
    }
}
