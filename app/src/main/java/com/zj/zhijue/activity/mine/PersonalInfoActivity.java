package com.zj.zhijue.activity.mine;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zj.zhijue.R;
import com.zj.zhijue.helper.PersonInfoActivityHelper;
import com.android.common.baselibrary.log.MLog;

import butterknife.BindView;

public class PersonalInfoActivity extends MineBaseActivity {
    @BindView(R.id.signincentertextview)
    TextView myTextView;

    @BindView(R.id.backlayout)
    LinearLayout mBackLayout;

    public static final String FRAGMENT_INDEX_KEY = "FRAGMENT_INDEX_KEY";

    private PersonInfoActivityHelper mPersonInfoActivityHelper = null;


    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setNewTheme();
        setContentView(R.layout.activity_personalinfo_layout);
        super.onCreate(bundle);
        initData();
        initView();
        initListener();
    }

    public void setTitleText(int resourceId) {
        myTextView.setText(resourceId);
    }

    protected void initView() {
        super.initView();
        initStatusBar(R.color.function_header_bg);
       // setTitleText(R.string.mine_text);
        myTextView.setTextColor(getResources().getColor(R.color.time_selected_text_color));


        initOperationDropButton();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MLog.d("PersonalInfoActivity onNewIntent()");
        initView();
        initData();
    }

    private void initData() {
        if (null == mPersonInfoActivityHelper) {
            mPersonInfoActivityHelper = new PersonInfoActivityHelper(this);
        }

        Intent mIntent = getIntent();
        if (null != mIntent) {
            int fragmentIndex = mIntent.getIntExtra(FRAGMENT_INDEX_KEY, -1);
            if (fragmentIndex >= 0) {
                mPersonInfoActivityHelper.addFragment(fragmentIndex);
            } else {
                throw new NullPointerException("fragmentIndex can not be null");
            }
        }

    }

    protected void initListener() {
        super.initListener();

        mBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalInfoActivity.this.finish();
            }
        });


    }

    @Override
    protected void handleNotEmptyBundle(Bundle bundle) {
        super.handleNotEmptyBundle(bundle);
        if (null != bundle) {
            FragmentManager manager = getSupportFragmentManager();//重新创建Manager，防止此对象为空
            manager.popBackStackImmediate(null,  FragmentManager.POP_BACK_STACK_INCLUSIVE);//弹出所有fragment
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        PersonalInfoActivity.this.finish();
    }

    public void clickBackLayout() {
        mBackLayout.performClick();
    }
}
