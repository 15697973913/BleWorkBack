package com.zj.zhijue.greendao.greenddaodb;

import android.content.Context;


import com.zj.zhijue.greendao.greendaobean.UserSignInDBBean;
import com.zj.zhijue.glasses.greendao.msdao.UserSignInDBBeanDao;


import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;



public class UserSignInBeanDaoOpe {

    private static String DB_NAME = "usersigninbeandaoope.db";

    public static boolean useDefineDBName = false;
    /**
     * 添加数据至数据库
     * @param context
     * @param userInfoDBBean
     */
    public static void insertData(Context context, UserSignInDBBean userInfoDBBean) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserSignInDBBeanDao().insert(userInfoDBBean);
            } else {
                DbManager.getDaoSession(context).getUserSignInDBBeanDao().insert(userInfoDBBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将数据实体通过事物添加至数据库
     * @param context
     * @param userInfoDBBeanList
     */
    public static void insertData(Context context, List<UserSignInDBBean> userInfoDBBeanList) {
        if (null == userInfoDBBeanList || userInfoDBBeanList.size() == 0) {
            return;
        }
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserSignInDBBeanDao().insertInTx(userInfoDBBeanList);
            } else {
                DbManager.getDaoSession(context).getUserSignInDBBeanDao().insertInTx(userInfoDBBeanList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了，如果存在就update(entity);不存在就insert(entity);z
     * @param context
     * @param userInfoDBBean
     */
    public static void saveData(Context context, UserSignInDBBean userInfoDBBean) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserSignInDBBeanDao().save(userInfoDBBean);
            } else {
                DbManager.getDaoSession(context).getUserSignInDBBeanDao().save(userInfoDBBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除数据库中的数据
     * @param context
     * @param userInfoDBBean 删除具体的内容
     */
    public static void deleteData(Context context, UserSignInDBBean userInfoDBBean) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserSignInDBBeanDao().delete(userInfoDBBean);
            } else {
                DbManager.getDaoSession(context).getUserSignInDBBeanDao().delete(userInfoDBBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 根据id删除数据库中的数据
     * @param context
     * @param id
     */
    public static void deleteByKeyData(Context context, String id) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserSignInDBBeanDao().deleteByKey(id);
            } else {
                DbManager.getDaoSession(context).getUserSignInDBBeanDao().deleteByKey(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除所有数据
     * @param context
     */
    public static void deleteAllData(Context context) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserSignInDBBeanDao().deleteAll();
            } else {
                DbManager.getDaoSession(context).getUserSignInDBBeanDao().deleteAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 更新数据
     * @param context
     * @param userInfoDBBean
     */
    public static void updateData(Context context, UserSignInDBBean userInfoDBBean){
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserSignInDBBeanDao().update(userInfoDBBean);
            } else {
                DbManager.getDaoSession(context).getUserSignInDBBeanDao().update(userInfoDBBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询所有数据
     * @param context
     * @return
     */
    public static List<UserSignInDBBean> queryAll(Context context) {
        try {
            if (useDefineDBName) {
                QueryBuilder<UserSignInDBBean> builder = DbManager.getDaoSession(context, DB_NAME).getUserSignInDBBeanDao().queryBuilder();
                return  builder.build().list();
            } else {
                QueryBuilder<UserSignInDBBean> builder = DbManager.getDaoSession(context).getUserSignInDBBeanDao().queryBuilder();
                return  builder.build().list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 分页查询
     * @param pageIndex 当前第几页
     * @param pageSize 每页显示的数据条数
     * @param context
     * @return
     */
    public static List<UserSignInDBBean> queryPaging(int pageIndex, int pageSize, Context context) {
        try {
            if (useDefineDBName) {
                UserSignInDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getUserSignInDBBeanDao();
                List<UserSignInDBBean> listMsg = userInfoDBBeanDao.queryBuilder().offset(pageIndex * pageSize).limit(pageSize).list();
                return listMsg;
            } else {
                UserSignInDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context).getUserSignInDBBeanDao();
                List<UserSignInDBBean> listMsg = userInfoDBBeanDao.queryBuilder().offset(pageIndex * pageSize).limit(pageSize).list();
                return listMsg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  List<UserSignInDBBean> queryRawAllUserSignInBySignServerID(Context context, String serverId) {
        List<UserSignInDBBean> userInfoTrainTimeDBBeanList = null;
        try {
            String whereSQL = "where " + UserSignInDBBeanDao.Properties.Signinserverid.columnName + " = ? ";
            if (useDefineDBName) {
                UserSignInDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getUserSignInDBBeanDao();
                userInfoTrainTimeDBBeanList = userInfoDBBeanDao.queryRaw(whereSQL, new String[]{serverId});
            } else {
                UserSignInDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context).getUserSignInDBBeanDao();
                userInfoTrainTimeDBBeanList = userInfoDBBeanDao.queryRaw(whereSQL, new String[]{serverId});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfoTrainTimeDBBeanList;
    }

    public static  List<UserSignInDBBean> queryRawUserSignInInfoWithSectionByServerID(Context context, String serverId, String startTime, String endTime) {
        List<UserSignInDBBean> userSignInDBBeanList = null;
        try {
            String sign_time = UserSignInDBBeanDao.Properties.Sign_time.columnName;
            String whereSQL = "where " + UserSignInDBBeanDao.Properties.Member_id.columnName + " = ? and ( " + sign_time + " >= ?  and " + sign_time + " =< ? )";
            if (useDefineDBName) {
                UserSignInDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getUserSignInDBBeanDao();
                userSignInDBBeanList = userInfoDBBeanDao.queryRaw(whereSQL, new String[]{serverId, String.valueOf(startTime), String.valueOf(endTime) });
            } else {
                UserSignInDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context).getUserSignInDBBeanDao();
                userSignInDBBeanList = userInfoDBBeanDao.queryRaw(whereSQL, new String[]{serverId, String.valueOf(startTime), String.valueOf(endTime) });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userSignInDBBeanList;
    }

    public static  List<UserSignInDBBean> queryUserSignInfoWithSectionByServerID(Context context, String serverId, Long startTime, Long endTime) {
        List<UserSignInDBBean> userInfoTrainTimeDBBeanList = null;
        try {
            Property signTime = UserSignInDBBeanDao.Properties.Sign_time;
            Property memberId = UserSignInDBBeanDao.Properties.Member_id;

            UserSignInDBBeanDao userSignInDBBeanDao = null;
            if (useDefineDBName) {
                userSignInDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getUserSignInDBBeanDao();
            } else {
                userSignInDBBeanDao = DbManager.getDaoSession(context).getUserSignInDBBeanDao();
            }
            QueryBuilder queryBuilder = userSignInDBBeanDao.queryBuilder();
            queryBuilder.where(memberId.eq(serverId)).and(signTime.ge(startTime), signTime.le(endTime));
            userInfoTrainTimeDBBeanList = queryBuilder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfoTrainTimeDBBeanList;
    }

    /** 原始查询
     方法1：
     Query<User> query = userDao.queryBuilder().where(
     new StringCondition("_ID IN " +
     "(SELECT USER_ID FROM USER_MESSAGE WHERE READ_FLAG = 0)")
     ).build();
     方法2：
     queryRaw 和queryRawCreate

     Query<User> query = userDao.queryRawCreate(
     ", GROUP G WHERE G.NAME=? AND T.GROUP_ID=G._ID", "admin"
     );
     debug查询
     QueryBuilder.LOG_SQL = true;
     QueryBuilder.LOG_VALUES = true;
     */
}
