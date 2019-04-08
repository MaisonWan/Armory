package com.domker.armory.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by wanlipeng on 2019/4/8 5:08 PM
 */
public final class FileUtils {
    private FileUtils() {
    }

    /**
     * 读取文件
     *
     * @param path 文件路径
     * @return
     * @throws IOException
     */
    public static String readFile(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        IoUtils.close(bufferedReader);
        return sb.toString();
    }
}
