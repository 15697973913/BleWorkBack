package com.zj.zhijue.util.thirdutil;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class Installation {
    private static final String INSTALLATION = "FANLIINSTALLATION";
    private static String sID = null;

    public static synchronized String id(Context context) {
        String str;
        synchronized (Installation.class) {
            if (sID == null) {
                File installation = new File(context.getFilesDir(), INSTALLATION);
                try {
                    if (!installation.exists()) {
                        writeInstallationFile(installation);
                    }
                    sID = readInstallationFile(installation);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            str = sID;
        }
        return str;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[((int) f.length())];
        f.readFully(bytes);
        f.close();
        return new String(bytes, "UTF-8");
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        out.write(UUID.randomUUID().toString().getBytes("UTF-8"));
        out.close();
    }
}
