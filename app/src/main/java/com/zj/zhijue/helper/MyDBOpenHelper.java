package com.zj.zhijue.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBOpenHelper extends SQLiteOpenHelper {
    public MyDBOpenHelper(Context context) {
        super(context, "goodeyelycj.db", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.i("mylog", "db oncreate");
        db.execSQL("CREATE TABLE tb_datacollection (id integer primary key autoincrement, FQuipmentID varchar(20), FProtocolProperties varchar(200),FMode integer,QGDZ integer,QGDY integer,FRange_UpperLimit integer,FRange_LowerLimit integer,FTimes integer,FRate integer,FDistance integer,UserID varchar(200),FTime varchar(24))");
        db.execSQL("CREATE TABLE tb_task (id integer primary key autoincrement, taskname varchar(20), taskdate varchar(20))");
        db.execSQL("CREATE TABLE tb_localvalue (id integer primary key autoincrement, name varchar(20),svalue varchar(200))");
        db.execSQL("CREATE TABLE tb_ruledata (id integer primary key autoincrement, speed integer, num integer, rangeLow integer, rangeUp integer, status integer)");
        db.execSQL("insert into tb_task(taskname,taskdate) values('getservertime','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('checkservertime','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('getversion','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('checkappversion','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('querypausewarn','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('querydelayusertime','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('queryspeedadjust','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('queryrangeadjust','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('queryonedayreport','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('querysevendayreport','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('queryspeedvalue','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('querysetdata','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('uploadsetdata','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('getgujianhao','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('checkgujianhao','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('checkglassesdata','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('checkappdata','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('downloadruledata','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('sendtimetoble','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('sendguilingtoble','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('sendinitdiptoertoble','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('sendstartpostoble','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('sendruletoble','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('sendruleoktoble','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('senduseridtoble','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('sendspeedjiaozhengtoble','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('sendrangejiaozhengtoble','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('senddistancecaiyangtoble','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('senddistance123toble','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('sendspeed0_5toble','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('sendspeed6_10toble','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('sendwuxiaojiance','0')");
        db.execSQL("insert into tb_task(taskname,taskdate) values('sendinitok','0')");
        db.execSQL("insert into tb_localvalue(name,svalue) values('local_authtoken','0')");
        db.execSQL("insert into tb_localvalue(name,svalue) values('local_userid','0')");
        db.execSQL("insert into tb_localvalue(name,svalue) values('local_username','0')");
        db.execSQL("insert into tb_localvalue(name,svalue) values('local_glassmac','0')");
        db.execSQL("insert into tb_localvalue(name,svalue) values('local_loginname','0')");
        db.execSQL("insert into tb_localvalue(name,svalue) values('local_logindate','0')");
        db.execSQL("insert into tb_localvalue(name,svalue) values('local_loginuserstatus','0')");
        db.execSQL("insert into tb_localvalue(name,svalue) values('local_istiyan','0')");
        db.execSQL("insert into tb_localvalue(name,svalue) values('local_tiyantime','0')");
        db.execSQL("insert into tb_localvalue(name,svalue) values('local_havedaoqi','0')");
        db.execSQL("insert into tb_localvalue(name,svalue) values('local_pausewarncode','0')");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("on update ");
    }
}
