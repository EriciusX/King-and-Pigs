package com.xch.pigsrpg.body;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.xch.pigsrpg.core.AssetManager;
import com.xch.pigsrpg.core.BodyFactory;
import com.xch.pigsrpg.logic.HumanKingLogic;
import com.xch.pigsrpg.maps.Map;

import java.util.ArrayList;
import java.util.List;

public class B2dModel {
    private OrthographicCamera camera;
    private final AssetManager assMan;
    public boolean isSwimming = false;
    public Body humanking, humankingSensor;
    public List<Body> barBody = new ArrayList<Body>();
    public List<Body> boxBody = new ArrayList<Body>();
    public List<Body> boxDestroyBody = new ArrayList<Body>();
    private final World world;
    private Map map;
    public BodyFactory bodyFactory;
    public B2dModel(OrthographicCamera cam, AssetManager assetManager, World wd, Map mp){
        assMan = assetManager;
        world = wd;
        map = mp;
        camera = cam;

        //world.setContactListener(new B2dContactListener(this));
        //createFloor();

        // get our body factory singleton and store it in bodyFactory
        bodyFactory = new BodyFactory(world);

        // add a player
        humanking = bodyFactory.makeBoxPolyBody((float) map.human.getProperties().get("x"), (float) map.human.getProperties().get("y")+14, 38, 28, BodyFactory.HUMAN, BodyType.DynamicBody, "king");
        humanking.setBullet(true);

        // add bars
        if (barBody != null) barBody.clear();
        for (int i = 0; i < map.barName.size(); i ++) {
            barBody.add(bodyFactory.makeBoxPolyBody((float) map.objects.get(map.barName.get(i)).getProperties().get("x")+16*Integer.parseInt(map.barName.get(i).substring(map.barName.get(i).indexOf("r")+1, map.barName.get(i).indexOf("x"))), (float) map.objects.get(map.barName.get(i)).getProperties().get("y")+5,
                    32*Integer.parseInt(map.barName.get(i).substring(map.barName.get(i).indexOf("r")+1, map.barName.get(i).indexOf("x"))), 3, BodyFactory.BAR, BodyType.StaticBody, "bar"));
        }

        // add box
        if (boxBody != null) boxBody.clear();
        for (int i = 0; i < map.boxName.size(); i ++) {
            boxBody.add(bodyFactory.makeBoxPolyBody((float) map.objects.get(map.boxName.get(i)).getProperties().get("x")+11, (float) map.objects.get(map.boxName.get(i)).getProperties().get("y")+7,
                    22, 14, BodyFactory.BOX, BodyType.StaticBody, "box"));
        }
    }

    public boolean pointIntersectsBody(Body body, Vector2 mouseLocation){
        Vector3 mousePos = new Vector3(mouseLocation,0); //convert mouseLocation to 3D position
        camera.unproject(mousePos); // convert from screen potition to world position
        if(body.getFixtureList().first().testPoint(mousePos.x, mousePos.y)){
            return true;
        }
        return false;
    }
}
