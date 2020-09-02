package com.zj.zhijue.greendao.greenddaodb;

import android.content.Context;
import android.database.Cursor;

import com.android.common.baselibrary.log.MLog;
import com.zj.zhijue.greendao.greendaobean.RunParamsBleDataDBBean;
import com.zj.zhijue.glasses.greendao.msdao.DaoSession;
import com.zj.zhijue.glasses.greendao.msdao.RunParamsBleDataDBBeanDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 运动参数数据库 Bean 的操作对象
 */
public class RunParamsBleDataBeanDaoOpe {
    private static String DB_NAME = "runparamsbleedatabeandaoope.db";

    public static boolean useDefineDBName = false;

    public static void insertOrReplaceListData(Context context, List<RunParamsBleDataDBBean> runParamsBleDataDBBeanList) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getRunParamsBleDataDBBeanDao().insertOrReplaceInTx(runParamsBleDataDBBeanList);
            } else {
                DbManager.getDaoSession(context).getRunParamsBleDataDBBeanDao().insertOrReplaceInTx(runParamsBleDataDBBeanList);
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
                queryBuilder = DbManager.getDaoSession(context, DB_NAME).getRunParamsBleDataDBBeanDao().queryBuilder();
            } else {
                queryBuilder = DbManager.getDaoSession(context).getRunParamsBleDataDBBeanDao().queryBuilder();
            }
            queryBuilder.where(RunParamsBleDataDBBeanDao.Properties.Localid.in(idList)).buildDelete().executeDeleteWithoutDetachingEntities();
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
                queryBuilder = DbManager.getDaoSession(context, DB_NAME).getRunParamsBleDataDBBeanDao().queryBuilder();
            } else {
                queryBuilder = DbManager.getDaoSession(context).getRunParamsBleDataDBBeanDao().queryBuilder();
            }
            queryBuilder.where(RunParamsBleDataDBBeanDao.Properties.UserId.eq(serverId),
                    RunParamsBleDataDBBeanDao.Properties.IsReportedServer.eq(reportedServer))
                    .buildDelete().executeDeleteWithoutDetachingEntities();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据用户ID 来删除数据，不管有没有上报数据
     * @param context
     * @param serverId
     */
    public static void deleteDataByUserId(Context context, String serverId) {
        try {
            QueryBuilder queryBuilder = null;
            if (useDefineDBName) {
                queryBuilder = DbManager.getDaoSession(context, DB_NAME).getRunParamsBleDataDBBeanDao().queryBuilder();
            } else {
                queryBuilder = DbManager.getDaoSession(context).getRunParamsBleDataDBBeanDao().queryBuilder();
            }
            queryBuilder.where(RunParamsBleDataDBBeanDao.Properties.UserId.eq(serverId))
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
    public static synchronized void updateRunParamsReportStatus(Context context,List<String> localIdList, boolean isReportedServer, String serverId) {

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
            stringBuffer.append(" update " + RunParamsBleDataDBBeanDao.TABLENAME);
            stringBuffer.append(" set ");
            stringBuffer.append(" " + RunParamsBleDataDBBeanDao.Properties.IsReportedServer.columnName + " = ?");
            stringBuffer.append(" where ");
            stringBuffer.append(RunParamsBleDataDBBeanDao.Properties.UserId.columnName);
            stringBuffer.append(" = ");
            stringBuffer.append(" ? ");
            stringBuffer.append(" and ");

            stringBuffer.append(RunParamsBleDataDBBeanDao.Properties.Localid.columnName);
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
    public static  List<RunParamsBleDataDBBean> queryRunParamsBleDataByReportedServer(Context context, String serverId, boolean isReportedServer, int limitCount) {
        List<RunParamsBleDataDBBean> runParamsBleDataDBBeanList = null;
        try {
            Property isReportedServerProperty = RunParamsBleDataDBBeanDao.Properties.IsReportedServer;
            Property serverIdColumnProperty = RunParamsBleDataDBBeanDao.Properties.UserId;

            RunParamsBleDataDBBeanDao runParamsBleDataDBBeanDao = null;
            if (useDefineDBName) {
                runParamsBleDataDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getRunParamsBleDataDBBeanDao();
            } else {
                runParamsBleDataDBBeanDao = DbManager.getDaoSession(context).getRunParamsBleDataDBBeanDao();
            }
            QueryBuilder queryBuilder = runParamsBleDataDBBeanDao.queryBuilder();
            queryBuilder.where(serverIdColumnProperty.eq(serverId),
                    isReportedServerProperty.eq(isReportedServer))
                    .orderDesc(RunParamsBleDataDBBeanDao.Properties.ReceiveLocalTime).limit(limitCount);
            runParamsBleDataDBBeanList = queryBuilder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return runParamsBleDataDBBeanList;
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
            sqlBuiler.append("select count(*) from " + RunParamsBleDataDBBeanDao.TABLENAME );
            sqlBuiler.append(" where ");
            sqlBuiler.append(" " + RunParamsBleDataDBBeanDao.Properties.UserId.columnName + " = ? ");
            sqlBuiler.append(" and " + RunParamsBleDataDBBeanDao.Properties.IsReportedServer.columnName + " = ? ");

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

    public static RunParamsBleDataDBBean queryTheOldestBean(Context context, String userID) {
        RunParamsBleDataDBBean runParamsBleDataDBBean = null;
        try {
            RunParamsBleDataDBBeanDao runParamsBleDataDBBeanDao = null;
            if (useDefineDBName) {
                runParamsBleDataDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getRunParamsBleDataDBBeanDao();
            } else {
                runParamsBleDataDBBeanDao = DbManager.getDaoSession(context).getRunParamsBleDataDBBeanDao();
            }
            QueryBuilder queryBuilder = runParamsBleDataDBBeanDao.queryBuilder();
            List<RunParamsBleDataDBBean> runParamsBleDataDBBeanList = queryBuilder
                    .where(RunParamsBleDataDBBeanDao.Properties.UserId.eq(userID))
                    .orderAsc(RunParamsBleDataDBBeanDao.Properties.ReceiveLocalTime)
                    .limit(1)
                    .list();

            if (null != runParamsBleDataDBBeanList && runParamsBleDataDBBeanList.size() > 0) {
                runParamsBleDataDBBean = runParamsBleDataDBBeanList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return runParamsBleDataDBBean;
    }

    public static RunParamsBleDataDBBean queryTheNewestBean(Context context, String userID) {
        RunParamsBleDataDBBean runParamsBleDataDBBean = null;
        try {
            RunParamsBleDataDBBeanDao runParamsBleDataDBBeanDao = null;
            if (useDefineDBName) {
                runParamsBleDataDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getRunParamsBleDataDBBeanDao();
            } else {
                runParamsBleDataDBBeanDao = DbManager.getDaoSession(context).getRunParamsBleDataDBBeanDao();
            }
            QueryBuilder queryBuilder = runParamsBleDataDBBeanDao.queryBuilder();

            List<RunParamsBleDataDBBean> runParamsBleDataDBBeanList = queryBuilder
                    .where(RunParamsBleDataDBBeanDao.Properties.UserId.eq(userID))
                    .orderDesc(RunParamsBleDataDBBeanDao.Properties.ReceiveLocalTime)
                    .limit(1)
                    .list();

            if (null != runParamsBleDataDBBeanList && runParamsBleDataDBBeanList.size() > 0) {
                runParamsBleDataDBBean = runParamsBleDataDBBeanList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return runParamsBleDataDBBean;
    }

    public static RunParamsBleDataDBBean queryTheNewestBeanByReportedStatus(Context context, String userID, boolean reported) {
        RunParamsBleDataDBBean runParamsBleDataDBBean = null;
        try {
            RunParamsBleDataDBBeanDao runParamsBleDataDBBeanDao = null;
            if (useDefineDBName) {
                runParamsBleDataDBBeanDao = DbManager.getDaoSession(context, DB_NAME).getRunParamsBleDataDBBeanDao();
            } else {
                runParamsBleDataDBBeanDao = DbManager.getDaoSession(context).getRunParamsBleDataDBBeanDao();
            }
            QueryBuilder queryBuilder = runParamsBleDataDBBeanDao.queryBuilder();

            List<RunParamsBleDataDBBean> runParamsBleDataDBBeanList = queryBuilder
                    .where(RunParamsBleDataDBBeanDao.Properties.UserId.eq(userID),RunParamsBleDataDBBeanDao.Properties.IsReportedServer.eq(reported) )
                    .orderDesc(RunParamsBleDataDBBeanDao.Properties.ReceiveLocalTime)
                    .limit(1)
                    .list();

            if (null != runParamsBleDataDBBeanList && runParamsBleDataDBBeanList.size() > 0) {
                runParamsBleDataDBBean = runParamsBleDataDBBeanList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return runParamsBleDataDBBean;
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
            cursor = daoSession.getDatabase().rawQuery("select count(*) from " + RunParamsBleDataDBBeanDao.TABLENAME, null);
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
