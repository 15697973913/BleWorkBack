package com.zj.zhijue.util;

import android.content.Context;

import androidx.core.internal.view.SupportMenu;
import androidx.core.view.MotionEventCompat;

import android.util.Log;

import com.blankj.utilcode.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyUtil {
    public static String GlobalAuthtoken = "";
    public static String Http_CheckMobilePhone_Error = "";
    public static int Http_CheckMobilePhone_State = -1000;
    public static String Http_ContactUs_Error = "";
    public static int Http_ContactUs_State = -1000;
    public static String Http_ContactUs_list = "";
    public static String Http_ListForParent_Error = "";
    public static int Http_ListForParent_State = -1000;
    public static String Http_ListForParent_list = "";
    public static String Http_LoginParent_Error = "";
    public static int Http_LoginParent_State = -1000;
    public static String Http_LoginUser_Error = "";
    public static int Http_LoginUser_State = -1000;
    public static String Http_ParentAddUser_Error = "";
    public static int Http_ParentAddUser_State = -1000;
    public static String Http_QueryDelayUserTime_Error = "";
    public static String Http_QueryDelayUserTime_Msg = "";
    public static int Http_QueryDelayUserTime_State = -1000;
    public static String Http_QueryLastWeekReport_Error = "";
    public static String Http_QueryLastWeekReport_Msg = "";
    public static int Http_QueryLastWeekReport_State = -1000;
    public static String Http_QueryNowDayReport_Error = "";
    public static String Http_QueryNowDayReport_Msg = "";
    public static int Http_QueryNowDayReport_State = -1000;
    public static String Http_QueryNowWeekReport_Error = "";
    public static String Http_QueryNowWeekReport_Msg = "";
    public static int Http_QueryNowWeekReport_State = -1000;
    public static String Http_QueryOneDayReport_Error = "";
    public static String Http_QueryOneDayReport_Msg = "";
    public static int Http_QueryOneDayReport_State = -1000;
    public static String Http_QueryPauseWarn_Error = "";
    public static String Http_QueryPauseWarn_Msg = "";
    public static int Http_QueryPauseWarn_State = -1000;
    public static int Http_QueryRangeAdjust_Code = 0;
    public static String Http_QueryRangeAdjust_Error = "";
    public static String Http_QueryRangeAdjust_Msg = "";
    public static int Http_QueryRangeAdjust_State = -1000;
    public static String Http_QuerySevenDayReport_Error = "";
    public static String Http_QuerySevenDayReport_Msg = "";
    public static int Http_QuerySevenDayReport_State = -1000;
    public static int Http_QuerySpeedAdjust_Code = 0;
    public static String Http_QuerySpeedAdjust_Error = "";
    public static String Http_QuerySpeedAdjust_Msg = "";
    public static int Http_QuerySpeedAdjust_State = -1000;
    public static String Http_QueryStoreInfo_Error = "";
    public static String Http_QueryStoreInfo_Msg = "";
    public static int Http_QueryStoreInfo_State = -1000;
    public static String Http_Questionnaire_Error = "";
    public static String Http_Questionnaire_Msg = "";
    public static int Http_Questionnaire_State = -1000;
    public static String Http_RegisterParent_Error = "";
    public static int Http_RegisterParent_State = -1000;
    public static String Http_Register_Error = "";
    public static int Http_Register_State = -1000;
    public static String Http_ResetPassWord_Error = "";
    public static String Http_ResetPassWord_Msg = "";
    public static int Http_ResetPassWord_State = -1000;
    public static String Http_ReviewData_Error = "";
    public static int Http_ReviewData_State = -1000;
    public static String Http_Suggestion_Error = "";
    public static String Http_Suggestion_Msg = "";
    public static String Http_Suggestion_Msgcount = "";
    public static String Http_Suggestion_MsgpageSize = "";
    public static int Http_Suggestion_State = -1000;
    public static String Http_Troubleshoot_Error = "";
    public static String Http_Troubleshoot_Msg = "";
    public static int Http_Troubleshoot_State = -1000;
    public static String Http_UploadAdvice_Error = "";
    public static int Http_UploadAdvice_State = -1000;
    public static String Http_UploadDiopterData_Error = "";
    public static int Http_UploadDiopterData_State = -1000;
    public static String Http_VisionRankList_Error = "";
    public static String Http_VisionRankList_Msg = "";
    public static int Http_VisionRankList_State = -1000;
    public static String Http_WearRankList_Error = "";
    public static String Http_WearRankList_Msg = "";
    public static int Http_WearRankList_State = -1000;
    public static boolean IsConnectNet = false;
    public static boolean IsDownloadRuleData = false;
    public static int IsNewUser = 0;
    public static int QuestionnaireState = 0;
    public static String VerCode = "";
    public static String ZhongZhuan_ServerTime = "";
    public static SimpleDateFormat df_year_m_d = new SimpleDateFormat("yyyyMMdd");
    public static boolean displaymylog = false;

    public static String md5(String string) {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("utf-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & MotionEventCompat.ACTION_MASK) < 16) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & MotionEventCompat.ACTION_MASK));
            }
            return hex.toString().substring(18, 26);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e2);
        }
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) ((charToByte(hexChars[pos]) << 4) | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static byte GetbyteFromCount(int count) {
        byte rv = (byte) 0;
        for (int n = 0; n < count; n++) {
            rv = (byte) (rv + 1);
        }
        return rv;
    }

    public static byte GetbyteFromCountCenter(int count) {
        byte rv = (byte) 0;
        for (int n = 0; n < Math.abs(count); n++) {
            rv = (byte) (rv + 1);
        }
        return rv;
    }

    public static int GetNumFrombyte(byte bvalue) {
        byte buffer = (byte) 0;
        for (int n = 0; n < 256; n++) {
            if (buffer == bvalue) {
                return n;
            }
            buffer = (byte) (buffer + 1);
        }
        return 0;
    }

    public static void DisPlayMyLog(String msg) {
        try {
            if (displaymylog) {
                Log.i("mylog", msg);
            }
        } catch (Exception e) {
        }
    }

    public static void DisPlayMyError(String msg) {
        try {
            if (displaymylog) {
                Log.i("myerror", msg);
            }
        } catch (Exception e) {
        }
    }

    public static void DisPlayReadImage(String msg) {
        try {
            if (displaymylog) {
                Log.i("readimage", msg);
            }
        } catch (Exception e) {
        }
    }

    public static void DisPlayMyTask(String msg) {
        try {
            if (displaymylog) {
                Log.i("mytask", msg);
            }
        } catch (Exception e) {
        }
    }

    public static void DisPlayMyDB_NET(String msg) {
        try {
            if (displaymylog) {
                Log.i("db_net", msg);
            }
        } catch (Exception e) {
        }
    }

    public static void DisPlayMyJiaoYan(String msg) {
        try {
            if (displaymylog) {
                Log.i("jiaoyan", msg);
            }
        } catch (Exception e) {
        }
    }

    public static String GetCurYMD() {
        String CurYMD = "";
        try {
            CurYMD = df_year_m_d.format(new Date());
        } catch (Exception e) {
        }
        return CurYMD;
    }

    public static boolean JudegTime(String localtime, String tasktime) {
        if (localtime.length() <= 0 || tasktime.length() <= 0 || localtime.equals(tasktime)) {
            return false;
        }
        return true;
    }

    public static int getVersionCode(Context context) {
        int rv = 1;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            return rv;
        }
    }

    public static int ConvertStringToInt(String sv, int default_iv) {
        int rv = default_iv;
        try {
            rv = Integer.parseInt(sv);
        } catch (Exception e) {
        }
        return rv;
    }

    public static int ConvertString_100_ToInt(String sv, int default_iv) {
        try {
            return (int) (Float.parseFloat(sv) * 100.0f);
        } catch (Exception e) {
            return default_iv;
        }
    }

    public static int CheckNumber(int v, int max, int min) {
        int rv = v;
        if (rv > max) {
            rv = max;
        }
        if (rv < min) {
            return min;
        }
        return rv;
    }

    public static int byteToUnsignedInt(byte b) {
        return b & MotionEventCompat.ACTION_MASK;
    }

    public static int ConvertIntToUnsigndInt(int v) {
        int rv = v & SupportMenu.USER_MASK;
        DisPlayMyError("输入:" + v + ",输出:" + rv);
        return rv;
    }

    /**
     * 根据身份证号码计算年龄
     *
     * @param psptNo
     * @return
     */
    public static String getAgeByPsptNo(String psptNo) {


        String age;


        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        int year = Integer.valueOf(psptNo.substring(6, 10));
        int month = Integer.valueOf(psptNo.substring(10, 12));
        int day = Integer.valueOf(psptNo.substring(12, 14));

        if ((month < monthNow) || (month == monthNow && day <= dayNow)) {
            age = String.valueOf(yearNow - year);
        } else {
            age = String.valueOf(yearNow - year - 1);
        }


        return age;
    }



}
