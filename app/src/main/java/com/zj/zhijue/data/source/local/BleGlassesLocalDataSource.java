package com.zj.zhijue.data.source.local;

import com.zj.zhijue.data.source.BleGlassesDataSource;

import java.util.List;

public interface BleGlassesLocalDataSource extends BleGlassesDataSource {
    interface LoadBleDataCallBack {
        void onBleDataLoad(List<Object> list);
    }

}
