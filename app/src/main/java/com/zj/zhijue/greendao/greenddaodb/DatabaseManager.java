package com.zj.zhijue.greendao.greenddaodb;

import android.content.Context;

import com.zj.zhijue.glasses.greendao.msdao.DaoSession;


public interface DatabaseManager {
    /**
     * 获取数据库连接
     */
    void startup(Context mContext);
    /**
     * 关闭数据库
     */
    void shutdown();
    
    /**
     * 检查数据库状态是否可用
     * @return
     */
    boolean checkDBStatus();
    /**
     * 获取greenDAO DAO管理类
     * @return
     */
    DaoSession getDaoSession();
}