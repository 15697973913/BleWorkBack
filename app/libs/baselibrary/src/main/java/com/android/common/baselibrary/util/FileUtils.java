package com.android.common.baselibrary.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;

public class FileUtils {
    private static String separator = File.separator;
    public static final long ONE_KB = 1024;
    public static final long ONE_MB = ONE_KB * ONE_KB;
    public static final long ONE_GB = ONE_KB * ONE_MB;

    public static boolean copyApkFromAssets(Context context, String fileName, String path) {
        try {
            InputStream is = context.getAssets().open(fileName);
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            while (true) {
                int i = is.read(temp);
                if (i > 0) {
                    fos.write(temp, 0, i);
                } else {
                    fos.close();
                    is.close();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static byte[] readFileContentOfAssets(Context context, String fileName) {
        //arraycopy(Object src,  int  srcPos, Object dest, int destPos, int length);
        InputStream is = null;
        try {
            is = context.getAssets().open(fileName);
            int fileSize = is.available();
            byte[] totalDataArray = new byte[fileSize];

            byte[] temp = new byte[1024];
            int i = -1;
            int count = 0;
            while ((i = is.read(temp)) > 0) {
                System.arraycopy(temp, 0, totalDataArray, count, i);
                count += i;
            }
            return totalDataArray;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCloseable(is);
        }
        return null;
    }

    public static boolean copyDirectory(String srcPath, String destDir) {
        int i = 0;
        System.out.println("复制文件夹开始!");
        boolean flag = false;
        File srcFile = new File(srcPath);
        if (srcFile.exists()) {
            String destPath = destDir + separator + getDirName(srcPath);
            if (destPath.equals(srcPath)) {
                System.out.println("目标文件夹与源文件夹重复");
                return false;
            }
            File destDirFile = new File(destPath);
            if (destDirFile.exists()) {
                System.out.println("目标位置已有同名文件夹!");
                return false;
            }
            destDirFile.mkdirs();
            File[] fileList = srcFile.listFiles();
            if (fileList.length != 0) {
                int length = fileList.length;
                while (i < length) {
                    File temp = fileList[i];
                    if (temp.isFile()) {
                        flag = copyFile(temp.getAbsolutePath(), destPath);
                    } else if (temp.isDirectory()) {
                        flag = copyDirectory(temp.getAbsolutePath(), destPath);
                    }
                    if (!flag) {
                        break;
                    }
                    i++;
                }
            } else {
                flag = true;
            }
            if (flag) {
                System.out.println("复制文件夹成功!");
            }
            return flag;
        }
        System.out.println("源文件夹不存在");
        return false;
    }

    public static boolean copyFile(String srcPath, String destDir) {
        boolean flag = false;
        if (new File(srcPath).exists()) {
            String destPath = destDir + srcPath.substring(srcPath.lastIndexOf(separator));
            if (destPath.equals(srcPath)) {
                System.out.println("源文件路径和目标文件路径重复!");
                return false;
            }
            File destFile = new File(destPath);
            if (destFile.exists() && destFile.isFile()) {
                System.out.println("目标目录下已有同名文件!");
                return false;
            }
            new File(destDir).mkdirs();
            try {
                FileInputStream fis = new FileInputStream(srcPath);
                FileOutputStream fos = new FileOutputStream(destFile);
                byte[] buf = new byte[1024];
                while (true) {
                    int c = fis.read(buf);
                    if (c == -1) {
                        break;
                    }
                    fos.write(buf, 0, c);
                }
                fis.close();
                fos.close();
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (flag) {
                System.out.println("复制文件成功!");
            }
            return flag;
        }
        System.out.println("源文件不存在");
        return false;
    }

    public static String getDirName(String dir) {
        if (dir.endsWith("/")) {
            dir = dir.substring(0, dir.lastIndexOf("/"));
        }
        return dir.substring(dir.lastIndexOf("/") + 1);
    }

    public static void deleteAllFiles(String dir) {
        File file = new File(dir);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    f.delete();
                }
            }
        }
    }

    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists()) {
            return;
        }
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (File deleteAllFilesOfDir : files) {
            deleteAllFilesOfDir(deleteAllFilesOfDir);
        }
        path.delete();
    }

    public static void clearDir(File dirFile, int days) {
        long sepTime = (long) ((((days * 24) * 60) * 60) * 1000);
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (File f : files) {
                if (days > 0) {
                    if (System.currentTimeMillis() - ((long) ((((days * 24) * 60) * 60) * 1000)) > f.lastModified()) {
                        f.delete();
                    }
                } else if (f.isDirectory()) {
                    clearDir(f, days);
                    f.delete();
                } else {
                    f.delete();
                }
            }
        }
    }

    public static void write(Context context, String fileName, String content) {
        if (content == null) {
            content = "";
        }
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String read(Context context, String fileName) {
        try {
            return readInStream(context.openFileInput(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String readInStream(FileInputStream inStream) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            while (true) {
                int length = inStream.read(buffer);
                if (length != -1) {
                    outStream.write(buffer, 0, length);
                } else {
                    outStream.close();
                    inStream.close();
                    return outStream.toString();
                }
            }
        } catch (IOException e) {
            Log.i("FileTest", e.getMessage());
        }
        return null;
    }

    public static File createFile(String folderPath, String fileName) {
        File destDir = new File(folderPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return new File(folderPath, fileName + fileName);
    }

    public static boolean writeFile(byte[] buffer, String folder, String fileName) {
        boolean writeSucc = false;
        String folderPath = "";
        if (Environment.getExternalStorageState().equals("mounted")) {
            folderPath = Environment.getExternalStorageDirectory() + File.separator + folder + File.separator;
        } else {
            writeSucc = false;
        }
        File fileDir = new File(folderPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(folderPath + fileName));
            out.write(buffer);
            writeSucc = true;
        } catch (Exception e4) {
            e4.printStackTrace();
        } finally {
            closeQuietly(out);
        }
        return writeSucc;
    }

    public static boolean writeFile(File file, byte[] data) {
        File fileDir = new File(file.getParent());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        boolean success = false;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(data);
            success = true;
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
            closeQuietly(out);
        }
        return success;
    }

    public static String readTextFile(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (true) {
                String strLine = br.readLine();
                if (strLine == null) {
                    break;
                }
                result.append(strLine + System.lineSeparator());
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String getFileName(String filePath) {
        if (CommonUtils.isEmpty(filePath)) {
            return "";
        }
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    public static String getFileNameNoFormat(String filePath) {
        if (CommonUtils.isEmpty(filePath)) {
            return "";
        }
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.lastIndexOf(46));
    }

    public static String getFileFormat(String fileName) {
        if (CommonUtils.isEmpty(fileName)) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(46) + 1);
    }

    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            return 0;
        }
        return file.length();
    }

    public static String getFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("##.##");
        float temp = ((float) size) / 1024.0f;
        if (temp >= 1024.0f) {
            return df.format((double) (temp / 1024.0f)) + "M";
        }
        return df.format((double) temp) + "K";
    }

    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            return df.format((double) fileS) + "B";
        }
        if (fileS < 1048576) {
            return df.format(((double) fileS) / 1024.0d) + "KB";
        }
        if (fileS < ONE_GB) {
            return df.format(((double) fileS) / 1048576.0d) + "MB";
        }
        return df.format(((double) fileS) / 1.073741824E9d) + "G";
    }

    public static long getDirSize(File dir) {
        long j = 0;
        if (dir != null && dir.isDirectory()) {
            j = 0;
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    j += file.length();
                } else if (file.isDirectory()) {
                    j = (j + file.length()) + getDirSize(file);
                }
            }
        }
        return j;
    }

    public long getFileList(File dir) {
        File[] files = dir.listFiles();
        long count = (long) files.length;
        for (File file : files) {
            if (file.isDirectory()) {
                count = (count + getFileList(file)) - 1;
            }
        }
        return count;
    }

    public static byte[] toBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (true) {
            int ch = in.read();
            if (ch != -1) {
                out.write(ch);
            } else {
                byte[] buffer = out.toByteArray();
                out.close();
                return buffer;
            }
        }
    }

    public static boolean deleteDirectory(String fileName) {
        SecurityManager checker = new SecurityManager();
        if (fileName.equals("")) {
            return false;
        }
        File newPath = new File(Environment.getExternalStorageDirectory().toString() + fileName);
        checker.checkDelete(newPath.toString());
        if (!newPath.isDirectory()) {
            return false;
        }
        String[] listfile = newPath.list();
        int i = 0;
        while (i < listfile.length) {
            try {
                new File(newPath.toString() + "/" + listfile[i].toString()).delete();
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        newPath.delete();
        return true;
    }

    public static boolean deleteFile(String fileName) {
        SecurityManager checker = new SecurityManager();
        if (fileName.equals("")) {
            return false;
        }
        File newPath = new File(Environment.getExternalStorageDirectory().toString() + fileName);
        checker.checkDelete(newPath.toString());
        if (!newPath.isFile()) {
            return false;
        }
        try {
            newPath.delete();
            return true;
        } catch (Exception se) {
            se.printStackTrace();
            return false;
        }
    }

    public static String getFileMd5(String path) throws Exception {
        FileInputStream fis = new FileInputStream(new File(path));
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buffer = new byte[1024];
        while (true) {
            int length = fis.read(buffer, 0, 1024);
            if (length == -1) {
                return new BigInteger(1, md.digest()).toString(16);
            }
            md.update(buffer, 0, length);
        }
    }

    public static boolean exist(String path) {
        return new File(path).exists();
    }

    public static void deleteFiles(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File file1 : files) {
                    deleteFiles(file1);
                }
            } else {
                file.delete();
            }
        }
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    public static boolean checkFileExists(String name) {
        if (name.equals("")) {
            return false;
        }
        return new File(Environment.getExternalStorageDirectory().toString() + name).exists();
    }

    public static long getFreeDiskSpace() {
        long freeSpace = 0;
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return -1;
        }
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            freeSpace = (((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize())) / 1024;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return freeSpace;
    }

    public static boolean createDirectory(String directoryName) {
        if (directoryName.equals("")) {
            return false;
        }
        boolean mkdir = new File(Environment.getExternalStorageDirectory().toString() + directoryName).mkdir();
        return true;
    }

    public static boolean checkSaveLocationExists() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }

    public static void closeCloseable(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
