package com.zj.zhijue.util;

import org.apache.http.HttpStatus;

public class MyUtilDushuBushu {
    private static final int[] DuShuBiaoJiZhun;
    private static int JianGeShuLiang = 5;
    private static int NewCenter = 191;

    static {
        int[] iArr = new int[77];
        iArr[0] = HttpStatus.SC_EXPECTATION_FAILED;
        iArr[1] = 395;
        iArr[2] = 374;
        iArr[3] = 349;
        iArr[4] = 327;
        iArr[5] = HttpStatus.SC_NOT_MODIFIED;
        iArr[6] = 281;
        iArr[7] = 257;
        iArr[8] = 237;
        iArr[9] = 214;
        iArr[10] = 191;
        iArr[11] = 168;
        iArr[12] = 148;
        iArr[13] = 124;
        iArr[14] = 105;
        iArr[15] = 83;
        iArr[16] = 63;
        iArr[17] = 44;
        iArr[18] = 25;
        iArr[19] = 10;
        iArr[21] = -30;
        iArr[22] = -45;
        iArr[23] = -64;
        iArr[24] = -82;
        iArr[25] = -102;
        iArr[26] = -120;
        iArr[27] = -140;
        iArr[28] = -159;
        iArr[29] = -178;
        iArr[30] = -197;
        iArr[31] = -218;
        iArr[32] = -237;
        iArr[33] = -258;
        iArr[34] = -279;
        iArr[35] = -301;
        iArr[36] = -322;
        iArr[37] = -344;
        iArr[38] = -359;
        iArr[39] = -378;
        iArr[40] = -400;
        iArr[41] = -416;
        iArr[42] = -435;
        iArr[43] = -451;
        iArr[44] = -469;
        iArr[45] = -483;
        iArr[46] = -498;
        iArr[47] = -510;
        iArr[48] = -523;
        iArr[49] = -535;
        iArr[50] = -546;
        iArr[51] = -554;
        iArr[52] = -563;
        iArr[53] = -572;
        iArr[54] = -578;
        iArr[55] = -585;
        iArr[56] = -591;
        iArr[57] = -596;
        iArr[58] = -602;
        iArr[59] = -608;
        iArr[60] = -616;
        iArr[61] = -624;
        iArr[62] = -636;
        iArr[63] = -647;
        iArr[64] = -659;
        iArr[65] = -673;
        iArr[66] = -686;
        iArr[67] = -698;
        iArr[68] = -714;
        iArr[69] = -728;
        iArr[70] = -742;
        iArr[71] = -757;
        iArr[72] = -771;
        iArr[73] = -786;
        iArr[74] = -797;
        iArr[75] = -812;
        iArr[76] = -824;
        DuShuBiaoJiZhun = iArr;
    }

    public static int ConvertYunDuShuToGlassBuShu(int yundushu) {
        int i_index = 0;
        int j_index = 0;
        for (int i = 0; i < DuShuBiaoJiZhun.length - 1; i++) {
            if (yundushu <= DuShuBiaoJiZhun[i]) {
                i_index = i;
            }
        }
        for (int j = 0; j < JianGeShuLiang; j++) {
            if (yundushu <= ((int) (((float) DuShuBiaoJiZhun[i_index]) + (((float) j) * (((float) (DuShuBiaoJiZhun[i_index + 1] - DuShuBiaoJiZhun[i_index])) / ((float) JianGeShuLiang)))))) {
                j_index = j;
            }
        }
        int DBbushu = ((JianGeShuLiang * i_index) + j_index) + 1;
        if (yundushu >= DuShuBiaoJiZhun[0]) {
            DBbushu = 1;
        }
        if (yundushu <= DuShuBiaoJiZhun[DuShuBiaoJiZhun.length - 1]) {
            DBbushu = 380;
        }
        if (DBbushu < 1) {
            DBbushu = 1;
        }
        if (DBbushu > 380) {
            DBbushu = 380;
        }
        return NewCenter - DBbushu;
    }

    public static int ConvertGlassBuShuToYunDuShu(int glassbushu) {
        int dataid = NewCenter - glassbushu;
        if (dataid < 1) {
            dataid = 1;
        }
        if (dataid > 380) {
            dataid = 380;
        }
        int dataid5 = dataid / JianGeShuLiang;
        if (dataid5 < 0) {
            dataid5 = 0;
        }
        if (dataid5 > DuShuBiaoJiZhun.length - 1) {
            dataid5 = DuShuBiaoJiZhun.length - 1;
        }
        int dataid5_1 = dataid5 + 1;
        if (dataid5_1 < 0) {
            dataid5_1 = 0;
        }
        if (dataid5_1 > DuShuBiaoJiZhun.length - 1) {
            dataid5_1 = DuShuBiaoJiZhun.length - 1;
        }
        return DuShuBiaoJiZhun[dataid5] + ((int) (((float) (dataid - (JianGeShuLiang * dataid5))) * (((float) (DuShuBiaoJiZhun[dataid5_1] - DuShuBiaoJiZhun[dataid5])) / ((float) JianGeShuLiang))));
    }
}
