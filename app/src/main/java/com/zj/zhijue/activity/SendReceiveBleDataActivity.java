package com.zj.zhijue.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.common.baselibrary.blebean.BaseCmdBean;
import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.log.BleDataLog;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.BleHexUtil;
import com.android.common.baselibrary.util.DateUtil;
import com.android.common.baselibrary.util.FileUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseBleActivity;
import com.zj.zhijue.bean.bledata.BleDataBeanConvertUtil;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesFeedbackBleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam1BleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam2BleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam3BleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam4BleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam5BleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesUserIdBleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveInteveneFeedbackBleDataBean;
import com.zj.zhijue.bean.bledata.send.SendForGetCommonFeedBackDataBean;
import com.zj.zhijue.bean.bledata.send.SendGlassesQueryUserIdBleCmdBeaan;
import com.zj.zhijue.bean.bledata.send.SendMachineBleCmdBeaan;
import com.zj.zhijue.bean.bledata.send.SendUserInfoBleCmdBean;
import com.zj.zhijue.bean.bledata.send.SendUserInfoControlBleCmdBean;
import com.zj.zhijue.bean.response.HttpResponseGlassesRunParamBean;
import com.zj.zhijue.ble.BleOptHelper;
import com.zj.zhijue.contracts.BleMainControlContract;

import com.zj.zhijue.dialog.bleconnect.BleDataLogDialog;
import com.zj.zhijue.event.CmdBleDataEvent;
import com.zj.zhijue.event.RunParamDataEvent;
import com.zj.zhijue.model.GlassesBleDataModel;
import com.zj.zhijue.presenter.BleMainControlPresenter;
import com.zj.zhijue.service.ForegroundService;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.jsonutil.GsonTools;

import com.zj.zhijue.event.CallbackDataEvent;
import com.zj.zhijue.event.ConnectEvent;
import com.zj.zhijue.event.EventConstant;
import com.zj.zhijue.event.NotifyDataEvent;
import com.vise.baseble.model.BluetoothLeDevice;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/11/12.
 */

public class SendReceiveBleDataActivity extends BaseBleActivity implements BleMainControlContract.View {

    @BindView(R.id.connecteddevicetextview)
    TextView connectedTextView;

    @BindView(R.id.disconnectbutton)
    Button disconnectButton;

    @BindView(R.id.useridedittext)
    EditText userIdEditText;

    @BindView(R.id.ageedittext)
    EditText ageEditText;

    @BindView(R.id.lefteyedegreeedittext)
    EditText lefteyeDegreeEditText;

    @BindView(R.id.righteyedegreeedittext)
    EditText righteyeDegreeEditText;

    @BindView(R.id.runmodeeditext)
    EditText runModeEditText;

    @BindView(R.id.eyestatus)
    Spinner eyeSpinner;

    @BindView(R.id.blestatus)
    Spinner bleSpinner;

    @BindView(R.id.maxrunnumberedit)
    EditText maxRunNumberEdit;

    @BindView(R.id.Lens)
    EditText Lens;

    @BindView(R.id.NewUser)
    EditText NewUser;

    @BindView(R.id.startspeededit)
    EditText startSppedEdit;

    @BindView(R.id.setspeedincedit)
    EditText setSpeedIncEdit;

    @BindView(R.id.stopspeedcedit)
    EditText stopSpeedEdit;

    @BindView(R.id.CommonSpeededit)
    EditText CommonSpeededitedit;

    @BindView(R.id.machinedata6cedit)
    EditText machineData6cedit;

    @BindView(R.id.machinedata7cedit)
    EditText machineData7cedit;

    @BindView(R.id.machinedata8cedit)
    EditText machineData8cedit;

    @BindView(R.id.machinedata9cedit)
    EditText machineData9cedit;

    @BindView(R.id.elecrunspeed)
    EditText elecRunModeEditText;

    @BindView(R.id.sendedittext)
    EditText sendEditText;

    @BindView(R.id.receivetextview)
    TextView receiveTextView;

    @BindView(R.id.receivejsontextview)
    TextView receiveJonsTextView;

    @BindView(R.id.receiveintervenejsontextview)
    TextView receiveInterveneTextView;

    @BindView(R.id.receiveIdjsontextview)
    TextView receiveJSONIDTextView;

    @BindView(R.id.sendcallbacktextview)
    TextView sendCallBackTextView;

    @BindView(R.id.getuseridbtn)
    Button getUserIDBtn;

    @BindView(R.id.sendbutton)
    Button sendButton;

    @BindView(R.id.sendmachinebutton)
    Button sendMachineButton;

    @BindView(R.id.sendcommondatarequestbtn)
    Button sendCommondataRequestbtn;

    @BindView(R.id.sendrunparambtn)
    Button sendRunParamBtn;

    @BindView(R.id.copysenddata)
    Button copySendDataButton;

    @BindView(R.id.searchbutton)
    Button searchBleButton;

    /**
     * 运行参数
     * 开始
     *
     */
    @BindView(R.id.minminusinterval)
    EditText minminusinterval;

    @BindView(R.id.minplusinterval)
    EditText minplusinterval;

    @BindView(R.id.commonnumber)
    EditText commonnumber;

    @BindView(R.id.interveneaccminute)
    EditText interveneaccminute;

    @BindView(R.id.weekkeyfre)
    EditText weekkeyfre;

    @BindView(R.id.weekAccMinute)
    EditText weekAccMinute;

    @BindView(R.id.backWeekAccMinute0)
    EditText backWeekAccMinute0;

    @BindView(R.id.backWeekAccMinute1)
    EditText backWeekAccMinute1;

    @BindView(R.id.backWeekAccMinute2)
    EditText backWeekAccMinute2;

    @BindView(R.id.backWeekAccMinute3)
    EditText backWeekAccMinute3;

    @BindView(R.id.plusInterval)
    EditText plusInterval;

    @BindView(R.id.minusInterval)
    EditText minusInterval;

    @BindView(R.id.plusInc)
    EditText plusInc;

    @BindView(R.id.minusInc)
    EditText minusInc;

    @BindView(R.id.incPer)
    EditText incPer;

    @BindView(R.id.runNumber)
    EditText runNumber;

    @BindView(R.id.runSpeed)
    EditText runSpeed;

    @BindView(R.id.speedInc)
    EditText speedInc;

    @BindView(R.id.speedSegment)
    EditText speedSegment;

    @BindView(R.id.intervalSegment)
    EditText intervalSegment;

    @BindView(R.id.backSpeedSegment)
    EditText backSpeedSegment;

    @BindView(R.id.backIntervalSegment)
    EditText backIntervalSegment;

    @BindView(R.id.speedKeyFre)
    EditText speedKeyFre;

    @BindView(R.id.interveneKeyFre)
    EditText interveneKeyFre;

    @BindView(R.id.intervalAccMinute)
    EditText intervalAccMinute;

    @BindView(R.id.minusInterval2)
    EditText minusInterval2;

    @BindView(R.id.plusInterval2)
    EditText plusInterval2;

    @BindView(R.id.minusInc2)
    EditText minusInc2;

    @BindView(R.id.plusInc2)
    EditText plusInc2;

    @BindView(R.id.incPer2)
    EditText incPer2;

    @BindView(R.id.runNumber2)
    EditText runNumber2;

    @BindView(R.id.runSpeed2)
    EditText runSpeed2;

    @BindView(R.id.speedSegment2)
    EditText speedSegment2;

    @BindView(R.id.speedInc2)
    EditText speedInc2;

    @BindView(R.id.intervalSegment2)
    EditText intervalSegment2;

    @BindView(R.id.backSpeedSegment2)
    EditText backSpeedSegment2;

    @BindView(R.id.backIntervalSegment2)
    EditText backIntervalSegment2;

    @BindView(R.id.speedKeyFre2)
    EditText speedKeyFre2;

    @BindView(R.id.interveneKeyFre2)
    EditText interveneKeyFre2;

    @BindView(R.id.intervalAccMinute2)
    EditText intervalAccMinute2;

    @BindView(R.id.txByte4)
    EditText rxByte4;

    @BindView(R.id.txByte5)
    EditText rxByte5;

    @BindView(R.id.txByte6)
    EditText rxByte6;

    @BindView(R.id.txByte7)
    EditText rxByte7;

    @BindView(R.id.txByte8)
    EditText rxByte8;

    @BindView(R.id.txByte9)
    EditText rxByte9;

    @BindView(R.id.txByte10)
    EditText rxByte10;

    @BindView(R.id.txByte11)
    EditText rxByte11;

    @BindView(R.id.txByte12)
    EditText rxByte12;

    @BindView(R.id.txByte13)
    EditText rxByte13;

    @BindView(R.id.txByte14)
    EditText rxByte14;

    @BindView(R.id.txByte15)
    EditText rxByte15;

    @BindView(R.id.txByte16)
    EditText rxByte16;

    @BindView(R.id.txByte17)
    EditText rxByte17;

    @BindView(R.id.txByte18)
    EditText rxByte18;

    @BindView(R.id.txByte19)
    EditText rxByte19;

    /**
     * 运行参数
     * 结束
     *
     */

    @BindView(R.id.startbtn)
    Button startButton;

    @BindView(R.id.pausebtn)
    Button pauseButton;

    @BindView(R.id.continuebtn)
    Button continueButton;

    @BindView(R.id.stopbtn)
    Button stopButton;

    @BindView(R.id.intevenebtn)
    Button inteveneButton;

    @BindView(R.id.powerbtn)
    Button powerButton;

    @BindView(R.id.sendrecevivebtn)
    Button sendReceiveBtn;

    @BindView(R.id.showbledatalogbtn)
    Button showBleDataLogBtn;

    @BindView(R.id.deletebledatabtn)
    Button deleteBleDataLogBtn;

    @BindView(R.id.receiverunparamtimetv)
    TextView receiveRunParamTimeTextView;

    @BindView(R.id.receiverunparamtv)
    TextView receiveRunParamTextView;

    @BindView(R.id.receiverunparamjsontv)
    TextView receiveRunparamJsonTextViiew;

    private BleDataLogDialog bleDataLogDialog;


    private BluetoothLeDevice selectedGlassBluetoothLeDevice;
    private BluetoothLeDevice selectedLightBluetoothLeDevice;

    private BleMainControlContract.Presenter presenter;

    public static final String BLUETOOTH_DEVICE_KEY = "BLUETOOTH_DEVICE_KEY";
    public static final String BLUETOOTH_DEVICE_TYPE_KEY = "BLUETOOTH_DEVICE_TYPE_KEY";

    private final String PROTOCAL_START = "a2";
    private final String PROTOCAL_ID_0_1 = "0001";
    private String PROTOCAL_AGE_2 = "";
    private String PROTOCAL_EYE_DEGREE_3_4 = "";
    private final String PROTOCAL_CURRENT_REGION_5 = "00";
    private final String PROTOCAL_CURRENT_SPEED_6 = "00";
    private final String PROTOCAL_CURRENT_FREQ_7 = "00";
    private final String PROTOCAL_TRAIN_TOTAL_TIME_8_9 = "0000";
    private String PROTOCAL_RUN_MODE_10 = "11";
    private final String PROTOCAL_POWER_11 = "00";
    private final String PROTOCAL_MEAS_DISTANCE_12 = "00";
    private String PROTOCAL_SETPDELAY_13_14 = "0180";

    private int eyeStatusPosition = -1;
    private int bleStatusPostion = -1;

    private boolean disconnectByManual = false;

    public static final String INPUT_FILE_NAME = "machine_data";
    public static final String INPUT_USER_INFO_FILE_NAME = "userinfo_data";
    public static final String INPUT_RUN_PARAM_INFO_FILE_NAME = "edit_run_param_info_data";
    private SendUserInfoBleCmdBean sendUserInfoBleCmdBean = null;
    private HttpResponseGlassesRunParamBean httpResponseGlassesRunParamBean = null;


    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setNewTheme();
        setContentView(R.layout.activity_send_receive_layout);
        super.onCreate(bundle);
        initData();
        createPresenter();
        //connectBleGlasses();
        restoreLastUserInfoData();
        initView(false);
        initListener();
        registerEventBus();
        restoreLastMachineData();
        restoreRunParamInfo();
        startForgroundService();
    }

    private void initView(boolean isConnecting) {
        boolean isConnected = presenter.isBleGlassesConnected();
        String deviceName = null;
        String address = null;
        if (null != selectedGlassBluetoothLeDevice) {
             deviceName = selectedGlassBluetoothLeDevice.getDevice().getName();
             address = selectedGlassBluetoothLeDevice.getDevice().getAddress();
        } else {
            deviceName = Config.getConfig().getLastConnectBleGlassesName();
            address = Config.getConfig().getLastConnectBleGlassesMac();
        }

        //SdLogUtil.writeCommonLog("initView() 蓝牙眼镜selectedGlassBluetoothLeDevice = " + selectedGlassBluetoothLeDevice);
        if (isConnected) {
            connectedTextView.setText("" + deviceName + "[" + address + "] 已连接");
            disconnectButton.setVisibility(View.VISIBLE);
            BleOptHelper.bindWriteChannel(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
            BleOptHelper.bindNotifyChannel(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
        } else {
            isConnecting = presenter.isBleGlassesConnecting();
            if (isConnecting) {
                connectedTextView.setText("" + deviceName + "[" + address + "] 蓝牙连接中");
                disconnectButton.setVisibility(View.VISIBLE);
            } else {
                connectedTextView.setText("" + deviceName + "[" + address + "] 未连接");
            }
        }

        initEyeSpinner();
        initBleSpinner();
    }

    private void setReceiveTextViewContent(String eventType, byte[] bleData) {
        if (null != bleData) {
            String bleDataStr = new String(bleData);
             displayData(receiveTextView, bleData);
             showFeedBackJson(bleData);
            //receiveTextView.setText("[EventType = " + eventType +"]" +  bytesToString(bleData));
             //receiveTextView.setText("[EventType = " + eventType +"]" + "[ bleData.length = " + bleData.length + "]" +   bytesToString(bleData));
        } else {
            receiveTextView.setText("[EventType = " + eventType +"]" + null);
        }
    }

    private void showFeedBackJson(byte[] bleData) {
        ReceiveGlassesFeedbackBleDataBean receiveGlassesFeedbackBleDataBean = new ReceiveGlassesFeedbackBleDataBean();
        receiveGlassesFeedbackBleDataBean.parseBleDataByteArray2Bean(bleData);

        ReceiveInteveneFeedbackBleDataBean receiveInteveneFeedbackBleDataBean = new ReceiveInteveneFeedbackBleDataBean();
        receiveInteveneFeedbackBleDataBean.parseBleDataByteArray2Bean(bleData);

        ReceiveGlassesUserIdBleDataBean receiveGlassesUserIdBleDataBean = new ReceiveGlassesUserIdBleDataBean();
        receiveGlassesUserIdBleDataBean.parseBleDataByteArray2Bean(bleData);

        if (receiveGlassesFeedbackBleDataBean.isParseSuccess()) {
            receiveJonsTextView.setText("[" + DateUtil.localformatter.format(new Date(System.currentTimeMillis())) + "]" + receiveGlassesFeedbackBleDataBean.toString());
        }else if (receiveInteveneFeedbackBleDataBean.isParseSuccess()) {
            receiveInterveneTextView.setText("[" + DateUtil.localformatter.format(new Date(System.currentTimeMillis())) + "]" + receiveInteveneFeedbackBleDataBean.toString());
        }else if (receiveGlassesUserIdBleDataBean.isParseSuccess()) {
            receiveJSONIDTextView.setText("[" + DateUtil.localformatter.format(new Date(System.currentTimeMillis())) + "]" + receiveGlassesUserIdBleDataBean.toString());
        } else {
            receiveJonsTextView.setText("[" + DateUtil.localformatter.format(new Date(System.currentTimeMillis())) + "]未知JSON");
        }
    }

    private void sendCallBackTextViewContent(String eventType, byte[] bleData) {
        if (null != bleData) {
            String bleDataStr = new String(bleData);
            sendCallBackTextView.setText("[ bleData.length = " + bleData.length + "]" +   bytesToString(bleData));
            //displayData(sendCallBackTextView, bleData);
        } else {
            sendCallBackTextView.setText("[EventType = " + eventType +"]" + null);
        }
    }

    private void initListener() {
        sendEditText.setEnabled(false);
        sendEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sendEditText.setText(getAllData2Send());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                limitAgeEditText();
                sendEditText.setText(getAllData2Send());
            }
        });

        lefteyeDegreeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sendEditText.setText(getAllData2Send());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sendEditText.setText(getAllData2Send());
            }
        });

        runModeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sendEditText.setText(getAllData2Send());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sendEditText.setText(getAllData2Send());
            }
        });

        elecRunModeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sendEditText.setText(getAllData2Send());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sendEditText.setText(getAllData2Send());
            }
        });

        getUserIDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!handleDisconnectNotShow()) {
                    return;
                }
                GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
                SendGlassesQueryUserIdBleCmdBeaan sendGlassesQueryUserIdBleCmdBeaan = new SendGlassesQueryUserIdBleCmdBeaan();
                presenter.sendGlassesBleData(sendGlassesQueryUserIdBleCmdBeaan.buildCmdByteArray());
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!handleDisconnectNotShow()) {
                    return;
                }
                clearStorStatus();
                getAllData2Send();
                String allData = getUserInfoBean(true).getSourceData();
                //allData = "a200010b05dc00000000001100000180";
                sendEditText.setText(allData);
                //SdLogUtil.writeCommonLog("sendButton " + allData);
                //byte[] bleDataArray = stringConvertBleDataArray(allData);
               // String realData =  bytesToString(bleDataArray);
                //SdLogUtil.writeCommonLog("bleDataArray bleDataArray" + bleDataArray);
                //发送数据
                presenter.sendGlassesBleData(getUserInfo());
            }
        });

        sendMachineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!handleDisconnectNotShow()) {
                    return;
                }
                SendMachineBleCmdBeaan sendMachineBleCmdBeaan = new SendMachineBleCmdBeaan();
                long currentTime = System.currentTimeMillis();
                String[] yearMonth = DateUtil.localformatterDay.format(new Date(currentTime)).split("-");
                String[] hourMinut = DateUtil.hourMinuteSecondFormat.format(new Date(currentTime)).split("\\:");
                sendMachineBleCmdBeaan.setYear(Integer.parseInt(yearMonth[0]));
                sendMachineBleCmdBeaan.setMonth(Integer.parseInt(yearMonth[1]));
                sendMachineBleCmdBeaan.setDay(Integer.parseInt(yearMonth[2]));

                sendMachineBleCmdBeaan.setHour(Integer.parseInt(hourMinut[0]));
                sendMachineBleCmdBeaan.setMinute(Integer.parseInt(hourMinut[1]));
                sendMachineBleCmdBeaan.setSeconds(Integer.parseInt(hourMinut[2]));

                sendMachineBleCmdBeaan.setMaxRunNumber(getMaxRunNumber() & 0xff);
                sendMachineBleCmdBeaan.setStartSpeed(getStartSpeed() & 0xff);
                sendMachineBleCmdBeaan.setSetSpeedInc(getSpeedInc() & 0xff);
                sendMachineBleCmdBeaan.setStopSpeed(getStopSpeed() & 0xff);
                sendMachineBleCmdBeaan.setCommonSpeed(getMachineData5() & 0xff);
                sendMachineBleCmdBeaan.setMachineData6(getMachineData6() & 0xff);
                sendMachineBleCmdBeaan.setMachineData7(getMachineData7() & 0xff);
                sendMachineBleCmdBeaan.setMachineData8(getMachineData8() & 0xff);
                sendMachineBleCmdBeaan.setMachineData9(getMachineData9() & 0xff);

                presenter.sendGlassesBleData(sendMachineBleCmdBeaan.buildCmdByteArray());

            }
        });

        sendRunParamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] runByteArray = getRunParamResponseByteArrayFromEdit();//TrainModel.getInstance().getLastGlassesRunningParams();
                if (null == runByteArray) {
                    ToastUtil.showShort("没有读取到存储的运行参数数据");
                } else  {
                    if (!handleDisconnectNotShow()) {
                        return;
                    }
                    presenter.sendGlassesBleData(runByteArray);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("眼镜运行参数\n");
                    for (int i = 0; i < runByteArray.length / 20; i++) {
                        byte[] tmpArray = new byte[20];
                        System.arraycopy(runByteArray, i* 20, tmpArray, 0, 20);
                        stringBuilder.append(BaseParseCmdBean.bytesToString(tmpArray) + "\n");
                    }
                    MLog.d("sendRunParam stringBuilder = " + stringBuilder);
                    ToastUtil.showLong(stringBuilder.toString());
                }
            }
        });

        copySendDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendData = getAllData2Send();
                sendEditText.setText(sendData);
                copyTextByNative(sendData);
                ToastUtil.showShortToast("已复制：" + sendData);
            }
        });

        searchBleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(SendReceiveBleDataActivity.this, BleSearchActivity.class);
                startActivityForResult(mIntent, 1200);
            }
        });

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnectByManual = true;

                presenter.disconnectGlassesBle(true);
                receiveTextView.setText("");
                sendCallBackTextView.setText("");
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 开始
                 */
                if (!handleDisconnectNotShow()) {
                    return;
                }
                GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
                EventBus.getDefault().post(new CmdBleDataEvent(getStartActionOrder()));

            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 暂停
                 */
                if (!handleDisconnectNotShow()) {
                    return;
                }
                GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
                EventBus.getDefault().post(new CmdBleDataEvent(getPauseActionOrder()));
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 继续
                 */
                if (!handleDisconnectNotShow()) {
                    return;
                }
                GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
                EventBus.getDefault().post(new CmdBleDataEvent(getContinueOrder()));
            }
        });


        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 停止
                 */
                if (!handleDisconnectNotShow()) {
                    return;
                }
                GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
                EventBus.getDefault().post(new CmdBleDataEvent(getStopActionOrder()));
            }
        });


        inteveneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 干预
                 */
                if (!handleDisconnectNotShow()) {
                    return;
                }
                GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
                EventBus.getDefault().post(new CmdBleDataEvent(getInterveneOrder()));
            }
        });

        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 电源
                 */
                if (!handleDisconnectNotShow()) {
                    return;
                }
                GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
                EventBus.getDefault().post(new CmdBleDataEvent(getCloseBleDeviceBattery()));
            }
        });

        sendReceiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendReceiveParam1();
                sendReceiveParam2();
                sendReceiveParam3();
                sendReceiveParam4();
                sendReceiveParam5();
            }
        });


        /**
         * 查看蓝牙日志文件
         */
        showBleDataLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bleLogPath = BleDataLog.getLogDirPath() + BleDataLog.getCommonLogFileName();
                String content = FileUtils.readTextFile(new File(bleLogPath));
                if (!CommonUtils.isEmpty(content)) {
                    int textLength = content.length();
                    if (textLength > 1024 * 1024) {
                        showBleDataLogDialog(content.substring(textLength - 1024 * 1024) + "\n" + "已经到底了（日志文件写入较慢，可以关闭之后，再次打开查看，可能会有新数据写入，日志文件的读写顺序可能会出现颠倒的情况）");
                    } else {
                        showBleDataLogDialog(content + "\n" + "已经到底了（日志文件写入较慢，可以关闭之后，再次打开查看，可能会有新数据写入，日志文件的读写顺序可能会出现颠倒的情况）");
                    }
                } else {
                    showBleDataLogDialog("没有数据");
                }
                //ToastUtil.showShort("content = " + content);
            }
        });

        deleteBleDataLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bleLogPath = BleDataLog.getLogDirPath() + BleDataLog.getCommonLogFileName();
                File bleDataFilee = new File(bleLogPath);
                try {
                   boolean deletBleDataLogFile = bleDataFilee.delete();
                   MLog.d("deletBleDataLogFile = " + deletBleDataLogFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        sendCommondataRequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 发送指令，接收实时反馈的数据
                 */
                if (!handleDisconnectNotShow()) {
                    return;
                }
                GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
                EventBus.getDefault().post(new CmdBleDataEvent(getSendCommonFeedBackRequestCmd()));
            }
        });
    }

    private void sendReceiveParam1() {
        NotifyDataEvent runParamDataEvent = new NotifyDataEvent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ReceiveGlassesRunParam1BleDataBean.USER_MONITOR_CMD);
        for (int i = 0; i< 16; i++) {
            stringBuilder.append(BaseCmdBean.decimalism2Hex(i, 2));
        }
        stringBuilder.append("aa");
        byte[] param1 = BleHexUtil.getRealSendData(stringBuilder.toString());
        runParamDataEvent.setData(param1);
        runParamDataEvent.setDeviceType(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
        EventBus.getDefault().post(runParamDataEvent);
    }


    private void sendReceiveParam2() {
        NotifyDataEvent runParamDataEvent = new NotifyDataEvent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ReceiveGlassesRunParam2BleDataBean.USER_MONITOR_CMD);
        for (int i = 0; i< 16; i++) {
            stringBuilder.append(BaseCmdBean.decimalism2Hex(i, 2));
        }
        stringBuilder.append("aa");
        byte[] param2 = BleHexUtil.getRealSendData(stringBuilder.toString());
        runParamDataEvent.setData(param2);
        runParamDataEvent.setDeviceType(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
        EventBus.getDefault().post(runParamDataEvent);

    }

    private void sendReceiveParam3() {
        NotifyDataEvent runParamDataEvent = new NotifyDataEvent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ReceiveGlassesRunParam3BleDataBean.USER_MONITOR_CMD);
        for (int i = 0; i< 16; i++) {
            stringBuilder.append(BaseCmdBean.decimalism2Hex(i, 2));
        }
        stringBuilder.append("aa");
        byte[] param3 = BleHexUtil.getRealSendData(stringBuilder.toString());
        runParamDataEvent.setData(param3);
        runParamDataEvent.setDeviceType(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
        EventBus.getDefault().post(runParamDataEvent);

    }

    private void sendReceiveParam4() {
        NotifyDataEvent runParamDataEvent = new NotifyDataEvent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ReceiveGlassesRunParam4BleDataBean.USER_MONITOR_CMD);
        for (int i = 0; i< 16; i++) {
            stringBuilder.append(BaseCmdBean.decimalism2Hex(i, 2));
        }
        stringBuilder.append("aa");

        byte[] param4 = BleHexUtil.getRealSendData(stringBuilder.toString());
        runParamDataEvent.setData(param4);
        runParamDataEvent.setDeviceType(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
        EventBus.getDefault().post(runParamDataEvent);

    }

    private void sendReceiveParam5() {
        NotifyDataEvent runParamDataEvent = new NotifyDataEvent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ReceiveGlassesRunParam5BleDataBean.USER_MONITOR_CMD);
        for (int i = 0; i< 16; i++) {
            stringBuilder.append(BaseCmdBean.decimalism2Hex(i, 2));
        }
        stringBuilder.append("aa");

        byte[] param5 = BleHexUtil.getRealSendData(stringBuilder.toString());
        runParamDataEvent.setData(param5);
        runParamDataEvent.setDeviceType(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
        EventBus.getDefault().post(runParamDataEvent);

    }

    private byte[] getSendCommonFeedBackRequestCmd() {
        /*SendReceiveGlassesRunParamBleConfirmDataBean receiveGlassesRunParamBleConfirmDataBean = new SendReceiveGlassesRunParamBleConfirmDataBean();
        receiveGlassesRunParamBleConfirmDataBean.setMonitorData_CMD("00");*/
        SendForGetCommonFeedBackDataBean sendForGetCommonFeedBackDataBean = new SendForGetCommonFeedBackDataBean();
        sendForGetCommonFeedBackDataBean.setMonitorData_CMD("00");
        byte[] requestFeedBack = sendForGetCommonFeedBackDataBean.buildCmdByteArray();
        return requestFeedBack;
    }

    private String getSendData() {
        return sendEditText.getText().toString();
    }

    private void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void unRegisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initData() {
        Intent mIntent = getIntent();
        if (null != mIntent) {
            int bluetoothDeviceType = mIntent.getIntExtra(BLUETOOTH_DEVICE_TYPE_KEY, -1);
            if (EventConstant.GLASS_BLUETOOTH_EVENT_TYPE == bluetoothDeviceType) {
                selectedGlassBluetoothLeDevice = mIntent.getParcelableExtra(BLUETOOTH_DEVICE_KEY);
                selectedLightBluetoothLeDevice = null;
            } else if (EventConstant.LIGHT_BLUETOOTH_EVENT_TYPE == bluetoothDeviceType) {
                selectedLightBluetoothLeDevice = mIntent.getParcelableExtra(BLUETOOTH_DEVICE_KEY);
                selectedGlassBluetoothLeDevice = null;
            } else {
                selectedGlassBluetoothLeDevice = null;
                selectedLightBluetoothLeDevice = null;
            }
        } else {
            selectedGlassBluetoothLeDevice = null;
            selectedLightBluetoothLeDevice = null;
        }
    }

    private void createPresenter() {
        setPresenter(new BleMainControlPresenter(getRespository(), this));
    }

    private void connectBleGlasses() {
        if (null != presenter && null != selectedGlassBluetoothLeDevice) {
            presenter.connectBleGlasses(selectedGlassBluetoothLeDevice);
        } else {
            showToastMsg("蓝牙眼镜连接失败！");
        }
    }

    private void connectBleGlassesByMac() {
        if (null != presenter && null != selectedGlassBluetoothLeDevice) {
            presenter.connectBleGlassesByMac(selectedGlassBluetoothLeDevice.getAddress());
        } else {
            showToastMsg("蓝牙眼镜连接失败！");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveBleDeviceConnnectStatus(ConnectEvent connectEvent) {
        dismissLoading();

        if (null == connectEvent) {
            ToastUtil.showShort("连接异常");
            return;
        }
        int deviceType = connectEvent.getDeviceType();
        //SdLogUtil.writeCommonLog("onReceiveBleDeviceConnnectStatus deviceType = " + deviceType);
        disconnectButton.setVisibility(View.GONE);
        receiveTextView.setText("");
        sendCallBackTextView.setText("");
        
        if (connectEvent.isSuccess()) {
            disconnectByManual = false;
            initView(false);
            //MLog.d("onConnectEvent connect success");
            ToastUtil.showShort("连接成功");
            disconnectButton.setVisibility(View.VISIBLE);
            BleOptHelper.bindWriteChannel(connectEvent.getDeviceType());
            BleOptHelper.bindNotifyChannel(connectEvent.getDeviceType());
        } else if (connectEvent.isDisconnected()) {
            //MLog.d("onConnectEvent disconnect success");
            ToastUtil.showShort( "断开连接");

            if (!disconnectByManual && null != selectedGlassBluetoothLeDevice) {
               /* showCustomSmallToasDialg("自动重连中");
                connectBleGlassesByMac();*/
                //connectBleGlasses();
                initView(true);
            } else {
                initView(false);
            }

        } else {
            // MLog.d("onConnectEvent connect failure");
            //ToastUtils.showShort((CharSequence) "连接失败");
            ToastUtils.showShort( "连接失败");
            if (!disconnectByManual && null != selectedGlassBluetoothLeDevice) {
                /*showCustomSmallToasDialg("自动重连中");
                connectBleGlassesByMac();*/
                //connectBleGlasses();
                initView(true);
            } else {
                initView(false);
            }


        }

    }

    /**
     * 接收数据的事件监听
     * @param notifyDataEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveBleDevicResponseStatus(NotifyDataEvent notifyDataEvent) {
        if (null != notifyDataEvent) {
            int deviceType = notifyDataEvent.getDeviceType();
            //SdLogUtil.writeCommonLog("onReceiveBleDevicResponseStatus deviceType = "+ "[EventType = " + deviceType +"]" + "[ notifyDataEvent.getData().length = " + notifyDataEvent.getData().length + "]" +   bytesToString(notifyDataEvent.getData()));
            if (EventConstant.GLASS_BLUETOOTH_EVENT_TYPE == deviceType) {
                setReceiveTextViewContent("NotifyDataEvent", notifyDataEvent.getData());
            } else if (EventConstant.LIGHT_BLUETOOTH_EVENT_TYPE == deviceType) {
                setReceiveTextViewContent("NotifyDataEvent", notifyDataEvent.getData());
            }
        }
    }

    /**
     * 接收运行参数的数据的事件监听
     * @param runParamDataEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveBleRunParamData(RunParamDataEvent runParamDataEvent) {
        if (null != runParamDataEvent) {
            byte[] runByteArray = runParamDataEvent.getData();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < runByteArray.length / 20; i++) {
                byte[] tmpArray = new byte[20];
                System.arraycopy(runByteArray, i* 20, tmpArray, 0, 20);
                stringBuilder.append(BaseParseCmdBean.bytesToStringWithSpace(tmpArray) + "\n");
            }
            MLog.d("RunParamDataEvent stringBuilder = " + stringBuilder.toString());
            receiveRunParamTextView.setText(stringBuilder.toString());
            receiveRunparamJsonTextViiew.setText(runParamDataEvent.getBeanJson());
            receiveRunParamTimeTextView.setText("[" + DateUtil.localformatter.format(new Date(System.currentTimeMillis())) + "]");
        }

    }



    /**
     * 操作数据事件的反馈监听
     * @param callbackDataEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveBleDevicSendCallBackStatus(CallbackDataEvent callbackDataEvent) {
        if (null != callbackDataEvent) {
            int deviceType = callbackDataEvent.getDeviceType();
            //SdLogUtil.writeCommonLog("onReceiveBleDevicSendCallBackStatus deviceType = " + deviceType);
            if (EventConstant.GLASS_BLUETOOTH_EVENT_TYPE == deviceType) {
                sendCallBackTextViewContent("CallbackDataEvent", callbackDataEvent.getData());
            } else if (EventConstant.LIGHT_BLUETOOTH_EVENT_TYPE == deviceType) {
                sendCallBackTextViewContent("CallbackDataEvent",callbackDataEvent.getData());
            }
        }
    }

    private void connectGlassesBle(BluetoothLeDevice bluetoothLeDeviceParam) {
        presenter.connectBleGlasses(bluetoothLeDeviceParam);
    }

    private void connectLightBle(BluetoothLeDevice bluetoothLeDeviceParam) {
        presenter.connectBleLight(bluetoothLeDeviceParam);
    }


    @Override
    public void setPresenter(BleMainControlContract.Presenter Presenter) {
        this.presenter = Presenter;
    }

    @Override
    public void showToastMsg(String tipMsg) {

    }

    @Override
    public void showToastMsg(int resourceId) {

    }

    @Override
    public void changeBleGlassesConnectStatus(boolean startAnimation) {

    }

    @Override
    public void changeBleLightConnectStatus(boolean startAnimation) {

    }

    @Override
    public void setStatusText(String statusText) {

    }

    @Override
    public void setBleGlassesBatteryText(String bleGlassesBatteryText) {

    }

    @Override
    public void setBleLightBatteryText(String bleLightBatteryText) {

    }

    @Override
    public void setSelectedScenarioTextView(String selectedScenarioText) {

    }

    @Override
    public void setTrainModeText(String trainModeText) {

    }

    @Override
    public void setUserName(String userName) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        saveRunParamsData();
        saveMachineData();
        saveUserInfoData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dimissBleDataDialog();
        unRegisterEventBus();
    }

    private void saveRunParamsData() {
        HttpResponseGlassesRunParamBean runParamResonseBean = createRunParamResonseBean();
        String jsonn = GsonTools.createGsonString(runParamResonseBean);
        MLog.d(" jsonn = " + jsonn);
        FileUtils.write(this, INPUT_RUN_PARAM_INFO_FILE_NAME, jsonn);
    }

    private void saveMachineData() {
        SendMachineBleCmdBeaan sendMachineBleCmdBeaan = new SendMachineBleCmdBeaan();
        sendMachineBleCmdBeaan.setMaxRunNumber(getMaxRunNumber() & 0xff);
        sendMachineBleCmdBeaan.setStartSpeed(getStartSpeed() & 0xff);
        sendMachineBleCmdBeaan.setSetSpeedInc(getSpeedInc() & 0xff);
        sendMachineBleCmdBeaan.setStopSpeed(getStopSpeed() & 0xff);
        sendMachineBleCmdBeaan.setCommonSpeed(getMachineData5() & 0xff);
        sendMachineBleCmdBeaan.setMachineData6(getMachineData6() & 0xff);
        sendMachineBleCmdBeaan.setMachineData7(getMachineData7() & 0xff);
        sendMachineBleCmdBeaan.setMachineData8(getMachineData8() & 0xff);
        sendMachineBleCmdBeaan.setMachineData9(getMachineData9() & 0xff);
        String jsonn = GsonTools.createGsonString(sendMachineBleCmdBeaan);
        FileUtils.write(this, INPUT_FILE_NAME, jsonn);
    }

    private void saveUserInfoData() {
        SendUserInfoBleCmdBean sendMachineBleCmdBeaan = getUserInfoBean(false);
        String jsonn = GsonTools.createGsonString(sendMachineBleCmdBeaan);
        MLog.d(" jsonn = " + jsonn);
        FileUtils.write(this, INPUT_USER_INFO_FILE_NAME, jsonn);
    }

    private void restoreRunParamInfo() {
        String json = FileUtils.read(this, INPUT_RUN_PARAM_INFO_FILE_NAME);
        if (!CommonUtils.isEmpty(json)) {
            httpResponseGlassesRunParamBean = GsonTools.changeGsonToBean(json, HttpResponseGlassesRunParamBean.class);
            if (null != httpResponseGlassesRunParamBean) {
                minminusinterval.setText(String.valueOf(httpResponseGlassesRunParamBean.getMinMinusInterval()));
                minplusinterval.setText(String.valueOf(httpResponseGlassesRunParamBean.getMinPlusInterval()));
                commonnumber.setText(String.valueOf(httpResponseGlassesRunParamBean.getCommonNumber()));
                interveneaccminute.setText(String.valueOf(httpResponseGlassesRunParamBean.getInterveneAccMinute()));
                weekkeyfre.setText(String.valueOf(httpResponseGlassesRunParamBean.getWeekKeyFre()));
                weekAccMinute.setText(String.valueOf(httpResponseGlassesRunParamBean.getWeekAccMinute()));
                backWeekAccMinute0.setText(String.valueOf(httpResponseGlassesRunParamBean.getBackWeekAccMinute0()));
                backWeekAccMinute1.setText(String.valueOf(httpResponseGlassesRunParamBean.getBackWeekAccMinute1()));
                backWeekAccMinute2.setText(String.valueOf(httpResponseGlassesRunParamBean.getBackWeekAccMinute2()));
                backWeekAccMinute3.setText(String.valueOf(httpResponseGlassesRunParamBean.getBackWeekAccMinute3()));

                plusInterval.setText(String.valueOf(httpResponseGlassesRunParamBean.getPlusInterval()));
                minusInterval.setText(String.valueOf(httpResponseGlassesRunParamBean.getMinusInterval()));
                plusInc.setText(String.valueOf(httpResponseGlassesRunParamBean.getPlusInc()));
                minusInc.setText(String.valueOf(httpResponseGlassesRunParamBean.getMinusInc()));
                incPer.setText(String.valueOf(httpResponseGlassesRunParamBean.getIncPer()));
                runNumber.setText(String.valueOf(httpResponseGlassesRunParamBean.getRunNumber()));

                runSpeed.setText(String.valueOf(httpResponseGlassesRunParamBean.getRunSpeed()));
                speedInc.setText(String.valueOf(httpResponseGlassesRunParamBean.getSpeedInc()));
                speedSegment.setText(String.valueOf(httpResponseGlassesRunParamBean.getSpeedSegment()));
                intervalSegment.setText(String.valueOf(httpResponseGlassesRunParamBean.getIntervalSegment()));
                backSpeedSegment.setText(String.valueOf(httpResponseGlassesRunParamBean.getBackSpeedSegment()));
                backIntervalSegment.setText(String.valueOf(httpResponseGlassesRunParamBean.getBackIntervalSegment()));

                speedKeyFre.setText(String.valueOf(httpResponseGlassesRunParamBean.getSpeedKeyFre()));
                interveneKeyFre.setText(String.valueOf(httpResponseGlassesRunParamBean.getInterveneKeyFre()));
                intervalAccMinute.setText(String.valueOf(httpResponseGlassesRunParamBean.getIntervalAccMinute()));
                minusInterval2.setText(String.valueOf(httpResponseGlassesRunParamBean.getMinusInterval2()));
                plusInterval2.setText(String.valueOf(httpResponseGlassesRunParamBean.getPlusInterval2()));
                minusInc2.setText(String.valueOf(httpResponseGlassesRunParamBean.getMinusInc2()));

                plusInc2.setText(String.valueOf(httpResponseGlassesRunParamBean.getPlusInc2()));
                incPer2.setText(String.valueOf(httpResponseGlassesRunParamBean.getIncPer2()));
                runNumber2.setText(String.valueOf(httpResponseGlassesRunParamBean.getRunNumber2()));
                runSpeed2.setText(String.valueOf(httpResponseGlassesRunParamBean.getRunSpeed2()));

                speedSegment2.setText(String.valueOf(httpResponseGlassesRunParamBean.getSpeedSegment2()));
                speedInc2.setText(String.valueOf(httpResponseGlassesRunParamBean.getSpeedInc2()));
                intervalSegment2.setText(String.valueOf(httpResponseGlassesRunParamBean.getIntervalSegment2()));
                backSpeedSegment2.setText(String.valueOf(httpResponseGlassesRunParamBean.getBackSpeedSegment2()));
                backIntervalSegment2.setText(String.valueOf(httpResponseGlassesRunParamBean.getBackIntervalSegment2()));
                speedKeyFre2.setText(String.valueOf(httpResponseGlassesRunParamBean.getSpeedKeyFre2()));
                interveneKeyFre2.setText(String.valueOf(httpResponseGlassesRunParamBean.getInterveneKeyFre2()));
                intervalAccMinute2.setText(String.valueOf(httpResponseGlassesRunParamBean.getIntervalAccMinute2()));

                rxByte4.setText(String.valueOf(httpResponseGlassesRunParamBean.getTrainingState()));
                rxByte5.setText(String.valueOf(httpResponseGlassesRunParamBean.getTrainingState2()));
                rxByte6.setText(String.valueOf(httpResponseGlassesRunParamBean.getAdjustSpeed()));
                rxByte7.setText(String.valueOf(httpResponseGlassesRunParamBean.getMaxRunSpeed()));
                rxByte8.setText(String.valueOf(httpResponseGlassesRunParamBean.getMinRunSpeed()));
                rxByte9.setText(String.valueOf(httpResponseGlassesRunParamBean.getAdjustSpeed2()));
                rxByte10.setText(String.valueOf(httpResponseGlassesRunParamBean.getMaxRunSpeed2()));
                rxByte11.setText(String.valueOf(httpResponseGlassesRunParamBean.getMinRunSpeed2()));
                rxByte12.setText(String.valueOf(httpResponseGlassesRunParamBean.getTxByte12()));
                rxByte13.setText(String.valueOf(httpResponseGlassesRunParamBean.getTxByte13()));
                rxByte14.setText(String.valueOf(httpResponseGlassesRunParamBean.getTxByte14()));
                rxByte15.setText(String.valueOf(httpResponseGlassesRunParamBean.getTxByte15()));
                rxByte16.setText(String.valueOf(httpResponseGlassesRunParamBean.getTxByte16()));
                rxByte17.setText(String.valueOf(httpResponseGlassesRunParamBean.getTxByte17()));
                rxByte18.setText(String.valueOf(httpResponseGlassesRunParamBean.getTxByte18()));
                rxByte19.setText(String.valueOf(httpResponseGlassesRunParamBean.getTxByte19()));
            }
        }
    }

    private void restoreLastUserInfoData() {
        String json = FileUtils.read(this, INPUT_USER_INFO_FILE_NAME);
        if (!CommonUtils.isEmpty(json)) {
            sendUserInfoBleCmdBean = GsonTools.changeGsonToBean(json, SendUserInfoBleCmdBean.class);
            if (null != sendUserInfoBleCmdBean) {
                userIdEditText.setText(String.valueOf(sendUserInfoBleCmdBean.getUserId()));
                ageEditText.setText(String.valueOf(sendUserInfoBleCmdBean.getUserAge()));
                lefteyeDegreeEditText.setText(String.valueOf(sendUserInfoBleCmdBean.getUserLeftEyeSightDegree()));
                righteyeDegreeEditText.setText(String.valueOf(sendUserInfoBleCmdBean.getUserRightSightDegree()));
                Lens.setText(String.valueOf(sendUserInfoBleCmdBean.getUserLens()));
                NewUser.setText(String.valueOf(sendUserInfoBleCmdBean.getNewUser()));
               /* eyeSpinner.setSelection(sendUserInfoBleCmdBean.getUserEyeSightType() - 1, true);
                bleSpinner.setSelection(sendUserInfoBleCmdBean.getUserTrainMode() -1, true);*/
            }
        }
    }

    private void restoreLastMachineData() {
        String json = FileUtils.read(this, INPUT_FILE_NAME);
        if (!CommonUtils.isEmpty(json)) {
            SendMachineBleCmdBeaan sendMachineBleCmdBeaan = GsonTools.changeGsonToBean(json, SendMachineBleCmdBeaan.class);
            if (null != sendMachineBleCmdBeaan) {
                maxRunNumberEdit.setText(String.valueOf(sendMachineBleCmdBeaan.getMaxRunNumber()));
                startSppedEdit.setText(String.valueOf(sendMachineBleCmdBeaan.getStartSpeed()));
                setSpeedIncEdit.setText(String.valueOf(sendMachineBleCmdBeaan.getSetSpeedInc()));
                stopSpeedEdit.setText(String.valueOf(sendMachineBleCmdBeaan.getStopSpeed()));

                CommonSpeededitedit.setText(String.valueOf(sendMachineBleCmdBeaan.getCommonSpeed()));
                machineData6cedit.setText(String.valueOf(sendMachineBleCmdBeaan.getMachineData6()));
                machineData7cedit.setText(String.valueOf(sendMachineBleCmdBeaan.getMachineData7()));
                machineData8cedit.setText(String.valueOf(sendMachineBleCmdBeaan.getMachineData8()));
                machineData9cedit.setText(String.valueOf(sendMachineBleCmdBeaan.getMachineData9()));
            }

        }

    }

    private byte[] stringConvertBleDataArray(String bleStr) {
        return bleStr.getBytes();
    }

    private String getAllData2Send() {
        PROTOCAL_AGE_2 = BaseCmdBean.decimalism2Hex(getAgeEditTextContent(), 2);
        PROTOCAL_EYE_DEGREE_3_4 = BaseCmdBean.decimalism2Hex(getLeftEyeDegreeTextContent(), 4);
        PROTOCAL_RUN_MODE_10 = getRunModeEditTextContent();
        PROTOCAL_SETPDELAY_13_14 = BaseCmdBean.decimalism2Hex(getElecSpeedEditTextContent(), 4);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PROTOCAL_START);
        stringBuilder.append(PROTOCAL_ID_0_1);
        stringBuilder.append(PROTOCAL_AGE_2);
        stringBuilder.append(PROTOCAL_EYE_DEGREE_3_4);
        stringBuilder.append(PROTOCAL_CURRENT_REGION_5);
        stringBuilder.append(PROTOCAL_CURRENT_SPEED_6);
        stringBuilder.append(PROTOCAL_CURRENT_FREQ_7);
        stringBuilder.append(PROTOCAL_TRAIN_TOTAL_TIME_8_9);
        stringBuilder.append(PROTOCAL_RUN_MODE_10);
        stringBuilder.append(PROTOCAL_POWER_11);
        stringBuilder.append(PROTOCAL_MEAS_DISTANCE_12);
        stringBuilder.append(PROTOCAL_SETPDELAY_13_14);
        return stringBuilder.toString();
    }

    private String getUserIDTextContent() {
        String idStr = userIdEditText.getText().toString();
        if (CommonUtils.isEmpty(idStr)) {
            return String.valueOf(0);
        }
        return idStr;
    }

    private String getAgeEditTextContent() {
        String agetSTr = ageEditText.getText().toString();
        if (CommonUtils.isEmpty(agetSTr)) {
            return String.valueOf(0);
        }
        return agetSTr;
    }

    private String getLeftEyeDegreeTextContent() {

        String leftEyeDegreeStr = lefteyeDegreeEditText.getText().toString();
        if (CommonUtils.isEmpty(leftEyeDegreeStr)) {
            return String.valueOf(0);
        }
        return leftEyeDegreeStr;
    }

    private String getRightEyeDegreeTextContent() {

        String rightEyeDegreeStr = righteyeDegreeEditText.getText().toString();
        if (CommonUtils.isEmpty(rightEyeDegreeStr)) {
            return String.valueOf(0);
        }
        return rightEyeDegreeStr;
    }

    private String getRunModeEditTextContent() {
        return runModeEditText.getText().toString();
    }

    private String getElecSpeedEditTextContent() {
        return elecRunModeEditText.getText().toString();
    }
    private void initEyeSpinner(){
        //原始string数组
        final String[] spinnerItems = getResources().getStringArray(R.array.eyestatus);
        //简单的string数组适配器：样式res，数组
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, spinnerItems);
        //下拉的样式res
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        eyeSpinner.setAdapter(spinnerAdapter);
        //选择监听
        eyeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //parent就是父控件spinner
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
            /*    LogUtil.i("onItemSelected : parent.id="+parent.getId()+
                        ",isSpinnerId="+(parent.getId() == R.id.spinner_1)+
                        ",viewid="+view.getId()+ ",pos="+pos+",id="+id);*/
                //ToastDialogUtil.showShort(instance,"选择了["+spinnerItems[pos]+"]");
                //设置spinner内的填充文字居中
                //((TextView)view).setGravity(Gravity.CENTER);
                eyeStatusPosition = pos;
                setRunModeText(eyeStatusPosition, bleStatusPostion);
                //ToastDialogUtil.showShortToast(""+spinnerItems[pos]+"");

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        int position = 0;
        if (null != sendUserInfoBleCmdBean) {
            position = sendUserInfoBleCmdBean.getUserEyeSightType() - 1;

            if (position < 0) {
                position = 0;
            }
            MLog.d("eyeSpinner postion = " + position);
            if (position > 3) {
                position = 3;
            }
        }

        eyeSpinner.setSelection(position, true);
    }

    private void initBleSpinner(){
        //原始string数组
        final String[] spinnerItems = getResources().getStringArray(R.array.blestatus);
        //简单的string数组适配器：样式res，数组
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, spinnerItems);
        //下拉的样式res
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        bleSpinner.setAdapter(spinnerAdapter);
        //选择监听
        bleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //parent就是父控件spinner
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
            /*    LogUtil.i("onItemSelected : parent.id="+parent.getId()+
                        ",isSpinnerId="+(parent.getId() == R.id.spinner_1)+
                        ",viewid="+view.getId()+ ",pos="+pos+",id="+id);*/
                //ToastDialogUtil.showShort(instance,"选择了["+spinnerItems[pos]+"]");
                //设置spinner内的填充文字居中
                //((TextView)view).setGravity(Gravity.CENTER);
                bleStatusPostion = pos;
                setRunModeText(eyeStatusPosition, bleStatusPostion);
                //ToastDialogUtil.showShortToast(""+spinnerItems[pos]+"");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        int position = 1;
       if (null != sendUserInfoBleCmdBean) {
            position = sendUserInfoBleCmdBean.getUserTrainMode() - 1;

            if (position < 0) {
                position = 0;
            }
            MLog.d("bleSpinner postion = " + position);
            if (position > 2) {
                position = 2;
            }
        }

        bleSpinner.setSelection(position, true);
    }

    private void setRunModeText(int eyeposition, int blepostion) {
        runModeEditText.setText("" + (eyeposition + 1) + (blepostion));
        sendEditText.setText(getAllData2Send());
    }

    public void copyTextByNative(String content) {
        Context context = getApplicationContext();
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        //创建ClipData对象
        ClipData clipData = ClipData.newPlainText("Label", content);
        //添加ClipData对象到剪切板中
        clipboardManager.setPrimaryClip(clipData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //SdLogUtil.writeCommonLog("onActivityResult resultCode = " + resultCode + " resultCode = " + resultCode );
        receiveTextView.setText("");
        sendCallBackTextView.setText("");
        disconnectByManual = false;
        if (requestCode == 1200 && null != data) {
            if (resultCode == 0) {
                selectedGlassBluetoothLeDevice =  data.getParcelableExtra(BleMainControlActivity.BLUETOOTH_DEVICE_KEY);
                if (null != selectedGlassBluetoothLeDevice) {
                    //ToastDialogUtil.showShortToast("正在连接");

                    //连接成功(正在连接)
                    connectGlassesBle(selectedGlassBluetoothLeDevice);
                    //SdLogUtil.writeCommonLog("onActivityResult " + selectedGlassBluetoothLeDevice);
                    selectedLightBluetoothLeDevice = null;
                } else {
                    ToastUtil.showShortToast("连接失败，请重新扫描");
                }
            } else if (resultCode == 1){
                //未搜索到
                ToastUtil.showShortToast("未搜索到设备");
            }

        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    private void limitAgeEditText() {
        String ageContent = getAgeEditTextContent();
        if (!CommonUtils.isEmpty(ageContent)) {
            int age = Integer.parseInt(ageContent);
            if (age > 0xff) {
                age = 0xff;
                ageEditText.setText(String.valueOf(age));
            }
        }
    }

    public String bytesToString(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];

            sb.append(hexChars[i * 2]);
            sb.append(hexChars[i * 2 + 1]);
            sb.append(' ');
        }
        return sb.toString();
    }


    private StringBuilder mData = new StringBuilder();
    long recv_cnt = 0;

    private void displayData(TextView textView , byte[] buf) {
        mData.setLength(0);
        recv_cnt = buf.length;

        if (recv_cnt >= 1024) {
            recv_cnt = 0;
            mData.delete(0, mData.length() / 2); //UI界面只保留512个字节，免得APP卡顿
        }

        String s = bytesToString(buf);
        mData.append(s);
        textView.setText("[len=" + recv_cnt + "]" + mData.toString());
    }

    private void clearStorStatus() {
        mData.delete(0, mData.length());
        recv_cnt = 0;
    }

    /**
     * 处理连接成功之后，没有绑定的问题
     */
    private boolean handleDisconnectNotShow() {
        if (presenter.isBleGlassesConnected()) {
            if (disconnectButton.getVisibility() != View.VISIBLE) {
                initView(false);
            }
            return true;
        } else {
            if (disconnectButton.getVisibility() == View.VISIBLE) {
                disconnectButton.setVisibility(View.GONE);
                initView(false);
            }
            ToastUtil.showShortToast("蓝牙未连接，请扫描选择蓝牙设备！");
            return false;
        }
    }

    private byte[] getUserInfo() {
        //SdLogUtil.writeCommonLog("getUserInfo sourceData =  " + getUserInfoBean(true).getSourceData());
        return getUserInfoBean(true).buildCmdByteArray();
    }

    private SendUserInfoBleCmdBean getUserInfoBean(boolean neenBuildByteArray) {
        SendUserInfoBleCmdBean sendUserInfoBleCmdBean = new SendUserInfoBleCmdBean();

        sendUserInfoBleCmdBean.setUserId(Integer.parseInt(getUserIDTextContent()));
        sendUserInfoBleCmdBean.setUserAge(Integer.parseInt(getAgeEditTextContent()));
        sendUserInfoBleCmdBean.setUserLeftEyeSightDegree(Integer.parseInt(getLeftEyeDegreeTextContent()));
        sendUserInfoBleCmdBean.setUserRightSightDegree(Integer.parseInt(getRightEyeDegreeTextContent()));
        sendUserInfoBleCmdBean.setUserEyeSightType(getEyeSightType());
        sendUserInfoBleCmdBean.setUserTrainMode(getTrainMode());
        sendUserInfoBleCmdBean.setNewUser(getNewUser());
        sendUserInfoBleCmdBean.setUserLens(getLens());
        if (neenBuildByteArray) {
            sendUserInfoBleCmdBean.buildCmdByteArray();
        }
        return sendUserInfoBleCmdBean;
    }

    private int getEyeSightType() {
       return eyeSpinner.getSelectedItemPosition() + 1;
    }

    private int getTrainMode() {
        return bleSpinner.getSelectedItemPosition() + 1;
    }

    private int getNewUser() {
        String maxRunNumber = NewUser.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getLens() {
        String maxRunNumber = Lens.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getMaxRunNumber() {
        String maxRunNumber = maxRunNumberEdit.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getStartSpeed() {
        String maxRunNumber = startSppedEdit.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getSpeedInc() {
        String maxRunNumber = setSpeedIncEdit.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getStopSpeed() {
        String maxRunNumber = stopSpeedEdit.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getMachineData5() {
        String maxRunNumber = CommonSpeededitedit.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getMachineData6() {
        String maxRunNumber = machineData6cedit.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getMachineData7() {
        String maxRunNumber = machineData7cedit.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getMachineData8() {
        String maxRunNumber = machineData8cedit.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getMachineData9() {
        String maxRunNumber = machineData9cedit.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    /**
     *     private byte[] getRunParamResponseByteArrayFromEdit() {
     *         SparseArray<BaseCmdBean> baseCmdBeanArray = BleDataBeanConvertUtil.httpResponseBleDataBean2BleCmdBean(createRunParamResonseBean());
     *         byte[] retByteArray = new byte[baseCmdBeanArray.size() * 20];
     *
     *         for (int i = 0; i < baseCmdBeanArray.size(); i++) {
     *             if (i ==  0) {
     *                 SendGlassesRunParam1BleCmdBeaan baseCmdBean = (SendGlassesRunParam1BleCmdBeaan)baseCmdBeanArray.get(i);
     *                 byte[] sendByte = baseCmdBean.buildCmdByteArray();
     *                 MLog.d("getLastGlassesRunningParams[" + i + "]" + BaseParseCmdBean.bytesToStringWithSpace(sendByte));
     *                 System.arraycopy(retByteArray, i * 20, sendByte, 0, 20);
     *             } else if (i == 1) {
     *                 SendGlassesRunParam2BleCmdBeaan baseCmdBean = (SendGlassesRunParam2BleCmdBeaan)baseCmdBeanArray.get(i);
     *                 byte[] sendByte = baseCmdBean.buildCmdByteArray();
     *                 MLog.d("getLastGlassesRunningParams[" + i + "]" + BaseParseCmdBean.bytesToStringWithSpace(sendByte));
     *                 System.arraycopy(retByteArray, i * 20, sendByte, 0, 20);
     *             } else if (i == 2) {
     *                 SendGlassesRunParam3BleCmdBeaan baseCmdBean = (SendGlassesRunParam3BleCmdBeaan)baseCmdBeanArray.get(i);
     *                 byte[] sendByte = baseCmdBean.buildCmdByteArray();
     *                 MLog.d("getLastGlassesRunningParams[" + i + "]" + BaseParseCmdBean.bytesToStringWithSpace(sendByte));
     *                 System.arraycopy(retByteArray, i * 20, sendByte, 0, 20);
     *             } else if (i == 3) {
     *                 SendGlassesRunParam4BleCmdBeaan baseCmdBean = (SendGlassesRunParam4BleCmdBeaan)baseCmdBeanArray.get(i);
     *                 byte[] sendByte = baseCmdBean.buildCmdByteArray();
     *                 MLog.d("getLastGlassesRunningParams[" + i + "]" + BaseParseCmdBean.bytesToStringWithSpace(sendByte));
     *                 System.arraycopy(retByteArray, i * 20, sendByte, 0, 20);
     *             }
     *
     *
     *         }
     *         return retByteArray;
     *     }
     * @return
     */

    private byte[] getRunParamResponseByteArrayFromEdit() {
        SparseArray<BaseCmdBean> baseCmdBeanArray = BleDataBeanConvertUtil.httpResponseBleDataBean2BleCmdBean(createRunParamResonseBean());
        byte[] retByteArray = new byte[baseCmdBeanArray.size() * 20];

        for (int i = 0; i < baseCmdBeanArray.size(); i++) {
            BaseCmdBean baseCmdBean = baseCmdBeanArray.get(i);
            byte[] sendByte = baseCmdBean.buildCmdByteArray();
            MLog.d("getLastGlassesRunningParams[" + i + "]" + BaseParseCmdBean.bytesToStringWithSpace(sendByte));
            //System.arraycopy(retByteArray, i * 20, sendByte, 0, 20);
            System.arraycopy(sendByte, 0, retByteArray, i * 20, 20);
        }

        MLog.d("retByteArrayStr =  " + BaseParseCmdBean.bytesToStringWithSpace(retByteArray));
        return retByteArray;
    }

    private HttpResponseGlassesRunParamBean createRunParamResonseBean() {
        HttpResponseGlassesRunParamBean httpResponseGlassesRunParamBean = new HttpResponseGlassesRunParamBean();
        httpResponseGlassesRunParamBean.setMinMinusInterval(getminminusinterval());
        httpResponseGlassesRunParamBean.setMinPlusInterval(getminplusinterval());
        httpResponseGlassesRunParamBean.setCommonNumber(getcommonnumber());
        httpResponseGlassesRunParamBean.setInterveneAccMinute(getinterveneaccminute());
        httpResponseGlassesRunParamBean.setWeekKeyFre(getweekkeyfre());
        httpResponseGlassesRunParamBean.setWeekAccMinute(getweekAccMinute());
        httpResponseGlassesRunParamBean.setBackWeekAccMinute0(getbackWeekAccMinute0());
        httpResponseGlassesRunParamBean.setBackWeekAccMinute1(getbackWeekAccMinute1());
        httpResponseGlassesRunParamBean.setBackWeekAccMinute2(getbackWeekAccMinute2());
        httpResponseGlassesRunParamBean.setBackWeekAccMinute3(getbackWeekAccMinute3());
        httpResponseGlassesRunParamBean.setPlusInterval(getplusInterval());
        httpResponseGlassesRunParamBean.setMinusInterval(getminusInterval());
        httpResponseGlassesRunParamBean.setPlusInc(getplusInc());
        httpResponseGlassesRunParamBean.setMinusInc(getminusInc());
        httpResponseGlassesRunParamBean.setIncPer(getincPer());
        httpResponseGlassesRunParamBean.setRunNumber(getrunNumber());
        httpResponseGlassesRunParamBean.setRunSpeed(getrunSpeed());
        httpResponseGlassesRunParamBean.setSpeedInc(getspeedInc());
        httpResponseGlassesRunParamBean.setSpeedSegment(getspeedSegment());
        httpResponseGlassesRunParamBean.setIntervalSegment(getintervalSegment());
        httpResponseGlassesRunParamBean.setBackSpeedSegment(getbackSpeedSegment());
        httpResponseGlassesRunParamBean.setBackIntervalSegment(getbackIntervalSegment());
        httpResponseGlassesRunParamBean.setSpeedKeyFre(getspeedKeyFre());
        httpResponseGlassesRunParamBean.setInterveneKeyFre(getinterveneKeyFre());
        httpResponseGlassesRunParamBean.setIntervalAccMinute(getintervalAccMinute());
        httpResponseGlassesRunParamBean.setMinusInterval2(getminusInterval2());
        httpResponseGlassesRunParamBean.setPlusInterval2(getplusInterval2());
        httpResponseGlassesRunParamBean.setMinusInc2(getminusInc2());
        httpResponseGlassesRunParamBean.setPlusInc2(getplusInc2());
        httpResponseGlassesRunParamBean.setIncPer2(getincPer2());
        httpResponseGlassesRunParamBean.setRunNumber2(getrunNumber2());
        httpResponseGlassesRunParamBean.setRunSpeed2(getrunSpeed2());
        httpResponseGlassesRunParamBean.setSpeedSegment2(getspeedSegment2());
        httpResponseGlassesRunParamBean.setSpeedInc2(getspeedInc2());
        httpResponseGlassesRunParamBean.setIntervalSegment2(getintervalSegment2());
        httpResponseGlassesRunParamBean.setBackSpeedSegment2(getbackSpeedSegment2());
        httpResponseGlassesRunParamBean.setBackIntervalSegment2(getbackIntervalSegment2());
        httpResponseGlassesRunParamBean.setSpeedKeyFre2(getspeedKeyFre2());
        httpResponseGlassesRunParamBean.setInterveneKeyFre2(getinterveneKeyFre2());
        httpResponseGlassesRunParamBean.setIntervalAccMinute2(getintervalAccMinute2());


        httpResponseGlassesRunParamBean.setTrainingState(getRxByte4());
        httpResponseGlassesRunParamBean.setTrainingState2(getRxByte5());
        httpResponseGlassesRunParamBean.setAdjustSpeed(getRxByte6());
        httpResponseGlassesRunParamBean.setMaxRunSpeed(getRxByte7());
        httpResponseGlassesRunParamBean.setMinRunSpeed(getRxByte8());
        httpResponseGlassesRunParamBean.setAdjustSpeed2(getRxByte9());
        httpResponseGlassesRunParamBean.setMaxRunSpeed2(getRxByte10());
        httpResponseGlassesRunParamBean.setMinRunSpeed2(getRxByte11());
        httpResponseGlassesRunParamBean.setTxByte12(getRxByte12());
        httpResponseGlassesRunParamBean.setTxByte13(getRxByte13());
        httpResponseGlassesRunParamBean.setTxByte14(getRxByte14());
        httpResponseGlassesRunParamBean.setTxByte15(getRxByte15());
        httpResponseGlassesRunParamBean.setTxByte16(getRxByte16());
        httpResponseGlassesRunParamBean.setTxByte17(getRxByte17());
        httpResponseGlassesRunParamBean.setTxByte18(getRxByte18());
        httpResponseGlassesRunParamBean.setTxByte19(getRxByte19());

        return httpResponseGlassesRunParamBean;
    }

    /**
     * 运行参数
     */

    private int getminminusinterval() {
        String maxRunNumber = minminusinterval.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getminplusinterval() {
        String maxRunNumber = minplusinterval.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getcommonnumber() {
        String maxRunNumber = commonnumber.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getinterveneaccminute() {
        String maxRunNumber = interveneaccminute.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getweekkeyfre() {
        String maxRunNumber = weekkeyfre.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getweekAccMinute() {
        String maxRunNumber = weekAccMinute.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getbackWeekAccMinute0() {
        String maxRunNumber = backWeekAccMinute0.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getbackWeekAccMinute1() {
        String maxRunNumber = backWeekAccMinute1.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getbackWeekAccMinute2() {
        String maxRunNumber = backWeekAccMinute2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getbackWeekAccMinute3() {
        String maxRunNumber = backWeekAccMinute3.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getplusInterval() {
        String maxRunNumber = plusInterval.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getminusInterval() {
        String maxRunNumber = minusInterval.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getplusInc() {
        String maxRunNumber = plusInc.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getminusInc() {
        String maxRunNumber = minusInc.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getincPer() {
        String maxRunNumber = incPer.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getrunNumber() {
        String maxRunNumber = runNumber.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getrunSpeed() {
        String maxRunNumber = runSpeed.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getspeedInc() {
        String maxRunNumber = speedInc.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getspeedSegment() {
        String maxRunNumber = speedSegment.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getintervalSegment() {
        String maxRunNumber = intervalSegment.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getbackSpeedSegment() {
        String maxRunNumber = backSpeedSegment.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getbackIntervalSegment() {
        String maxRunNumber = backIntervalSegment.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getspeedKeyFre() {
        String maxRunNumber = speedKeyFre.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getinterveneKeyFre() {
        String maxRunNumber = interveneKeyFre.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getintervalAccMinute() {
        String maxRunNumber = intervalAccMinute.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getminusInterval2() {
        String maxRunNumber = minusInterval2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getplusInterval2() {
        String maxRunNumber = plusInterval2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getminusInc2() {
        String maxRunNumber = minusInc2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getplusInc2() {
        String maxRunNumber = plusInc2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getincPer2() {
        String maxRunNumber = incPer2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getrunNumber2() {
        String maxRunNumber = runNumber2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getrunSpeed2() {
        String maxRunNumber = runSpeed2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getspeedSegment2() {
        String maxRunNumber = speedSegment2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getspeedInc2() {
        String maxRunNumber = speedInc2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getintervalSegment2() {
        String maxRunNumber = intervalSegment2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getbackSpeedSegment2() {
        String maxRunNumber = backSpeedSegment2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getbackIntervalSegment2() {
        String maxRunNumber = backIntervalSegment2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getspeedKeyFre2() {
        String maxRunNumber = speedKeyFre2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getinterveneKeyFre2() {
        String maxRunNumber = interveneKeyFre2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getintervalAccMinute2() {
        String maxRunNumber = intervalAccMinute2.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }


    private int getRxByte4() {
        String maxRunNumber = rxByte4.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getRxByte5() {
        String maxRunNumber = rxByte5.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }


    private int getRxByte6() {
        String maxRunNumber = rxByte6.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getRxByte7() {
        String maxRunNumber = rxByte7.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }


    private int getRxByte8() {
        String maxRunNumber = rxByte8.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getRxByte9() {
        String maxRunNumber = rxByte9.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getRxByte10() {
        String maxRunNumber = rxByte10.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getRxByte11() {
        String maxRunNumber = rxByte11.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getRxByte12() {
        String maxRunNumber = rxByte12.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getRxByte13() {
        String maxRunNumber = rxByte13.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getRxByte14() {
        String maxRunNumber = rxByte14.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getRxByte15() {
        String maxRunNumber = rxByte15.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getRxByte16() {
        String maxRunNumber = rxByte16.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }


    private int getRxByte17() {
        String maxRunNumber = rxByte17.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }


    private int getRxByte18() {
        String maxRunNumber = rxByte18.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    private int getRxByte19() {
        String maxRunNumber = rxByte19.getText().toString();
        if (CommonUtils.isEmpty(maxRunNumber)) {
            return 0;
        }
        return Integer.parseInt(maxRunNumber);
    }

    /**
     * 获取开始指令
     *
     * @return
     */
    private byte[] getStartActionOrder() {
        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.START_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }

    /**
     * 获取停止指令
     *
     * @return
     */
    private byte[] getStopActionOrder() {

        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.STOP_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }

    /**
     * 获取暂停指令
     *
     * @return
     */
    private byte[] getPauseActionOrder() {
        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.PASUSE_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }

    /**
     * 获取继续指令
     *
     * @return
     */
    private byte[] getContinueOrder() {
        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.CONTINUE_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }

    /**
     * 关闭蓝牙眼镜电源
     * @return
     */
    private byte[] getCloseBleDeviceBattery() {
        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.POWER_OFF_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }

    /**
     * 获取干预指令
     *
     * @return
     */
    private byte[] getInterveneOrder() {
        SendUserInfoControlBleCmdBean sendUserInfoControlBleCmdBean = new SendUserInfoControlBleCmdBean();
        sendUserInfoControlBleCmdBean.setControlCmd(SendUserInfoControlBleCmdBean.INTEVENE_OPERATION_CODE);
        return sendUserInfoControlBleCmdBean.buildCmdByteArray();
    }

    private void showBleDataLogDialog(String content) {
        if (null == bleDataLogDialog) {
            bleDataLogDialog = new BleDataLogDialog(this);
        }
        bleDataLogDialog.showTip(content);
    }

    private void dimissBleDataDialog() {
        if (null != bleDataLogDialog) {
            bleDataLogDialog.dismiss();
        }
    }

    private void startForgroundService() {
        startService(new Intent(this, ForegroundService.class));
    }

}
