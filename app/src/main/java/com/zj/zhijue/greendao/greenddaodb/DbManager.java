package com.zj.zhijue.greendao.greenddaodb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zj.zhijue.MyApplication;
import com.zj.zhijue.glasses.greendao.msdao.DaoMaster;
import com.zj.zhijue.glasses.greendao.msdao.DaoSession;
import com.zj.zhijue.greendao.greenddaodb.dbupdate.MyOpenHelper;


import org.greenrobot.greendao.database.Database;

public class DbManager {

    // 是否加密
    public static final boolean ENCRYPTED = false;

    public static final String DB_NAME = "ruimingxingtec.db";
    private static final String DB_PASSWD = MyApplication.getInstance().getPackageName();
    private static DbManager mDbManager;
    private static MyOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    private Context mContext;

    private DbManager(Context context, String databaseName) {
        this.mContext = context;
        // 初始化数据库信息
        mDevOpenHelper = new MyOpenHelper(context, databaseName, null);
        getDaoMaster(context, databaseName);
        getDaoSession(context, databaseName);
    }

    public static DbManager getInstance(Context context, String databaseName) {
        if (null == mDbManager) {
            synchronized (DbManager.class) {
                if (null == mDbManager) {
                    mDbManager = new DbManager(context, databaseName);
                }
            }
        }
        return mDbManager;
    }

    /**
     * 获取可读数据库
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getReadableDatabase(Context context, String databaseName) {
        if (null == mDevOpenHelper) {
            getInstance(context, databaseName);
        }
        return mDevOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     *
     * @param context
     * @return
     */
    public static Database getWritableDatabase(Context context, String databaseName) {
        if (null == mDevOpenHelper) {
            getInstance(context, databaseName);
        }

        if (ENCRYPTED) {//加密
            return mDevOpenHelper.getEncryptedWritableDb(DB_PASSWD);
        } else {
            return mDevOpenHelper.getWritableDb();
        }
    }

    /**
     * 获取DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context, String databaseName) {
        if (null == mDaoMaster) {
            synchronized (DbManager.class) {
                if (null == mDaoMaster) {
                    mDaoMaster = new DaoMaster(getWritableDatabase(context, databaseName));
                }
            }
        }
        return mDaoMaster;
    }

//    /**
//     * 获取DaoMaster
//     *    数据库升级的时候用该方法
//     * 判断是否存在数据库，如果没有则创建数据库
//     * @param context
//     * @return
//     */
//    public static DaoMaster getDaoMaster(Context context) {
//        if (null == mDaoMaster) {
//            synchronized (DbManager.class) {
//                if (null == mDaoMaster) {
//                    MyOpenHelper helper = new MyOpenHelper(context,DB_NAME,null);
//                    mDaoMaster = new DaoMaster(helper.getWritableDatabase());
//                }
//            }
//        }
//        return mDaoMaster;
//    }

    /**
     * 获取DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context, String databaseName) {
        if (null == mDaoSession) {
            synchronized (DbManager.class) {
                mDaoSession = getDaoMaster(context, databaseName).newSession();
            }
        }
        return mDaoSession;
    }

    public static DaoSession getDaoSession(Context context) {
        //return getDaoSession(context, DB_NAME);
        return MyApplication.getInstance().getDataBaseManager().getDaoSession();
    }
}