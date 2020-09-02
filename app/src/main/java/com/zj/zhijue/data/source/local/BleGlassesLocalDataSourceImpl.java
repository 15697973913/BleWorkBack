package com.zj.zhijue.data.source.local;

import androidx.annotation.NonNull;

import com.zj.zhijue.util.AppExecutors;

public class BleGlassesLocalDataSourceImpl implements BleGlassesLocalDataSource {

    private static volatile BleGlassesLocalDataSourceImpl INSTANCE;

    private AppExecutors appExecutors;

    private BleGlassesLocalDataSourceImpl(@NonNull AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    public static BleGlassesLocalDataSourceImpl getInstance(@NonNull AppExecutors appExecutors) {
        if (null == INSTANCE) {
            synchronized (BleGlassesLocalDataSourceImpl.class) {
                if (null == INSTANCE) {
                    INSTANCE = new BleGlassesLocalDataSourceImpl(appExecutors);
                }
            }
        }
        return INSTANCE;
    }

/*    @Override
    public void saveTask() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getTask(LoadBleDataCallBack loadBleDataCallBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        appExecutors.diskIO().execute(runnable);
    }*/

}
