package com.zj.zhijue.data.source.remote;

import com.zj.zhijue.bean.BaseBean;
import com.zj.zhijue.data.source.BleGlassesDataSource;



public interface BleGlassesRemoteDataSource extends BleGlassesDataSource {

    interface getBleDataCallBac {
        void onBleDataLoaded(Object object);
    }

    void login(BaseBean baseBean);

    void register(BaseBean baseBean);

    void bindlasses(BaseBean baseBean);

    void bindEyeInfo(BaseBean baseBean);

    BaseBean getTaskBean(BaseBean baseBean);

}
