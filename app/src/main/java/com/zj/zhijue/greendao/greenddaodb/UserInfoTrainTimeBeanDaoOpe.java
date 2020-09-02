package com.zj.zhijue.greendao.greenddaodb;

import android.content.Context;
import android.database.Cursor;

import com.zj.zhijue.greendao.greendaobean.UserInfoTrainTimeDBBean;
import com.zj.zhijue.glasses.greendao.msdao.DaoSession;
import com.zj.zhijue.glasses.greendao.msdao.UserInfoTrainTimeDBBeanDao;


import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


public class UserInfoTrainTimeBeanDaoOpe {

    private static String DB_NAME = "userinfotraintimebeandaoope.db";

    public static boolean useDefineDBName = false;
    /**
     * 添加数据至数据库
     * @param context
     * @param userInfoDBBean
     */
    public static void insertData(Context context, UserInfoTrainTimeDBBean userInfoDBBean) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao().insert(userInfoDBBean);
            } else {
                DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao().insert(userInfoDBBean);
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
    public static void insertData(Context context, List<UserInfoTrainTimeDBBean> userInfoDBBeanList) {
        if (null == userInfoDBBeanList || userInfoDBBeanList.size() == 0) {
            return;
        }
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao().insertInTx(userInfoDBBeanList);
            } else {
                DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao().insertInTx(userInfoDBBeanList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了，如果存在就update(entity);不存在就insert(entity);z
     * @param context
     * @param userInfoDBBeanList
     */
    public static void saveData(Context context, List<UserInfoTrainTimeDBBean> userInfoDBBeanList) {
        if (null == userInfoDBBeanList || userInfoDBBeanList.size() == 0) {
            return;
        }

        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao().insertOrReplaceInTx(userInfoDBBeanList);
            } else {
                DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao().insertOrReplaceInTx(userInfoDBBeanList);
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
    public static void deleteData(Context context, UserInfoTrainTimeDBBean userInfoDBBean) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao().delete(userInfoDBBean);
            } else {
                DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao().delete(userInfoDBBean);
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
            QueryBuilder queryBuilder = null;
            if (useDefineDBName) {
                queryBuilder = DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao().queryBuilder();
            } else {
                queryBuilder = DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao().queryBuilder();
            }
            queryBuilder.where(UserInfoTrainTimeDBBeanDao.Properties.Localid.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();
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
                DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao().deleteAll();
            } else {
                DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao().deleteAll();
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
    public static void updateData(Context context, UserInfoTrainTimeDBBean userInfoDBBean){
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao().update(userInfoDBBean);
            } else {
                DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao().update(userInfoDBBean);
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
    public static List<UserInfoTrainTimeDBBean> queryAll(Context context) {
        try {
            if (useDefineDBName) {
                QueryBuilder<UserInfoTrainTimeDBBean> builder = DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao().queryBuilder();
                return  builder.build().list();
            } else {
                QueryBuilder<UserInfoTrainTimeDBBean> builder = DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao().queryBuilder();
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
    public static List<UserInfoTrainTimeDBBean> queryPaging(int pageIndex, int pageSize, Context context) {
        try {
            if (useDefineDBName) {
                UserInfoTrainTimeDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao();
                List<UserInfoTrainTimeDBBean> listMsg = userInfoDBBeanDao.queryBuilder().offset(pageIndex * pageSize).limit(pageSize).list();
                return listMsg;
            } else {
                UserInfoTrainTimeDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao();
                List<UserInfoTrainTimeDBBean> listMsg = userInfoDBBeanDao.queryBuilder().offset(pageIndex * pageSize).limit(pageSize).list();
                return listMsg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static  List<UserInfoTrainTimeDBBean> queryRawAllUserInfoTrainTimeByServerID(Context context, String serverId) {
        List<UserInfoTrainTimeDBBean> userInfoTrainTimeDBBeanList = null;
        try {
            String whereSQL = "where " + UserInfoTrainTimeDBBeanDao.Properties.ServerId.columnName + " = ? ";
            if (useDefineDBName) {
                UserInfoTrainTimeDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao();
                userInfoTrainTimeDBBeanList = userInfoDBBeanDao.queryRaw(whereSQL, new String[]{serverId});
            } else {
                UserInfoTrainTimeDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao();
                userInfoTrainTimeDBBeanList = userInfoDBBeanDao.queryRaw(whereSQL, new String[]{serverId});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfoTrainTimeDBBeanList;
    }

    public static  List<UserInfoTrainTimeDBBean> queryRawUserInfoTrainTimeWithSectionByServerID(Context context, String serverId, Long startTime, Long endTime) {
        List<UserInfoTrainTimeDBBean> userInfoTrainTimeDBBeanList = null;
        try {
            String trainColumnName = UserInfoTrainTimeDBBeanDao.Properties.TrainDate.columnName;
            String whereSQL = "where " + UserInfoTrainTimeDBBeanDao.Properties.ServerId.columnName + " = ? and ( " + trainColumnName + " >= ?  and " + trainColumnName + " =< ? )";
            if (useDefineDBName) {
                UserInfoTrainTimeDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao();
                userInfoTrainTimeDBBeanList = userInfoDBBeanDao.queryRaw(whereSQL, new String[]{serverId, String.valueOf(startTime), String.valueOf(endTime) });
            } else {
                UserInfoTrainTimeDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao();
                userInfoTrainTimeDBBeanList = userInfoDBBeanDao.queryRaw(whereSQL, new String[]{serverId, String.valueOf(startTime), String.valueOf(endTime) });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfoTrainTimeDBBeanList;
    }

    public static  List<UserInfoTrainTimeDBBean> queryUserInfoTrainTimeWithSectionByServerID(Context context, String serverId, Long startTime, Long endTime) {
        List<UserInfoTrainTimeDBBean> userInfoTrainTimeDBBeanList = null;
        try {
            Property trainColumnProperty = UserInfoTrainTimeDBBeanDao.Properties.TrainDate;
            Property serverIdColumnProperty = UserInfoTrainTimeDBBeanDao.Properties.ServerId;

            UserInfoTrainTimeDBBeanDao userInfoDBBeanDao = null;
            if (useDefineDBName) {
                userInfoDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao();
            } else {
                userInfoDBBeanDao = DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao();
            }
            QueryBuilder queryBuilder = userInfoDBBeanDao.queryBuilder();
            queryBuilder.where(serverIdColumnProperty.eq(serverId)).and(trainColumnProperty.ge(startTime), trainColumnProperty.le(endTime));
            userInfoTrainTimeDBBeanList = queryBuilder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfoTrainTimeDBBeanList;
    }

    public static List<UserInfoTrainTimeDBBean> queryUserInfoTrainTimeServerIDAndLimit(Context context, String serverId, int limit) {
        List<UserInfoTrainTimeDBBean> userInfoTrainTimeDBBeanList = null;
        try {
            Property serverIdColumnProperty = UserInfoTrainTimeDBBeanDao.Properties.ServerId;
            Property trainDateLongPropery = UserInfoTrainTimeDBBeanDao.Properties.TrainDate;

            UserInfoTrainTimeDBBeanDao userInfoDBBeanDao = null;
            if (useDefineDBName) {
                userInfoDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao();
            } else {
                userInfoDBBeanDao = DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao();
            }
            QueryBuilder queryBuilder = userInfoDBBeanDao.queryBuilder();
            queryBuilder.where(serverIdColumnProperty.eq(serverId)).orderDesc(trainDateLongPropery).limit(limit);
            userInfoTrainTimeDBBeanList = queryBuilder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfoTrainTimeDBBeanList;
    }


    /**
     * 查询用户最近的一次数据
     * @param context
     * @param serverId
     * @return
     */
    public static  UserInfoTrainTimeDBBean queryRawLastUserInfoTrainTimeByServerID(Context context, String serverId) {
        UserInfoTrainTimeDBBean userInfoTrainTimeDBBean = null;
        try {
            Property serverIdColumnProperty = UserInfoTrainTimeDBBeanDao.Properties.ServerId;
            Property trainDateLongPropery = UserInfoTrainTimeDBBeanDao.Properties.TrainDate;

            UserInfoTrainTimeDBBeanDao userInfoDBBeanDao = null;
            if (useDefineDBName) {
                userInfoDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getUserInfoTrainTimeDBBeanDao();
            } else {
                userInfoDBBeanDao = DbManager.getDaoSession(context).getUserInfoTrainTimeDBBeanDao();
            }
            QueryBuilder queryBuilder = userInfoDBBeanDao.queryBuilder();
            List<UserInfoTrainTimeDBBean> trainTimeDBBeanList = queryBuilder.where(serverIdColumnProperty.eq(serverId)).orderDesc(trainDateLongPropery).limit(1).build().list();
            if (null != trainTimeDBBeanList && trainTimeDBBeanList.size() > 0) {
                userInfoTrainTimeDBBean = trainTimeDBBeanList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfoTrainTimeDBBean;
    }

    /**
     * 查询该表所有的记录数
     * @param context
     * @return
     */
    public static Long getAllDataCount(Context context) {
        Cursor cursor = null;
        DaoSession daoSession = null;
        try {
            if (useDefineDBName) {
                daoSession = DbManager.getDaoSession(context, DB_NAME);
            } else {
                daoSession = DbManager.getDaoSession(context);
            }
            cursor = daoSession.getDatabase().rawQuery("select count(*) from " + UserInfoTrainTimeDBBeanDao.TABLENAME, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    return cursor.getLong(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        return 0L;
    }

    /**
     * 根据 ServerID 查询该表所有的记录数
     * @param context
     * @return
     */
    public static Long getAllDataCountByServerId(Context context, String serverId) {
        Cursor cursor = null;
        DaoSession daoSession = null;
        try {
            if (useDefineDBName) {
                daoSession = DbManager.getDaoSession(context, DB_NAME);
            } else {
                daoSession = DbManager.getDaoSession(context);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select count(*) from " + UserInfoTrainTimeDBBeanDao.TABLENAME );
            stringBuilder.append(" where " + UserInfoTrainTimeDBBeanDao.Properties.ServerId.columnName + " = ? ");
            cursor = daoSession.getDatabase().rawQuery(stringBuilder.toString(), new String[]{serverId});
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    return cursor.getLong(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        return 0L;
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
