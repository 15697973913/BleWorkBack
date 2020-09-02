package com.zj.zhijue.greendao.greenddaodb;

import android.content.Context;

import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.CommonUtils;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.glasses.greendao.msdao.DaoSession;
import com.zj.zhijue.glasses.greendao.msdao.UserInfoDBBeanDao;


import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2018/11/3.
 */

public class UserInfoBeanDaoOpe {

    private static String DB_NAME = "userinfobeandaoope.db";

    public static boolean useDefineDBName = false;
    /**
     * 添加数据至数据库
     * @param context
     * @param userInfoDBBean
     */
    public static void insertData(Context context, UserInfoDBBean userInfoDBBean) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserInfoDBBeanDao().insert(userInfoDBBean);
            } else {
                DbManager.getDaoSession(context).getUserInfoDBBeanDao().insert(userInfoDBBean);
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
    public static void insertData(Context context, List<UserInfoDBBean> userInfoDBBeanList) {
        if (null == userInfoDBBeanList || userInfoDBBeanList.size() == 0) {
            return;
        }
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserInfoDBBeanDao().insertInTx(userInfoDBBeanList);
            } else {
                DbManager.getDaoSession(context).getUserInfoDBBeanDao().insertInTx(userInfoDBBeanList);
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
    public static void saveData(Context context, UserInfoDBBean userInfoDBBean) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserInfoDBBeanDao().save(userInfoDBBean);
            } else {
                DbManager.getDaoSession(context).getUserInfoDBBeanDao().save(userInfoDBBean);
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
    public static void deleteData(Context context, UserInfoDBBean userInfoDBBean) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserInfoDBBeanDao().delete(userInfoDBBean);
            } else {
                DbManager.getDaoSession(context).getUserInfoDBBeanDao().delete(userInfoDBBean);
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
    public static void deleteByKeyData(Context context, long id) {
        try {
            QueryBuilder queryBuilder = null;
            if (useDefineDBName) {
                queryBuilder = DbManager.getDaoSession(context, DB_NAME).getUserInfoDBBeanDao().queryBuilder();
            } else {
                queryBuilder = DbManager.getDaoSession(context).getUserInfoDBBeanDao().queryBuilder();
            }
            queryBuilder.where(UserInfoDBBeanDao.Properties.Localid.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();
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
                DbManager.getDaoSession(context, DB_NAME).getUserInfoDBBeanDao().deleteAll();
            } else {
                DbManager.getDaoSession(context).getUserInfoDBBeanDao().deleteAll();
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
    public static void updateData(Context context, UserInfoDBBean userInfoDBBean){
        /**
         * 主键是 long ,执行才有效
         */
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserInfoDBBeanDao().updateInTx(userInfoDBBean);
            } else {
                DbManager.getDaoSession(context).getUserInfoDBBeanDao().update(userInfoDBBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updatePortraitUrlByServerId(Context context, String serverID, String newPortraitImagePath) {
        try {
            DaoSession daoSession = null;
            if (useDefineDBName) {
                daoSession = DbManager.getDaoSession(context, DB_NAME);
            } else {
                daoSession = DbManager.getDaoSession(context);
            }
            String sqlBindArg = " update " + UserInfoDBBeanDao.TABLENAME
                    + " set "
                    + UserInfoDBBeanDao.Properties.Portrait_image_url.columnName
                    + " = ? where "
                    + UserInfoDBBeanDao.Properties.ServerId.columnName
                    + " = ? ";

            MLog.d("sqlBindArg = " + sqlBindArg);
            daoSession.getDatabase().execSQL(sqlBindArg, new String[]{newPortraitImagePath, serverID});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param context
     * @param keyList keyList.size() + 1 == valueList.size()
     * @param valueList 最后一个值是 ServerID
     */
    public static void updateUserInfoByServerId(Context context,List<String> keyList, List<Object> valueList) {
        assert(keyList.size() + 1 == valueList.size());

        try {
            DaoSession daoSession = null;
            if (useDefineDBName) {
                daoSession = DbManager.getDaoSession(context, DB_NAME);
            } else {
                daoSession = DbManager.getDaoSession(context);
            }
            /**
             * StringBuilder 线程不安全
             */
            StringBuilder stringBuffer  = new StringBuilder("");
            stringBuffer.append(" update " + UserInfoDBBeanDao.TABLENAME);
            stringBuffer.append(" set ");
            int keySize = keyList.size();
            for (int i = 0; i < keySize; i++) {
                if (i != keySize - 1) {
                    stringBuffer.append( " " + keyList.get(i) + " = ? , ");
                } else {
                    /**
                     * 最后一项
                     */
                    stringBuffer.append( " " + keyList.get(i) + " = ? ");
                }
            }

            stringBuffer.append("  where ");
            stringBuffer.append(" " + UserInfoDBBeanDao.Properties.ServerId.columnName + " = ?");

            Object[] valuesArray = new Object[valueList.size()];
            valueList.toArray(valuesArray);

            MLog.d("sqlBindArg = " + stringBuffer.toString());
            daoSession.getDatabase().execSQL(stringBuffer.toString(), valuesArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新数据
     * @param context
     * @param userInfoDBBean
     */
    public static void insertOrReplaceData(Context context, UserInfoDBBean userInfoDBBean){
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getUserInfoDBBeanDao().insertOrReplace(userInfoDBBean);
            } else {
                DbManager.getDaoSession(context).getUserInfoDBBeanDao().insertOrReplace(userInfoDBBean);
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
    public static List<UserInfoDBBean> queryAll(Context context) {
        try {
            if (useDefineDBName) {
                QueryBuilder<UserInfoDBBean> builder = DbManager.getDaoSession(context, DB_NAME).getUserInfoDBBeanDao().queryBuilder();
                return  builder.build().list();
            } else {
                QueryBuilder<UserInfoDBBean> builder = DbManager.getDaoSession(context).getUserInfoDBBeanDao().queryBuilder();
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
    public static List<UserInfoDBBean> queryPaging(int pageIndex, int pageSize, Context context) {
        try {
            if (useDefineDBName) {
                UserInfoDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getUserInfoDBBeanDao();
                List<UserInfoDBBean> listMsg = userInfoDBBeanDao.queryBuilder().offset(pageIndex * pageSize).limit(pageSize).list();
                return listMsg;
            } else {
                UserInfoDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context).getUserInfoDBBeanDao();
                List<UserInfoDBBean> listMsg = userInfoDBBeanDao.queryBuilder().offset(pageIndex * pageSize).limit(pageSize).list();
                return listMsg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static  List<UserInfoDBBean> queryUserInfoByServerID(Context context, String serverId) {
        List<UserInfoDBBean> userInfoDBBeanList = null;
        if (CommonUtils.isEmpty(serverId)) {
            return null;
        }
        try {

            if (useDefineDBName) {
                UserInfoDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getUserInfoDBBeanDao();
                userInfoDBBeanList = userInfoDBBeanDao.queryBuilder().where(UserInfoDBBeanDao.Properties.ServerId.eq(serverId)).build().list();
            } else {
                UserInfoDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context).getUserInfoDBBeanDao();
                userInfoDBBeanList = userInfoDBBeanDao.queryBuilder().where(UserInfoDBBeanDao.Properties.ServerId.eq(serverId)).build().list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfoDBBeanList;
    }

    public static  List<UserInfoDBBean> queryRawUserInfoByServerID(Context context, String serverId) {
        List<UserInfoDBBean> userInfoDBBeanList = null;
        try {
            String whereSQL = "where " + UserInfoDBBeanDao.Properties.ServerId.columnName + " = ? ";
            if (useDefineDBName) {
                UserInfoDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getUserInfoDBBeanDao();
                userInfoDBBeanList = userInfoDBBeanDao.queryRaw(whereSQL, new String[]{serverId});
            } else {
                UserInfoDBBeanDao userInfoDBBeanDao = DbManager.getDaoSession(context).getUserInfoDBBeanDao();
                userInfoDBBeanList = userInfoDBBeanDao.queryRaw(whereSQL, new String[]{serverId});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfoDBBeanList;
    }
}
