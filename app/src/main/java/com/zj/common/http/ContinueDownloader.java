package com.zj.common.http;

import com.android.common.baselibrary.util.ThreadPoolUtils;
import com.litesuits.common.io.FileUtils;




import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContinueDownloader implements Runnable, ContinueDownloadInterface {
    private static final int RE_DOWNLOAD_COUNT = 5;
    private int contentLength;
    private int downloadCount = 1;
    private DownloadListener downloadListener;
    private int downloadTotal = 0;
    private boolean downloading = false;
    private File file;
    private String fileUrl;
    List<HashMap<String, Integer>> listThread = new ArrayList();
    private int reloadCount = 0;

    public interface DownloadListener {
        void downloadFailed(String str, Throwable th);

        void downloadSuccessed();

        void handleContentLength(int i);

        void handleProgress(int i);
    }

    class DownloadRunnable implements Runnable {
        private int begin;
        private int end;
        private File file;
        private String fileUrl;
        private int id;
        private int reloadCount = 0;

        public DownloadRunnable(int id, int begin, int end, File file, String url) {
            this.id = id;
            this.begin = begin;
            this.end = end;
            this.file = file;
            this.fileUrl = url;
        }

        public void run() {
            try {
                if (this.reloadCount >= RE_DOWNLOAD_COUNT) {
                    if (ContinueDownloader.this.downloadListener != null) {
                        ContinueDownloader.this.downloadListener.downloadFailed(this.fileUrl, new Exception("下载线程超过重试下载次数"));
                    }
                    ContinueDownloader.this.setDownloading(false);
                } else if (this.begin >= this.end) {
                    //LogCat.LogI(this.id + " : 下载完成:" + this.begin + "-" + this.end);
                    if (ContinueDownloader.this.downloadTotal == ContinueDownloader.this.contentLength) {
                        if (ContinueDownloader.this.downloadListener != null) {
                            ContinueDownloader.this.downloadListener.downloadSuccessed();
                        }
                        ContinueDownloader.this.setDownloading(false);
                       //SdLogUtil.writeTaskLog("==========================downloadListener-->downloadSuccessed");
                    }
                } else {
                    HttpURLConnection conn = (HttpURLConnection) new URL(this.fileUrl).openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322)");
                    conn.setRequestProperty("Range", "bytes=" + this.begin + "-" + this.end);
                    InputStream is = conn.getInputStream();
                    byte[] buf = new byte[2048];
                    RandomAccessFile randomAccessFile = new RandomAccessFile(this.file, "rw");
                    randomAccessFile.seek((long) this.begin);
                    HashMap<String, Integer> map = (HashMap) ContinueDownloader.this.listThread.get(this.id);
                    while (true) {
                        int len = is.read(buf);
                        if (len != -1 && ContinueDownloader.this.downloading) {
                            randomAccessFile.write(buf, 0, len);
                            map.put("finish", Integer.valueOf(((Integer) map.get("finish")).intValue() + len));
                            ContinueDownloader.this.updateProgress(len);
                            continue;
                        }
                        break;
                    }
                   if (!ContinueDownloader.this.downloading) {
                        //SdLogUtil.writeTaskLog("===================暂停下载========================");
                    }
                    randomAccessFile.close();
                    is.close();
                    if (((long) ((int) FileUtils.sizeOf(this.file))) == ((long) ContinueDownloader.this.contentLength)) {
                        if (ContinueDownloader.this.downloadListener != null) {
                            ContinueDownloader.this.downloadListener.downloadSuccessed();
                        }
                        ContinueDownloader.this.setDownloading(false);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
                reDownloadThread();
            }
        }

        private void reDownloadThread() {
            if (ContinueDownloader.this.isDownloading()) {
                try {
                    Thread.currentThread();
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                this.reloadCount++;
                if (this.reloadCount >= RE_DOWNLOAD_COUNT) {
                    //SdLogUtil.writeTaskLog("超过重试下载次数");
                    if (ContinueDownloader.this.downloadListener != null) {
                        ContinueDownloader.this.downloadListener.downloadFailed(this.fileUrl, new Exception("下载线程超过重试下载次数"));
                    }
                    ContinueDownloader.this.setDownloading(false);
                    return;
                }
                //SdLogUtil.writeTaskLog("重试下载：" + this.reloadCount);
                HashMap<String, Integer> map = (HashMap) ContinueDownloader.this.listThread.get(this.id);
                this.begin = ((Integer) map.get("finish")).intValue() + ((Integer) map.get("begin")).intValue();
                ThreadPoolUtils.execute(this);
            }
        }
    }

    public int getDownloadTotal() {
        return this.downloadTotal;
    }

    public int getContentLength() {
        return this.contentLength;
    }

    public ContinueDownloader(int downloadCount, File file, String fileUrl, DownloadListener callback) {
        this.downloadCount = downloadCount;
        this.file = file;
        this.fileUrl = fileUrl;
        this.downloadListener = callback;
    }

    public void restoreStateByFile() {
        HashMap<String, Integer> map = new HashMap();
        map.put("begin", Integer.valueOf(0));
        map.put("end", Integer.valueOf(-1));
        int fileSize = 0;
        if (this.file.exists()) {
            fileSize = (int) FileUtils.sizeOf(this.file);
            this.downloadTotal = fileSize;
            //SdLogUtil.writeSystemLog("===========restoreStateByFile恢复下载===========" + fileSize);
        }
        map.put("finish", Integer.valueOf(fileSize));
        this.listThread.clear();
        this.listThread.add(map);
        if (this.downloadListener != null) {
            this.downloadListener.handleProgress(fileSize);
        }
    }

    public void run() {
        if (this.reloadCount >= RE_DOWNLOAD_COUNT) {
            //SdLogUtil.writeSystemLog("超过重试下载次数");
            if (this.downloadListener != null) {
                this.downloadListener.downloadFailed(this.fileUrl, new Exception("超过重试下载次数"));
            }
            setDownloading(false);
            return;
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(this.fileUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322)");
            this.contentLength = conn.getContentLength();

            if (this.contentLength < 0) {
                throw new Exception("File not exist!!!");
            }
            this.downloadListener.handleContentLength(this.contentLength);
            int i;
            int begin;
            int end;
            HashMap<String, Integer> map;
            if (this.listThread.size() == 0) {
                //SdLogUtil.writeTaskLog(" first Download !!!!");
                int blockSize = this.contentLength / this.downloadCount;
                for (i = 0; i < this.downloadCount; i++) {
                    begin = i * blockSize;
                    end = ((i + 1) * blockSize) - 1;
                    if (i == this.downloadCount - 1) {
                        end = this.contentLength;
                    }
                    map = new HashMap();
                    map.put("begin", Integer.valueOf(begin));
                    map.put("end", Integer.valueOf(end));
                    map.put("finish", Integer.valueOf(0));
                    this.listThread.add(map);
                    ThreadPoolUtils.execute(new DownloadRunnable(i, begin, end, this.file, this.fileUrl));
                }
                return;
            }
            //SdLogUtil.writeTaskLog("=====================恢复下载======================");
            for (i = 0; i < this.listThread.size(); i++) {
                map = (HashMap) this.listThread.get(i);
                begin = ((Integer) map.get("begin")).intValue();
                end = ((Integer) map.get("end")).intValue();
                if (end < 0) {
                    end = this.contentLength;
                }
                int i2 = i;
                int i3 = end;
                ThreadPoolUtils.execute(new DownloadRunnable(i2, begin + ((Integer) map.get("finish")).intValue(), i3, this.file, this.fileUrl));
            }
        } catch (Throwable e) {
           e.printStackTrace();
            reDownload();
        }
    }

    private void reDownload() {
        if (isDownloading()) {
            try {
                Thread.currentThread();
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            this.reloadCount++;
            if (this.reloadCount >= 5) {
                //SdLogUtil.writeTaskLog("超过重试下载次数");
                if (this.downloadListener != null) {
                    this.downloadListener.downloadFailed(this.fileUrl, new Exception("超过重试下载次数"));
                }
                setDownloading(false);
                return;
            }
            //SdLogUtil.writeTaskLog("重试下载：" + this.reloadCount);
            ThreadPoolUtils.execute(this);
        }
    }

    public boolean isDownloading() {
        return this.downloading;
    }

    public void setDownloading(boolean downloading) {
        this.downloading = downloading;
    }

    public void start() {
        if (!isDownloading()) {
            this.reloadCount = 0;
            setDownloading(true);
            ThreadPoolUtils.execute(this);
        }
    }

    public void pause() {
        setDownloading(false);
    }

    public synchronized int updateProgress(int downloadSize) {
        this.downloadTotal += downloadSize;
        if (this.downloadListener != null) {
            this.downloadListener.handleProgress(this.downloadTotal);
        }
        return this.downloadTotal;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public File getFile() {
        return this.file;
    }
}
