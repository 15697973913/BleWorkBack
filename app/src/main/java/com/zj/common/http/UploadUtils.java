package com.zj.common.http;


import android.os.AsyncTask;
import android.text.TextUtils;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.log.SdLogUtil;
import com.zj.zhijue.util.AppUtils;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.android.common.baselibrary.util.comutil.CustomListener;
import com.android.common.baselibrary.util.comutil.log.CrashHandler;
import com.android.common.baselibrary.util.comutil.security.Md5Util;
import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.common.http.retrofit.netutils.NetUtil;
import com.zj.common.http.retrofit.utils.CompressUtils;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.http.request.IFileUpload;
import com.zj.zhijue.http.response.HttpResponseUploadExceptionFileBean;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.litesuits.common.io.IOUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class UploadUtils {
    private static final String CHARSET = "utf-8";
    private static final String TAG = "uploadFile";
    public static final int TIME_OUT = 60000;
    public static volatile AtomicBoolean uploading = new AtomicBoolean(false);

    public static class UploadFileAsyncTask extends AsyncTask<String, Void, String> {
        CustomListener listener;
        public String mUploadDir;

        public UploadFileAsyncTask(String uploadDir, CustomListener listener) {
            if (!CommonUtils.isEmpty(uploadDir)) {
                this.mUploadDir = uploadDir;
            } else {
                this.mUploadDir = "app_temp";
            }
            this.listener = listener;
        }

        protected void onPostExecute(String result) {
            if (TextUtils.isEmpty(result)) {
                //this.listener.onFailed(FileUploadResult.getDefaultError("文件上传服务器失败"));
                listener.onFailed();
                return;
            }
            MLog.d("onPostExecute result = " + result);
            listener.onSuccess();
        }

        protected void onPreExecute() {
        }

        protected void onCancelled() {
            super.onCancelled();
        }

        protected String doInBackground(String... params) {
            String response = "";
            String requestUrl = URLConstant.POST_CRASH_LOG_FILE_URL;
            String imei = DeviceUtils.getIMEI();

            if (CommonUtils.isEmpty(requestUrl)) {
                return "";
            }

            try {
                return UploadUtils.uploadFile(new File(params[0]), requestUrl + "?imei=" + imei, "");
            } catch (Exception e) {
                e.printStackTrace();
                response = e.getMessage();
            }
            return response;
        }

        protected void onProgressUpdate(Void... values) {
        }
    }

    public static String uploadFile(File file, String RequestURL, String fileType) throws Exception {
        RequestURL = RequestURL + "&filename=" + file.getName();
        String BOUNDARY = UUID.randomUUID().toString();
        String PREFIX = "--";
        String LINE_END = IOUtils.LINE_SEPARATOR_WINDOWS;
        String CONTENT_TYPE = "multipart/form-data";
        HttpURLConnection conn = (HttpURLConnection) new URL(RequestURL).openConnection();
        conn.setReadTimeout(TIME_OUT);
        conn.setConnectTimeout(TIME_OUT);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Charset", CHARSET);
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
        if (file == null) {
            return null;
        }
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        StringBuffer sb = new StringBuffer();
        sb.append(PREFIX);
        sb.append(BOUNDARY);
        sb.append(LINE_END);
        sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + fileType + "\"" + LINE_END);
        sb.append("Content-Type: application/octet-stream; charset=utf-8" + LINE_END);
        sb.append(LINE_END);
        dos.write(sb.toString().getBytes());
        InputStream is = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        while (true) {
            int len = is.read(bytes);
            if (len == -1) {
                break;
            }
            dos.write(bytes, 0, len);
        }
        is.close();
        dos.write(LINE_END.getBytes());
        dos.write((PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes());
        dos.flush();
        int res = conn.getResponseCode();
        //MLog.d(("=========upload file========response code:" + res);
        if (res != 200) {
            return null;
        }
        String response = convertStreamToString(conn.getInputStream());
        //MLog.d("=========upload file========response data:" + res);
        return response;
    }

    public static String convertStreamToString(InputStream inputStream) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while (true) {
                String v1 = bufferedReader.readLine();
                if (v1 == null) {
                    break;
                }
                stringBuilder.append(v1 + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return stringBuilder.toString();
    }

    public synchronized static void uploadLogFile() {
        if (!NetUtil.isNetworkAvalible(MyApplication.getInstance())) {
            return;
        }

        if (!NetUtil.isWifiConnected(MyApplication.getInstance())) {
            return;
        }

        if (uploading.get()) {
            return;
        }
        uploading.set(true);

        String uploadPath = MyApplication.getInstance().getUploadPath();
        String logDir = SdLogUtil.getLogDirPath();
        String logFileName = SdLogUtil.getCommonLogFileName();

        String sourceLogPath = CrashHandler.getCrashLogFileName();//logDir + logFileName;
        MLog.d("sourceLogPath = " + sourceLogPath);
        final File soureFile = new File(sourceLogPath);
        if (!soureFile.exists() || soureFile.length() == 0) {
            uploading.set(false);
            return;
        } else {
            if (soureFile.length() > 10L * 1024 * 1024L) {
                try {
                    soureFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                uploading.set(false);
                return;
            }
        }

        File desFile = new File(uploadPath + File.separator + soureFile.getName());
        String newDesName = Md5Util.getMD5String(AppUtils.getUtdId()) + "_" + DeviceUtils.getIMEI()+ "_" + System.currentTimeMillis()+ "_" + desFile.getName() ;
        int dotIndex = newDesName.indexOf(".");
        newDesName = newDesName.substring(0, dotIndex + 1) + "zip";
        MLog.d("newDesName = " + newDesName);
        String parentPath = desFile.getParentFile().getAbsolutePath();
        desFile = new File(parentPath + File.separator + newDesName);
        if (!desFile.getParentFile().exists()) {
            desFile.getParentFile().mkdirs();
        }
        try {
            CompressUtils.zip(sourceLogPath, desFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        MLog.d("desFile.length() = " + desFile.length() + " desFile = " + desFile.getAbsolutePath());
        uploadAppCrashLogZipFile(soureFile, desFile.getAbsolutePath());

     /*   UploadFileAsyncTask uploadDeviceInfoTask = new UploadFileAsyncTask(uploadPath, new CustomListener() {
            @Override
            public void onSuccess() {
                MLog.d("onSuccess()");
                try {
                    soureFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {
                MLog.d("onFailed()");
            }
        });
        uploadDeviceInfoTask.execute(desFile.getAbsolutePath());*/
    }

    private static void uploadAppCrashLogZipFile(final File soureFile, final String zipLogFileAbsPath) {

        final HashMap<String, Object> headParaMap = new HashMap<>();
        HashMap<String, Object> partMap = new HashMap<>();

        String memberId = Config.getConfig().getUserServerId();
        UserInfoDBBean userInfoDBBean = null;
        if (!CommonUtils.isEmpty(memberId)) {
            List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), memberId);
            if (null != userInfoDBBeanList && userInfoDBBeanList.size() > 0) {
                userInfoDBBean = userInfoDBBeanList.get(0);
            }

        }

        String utdid = AppUtils.getUtdId();
        String versionCode = String.valueOf(AppUtils.getVersionCode());
        String versionName = String.valueOf(AppUtils.getGitCommitVersionName(MyApplication.getInstance()));
        String imei = DeviceUtils.getIMEI();
        String android_SDK_VersionCode = String.valueOf(DeviceUtils.getSDK_INI());
        String android_SDK_VersionName = DeviceUtils.getDeviceSystemVersion();

        partMap.put(IFileUpload.OWNERID, !CommonUtils.isEmpty(memberId) ? memberId : IFileUpload.OWNER_ID_DEFAULT_VALUE);
        partMap.put(IFileUpload.PLATFORMTYPE, IFileUpload.PLATFORM_VALUE_ANDROID);
        partMap.put(IFileUpload.CATEGORY, IFileUpload.CATEGORY_EXCEPTION_ZIP_FILE_TYPE);
        partMap.put(IFileUpload.UTDID, utdid);
        partMap.put(IFileUpload.IMEI, imei);
        partMap.put(IFileUpload.APP_VERSION_NAME, versionName);
        partMap.put(IFileUpload.APP_VERSION_CODE, versionCode);
        partMap.put(IFileUpload.SYS_VERSION_NAME, android_SDK_VersionName);
        partMap.put(IFileUpload.SYS_VERSION_CODE, android_SDK_VersionCode);
        partMap.put(IFileUpload.UPLOADER, null != userInfoDBBean ? userInfoDBBean.getLogin_name() : "");
        partMap.put(IFileUpload.MODULE, IFileUpload.PLATFORM_VALUE_ANDROID);

        LoginSubscribe.uploadAppLogZipFile(headParaMap, partMap, zipLogFileAbsPath, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String response = null;
                try {
                    response = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                HttpResponseUploadExceptionFileBean httpResponseUploadExceptionFileBean = (HttpResponseUploadExceptionFileBean) JsonUtil.json2objectWithDataCheck(response, new TypeReference<HttpResponseUploadExceptionFileBean>() {
                });

                MLog.d("uploadAppCrashLogZipFile response = " + response);
                if (null != httpResponseUploadExceptionFileBean
                        && !CommonUtils.isEmpty(httpResponseUploadExceptionFileBean.getStatus())
                        && httpResponseUploadExceptionFileBean.getStatus().equalsIgnoreCase(IBaseRequest.SUCCESS)) {
                    try {
                        boolean sourdeDeleteOk = soureFile.delete();
                        MLog.d("sourdedeleteOk = " + sourdeDeleteOk  + " sourdeDeleteOk.absPath = " + soureFile.getAbsolutePath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        File zipFile = new File(zipLogFileAbsPath);
                        boolean deleteOk = zipFile.delete();
                        MLog.d("deleteOk = " + deleteOk + " deleteFile = " + zipFile.getAbsolutePath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }


}
