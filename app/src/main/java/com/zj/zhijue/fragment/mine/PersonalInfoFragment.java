package com.zj.zhijue.fragment.mine;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.PermissionUtil;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.huige.library.utils.SharedPreferencesUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.vise.xsnow.permission.OnPermissionCallback;
import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.zhijue.BuildConfig;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.mine.AccountManagerActivity;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.UploadPortraitImageResponseBean;
import com.zj.zhijue.bean.UseInfoBean;
import com.zj.zhijue.bean.event.PortraitUpdateEventBean;
import com.zj.zhijue.config.Constants;
import com.zj.zhijue.glasses.greendao.msdao.UserInfoDBBeanDao;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.helper.TakePhotoCustomHelper;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.http.request.IFileUpload;
import com.zj.zhijue.http.request.IMemberLogin;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.GsonUtil;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.util.view.dropdownmenu.DropBean;
import com.zj.zhijue.util.view.dropdownmenu.DropdownButtonSelectPicture;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static android.app.Activity.RESULT_OK;

public class PersonalInfoFragment extends BaseFragment implements TakePhoto.TakeResultListener, InvokeListener {
    private static final String TAG = PersonalInfoFragment.class.getName();
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private TakePhotoCustomHelper customHelper;

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLayout;

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView titleTextView;

    @BindView(R.id.person_portrait_image)
    ImageView personImageView;

    @BindView(R.id.portraitlaout)
    LinearLayout portraitLayout;

    @BindView(R.id.resetpasswdlayout)
    RelativeLayout resetPasswdLayout;

    @BindView(R.id.resetphonelayout)
    RelativeLayout resetPhoneLayout;

    @BindView(R.id.dropdownbuttonselectpic)
    DropdownButtonSelectPicture dropdownButtonSelectPicture;

    @BindView(R.id.popuwinlayout)
    LinearLayout popuWinLayout;

    @BindView(R.id.accounttv)
    AppCompatTextView accountTextView;

    @BindView(R.id.phonenumtv)
    AppCompatTextView phoneNumTextView;

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAge)
    TextView tvAge;
    @BindView(R.id.tvSex)
    TextView tvSex;
    @BindView(R.id.tvIdCard)
    TextView tvIdCard;

    private List<DropBean> mTrainModeDropBeanList = new ArrayList<>();

    private UserInfoDBBean userInfoDBBean;
    private UseInfoBean useInfoBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        customHelper = TakePhotoCustomHelper.of();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personalinfo_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        initListener();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult：requestCode = " + requestCode + " resultCode = " + resultCode + " data = " + data);
//        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList.size() > 0) {
                        LocalMedia localMedia = selectList.get(0);
                        uploadPortraitImage(localMedia.getCompressPath());
                        MLog.e("=========" + localMedia.getPath() + "----" + localMedia.getCompressPath());
                    }

                    break;
                default:
                    break;
            }
        }
    }

    private void initData() {
        String serverId = Config.getConfig().getUserServerId();
        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), serverId);
        getUseInfo(serverId);
        if (null != userInfoDBBeanList && userInfoDBBeanList.size() > 0) {
            userInfoDBBean = userInfoDBBeanList.get(0);
        }
        if (null == userInfoDBBean && BuildConfig.DEBUG) {
            userInfoDBBean = new UserInfoDBBean();
        }

        String gs = (String) SharedPreferencesUtils.get(Constants.USER_INFO, "");
        if (!TextUtils.isEmpty(gs)) {
            useInfoBean = GsonUtil.GsonToBean(gs, UseInfoBean.class);
        }

    }


    /**
     * 获取个人用户信息
     *
     * @param serverId 用户id
     */
    private void getUseInfo(String serverId) {
        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("id", serverId);
        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                    Log.v(TAG, "newStringDataJson:" + newStringDataJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UseInfoBean useInfo = (UseInfoBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<UseInfoBean>() {
                });

                if (useInfo != null) {
                    updataUserInfo(useInfo);
                }


            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
        TrainSuscribe.getPersonalInfo(headerMap, bodyMap, disposableObserver);

    }

    /**
     * 更新个人用户信息
     *
     * @param useInfo 用户信息
     */
    private void updataUserInfo(UseInfoBean useInfo) {

        if (useInfoBean == null) {
            return;
        }

        String portraitUrl = useInfo.getData().getFace();
        //显示头像
        if (!CommonUtils.isEmpty(portraitUrl)) {
            Glide.with(PersonalInfoFragment.this)
                    .load(com.zj.zhijue.util.CommonUtils.diffAvatar(URLConstant.BASE_URL_FIRST_PARTY, portraitUrl))
                    .apply(new RequestOptions().placeholder(R.mipmap.qidongtubiao).error(R.mipmap.qidongtubiao))
                    .into(personImageView);
        }
        initPopWindow();

        accountTextView.setText(useInfo.getData().getLoginName());
        phoneNumTextView.setText(useInfo.getData().getPhone());

        tvName.setText(useInfo.getData().getName());
        tvAge.setText(String.valueOf(useInfo.getData().getAge()));
        tvSex.setText(useInfo.getData().getSex() == 0 ? "女" : "男");
        tvIdCard.setText(useInfo.getData().getCredentialsCard());

    }

    private void initView(View view) {
        titleTextView.setText("个人信息");


//        if (!CommonUtils.isEmpty(phoneNum) && CommonUtils.verifyPhoneNum(phoneNum)) {
//            String encData = phoneNum.substring(3, 7);
//            //bindPhoneNumTextView.setText(phoneNum.replace(encData, "****"));
//        }
        String guardId = useInfoBean.getData().getGuardian_id();
        if (!CommonUtils.isEmpty(guardId)) {
            List<UserInfoDBBean> guardUserBeanList = UserInfoBeanDaoOpe.queryRawUserInfoByServerID(MyApplication.getInstance(), guardId);
            UserInfoDBBean guardUserBean = guardUserBeanList.get(0);
            //guardTextView.setText(guardUserBean.getName());
        } else {
            //guardTextView.setText("");
        }

        initPopWindow();

    }

    private void initPopWindow() {
        mTrainModeDropBeanList.clear();
        DropBean dropBean1 = new DropBean();
        dropBean1.setName("拍照");
        mTrainModeDropBeanList.add(dropBean1);

        DropBean dropBean2 = new DropBean();
        dropBean2.setName("从相册中选取");
        mTrainModeDropBeanList.add(dropBean2);

        DropBean dropBean3 = new DropBean();
        dropBean3.setName("取消");
        mTrainModeDropBeanList.add(dropBean3);

        dropdownButtonSelectPicture.setRelayView(popuWinLayout);
        dropdownButtonSelectPicture.setData(mTrainModeDropBeanList);
        //dropdownButtonSelectPicture.setVerticalGravity(Gravity.BOTTOM);

        dropdownButtonSelectPicture.setOnDropItemSelectListener(new DropdownButtonSelectPicture.OnDropItemSelectListener() {
            @Override
            public void onDropItemSelect(int Postion) {
                if (0 == Postion) {
//                    initConfig(true);

                    PictureSelector.create(PersonalInfoFragment.this)
                            .openCamera(PictureMimeType.ofImage())
                            .compress(true)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                } else if (1 == Postion) {
//                    initConfig(false);

                    PictureSelector.create(PersonalInfoFragment.this)
                            .openGallery(PictureMimeType.ofImage())
                            .compress(true)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                } else {
                    dropdownButtonSelectPicture.onDismiss();
                }
            }
        });
    }

    protected void initListener() {

        portraitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PermissionUtil.hasPermission(getActivity(), Manifest.permission.CAMERA)
                        && PermissionUtil.hasPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        && PermissionUtil.hasPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    dropdownButtonSelectPicture.performClick();
                } else {
                    final List<String> granterPermissionsList = new ArrayList<>();
                    final List<String> denieyPermissonsList = new ArrayList<>();
                    final String[] permissons = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    com.vise.xsnow.permission.PermissionManager.instance().request(getActivity(), new OnPermissionCallback() {
                        @Override
                        public void onRequestAllow(String permissionName) {
                            MLog.d("onRequestAllow = " + permissionName + " ThreadId = " + Thread.currentThread().getId());
                            granterPermissionsList.add(permissionName);

                            showTipsByGrantedList(permissons, granterPermissionsList, denieyPermissonsList);
                        }

                        @Override
                        public void onRequestRefuse(String permissionName) {
                            denieyPermissonsList.add(permissionName);
                            MLog.d("onRequestRefuse = " + permissionName + " ThreadId = " + Thread.currentThread().getId());
                            showTipsByGrantedList(permissons, granterPermissionsList, denieyPermissonsList);
                        }

                        @Override
                        public void onRequestNoAsk(String permissionName) {
                            MLog.d("onRequestNoAsk = " + permissionName + " ThreadId = " + Thread.currentThread().getId());
                            denieyPermissonsList.add(permissionName);
                            showTipsByGrantedList(permissons, granterPermissionsList, denieyPermissonsList);
                        }
                    }, permissons);
                }
            }
        });

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        resetPasswdLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), AccountManagerActivity.class);
                mIntent.putExtra(AccountManagerActivity.MINE_FRAGMENT_INDEX_KEY, AccountManagerActivity.RESET_PASSWD_FRAGMENT_INDEX);
                startActivity(mIntent);
            }
        });

        resetPhoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG, "takeSuccess：" + result.getImage().getCompressPath());
        //Glide.with(getActivity()).load(new File(result.getImage().getCompressPath())).into(personImageView);
//        uploadPortraitImage(result.getImage().getCompressPath());
    }

    @Override
    public void takeFail(TResult result, String msg) {
        //Log.i(TAG, "takeFail:" + msg);
        ToastUtil.showShort(msg);
    }

    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(org.devio.takephoto.R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    private void showTipsByGrantedList(String[] requestPermissions, List<String> grandedList, List<String> deniedList) {

        if (grandedList.size() + deniedList.size() == requestPermissions.length) {
            if (deniedList.size() == requestPermissions.length) {
                /**
                 * 全部拒绝授权
                 */
                ToastUtil.showShort("相机和读写存储权限被禁用，请在权限管理中开启");
            } else if (deniedList.contains(Manifest.permission.CAMERA)) {

                ToastUtil.showShort("相机权限被禁用，请在权限管理中开启");

            } else if (deniedList.contains(Manifest.permission.READ_EXTERNAL_STORAGE)
                    || deniedList.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ToastUtil.showShort("读写读写存储权限被禁用，请在权限管理中开启");

            } else if (requestPermissions.length == grandedList.size()) {
                dropdownButtonSelectPicture.performClick();
            }
        }
    }

    public void initConfig(boolean takePhoto) {
        customHelper.initConfig(getTakePhoto(), takePhoto);
    }

    private void uploadPortraitImage(final String imagePath) {

        HashMap<String, String> headParaMap = new HashMap<>();
        HashMap<String, RequestBody> partMap = new HashMap<>();

        String memberId = Config.getConfig().getUserServerId();
        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), memberId);
        UserInfoDBBean userInfoDBBean = userInfoDBBeanList.get(0);

        partMap.put(IFileUpload.OWNERID, RequestBody.create(null, memberId));
        partMap.put(IFileUpload.CATEGORY, RequestBody.create(null, IFileUpload.CATEGORY_PORTRAIT_TYPE));
        partMap.put(IFileUpload.UPLOADER, RequestBody.create(null, userInfoDBBean.getLogin_name()));
        partMap.put(IFileUpload.MODULE, RequestBody.create(null, String.valueOf(IMemberLogin.PLATFORM_VALUE_ANDROID)));
        //partMap.put(IFileUpload.VERSION_CODE, RequestBody.create(null, AppUtils.getVersionCode2Str()));

        LoginSubscribe.uploadUserCustomPortraitImage(headParaMap, partMap, imagePath, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String response = null;
                try {
                    response = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //SdLogUtil.writeCommonLog("uploadPortraitImage onNext response = " + response);

                UploadPortraitImageResponseBean uploadPortraitImageResponseBean = (UploadPortraitImageResponseBean) JsonUtil.json2objectWithDataCheck(response, new TypeReference<UploadPortraitImageResponseBean>() {
                });
                //SdLogUtil.writeCommonLog("uploadPortraitImage onNext uploadPortraitImageResponseBean = " + uploadPortraitImageResponseBean);
                String memberId = Config.getConfig().getUserServerId();
                //List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), memberId);
                //UserInfoDBBean userInfoDBBean = userInfoDBBeanList.get(0);
                if (null != uploadPortraitImageResponseBean && uploadPortraitImageResponseBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                    UploadPortraitImageResponseBean.DataBean dataBean = uploadPortraitImageResponseBean.getData();
                    if (null != dataBean && !CommonUtils.isEmpty(dataBean.getFilePath())) {
                        //userInfoDBBean.setPortrait_image_url(dataBean.getFilePath());
                        //SdLogUtil.writeCommonLog(" userInfoDBBean = " + userInfoDBBean);
                        updatePortraitUrl(dataBean.getFilePath(), memberId);
                        String loadUrl = CommonUtils.startWithHttp(dataBean.getFilePath()) ? dataBean.getFilePath() : URLConstant.BASE_URL + dataBean.getFilePath();
                        Glide.with(PersonalInfoFragment.this)
                                .load(String.valueOf(loadUrl))
                                .apply(new RequestOptions().placeholder(R.mipmap.qidongtubiao).error(R.mipmap.qidongtubiao))
                                .into(personImageView);
                        EventBus.getDefault().post(new PortraitUpdateEventBean(true, loadUrl));

                        SPUtils.getInstance().put(SPUtils.KEY_UPLOAD_USER_AVATAR, loadUrl);

                        //SdLogUtil.writeCommonLog(" userInfoDBBean1 = " + UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), memberId).get(0));
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                //SdLogUtil.writeCommonLog("uploadPortraitImage  onError Throwable = " + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void updatePortraitUrl(String portraitUrl, String serverId) {
        List<String> keyList = new ArrayList<>();
        keyList.add(UserInfoDBBeanDao.Properties.Portrait_image_url.columnName);

        List<Object> valueList = new ArrayList<>();
        valueList.add(portraitUrl);
        valueList.add(serverId);
        UserInfoBeanDaoOpe.updateUserInfoByServerId(MyApplication.getInstance(), keyList, valueList);
    }


}
