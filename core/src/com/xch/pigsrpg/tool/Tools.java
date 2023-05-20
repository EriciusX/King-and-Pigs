package com.xch.pigsrpg.tool;

public class Tools {
    public double Distance (int x1,int x2,int y1,int y2) {
        double distance = Math.sqrt(Math.pow(Math.abs(x1-x2), 2)+Math.pow(Math.abs(y1-y2), 2));
        return distance;
    }
}
