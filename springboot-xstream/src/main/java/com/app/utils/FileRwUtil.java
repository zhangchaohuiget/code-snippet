package com.app.utils;

import java.io.*;

/**
 * 文件读写工具类
 *
 * @author zch
 * @date 2019年10月23日 下午2:03:21
 */
public class FileRwUtil {
    private static File createFile;

    /**
     * 获取文件内容
     *
     * @param file 文件
     * @return 内容
     */
    public static String getContent(File file, String characterSet) {
        BufferedReader br = null;
        try {
            if (characterSet == null || characterSet == "") {
                characterSet = "UTF-8";
            }
            StringBuffer buffer = new StringBuffer("");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), characterSet));
            String line = "";
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            if (br != null) {
                br.close();
            }
            br = null;
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            br = null;
            return null;
        }
    }

    /**
     * 将内容写人文件中
     *
     * @param content 写入内容
     * @param path    文件路径（如：F:/a/b/test.txt）
     */
    public static boolean write(String content, String path) {
        // 检测文件夹是否存在，不存在则创建文件夹和文件
        FileWriter writer = null;
        try {
            File createFile = createFile(path);
            if (createFile == null) {
                return false;
            }
            writer = new FileWriter(path);
            writer.write(content);
            writer.flush();
            writer.close();
            writer = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            writer = null;
            return false;
        }
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return 文件路径（如：F:/a/b/test.txt）
     */
    public static File createFile(String path) {
        try {
            // 创建文件夹
            if (path.contains("/")) {
                String[] split = path.split("/");
                String fileName = split[split.length - 1];
                String dirPath = path.replace(fileName, "");
                File dir = new File(dirPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}