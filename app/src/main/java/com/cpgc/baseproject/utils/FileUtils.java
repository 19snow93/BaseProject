package com.cpgc.baseproject.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用于文件的一些读取工作
 * Created by jamin on 16-5-31.
 */
public class FileUtils {

    /**
     * 读取文件
     * @param path
     * @return
     */
    public static byte[] readFile(String path) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream((new File(path)));
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[4096];
            int len = 0;
            while ((len = inputStream.read(bytes)) > 0) {
                byteArrayOutputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            Log.d("FILE", path);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (byteArrayOutputStream == null) {
            return new byte[0];
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 保存文件
     *
     * @param path
     * @param content
     */
    public static void saveFile(String path, byte[] content) {
        if (content != null && content.length == 1) {
            throw new IllegalArgumentException("invalid image content");
        }
        File file = new File(path);
        if (file.getParentFile().exists() == false) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(content);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 获取文件的所在目录的路径,如果没有就创建该目录
     *
     * @param path
     * @return
     */
    public static String getFileMkDirPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        return path;
    }

    /**
     * 写入文件
     *
     * @param in
     * @param file
     */
    public static void writeFile(InputStream in, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (file != null && file.exists())
            file.delete();

        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[256];
        int len = -1;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
        out.close();
        in.close();
    }

    /**
     * 转换文件大小
     * @param filesize
     * @return
     */
    public static String getFileSizeStr(long filesize){
        String str = "0K";
        double sizeB = filesize;
        if(sizeB <= 0) {
            return str;
        }
        double sizeK = 1.0 * sizeB/1024;
        double sizeM = sizeK/1024;
        double sizeG = sizeM/1024;
        double sizeT = sizeG/1024;
        if (sizeT >= 1) {
            str = String.format("%.2f", sizeT) + "T";
        } else if (sizeG >= 1) {
            str = String.format("%.2f", sizeG) + "G";
        } else if (sizeM >= 1) {
            str = String.format("%.2f", sizeM) + "M";
        } else if (sizeK>=1) {
            str = String.format("%.2f", sizeK) + "K";
        } else  {
            str = String.format("%.2f", sizeB) + "B";
        }
        return str;
    }
}
