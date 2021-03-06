package com.zj.zhijue.glasses.greendao.msdao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zj.zhijue.greendao.greendaobean.CommonFeedBackBleDataDBBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "comfeedback_bledata_tab".
*/
public class CommonFeedBackBleDataDBBeanDao extends AbstractDao<CommonFeedBackBleDataDBBean, String> {

    public static final String TABLENAME = "comfeedback_bledata_tab";

    /**
     * Properties of entity CommonFeedBackBleDataDBBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Localid = new Property(0, String.class, "localid", true, "localid");
        public final static Property UserId = new Property(1, String.class, "userId", false, "userId");
        public final static Property CurrentUserCode = new Property(2, long.class, "currentUserCode", false, "currentUserCode");
        public final static Property GlassesMAC = new Property(3, String.class, "glassesMAC", false, "glassesMAC");
        public final static Property MearsureDistance = new Property(4, int.class, "mearsureDistance", false, "mearsureDistance");
        public final static Property Battery = new Property(5, int.class, "battery", false, "battery");
        public final static Property TrainTimeYear = new Property(6, int.class, "trainTimeYear", false, "trainTimeYear");
        public final static Property TrainTimeMonth = new Property(7, int.class, "trainTimeMonth", false, "trainTimeMonth");
        public final static Property TrainTimeDay = new Property(8, int.class, "trainTimeDay", false, "trainTimeDay");
        public final static Property TrainTimeHour = new Property(9, int.class, "trainTimeHour", false, "trainTimeHour");
        public final static Property TrainTimeMinute = new Property(10, int.class, "trainTimeMinute", false, "trainTimeMinute");
        public final static Property TrainTimeSecond = new Property(11, int.class, "trainTimeSecond", false, "trainTimeSecond");
        public final static Property InterveneAccMinute = new Property(12, int.class, "interveneAccMinute", false, "interveneAccMinute");
        public final static Property IntervalAccMinute = new Property(13, int.class, "intervalAccMinute", false, "intervalAccMinute");
        public final static Property IntervalAccMinute2 = new Property(14, int.class, "intervalAccMinute2", false, "intervalAccMinute2");
        public final static Property OperationCmd = new Property(15, String.class, "operationCmd", false, "operationCmd");
        public final static Property ReceiveLocalTime = new Property(16, long.class, "receiveLocalTime", false, "receiveLocalTime");
        public final static Property ReceiveLocalTimeStr = new Property(17, String.class, "receiveLocalTimeStr", false, "receiveLocalTimeStr");
        public final static Property IsReportedServer = new Property(18, boolean.class, "isReportedServer", false, "isReportedServer");
        public final static Property Reserve1 = new Property(19, String.class, "reserve1", false, "reserve1");
        public final static Property ReservedStr0 = new Property(20, String.class, "reservedStr0", false, "reservedStr0");
        public final static Property ReservedStr1 = new Property(21, String.class, "reservedStr1", false, "reservedStr1");
        public final static Property ReservedStr2 = new Property(22, String.class, "reservedStr2", false, "reservedStr2");
        public final static Property ReservedStr3 = new Property(23, String.class, "reservedStr3", false, "reservedStr3");
        public final static Property ReservedLong0 = new Property(24, long.class, "reservedLong0", false, "reservedLong0");
        public final static Property ReservedLong1 = new Property(25, long.class, "reservedLong1", false, "reservedLong1");
        public final static Property ReservedInt0 = new Property(26, int.class, "reservedInt0", false, "reservedInt0");
        public final static Property ReservedInt1 = new Property(27, int.class, "reservedInt1", false, "reservedInt1");
        public final static Property ReservedInt2 = new Property(28, int.class, "reservedInt2", false, "reservedInt2");
        public final static Property ReservedInt3 = new Property(29, int.class, "reservedInt3", false, "reservedInt3");
        public final static Property ReservedInt4 = new Property(30, int.class, "reservedInt4", false, "reservedInt4");
    }


    public CommonFeedBackBleDataDBBeanDao(DaoConfig config) {
        super(config);
    }
    
    public CommonFeedBackBleDataDBBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"comfeedback_bledata_tab\" (" + //
                "\"localid\" TEXT PRIMARY KEY NOT NULL ," + // 0: localid
                "\"userId\" TEXT," + // 1: userId
                "\"currentUserCode\" INTEGER NOT NULL ," + // 2: currentUserCode
                "\"glassesMAC\" TEXT," + // 3: glassesMAC
                "\"mearsureDistance\" INTEGER NOT NULL ," + // 4: mearsureDistance
                "\"battery\" INTEGER NOT NULL ," + // 5: battery
                "\"trainTimeYear\" INTEGER NOT NULL ," + // 6: trainTimeYear
                "\"trainTimeMonth\" INTEGER NOT NULL ," + // 7: trainTimeMonth
                "\"trainTimeDay\" INTEGER NOT NULL ," + // 8: trainTimeDay
                "\"trainTimeHour\" INTEGER NOT NULL ," + // 9: trainTimeHour
                "\"trainTimeMinute\" INTEGER NOT NULL ," + // 10: trainTimeMinute
                "\"trainTimeSecond\" INTEGER NOT NULL ," + // 11: trainTimeSecond
                "\"interveneAccMinute\" INTEGER NOT NULL ," + // 12: interveneAccMinute
                "\"intervalAccMinute\" INTEGER NOT NULL ," + // 13: intervalAccMinute
                "\"intervalAccMinute2\" INTEGER NOT NULL ," + // 14: intervalAccMinute2
                "\"operationCmd\" TEXT," + // 15: operationCmd
                "\"receiveLocalTime\" INTEGER NOT NULL ," + // 16: receiveLocalTime
                "\"receiveLocalTimeStr\" TEXT," + // 17: receiveLocalTimeStr
                "\"isReportedServer\" INTEGER NOT NULL ," + // 18: isReportedServer
                "\"reserve1\" TEXT," + // 19: reserve1
                "\"reservedStr0\" TEXT," + // 20: reservedStr0
                "\"reservedStr1\" TEXT," + // 21: reservedStr1
                "\"reservedStr2\" TEXT," + // 22: reservedStr2
                "\"reservedStr3\" TEXT," + // 23: reservedStr3
                "\"reservedLong0\" INTEGER NOT NULL ," + // 24: reservedLong0
                "\"reservedLong1\" INTEGER NOT NULL ," + // 25: reservedLong1
                "\"reservedInt0\" INTEGER NOT NULL ," + // 26: reservedInt0
                "\"reservedInt1\" INTEGER NOT NULL ," + // 27: reservedInt1
                "\"reservedInt2\" INTEGER NOT NULL ," + // 28: reservedInt2
                "\"reservedInt3\" INTEGER NOT NULL ," + // 29: reservedInt3
                "\"reservedInt4\" INTEGER NOT NULL );"); // 30: reservedInt4
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_comfeedback_bledata_tab_localid ON \"comfeedback_bledata_tab\"" +
                " (\"localid\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"comfeedback_bledata_tab\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CommonFeedBackBleDataDBBean entity) {
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
        stmt.bindLong(5, entity.getMearsureDistance());
        stmt.bindLong(6, entity.getBattery());
        stmt.bindLong(7, entity.getTrainTimeYear());
        stmt.bindLong(8, entity.getTrainTimeMonth());
        stmt.bindLong(9, entity.getTrainTimeDay());
        stmt.bindLong(10, entity.getTrainTimeHour());
        stmt.bindLong(11, entity.getTrainTimeMinute());
        stmt.bindLong(12, entity.getTrainTimeSecond());
        stmt.bindLong(13, entity.getInterveneAccMinute());
        stmt.bindLong(14, entity.getIntervalAccMinute());
        stmt.bindLong(15, entity.getIntervalAccMinute2());
 
        String operationCmd = entity.getOperationCmd();
        if (operationCmd != null) {
            stmt.bindString(16, operationCmd);
        }
        stmt.bindLong(17, entity.getReceiveLocalTime());
 
        String receiveLocalTimeStr = entity.getReceiveLocalTimeStr();
        if (receiveLocalTimeStr != null) {
            stmt.bindString(18, receiveLocalTimeStr);
        }
        stmt.bindLong(19, entity.getIsReportedServer() ? 1L: 0L);
 
        String reserve1 = entity.getReserve1();
        if (reserve1 != null) {
            stmt.bindString(20, reserve1);
        }
 
        String reservedStr0 = entity.getReservedStr0();
        if (reservedStr0 != null) {
            stmt.bindString(21, reservedStr0);
        }
 
        String reservedStr1 = entity.getReservedStr1();
        if (reservedStr1 != null) {
            stmt.bindString(22, reservedStr1);
        }
 
        String reservedStr2 = entity.getReservedStr2();
        if (reservedStr2 != null) {
            stmt.bindString(23, reservedStr2);
        }
 
        String reservedStr3 = entity.getReservedStr3();
        if (reservedStr3 != null) {
            stmt.bindString(24, reservedStr3);
        }
        stmt.bindLong(25, entity.getReservedLong0());
        stmt.bindLong(26, entity.getReservedLong1());
        stmt.bindLong(27, entity.getReservedInt0());
        stmt.bindLong(28, entity.getReservedInt1());
        stmt.bindLong(29, entity.getReservedInt2());
        stmt.bindLong(30, entity.getReservedInt3());
        stmt.bindLong(31, entity.getReservedInt4());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CommonFeedBackBleDataDBBean entity) {
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
        stmt.bindLong(5, entity.getMearsureDistance());
        stmt.bindLong(6, entity.getBattery());
        stmt.bindLong(7, entity.getTrainTimeYear());
        stmt.bindLong(8, entity.getTrainTimeMonth());
        stmt.bindLong(9, entity.getTrainTimeDay());
        stmt.bindLong(10, entity.getTrainTimeHour());
        stmt.bindLong(11, entity.getTrainTimeMinute());
        stmt.bindLong(12, entity.getTrainTimeSecond());
        stmt.bindLong(13, entity.getInterveneAccMinute());
        stmt.bindLong(14, entity.getIntervalAccMinute());
        stmt.bindLong(15, entity.getIntervalAccMinute2());
 
        String operationCmd = entity.getOperationCmd();
        if (operationCmd != null) {
            stmt.bindString(16, operationCmd);
        }
        stmt.bindLong(17, entity.getReceiveLocalTime());
 
        String receiveLocalTimeStr = entity.getReceiveLocalTimeStr();
        if (receiveLocalTimeStr != null) {
            stmt.bindString(18, receiveLocalTimeStr);
        }
        stmt.bindLong(19, entity.getIsReportedServer() ? 1L: 0L);
 
        String reserve1 = entity.getReserve1();
        if (reserve1 != null) {
            stmt.bindString(20, reserve1);
        }
 
        String reservedStr0 = entity.getReservedStr0();
        if (reservedStr0 != null) {
            stmt.bindString(21, reservedStr0);
        }
 
        String reservedStr1 = entity.getReservedStr1();
        if (reservedStr1 != null) {
            stmt.bindString(22, reservedStr1);
        }
 
        String reservedStr2 = entity.getReservedStr2();
        if (reservedStr2 != null) {
            stmt.bindString(23, reservedStr2);
        }
 
        String reservedStr3 = entity.getReservedStr3();
        if (reservedStr3 != null) {
            stmt.bindString(24, reservedStr3);
        }
        stmt.bindLong(25, entity.getReservedLong0());
        stmt.bindLong(26, entity.getReservedLong1());
        stmt.bindLong(27, entity.getReservedInt0());
        stmt.bindLong(28, entity.getReservedInt1());
        stmt.bindLong(29, entity.getReservedInt2());
        stmt.bindLong(30, entity.getReservedInt3());
        stmt.bindLong(31, entity.getReservedInt4());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public CommonFeedBackBleDataDBBean readEntity(Cursor cursor, int offset) {
        CommonFeedBackBleDataDBBean entity = new CommonFeedBackBleDataDBBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // localid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.getLong(offset + 2), // currentUserCode
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // glassesMAC
            cursor.getInt(offset + 4), // mearsureDistance
            cursor.getInt(offset + 5), // battery
            cursor.getInt(offset + 6), // trainTimeYear
            cursor.getInt(offset + 7), // trainTimeMonth
            cursor.getInt(offset + 8), // trainTimeDay
            cursor.getInt(offset + 9), // trainTimeHour
            cursor.getInt(offset + 10), // trainTimeMinute
            cursor.getInt(offset + 11), // trainTimeSecond
            cursor.getInt(offset + 12), // interveneAccMinute
            cursor.getInt(offset + 13), // intervalAccMinute
            cursor.getInt(offset + 14), // intervalAccMinute2
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // operationCmd
            cursor.getLong(offset + 16), // receiveLocalTime
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // receiveLocalTimeStr
            cursor.getShort(offset + 18) != 0, // isReportedServer
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // reserve1
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // reservedStr0
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // reservedStr1
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // reservedStr2
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // reservedStr3
            cursor.getLong(offset + 24), // reservedLong0
            cursor.getLong(offset + 25), // reservedLong1
            cursor.getInt(offset + 26), // reservedInt0
            cursor.getInt(offset + 27), // reservedInt1
            cursor.getInt(offset + 28), // reservedInt2
            cursor.getInt(offset + 29), // reservedInt3
            cursor.getInt(offset + 30) // reservedInt4
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CommonFeedBackBleDataDBBean entity, int offset) {
        entity.setLocalid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCurrentUserCode(cursor.getLong(offset + 2));
        entity.setGlassesMAC(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMearsureDistance(cursor.getInt(offset + 4));
        entity.setBattery(cursor.getInt(offset + 5));
        entity.setTrainTimeYear(cursor.getInt(offset + 6));
        entity.setTrainTimeMonth(cursor.getInt(offset + 7));
        entity.setTrainTimeDay(cursor.getInt(offset + 8));
        entity.setTrainTimeHour(cursor.getInt(offset + 9));
        entity.setTrainTimeMinute(cursor.getInt(offset + 10));
        entity.setTrainTimeSecond(cursor.getInt(offset + 11));
        entity.setInterveneAccMinute(cursor.getInt(offset + 12));
        entity.setIntervalAccMinute(cursor.getInt(offset + 13));
        entity.setIntervalAccMinute2(cursor.getInt(offset + 14));
        entity.setOperationCmd(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setReceiveLocalTime(cursor.getLong(offset + 16));
        entity.setReceiveLocalTimeStr(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setIsReportedServer(cursor.getShort(offset + 18) != 0);
        entity.setReserve1(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setReservedStr0(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setReservedStr1(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setReservedStr2(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setReservedStr3(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setReservedLong0(cursor.getLong(offset + 24));
        entity.setReservedLong1(cursor.getLong(offset + 25));
        entity.setReservedInt0(cursor.getInt(offset + 26));
        entity.setReservedInt1(cursor.getInt(offset + 27));
        entity.setReservedInt2(cursor.getInt(offset + 28));
        entity.setReservedInt3(cursor.getInt(offset + 29));
        entity.setReservedInt4(cursor.getInt(offset + 30));
     }
    
    @Override
    protected final String updateKeyAfterInsert(CommonFeedBackBleDataDBBean entity, long rowId) {
        return entity.getLocalid();
    }
    
    @Override
    public String getKey(CommonFeedBackBleDataDBBean entity) {
        if(entity != null) {
            return entity.getLocalid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CommonFeedBackBleDataDBBean entity) {
        return entity.getLocalid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
