package com.zj.zhijue.glasses.greendao.msdao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zj.zhijue.greendao.greendaobean.InterveneFeedBackBleDataDBBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "intevenefeedback_bledata_tab".
*/
public class InterveneFeedBackBleDataDBBeanDao extends AbstractDao<InterveneFeedBackBleDataDBBean, String> {

    public static final String TABLENAME = "intevenefeedback_bledata_tab";

    /**
     * Properties of entity InterveneFeedBackBleDataDBBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Localid = new Property(0, String.class, "localid", true, "localid");
        public final static Property UserId = new Property(1, String.class, "userId", false, "userId");
        public final static Property CurrentUserCode = new Property(2, long.class, "currentUserCode", false, "currentUserCode");
        public final static Property GlassesMAC = new Property(3, String.class, "glassesMAC", false, "glassesMAC");
        public final static Property InterveneYear = new Property(4, int.class, "interveneYear", false, "interveneYear");
        public final static Property InterveneMonth = new Property(5, int.class, "interveneMonth", false, "interveneMonth");
        public final static Property InterveneDay = new Property(6, int.class, "interveneDay", false, "interveneDay");
        public final static Property InterveneHour = new Property(7, int.class, "interveneHour", false, "interveneHour");
        public final static Property InterveneMinute = new Property(8, int.class, "interveneMinute", false, "interveneMinute");
        public final static Property InterveneSecond = new Property(9, int.class, "interveneSecond", false, "interveneSecond");
        public final static Property WeekKeyFre = new Property(10, int.class, "weekKeyFre", false, "weekKeyFre");
        public final static Property SpeedKeyFre = new Property(11, int.class, "speedKeyFre", false, "speedKeyFre");
        public final static Property InterveneKeyFre = new Property(12, int.class, "interveneKeyFre", false, "interveneKeyFre");
        public final static Property SpeedKeyFre2 = new Property(13, int.class, "speedKeyFre2", false, "speedKeyFre2");
        public final static Property InterveneKeyFre2 = new Property(14, int.class, "interveneKeyFre2", false, "interveneKeyFre2");
        public final static Property WeekAccMinute = new Property(15, int.class, "weekAccMinute", false, "weekAccMinute");
        public final static Property MonitorCmd = new Property(16, String.class, "monitorCmd", false, "monitorCmd");
        public final static Property ReceiveLocalTime = new Property(17, long.class, "receiveLocalTime", false, "receiveLocalTime");
        public final static Property ReceiveLocalTimeStr = new Property(18, String.class, "receiveLocalTimeStr", false, "receiveLocalTimeStr");
        public final static Property IsReportedServer = new Property(19, boolean.class, "isReportedServer", false, "isReportedServer");
        public final static Property Reserve1 = new Property(20, String.class, "reserve1", false, "reserve1");
        public final static Property ReservedStr0 = new Property(21, String.class, "reservedStr0", false, "reservedStr0");
        public final static Property ReservedStr1 = new Property(22, String.class, "reservedStr1", false, "reservedStr1");
        public final static Property ReservedStr2 = new Property(23, String.class, "reservedStr2", false, "reservedStr2");
        public final static Property ReservedStr3 = new Property(24, String.class, "reservedStr3", false, "reservedStr3");
        public final static Property ReservedLong0 = new Property(25, long.class, "reservedLong0", false, "reservedLong0");
        public final static Property ReservedLong1 = new Property(26, long.class, "reservedLong1", false, "reservedLong1");
        public final static Property ReservedInt0 = new Property(27, int.class, "reservedInt0", false, "reservedInt0");
        public final static Property ReservedInt1 = new Property(28, int.class, "reservedInt1", false, "reservedInt1");
        public final static Property ReservedInt2 = new Property(29, int.class, "reservedInt2", false, "reservedInt2");
        public final static Property ReservedInt3 = new Property(30, int.class, "reservedInt3", false, "reservedInt3");
        public final static Property ReservedInt4 = new Property(31, int.class, "reservedInt4", false, "reservedInt4");
    }


    public InterveneFeedBackBleDataDBBeanDao(DaoConfig config) {
        super(config);
    }
    
    public InterveneFeedBackBleDataDBBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"intevenefeedback_bledata_tab\" (" + //
                "\"localid\" TEXT PRIMARY KEY NOT NULL ," + // 0: localid
                "\"userId\" TEXT," + // 1: userId
                "\"currentUserCode\" INTEGER NOT NULL ," + // 2: currentUserCode
                "\"glassesMAC\" TEXT," + // 3: glassesMAC
                "\"interveneYear\" INTEGER NOT NULL ," + // 4: interveneYear
                "\"interveneMonth\" INTEGER NOT NULL ," + // 5: interveneMonth
                "\"interveneDay\" INTEGER NOT NULL ," + // 6: interveneDay
                "\"interveneHour\" INTEGER NOT NULL ," + // 7: interveneHour
                "\"interveneMinute\" INTEGER NOT NULL ," + // 8: interveneMinute
                "\"interveneSecond\" INTEGER NOT NULL ," + // 9: interveneSecond
                "\"weekKeyFre\" INTEGER NOT NULL ," + // 10: weekKeyFre
                "\"speedKeyFre\" INTEGER NOT NULL ," + // 11: speedKeyFre
                "\"interveneKeyFre\" INTEGER NOT NULL ," + // 12: interveneKeyFre
                "\"speedKeyFre2\" INTEGER NOT NULL ," + // 13: speedKeyFre2
                "\"interveneKeyFre2\" INTEGER NOT NULL ," + // 14: interveneKeyFre2
                "\"weekAccMinute\" INTEGER NOT NULL ," + // 15: weekAccMinute
                "\"monitorCmd\" TEXT," + // 16: monitorCmd
                "\"receiveLocalTime\" INTEGER NOT NULL ," + // 17: receiveLocalTime
                "\"receiveLocalTimeStr\" TEXT," + // 18: receiveLocalTimeStr
                "\"isReportedServer\" INTEGER NOT NULL ," + // 19: isReportedServer
                "\"reserve1\" TEXT," + // 20: reserve1
                "\"reservedStr0\" TEXT," + // 21: reservedStr0
                "\"reservedStr1\" TEXT," + // 22: reservedStr1
                "\"reservedStr2\" TEXT," + // 23: reservedStr2
                "\"reservedStr3\" TEXT," + // 24: reservedStr3
                "\"reservedLong0\" INTEGER NOT NULL ," + // 25: reservedLong0
                "\"reservedLong1\" INTEGER NOT NULL ," + // 26: reservedLong1
                "\"reservedInt0\" INTEGER NOT NULL ," + // 27: reservedInt0
                "\"reservedInt1\" INTEGER NOT NULL ," + // 28: reservedInt1
                "\"reservedInt2\" INTEGER NOT NULL ," + // 29: reservedInt2
                "\"reservedInt3\" INTEGER NOT NULL ," + // 30: reservedInt3
                "\"reservedInt4\" INTEGER NOT NULL );"); // 31: reservedInt4
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_intevenefeedback_bledata_tab_localid ON \"intevenefeedback_bledata_tab\"" +
                " (\"localid\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"intevenefeedback_bledata_tab\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, InterveneFeedBackBleDataDBBean entity) {
        stmt.clearBindings();
 
        String localid = entity.getLocalid();
        if (localid != null) {
            stmt.bindString(1, localid);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
        stmt.bindLong(3, entity.getCurrentUserCode());
 
        String glassesMAC = entity.getGlassesMAC();
        if (glassesMAC != null) {
            stmt.bindString(4, glassesMAC);
        }
        stmt.bindLong(5, entity.getInterveneYear());
        stmt.bindLong(6, entity.getInterveneMonth());
        stmt.bindLong(7, entity.getInterveneDay());
        stmt.bindLong(8, entity.getInterveneHour());
        stmt.bindLong(9, entity.getInterveneMinute());
        stmt.bindLong(10, entity.getInterveneSecond());
        stmt.bindLong(11, entity.getWeekKeyFre());
        stmt.bindLong(12, entity.getSpeedKeyFre());
        stmt.bindLong(13, entity.getInterveneKeyFre());
        stmt.bindLong(14, entity.getSpeedKeyFre2());
        stmt.bindLong(15, entity.getInterveneKeyFre2());
        stmt.bindLong(16, entity.getWeekAccMinute());
 
        String monitorCmd = entity.getMonitorCmd();
        if (monitorCmd != null) {
            stmt.bindString(17, monitorCmd);
        }
        stmt.bindLong(18, entity.getReceiveLocalTime());
 
        String receiveLocalTimeStr = entity.getReceiveLocalTimeStr();
        if (receiveLocalTimeStr != null) {
            stmt.bindString(19, receiveLocalTimeStr);
        }
        stmt.bindLong(20, entity.getIsReportedServer() ? 1L: 0L);
 
        String reserve1 = entity.getReserve1();
        if (reserve1 != null) {
            stmt.bindString(21, reserve1);
        }
 
        String reservedStr0 = entity.getReservedStr0();
        if (reservedStr0 != null) {
            stmt.bindString(22, reservedStr0);
        }
 
        String reservedStr1 = entity.getReservedStr1();
        if (reservedStr1 != null) {
            stmt.bindString(23, reservedStr1);
        }
 
        String reservedStr2 = entity.getReservedStr2();
        if (reservedStr2 != null) {
            stmt.bindString(24, reservedStr2);
        }
 
        String reservedStr3 = entity.getReservedStr3();
        if (reservedStr3 != null) {
            stmt.bindString(25, reservedStr3);
        }
        stmt.bindLong(26, entity.getReservedLong0());
        stmt.bindLong(27, entity.getReservedLong1());
        stmt.bindLong(28, entity.getReservedInt0());
        stmt.bindLong(29, entity.getReservedInt1());
        stmt.bindLong(30, entity.getReservedInt2());
        stmt.bindLong(31, entity.getReservedInt3());
        stmt.bindLong(32, entity.getReservedInt4());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, InterveneFeedBackBleDataDBBean entity) {
        stmt.clearBindings();
 
        String localid = entity.getLocalid();
        if (localid != null) {
            stmt.bindString(1, localid);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
        stmt.bindLong(3, entity.getCurrentUserCode());
 
        String glassesMAC = entity.getGlassesMAC();
        if (glassesMAC != null) {
            stmt.bindString(4, glassesMAC);
        }
        stmt.bindLong(5, entity.getInterveneYear());
        stmt.bindLong(6, entity.getInterveneMonth());
        stmt.bindLong(7, entity.getInterveneDay());
        stmt.bindLong(8, entity.getInterveneHour());
        stmt.bindLong(9, entity.getInterveneMinute());
        stmt.bindLong(10, entity.getInterveneSecond());
        stmt.bindLong(11, entity.getWeekKeyFre());
        stmt.bindLong(12, entity.getSpeedKeyFre());
        stmt.bindLong(13, entity.getInterveneKeyFre());
        stmt.bindLong(14, entity.getSpeedKeyFre2());
        stmt.bindLong(15, entity.getInterveneKeyFre2());
        stmt.bindLong(16, entity.getWeekAccMinute());
 
        String monitorCmd = entity.getMonitorCmd();
        if (monitorCmd != null) {
            stmt.bindString(17, monitorCmd);
        }
        stmt.bindLong(18, entity.getReceiveLocalTime());
 
        String receiveLocalTimeStr = entity.getReceiveLocalTimeStr();
        if (receiveLocalTimeStr != null) {
            stmt.bindString(19, receiveLocalTimeStr);
        }
        stmt.bindLong(20, entity.getIsReportedServer() ? 1L: 0L);
 
        String reserve1 = entity.getReserve1();
        if (reserve1 != null) {
            stmt.bindString(21, reserve1);
        }
 
        String reservedStr0 = entity.getReservedStr0();
        if (reservedStr0 != null) {
            stmt.bindString(22, reservedStr0);
        }
 
        String reservedStr1 = entity.getReservedStr1();
        if (reservedStr1 != null) {
            stmt.bindString(23, reservedStr1);
        }
 
        String reservedStr2 = entity.getReservedStr2();
        if (reservedStr2 != null) {
            stmt.bindString(24, reservedStr2);
        }
 
        String reservedStr3 = entity.getReservedStr3();
        if (reservedStr3 != null) {
            stmt.bindString(25, reservedStr3);
        }
        stmt.bindLong(26, entity.getReservedLong0());
        stmt.bindLong(27, entity.getReservedLong1());
        stmt.bindLong(28, entity.getReservedInt0());
        stmt.bindLong(29, entity.getReservedInt1());
        stmt.bindLong(30, entity.getReservedInt2());
        stmt.bindLong(31, entity.getReservedInt3());
        stmt.bindLong(32, entity.getReservedInt4());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public InterveneFeedBackBleDataDBBean readEntity(Cursor cursor, int offset) {
        InterveneFeedBackBleDataDBBean entity = new InterveneFeedBackBleDataDBBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // localid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.getLong(offset + 2), // currentUserCode
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // glassesMAC
            cursor.getInt(offset + 4), // interveneYear
            cursor.getInt(offset + 5), // interveneMonth
            cursor.getInt(offset + 6), // interveneDay
            cursor.getInt(offset + 7), // interveneHour
            cursor.getInt(offset + 8), // interveneMinute
            cursor.getInt(offset + 9), // interveneSecond
            cursor.getInt(offset + 10), // weekKeyFre
            cursor.getInt(offset + 11), // speedKeyFre
            cursor.getInt(offset + 12), // interveneKeyFre
            cursor.getInt(offset + 13), // speedKeyFre2
            cursor.getInt(offset + 14), // interveneKeyFre2
            cursor.getInt(offset + 15), // weekAccMinute
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // monitorCmd
            cursor.getLong(offset + 17), // receiveLocalTime
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // receiveLocalTimeStr
            cursor.getShort(offset + 19) != 0, // isReportedServer
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // reserve1
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // reservedStr0
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // reservedStr1
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // reservedStr2
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // reservedStr3
            cursor.getLong(offset + 25), // reservedLong0
            cursor.getLong(offset + 26), // reservedLong1
            cursor.getInt(offset + 27), // reservedInt0
            cursor.getInt(offset + 28), // reservedInt1
            cursor.getInt(offset + 29), // reservedInt2
            cursor.getInt(offset + 30), // reservedInt3
            cursor.getInt(offset + 31) // reservedInt4
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, InterveneFeedBackBleDataDBBean entity, int offset) {
        entity.setLocalid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCurrentUserCode(cursor.getLong(offset + 2));
        entity.setGlassesMAC(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setInterveneYear(cursor.getInt(offset + 4));
        entity.setInterveneMonth(cursor.getInt(offset + 5));
        entity.setInterveneDay(cursor.getInt(offset + 6));
        entity.setInterveneHour(cursor.getInt(offset + 7));
        entity.setInterveneMinute(cursor.getInt(offset + 8));
        entity.setInterveneSecond(cursor.getInt(offset + 9));
        entity.setWeekKeyFre(cursor.getInt(offset + 10));
        entity.setSpeedKeyFre(cursor.getInt(offset + 11));
        entity.setInterveneKeyFre(cursor.getInt(offset + 12));
        entity.setSpeedKeyFre2(cursor.getInt(offset + 13));
        entity.setInterveneKeyFre2(cursor.getInt(offset + 14));
        entity.setWeekAccMinute(cursor.getInt(offset + 15));
        entity.setMonitorCmd(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setReceiveLocalTime(cursor.getLong(offset + 17));
        entity.setReceiveLocalTimeStr(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setIsReportedServer(cursor.getShort(offset + 19) != 0);
        entity.setReserve1(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setReservedStr0(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setReservedStr1(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setReservedStr2(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setReservedStr3(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setReservedLong0(cursor.getLong(offset + 25));
        entity.setReservedLong1(cursor.getLong(offset + 26));
        entity.setReservedInt0(cursor.getInt(offset + 27));
        entity.setReservedInt1(cursor.getInt(offset + 28));
        entity.setReservedInt2(cursor.getInt(offset + 29));
        entity.setReservedInt3(cursor.getInt(offset + 30));
        entity.setReservedInt4(cursor.getInt(offset + 31));
     }
    
    @Override
    protected final String updateKeyAfterInsert(InterveneFeedBackBleDataDBBean entity, long rowId) {
        return entity.getLocalid();
    }
    
    @Override
    public String getKey(InterveneFeedBackBleDataDBBean entity) {
        if(entity != null) {
            return entity.getLocalid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(InterveneFeedBackBleDataDBBean entity) {
        return entity.getLocalid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
