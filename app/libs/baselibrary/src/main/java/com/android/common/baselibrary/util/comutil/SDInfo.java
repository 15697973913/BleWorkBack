package com.android.common.baselibrary.util.comutil;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SDInfo {
    private static File mInternalFile = Environment.getDataDirectory();
    private static StatFs mInternalStatfs = new StatFs(mInternalFile.getPath());
    private static File mSdFile = Environment.getExternalStorageDirectory();
    private static StatFs mStatfs = new StatFs(mSdFile.getPath());

    public static long getAvailableInternalMemorySize() {
        return ((long) mInternalStatfs.getAvailableBlocks()) * ((long) mInternalStatfs.getBlockSize());
    }

    public static boolean hasEnoughAvailableSizeInternal(long size) {
        return (getAvailableInternalMemorySize() / 1024) / 1024 > size;
    }

    public static boolean hasEnoughAvailableSizeInternal() {
        return (getAvailableInternalMemorySize() / 1024) / 1024 > 10;
    }

    public static long getAvailaleSize() {
        return ((long) mStatfs.getBlockSize()) * ((long) mStatfs.getAvailableBlocks());
    }

    public static long getAllSize() {
        return (long) (mStatfs.getBlockSize() * mStatfs.getBlockCount());
    }

    public static boolean hasEnoughAvailableSize() {
        return (getAvailaleSize() / 1024) / 1024 > 10;
    }

    public static boolean hasDownloadSkinSize() {
        return (getAvailaleSize() / 1024) / 1024 > 10;
    }

    public static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean checkSdcard() {
        if (hasSDCard() && hasEnoughAvailableSize()) {
            return true;
        }
        return false;
    }

    public static FileOutputStream saveInMemory(Context context, String fileName) throws FileNotFoundException {
        return context.openFileOutput(fileName, 1);
    }
}
