package com.zj.zhijue.greendao.greenddaodb;


import android.content.Context;


import com.zj.zhijue.greendao.greendaobean.ReviewDataEyeSightDBBean;
import com.zj.zhijue.glasses.greendao.msdao.ReviewDataEyeSightDBBeanDao;


import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 视力复查
 */
public class ReviewDataEyeSightBeanDaoOpe {

    private static String DB_NAME = "reviewdataeyesightbeandaoope.db";

    public static boolean useDefineDBName = false;

    /**
     * 添加数据至数据库
     * @param context
     * @param reviewDataEyeSightDBBean
     */
    public static void insertData(Context context, ReviewDataEyeSightDBBean reviewDataEyeSightDBBean) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getReviewDataEyeSightDBBeanDao().insert(reviewDataEyeSightDBBean);
            } else {
                DbManager.getDaoSession(context).getReviewDataEyeSightDBBeanDao().insert(reviewDataEyeSightDBBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将数据实体通过事物添加至数据库
     * @param context
     * @param reviewDataEyeSightDBBeanList
     */
    public static void insertData(Context context, List<ReviewDataEyeSightDBBean> reviewDataEyeSightDBBeanList) {
        if (null == reviewDataEyeSightDBBeanList || reviewDataEyeSightDBBeanList.size() == 0) {
            return;
        }
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getReviewDataEyeSightDBBeanDao().insertInTx(reviewDataEyeSightDBBeanList);
            } else {
                DbManager.getDaoSession(context).getReviewDataEyeSightDBBeanDao().insertInTx(reviewDataEyeSightDBBeanList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了，如果存在就update(entity);不存在就insert(entity);z
     * @param context
     * @param reviewDataEyeSightDBBean
     */
    public static void saveData(Context context, ReviewDataEyeSightDBBean reviewDataEyeSightDBBean) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getReviewDataEyeSightDBBeanDao().insertOrReplace(reviewDataEyeSightDBBean);
            } else {
                DbManager.getDaoSession(context).getReviewDataEyeSightDBBeanDao().insertOrReplace(reviewDataEyeSightDBBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveListData(Context context, List<ReviewDataEyeSightDBBean> reviewDataEyeSightDBBeanList) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getReviewDataEyeSightDBBeanDao().insertOrReplaceInTx(reviewDataEyeSightDBBeanList);
            } else {
                DbManager.getDaoSession(context).getReviewDataEyeSightDBBeanDao().insertOrReplaceInTx(reviewDataEyeSightDBBeanList);
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
    public static List<ReviewDataEyeSightDBBean> queryAllByMemeberId(Context context, String memberId) {
        try {
            Property userIDProperty = ReviewDataEyeSightDBBeanDao.Properties.UserId;
            Property reviewTimes = ReviewDataEyeSightDBBeanDao.Properties.ReviewEyeSightTimes;

            if (useDefineDBName) {
                QueryBuilder<ReviewDataEyeSightDBBean> builder = DbManager.getDaoSession(context, DB_NAME).getReviewDataEyeSightDBBeanDao().queryBuilder();
                builder.where(userIDProperty.eq(memberId)).orderAsc(reviewTimes);
                return  builder.build().list();
            } else {
                QueryBuilder<ReviewDataEyeSightDBBean> builder = DbManager.getDaoSession(context).getReviewDataEyeSightDBBeanDao().queryBuilder();
                builder.where(userIDProperty.eq(memberId)).orderAsc(reviewTimes);
                return  builder.build().list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 删除所有数据
     * @param context
     */
    public static void deleteAllData(Context context) {
        try {
            if (useDefineDBName) {
                DbManager.getDaoSession(context, DB_NAME).getReviewDataEyeSightDBBeanDao().deleteAll();
            } else {
                DbManager.getDaoSession(context).getReviewDataEyeSightDBBeanDao().deleteAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
