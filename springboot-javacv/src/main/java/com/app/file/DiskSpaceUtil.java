package com.app.file;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiskSpaceUtil {

    public static List<Map<String, Object>> getDiskSpace() {
        List<Map<String, Object>> result = new ArrayList<>();
        File[] roots = File.listRoots();
        for (File file : roots) {
            String path = file.getAbsolutePath();// 盘符路径
            String totalSize = formatSize(file.getTotalSpace());// 此盘符总大小
            String freeSize = formatSize(file.getFreeSpace());// 此盘符剩余空间

            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("path", path);
            temp.put("totalSize", totalSize);
            temp.put("freeSize", freeSize);
            result.add(temp);
        }
        return result;
    }

    private static String formatSize(long longSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String size = null;
        if (longSize < 1024) {
            size = df.format((double) longSize) + "B";
        } else if (longSize < (1024 * 1024)) {
            size = df.format((double) longSize / 1024) + "K";
        } else if (longSize < (1024 * 1024 * 1024)) {
            size = df.format((double) longSize / 1048576) + "M";
        } else {
            size = df.format((double) longSize / 1073741824) + "G";
        }
        return size;
    }
}
