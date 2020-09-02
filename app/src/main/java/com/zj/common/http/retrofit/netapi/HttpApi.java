package com.zj.common.http.retrofit.netapi;


import com.zj.zhijue.http.request.IFileUpload;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 存放所有的 Api
 */
public interface HttpApi {

    /**
     * DX123456;;
     * zjfd_13322983552;;
     * 处理多个主机地址或域名的问题
     *
     * @param username
     * @param password
     * @return
     */
    @Headers({"urlname:retail"})
    @FormUrlEncoded
    @POST("login")
    Observable<ResponseBody> doLoginMdffx(@Field("username") String username, @Field("password") String password);

    @Headers({"urlname:train"})
    @GET("members/datas")
    Observable<ResponseBody> doData(@Query("type") int type, @Query("params") int params);


    @GET("")
    Observable<ResponseBody> getDataForMap(@QueryMap Map<String, Integer> paramMap);

    @GET
    @Streaming
    Observable<ResponseBody> downloadFile(@HeaderMap Map<String, Object> headmap, @Body RequestBody bodymap, @Url String url);

    /**
     * 获取协议信息
     */
    @Headers({"urlname:" + URLConstant.URLNAME_ZJFD})
    @POST("zjold/api" + URLConstant.MEMBER_PAHT + "/common/getConfigInfo")
    Observable<ResponseBody> getConfigInfo(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    /**
     * 下单
     */
    @Headers({"urlname:" + URLConstant.URLNAME_ZJFD})
    @POST("zjold/api" + URLConstant.MEMBER_PAHT + "/order/createOrder")
    Observable<ResponseBody> createOrder(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);


    /**
     * 获取充值服务信息
     */
    @Headers({"urlname:" + URLConstant.URLNAME_ZJFD})
    @POST("zjold/api" + URLConstant.MEMBER_PAHT + "/product/getProductInfo")
    Observable<ResponseBody> getProductInfo(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);


    @Headers({"urlname:" + URLConstant.URLNAME_ZJFD})
    @POST("zjold/api" + URLConstant.MEMBER_PAHT + "/login/login")
    Observable<ResponseBody> loginAccountWithHeader(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

//    //甲方登录接口
//    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
//    @POST("api/oauth/login")
//    Observable<ResponseBody> loginAccountWithHeader(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    /**
     * 10.发送时长不足推送消息
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_ZJFD})
    @POST("zjold/api" + URLConstant.MEMBER_PAHT + "/common/sendTimeRemind")
    Observable<ResponseBody> sendTimeRemind(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    /**
     * 获取时长提醒时间
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_ZJFD})
    @POST("zjold/api" + URLConstant.MEMBER_PAHT + "/common/durationRemindTime")
    Observable<ResponseBody> getDurationRemindTime(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    /**
     * 注册接口
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_ZJFD})
    @POST("zjold/api" + URLConstant.MEMBER_PAHT + "/login/register")
    Observable<ResponseBody> register(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/member/findPassword")
//找回密码
    Observable<ResponseBody> findPasswd(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api/member/updatePassword")
//修改密码
    Observable<ResponseBody> updatePasswd(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/verfication/SendVerficationCode")
    Observable<ResponseBody> postRegiterInfoWithVerficationCodeWithJson(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    /**
     * 绑定眼镜信息的接口
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/member/upsertEyeData")
    Observable<ResponseBody> bindGlassesInfo(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    /**
     * 修改视力信息的接口
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api/Customer/UpdateMemberEyeData")
    Observable<ResponseBody> updateEyeInfo(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    /**
     * 获取用户的训练时间信息
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/training/getTrainingTime")
    Observable<ResponseBody> getUserTrainTimeInfo(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    /**
     * 提交用户训练时间信息
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api/Customer/*")
    Observable<ResponseBody> postUserTrainTimeInfo(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    /**
     * 签到提交接口
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("/api/Customer/SignIn")
    Observable<ResponseBody> postSignInInfo(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    /**
     * 获取签到信息接口
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api/Customer/GetRecentSignIns")
    Observable<ResponseBody> getSignInInfo(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    //头像上传
    @Multipart
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api/fastdfs/upload")
    Observable<ResponseBody> uploadPortraitImage(@HeaderMap Map<String, String> params, @PartMap Map<String, RequestBody> parMap, @Part MultipartBody.Part file);

    @Multipart
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.UPLOAD_PATH + "/fastdfs/uploadException")
    Observable<ResponseBody> uploadAppLogZipFile(@HeaderMap Map<String, Object> params, @Part(IFileUpload.PARAMS) RequestBody bodymap, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST(URLConstant.OATH_TOKEN_URL)
    Call<ResponseBody> getNewToken(@HeaderMap Map<String, String> headmap, @FieldMap Map<String, String> bodymap);


    /**
     * 删除云端训练数据（调试用）
     *
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @GET("api" + URLConstant.MEMBER_PAHT + "/training/deleteTrainingData")
    Observable<ResponseBody> deleteServerTrainInfoWithDebug(@QueryMap Map<String, Object> headmap);

    /**
     * 退出登录
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_ZJFD})
    @FormUrlEncoded
    @POST("zjold/api/login/getExit")
    Observable<ResponseBody> logoutToken(@HeaderMap Map<String, Object> headmap, @FieldMap Map<String, Object> bodymap);

    /**
     * 上报用户的训练数据（双眼训练时，电机的运转数据）
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/training/upload")
    Observable<ResponseBody> postUserTrainInfo(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    /**
     * 获取最新的训练数据给蓝牙设备
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/training/recentlyTraining")
    Observable<ResponseBody> getNewUserTrainInfo(@HeaderMap Map<String, String> headmap, @Body RequestBody bodymap);

    /**
     * 视力复查数据
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/member/review/getReviewEyeDegreeData")
    Observable<ResponseBody> getReviewSightDataInfo(@HeaderMap Map<String, Object> headmap, @Body RequestBody bodymap);

    /**
     * 提交设备信息
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/other/mobileinfo/upsert")
    Observable<ResponseBody> postDeviceInfo(@HeaderMap Map<String, Object> headmap, @Body RequestBody bodymap);

    /**
     * 提交用户反馈
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api/afterSale/feedback")
    Observable<ResponseBody> postFeedback(@HeaderMap Map<String, Object> headmap, @Body RequestBody bodymap);

    /**
     * 获取当前用户的反馈信息列表
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @GET("api" + URLConstant.MEMBER_PAHT + "/afterSale/feedback")
    Observable<ResponseBody> getFeedbackList(@HeaderMap Map<String, Object> headmap, @QueryMap Map<String, Object> bodymap);

    /**
     * 获取常见问题
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @GET("api" + URLConstant.MEMBER_PAHT + "/afterSale/question")
    Observable<ResponseBody> getQuestionList(@HeaderMap Map<String, Object> headmap, @QueryMap Map<String, Object> bodymap);


    /**
     * 获取APP版本信息 或 固件版本信息
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/system/update/getLastedSystemUpdate")
    Observable<ResponseBody> getApkVersionInfo(@HeaderMap Map<String, Object> headmap, @Body RequestBody bodymap);


    /**
     * 获取机器运行参数
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @GET("api" + URLConstant.MEMBER_PAHT + "/member/getDeviceParam")
    Observable<ResponseBody> getGlassDeviceInitParam(@HeaderMap Map<String, Object> headmap, @QueryMap Map<String, Object> bodymap);


    /**
     * 上报干预反馈数据
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/training/uploadTrainingInterveneData")
    Observable<ResponseBody> postInterveneFeedBackData(@HeaderMap Map<String, Object> headmap, @Body RequestBody bodymap);

    /**
     * 上报实时反馈数据
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/training/uploadTrainingRealTimeData")
    Observable<ResponseBody> postCommonFeedBackData(@HeaderMap Map<String, Object> headmap, @Body RequestBody bodymap);

    /**
     * 上报运行参数数据
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/training/uploadDetail")
    Observable<ResponseBody> postRunParamData(@HeaderMap Map<String, Object> headmap, @Body RequestBody bodymap);


    /**
     * 获取运行参数数据
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/training/recentlyTrainingDetail")
    Observable<ResponseBody> queryRunParamData(@HeaderMap Map<String, Object> headmap, @Body RequestBody bodymap);

    /**
     * 获取 Html 代码
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @GET("api" + URLConstant.MEMBER_PAHT + "/system/info/getInfoByCategoryId")
    Observable<ResponseBody> getHtmlCode(@HeaderMap Map<String, Object> headmap, @QueryMap Map<String, Object> bodymap);

    /**
     * 绑定设备
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/member/bindDevice")
    Observable<ResponseBody> bindDevice(@HeaderMap Map<String, Object> headmap, @Body RequestBody bodymap);

    /**
     * 提交录入的复查数据
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/member/review")
    Observable<ResponseBody> postInputReviewData(@HeaderMap Map<String, Object> headmap, @Body RequestBody bodymap);

    /**
     * 提交录入的其他数据
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api" + URLConstant.MEMBER_PAHT + "/")
    Observable<ResponseBody> postInputOtherData(@HeaderMap Map<String, Object> headmap, @Body RequestBody bodymap);


    /**
     * 获取消息列表
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_ZJFD})
    @FormUrlEncoded
    @POST("zjold/api/account/getMessageList")
    Observable<ResponseBody> getMessageList(@HeaderMap Map<String, Object> headmap, @FieldMap Map<String, Object> bodymap);

    /**
     * 获取我的团队列表
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_ZJFD})
    @FormUrlEncoded
    @POST("zjold/api/account/getAccountTeam")
    Observable<ResponseBody> getTeamList(@HeaderMap Map<String, Object> headmap, @FieldMap Map<String, Object> bodymap);

    /**
     * 获取充值及消费时长记录
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @POST("api/member/time/logs")
    Observable<ResponseBody> getAddAndUseLogs(@HeaderMap Map<String, Object> headmap, @Body RequestBody bodymap);

    /**
     * 根据手机号获取用户信息
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @GET("api/member/getMemberInfoByPhone")
    Observable<ResponseBody> getUseInfo(@HeaderMap Map<String, Object> headmap, @QueryMap Map<String, Object> bodymap);


    /**
     * 获取app版本信息
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_ZJFD})
    @FormUrlEncoded
    @POST("zjold/api/common/getAppVersion")
    Observable<ResponseBody> getAppVersion(@HeaderMap Map<String, Object> headmap, @FieldMap Map<String, Object> bodymap);

    /**
     * 根据手机号获取用户信息
     *
     * @param headmap
     * @param bodymap
     * @return
     */
    @Headers({"urlname:" + URLConstant.URLNAME_FIRST_PARTY})
    @GET("api/member/getMemberById")
    Observable<ResponseBody> getPersonalInfo(@HeaderMap Map<String, Object> headmap, @QueryMap Map<String, Object> bodymap);

}
