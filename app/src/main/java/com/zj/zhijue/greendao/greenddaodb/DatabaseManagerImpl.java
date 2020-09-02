package com.zj.zhijue.greendao.greenddaodb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.blankj.utilcode.util.LogUtils;

import com.android.common.baselibrary.log.SdLogUtil;
import com.zj.zhijue.BuildConfig;
import com.zj.zhijue.greendao.greenddaodb.dbupdate.MyOpenHelper;

import com.zj.zhijue.glasses.greendao.msdao.DaoMaster;
import com.zj.zhijue.glasses.greendao.msdao.DaoSession;


import org.greenrobot.greendao.query.QueryBuilder;

public class DatabaseManagerImpl implements DatabaseManager {
    private static final String DBNAME = DbManager.DB_NAME;
    private DaoMaster.OpenHelper helper;
    private SQLiteDatabase db;
    private DaoSession daoSession;
    private Context mContext;
    @Override
    public void startup(Context mContext) {
        this.mContext = mContext;
        if (BuildConfig.DEBUG) {
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        }
        getOpenHelper();
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    @Override
    public void shutdown() {
        if (daoSession != null) {
            daoSession.clear();
        }
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
    private void getOpenHelper() {
        if (helper != null) {
            return;
        }
        // TODO: release版本请使用ReleaseOpenHelper
        /*if (BuildConfig.DEBUG) {
            helper = new DaoMaster.DevOpenHelper(mContext, DBNAME, null);
        } else {
            helper = new MyOpenHelper(mContext, DBNAME, null);
        }*/
        helper = new MyOpenHelper(mContext, DBNAME, null);
    }

    @Override
    public boolean checkDBStatus() {
        if (db == null || !db.isOpen()) {
            getOpenHelper();
            db = helper.getWritableDatabase();
        }
        if (db.isOpen()) {
            return true;
        } else {
            if (BuildConfig.DEBUG) {
                LogUtils.d("database open fail.");
            }
            return false;
        }
    }
    @Override
    public synchronized DaoSession getDaoSession() {
        if (!checkDBStatus()) {
            SdLogUtil.writeCommonLog("DaoSession is null");
            LogUtils.e("DaoSession is null");
            return null;
        }
        if (daoSession == null) {
            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

}