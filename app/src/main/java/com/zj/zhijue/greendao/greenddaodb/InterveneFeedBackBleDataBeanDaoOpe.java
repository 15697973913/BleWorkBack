package com.zj.zhijue.greendao.greenddaodb;

import android.content.Context;
import android.database.Cursor;

import com.android.common.baselibrary.log.MLog;
import com.zj.zhijue.greendao.greendaobean.InterveneFeedBackBleDataDBBean;
import com.zj.zhijue.glasses.greendao.msdao.DaoSession;
import com.zj.zhijue.glasses.greendao.msdao.InterveneFeedBackBleDataDBBeanDao;


import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 干预反馈数据库 Bean 的操作对象
 */
public class InterveneFeedBackBleDataBeanDaoOpe {
    private static String DB_NAME = "intervenefeedbackbledatabeandaoope.db";

    public static boolean useDefineDBName = false;

    public static void insertOrReplaceListData(Context context, List<InterveneFeedBackBleDataDBBean> inteveneFeedBackBleDataDBBeanList) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getInterveneFeedBackBleDataDBBeanDao().insertOrReplaceInTx(inteveneFeedBackBleDataDBBeanList);
            } else {
                DbManager.getDaoSession(context).getInterveneFeedBackBleDataDBBeanDao().insertOrReplaceInTx(inteveneFeedBackBleDataDBBeanList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteByListBeanData(Context context, List<InterveneFeedBackBleDataDBBean> beanList) {
        try {

            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getInterveneFeedBackBleDataDBBeanDao().deleteInTx(beanList);
            } else {
                DbManager.getDaoSession(context).getInterveneFeedBackBleDataDBBeanDao().deleteInTx(beanList);
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
                queryBuilder = DbManager.getDaoSession(context, DB_NAME).getInterveneFeedBackBleDataDBBeanDao().queryBuilder();
            } else {
                queryBuilder = DbManager.getDaoSession(context).getInterveneFeedBackBleDataDBBeanDao().queryBuilder();
            }
            queryBuilder.where(InterveneFeedBackBleDataDBBeanDao.Properties.Localid.in(idList)).buildDelete().executeDeleteWithoutDetachingEntities();
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
                queryBuilder = DbManager.getDaoSession(context, DB_NAME).getInterveneFeedBackBleDataDBBeanDao().queryBuilder();
            } else {
                queryBuilder = DbManager.getDaoSession(context).getInterveneFeedBackBleDataDBBeanDao().queryBuilder();
            }
            queryBuilder.where(InterveneFeedBackBleDataDBBeanDao.Properties.UserId.eq(serverId),
                    InterveneFeedBackBleDataDBBeanDao.Properties.IsReportedServer.eq(reportedServer))
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
    public static synchronized void updateInterveneBleDataStatus(Context context,List<String> localIdList, boolean isReportedServer, String serverId) {

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
            stringBuffer.append(" update " + InterveneFeedBackBleDataDBBeanDao.TABLENAME);
            stringBuffer.append(" set ");
            stringBuffer.append(" " + InterveneFeedBackBleDataDBBeanDao.Properties.IsReportedServer.columnName + " = ?");
            stringBuffer.append(" where ");
            stringBuffer.append(InterveneFeedBackBleDataDBBeanDao.Properties.UserId.columnName);
            stringBuffer.append(" = ");
            stringBuffer.append(" ? ");
            stringBuffer.append(" and ");

            stringBuffer.append(InterveneFeedBackBleDataDBBeanDao.Properties.Localid.columnName);
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
    public static  List<InterveneFeedBackBleDataDBBean> queryInterveneBleDataByReportedServer(Context context, String serverId, boolean isReportedServer, int limitCount) {
        List<InterveneFeedBackBleDataDBBean> InteveneFeedBackBleDataDBBeanList = null;
        try {
            Property isReportedServerProperty = InterveneFeedBackBleDataDBBeanDao.Properties.IsReportedServer;
            Property serverIdColumnProperty = InterveneFeedBackBleDataDBBeanDao.Properties.UserId;

            InterveneFeedBackBleDataDBBeanDao inteveneFeedBackBleDataDBBeanDao = null;
            if (useDefineDBName) {
                inteveneFeedBackBleDataDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getInterveneFeedBackBleDataDBBeanDao();
            } else {
                inteveneFeedBackBleDataDBBeanDao = DbManager.getDaoSession(context).getInterveneFeedBackBleDataDBBeanDao();
            }
            QueryBuilder queryBuilder = inteveneFeedBackBleDataDBBeanDao.queryBuilder();
            queryBuilder.where(serverIdColumnProperty.eq(serverId),
                    isReportedServerProperty.eq(isReportedServer))
                    .orderDesc(InterveneFeedBackBleDataDBBeanDao.Properties.ReceiveLocalTime)
                    .limit(limitCount);
            InteveneFeedBackBleDataDBBeanList = queryBuilder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return InteveneFeedBackBleDataDBBeanList;
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
            sqlBuiler.append("select count(*) from " + InterveneFeedBackBleDataDBBeanDao.TABLENAME );
            sqlBuiler.append(" where ");
            sqlBuiler.append(" " + InterveneFeedBackBleDataDBBeanDao.Properties.UserId.columnName + " = ? ");
            sqlBuiler.append(" and " + InterveneFeedBackBleDataDBBeanDao.Properties.IsReportedServer.columnName + " = ? ");

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

    public static InterveneFeedBackBleDataDBBean queryTheOldestBean(Context context) {
        InterveneFeedBackBleDataDBBean InteveneFeedBackBleDataDBBean = null;
        try {
            InterveneFeedBackBleDataDBBeanDao inteveneFeedBackBleDataDBBeanDao = null;
            if (useDefineDBName) {
                inteveneFeedBackBleDataDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getInterveneFeedBackBleDataDBBeanDao();
            } else {
                inteveneFeedBackBleDataDBBeanDao = DbManager.getDaoSession(context).getInterveneFeedBackBleDataDBBeanDao();
            }
            QueryBuilder queryBuilder = inteveneFeedBackBleDataDBBeanDao.queryBuilder();
            List<InterveneFeedBackBleDataDBBean> InteveneFeedBackBleDataDBBeanList = queryBuilder.orderAsc(InterveneFeedBackBleDataDBBeanDao.Properties.ReceiveLocalTime).limit(1).list();
            if (null != InteveneFeedBackBleDataDBBeanList && InteveneFeedBackBleDataDBBeanList.size() > 0) {
                InteveneFeedBackBleDataDBBean = InteveneFeedBackBleDataDBBeanList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return InteveneFeedBackBleDataDBBean;
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
            cursor = daoSession.getDatabase().rawQuery("select count(*) from " + InterveneFeedBackBleDataDBBeanDao.TABLENAME, null);
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
