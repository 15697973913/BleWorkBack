package com.zj.zhijue.helper;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zj.zhijue.R;
import com.zj.zhijue.activity.mine.PersonalInfoActivity;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.fragment.MineFragment;
import com.zj.zhijue.fragment.mine.CommonProblemFragment;
import com.zj.zhijue.fragment.mine.ContactCustomerServiceFragment;
import com.zj.zhijue.fragment.mine.FeedBackFragment;
import com.zj.zhijue.fragment.mine.InputOtherDataFragment;
import com.zj.zhijue.fragment.mine.InputReviewDataFragment;
import com.zj.zhijue.fragment.mine.InterveneKeyIntroduceFragment;
import com.zj.zhijue.fragment.mine.PersonalInfoFragment;
import com.zj.zhijue.fragment.mine.ProductIntroduceFragment;
import com.zj.zhijue.fragment.mine.ReviewDataLineChartFragment;
import com.zj.zhijue.fragment.mine.ManagerInputDataFragment;
import com.zj.zhijue.fragment.mine.SystemSettings2Fragment;
import com.zj.zhijue.fragment.mine.SystemSettingsFragment;
import com.zj.zhijue.fragment.mine.TeamFragment;
import com.zj.zhijue.fragment.mine.UserAgreementFragment;
import com.zj.zhijue.fragment.systemsettings.BluetoothLogFragment;

/**
 * Created by Administrator on 2018/11/29.
 */

public class PersonInfoActivityHelper {
    private PersonalInfoActivity mPersonInfoActivity;

    public PersonInfoActivityHelper(PersonalInfoActivity personalInfoActivity) {
        this.mPersonInfoActivity = personalInfoActivity;
    }

    public void addFragment(int fragmentIndex) {
        FragmentManager manager = mPersonInfoActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        switch (fragmentIndex) {

            case MineFragment.REVIEW_DATA_INDEX:
                setTextTitle(R.string.review_data_text);
                fragmentTransaction.add(R.id.mineframelayout, new ReviewDataLineChartFragment());
                break;

            case MineFragment.PRODUCT_INTRODUCE_INDEX:
                setTextTitle(R.string.product_introduce_text);
                fragmentTransaction.add(R.id.mineframelayout, new ProductIntroduceFragment());
                break;

            case MineFragment.INTERVENE_KEY_INTRODUCE_INDEX:
                setTextTitle(R.string.intervene_key_introduce_text);
                fragmentTransaction.add(R.id.mineframelayout, new InterveneKeyIntroduceFragment());
                break;

            case MineFragment.TEAM_INDEX:
                setTextTitle(R.string.string_mine_6);
                fragmentTransaction.add(R.id.mineframelayout, new TeamFragment());
                break;

            case MineFragment.FEEDBACK_INDEX:
                 setTextTitle(R.string.feedback_text);
                 fragmentTransaction.add(R.id.mineframelayout, new FeedBackFragment());
                 break;

            case MineFragment.COMMON_PROBLEM_INDEX:
                setTextTitle(R.string.common_problem_text);
                fragmentTransaction.add(R.id.mineframelayout, new CommonProblemFragment());
                break;

            case MineFragment.CONTACT_CUSTOMER_SERVICE_INDEX:
                setTextTitle(R.string.contact_customer_service_text);
                fragmentTransaction.add(R.id.mineframelayout, new ContactCustomerServiceFragment());
                break;

            case MineFragment.SYSTEM_SETTINGS_KEY:
                setTextTitle(R.string.system_settings_text);
                fragmentTransaction.add(R.id.mineframelayout, new SystemSettings2Fragment());
                break;
            case MineFragment.BLUETOOTHLOG_INDEX:
                setTextTitle(R.string.system_settings_text);
                fragmentTransaction.add(R.id.mineframelayout, new BluetoothLogFragment());
                break;

            case MineFragment.MANAGER_INPUT_DATA_SETTINGS_KEY://录入数据主界面
                setTextTitle(R.string.input_data_text);
                fragmentTransaction.add(R.id.mineframelayout, new ManagerInputDataFragment());
                break;

            case MineFragment.INPUT_REVIEW_DATA_INDEX://录入复查数据
                setTextTitle(R.string.review_input_data_text);
                fragmentTransaction.add(R.id.mineframelayout, new InputReviewDataFragment());
                break;

            case MineFragment.INPUT_OTHER_DATA_INDEX://录入其他数据
                setTextTitle(R.string.input_other_data_text);
                fragmentTransaction.add(R.id.mineframelayout, new InputOtherDataFragment());
                break;

            case MineFragment.PERSONAL_INFO_INDEX:
                setTextTitle(R.string.personal_info_text);
                fragmentTransaction.add(R.id.mineframelayout, new PersonalInfoFragment());

                break;

            case  MineFragment.USER_AGREEMENT_INDEX:
                setTextTitle(R.string.user_agreement_text);
                fragmentTransaction.add(R.id.mineframelayout, new UserAgreementFragment());
                break;
            default:
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    private void setTextTitle(int resourceId) {
        mPersonInfoActivity.setTitleText(resourceId);
    }


}
