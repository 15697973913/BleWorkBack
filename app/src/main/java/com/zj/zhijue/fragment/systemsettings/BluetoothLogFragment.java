package com.zj.zhijue.fragment.systemsettings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.common.baselibrary.log.BleDataLog;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.BleMainControlActivity;
import com.zj.zhijue.activity.BleSearchActivity;
import com.zj.zhijue.adapter.BluetoothLogAdapter;
import com.zj.zhijue.base.BaseFragment;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zj.zhijue.base.BaseBleActivity.REQUEST_STORAGE_PERMISSION;


public class BluetoothLogFragment extends BaseFragment {


    @BindView(R.id.rcvBluetoothLog)
    RecyclerView rcvBluetoothLog;
    @BindView(R.id.tvLogDetail)
    TextView tvLogDetail;
    @BindView(R.id.svLogDetail)
    ScrollView svLogDetail;

    private BluetoothLogAdapter bluetoothLogAdapter;
    List<File> fileList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bluetooth_log, container, false);
        ButterKnife.bind(this, view);
        rcvBluetoothLog.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // 此处写第一次检查权限且已经拥有权限后的业务
            LogUtils.v("获取了读写权限");
            fileList = FileUtils.listFilesInDirWithFilter(BleDataLog.getLogDirPath(), new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.getName().endsWith("log");
                }
            }, true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            }
        }

        bluetoothLogAdapter = new BluetoothLogAdapter(fileList);
        rcvBluetoothLog.setAdapter(bluetoothLogAdapter);

        bluetoothLogAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                svLogDetail.setVisibility(View.VISIBLE);
                tvLogDetail.setText(readFileData(fileList.get(position)));
            }
        });

        return view;
    }

    /**
     * 检查权限后的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            LogUtils.v("获取了读写权限");
            fileList = FileUtils.listFilesInDirWithFilter(BleDataLog.getLogDirPath(), new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.getName().endsWith("log");
                }
            }, true);
            bluetoothLogAdapter.notifyDataSetChanged();
        }
    }


    public static String readFileData(File file) {
        StringBuilder content = new StringBuilder();
        try {
            if (file.exists()) {
                FileInputStream is = new FileInputStream(file);
                //设置流读取方式
                InputStreamReader inputReader = new InputStreamReader(is);
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line;
                try {
                    while (null != (line = buffReader.readLine())) {
                        //读取的文件容
                        content.append(line).append("\n");
                    }
                    is.close();//关闭输入流
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return content.toString();
    }


    @OnClick(R.id.backimg)
    public void onViewClicked() {
        if (svLogDetail.getVisibility() == View.VISIBLE) {
            svLogDetail.setVisibility(View.GONE);
        } else {
            if (getActivity() != null) {
                getActivity().finish();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
                    if (svLogDetail.getVisibility() == View.VISIBLE) {
                        svLogDetail.setVisibility(View.GONE);
                    } else {
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }


}