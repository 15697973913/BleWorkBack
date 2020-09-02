package com.zj.common.http;

public interface ContinueDownloadInterface {
    void pause();

    void start();

    int updateProgress(int i);
}
