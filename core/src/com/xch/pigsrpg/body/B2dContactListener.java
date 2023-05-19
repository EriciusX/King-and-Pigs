package com.xch.pigsrpg.body;

import com.badlogic.gdx.physics.box2d.*;
import com.xch.pigsrpg.logic.HumanKingLogic;

public class B2dContactListener implements ContactListener {
    private HumanKingLogic humanKingLogic;
    private B2dModel model;
    private World world;
    public B2dContactListener(HumanKingLogic lgc, B2dModel md, World wd){
        humanKingLogic = lgc;
        model = md;
        world = wd;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        System.out.print(fixtureA.getBody().getUserData() + " ");
        System.out.print(fixtureB.getBody().getUserData() + "\n");

        if(fixtureA.getBody().getUserData() == "humankingsensor" && fixtureB.getBody().getUserData() == "box" ||
           fixtureA.getBody().getUserData() == "box" && fixtureB.getBody().getUserData() == "humankingsensor") {
            if (humanKingLogic.human_attack){
                if (fixtureA.getBody().getUserData() == "box") {
                    model.boxBody.remove(fixtureA.getBody());
                    model.boxDestroyBody.add(fixtureA.getBody());
                } else {
                    model.boxBody.remove(fixtureB.getBody());
                    model.boxDestroyBody.add(fixtureB.getBody());
                }
            }
        }

        if(fixtureA.getBody().getUserData() == "king" && fixtureB.getBody().getUserData() == "diamond" ||
           fixtureA.getBody().getUserData() == "diamond" && fixtureB.getBody().getUserData() == "king") {
            if (fixtureA.getBody().getUserData() == "diamond") {
                model.diamondBody.remove(fixtureA.getBody());
                model.diamondDestroyBody.add(fixtureA.getBody());
            } else {
                model.diamondBody.remove(fixtureB.getBody());
                model.diamondDestroyBody.add(fixtureB.getBody());
            }
        }

        if(fixtureA.getBody().getUserData() == "king" && fixtureB.getBody().getUserData() == "heart" ||
           fixtureA.getBody().getUserData() == "heart" && fixtureB.getBody().getUserData() == "king") {
            if (fixtureA.getBody().getUserData() == "heart") {
                model.heartBody.remove(fixtureA.getBody());
                model.heartDestroyBody.add(fixtureA.getBody());
            } else {
                model.heartBody.remove(fixtureB.getBody());
                model.heartDestroyBody.add(fixtureB.getBody());
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //THIS SHOULD ALLOW PLAYER TO PASS THROUGH PLATFORMS AND COLLIDE ON THE WAY DOWN
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        float platform_y = fixtureA.getBody().getPosition().y;
        float player_y = fixtureB.getBody().getPosition().y;

        if(fixtureA.getBody().getUserData() == "king" && fixtureB.getBody().getUserData() == "bar" ||
           fixtureA.getBody().getUserData() == "bar" && fixtureB.getBody().getUserData() == "king") {

            if (fixtureA.getBody().getType() == BodyDef.BodyType.StaticBody) {
                platform_y = fixtureA.getBody().getPosition().y;
                player_y = fixtureB.getBody().getPosition().y;
            } else if(fixtureA.getBody().getType() == BodyDef.BodyType.DynamicBody) {
                player_y = fixtureA.getBody().getPosition().y;
                platform_y = fixtureB.getBody().getPosition().y;
            }

            if(player_y - 14f < platform_y || humanKingLogic.down) {
                contact.setEnabled(false);
            } else {
                if (humanKingLogic.human_fall) humanKingLogic.attachBar = true;
                contact.setEnabled(true);
            }
        }

        if(fixtureA.getBody().getUserData() == "king" && fixtureB.getBody().getUserData() == "box" ||
           fixtureA.getBody().getUserData() == "box" && fixtureB.getBody().getUserData() == "king") {
            contact.setEnabled(false);
        }

        if(fixtureA.getBody().getUserData() == "king" && fixtureB.getBody().getUserData() == "humankingsensor" ||
           fixtureA.getBody().getUserData() == "humankingsensor" && fixtureB.getBody().getUserData() == "king") {
            contact.setEnabled(false);
        }

    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
