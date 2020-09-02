package com.zj.zhijue.fragment.systemsettings;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.activity.function.SystemSetttingItemActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * APP 更新
 */
public class AppUpdateFragment extends BaseFragment {

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView appCompatTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLinearLayout;

    @BindView(R.id.downloadingimg)
    ImageView downloadImageView;

    @BindView(R.id.updateprogresstextview)
    AppCompatTextView progressTextView;

    private int fragmentIndex = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system_settings_apkupdate_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        String[]  titleArray = getResources().getStringArray(R.array.system_settings);
        appCompatTextView.setText(titleArray[fragmentIndex]);
        startRotate(downloadImageView);
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

    /**
     * 开始旋转动画
     */
    private void startRotate(ImageView imageView) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_round_rotate);
        LinearInterpolator linearInterpolator = new LinearInterpolator();//设置匀速旋转，在 xml 里面设置会卡顿
        animation.setInterpolator(linearInterpolator);
        if (null != imageView) {
            imageView.startAnimation(animation);
        }
    }

    private void stopImageRotate(ImageView imageView) {
        if (null != imageView) {
            imageView.clearAnimation();
        }
    }

    @Override
    public void onDestroy() {
        stopImageRotate(downloadImageView);
        super.onDestroy();
    }
}
