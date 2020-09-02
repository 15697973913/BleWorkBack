package com.zj.zhijue.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zj.zhijue.helper.MyDBOpenHelper;


public class MyUtilDataBase {
    public static MyDBOpenHelper globaldbOpenHelper;

    public static void CheckGlobalDbOpenHelper(Context context) {
        if (globaldbOpenHelper == null) {
            globaldbOpenHelper = new MyDBOpenHelper(context);
        }
    }

    public static MyDBOpenHelper GetMyDBOpenHelper(Context context) {
        CheckGlobalDbOpenHelper(context);
        return globaldbOpenHelper;
    }

    public static void DBadd(Context context, String FQuipmentID, String FProtocolProperties, int FMode, int QGDZ, int QGDY, int FRange_UpperLimit, int FRange_LowerLimit, int FTimes, int FRate, int FDistance, String UserID, String FTime) {
        CheckGlobalDbOpenHelper(context);
        SQLiteDatabase db = globaldbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put("FQuipmentID", FQuipmentID);
            values.put("FProtocolProperties", FProtocolProperties);
            values.put("FMode", Integer.valueOf(FMode));
            values.put("QGDZ", Integer.valueOf(QGDZ));
            values.put("QGDY", Integer.valueOf(QGDY));
            values.put("FRange_UpperLimit", Integer.valueOf(FRange_UpperLimit));
            values.put("FRange_LowerLimit", Integer.valueOf(FRange_LowerLimit));
            values.put("FTimes", Integer.valueOf(FTimes));
            values.put("FRate", Integer.valueOf(FRate));
            values.put("FDistance", Integer.valueOf(FDistance));
            values.put("UserID", UserID);
            values.put("FTime", FTime);
            db.insert("tb_datacollection", null, values);
        }
    }

    public static void CheckTaskName(Context context, String name) {
        CheckGlobalDbOpenHelper(context);
        SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select count(*) from tb_task where taskname='" + name + "'", null);
            cursor.moveToFirst();
            int Num = cursor.getInt(0);
            cursor.close();
            if (Num == 0) {
                ContentValues values = new ContentValues();
                values.put("taskname", name);
                values.put("taskdate", "0");
                db.insert("tb_task", null, values);
                MyUtil.DisPlayMyError("需要新建:" + name);
                return;
            }
            MyUtil.DisPlayMyError("已经存在:" + name);
        }
    }

    public static void DBdelete(Context context, int id) {
        CheckGlobalDbOpenHelper(context);
        SQLiteDatabase db = globaldbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete("tb_datacollection", "id=?", new String[]{Integer.toString(id)});
        }
    }

    public static int DBCountNum(Context context) {
        CheckGlobalDbOpenHelper(context);
        SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
        if (!db.isOpen()) {
            return 0;
        }
        Cursor cursor = db.rawQuery("select count(*) from tb_datacollection", null);
        cursor.moveToFirst();
        int Num = cursor.getInt(0);
        cursor.close();
        return Num;
    }

    public static int DBCountLocalRuleDataNum(Context context) {
        CheckGlobalDbOpenHelper(context);
        SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
        if (!db.isOpen()) {
            return 0;
        }
        Cursor cursor = db.rawQuery("select count(*) from tb_ruledata", null);
        cursor.moveToFirst();
        int Num = cursor.getInt(0);
        cursor.close();
        return Num;
    }

    public static void DB_ruledata_add(Context context, int speed, int num, int rangeLow, int rangeUp, int status) {
        CheckGlobalDbOpenHelper(context);
        SQLiteDatabase db = globaldbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put("speed", Integer.valueOf(speed));
            values.put("num", Integer.valueOf(num));
            values.put("rangeLow", Integer.valueOf(rangeLow));
            values.put("rangeUp", Integer.valueOf(rangeUp));
            values.put("status", Integer.valueOf(status));
            db.insert("tb_ruledata", null, values);
        }
    }

    public static void clearFeedTable(Context context, String FEED_TABLE_NAME) {
        CheckGlobalDbOpenHelper(context);
        String sql = "DELETE FROM " + FEED_TABLE_NAME + ";";
        SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            db.execSQL(sql);
        }
        revertSeq(context, FEED_TABLE_NAME);
    }

    public static void revertSeq(Context context, String FEED_TABLE_NAME) {
        CheckGlobalDbOpenHelper(context);
        String sql = "update sqlite_sequence set seq=0 where name='" + FEED_TABLE_NAME + "'";
        SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            db.execSQL(sql);
        }
    }

    public static void UpdateTaskDate(Context context, String taskname, String CurYMD) {
        CheckGlobalDbOpenHelper(context);
        try {
            SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
            if (db.isOpen()) {
                ContentValues cv = new ContentValues();
                cv.put("taskdate", CurYMD);
                db.update("tb_task", cv, "taskname=?", new String[]{taskname});
            }
        } catch (Exception e) {
            MyUtil.DisPlayMyError("更新数据表tb_task出错啦" + e.getLocalizedMessage());
        }
        MyUtil.DisPlayMyTask("更新数据表tb_task:" + taskname);
    }

    public static void UpdateTaskDateClear(Context context, String taskname) {
        CheckGlobalDbOpenHelper(context);
        try {
            SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
            if (db.isOpen()) {
                ContentValues cv = new ContentValues();
                cv.put("taskdate", "0");
                db.update("tb_task", cv, "taskname=?", new String[]{taskname});
            }
        } catch (Exception e) {
            MyUtil.DisPlayMyError("清零数据表tb_task出错啦" + e.getLocalizedMessage());
        }
    }

    public static void UpdateTaskDateAllClear(Context context) {
        CheckGlobalDbOpenHelper(context);
        try {
            SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
            if (db.isOpen()) {
                ContentValues cv = new ContentValues();
                cv.put("taskdate", "0");
                db.update("tb_task", cv, null, null);
            }
        } catch (Exception e) {
            MyUtil.DisPlayMyError("清零数据表tb_task出错啦" + e.getLocalizedMessage());
        }
    }

    public static void UpdateRuleDateStatus(Context context, int sendruledataindex) {
        CheckGlobalDbOpenHelper(context);
        try {
            SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
            if (db.isOpen()) {
                ContentValues cv = new ContentValues();
                cv.put("status", "1");
                db.update("tb_ruledata", cv, "id=?", new String[]{String.valueOf(sendruledataindex)});
            }
        } catch (Exception e) {
            MyUtil.DisPlayMyError("更新数据表tb_ruledata出错啦" + e.getLocalizedMessage());
        }
    }

    public static void UpdateAllRuleDateStatusToZero(Context context) {
        CheckGlobalDbOpenHelper(context);
        try {
            SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
            if (db.isOpen()) {
                ContentValues cv = new ContentValues();
                cv.put("status", "0");
                db.update("tb_ruledata", cv, null, null);
            }
        } catch (Exception e) {
            MyUtil.DisPlayMyError("更新数据表tb_ruledata出错啦" + e.getLocalizedMessage());
        }
    }

    public static int DBCountNum_tb_ruledata(Context context) {
        CheckGlobalDbOpenHelper(context);
        SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
        if (!db.isOpen()) {
            return 0;
        }
        Cursor cursor = db.rawQuery("select count(*) from tb_ruledata", null);
        cursor.moveToFirst();
        int Num = cursor.getInt(0);
        cursor.close();
        return Num;
    }

    public static void UpdateLocalValue(Context context, String name, String svalue) {
        CheckGlobalDbOpenHelper(context);
        try {
            SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
            if (db.isOpen()) {
                ContentValues cv_TJZQ = new ContentValues();
                cv_TJZQ.put("svalue", svalue);
                db.update("tb_localvalue", cv_TJZQ, "name=?", new String[]{name});
            }
        } catch (Exception e) {
            MyUtil.DisPlayMyError("更新数据表tb_localvalue出错啦" + e.getLocalizedMessage());
        }
    }

    public static String GetLocalValue(Context context, String name) {
        CheckGlobalDbOpenHelper(context);
        String svaluebuf = "";
        try {
            SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
            if (db.isOpen()) {
                Cursor cursor = db.query("tb_localvalue", null, "name=?", new String[]{name}, null, null, null);
                while (cursor.moveToNext()) {
                    svaluebuf = cursor.getString(cursor.getColumnIndex("svalue"));
                }
                cursor.close();
            }
        } catch (Exception e) {
            MyUtil.DisPlayMyError("查询数据表tb_localvalue获取本地保存的" + name + "数据出错啦" + e.getLocalizedMessage());
        }
        return svaluebuf;
    }

    public static String GetLocalValueNoContext(String name) {
        String svaluebuf = "";
        if (globaldbOpenHelper == null) {
            return svaluebuf;
        }
        try {
            SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
            if (db.isOpen()) {
                Cursor cursor = db.query("tb_localvalue", null, "name=?", new String[]{name}, null, null, null);
                while (cursor.moveToNext()) {
                    svaluebuf = cursor.getString(cursor.getColumnIndex("svalue"));
                }
                cursor.close();
            }
        } catch (Exception e) {
            MyUtil.DisPlayMyError("查询数据表tb_localvalue获取本地保存的" + name + "数据出错啦" + e.getLocalizedMessage());
        }
        return svaluebuf;
    }

    public static String GetTaskDate(Context context, String taskname) {
        CheckGlobalDbOpenHelper(context);
        String svaluebuf = "";
        try {
            SQLiteDatabase db = globaldbOpenHelper.getReadableDatabase();
            if (db.isOpen()) {
                Cursor cursor = db.query("tb_task", null, "taskname=?", new String[]{taskname}, null, null, null);
                while (cursor.moveToNext()) {
                    svaluebuf = cursor.getString(cursor.getColumnIndex("taskdate"));
                }
                cursor.close();
            }
        } catch (Exception e) {
            MyUtil.DisPlayMyError("查询数据表tb_task中的" + taskname + "数据出错啦" + e.getLocalizedMessage());
        }
        return svaluebuf;
    }
}
