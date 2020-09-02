package com.zj.zhijue.data.source.remote;

import com.zj.zhijue.bean.BaseBean;


public class BleGlassesRemoteDataSourceImpl implements BleGlassesRemoteDataSource {
    private static BleGlassesRemoteDataSourceImpl INSTANCE;

    public static BleGlassesRemoteDataSourceImpl getInstance() {
        return INSTANCE = SingleTon.single;
    }

    private static class SingleTon {
        private final static BleGlassesRemoteDataSourceImpl single = new BleGlassesRemoteDataSourceImpl();
    }

    private BleGlassesRemoteDataSourceImpl() {

    }

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
