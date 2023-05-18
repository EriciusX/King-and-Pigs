package com.xch.pigsrpg.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class BodyFactory {
    private World world;
    private final float DEGTORAD = 0.0174533f;
    public BodyFactory(World wd){
        world = wd;
    }

    public static final int HUMAN = 0;
    public static final int BAR = 1;
    public static final int BOX = 2;
    public static final int SENSOR = 3;
    static public FixtureDef makeFixture(int material, Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        switch (material) {
            case 0:
                fixtureDef.density = 0f;
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0f;
                break;
            case 1:
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0f;
                fixtureDef.restitution = 0f;
                break;
            case 2:
                fixtureDef.density = 0f;
                fixtureDef.friction = 0f;
                fixtureDef.restitution = 0f;
                break;
            case 3:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.9f;
                fixtureDef.restitution = 0.01f;
            default:
                fixtureDef.density = 7f;
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0.3f;
        }
        return fixtureDef;
    }
    public Body makeCirclePolyBody(float posx, float posy, float radius, int material, BodyType bodyType, boolean fixedRotation){
        // create a definition
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        boxBodyDef.fixedRotation = fixedRotation;

        //create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius /2);
        boxBody.createFixture(makeFixture(material,circleShape));
        circleShape.dispose();
        return boxBody;
    }

    public Body makeCirclePolyBody(float posx, float posy, float radius, int material, BodyType bodyType){
        return makeCirclePolyBody( posx,  posy,  radius,  material,  bodyType,  false);
    }

    public Body makeCirclePolyBody(float posx, float posy, float radius, int material){
        return makeCirclePolyBody( posx,  posy,  radius,  material,  BodyType.DynamicBody,  false);
    }

    //矩形体
    public Body makeBoxPolyBody(float posx, float posy, float width, float height,int material, BodyType bodyType){
        return makeBoxPolyBody(posx, posy, width, height, material, bodyType, "default", false);
    }
    public Body makeBoxPolyBody(float posx, float posy, float width, float height,int material, BodyType bodyType, String userData){
        return makeBoxPolyBody(posx, posy, width, height, material, bodyType, userData, false);
    }

    public Body makeBoxPolyBody(float posx, float posy, float width, float height, int material, BodyType bodyType, String userData, boolean fixedRotation){
        // create a definition
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        boxBodyDef.fixedRotation = fixedRotation;

        //create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width/2, height/2);
        boxBody.getFixtureList();
        boxBody.createFixture(makeFixture(material, poly));
        boxBody.setUserData(userData);
        poly.dispose();

        return boxBody;
    }
    //多边形形体
    public Body makePolygonShapeBody(Vector2[] vertices, float posx, float posy, int material, BodyType bodyType){
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        Body boxBody = world.createBody(boxBodyDef);

        PolygonShape polygon = new PolygonShape();
        polygon.set(vertices);
        boxBody.createFixture(makeFixture(material,polygon));
        polygon.dispose();

        return boxBody;
    }
    //锥形体
    public void makeConeSensor(Body body, float size){

        FixtureDef fixtureDef = new FixtureDef();
        //fixtureDef.isSensor = true; // will add in future

        PolygonShape polygon = new PolygonShape();

        float radius = size;
        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(0,0);
        for (int i = 2; i < 6; i++) {
            float angle = (float) (i  / 6.0 * 145 * DEGTORAD); // convert degrees to radians
            vertices[i-1] = new Vector2( radius * ((float)Math.cos(angle)), radius * ((float)Math.sin(angle)));
        }
        polygon.set(vertices);
        fixtureDef.shape = polygon;
        body.createFixture(fixtureDef);
        polygon.dispose();
    }

    public void makeSensor(Body body, Body sensor) {
        for(Fixture fix :sensor.getFixtureList()){
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.isSensor = true;
            fixtureDef.shape = fix.getShape();
            body.createFixture(fixtureDef);
            world.destroyBody(sensor);
        }
    }
    // Turn to Sensor
    public void makeAllFixturesSensors(Body body){
        for(Fixture fix :body.getFixtureList()){
            fix.setSensor(true);
        }
    }
}
