package com.zj.zhijue.glasses.greendao.msdao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zj.zhijue.greendao.greendaobean.UserSignInDBBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "usersignindb_tab".
*/
public class UserSignInDBBeanDao extends AbstractDao<UserSignInDBBean, String> {

    public static final String TABLENAME = "usersignindb_tab";

    /**
     * Properties of entity UserSignInDBBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Signinserverid = new Property(0, String.class, "signinserverid", true, "signinserverid");
        public final static Property Member_id = new Property(1, String.class, "member_id", false, "member_id");
        public final static Property Login_name = new Property(2, String.class, "login_name", false, "login_name");
        public final static Property Nick_name = new Property(3, String.class, "nick_name", false, "nick_name");
        public final static Property Name = new Property(4, String.class, "name", false, "name");
        public final static Property Sign_time = new Property(5, String.class, "sign_time", false, "sign_time");
        public final static Property Sign_time_long = new Property(6, Long.class, "sign_time_long", false, "sign_time_long");
        public final static Property ReservedStr0 = new Property(7, String.class, "reservedStr0", false, "reservedStr0");
        public final static Property ReservedStr1 = new Property(8, String.class, "reservedStr1", false, "reservedStr1");
        public final static Property ReservedStr2 = new Property(9, String.class, "reservedStr2", false, "reservedStr2");
        public final static Property ReservedStr3 = new Property(10, String.class, "reservedStr3", false, "reservedStr3");
        public final static Property ReservedLong0 = new Property(11, long.class, "reservedLong0", false, "reservedLong0");
        public final static Property ReservedLong1 = new Property(12, long.class, "reservedLong1", false, "reservedLong1");
        public final static Property ReservedDouble0 = new Property(13, double.class, "reservedDouble0", false, "reservedDouble0");
        public final static Property ReservedDouble1 = new Property(14, double.class, "reservedDouble1", false, "reservedDouble1");
    }


    public UserSignInDBBeanDao(DaoConfig config) {
        super(config);
    }
    
    public UserSignInDBBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"usersignindb_tab\" (" + //
                "\"signinserverid\" TEXT PRIMARY KEY NOT NULL UNIQUE ," + // 0: signinserverid
                "\"member_id\" TEXT," + // 1: member_id
                "\"login_name\" TEXT," + // 2: login_name
                "\"nick_name\" TEXT," + // 3: nick_name
                "\"name\" TEXT," + // 4: name
                "\"sign_time\" TEXT," + // 5: sign_time
                "\"sign_time_long\" INTEGER," + // 6: sign_time_long
                "\"reservedStr0\" TEXT," + // 7: reservedStr0
                "\"reservedStr1\" TEXT," + // 8: reservedStr1
                "\"reservedStr2\" TEXT," + // 9: reservedStr2
                "\"reservedStr3\" TEXT," + // 10: reservedStr3
                "\"reservedLong0\" INTEGER NOT NULL ," + // 11: reservedLong0
                "\"reservedLong1\" INTEGER NOT NULL ," + // 12: reservedLong1
                "\"reservedDouble0\" REAL NOT NULL ," + // 13: reservedDouble0
                "\"reservedDouble1\" REAL NOT NULL );"); // 14: reservedDouble1
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"usersignindb_tab\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserSignInDBBean entity) {
        stmt.clearBindings();
 
        String signinserverid = entity.getSigninserverid();
        if (signinserverid != null) {
            stmt.bindString(1, signinserverid);
        }
 
        String member_id = entity.getMember_id();
        if (member_id != null) {
            stmt.bindString(2, member_id);
        }
 
        String login_name = entity.getLogin_name();
        if (login_name != null) {
            stmt.bindString(3, login_name);
        }
 
        String nick_name = entity.getNick_name();
        if (nick_name != null) {
            stmt.bindString(4, nick_name);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
 
        String sign_time = entity.getSign_time();
        if (sign_time != null) {
            stmt.bindString(6, sign_time);
        }
 
        Long sign_time_long = entity.getSign_time_long();
        if (sign_time_long != null) {
            stmt.bindLong(7, sign_time_long);
        }
 
        String reservedStr0 = entity.getReservedStr0();
        if (reservedStr0 != null) {
            stmt.bindString(8, reservedStr0);
        }
 
        String reservedStr1 = entity.getReservedStr1();
        if (reservedStr1 != null) {
            stmt.bindString(9, reservedStr1);
        }
 
        String reservedStr2 = entity.getReservedStr2();
        if (reservedStr2 != null) {
            stmt.bindString(10, reservedStr2);
        }
 
        String reservedStr3 = entity.getReservedStr3();
        if (reservedStr3 != null) {
            stmt.bindString(11, reservedStr3);
        }
        stmt.bindLong(12, entity.getReservedLong0());
        stmt.bindLong(13, entity.getReservedLong1());
        stmt.bindDouble(14, entity.getReservedDouble0());
        stmt.bindDouble(15, entity.getReservedDouble1());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserSignInDBBean entity) {
        stmt.clearBindings();
 
        String signinserverid = entity.getSigninserverid();
        if (signinserverid != null) {
            stmt.bindString(1, signinserverid);
        }
 
        String member_id = entity.getMember_id();
        if (member_id != null) {
            stmt.bindString(2, member_id);
        }
 
        String login_name = entity.getLogin_name();
        if (login_name != null) {
            stmt.bindString(3, login_name);
        }
 
        String nick_name = entity.getNick_name();
        if (nick_name != null) {
            stmt.bindString(4, nick_name);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
 
        String sign_time = entity.getSign_time();
        if (sign_time != null) {
            stmt.bindString(6, sign_time);
        }
 
        Long sign_time_long = entity.getSign_time_long();
        if (sign_time_long != null) {
            stmt.bindLong(7, sign_time_long);
        }
 
        String reservedStr0 = entity.getReservedStr0();
        if (reservedStr0 != null) {
            stmt.bindString(8, reservedStr0);
        }
 
        String reservedStr1 = entity.getReservedStr1();
        if (reservedStr1 != null) {
            stmt.bindString(9, reservedStr1);
        }
 
        String reservedStr2 = entity.getReservedStr2();
        if (reservedStr2 != null) {
            stmt.bindString(10, reservedStr2);
        }
 
        String reservedStr3 = entity.getReservedStr3();
        if (reservedStr3 != null) {
            stmt.bindString(11, reservedStr3);
        }
        stmt.bindLong(12, entity.getReservedLong0());
        stmt.bindLong(13, entity.getReservedLong1());
        stmt.bindDouble(14, entity.getReservedDouble0());
        stmt.bindDouble(15, entity.getReservedDouble1());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public UserSignInDBBean readEntity(Cursor cursor, int offset) {
        UserSignInDBBean entity = new UserSignInDBBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // signinserverid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // member_id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // login_name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nick_name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // name
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // sign_time
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // sign_time_long
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // reservedStr0
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // reservedStr1
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // reservedStr2
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // reservedStr3
            cursor.getLong(offset + 11), // reservedLong0
            cursor.getLong(offset + 12), // reservedLong1
            cursor.getDouble(offset + 13), // reservedDouble0
            cursor.getDouble(offset + 14) // reservedDouble1
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserSignInDBBean entity, int offset) {
        entity.setSigninserverid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setMember_id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLogin_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNick_name(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSign_time(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSign_time_long(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
        entity.setReservedStr0(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setReservedStr1(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setReservedStr2(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setReservedStr3(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setReservedLong0(cursor.getLong(offset + 11));
        entity.setReservedLong1(cursor.getLong(offset + 12));
        entity.setReservedDouble0(cursor.getDouble(offset + 13));
        entity.setReservedDouble1(cursor.getDouble(offset + 14));
     }
    
    @Override
    protected final String updateKeyAfterInsert(UserSignInDBBean entity, long rowId) {
        return entity.getSigninserverid();
    }
    
    @Override
    public String getKey(UserSignInDBBean entity) {
        if(entity != null) {
            return entity.getSigninserverid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserSignInDBBean entity) {
        return entity.getSigninserverid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
