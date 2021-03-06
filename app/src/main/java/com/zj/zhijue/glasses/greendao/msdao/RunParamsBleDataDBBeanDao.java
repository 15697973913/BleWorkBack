package com.zj.zhijue.glasses.greendao.msdao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zj.zhijue.greendao.greendaobean.RunParamsBleDataDBBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "runparams_bledata_tab".
*/
public class RunParamsBleDataDBBeanDao extends AbstractDao<RunParamsBleDataDBBean, String> {

    public static final String TABLENAME = "runparams_bledata_tab";

    /**
     * Properties of entity RunParamsBleDataDBBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Localid = new Property(0, String.class, "localid", true, "localid");
        public final static Property UserId = new Property(1, String.class, "userId", false, "userId");
        public final static Property GlassesMAC = new Property(2, String.class, "glassesMAC", false, "glassesMAC");
        public final static Property CurrentUserCode = new Property(3, long.class, "currentUserCode", false, "currentUserCode");
        public final static Property MinMinusInterval = new Property(4, int.class, "minMinusInterval", false, "minMinusInterval");
        public final static Property MinPlusInterval = new Property(5, int.class, "minPlusInterval", false, "minPlusInterval");
        public final static Property CommonNumber = new Property(6, int.class, "commonNumber", false, "commonNumber");
        public final static Property InterveneAccMinute = new Property(7, int.class, "interveneAccMinute", false, "interveneAccMinute");
        public final static Property WeekKeyFre = new Property(8, int.class, "weekKeyFre", false, "weekKeyFre");
        public final static Property WeekAccMinute = new Property(9, int.class, "weekAccMinute", false, "weekAccMinute");
        public final static Property BackWeekAccMinute0 = new Property(10, int.class, "backWeekAccMinute0", false, "backWeekAccMinute0");
        public final static Property BackWeekAccMinute1 = new Property(11, int.class, "backWeekAccMinute1", false, "backWeekAccMinute1");
        public final static Property BackWeekAccMinute2 = new Property(12, int.class, "backWeekAccMinute2", false, "backWeekAccMinute2");
        public final static Property BackWeekAccMinute3 = new Property(13, int.class, "backWeekAccMinute3", false, "backWeekAccMinute3");
        public final static Property PlusInterval = new Property(14, int.class, "plusInterval", false, "plusInterval");
        public final static Property MinusInterval = new Property(15, int.class, "minusInterval", false, "minusInterval");
        public final static Property PlusInc = new Property(16, int.class, "plusInc", false, "plusInc");
        public final static Property MinusInc = new Property(17, int.class, "minusInc", false, "minusInc");
        public final static Property IncPer = new Property(18, int.class, "incPer", false, "incPer");
        public final static Property RunNumber = new Property(19, int.class, "runNumber", false, "runNumber");
        public final static Property RunSpeed = new Property(20, int.class, "runSpeed", false, "runSpeed");
        public final static Property SpeedInc = new Property(21, int.class, "speedInc", false, "speedInc");
        public final static Property SpeedSegment = new Property(22, int.class, "speedSegment", false, "speedSegment");
        public final static Property IntervalSegment = new Property(23, int.class, "intervalSegment", false, "intervalSegment");
        public final static Property BackSpeedSegment = new Property(24, int.class, "backSpeedSegment", false, "backSpeedSegment");
        public final static Property BackIntervalSegment = new Property(25, int.class, "backIntervalSegment", false, "backIntervalSegment");
        public final static Property SpeedKeyFre = new Property(26, int.class, "speedKeyFre", false, "speedKeyFre");
        public final static Property InterveneKeyFre = new Property(27, int.class, "interveneKeyFre", false, "interveneKeyFre");
        public final static Property IntervalAccMinute = new Property(28, int.class, "intervalAccMinute", false, "intervalAccMinute");
        public final static Property MinusInterval2 = new Property(29, int.class, "minusInterval2", false, "minusInterval2");
        public final static Property PlusInterval2 = new Property(30, int.class, "plusInterval2", false, "plusInterval2");
        public final static Property MinusInc2 = new Property(31, int.class, "minusInc2", false, "minusInc2");
        public final static Property PlusInc2 = new Property(32, int.class, "plusInc2", false, "plusInc2");
        public final static Property IncPer2 = new Property(33, int.class, "incPer2", false, "incPer2");
        public final static Property RunNumber2 = new Property(34, int.class, "runNumber2", false, "runNumber2");
        public final static Property RunSpeed2 = new Property(35, int.class, "runSpeed2", false, "runSpeed2");
        public final static Property SpeedSegment2 = new Property(36, int.class, "speedSegment2", false, "speedSegment2");
        public final static Property SpeedInc2 = new Property(37, int.class, "speedInc2", false, "speedInc2");
        public final static Property IntervalSegment2 = new Property(38, int.class, "intervalSegment2", false, "intervalSegment2");
        public final static Property BackSpeedSegment2 = new Property(39, int.class, "backSpeedSegment2", false, "backSpeedSegment2");
        public final static Property BackIntervalSegment2 = new Property(40, int.class, "backIntervalSegment2", false, "backIntervalSegment2");
        public final static Property SpeedKeyFre2 = new Property(41, int.class, "speedKeyFre2", false, "speedKeyFre2");
        public final static Property InterveneKeyFre2 = new Property(42, int.class, "interveneKeyFre2", false, "interveneKeyFre2");
        public final static Property IntervalAccMinute2 = new Property(43, int.class, "intervalAccMinute2", false, "intervalAccMinute2");
        public final static Property CurrentUserNewUser = new Property(44, int.class, "currentUserNewUser", false, "currentUserNewUser");
        public final static Property MonitorDataCMD = new Property(45, String.class, "monitorDataCMD", false, "monitorDataCMD");
        public final static Property ReceiveLocalTime = new Property(46, long.class, "receiveLocalTime", false, "receiveLocalTime");
        public final static Property ReceiveLocalTimeStr = new Property(47, String.class, "receiveLocalTimeStr", false, "receiveLocalTimeStr");
        public final static Property IsReportedServer = new Property(48, boolean.class, "isReportedServer", false, "isReportedServer");
        public final static Property TrainingState = new Property(49, int.class, "trainingState", false, "trainingState");
        public final static Property TrainingState2 = new Property(50, int.class, "trainingState2", false, "TrainingState2");
        public final static Property AdjustSpeed = new Property(51, int.class, "AdjustSpeed", false, "AdjustSpeed");
        public final static Property MaxRunSpeed = new Property(52, int.class, "MaxRunSpeed", false, "MaxRunSpeed");
        public final static Property MinRunSpeed = new Property(53, int.class, "MinRunSpeed", false, "MinRunSpeed");
        public final static Property AdjustSpeed2 = new Property(54, int.class, "AdjustSpeed2", false, "AdjustSpeed2");
        public final static Property MaxRunSpeed2 = new Property(55, int.class, "MaxRunSpeed2", false, "MaxRunSpeed2");
        public final static Property MinRunSpeed2 = new Property(56, int.class, "MinRunSpeed2", false, "MinRunSpeed2");
        public final static Property TxByte12 = new Property(57, int.class, "txByte12", false, "txByte12");
        public final static Property TxByte13 = new Property(58, int.class, "txByte13", false, "txByte13");
        public final static Property TxByte14 = new Property(59, int.class, "txByte14", false, "txByte14");
        public final static Property TxByte15 = new Property(60, int.class, "txByte15", false, "txByte15");
        public final static Property TxByte16 = new Property(61, int.class, "txByte16", false, "txByte16");
        public final static Property TxByte17 = new Property(62, int.class, "txByte17", false, "txByte17");
        public final static Property TxByte18 = new Property(63, int.class, "txByte18", false, "txByte18");
        public final static Property Reserve1 = new Property(64, String.class, "reserve1", false, "reserve1");
        public final static Property ReservedStr0 = new Property(65, String.class, "reservedStr0", false, "reservedStr0");
        public final static Property ReservedStr1 = new Property(66, String.class, "reservedStr1", false, "reservedStr1");
        public final static Property ReservedStr2 = new Property(67, String.class, "reservedStr2", false, "reservedStr2");
        public final static Property ReservedStr3 = new Property(68, String.class, "reservedStr3", false, "reservedStr3");
        public final static Property ReservedLong0 = new Property(69, long.class, "reservedLong0", false, "reservedLong0");
        public final static Property ReservedLong1 = new Property(70, long.class, "reservedLong1", false, "reservedLong1");
        public final static Property ReservedInt0 = new Property(71, int.class, "reservedInt0", false, "reservedInt0");
        public final static Property ReservedInt1 = new Property(72, int.class, "reservedInt1", false, "reservedInt1");
        public final static Property ReservedInt2 = new Property(73, int.class, "reservedInt2", false, "reservedInt2");
        public final static Property ReservedInt3 = new Property(74, int.class, "reservedInt3", false, "reservedInt3");
        public final static Property ReservedInt4 = new Property(75, int.class, "reservedInt4", false, "reservedInt4");
    }


    public RunParamsBleDataDBBeanDao(DaoConfig config) {
        super(config);
    }
    
    public RunParamsBleDataDBBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"runparams_bledata_tab\" (" + //
                "\"localid\" TEXT PRIMARY KEY NOT NULL ," + // 0: localid
                "\"userId\" TEXT," + // 1: userId
                "\"glassesMAC\" TEXT," + // 2: glassesMAC
                "\"currentUserCode\" INTEGER NOT NULL ," + // 3: currentUserCode
                "\"minMinusInterval\" INTEGER NOT NULL ," + // 4: minMinusInterval
                "\"minPlusInterval\" INTEGER NOT NULL ," + // 5: minPlusInterval
                "\"commonNumber\" INTEGER NOT NULL ," + // 6: commonNumber
                "\"interveneAccMinute\" INTEGER NOT NULL ," + // 7: interveneAccMinute
                "\"weekKeyFre\" INTEGER NOT NULL ," + // 8: weekKeyFre
                "\"weekAccMinute\" INTEGER NOT NULL ," + // 9: weekAccMinute
                "\"backWeekAccMinute0\" INTEGER NOT NULL ," + // 10: backWeekAccMinute0
                "\"backWeekAccMinute1\" INTEGER NOT NULL ," + // 11: backWeekAccMinute1
                "\"backWeekAccMinute2\" INTEGER NOT NULL ," + // 12: backWeekAccMinute2
                "\"backWeekAccMinute3\" INTEGER NOT NULL ," + // 13: backWeekAccMinute3
                "\"plusInterval\" INTEGER NOT NULL ," + // 14: plusInterval
                "\"minusInterval\" INTEGER NOT NULL ," + // 15: minusInterval
                "\"plusInc\" INTEGER NOT NULL ," + // 16: plusInc
                "\"minusInc\" INTEGER NOT NULL ," + // 17: minusInc
                "\"incPer\" INTEGER NOT NULL ," + // 18: incPer
                "\"runNumber\" INTEGER NOT NULL ," + // 19: runNumber
                "\"runSpeed\" INTEGER NOT NULL ," + // 20: runSpeed
                "\"speedInc\" INTEGER NOT NULL ," + // 21: speedInc
                "\"speedSegment\" INTEGER NOT NULL ," + // 22: speedSegment
                "\"intervalSegment\" INTEGER NOT NULL ," + // 23: intervalSegment
                "\"backSpeedSegment\" INTEGER NOT NULL ," + // 24: backSpeedSegment
                "\"backIntervalSegment\" INTEGER NOT NULL ," + // 25: backIntervalSegment
                "\"speedKeyFre\" INTEGER NOT NULL ," + // 26: speedKeyFre
                "\"interveneKeyFre\" INTEGER NOT NULL ," + // 27: interveneKeyFre
                "\"intervalAccMinute\" INTEGER NOT NULL ," + // 28: intervalAccMinute
                "\"minusInterval2\" INTEGER NOT NULL ," + // 29: minusInterval2
                "\"plusInterval2\" INTEGER NOT NULL ," + // 30: plusInterval2
                "\"minusInc2\" INTEGER NOT NULL ," + // 31: minusInc2
                "\"plusInc2\" INTEGER NOT NULL ," + // 32: plusInc2
                "\"incPer2\" INTEGER NOT NULL ," + // 33: incPer2
                "\"runNumber2\" INTEGER NOT NULL ," + // 34: runNumber2
                "\"runSpeed2\" INTEGER NOT NULL ," + // 35: runSpeed2
                "\"speedSegment2\" INTEGER NOT NULL ," + // 36: speedSegment2
                "\"speedInc2\" INTEGER NOT NULL ," + // 37: speedInc2
                "\"intervalSegment2\" INTEGER NOT NULL ," + // 38: intervalSegment2
                "\"backSpeedSegment2\" INTEGER NOT NULL ," + // 39: backSpeedSegment2
                "\"backIntervalSegment2\" INTEGER NOT NULL ," + // 40: backIntervalSegment2
                "\"speedKeyFre2\" INTEGER NOT NULL ," + // 41: speedKeyFre2
                "\"interveneKeyFre2\" INTEGER NOT NULL ," + // 42: interveneKeyFre2
                "\"intervalAccMinute2\" INTEGER NOT NULL ," + // 43: intervalAccMinute2
                "\"currentUserNewUser\" INTEGER NOT NULL ," + // 44: currentUserNewUser
                "\"monitorDataCMD\" TEXT," + // 45: monitorDataCMD
                "\"receiveLocalTime\" INTEGER NOT NULL ," + // 46: receiveLocalTime
                "\"receiveLocalTimeStr\" TEXT," + // 47: receiveLocalTimeStr
                "\"isReportedServer\" INTEGER NOT NULL ," + // 48: isReportedServer
                "\"trainingState\" INTEGER NOT NULL ," + // 49: trainingState
                "\"TrainingState2\" INTEGER NOT NULL ," + // 50: trainingState2
                "\"AdjustSpeed\" INTEGER NOT NULL ," + // 51: AdjustSpeed
                "\"MaxRunSpeed\" INTEGER NOT NULL ," + // 52: MaxRunSpeed
                "\"MinRunSpeed\" INTEGER NOT NULL ," + // 53: MinRunSpeed
                "\"AdjustSpeed2\" INTEGER NOT NULL ," + // 54: AdjustSpeed2
                "\"MaxRunSpeed2\" INTEGER NOT NULL ," + // 55: MaxRunSpeed2
                "\"MinRunSpeed2\" INTEGER NOT NULL ," + // 56: MinRunSpeed2
                "\"txByte12\" INTEGER NOT NULL ," + // 57: txByte12
                "\"txByte13\" INTEGER NOT NULL ," + // 58: txByte13
                "\"txByte14\" INTEGER NOT NULL ," + // 59: txByte14
                "\"txByte15\" INTEGER NOT NULL ," + // 60: txByte15
                "\"txByte16\" INTEGER NOT NULL ," + // 61: txByte16
                "\"txByte17\" INTEGER NOT NULL ," + // 62: txByte17
                "\"txByte18\" INTEGER NOT NULL ," + // 63: txByte18
                "\"reserve1\" TEXT," + // 64: reserve1
                "\"reservedStr0\" TEXT," + // 65: reservedStr0
                "\"reservedStr1\" TEXT," + // 66: reservedStr1
                "\"reservedStr2\" TEXT," + // 67: reservedStr2
                "\"reservedStr3\" TEXT," + // 68: reservedStr3
                "\"reservedLong0\" INTEGER NOT NULL ," + // 69: reservedLong0
                "\"reservedLong1\" INTEGER NOT NULL ," + // 70: reservedLong1
                "\"reservedInt0\" INTEGER NOT NULL ," + // 71: reservedInt0
                "\"reservedInt1\" INTEGER NOT NULL ," + // 72: reservedInt1
                "\"reservedInt2\" INTEGER NOT NULL ," + // 73: reservedInt2
                "\"reservedInt3\" INTEGER NOT NULL ," + // 74: reservedInt3
                "\"reservedInt4\" INTEGER NOT NULL );"); // 75: reservedInt4
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_runparams_bledata_tab_localid ON \"runparams_bledata_tab\"" +
                " (\"localid\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"runparams_bledata_tab\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, RunParamsBleDataDBBean entity) {
        stmt.clearBindings();
 
        String localid = entity.getLocalid();
        if (localid != null) {
            stmt.bindString(1, localid);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String glassesMAC = entity.getGlassesMAC();
        if (glassesMAC != null) {
            stmt.bindString(3, glassesMAC);
        }
        stmt.bindLong(4, entity.getCurrentUserCode());
        stmt.bindLong(5, entity.getMinMinusInterval());
        stmt.bindLong(6, entity.getMinPlusInterval());
        stmt.bindLong(7, entity.getCommonNumber());
        stmt.bindLong(8, entity.getInterveneAccMinute());
        stmt.bindLong(9, entity.getWeekKeyFre());
        stmt.bindLong(10, entity.getWeekAccMinute());
        stmt.bindLong(11, entity.getBackWeekAccMinute0());
        stmt.bindLong(12, entity.getBackWeekAccMinute1());
        stmt.bindLong(13, entity.getBackWeekAccMinute2());
        stmt.bindLong(14, entity.getBackWeekAccMinute3());
        stmt.bindLong(15, entity.getPlusInterval());
        stmt.bindLong(16, entity.getMinusInterval());
        stmt.bindLong(17, entity.getPlusInc());
        stmt.bindLong(18, entity.getMinusInc());
        stmt.bindLong(19, entity.getIncPer());
        stmt.bindLong(20, entity.getRunNumber());
        stmt.bindLong(21, entity.getRunSpeed());
        stmt.bindLong(22, entity.getSpeedInc());
        stmt.bindLong(23, entity.getSpeedSegment());
        stmt.bindLong(24, entity.getIntervalSegment());
        stmt.bindLong(25, entity.getBackSpeedSegment());
        stmt.bindLong(26, entity.getBackIntervalSegment());
        stmt.bindLong(27, entity.getSpeedKeyFre());
        stmt.bindLong(28, entity.getInterveneKeyFre());
        stmt.bindLong(29, entity.getIntervalAccMinute());
        stmt.bindLong(30, entity.getMinusInterval2());
        stmt.bindLong(31, entity.getPlusInterval2());
        stmt.bindLong(32, entity.getMinusInc2());
        stmt.bindLong(33, entity.getPlusInc2());
        stmt.bindLong(34, entity.getIncPer2());
        stmt.bindLong(35, entity.getRunNumber2());
        stmt.bindLong(36, entity.getRunSpeed2());
        stmt.bindLong(37, entity.getSpeedSegment2());
        stmt.bindLong(38, entity.getSpeedInc2());
        stmt.bindLong(39, entity.getIntervalSegment2());
        stmt.bindLong(40, entity.getBackSpeedSegment2());
        stmt.bindLong(41, entity.getBackIntervalSegment2());
        stmt.bindLong(42, entity.getSpeedKeyFre2());
        stmt.bindLong(43, entity.getInterveneKeyFre2());
        stmt.bindLong(44, entity.getIntervalAccMinute2());
        stmt.bindLong(45, entity.getCurrentUserNewUser());
 
        String monitorDataCMD = entity.getMonitorDataCMD();
        if (monitorDataCMD != null) {
            stmt.bindString(46, monitorDataCMD);
        }
        stmt.bindLong(47, entity.getReceiveLocalTime());
 
        String receiveLocalTimeStr = entity.getReceiveLocalTimeStr();
        if (receiveLocalTimeStr != null) {
            stmt.bindString(48, receiveLocalTimeStr);
        }
        stmt.bindLong(49, entity.getIsReportedServer() ? 1L: 0L);
        stmt.bindLong(50, entity.getTrainingState());
        stmt.bindLong(51, entity.getTrainingState2());
        stmt.bindLong(52, entity.getAdjustSpeed());
        stmt.bindLong(53, entity.getMaxRunSpeed());
        stmt.bindLong(54, entity.getMinRunSpeed());
        stmt.bindLong(55, entity.getAdjustSpeed2());
        stmt.bindLong(56, entity.getMaxRunSpeed2());
        stmt.bindLong(57, entity.getMinRunSpeed2());
        stmt.bindLong(58, entity.getTxByte12());
        stmt.bindLong(59, entity.getTxByte13());
        stmt.bindLong(60, entity.getTxByte14());
        stmt.bindLong(61, entity.getTxByte15());
        stmt.bindLong(62, entity.getTxByte16());
        stmt.bindLong(63, entity.getTxByte17());
        stmt.bindLong(64, entity.getTxByte18());
 
        String reserve1 = entity.getReserve1();
        if (reserve1 != null) {
            stmt.bindString(65, reserve1);
        }
 
        String reservedStr0 = entity.getReservedStr0();
        if (reservedStr0 != null) {
            stmt.bindString(66, reservedStr0);
        }
 
        String reservedStr1 = entity.getReservedStr1();
        if (reservedStr1 != null) {
            stmt.bindString(67, reservedStr1);
        }
 
        String reservedStr2 = entity.getReservedStr2();
        if (reservedStr2 != null) {
            stmt.bindString(68, reservedStr2);
        }
 
        String reservedStr3 = entity.getReservedStr3();
        if (reservedStr3 != null) {
            stmt.bindString(69, reservedStr3);
        }
        stmt.bindLong(70, entity.getReservedLong0());
        stmt.bindLong(71, entity.getReservedLong1());
        stmt.bindLong(72, entity.getReservedInt0());
        stmt.bindLong(73, entity.getReservedInt1());
        stmt.bindLong(74, entity.getReservedInt2());
        stmt.bindLong(75, entity.getReservedInt3());
        stmt.bindLong(76, entity.getReservedInt4());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, RunParamsBleDataDBBean entity) {
        stmt.clearBindings();
 
        String localid = entity.getLocalid();
        if (localid != null) {
            stmt.bindString(1, localid);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String glassesMAC = entity.getGlassesMAC();
        if (glassesMAC != null) {
            stmt.bindString(3, glassesMAC);
        }
        stmt.bindLong(4, entity.getCurrentUserCode());
        stmt.bindLong(5, entity.getMinMinusInterval());
        stmt.bindLong(6, entity.getMinPlusInterval());
        stmt.bindLong(7, entity.getCommonNumber());
        stmt.bindLong(8, entity.getInterveneAccMinute());
        stmt.bindLong(9, entity.getWeekKeyFre());
        stmt.bindLong(10, entity.getWeekAccMinute());
        stmt.bindLong(11, entity.getBackWeekAccMinute0());
        stmt.bindLong(12, entity.getBackWeekAccMinute1());
        stmt.bindLong(13, entity.getBackWeekAccMinute2());
        stmt.bindLong(14, entity.getBackWeekAccMinute3());
        stmt.bindLong(15, entity.getPlusInterval());
        stmt.bindLong(16, entity.getMinusInterval());
        stmt.bindLong(17, entity.getPlusInc());
        stmt.bindLong(18, entity.getMinusInc());
        stmt.bindLong(19, entity.getIncPer());
        stmt.bindLong(20, entity.getRunNumber());
        stmt.bindLong(21, entity.getRunSpeed());
        stmt.bindLong(22, entity.getSpeedInc());
        stmt.bindLong(23, entity.getSpeedSegment());
        stmt.bindLong(24, entity.getIntervalSegment());
        stmt.bindLong(25, entity.getBackSpeedSegment());
        stmt.bindLong(26, entity.getBackIntervalSegment());
        stmt.bindLong(27, entity.getSpeedKeyFre());
        stmt.bindLong(28, entity.getInterveneKeyFre());
        stmt.bindLong(29, entity.getIntervalAccMinute());
        stmt.bindLong(30, entity.getMinusInterval2());
        stmt.bindLong(31, entity.getPlusInterval2());
        stmt.bindLong(32, entity.getMinusInc2());
        stmt.bindLong(33, entity.getPlusInc2());
        stmt.bindLong(34, entity.getIncPer2());
        stmt.bindLong(35, entity.getRunNumber2());
        stmt.bindLong(36, entity.getRunSpeed2());
        stmt.bindLong(37, entity.getSpeedSegment2());
        stmt.bindLong(38, entity.getSpeedInc2());
        stmt.bindLong(39, entity.getIntervalSegment2());
        stmt.bindLong(40, entity.getBackSpeedSegment2());
        stmt.bindLong(41, entity.getBackIntervalSegment2());
        stmt.bindLong(42, entity.getSpeedKeyFre2());
        stmt.bindLong(43, entity.getInterveneKeyFre2());
        stmt.bindLong(44, entity.getIntervalAccMinute2());
        stmt.bindLong(45, entity.getCurrentUserNewUser());
 
        String monitorDataCMD = entity.getMonitorDataCMD();
        if (monitorDataCMD != null) {
            stmt.bindString(46, monitorDataCMD);
        }
        stmt.bindLong(47, entity.getReceiveLocalTime());
 
        String receiveLocalTimeStr = entity.getReceiveLocalTimeStr();
        if (receiveLocalTimeStr != null) {
            stmt.bindString(48, receiveLocalTimeStr);
        }
        stmt.bindLong(49, entity.getIsReportedServer() ? 1L: 0L);
        stmt.bindLong(50, entity.getTrainingState());
        stmt.bindLong(51, entity.getTrainingState2());
        stmt.bindLong(52, entity.getAdjustSpeed());
        stmt.bindLong(53, entity.getMaxRunSpeed());
        stmt.bindLong(54, entity.getMinRunSpeed());
        stmt.bindLong(55, entity.getAdjustSpeed2());
        stmt.bindLong(56, entity.getMaxRunSpeed2());
        stmt.bindLong(57, entity.getMinRunSpeed2());
        stmt.bindLong(58, entity.getTxByte12());
        stmt.bindLong(59, entity.getTxByte13());
        stmt.bindLong(60, entity.getTxByte14());
        stmt.bindLong(61, entity.getTxByte15());
        stmt.bindLong(62, entity.getTxByte16());
        stmt.bindLong(63, entity.getTxByte17());
        stmt.bindLong(64, entity.getTxByte18());
 
        String reserve1 = entity.getReserve1();
        if (reserve1 != null) {
            stmt.bindString(65, reserve1);
        }
 
        String reservedStr0 = entity.getReservedStr0();
        if (reservedStr0 != null) {
            stmt.bindString(66, reservedStr0);
        }
 
        String reservedStr1 = entity.getReservedStr1();
        if (reservedStr1 != null) {
            stmt.bindString(67, reservedStr1);
        }
 
        String reservedStr2 = entity.getReservedStr2();
        if (reservedStr2 != null) {
            stmt.bindString(68, reservedStr2);
        }
 
        String reservedStr3 = entity.getReservedStr3();
        if (reservedStr3 != null) {
            stmt.bindString(69, reservedStr3);
        }
        stmt.bindLong(70, entity.getReservedLong0());
        stmt.bindLong(71, entity.getReservedLong1());
        stmt.bindLong(72, entity.getReservedInt0());
        stmt.bindLong(73, entity.getReservedInt1());
        stmt.bindLong(74, entity.getReservedInt2());
        stmt.bindLong(75, entity.getReservedInt3());
        stmt.bindLong(76, entity.getReservedInt4());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public RunParamsBleDataDBBean readEntity(Cursor cursor, int offset) {
        RunParamsBleDataDBBean entity = new RunParamsBleDataDBBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // localid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // glassesMAC
            cursor.getLong(offset + 3), // currentUserCode
            cursor.getInt(offset + 4), // minMinusInterval
            cursor.getInt(offset + 5), // minPlusInterval
            cursor.getInt(offset + 6), // commonNumber
            cursor.getInt(offset + 7), // interveneAccMinute
            cursor.getInt(offset + 8), // weekKeyFre
            cursor.getInt(offset + 9), // weekAccMinute
            cursor.getInt(offset + 10), // backWeekAccMinute0
            cursor.getInt(offset + 11), // backWeekAccMinute1
            cursor.getInt(offset + 12), // backWeekAccMinute2
            cursor.getInt(offset + 13), // backWeekAccMinute3
            cursor.getInt(offset + 14), // plusInterval
            cursor.getInt(offset + 15), // minusInterval
            cursor.getInt(offset + 16), // plusInc
            cursor.getInt(offset + 17), // minusInc
            cursor.getInt(offset + 18), // incPer
            cursor.getInt(offset + 19), // runNumber
            cursor.getInt(offset + 20), // runSpeed
            cursor.getInt(offset + 21), // speedInc
            cursor.getInt(offset + 22), // speedSegment
            cursor.getInt(offset + 23), // intervalSegment
            cursor.getInt(offset + 24), // backSpeedSegment
            cursor.getInt(offset + 25), // backIntervalSegment
            cursor.getInt(offset + 26), // speedKeyFre
            cursor.getInt(offset + 27), // interveneKeyFre
            cursor.getInt(offset + 28), // intervalAccMinute
            cursor.getInt(offset + 29), // minusInterval2
            cursor.getInt(offset + 30), // plusInterval2
            cursor.getInt(offset + 31), // minusInc2
            cursor.getInt(offset + 32), // plusInc2
            cursor.getInt(offset + 33), // incPer2
            cursor.getInt(offset + 34), // runNumber2
            cursor.getInt(offset + 35), // runSpeed2
            cursor.getInt(offset + 36), // speedSegment2
            cursor.getInt(offset + 37), // speedInc2
            cursor.getInt(offset + 38), // intervalSegment2
            cursor.getInt(offset + 39), // backSpeedSegment2
            cursor.getInt(offset + 40), // backIntervalSegment2
            cursor.getInt(offset + 41), // speedKeyFre2
            cursor.getInt(offset + 42), // interveneKeyFre2
            cursor.getInt(offset + 43), // intervalAccMinute2
            cursor.getInt(offset + 44), // currentUserNewUser
            cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45), // monitorDataCMD
            cursor.getLong(offset + 46), // receiveLocalTime
            cursor.isNull(offset + 47) ? null : cursor.getString(offset + 47), // receiveLocalTimeStr
            cursor.getShort(offset + 48) != 0, // isReportedServer
            cursor.getInt(offset + 49), // trainingState
            cursor.getInt(offset + 50), // trainingState2
            cursor.getInt(offset + 51), // AdjustSpeed
            cursor.getInt(offset + 52), // MaxRunSpeed
            cursor.getInt(offset + 53), // MinRunSpeed
            cursor.getInt(offset + 54), // AdjustSpeed2
            cursor.getInt(offset + 55), // MaxRunSpeed2
            cursor.getInt(offset + 56), // MinRunSpeed2
            cursor.getInt(offset + 57), // txByte12
            cursor.getInt(offset + 58), // txByte13
            cursor.getInt(offset + 59), // txByte14
            cursor.getInt(offset + 60), // txByte15
            cursor.getInt(offset + 61), // txByte16
            cursor.getInt(offset + 62), // txByte17
            cursor.getInt(offset + 63), // txByte18
            cursor.isNull(offset + 64) ? null : cursor.getString(offset + 64), // reserve1
            cursor.isNull(offset + 65) ? null : cursor.getString(offset + 65), // reservedStr0
            cursor.isNull(offset + 66) ? null : cursor.getString(offset + 66), // reservedStr1
            cursor.isNull(offset + 67) ? null : cursor.getString(offset + 67), // reservedStr2
            cursor.isNull(offset + 68) ? null : cursor.getString(offset + 68), // reservedStr3
            cursor.getLong(offset + 69), // reservedLong0
            cursor.getLong(offset + 70), // reservedLong1
            cursor.getInt(offset + 71), // reservedInt0
            cursor.getInt(offset + 72), // reservedInt1
            cursor.getInt(offset + 73), // reservedInt2
            cursor.getInt(offset + 74), // reservedInt3
            cursor.getInt(offset + 75) // reservedInt4
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, RunParamsBleDataDBBean entity, int offset) {
        entity.setLocalid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGlassesMAC(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCurrentUserCode(cursor.getLong(offset + 3));
        entity.setMinMinusInterval(cursor.getInt(offset + 4));
        entity.setMinPlusInterval(cursor.getInt(offset + 5));
        entity.setCommonNumber(cursor.getInt(offset + 6));
        entity.setInterveneAccMinute(cursor.getInt(offset + 7));
        entity.setWeekKeyFre(cursor.getInt(offset + 8));
        entity.setWeekAccMinute(cursor.getInt(offset + 9));
        entity.setBackWeekAccMinute0(cursor.getInt(offset + 10));
        entity.setBackWeekAccMinute1(cursor.getInt(offset + 11));
        entity.setBackWeekAccMinute2(cursor.getInt(offset + 12));
        entity.setBackWeekAccMinute3(cursor.getInt(offset + 13));
        entity.setPlusInterval(cursor.getInt(offset + 14));
        entity.setMinusInterval(cursor.getInt(offset + 15));
        entity.setPlusInc(cursor.getInt(offset + 16));
        entity.setMinusInc(cursor.getInt(offset + 17));
        entity.setIncPer(cursor.getInt(offset + 18));
        entity.setRunNumber(cursor.getInt(offset + 19));
        entity.setRunSpeed(cursor.getInt(offset + 20));
        entity.setSpeedInc(cursor.getInt(offset + 21));
        entity.setSpeedSegment(cursor.getInt(offset + 22));
        entity.setIntervalSegment(cursor.getInt(offset + 23));
        entity.setBackSpeedSegment(cursor.getInt(offset + 24));
        entity.setBackIntervalSegment(cursor.getInt(offset + 25));
        entity.setSpeedKeyFre(cursor.getInt(offset + 26));
        entity.setInterveneKeyFre(cursor.getInt(offset + 27));
        entity.setIntervalAccMinute(cursor.getInt(offset + 28));
        entity.setMinusInterval2(cursor.getInt(offset + 29));
        entity.setPlusInterval2(cursor.getInt(offset + 30));
        entity.setMinusInc2(cursor.getInt(offset + 31));
        entity.setPlusInc2(cursor.getInt(offset + 32));
        entity.setIncPer2(cursor.getInt(offset + 33));
        entity.setRunNumber2(cursor.getInt(offset + 34));
        entity.setRunSpeed2(cursor.getInt(offset + 35));
        entity.setSpeedSegment2(cursor.getInt(offset + 36));
        entity.setSpeedInc2(cursor.getInt(offset + 37));
        entity.setIntervalSegment2(cursor.getInt(offset + 38));
        entity.setBackSpeedSegment2(cursor.getInt(offset + 39));
        entity.setBackIntervalSegment2(cursor.getInt(offset + 40));
        entity.setSpeedKeyFre2(cursor.getInt(offset + 41));
        entity.setInterveneKeyFre2(cursor.getInt(offset + 42));
        entity.setIntervalAccMinute2(cursor.getInt(offset + 43));
        entity.setCurrentUserNewUser(cursor.getInt(offset + 44));
        entity.setMonitorDataCMD(cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45));
        entity.setReceiveLocalTime(cursor.getLong(offset + 46));
        entity.setReceiveLocalTimeStr(cursor.isNull(offset + 47) ? null : cursor.getString(offset + 47));
        entity.setIsReportedServer(cursor.getShort(offset + 48) != 0);
        entity.setTrainingState(cursor.getInt(offset + 49));
        entity.setTrainingState2(cursor.getInt(offset + 50));
        entity.setAdjustSpeed(cursor.getInt(offset + 51));
        entity.setMaxRunSpeed(cursor.getInt(offset + 52));
        entity.setMinRunSpeed(cursor.getInt(offset + 53));
        entity.setAdjustSpeed2(cursor.getInt(offset + 54));
        entity.setMaxRunSpeed2(cursor.getInt(offset + 55));
        entity.setMinRunSpeed2(cursor.getInt(offset + 56));
        entity.setTxByte12(cursor.getInt(offset + 57));
        entity.setTxByte13(cursor.getInt(offset + 58));
        entity.setTxByte14(cursor.getInt(offset + 59));
        entity.setTxByte15(cursor.getInt(offset + 60));
        entity.setTxByte16(cursor.getInt(offset + 61));
        entity.setTxByte17(cursor.getInt(offset + 62));
        entity.setTxByte18(cursor.getInt(offset + 63));
        entity.setReserve1(cursor.isNull(offset + 64) ? null : cursor.getString(offset + 64));
        entity.setReservedStr0(cursor.isNull(offset + 65) ? null : cursor.getString(offset + 65));
        entity.setReservedStr1(cursor.isNull(offset + 66) ? null : cursor.getString(offset + 66));
        entity.setReservedStr2(cursor.isNull(offset + 67) ? null : cursor.getString(offset + 67));
        entity.setReservedStr3(cursor.isNull(offset + 68) ? null : cursor.getString(offset + 68));
        entity.setReservedLong0(cursor.getLong(offset + 69));
        entity.setReservedLong1(cursor.getLong(offset + 70));
        entity.setReservedInt0(cursor.getInt(offset + 71));
        entity.setReservedInt1(cursor.getInt(offset + 72));
        entity.setReservedInt2(cursor.getInt(offset + 73));
        entity.setReservedInt3(cursor.getInt(offset + 74));
        entity.setReservedInt4(cursor.getInt(offset + 75));
     }
    
    @Override
    protected final String updateKeyAfterInsert(RunParamsBleDataDBBean entity, long rowId) {
        return entity.getLocalid();
    }
    
    @Override
    public String getKey(RunParamsBleDataDBBean entity) {
        if(entity != null) {
            return entity.getLocalid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(RunParamsBleDataDBBean entity) {
        return entity.getLocalid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
