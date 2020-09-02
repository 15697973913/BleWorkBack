package com.zj.zhijue.greendao.greenddaodb;

import android.content.Context;
import android.database.Cursor;

import com.android.common.baselibrary.log.MLog;
import com.zj.zhijue.glasses.greendao.msdao.CommonFeedBackBleDataDBBeanDao;
import com.zj.zhijue.glasses.greendao.msdao.DaoSession;
import com.zj.zhijue.greendao.greendaobean.CommonFeedBackBleDataDBBean;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 实时反馈数据库 Bean 的操作对象
 */
public class CommonFeedBackBleDataBeanDaoOpe {
    private static String DB_NAME = "commonfeedbackbledatabeandaoope.db";

    public static boolean useDefineDBName = false;

    public static void insertOrReplaceListData(Context context, List<CommonFeedBackBleDataDBBean> commonFeedBackBleDataDBBeanList) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getCommonFeedBackBleDataDBBeanDao().insertOrReplaceInTx(commonFeedBackBleDataDBBeanList);
            } else {
                DbManager.getDaoSession(context).getCommonFeedBackBleDataDBBeanDao().insertOrReplaceInTx(commonFeedBackBleDataDBBeanList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteByKeyListDBDataBean(Context context, List<CommonFeedBackBleDataDBBean> beanList) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getCommonFeedBackBleDataDBBeanDao().deleteInTx(beanList);
            } else {
                DbManager.getDaoSession(context).getCommonFeedBackBleDataDBBeanDao().deleteInTx(beanList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据 localId 批量删除数据
     * @param context
     * @param idList
     */
    public static void deleteByKeyListData(Context context, List<String> idList) {
        try {
            QueryBuilder queryBuilder = null;
            if (useDefineDBName) {
                queryBuilder = DbManager.getDaoSession(context, DB_NAME).getCommonFeedBackBleDataDBBeanDao().queryBuilder();
            } else {
                queryBuilder = DbManager.getDaoSession(context).getCommonFeedBackBleDataDBBeanDao().queryBuilder();
            }
            queryBuilder.where(CommonFeedBackBleDataDBBeanDao.Properties.Localid.in(idList)).buildDelete().executeDeleteWithoutDetachingEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据是否已上报给云端，来删除数据
     * @param context
     * @param serverId
     * @param reportedServer
     */
    public static void deleteData(Context context, String serverId, boolean reportedServer) {
        try {
            QueryBuilder queryBuilder = null;
            if (useDefineDBName) {
                queryBuilder = DbManager.getDaoSession(context, DB_NAME).getCommonFeedBackBleDataDBBeanDao().queryBuilder();
            } else {
                queryBuilder = DbManager.getDaoSession(context).getCommonFeedBackBleDataDBBeanDao().queryBuilder();
            }
            queryBuilder.where(CommonFeedBackBleDataDBBeanDao.Properties.UserId.eq(serverId),
                    CommonFeedBackBleDataDBBeanDao.Properties.IsReportedServer.eq(reportedServer))
                    .buildDelete().executeDeleteWithoutDetachingEntities();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 批量修改训练数据是否已上报的状态
     * @param context
     * @param localIdList
     * @param isReportedServer
     */
    public static synchronized void updateCommonFeedbackBleDataStatus(Context context, List<String> localIdList, boolean isReportedServer, String serverId) {

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
            stringBuffer.append(" update " + CommonFeedBackBleDataDBBeanDao.TABLENAME);
            stringBuffer.append(" set ");
            stringBuffer.append(" " + CommonFeedBackBleDataDBBeanDao.Properties.IsReportedServer.columnName + " = ?");
            stringBuffer.append(" where ");
            stringBuffer.append(CommonFeedBackBleDataDBBeanDao.Properties.UserId.columnName);
            stringBuffer.append(" = ");
            stringBuffer.append(" ? ");
            stringBuffer.append(" and ");

            stringBuffer.append(CommonFeedBackBleDataDBBeanDao.Properties.Localid.columnName);
            stringBuffer.append(" in (" );

            int idSize = localIdList.size();

            for (int i = 0; i < idSize; i++) {
                if (i == idSize - 1) {
                    /**
                     * 最后一个
                     */
                    stringBuffer.append(" ? ");
                } else {
                    stringBuffer.append(" ?, ");
                }
            }
            stringBuffer.append(" )" );

            List<Object>  argsList = new ArrayList<>();
            argsList.add(isReportedServer);
            argsList.add(serverId);
            argsList.addAll(localIdList);

            Object[] argsArray = new Object[argsList.size()];

            argsList.toArray(argsArray);


            MLog.d("sqlBindArg = " + stringBuffer.toString());
            daoSession.getDatabase().execSQL(stringBuffer.toString(), argsArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询是否已上报(哪种操作)给服务器的数据，结果以收集数据的时间降序排序
     * @param context
     * @param serverId
     * @param isReportedServer
     * @return
     */
    public static  List<CommonFeedBackBleDataDBBean> queryComFeedbackBleDataByReportedServer(Context context, String serverId, boolean isReportedServer,int limitCount) {
        List<CommonFeedBackBleDataDBBean> CommonFeedBackBleDataDBBeanList = null;
        try {
            Property isReportedServerProperty = CommonFeedBackBleDataDBBeanDao.Properties.IsReportedServer;
            Property serverIdColumnProperty = CommonFeedBackBleDataDBBeanDao.Properties.UserId;

            CommonFeedBackBleDataDBBeanDao commonFeedBackBleDataDBBeanDao = null;
            if (useDefineDBName) {
                commonFeedBackBleDataDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getCommonFeedBackBleDataDBBeanDao();
            } else {
                commonFeedBackBleDataDBBeanDao = DbManager.getDaoSession(context).getCommonFeedBackBleDataDBBeanDao();
            }
            QueryBuilder queryBuilder = commonFeedBackBleDataDBBeanDao.queryBuilder();
            queryBuilder.where(serverIdColumnProperty.eq(serverId),
                    isReportedServerProperty.eq(isReportedServer))
                    .orderDesc(CommonFeedBackBleDataDBBeanDao.Properties.ReceiveLocalTime).limit(limitCount);
            CommonFeedBackBleDataDBBeanList = queryBuilder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonFeedBackBleDataDBBeanList;
    }

    /**
     * 本地查询未上报给云端的数量
     * @param context
     * @return
     */
    public static  Long getAllUnReportedDataCount(Context context, String serverId) {
        Cursor cursor = null;
        DaoSession daoSession = null;
        try {

            if (useDefineDBName) {
                daoSession = DbManager.getDaoSession(context, DB_NAME);
            } else {
                daoSession = DbManager.getDaoSession(context);
            }

            StringBuilder sqlBuiler = new StringBuilder();
            sqlBuiler.append("select count(*) from " + CommonFeedBackBleDataDBBeanDao.TABLENAME );
            sqlBuiler.append(" where ");
            sqlBuiler.append(" " + CommonFeedBackBleDataDBBeanDao.Properties.UserId.columnName + " = ? ");
            sqlBuiler.append(" and " + CommonFeedBackBleDataDBBeanDao.Properties.IsReportedServer.columnName + " = ? ");

            MLog.d(" " + sqlBuiler.toString());
            //布尔值被存储为整数 0（false）和 1（true）。
            cursor = daoSession.getDatabase().rawQuery(sqlBuiler.toString(), new String[]{serverId, String.valueOf(0)});
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

    public static CommonFeedBackBleDataDBBean queryTheOldestBean(Context context) {
        CommonFeedBackBleDataDBBean CommonFeedBackBleDataDBBean = null;
        try {
            CommonFeedBackBleDataDBBeanDao commonFeedBackBleDataDBBeanDao = null;
            if (useDefineDBName) {
                commonFeedBackBleDataDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getCommonFeedBackBleDataDBBeanDao();
            } else {
                commonFeedBackBleDataDBBeanDao = DbManager.getDaoSession(context).getCommonFeedBackBleDataDBBeanDao();
            }
            QueryBuilder queryBuilder = commonFeedBackBleDataDBBeanDao.queryBuilder();
            List<CommonFeedBackBleDataDBBean> CommonFeedBackBleDataDBBeanList = queryBuilder.orderAsc(CommonFeedBackBleDataDBBeanDao.Properties.ReceiveLocalTime).limit(1).list();
            if (null != CommonFeedBackBleDataDBBeanList && CommonFeedBackBleDataDBBeanList.size() > 0) {
                CommonFeedBackBleDataDBBean = CommonFeedBackBleDataDBBeanList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonFeedBackBleDataDBBean;
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
            cursor = daoSession.getDatabase().rawQuery("select count(*) from " + CommonFeedBackBleDataDBBeanDao.TABLENAME, null);
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

}
