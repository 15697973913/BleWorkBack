package com.zj.zhijue.dialog.bleconnect;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.zj.zhijue.R;
import com.zj.zhijue.listener.DialogButtonClickListener;
import com.zj.zhijue.dialog.CenterScaleDialog;
import com.zj.zhijue.util.view.ui.DeviceUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 手动输入 设备ID Dialog
 */
public class BleInputDeviceIDDialog extends CenterScaleDialog<BleInputDeviceIDDialog> {
    @BindView(R.id.mainrelativelayout)
    RelativeLayout mainLayout;

    @BindView(R.id.inputdeviceidedit)
    AppCompatEditText deviceIdEditText;

    private int screenWidth;
    private int screenHeight;

    private DialogButtonClickListener mDialogButtonClickListener;

    private Context mContext;

    public BleInputDeviceIDDialog(Context context) {
        super(context);
        setCancelable(false);
        ButterKnife.bind(this);
        mContext = context;
        initView(context);
        resetSize();
        setEidtTextFilter();
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
        return R.layout.dialog_input_deviceid_layout;
    }

    @Override
    public void onCreateData(Context context) {

    }

    public void showTip() {
        show();
    }

    @Override
    public void onClick(View v, int id) {
        switch (id) {
            case R.id.inputdeviceidsurebtn:
                if (!CommonUtils.isEmpty(getInputDeviceIdText())) {
                    callBackButtonClickListener(id);
                    dismiss();
                } else {
                    ToastUtil.showShort("设备ID不能为空！");
                }
                break;

            case R.id.inputdeviceidcancelbtn:
                callBackButtonClickListener(id);
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

    public String getInputDeviceIdText() {
        return deviceIdEditText.getText().toString();
    }

    private void setEidtTextFilter() {
        String singleCharRegex = "^[0-9a-zA-Z:-]";
        /**
         * (?<=exp)/d+(?=exp2)   文本[12]和【56】abcd
         * 要求：         *
         * 找到[]或【】中间的数字
         */
        final String longCharRegex = "([0-9a-zA-Z]+(([?<=(-))[0-9a-zA-Z]+(?=(-)]|[?<=(:))[0-9a-zA-Z]+(?=(:)]){1})?)+";
        final Pattern pattern = Pattern.compile(singleCharRegex);
        final Pattern longPattern = Pattern.compile(longCharRegex);

        deviceIdEditText.setFilters(new InputFilter[] {
            new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                           int dstart, int dend) {
                    Matcher matcher = pattern.matcher(source);
                    if (matcher.find()) {
                        String inputText = deviceIdEditText.getText().toString();
                        //MLog.d("inputText = " + inputText);
                        Matcher longMatcher = longPattern.matcher(inputText + source);
                        if (longMatcher.matches()) {
                            return null;
                        }
                    }
                    return "";
                }
            }

        });
    }
}
