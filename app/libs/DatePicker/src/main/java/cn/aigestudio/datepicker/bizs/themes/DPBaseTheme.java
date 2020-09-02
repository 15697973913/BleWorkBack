package cn.aigestudio.datepicker.bizs.themes;

import android.graphics.Color;

/**
 * 主题的默认实现类
 * 
 * The default implement of theme
 *
 * @author AigeStudio 2015-06-17
 */
public class DPBaseTheme extends DPTheme {
    @Override
    public int colorBG() {
        return 0xEE333333;
    }//选择的日期颜色

    @Override
    public int colorBGCircle() {
        return 0x22374CFF;
    }//选择中日期的圆形颜色

    @Override
    public int colorTitleBG() {
        return 0xffffff;
    }

    @Override
    public int colorTitle() {
        return 0xEEFFFFFF;
    }

    @Override
    public int colorToday() {
        return 0XFF374CFF;
    }

    @Override
    public int colorG() {
        return 0xEE666666;
    }

    @Override
    public int colorF() {
        return 0xEEC08AA4;
    }

    @Override
    public int colorWeekend() {
        return 0xEEF78082;
    }

    @Override
    public int colorHoliday() {
        return 0x80FED6D6;
    }

    @Override
    public int colorLineCircle() {
        return 0xFF3538FF;
    }

    @Override
    public int colorToDayText() {
        return 0xFFFFFFFF;
    }
}
