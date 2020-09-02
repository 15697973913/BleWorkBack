package com.android.common.baselibrary.util.comutil;





import com.litesuits.common.io.FileUtils;
import com.android.common.baselibrary.app.BaseApplication;
import com.android.common.baselibrary.log.SdLogUtil;



import java.io.File;
import java.util.ArrayList;


public class LogTaskUtil {

    public static String generateUploadFiles(int hours) throws Exception {
        clearUploadFileDir();
        File logBaseDir = new File(SdLogUtil.getLogDirPath());
        if (logBaseDir.exists()) {
            File[] logDirs = logBaseDir.listFiles();
            if (logDirs != null) {
                ArrayList fileList = new ArrayList();
                for (File logDir : logDirs) {
                    if (logDir.isDirectory()) {
                        for (File logFile : logDir.listFiles()) {
                           if (System.currentTimeMillis() - ((long) (((hours * 60) * 60) * 1000)) <= logFile.lastModified()
                                   && isSizeTooBig(logFile)) {
                                fileList.add(logFile);
                            }
                        }
                    }
                }
                if (fileList.size() > 0) {
                    return ZipUtils.zipFiles(fileList, getLogUploadZipFilePath("all_" + System.currentTimeMillis()));
                }
            }
        }
        return null;
    }

    private static boolean isSizeTooBig(File f) {
        return FileUtils.sizeOf(f) > 5 * 1024 * 1024;
    }

    private static void clearUploadFileDir() {
        File dir = getLogUploadDirFile();
        if (dir.exists()) {
            deleteUploadTaskFiles();
        } else {
            dir.mkdirs();
        }
    }

    private static final String getLogUploadZipFilePath(String taskId) {
        return getLogUploadDirFile().getAbsolutePath() + File.separator + taskId + ".zip";
    }

    public static void deleteUploadTaskFiles() {
        File file = getLogUploadDirFile();
        if (file.exists()) {
            for (File f : file.listFiles()) {
                f.delete();
            }
        }
    }

    private static final File getLogUploadDirFile() {
        return new File(BaseApplication.getInstance().getUploadPath() + File.separator + "log");
    }
}
