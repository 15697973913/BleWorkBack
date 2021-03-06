package com.zj.common.http.retrofit.netapi;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DownloadApi {
    @GET
    @Streaming
    Observable<ResponseBody> downloadFile(@Url String url);
}
