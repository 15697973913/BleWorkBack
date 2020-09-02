package com.android.common.baselibrary.util.comutil.log;

import com.litesuits.common.io.FileUtils;
import com.android.common.baselibrary.app.BaseApplication;
import com.android.common.baselibrary.log.SdLogUtil;
import com.android.common.baselibrary.util.comutil.TimeUtil;
import com.android.common.baselibrary.util.comutil.ZipUtils;


import java.io.File;
import java.util.ArrayList;

public class LogUploadUtil {
    public static final int MAX_WAIT_TASK_FINISH = 120000;



    public static String getTaskDirPath() {
        return BaseApplication.getInstance().getHomePath() + File.separator + "macro";
    }

    private static final File getLogUploadDirFile() {
        return new File(BaseApplication.getInstance().getUploadPath() + File.separator + "log");
    }

    public static void deleteUploadTaskFiles() {
        File file = getLogUploadDirFile();
        if (file.exists()) {
            for (File f : file.listFiles()) {
                f.delete();
            }
        }
    }

    private static final String getLogUploadZipFilePath(String taskId) {
        return getLogUploadDirFile().getAbsolutePath() + File.separator + taskId + ".zip";
    }


    public static String generateUploadFiles(int hours) throws Exception {
        clearUploadFileDir();
        File logBaseDir = new File(SdLogUtil.getLogDirPath());
        if (logBaseDir.exists()) {
            File[] logDirs = logBaseDir.listFiles();//File[] 是根据日期生成的目录
            if (logDirs != null) {
                ArrayList fl = new ArrayList();
                for (File logDir : logDirs) {
                    if (logDir.isDirectory()) {
                        for (File logFile : logDir.listFiles()) {
                            if (isSizeTooBig(logFile)) {

                            } else if (System.currentTimeMillis() - ((long) (((hours * 60) * 60) * 1000)) <= logFile.lastModified()) {
                                fl.add(logFile);
                            }
                        }
                    }
                }
                if (fl.size() > 0) {
                    return ZipUtils.zipFiles(fl, getLogUploadZipFilePath("all_" + System.currentTimeMillis()));
                }
            }
        }
        return null;
    }

    private static String getTimeAreaLog(String startTime, String endTime) throws Exception {
        clearUploadFileDir();
        long lStartTime = TimeUtil.getLongTime(startTime);
        long lEndTime = TimeUtil.getLongTime(endTime);
        File logBaseDir = new File(SdLogUtil.getLogDirPath());
        if (logBaseDir.exists()) {
            File[] logDirs = logBaseDir.listFiles();//File[] 是根据日期生成的目录
            if (logDirs != null) {
                ArrayList fl = new ArrayList();
                for (File logDir : logDirs) {
                    if (logDir.isDirectory()) {
                        for (File logFile : logDir.listFiles()) {
                            if (isSizeTooBig(logFile)) {

                            } else if (lStartTime <= logFile.lastModified() && lEndTime >= logFile.lastModified()) {
                                fl.add(logFile);
                            }
                        }
                    }
                }
                if (fl.size() > 0) {
                    return ZipUtils.zipFiles(fl, getLogUploadZipFilePath("all_" + System.currentTimeMillis()));
                }
            }
        }
        return null;
    }

    private static void clearUploadFileDir() {
        File dir = getLogUploadDirFile();
        if (dir.exists()) {
            deleteUploadTaskFiles();
        } else {
            dir.mkdirs();
        }
    }

    private static ArrayList<File> getTaskLogFiles(String taskId) throws Exception {
        ArrayList<File> files = new ArrayList();
        File logBaseDir = new File(SdLogUtil.getLogDirPath());
        if (logBaseDir.exists()) {
            File[] logDirs = logBaseDir.listFiles();//File[] 是根据日期生成的目录
            if (logDirs != null) {
                String logFilePrefix = "task_" + taskId;
                for (File logDir : logDirs) {
                    if (logDir.isDirectory()) {
                        for (File f : logDir.listFiles()) {
                            if (f.getName().startsWith(logFilePrefix)) {
                                if (isSizeTooBig(f)) {
                                    //SdLogUtil.writeSystemLog("单个文件过大，放弃上传此文件。[" + f.getAbsolutePath() + "]");
                                } else {
                                    files.add(f);
                                }
                            }
                        }
                    }
                }
            }
        }
        return files;
    }

    private static String getTaskLog(String taskId) throws Exception {
        clearUploadFileDir();
        ArrayList files = getTaskLogFiles(taskId);
        if (files.isEmpty()) {
            return null;
        }
        return ZipUtils.zipFiles(files, getLogUploadZipFilePath("all_" + System.currentTimeMillis()));
    }

    private static String getFileNameLog(String fileName) throws Exception {
        clearUploadFileDir();
        File logBaseDir = new File(SdLogUtil.getLogDirPath());
        if (logBaseDir.exists()) {
            File[] logDirs = logBaseDir.listFiles();//File[] 是根据日期生成的目录
            if (logDirs != null) {
                ArrayList fl = new ArrayList();
                for (File logDir : logDirs) {
                    if (logDir.isDirectory()) {
                        for (File logFile : logDir.listFiles()) {
                            if (isSizeTooBig(logFile)) {
                                //SdLogUtil.writeSystemLog("单个文件过大，放弃上传此文件。[" + logFile.getAbsolutePath() + "]");
                            } else if (logFile.getName().equals(fileName)) {
                                fl.add(logFile);
                            }
                        }
                    }
                }
                if (fl.size() > 0) {
                    return ZipUtils.zipFiles(fl, getLogUploadZipFilePath("all_" + System.currentTimeMillis()));
                }
            }
        }
        return null;
    }

    private static boolean isSizeTooBig(File f) {
        return FileUtils.sizeOf(f) > 5 * 1024 * 1024;
    }

}
