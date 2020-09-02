package com.zj.zhijue.glasses.greendao.msdao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zj.zhijue.greendao.greendaobean.UserInfoTrainTimeDBBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "usertraintimedb_tab".
*/
public class UserInfoTrainTimeDBBeanDao extends AbstractDao<UserInfoTrainTimeDBBean, String> {

    public static final String TABLENAME = "usertraintimedb_tab";

    /**
     * Properties of entity UserInfoTrainTimeDBBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Localid = new Property(0, String.class, "localid", true, "localid");
        public final static Property ServerId = new Property(1, String.class, "serverId", false, "serverId");
        public final static Property UserName = new Property(2, String.class, "userName", false, "userName");
        public final static Property TrainDate = new Property(3, long.class, "trainDate", false, "trainDate");
        public final static Property TrainDateStr = new Property(4, String.class, "trainDateStr", false, "trainDateStr");
        public final static Property TrainTime = new Property(5, float.class, "trainTime", false, "trainTime");
        public final static Property TrainTimeYear = new Property(6, int.class, "trainTimeYear", false, "trainTimeYear");
        public final static Property TrainTimeMonth = new Property(7, int.class, "trainTimeMonth", false, "trainTimeMonth");
        public final static Property TrainTimeDay = new Property(8, int.class, "trainTimeDay", false, "trainTimeDay");
        public final static Property TrainTimeHour = new Property(9, int.class, "trainTimeHour", false, "trainTimeHour");
        public final static Property TrainTimeMinute = new Property(10, int.class, "trainTimeMinute", false, "trainTimeMinute");
        public final static Property TrainTimeSecond = new Property(11, int.class, "trainTimeSecond", false, "trainTimeSecond");
        public final static Property TrainCount = new Property(12, int.class, "trainCount", false, "trainCount");
        public final static Property CreateTime = new Property(13, String.class, "createTime", false, "createTime");
        public final static Property UpdateTime = new Property(14, String.class, "updateTime", false, "updateTime");
        public final static Property UpdateTimeLong = new Property(15, long.class, "updateTimeLong", false, "updateTimeLong");
        public final static Property ReservedStr0 = new Property(16, String.class, "reservedStr0", false, "reservedStr0");
        public final static Property ReservedStr1 = new Property(17, String.class, "reservedStr1", false, "reservedStr1");
        public final static Property ReservedStr2 = new Property(18, String.class, "reservedStr2", false, "reservedStr2");
        public final static Property ReservedStr3 = new Property(19, String.class, "reservedStr3", false, "reservedStr3");
        public final static Property ReservedLong0 = new Property(20, long.class, "reservedLong0", false, "reservedLong0");
        public final static Property ReservedLong1 = new Property(21, long.class, "reservedLong1", false, "reservedLong1");
        public final static Property ReservedDouble0 = new Property(22, double.class, "reservedDouble0", false, "reservedDouble0");
        public final static Property ReservedDouble1 = new Property(23, double.class, "reservedDouble1", false, "reservedDouble1");
    }


    public UserInfoTrainTimeDBBeanDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoTrainTimeDBBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"usertraintimedb_tab\" (" + //
                "\"localid\" TEXT PRIMARY KEY NOT NULL ," + // 0: localid
                "\"serverId\" TEXT," + // 1: serverId
                "\"userName\" TEXT," + // 2: userName
                "\"trainDate\" INTEGER NOT NULL ," + // 3: trainDate
                "\"trainDateStr\" TEXT NOT NULL UNIQUE ," + // 4: trainDateStr
                "\"trainTime\" REAL NOT NULL ," + // 5: trainTime
                "\"trainTimeYear\" INTEGER NOT NULL ," + // 6: trainTimeYear
                "\"trainTimeMonth\" INTEGER NOT NULL ," + // 7: trainTimeMonth
                "\"trainTimeDay\" INTEGER NOT NULL ," + // 8: trainTimeDay
                "\"trainTimeHour\" INTEGER NOT NULL ," + // 9: trainTimeHour
                "\"trainTimeMinute\" INTEGER NOT NULL ," + // 10: trainTimeMinute
                "\"trainTimeSecond\" INTEGER NOT NULL ," + // 11: trainTimeSecond
                "\"trainCount\" INTEGER NOT NULL ," + // 12: trainCount
                "\"createTime\" TEXT NOT NULL ," + // 13: createTime
                "\"updateTime\" TEXT NOT NULL ," + // 14: updateTime
                "\"updateTimeLong\" INTEGER NOT NULL ," + // 15: updateTimeLong
                "\"reservedStr0\" TEXT," + // 16: reservedStr0
                "\"reservedStr1\" TEXT," + // 17: reservedStr1
                "\"reservedStr2\" TEXT," + // 18: reservedStr2
                "\"reservedStr3\" TEXT," + // 19: reservedStr3
                "\"reservedLong0\" INTEGER NOT NULL ," + // 20: reservedLong0
                "\"reservedLong1\" INTEGER NOT NULL ," + // 21: reservedLong1
                "\"reservedDouble0\" REAL NOT NULL ," + // 22: reservedDouble0
                "\"reservedDouble1\" REAL NOT NULL );"); // 23: reservedDouble1
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_usertraintimedb_tab_localid ON \"usertraintimedb_tab\"" +
                " (\"localid\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"usertraintimedb_tab\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserInfoTrainTimeDBBean entity) {
        stmt.clearBindings();
 
        String localid = entity.getLocalid();
        if (localid != null) {
            stmt.bindString(1, localid);
        }
 
        String serverId = entity.getServerId();
        if (serverId != null) {
            stmt.bindString(2, serverId);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
        stmt.bindLong(4, entity.getTrainDate());
        stmt.bindString(5, entity.getTrainDateStr());
        stmt.bindDouble(6, entity.getTrainTime());
        stmt.bindLong(7, entity.getTrainTimeYear());
        stmt.bindLong(8, entity.getTrainTimeMonth());
        stmt.bindLong(9, entity.getTrainTimeDay());
        stmt.bindLong(10, entity.getTrainTimeHour());
        stmt.bindLong(11, entity.getTrainTimeMinute());
        stmt.bindLong(12, entity.getTrainTimeSecond());
        stmt.bindLong(13, entity.getTrainCount());
        stmt.bindString(14, entity.getCreateTime());
        stmt.bindString(15, entity.getUpdateTime());
        stmt.bindLong(16, entity.getUpdateTimeLong());
 
        String reservedStr0 = entity.getReservedStr0();
        if (reservedStr0 != null) {
            stmt.bindString(17, reservedStr0);
        }
 
        String reservedStr1 = entity.getReservedStr1();
        if (reservedStr1 != null) {
            stmt.bindString(18, reservedStr1);
        }
 
        String reservedStr2 = entity.getReservedStr2();
        if (reservedStr2 != null) {
            stmt.bindString(19, reservedStr2);
        }
 
        String reservedStr3 = entity.getReservedStr3();
        if (reservedStr3 != null) {
            stmt.bindString(20, reservedStr3);
        }
        stmt.bindLong(21, entity.getReservedLong0());
        stmt.bindLong(22, entity.getReservedLong1());
        stmt.bindDouble(23, entity.getReservedDouble0());
        stmt.bindDouble(24, entity.getReservedDouble1());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserInfoTrainTimeDBBean entity) {
        stmt.clearBindings();
 
        String localid = entity.getLocalid();
        if (localid != null) {
            stmt.bindString(1, localid);
        }
 
        String serverId = entity.getServerId();
        if (serverId != null) {
            stmt.bindString(2, serverId);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
        stmt.bindLong(4, entity.getTrainDate());
        stmt.bindString(5, entity.getTrainDateStr());
        stmt.bindDouble(6, entity.getTrainTime());
        stmt.bindLong(7, entity.getTrainTimeYear());
        stmt.bindLong(8, entity.getTrainTimeMonth());
        stmt.bindLong(9, entity.getTrainTimeDay());
        stmt.bindLong(10, entity.getTrainTimeHour());
        stmt.bindLong(11, entity.getTrainTimeMinute());
        stmt.bindLong(12, entity.getTrainTimeSecond());
        stmt.bindLong(13, entity.getTrainCount());
        stmt.bindString(14, entity.getCreateTime());
        stmt.bindString(15, entity.getUpdateTime());
        stmt.bindLong(16, entity.getUpdateTimeLong());
 
        String reservedStr0 = entity.getReservedStr0();
        if (reservedStr0 != null) {
            stmt.bindString(17, reservedStr0);
        }
 
        String reservedStr1 = entity.getReservedStr1();
        if (reservedStr1 != null) {
            stmt.bindString(18, reservedStr1);
        }
 
        String reservedStr2 = entity.getReservedStr2();
        if (reservedStr2 != null) {
            stmt.bindString(19, reservedStr2);
        }
 
        String reservedStr3 = entity.getReservedStr3();
        if (reservedStr3 != null) {
            stmt.bindString(20, reservedStr3);
        }
        stmt.bindLong(21, entity.getReservedLong0());
        stmt.bindLong(22, entity.getReservedLong1());
        stmt.bindDouble(23, entity.getReservedDouble0());
        stmt.bindDouble(24, entity.getReservedDouble1());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public UserInfoTrainTimeDBBean readEntity(Cursor cursor, int offset) {
        UserInfoTrainTimeDBBean entity = new UserInfoTrainTimeDBBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // localid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // serverId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userName
            cursor.getLong(offset + 3), // trainDate
            cursor.getString(offset + 4), // trainDateStr
            cursor.getFloat(offset + 5), // trainTime
            cursor.getInt(offset + 6), // trainTimeYear
            cursor.getInt(offset + 7), // trainTimeMonth
            cursor.getInt(offset + 8), // trainTimeDay
            cursor.getInt(offset + 9), // trainTimeHour
            cursor.getInt(offset + 10), // trainTimeMinute
            cursor.getInt(offset + 11), // trainTimeSecond
            cursor.getInt(offset + 12), // trainCount
            cursor.getString(offset + 13), // createTime
            cursor.getString(offset + 14), // updateTime
            cursor.getLong(offset + 15), // updateTimeLong
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // reservedStr0
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // reservedStr1
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // reservedStr2
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // reservedStr3
            cursor.getLong(offset + 20), // reservedLong0
            cursor.getLong(offset + 21), // reservedLong1
            cursor.getDouble(offset + 22), // reservedDouble0
            cursor.getDouble(offset + 23) // reservedDouble1
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserInfoTrainTimeDBBean entity, int offset) {
        entity.setLocalid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setServerId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTrainDate(cursor.getLong(offset + 3));
        entity.setTrainDateStr(cursor.getString(offset + 4));
        entity.setTrainTime(cursor.getFloat(offset + 5));
        entity.setTrainTimeYear(cursor.getInt(offset + 6));
        entity.setTrainTimeMonth(cursor.getInt(offset + 7));
        entity.setTrainTimeDay(cursor.getInt(offset + 8));
        entity.setTrainTimeHour(cursor.getInt(offset + 9));
        entity.setTrainTimeMinute(cursor.getInt(offset + 10));
        entity.setTrainTimeSecond(cursor.getInt(offset + 11));
        entity.setTrainCount(cursor.getInt(offset + 12));
        entity.setCreateTime(cursor.getString(offset + 13));
        entity.setUpdateTime(cursor.getString(offset + 14));
        entity.setUpdateTimeLong(cursor.getLong(offset + 15));
        entity.setReservedStr0(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setReservedStr1(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setReservedStr2(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setReservedStr3(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setReservedLong0(cursor.getLong(offset + 20));
        entity.setReservedLong1(cursor.getLong(offset + 21));
        entity.setReservedDouble0(cursor.getDouble(offset + 22));
        entity.setReservedDouble1(cursor.getDouble(offset + 23));
     }
    
    @Override
    protected final String updateKeyAfterInsert(UserInfoTrainTimeDBBean entity, long rowId) {
        return entity.getLocalid();
    }
    
    @Override
    public String getKey(UserInfoTrainTimeDBBean entity) {
        if(entity != null) {
            return entity.getLocalid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserInfoTrainTimeDBBean entity) {
        return entity.getLocalid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
