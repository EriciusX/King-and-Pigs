package com.xch.pigsrpg.maps;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.xch.pigsrpg.core.Pigsrpg;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private final Pigsrpg parent;
    private final SpriteBatch sb;
    private TiledMap map;
    public MapObject human, door1, door2;
    public MapRenderer mapRenderer;
    public TiledMapTileLayer Walllayer;;
    public MapObjects objects;
    public List<String> barName = new ArrayList<String>();
    public List<String> boxName = new ArrayList<String>();
    public List<String> cannonName = new ArrayList<String>();
    public List<String> pigMatchName = new ArrayList<String>();
    private List<String> objectName = new ArrayList<String>();
    public Map (Pigsrpg pigsrpg, SpriteBatch spriteBatch) {
        parent = pigsrpg;
        sb = spriteBatch;

        // Laod Map Asset
        Pigsrpg.assMan.queueAddMap((String.format("map/%s", parent.maps.get(parent.levelMap))));
        Pigsrpg.assMan.manager.finishLoading();

        // Get Map Objects
        map = Pigsrpg.assMan.manager.get((String.format("map/%s", parent.maps.get(parent.levelMap))));
        mapRenderer = new OrthogonalTiledMapRenderer(map, sb);
        MapLayer layer = map.getLayers().get("对象层 1");
        objects = layer.getObjects();

        // Load Map ObjectName
        getLayerObjectName();
        separateObjectName(barName, "bar");
        separateObjectName(boxName, "box");
        separateObjectName(cannonName, "cannon");
        separateObjectName(pigMatchName, "pigMatch");
        System.out.print(boxName);

        Walllayer = (TiledMapTileLayer) map.getLayers().get("map");
        human = objects.get("humanking");
        door1 = objects.get("door1");
        door2 = objects.get("door2");
    }

    public void mapRender (OrthographicCamera cam) {
        mapRenderer.setView(cam);
        mapRenderer.render();
    }

    private void separateObjectName (List<String> NameList, String object) {
        if (NameList != null) NameList.clear();

        for (int i = 0; i < objectName.size()-1; i++) {
            if (objectName.get(i).contains(object))
                NameList.add(objectName.get(i));
        }
    }

    private void getLayerObjectName () {
        int count = objects.getCount();
        if (objectName != null) objectName.clear();
        for (int i = 0; i < count-1; i ++) {
            objectName.add(objects.get(i).getName());
        }
    }
}
