package com.zj.zhijue.dialog.register;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zj.zhijue.R;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.dialog.CenterScaleDialog;
import com.zj.zhijue.util.view.ui.DeviceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 注册结果提示(成功，失败)Dialog
 */
public class RegisterTipDialog extends CenterScaleDialog<RegisterTipDialog> {
    @BindView(R.id.mainrelativelayout)
    RelativeLayout mainLayout;

    @BindView(R.id.register_tipimage)
    ImageView registerTipTitleImageView;

    @BindView(R.id.register_tiptitletext)
    AppCompatTextView registerTipTextView;

    @BindView(R.id.register_success_center_layout)
    LinearLayout registerSuccessLayout;

    @BindView(R.id.register_success_accounttext)
    AppCompatTextView registerSuccessAccountTextView;

    @BindView(R.id.register_faile_center_layout)
    LinearLayout registerFaileLayout;

    @BindView(R.id.register_sure_btn)
    Button registerSureButton;

    @BindView(R.id.register_sure_fail_btn)
    Button registerSureFailButton;

    private int screenWidth;
    private int screenHeight;

    private DialogButtonClickListener mDialogButtonClickListener;

    private Context mContext;

    public RegisterTipDialog(Context context) {
        super(context);
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
        return R.layout.dialog_register_tip_layout;
    }

    @Override
    public void onCreateData(Context context) {

    }

    public void showTip(boolean registerSuccess, String newLoginName) {
        if (registerSuccess) {
            showSuccessView(newLoginName);
            setCancelable(false);
        } else {
            showFailView();
            setCancelable(true);
        }
        show();
    }

    private void showSuccessView(String newLoginName) {
        registerSuccessLayout.setVisibility(View.VISIBLE);
        //registerTipTitleImageView.setImageResource(R.drawable.register_success);
        registerTipTextView.setText(mContext.getResources().getText(R.string.register_success_tip_title));
        registerSuccessAccountTextView.setText(newLoginName);
        registerFaileLayout.setVisibility(View.GONE);
        registerSureFailButton.setVisibility(View.GONE);
        registerSureButton.setVisibility(View.VISIBLE);
    }

    private void showFailView() {
        registerSuccessLayout.setVisibility(View.GONE);
        //registerTipTitleImageView.setImageResource(R.drawable.register_fail);
        registerTipTextView.setText(mContext.getResources().getText(R.string.register_faile_tip_title));
        registerFaileLayout.setVisibility(View.VISIBLE);
        registerSureFailButton.setVisibility(View.VISIBLE);
        registerSureButton.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v, int id) {
        callBackButtonClickListener(id);

        switch (id) {

            case R.id.register_sure_btn:
                dismiss();
                break;

            case R.id.register_sure_fail_btn:
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
