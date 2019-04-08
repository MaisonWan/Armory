package com.domker.armory.util;

import android.os.Build;
import android.os.StatFs;
import android.support.annotation.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipFile;

/**
 * Created by wanlipeng on 2019/4/8 4:59 PM
 */
public final class IoUtils {
    private IoUtils() {

    }

    public static void close(@Nullable Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭文件channel
     *
     * @param zipFile
     */
    public static void close(ZipFile zipFile) {
        try {
            if (zipFile != null) {
                zipFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拷贝文件到指定OutputStream，完成之后没有关闭输入流和输出流
     *
     * @param inputStream
     * @param outputStream
     * @param bufferSize
     * @return
     * @throws IOException
     */
    public static int copy(InputStream inputStream, OutputStream outputStream, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        int sum = 0;
        int length = inputStream.read(buffer);
        while (length != -1) {
            outputStream.write(buffer, 0, length);
            sum += length;
            length = inputStream.read(buffer);
        }
        outputStream.flush();
        return sum;
    }

    private static long sDefaultPageSize = 0;

    /**
     * 获取默认的pageSize
     *
     * @return 返回磁盘块
     */
    public static long getDefaultPageSize() {
        synchronized (IoUtils.class) {
            if (sDefaultPageSize == 0) {
                final StatFs fs = new StatFs("/data");
                if (Build.VERSION.SDK_INT >= 18) {
                    sDefaultPageSize = fs.getBlockSizeLong();
                } else {
                    sDefaultPageSize = fs.getBlockSize();
                }
            }
            return sDefaultPageSize;
        }
    }
}
