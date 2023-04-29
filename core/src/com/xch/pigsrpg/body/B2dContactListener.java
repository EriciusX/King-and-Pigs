package com.xch.pigsrpg.body;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.xch.pigsrpg.core.BodyFactory;
import com.xch.pigsrpg.core.KeyBoardController;
import com.xch.pigsrpg.logic.HumanKingLogic;

import java.util.Objects;

public class B2dContactListener implements ContactListener {
    private HumanKingLogic humanKingLogic;
    public B2dContactListener(HumanKingLogic lgc){
        humanKingLogic = lgc;
    }

    @Override
    public void beginContact(Contact contact) {
//        System.out.println("Contact");
//        Fixture fa = contact.getFixtureA();
//        Fixture fb = contact.getFixtureB();
//
//        System.out.println(fa.getBody().getType()+" has hit "+ fb.getBody().getType());
//
//        if(fa.getBody().getType() == BodyDef.BodyType.StaticBody){
//            this.shootUpInAir(fa, fb);
//        }else if(fb.getBody().getType() == BodyDef.BodyType.StaticBody) {
//            this.shootUpInAir(fb, fa);
//        }
    }

    @Override
    public void endContact(Contact contact) {
//        System.out.println("Contact");
//        Fixture fa = contact.getFixtureA();
//        Fixture fb = contact.getFixtureB();
//        if(fa.getBody().getUserData() == "IAMTHESEA"){
//            parent.isSwimming = false;
//            return;
//        }else if(fb.getBody().getUserData() == "IAMTHESEA"){
//            parent.isSwimming = false;
//            return;
//        }
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

            if(player_y-14 < platform_y) {  //the player is below platform
                contact.setEnabled(false);
            } else {
                contact.setEnabled(true);
                humanKingLogic.attachBar = true;
            }
        }
    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
