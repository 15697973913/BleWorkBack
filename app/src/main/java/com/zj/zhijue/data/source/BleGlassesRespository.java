package com.zj.zhijue.data.source;

import androidx.annotation.NonNull;

import com.zj.zhijue.bean.BaseBean;
import com.zj.zhijue.data.source.local.BleGlassesLocalDataSource;
import com.zj.zhijue.data.source.remote.BleGlassesRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;



public class BleGlassesRespository implements BleGlassesLocalDataSource, BleGlassesRemoteDataSource{
    private static BleGlassesRespository INSTANCE;

    private final BleGlassesLocalDataSource mBleGlassesLocalDataSource;

    private final BleGlassesRemoteDataSource mBleGlassRemoteDataSource;

    private BleGlassesRespository(@NonNull BleGlassesLocalDataSource bleGlassesLocalDataSource, @NonNull BleGlassesRemoteDataSource bleGlassRemoteDataSource) {
        mBleGlassesLocalDataSource = bleGlassesLocalDataSource;
        mBleGlassRemoteDataSource = bleGlassRemoteDataSource;
    }

    public static BleGlassesRespository getInstance(@NonNull BleGlassesLocalDataSource bleGlassesLocalDataSource, @NonNull BleGlassesRemoteDataSource bleGlassRemoteDataSource) {
        if (null == INSTANCE) {
            INSTANCE = new BleGlassesRespository(bleGlassesLocalDataSource, bleGlassRemoteDataSource);
        }
        return INSTANCE;
    }

    public static BleGlassesRespository getInstance() {
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

  /*  @Override
    public void saveTask() {

    }

    @Override
    public void getTask(LoadBleDataCallBack loadBleDataCallBack) {
        //checkNotNull(loadBleDataCallBack);
    }*/

    @Override
    public void login(BaseBean baseBean) {

    }

    @Override
    public void register(BaseBean baseBean) {

    }

    @Override
    public void bindlasses(BaseBean baseBean) {

    }

    @Override
    public void bindEyeInfo(BaseBean baseBean) {

    }

    @Override
    public BaseBean getTaskBean(BaseBean baseBean) {
        return null;
    }
}
