package com.zj.zhijue.greendao.greenddaodb;

import android.content.Context;

import com.zj.zhijue.greendao.greendaobean.FirstBean;
import com.zj.zhijue.glasses.greendao.msdao.FirstBeanDao;


import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class FirstBeanDaoOpe {
    private static String DB_NAME = "firstbeandaoope.db";

    public static boolean useDefineDBName = false;
    /**
     * 添加数据至数据库
     * @param context
     * @param firstBean
     */
    public static void insertData(Context context, FirstBean firstBean) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context,DB_NAME).getFirstBeanDao().insert(firstBean);
            } else {
                DbManager.getDaoSession(context).getFirstBeanDao().insert(firstBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 将数据实体通过事物添加至数据库
     * @param context
     * @param firstBeanList
     */
    public static void insertData(Context context, List<FirstBean> firstBeanList) {
        if (null == firstBeanList || firstBeanList.size() == 0) {
            return;
        }
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getFirstBeanDao().insertInTx(firstBeanList);
            } else {
                DbManager.getDaoSession(context).getFirstBeanDao().insertInTx(firstBeanList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了，如果存在就update(entity);不存在就insert(entity);z
     * @param context
     * @param firstBean
     */
    public static void saveData(Context context, FirstBean firstBean) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context,DB_NAME).getFirstBeanDao().save(firstBean);
            } else{
                DbManager.getDaoSession(context).getFirstBeanDao().save(firstBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除数据库中的数据
     * @param context
     * @param firstBean 删除具体的内容
     */
    public static void deleteData(Context context, FirstBean firstBean) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getFirstBeanDao().delete(firstBean);
            } else {
                DbManager.getDaoSession(context).getFirstBeanDao().delete(firstBean);
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
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getFirstBeanDao().deleteByKey(id);
            } else {
                DbManager.getDaoSession(context).getFirstBeanDao().deleteByKey(id);
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
                DbManager.getDaoSession(context, DB_NAME).getFirstBeanDao().deleteAll();
            } else {
                DbManager.getDaoSession(context).getFirstBeanDao().deleteAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 更新数据
     * @param context
     * @param firstBean
     */
    public static void updateData(Context context, FirstBean firstBean){
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getFirstBeanDao().update(firstBean);
            } else {
                DbManager.getDaoSession(context).getFirstBeanDao().update(firstBean);
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
    public static List<FirstBean> queryAll(Context context) {
        try {
            if (useDefineDBName) {
                QueryBuilder<FirstBean> builder = DbManager.getDaoSession(context, DB_NAME).getFirstBeanDao().queryBuilder();
                return  builder.build().list();
            } else {
                QueryBuilder<FirstBean> builder = DbManager.getDaoSession(context).getFirstBeanDao().queryBuilder();
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
    public static List<FirstBean> queryPaging(int pageIndex, int pageSize, Context context) {
        try {
            if (useDefineDBName) {
                FirstBeanDao firstBeanDao = DbManager.getDaoSession(context, DB_NAME).getFirstBeanDao();
                List<FirstBean> listMsg = firstBeanDao.queryBuilder().offset(pageIndex * pageSize).limit(pageSize).list();
                return listMsg;
            } else {
                FirstBeanDao firstBeanDao = DbManager.getDaoSession(context).getFirstBeanDao();
                List<FirstBean> listMsg = firstBeanDao.queryBuilder().offset(pageIndex * pageSize).limit(pageSize).list();
                return listMsg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static  List<FirstBean> queryFirstBeanInfoByServerID(Context context, String serverId) {
        List<FirstBean> userInfoDBBeanList = null;
        try {
            if (useDefineDBName) {
                FirstBeanDao firstBeanDao = DbManager.getDaoSession(context,DB_NAME).getFirstBeanDao();
                userInfoDBBeanList = firstBeanDao.queryBuilder().where(FirstBeanDao.Properties.Localid.eq(serverId)).build().list();
            } else {
                FirstBeanDao firstBeanDao = DbManager.getDaoSession(context).getFirstBeanDao();
                userInfoDBBeanList = firstBeanDao.queryBuilder().where(FirstBeanDao.Properties.Localid.eq(serverId)).build().list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfoDBBeanList;
    }

    public static  List<FirstBean> queryRawFirstBeanByServerID(Context context, String serverId) {
        List<FirstBean> userInfoDBBeanList = null;
        try {
            String whereSQL = "where " + FirstBeanDao.Properties.Localid.columnName + " = ? ";
            if (useDefineDBName) {
                FirstBeanDao firstBeanDao = DbManager.getDaoSession(context, DB_NAME).getFirstBeanDao();
                userInfoDBBeanList = firstBeanDao.queryRaw(whereSQL, new String[]{serverId});
            } else {
                FirstBeanDao firstBeanDao = DbManager.getDaoSession(context).getFirstBeanDao();
                userInfoDBBeanList = firstBeanDao.queryRaw(whereSQL, new String[]{serverId});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfoDBBeanList;
    }







}
