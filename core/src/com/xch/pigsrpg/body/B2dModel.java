package com.xch.pigsrpg.body;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.xch.pigsrpg.core.AssetManager;
import com.xch.pigsrpg.core.BodyFactory;
import com.xch.pigsrpg.maps.Map;

import java.util.ArrayList;
import java.util.List;

public class B2dModel {
    private OrthographicCamera camera;
    private final AssetManager assMan;
    public boolean isSwimming = false;
    public Body humanking;
    public List<Body> barBody = new ArrayList<Body>();
    private final World world;
    private Map map;
    public static final short PLATFORM_BIT = 2;
    public static final short PLAYER_BIT = 3;
    public B2dModel(OrthographicCamera cam, AssetManager assetManager, World wd, Map mp){
        assMan = assetManager;
        world = wd;
        map = mp;
        camera = cam;

        //world.setContactListener(new B2dContactListener(this));
        //createFloor();

        // get our body factory singleton and store it in bodyFactory
        BodyFactory bodyFactory = new BodyFactory(world);

        // add a player
        humanking = bodyFactory.makeBoxPolyBody((float) map.human.getProperties().get("x"), (float) map.human.getProperties().get("y")+14, 38, 28, BodyFactory.HUMAN, BodyType.DynamicBody, "king");
        humanking.setBullet(true);

        // add bars
        if (barBody != null) barBody.clear();
        for (int i = 0; i < map.barName.size(); i ++) {
            barBody.add(bodyFactory.makeBoxPolyBody((float) map.objects.get(map.barName.get(i)).getProperties().get("x")+16*Integer.parseInt(map.barName.get(i).substring(map.barName.get(i).indexOf("r")+1, map.barName.get(i).indexOf("x"))), (float) map.objects.get(map.barName.get(i)).getProperties().get("y")+5,
                    32*Integer.parseInt(map.barName.get(i).substring(map.barName.get(i).indexOf("r")+1, map.barName.get(i).indexOf("x"))), 3, BodyFactory.Bar, BodyType.StaticBody, "bar"));
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
