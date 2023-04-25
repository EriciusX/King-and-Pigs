package com.xch.pigsrpg.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTool {

    public static List getData(String path, List data) {

        File f = new File(path);
        if (f.isDirectory()) {
            File[] fs = f.listFiles();
            for (int i = 0; i < fs.length; i++) {
                data = getData(fs[i].getPath(), data);
            }
        } else if (f.getName().endsWith(".tmx")) {
            data.add(f.getName());
        }
        return data;
    }
}
