package com.zj.zhijue.dialog.function;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.dialog.CenterScaleDialog;
import com.zj.zhijue.util.view.ui.DeviceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 显示常见问题详情
 */
public class CommonProblemContentDialog extends CenterScaleDialog<CommonProblemContentDialog> {
    @BindView(R.id.mainrelativelayout)
    RelativeLayout mainLayout;

    @BindView(R.id.commonproblem_content_tv)
    AppCompatTextView contentTextView;

    private int screenWidth;
    private int screenHeight;

    private DialogButtonClickListener mDialogButtonClickListener;

    private Context mContext;

    public CommonProblemContentDialog(Context context) {
        super(context);
        setCancelable(true);
        ButterKnife.bind(this);
        mContext = context;
        initView(context);
        resetSize();
    }

    private void initView(Context context) {
        int[] wh = DeviceUtils.getDeviceSize(context);
        screenWidth = wh[0];
        screenHeight = wh[1];
    }

    private void resetSize() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainLayout.getLayoutParams();
        layoutParams.width = screenWidth * 4 / 5;
        layoutParams.height = screenHeight * 2 / 5;
        mainLayout.setLayoutParams(layoutParams);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_common_problem_content_layout;
    }

    @Override
    public void onCreateData(Context context) {

    }

    public void showTip(String content) {
        contentTextView.setText(content);
        show();
    }

    @Override
    public void onClick(View v, int id) {
        callBackButtonClickListener(id);

        switch (id) {
            case R.id.commonproblesurebutton:
                dismiss();
            break;

            default:
                break;
        }
    }

    public void setDialogButtonClickListener(DialogButtonClickListener dialogButtonClickListener) {
        mDialogButtonClickListener = dialogButtonClickListener;
    }

    private void callBackButtonClickListener(int resourceId) {
        if (null != mDialogButtonClickListener) {
            mDialogButtonClickListener.onButtonClick(resourceId);
        }
    }
}
