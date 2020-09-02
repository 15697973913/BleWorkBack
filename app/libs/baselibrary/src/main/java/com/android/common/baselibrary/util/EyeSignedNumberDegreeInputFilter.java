package com.android.common.baselibrary.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import com.android.common.baselibrary.log.MLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 视力 100 ~ -700 的输入限制
 */

public class EyeSignedNumberDegreeInputFilter implements InputFilter {
    Pattern mPattern;

    private static final String MINUS = "-";

    public EyeSignedNumberDegreeInputFilter() {
        mPattern = Pattern.compile("^(-?)(0|[1-9][0-9]*)$");
    }
    /**
     * @param source 新输入的字符串
     * @param start  新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为0
     * @param dend   原内容终点坐标，一般为dest长度-1
     * @return 输入内容
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();
        //MLog.d("sourceText = " + sourceText + " destText = " + destText + " dstart = " + dstart + " dend = " + dend);
        //验证删除等按键
        if (TextUtils.isEmpty(sourceText)) {
            return "";
        }
        Matcher matcher = mPattern.matcher(source);
        boolean match = matcher.matches();
        //MLog.d("match = " + match);
        if (!match) {
            return "";
        }

        if (destText.trim().length() >=2) {
            boolean biggerMAX = Math.abs(Integer.parseInt(destText + source)) > 1200;
            if (biggerMAX) {
                return "";
            }
        }

        if (destText.trim().length()>= 1) {
            if (Math.abs(Integer.parseInt(destText)) == 0) {
                return "";
            }
        }

        return dest.subSequence(dstart, dend) + sourceText;
    }
}