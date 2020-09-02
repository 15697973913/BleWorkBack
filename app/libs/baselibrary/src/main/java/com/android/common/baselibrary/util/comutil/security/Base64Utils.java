package com.android.common.baselibrary.util.comutil.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Base64Utils {
    private static final int CACHE_SIZE = 1024;

    public static byte[] decode(String base64) throws Exception {
        return Base64.decode(base64);
    }

    public static String encode(byte[] bytes) throws Exception {
        return new String(Base64.encode(bytes));
    }

    public static String encodeFile(String filePath) throws Exception {
        return encode(fileToByte(filePath));
    }

    public static void decodeToFile(String filePath, String base64) throws Exception {
        byteArrayToFile(decode(base64), filePath);
    }

    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (!file.exists()) {
            return data;
        }
        FileInputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
        byte[] cache = new byte[1024];
        while (true) {
            int nRead = in.read(cache);
            if (nRead != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            } else {
                out.close();
                in.close();
                return out.toByteArray();
            }
        }
    }

    public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);
        byte[] cache = new byte[1024];
        while (true) {
            int nRead = in.read(cache);
            if (nRead != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            } else {
                out.close();
                in.close();
                return;
            }
        }
    }
}
