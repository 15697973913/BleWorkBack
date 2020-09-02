package com.zj.zhijue.util;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.alibaba.fastjson.parser.JSONLexer;
import com.vise.baseble.utils.HexUtil;
import com.android.common.baselibrary.log.MLog;

import java.util.Calendar;

public class MyUtilBytes {
    public static byte[] WriteBytes1 = new byte[1];
    public static byte[] WriteBytes10 = new byte[10];
    public static byte[] WriteBytes11 = new byte[11];
    public static byte[] WriteBytes12 = new byte[12];
    public static byte[] WriteBytes13 = new byte[13];
    public static byte[] WriteBytes14 = new byte[14];
    public static byte[] WriteBytes15 = new byte[15];
    public static byte[] WriteBytes16 = new byte[16];
    public static byte[] WriteBytes17 = new byte[17];
    public static byte[] WriteBytes18 = new byte[18];
    public static byte[] WriteBytes19 = new byte[19];
    public static byte[] WriteBytes2 = new byte[2];
    public static byte[] WriteBytes20 = new byte[20];
    public static byte[] WriteBytes3 = new byte[3];
    public static byte[] WriteBytes4 = new byte[4];
    public static byte[] WriteBytes5 = new byte[5];
    public static byte[] WriteBytes6 = new byte[6];
    public static byte[] WriteBytes7 = new byte[7];
    public static byte[] WriteBytes8 = new byte[8];
    public static byte[] WriteBytes9 = new byte[9];

    public static void Button_ReadDuShu() {
        WriteBytes6[0] = (byte) -24;
        WriteBytes6[1] = (byte) 1;
        WriteBytes6[2] = (byte) 1;
        WriteBytes6[3] = (byte) -22;
        WriteBytes6[4] = (byte) 13;
        WriteBytes6[5] = (byte) 10;
    }

    public static void Button_Begin() {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 1;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = (byte) 3;
        WriteBytes9[7] = (byte) 3;
        WriteBytes9[8] = (byte) 36;
    }

    public static void Button_Stop() {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 1;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = (byte) 0;
        WriteBytes9[7] = (byte) 0;
        WriteBytes9[8] = (byte) 36;
    }

    public static void Button_Pause() {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 1;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = (byte) 4;
        WriteBytes9[7] = (byte) 4;
        WriteBytes9[8] = (byte) 36;
    }

    public static void Button_Continue() {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 1;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = (byte) 5;
        WriteBytes9[7] = (byte) 5;
        WriteBytes9[8] = (byte) 36;
    }

    public static void Button_GanYu() {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 1;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = (byte) 8;
        WriteBytes9[7] = (byte) 8;
        WriteBytes9[8] = (byte) 36;
    }

    public static void Button_CheckData() {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 18;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = (byte) 0;
        WriteBytes9[7] = (byte) 0;
        WriteBytes9[8] = (byte) 36;
    }

    public static void Byte_QueryGlassVersion() {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 12;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = (byte) 1;
        WriteBytes9[7] = (byte) 1;
        WriteBytes9[8] = (byte) 36;
    }

    public static void Byte_QueryGlassDataNum() {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 18;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = (byte) 0;
        WriteBytes9[7] = (byte) (WriteBytes9[6] & MotionEventCompat.ACTION_MASK);
        WriteBytes9[8] = (byte) 36;
    }

    public static void Byte_SendTimeToGlass() {
        Calendar curTime = Calendar.getInstance();
        int curYear = curTime.get(1) - 2000;
        int curMonth = curTime.get(2) + 1;
        int curDay = curTime.get(5);
        int curHour = curTime.get(11);
        int curMinute = curTime.get(12);
        int curSecond = curTime.get(13);
        MyUtil.DisPlayMyError("同步curYear:" + curYear + ",curMonth:" + curMonth + ",curDay:" + curDay + ",curHour:" + curHour);
        WriteBytes14[0] = (byte) 36;
        WriteBytes14[1] = (byte) 68;
        WriteBytes14[2] = (byte) 84;
        WriteBytes14[3] = (byte) 58;
        WriteBytes14[4] = (byte) 5;
        WriteBytes14[5] = (byte) 6;
        WriteBytes14[6] = MyUtil.GetbyteFromCountCenter(curYear);
        WriteBytes14[7] = MyUtil.GetbyteFromCountCenter(curMonth);
        WriteBytes14[8] = MyUtil.GetbyteFromCountCenter(curDay);
        WriteBytes14[9] = MyUtil.GetbyteFromCountCenter(curHour);
        WriteBytes14[10] = MyUtil.GetbyteFromCountCenter(curMinute);
        WriteBytes14[11] = MyUtil.GetbyteFromCountCenter(curSecond);
        WriteBytes14[12] = (byte) (((((WriteBytes14[6] + WriteBytes14[7]) + WriteBytes14[8]) + WriteBytes14[9]) + WriteBytes14[10]) + WriteBytes14[11]);
        WriteBytes14[12] = (byte) (WriteBytes14[12] & MotionEventCompat.ACTION_MASK);
        WriteBytes14[13] = (byte) 36;
    }

    public static void Byte_SendGuiLing(int guilinghour) {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 9;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = MyUtil.GetbyteFromCountCenter(guilinghour);
        WriteBytes9[7] = (byte) (WriteBytes9[6] & MotionEventCompat.ACTION_MASK);
        WriteBytes9[8] = (byte) 36;
    }

    public static void Byte_SendInitDuShu(int left_dushu, int right_dushu) {
        WriteBytes12[0] = (byte) 36;
        WriteBytes12[1] = (byte) 68;
        WriteBytes12[2] = (byte) 84;
        WriteBytes12[3] = (byte) 58;
        WriteBytes12[4] = (byte) 4;
        WriteBytes12[5] = (byte) 4;
        if (left_dushu >= 0) {
            WriteBytes12[6] = (byte) 1;
        } else {
            WriteBytes12[6] = (byte) 0;
        }
        WriteBytes12[7] = MyUtil.GetbyteFromCountCenter(left_dushu);
        if (right_dushu >= 0) {
            WriteBytes12[8] = (byte) 1;
        } else {
            WriteBytes12[8] = (byte) 0;
        }
        WriteBytes12[9] = MyUtil.GetbyteFromCountCenter(right_dushu);
        WriteBytes12[10] = (byte) (((WriteBytes12[6] + WriteBytes12[7]) + WriteBytes12[8]) + WriteBytes12[9]);
        WriteBytes12[10] = (byte) (WriteBytes12[10] & MotionEventCompat.ACTION_MASK);
        WriteBytes12[11] = (byte) 36;
    }

    public static void Byte_SendStartPos(int Numindex) {
        WriteBytes10[0] = (byte) 36;
        WriteBytes10[1] = (byte) 68;
        WriteBytes10[2] = (byte) 84;
        WriteBytes10[3] = (byte) 58;
        WriteBytes10[4] = (byte) 16;
        WriteBytes10[5] = (byte) 2;
        WriteBytes10[6] = (byte) 0;
        WriteBytes10[7] = MyUtil.GetbyteFromCountCenter(Numindex);
        WriteBytes10[8] = (byte) (WriteBytes10[6] + WriteBytes10[7]);
        WriteBytes10[8] = (byte) (WriteBytes10[8] & MotionEventCompat.ACTION_MASK);
        WriteBytes10[9] = (byte) 36;
    }

    public static void SendRuleData_Run(int index, int speed, int num, int rangeLow, int rangeUp) {
        WriteBytes15[0] = (byte) 36;
        WriteBytes15[1] = (byte) 68;
        WriteBytes15[2] = (byte) 84;
        WriteBytes15[3] = (byte) 58;
        WriteBytes15[4] = (byte) 3;
        WriteBytes15[5] = (byte) 7;
        WriteBytes15[6] = MyUtil.GetbyteFromCountCenter(index);
        WriteBytes15[7] = MyUtil.GetbyteFromCountCenter(speed);
        WriteBytes15[8] = MyUtil.GetbyteFromCountCenter(num);
        if (rangeLow >= 0) {
            WriteBytes15[9] = (byte) 1;
        } else {
            WriteBytes15[9] = (byte) 0;
        }
        WriteBytes15[10] = MyUtil.GetbyteFromCountCenter(rangeLow);
        if (rangeUp >= 0) {
            WriteBytes15[11] = (byte) 1;
        } else {
            WriteBytes15[11] = (byte) 0;
        }
        WriteBytes15[12] = MyUtil.GetbyteFromCountCenter(rangeUp);
        WriteBytes15[13] = (byte) ((((((WriteBytes15[6] + WriteBytes15[7]) + WriteBytes15[8]) + WriteBytes15[9]) + WriteBytes15[10]) + WriteBytes15[11]) + WriteBytes15[12]);
        WriteBytes15[13] = (byte) (WriteBytes15[13] & MotionEventCompat.ACTION_MASK);
        WriteBytes15[14] = (byte) 36;
    }

    public static void SendRuleData_OK(int num) {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 7;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = MyUtil.GetbyteFromCountCenter(num);
        WriteBytes9[7] = (byte) (WriteBytes9[6] & MotionEventCompat.ACTION_MASK);
        WriteBytes9[8] = (byte) 36;
    }

    public static void Byte_SendUserID(int userid) {
        int num4 = userid / ViewCompat.MEASURED_STATE_TOO_SMALL;
        int num3 = (userid - (((num4 * 256) * 256) * 256)) / 65536;
        int num2 = ((userid - (((num4 * 256) * 256) * 256)) - ((num3 * 256) * 256)) / 256;
        int num1 = ((userid - (((num4 * 256) * 256) * 256)) - ((num3 * 256) * 256)) - (num2 * 256);
        WriteBytes12[0] = (byte) 36;
        WriteBytes12[1] = (byte) 68;
        WriteBytes12[2] = (byte) 84;
        WriteBytes12[3] = (byte) 58;
        WriteBytes12[4] = (byte) 20;
        WriteBytes12[5] = (byte) 4;
        WriteBytes12[6] = MyUtil.GetbyteFromCountCenter(num4);
        WriteBytes12[7] = MyUtil.GetbyteFromCountCenter(num3);
        WriteBytes12[8] = MyUtil.GetbyteFromCountCenter(num2);
        WriteBytes12[9] = MyUtil.GetbyteFromCountCenter(num1);
        WriteBytes12[10] = (byte) (((WriteBytes12[6] + WriteBytes12[7]) + WriteBytes12[8]) + WriteBytes12[9]);
        WriteBytes12[10] = (byte) (WriteBytes12[10] & MotionEventCompat.ACTION_MASK);
        WriteBytes12[11] = (byte) 36;
    }

    public static void SendSpeedAdjust(int state) {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 28;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = MyUtil.GetbyteFromCountCenter(state);
        WriteBytes9[7] = (byte) (WriteBytes9[6] & MotionEventCompat.ACTION_MASK);
        WriteBytes9[8] = (byte) 36;
    }

    public static void SendRangeAdjust(int state) {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 29;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = MyUtil.GetbyteFromCountCenter(state);
        WriteBytes9[7] = (byte) (WriteBytes9[6] & MotionEventCompat.ACTION_MASK);
        WriteBytes9[8] = (byte) 36;
    }

    public static void Byte_SendDistanceZhouQi(int tongji, int caiyang) {
        int tongji2 = tongji / 256;
        int tongji1 = tongji - (tongji2 * 256);
        int caiyang2 = caiyang / 256;
        int caiyang1 = caiyang - (caiyang2 * 256);
        WriteBytes12[0] = (byte) 36;
        WriteBytes12[1] = (byte) 68;
        WriteBytes12[2] = (byte) 84;
        WriteBytes12[3] = (byte) 58;
        WriteBytes12[4] = (byte) 24;
        WriteBytes12[5] = (byte) 4;
        WriteBytes12[6] = MyUtil.GetbyteFromCountCenter(tongji2);
        WriteBytes12[7] = MyUtil.GetbyteFromCountCenter(tongji1);
        WriteBytes12[8] = MyUtil.GetbyteFromCountCenter(caiyang2);
        WriteBytes12[9] = MyUtil.GetbyteFromCountCenter(caiyang1);
        WriteBytes12[10] = (byte) (((WriteBytes12[6] + WriteBytes12[7]) + WriteBytes12[8]) + WriteBytes12[9]);
        WriteBytes12[10] = (byte) (WriteBytes12[10] & MotionEventCompat.ACTION_MASK);
        WriteBytes12[11] = (byte) 36;
    }

    public static void Byte_SendDitanceHuaFen(int jin_zhong, int zhong_yuan) {
        int jin_zhong2 = jin_zhong / 256;
        int jin_zhong1 = jin_zhong - (jin_zhong2 * 256);
        int zhong_yuan2 = zhong_yuan / 256;
        int zhong_yuan1 = zhong_yuan - (zhong_yuan2 * 256);
        WriteBytes12[0] = (byte) 36;
        WriteBytes12[1] = (byte) 68;
        WriteBytes12[2] = (byte) 84;
        WriteBytes12[3] = (byte) 58;
        WriteBytes12[4] = (byte) 25;
        WriteBytes12[5] = (byte) 4;
        WriteBytes12[6] = MyUtil.GetbyteFromCountCenter(jin_zhong2);
        WriteBytes12[7] = MyUtil.GetbyteFromCountCenter(jin_zhong1);
        WriteBytes12[8] = MyUtil.GetbyteFromCountCenter(zhong_yuan2);
        WriteBytes12[9] = MyUtil.GetbyteFromCountCenter(zhong_yuan1);
        WriteBytes12[10] = (byte) (((WriteBytes12[6] + WriteBytes12[7]) + WriteBytes12[8]) + WriteBytes12[9]);
        WriteBytes12[10] = (byte) (WriteBytes12[10] & MotionEventCompat.ACTION_MASK);
        WriteBytes12[11] = (byte) 36;
    }

    public static void Byte_SendSpeed0_5(int speed0, int speed1, int speed2, int speed3, int speed4, int speed5) {
        int speed0_2 = speed0 / 256;
        int speed0_1 = speed0 - (speed0_2 * 256);
        int speed1_2 = speed1 / 256;
        int speed1_1 = speed1 - (speed1_2 * 256);
        int speed2_2 = speed2 / 256;
        int speed2_1 = speed2 - (speed2_2 * 256);
        int speed3_2 = speed3 / 256;
        int speed3_1 = speed3 - (speed3_2 * 256);
        int speed4_2 = speed4 / 256;
        int speed4_1 = speed4 - (speed4_2 * 256);
        int speed5_2 = speed5 / 256;
        int speed5_1 = speed5 - (speed5_2 * 256);
        WriteBytes20[0] = (byte) 36;
        WriteBytes20[1] = (byte) 68;
        WriteBytes20[2] = (byte) 84;
        WriteBytes20[3] = (byte) 58;
        WriteBytes20[4] = JSONLexer.EOI;
        WriteBytes20[5] = (byte) 12;
        WriteBytes20[6] = MyUtil.GetbyteFromCountCenter(speed0_2);
        WriteBytes20[7] = MyUtil.GetbyteFromCountCenter(speed0_1);
        WriteBytes20[8] = MyUtil.GetbyteFromCountCenter(speed1_2);
        WriteBytes20[9] = MyUtil.GetbyteFromCountCenter(speed1_1);
        WriteBytes20[10] = MyUtil.GetbyteFromCountCenter(speed2_2);
        WriteBytes20[11] = MyUtil.GetbyteFromCountCenter(speed2_1);
        WriteBytes20[12] = MyUtil.GetbyteFromCountCenter(speed3_2);
        WriteBytes20[13] = MyUtil.GetbyteFromCountCenter(speed3_1);
        WriteBytes20[14] = MyUtil.GetbyteFromCountCenter(speed4_2);
        WriteBytes20[15] = MyUtil.GetbyteFromCountCenter(speed4_1);
        WriteBytes20[16] = MyUtil.GetbyteFromCountCenter(speed5_2);
        WriteBytes20[17] = MyUtil.GetbyteFromCountCenter(speed5_1);
        WriteBytes20[18] = (byte) (((((((((((WriteBytes20[6] + WriteBytes20[7]) + WriteBytes20[8]) + WriteBytes20[9]) + WriteBytes20[10]) + WriteBytes20[11]) + WriteBytes20[12]) + WriteBytes20[13]) + WriteBytes20[14]) + WriteBytes20[15]) + WriteBytes20[16]) + WriteBytes20[17]);
        WriteBytes20[18] = (byte) (WriteBytes20[18] & MotionEventCompat.ACTION_MASK);
        WriteBytes20[19] = (byte) 36;
    }

    public static void Byte_SendSpeed6_10(int speed6, int speed7, int speed8, int speed9, int speed10) {
        int speed6_2 = speed6 / 256;
        int speed6_1 = speed6 - (speed6_2 * 256);
        int speed7_2 = speed7 / 256;
        int speed7_1 = speed7 - (speed7_2 * 256);
        int speed8_2 = speed8 / 256;
        int speed8_1 = speed8 - (speed8_2 * 256);
        int speed9_2 = speed9 / 256;
        int speed9_1 = speed9 - (speed9_2 * 256);
        int speed10_2 = speed10 / 256;
        int speed10_1 = speed10 - (speed10_2 * 256);
        WriteBytes18[0] = (byte) 36;
        WriteBytes18[1] = (byte) 68;
        WriteBytes18[2] = (byte) 84;
        WriteBytes18[3] = (byte) 58;
        WriteBytes18[4] = (byte) 27;
        WriteBytes18[5] = (byte) 10;
        WriteBytes18[6] = MyUtil.GetbyteFromCountCenter(speed6_2);
        WriteBytes18[7] = MyUtil.GetbyteFromCountCenter(speed6_1);
        WriteBytes18[8] = MyUtil.GetbyteFromCountCenter(speed7_2);
        WriteBytes18[9] = MyUtil.GetbyteFromCountCenter(speed7_1);
        WriteBytes18[10] = MyUtil.GetbyteFromCountCenter(speed8_2);
        WriteBytes18[11] = MyUtil.GetbyteFromCountCenter(speed8_1);
        WriteBytes18[12] = MyUtil.GetbyteFromCountCenter(speed9_2);
        WriteBytes18[13] = MyUtil.GetbyteFromCountCenter(speed9_1);
        WriteBytes18[14] = MyUtil.GetbyteFromCountCenter(speed10_2);
        WriteBytes18[15] = MyUtil.GetbyteFromCountCenter(speed10_1);
        WriteBytes18[16] = (byte) (((((((((WriteBytes18[6] + WriteBytes18[7]) + WriteBytes18[8]) + WriteBytes18[9]) + WriteBytes18[10]) + WriteBytes18[11]) + WriteBytes18[12]) + WriteBytes18[13]) + WriteBytes18[14]) + WriteBytes18[15]);
        WriteBytes18[16] = (byte) (WriteBytes18[16] & MotionEventCompat.ACTION_MASK);
        WriteBytes18[17] = (byte) 36;
    }

    public static void send_Statedata_OK(int id) {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 17;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = MyUtil.GetbyteFromCountCenter(id);
        WriteBytes9[7] = (byte) (WriteBytes9[6] & MotionEventCompat.ACTION_MASK);
        WriteBytes9[8] = (byte) 36;
    }

    public static void Byte_Sendxin_tiao() {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 23;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = (byte) 1;
        WriteBytes9[7] = (byte) (WriteBytes9[6] & MotionEventCompat.ACTION_MASK);
        WriteBytes9[8] = (byte) 36;
    }

    public static void Byte_SendWuXiaoJianCe(int allowTwo, int allowTen) {
        WriteBytes10[0] = (byte) 36;
        WriteBytes10[1] = (byte) 68;
        WriteBytes10[2] = (byte) 84;
        WriteBytes10[3] = (byte) 58;
        WriteBytes10[4] = (byte) 32;
        WriteBytes10[5] = (byte) 2;
        WriteBytes10[6] = MyUtil.GetbyteFromCountCenter(allowTwo);
        WriteBytes10[7] = MyUtil.GetbyteFromCountCenter(allowTen);
        WriteBytes10[8] = (byte) (WriteBytes10[6] + WriteBytes10[7]);
        WriteBytes10[8] = (byte) (WriteBytes10[8] & MotionEventCompat.ACTION_MASK);
        WriteBytes10[9] = (byte) 36;
    }

    public static void Byte_SendInitOk() {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 31;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = (byte) 1;
        WriteBytes9[7] = (byte) 1;
        WriteBytes9[8] = (byte) 36;
    }

    public static void Byte_BeginBin(int length) {
        int l2 = length / 256;
        int l1 = length - (l2 * 256);
        WriteBytes10[0] = (byte) 36;
        WriteBytes10[1] = (byte) 68;
        WriteBytes10[2] = (byte) 84;
        WriteBytes10[3] = (byte) 58;
        WriteBytes10[4] = (byte) 33;
        WriteBytes10[5] = (byte) 2;
        WriteBytes10[6] = MyUtil.GetbyteFromCountCenter(l2);
        WriteBytes10[7] = MyUtil.GetbyteFromCountCenter(l1);
        WriteBytes10[8] = (byte) (WriteBytes10[6] + WriteBytes10[7]);
        WriteBytes10[8] = (byte) (WriteBytes10[8] & MotionEventCompat.ACTION_MASK);
        WriteBytes10[9] = (byte) 36;
    }

    public static void Byte_JieDuanCheck(int checksum) {
        int checksum2 = checksum / 256;
        int checksum1 = checksum - (checksum2 * 256);
        WriteBytes10[0] = (byte) 36;
        WriteBytes10[1] = (byte) 68;
        WriteBytes10[2] = (byte) 84;
        WriteBytes10[3] = (byte) 59;
        WriteBytes10[4] = (byte) 35;
        WriteBytes10[5] = (byte) 2;
        WriteBytes10[6] = MyUtil.GetbyteFromCountCenter(checksum2);
        WriteBytes10[7] = MyUtil.GetbyteFromCountCenter(checksum1);
        WriteBytes10[8] = (byte) (WriteBytes10[6] + WriteBytes10[7]);
        WriteBytes10[8] = (byte) (WriteBytes10[8] & MotionEventCompat.ACTION_MASK);
        WriteBytes10[9] = (byte) 36;
    }

    public static void Byte_StopBin(int checksum) {
        int checksum2 = checksum / 256;
        int checksum1 = checksum - (checksum2 * 256);
        WriteBytes10[0] = (byte) 36;
        WriteBytes10[1] = (byte) 68;
        WriteBytes10[2] = (byte) 84;
        WriteBytes10[3] = (byte) 59;
        WriteBytes10[4] = (byte) 34;
        WriteBytes10[5] = (byte) 2;
        WriteBytes10[6] = MyUtil.GetbyteFromCountCenter(checksum2);
        WriteBytes10[7] = MyUtil.GetbyteFromCountCenter(checksum1);
        WriteBytes10[8] = (byte) (WriteBytes10[6] + WriteBytes10[7]);
        WriteBytes10[8] = (byte) (WriteBytes10[8] & MotionEventCompat.ACTION_MASK);
        WriteBytes10[9] = (byte) 36;
    }

    public static void Byte_SendChaoShenBoLD(int state) {
        if (state > 1) {
            state = 1;
        }
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 40;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = MyUtil.GetbyteFromCountCenter(state);
        WriteBytes9[7] = (byte) (WriteBytes9[6] & MotionEventCompat.ACTION_MASK);
        WriteBytes9[8] = (byte) 36;
    }

    public static void Byte_SendCheckUserID(int userid) {
        int num4 = userid / ViewCompat.MEASURED_STATE_TOO_SMALL;
        int num3 = (userid - (((num4 * 256) * 256) * 256)) / 65536;
        int num2 = ((userid - (((num4 * 256) * 256) * 256)) - ((num3 * 256) * 256)) / 256;
        int num1 = ((userid - (((num4 * 256) * 256) * 256)) - ((num3 * 256) * 256)) - (num2 * 256);
        WriteBytes12[0] = (byte) 36;
        WriteBytes12[1] = (byte) 68;
        WriteBytes12[2] = (byte) 84;
        WriteBytes12[3] = (byte) 58;
        WriteBytes12[4] = (byte) 41;
        WriteBytes12[5] = (byte) 4;
        WriteBytes12[6] = MyUtil.GetbyteFromCountCenter(num4);
        WriteBytes12[7] = MyUtil.GetbyteFromCountCenter(num3);
        WriteBytes12[8] = MyUtil.GetbyteFromCountCenter(num2);
        WriteBytes12[9] = MyUtil.GetbyteFromCountCenter(num1);
        WriteBytes12[10] = (byte) (((WriteBytes12[6] + WriteBytes12[7]) + WriteBytes12[8]) + WriteBytes12[9]);
        WriteBytes12[10] = (byte) (WriteBytes12[10] & MotionEventCompat.ACTION_MASK);
        WriteBytes12[11] = (byte) 36;
    }

    public static void Byte_SendImageBeginReturn(int id) {
        WriteBytes9[0] = (byte) 36;
        WriteBytes9[1] = (byte) 68;
        WriteBytes9[2] = (byte) 84;
        WriteBytes9[3] = (byte) 58;
        WriteBytes9[4] = (byte) 46;
        WriteBytes9[5] = (byte) 1;
        WriteBytes9[6] = MyUtil.GetbyteFromCountCenter(id);
        WriteBytes9[7] = (byte) (WriteBytes9[6] & MotionEventCompat.ACTION_MASK);
        WriteBytes9[8] = (byte) 36;
    }

    public static void Byte_SendImageResult(boolean result, int id) {
        int ir = 0;
        if (result) {
            ir = 1;
        }
        WriteBytes10[0] = (byte) 36;
        WriteBytes10[1] = (byte) 68;
        WriteBytes10[2] = (byte) 84;
        WriteBytes10[3] = (byte) 58;
        WriteBytes10[4] = (byte) 45;
        WriteBytes10[5] = (byte) 2;
        WriteBytes10[6] = MyUtil.GetbyteFromCountCenter(ir);
        WriteBytes10[7] = MyUtil.GetbyteFromCountCenter(id);
        WriteBytes10[8] = (byte) (WriteBytes10[6] + WriteBytes10[7]);
        WriteBytes10[8] = (byte) (WriteBytes10[8] & MotionEventCompat.ACTION_MASK);
        WriteBytes10[9] = (byte) 36;
    }


    public static void printByteArray(byte[] byteArray ) {
        if (null != byteArray) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < byteArray.length; i++ ) {
                stringBuilder.append(byteArray[i] + " ");
            }
            MLog.d(" byteArray =  " + stringBuilder.toString());
        }
        MLog.d("HexUtil.encodeHexStr(byteArray) = " + HexUtil.encodeHexStr(byteArray));
    }

    public static String byteArray2String(byte[] byteArray) {
        if (null != byteArray) {
            if (null != byteArray) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < byteArray.length; i++ ) {
                    stringBuilder.append(byteArray[i] + " ");
                }
                return stringBuilder.toString();
            }
        }
        return null;
    }
}
