package com.android.common.baselibrary.log;

import android.Manifest;
import android.os.Process;
import android.text.TextUtils;

import com.android.common.baselibrary.app.BaseApplication;
import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.FileUtils;
import com.android.common.baselibrary.util.PermissionUtil;
import com.android.common.baselibrary.util.ThreadPoolUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BleDataLog {
    private static final int MAX_LOG_FILE_SIZE = 2 * 1024 * 1024;

    private static DateFormat formatterDate = new SimpleDateFormat("yyyy_MM_dd");
    private static DateFormat formatterTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    private static DateFormat formatterTime2 = new SimpleDateFormat("yyyyMMddHHmmss");
    private static LogProcessor processor = null;
    private static String taskLogIdentity = "";

    static class LogContent {
        private String content;
        private String fileName;
        private int maxFileSize;
        private int myPid;
        private int myTid;
        private long threadId;

        LogContent() {
        }

        public int getMyPid() {
            return this.myPid;
        }

        public void setMyPid(int myPid) {
            this.myPid = myPid;
        }

        public int getMyTid() {
            return this.myTid;
        }

        public void setMyTid(int myTid) {
            this.myTid = myTid;
        }

        public long getThreadId() {
            return this.threadId;
        }

        public void setThreadId(long threadId) {
            this.threadId = threadId;
        }

        public String getFileName() {
            return this.fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getMaxFileSize() {
            return this.maxFileSize;
        }

        public void setMaxFileSize(int maxFileSize) {
            this.maxFileSize = maxFileSize;
        }

        @Override
        public String toString() {
            return  content
                   /* ", myPid=" + myPid +
                    ", myTid=" + myTid +
                    ", threadId=" + threadId +
                    '}'*/;
        }
    }

    static class LogProcessor implements Runnable {
        List dataList;
        private ExecutorService executorService;
        private volatile boolean mIsActive;
        private volatile boolean mIsAlive;

        public LogProcessor() {
            this.executorService = Executors.newSingleThreadExecutor();
            this.mIsAlive = true;
            this.mIsActive = false;
            this.dataList = new ArrayList();
            this.mIsAlive = true;
            this.mIsActive = false;
        }

        public void startup() {
            if (!this.mIsActive) {
                this.executorService.execute(this);
            }
        }

        public void shutdown() {
            this.mIsAlive = false;
            this.executorService.shutdownNow();
        }

        public boolean isActive() {
            return this.mIsActive;
        }

        public synchronized boolean pushData(int myPid, int myTid, long threadId, String fileName, int maxFileSize, String content) {
            boolean addSuccess;
            try {
                LogContent data = new LogContent();
                data.setMyPid(myPid);
                data.setMyTid(myTid);
                data.setThreadId(threadId);
                data.setFileName(fileName);
                data.setMaxFileSize(maxFileSize);
                data.setContent(content);
                this.dataList.add(data);
                addSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
                addSuccess = false;
            }
            return addSuccess;
        }

        public synchronized LogContent popData() {
            LogContent data = null;
            try {
                if (!this.dataList.isEmpty()) {
                    data = (LogContent) this.dataList.get(0);
                    this.dataList.remove(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        public void run() {
            writeLog(getCommonLogFileName(), MAX_LOG_FILE_SIZE, "BleData Log Processor Start" + "\n");
            while (this.mIsAlive) {
                this.mIsActive = true;
                try {
                    LogContent data = popData();
                    if (data != null) {
                        writeLog(data.getFileName(), data.getMaxFileSize(), data.toString() + "\n");
                        //LogUtils.d("writeLog  data = " + data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CommonUtils.sleep(1000);
            }
            this.mIsActive = false;
            //writeLog(BleDataLog.getExceptionLogFileName(), MAX_LOG_FILE_SIZE, "Log Processor Stop");
        }

        private File initFile(String fileName, int maxFileSize) {
            String filePath = BleDataLog.getLogDirPath() + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                if (maxFileSize <= 0 || file.length() <= ((long) maxFileSize)) {
                    return file;
                }
                file.renameTo(new File(filePath + "." + System.currentTimeMillis()));
            }
            if (!file.getParentFile().exists()) {
                /**
                 * 创建新的日期为目录文件
                 */
                file.getParentFile().mkdirs();
                deleteOtherDateDirFiles();
            }
            try {
                if (file.createNewFile()) {
                    return file;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private BufferedWriter openFile(File file) {
            if (file == null) {
                return null;
            }
            try {
                return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private void appendContent(BufferedWriter writer, String content) {
            if (writer != null && !TextUtils.isEmpty(content)) {
                try {
                    writer.write(content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void closeFile(BufferedWriter writer) {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private synchronized void writeLog(String fileName, int maxFilesize, String content) {
            try {
                File initFile = initFile(fileName, maxFilesize);
                BufferedWriter writer = openFile(initFile);
                appendContent(writer, content);
                closeFile(writer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized String getTaskLogIdentity() {
        String str;
        synchronized (BleDataLog.class) {
            str = taskLogIdentity;
        }
        return str;
    }

    public static synchronized void setTaskLogIdentity(String val) {
        synchronized (BleDataLog.class) {
            taskLogIdentity = val;
        }
    }

    public static synchronized void setTaskLogIdentityWithTime(String val) {
        synchronized (BleDataLog.class) {
            taskLogIdentity = val + "_" + formatterTime2.format(new Date());
        }
    }

    public static synchronized String getTaskLogIdentityWithTime(String val) {
        String str;
        synchronized (BleDataLog.class) {
            str = val + "_" + formatterTime2.format(new Date());
        }
        return str;
    }

    public static synchronized void resetTaskLogIdentity() {
        synchronized (BleDataLog.class) {
            taskLogIdentity = "";
        }
    }

    private static synchronized String getTaskLogFileName() {
        String str;
        synchronized (BleDataLog.class) {
            str = formatterDate.format(new Date()) + "/task_" + getTaskLogIdentity() + ".log";
        }
        return str;
    }

    private static synchronized String getDeviceLogFileName() {
        String str;
        synchronized (BleDataLog.class) {
            str = formatterDate.format(new Date()) + "/device_" + formatterDate.format(new Date()) + ".log";
        }
        return str;
    }

    private static synchronized String getExceptionLogFileName() {
        String str;
        synchronized (BleDataLog.class) {
            str = formatterDate.format(new Date()) + "/exception_" + formatterDate.format(new Date()) + ".log";
        }
        return str;
    }

    public static synchronized String getLogDirPath() {
        String str;
        synchronized (BleDataLog.class) {
            str = BaseApplication.getInstance().getHomePath() + File.separator + "log" + File.separator;
        }
        return str;
    }

    public static synchronized String getUploadDirPath() {
        String uploadPath;
        synchronized (BleDataLog.class) {
            uploadPath = BaseApplication.getInstance().getUploadPath() + File.separator;
        }
        return uploadPath;
    }


    public synchronized static String getCommonLogFileName() {
        String str;
        synchronized (BleDataLog.class) {
            str = formatterDate.format(new Date()) + "/bledatalog_" + formatterDate.format(new Date()) + ".log";
        }
        return str;
    }

    public static synchronized File getTaskLogFile(String identity) {
        File file;
        synchronized (BleDataLog.class) {
            file = new File(getLogDirPath() + formatterDate.format(new Date()) + "/task_" + identity + ".log");
        }
        return file;
    }

    public static void init() {
        if (!PermissionUtil.hasPermission(BaseApplication.getInstance(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return;
        }

        if (processor == null) {
            processor = new LogProcessor();
        }
        processor.startup();
    }

    public static void uninit() {
        if (processor != null) {
            processor.shutdown();
        }
    }

    public static void writeExceptionLog(Throwable e) {
        if (e != null) {
            e.printStackTrace();
            if (processor != null) {
                processor.pushData(Process.myPid(), Process.myTid(), Thread.currentThread().getId(), getExceptionLogFileName(), 2097152, e.getMessage() + "\n" + getExceptionString(e));
            }
        }
    }

    public static void writeBleData(String commonLog) {
        if (null != commonLog) {
            if (processor != null) {
                commonLog = "[" + formatterTime.format(new Date(System.currentTimeMillis())) + "]\n" + commonLog;
                processor.pushData(Process.myPid(), Process.myTid(), Thread.currentThread().getId(), getCommonLogFileName(), 2097152, commonLog);
            }
        }
    }

    private static String getExceptionString(Exception e) {
        try {
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stackArray = e.getStackTrace();
            for (StackTraceElement element : stackArray) {
                sb.append(element.toString() + "\n");
            }
            return sb.toString();
        } catch (Exception e2) {
            return "";
        }
    }

    private static String getExceptionString(Throwable e) {
        try {
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stackArray = e.getStackTrace();
            for (StackTraceElement element : stackArray) {
                sb.append(element.toString() + "\n");
            }
            return sb.toString();
        } catch (Exception e2) {
            return "";
        }
    }

    private static long dayIndex(DateFormat format, String startDate) {
        long index = 0;
        try {
            return (format.parse(format.format(new Date())).getTime() - format.parse(startDate).getTime()) / 86400000;
        } catch (Exception e) {
            e.printStackTrace();
            return index;
        }
    }

    /**
     * 删除非当前日期下的所有目录下的文件（包括非当前目录）
     */
    private static void deleteOtherDateDirFiles() {
        final String logDirAbsPath = getLogDirPath();
        final String uploadAbsPath = getUploadDirPath();
        final String currentDateDir = formatterDate.format(new Date());
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                deleteFilesWithoutCurrentDateDir(logDirAbsPath, currentDateDir);
                //LogUtils.d("deleteOtherDateDirFiles = " + uploadAbsPath + currentDateDir);
                deleteFilesWithoutCurrentDateDir(uploadAbsPath, currentDateDir);
            }
        });

    }

    private static void deleteFilesWithoutCurrentDateDir(String dirPath, String withoutDirName) {
        File fileRootDir = new File(dirPath);
        if (fileRootDir.exists()) {
            if (fileRootDir.isDirectory()) {
                File[] filesArray = fileRootDir.listFiles();
                if (null != filesArray) {
                    for (File file : filesArray) {
                        if (file.isDirectory() && file.getName().equals(withoutDirName)) {
                            continue;
                        } else {
                            FileUtils.deleteAllFilesOfDir(file);
                            file.delete();
                        }
                    }
                }
            }
        }

    }
}
