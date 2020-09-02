package com.zj.zhijue.fragment.systemsettings;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.common.baselibrary.util.FileUtils;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.activity.function.SystemSetttingItemActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * APP 版本信息
 */
public class AppVersionFragment extends BaseFragment {

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView appCompatTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLinearLayout;

    @BindView(R.id.apkversiontv)
    AppCompatTextView apkVersionTextView;

    private int fragmentIndex = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system_settings_apkverioninfo_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        String[]  titleArray = getResources().getStringArray(R.array.system_settings);
        appCompatTextView.setText(titleArray[fragmentIndex]);
        apkVersionTextView.setText(getApkVersion());
    }

    private void initData() {
        Bundle mIntent = getArguments();
        if (null != mIntent) {
            fragmentIndex = mIntent.getInt(SystemSetttingItemActivity.FRAGMENT_INDEX_KEY);
        }
    }

    protected void initListener() {
        backLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private String getApkVersion() {
        byte[] apkVersion = FileUtils.readFileContentOfAssets(MyApplication.getInstance(), "commit-msg.txt");
        if (null != apkVersion) {
            return new String(apkVersion);
        }
        return "";
    }
}
