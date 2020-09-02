package com.zj.zhijue.bean.bledata;

import android.util.SparseArray;

import com.android.common.baselibrary.blebean.BaseCmdBean;
import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam1BleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam2BleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam3BleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam4BleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesRunParam5BleDataBean;
import com.zj.zhijue.bean.bledata.send.SendGlassesRunParam1BleCmdBeaan;
import com.zj.zhijue.bean.bledata.send.SendGlassesRunParam2BleCmdBeaan;
import com.zj.zhijue.bean.bledata.send.SendGlassesRunParam3BleCmdBeaan;
import com.zj.zhijue.bean.bledata.send.SendGlassesRunParam4BleCmdBeaan;
import com.zj.zhijue.bean.bledata.send.SendGlassesRunParam5BleCmdBeaan;
import com.zj.zhijue.bean.request.HttpRequestGlassesRunParamBean;
import com.zj.zhijue.bean.response.HttpResponseGlassesRunParamBean;

/**
 * 关于眼镜运行参数，如何将四个 Bean 转换成一个 Bean 用于请求云端，以及如何将云端解析到的一个对象转换成发送给蓝牙眼镜的四个Bean 对象
 */
public class BleDataBeanConvertUtil {

    public static HttpRequestGlassesRunParamBean bleDataBean2HttpRequestBean(SparseArray<BaseParseCmdBean> fourBleDataParseBean) {
        HttpRequestGlassesRunParamBean httpBean = new HttpRequestGlassesRunParamBean();
        ReceiveGlassesRunParam1BleDataBean param1BleDataBean = (ReceiveGlassesRunParam1BleDataBean)fourBleDataParseBean.get(0);
        ReceiveGlassesRunParam2BleDataBean param2BleDataBean = (ReceiveGlassesRunParam2BleDataBean)fourBleDataParseBean.get(1);
        ReceiveGlassesRunParam3BleDataBean param3BleDataBean = (ReceiveGlassesRunParam3BleDataBean)fourBleDataParseBean.get(2);
        ReceiveGlassesRunParam4BleDataBean param4BleDataBean = (ReceiveGlassesRunParam4BleDataBean)fourBleDataParseBean.get(3);
        ReceiveGlassesRunParam5BleDataBean param5BleDataBean = (ReceiveGlassesRunParam5BleDataBean)fourBleDataParseBean.get(4);

        httpBean.setCurrentUserCode(param1BleDataBean.getCurrentUserCode());
        httpBean.setMinusInterval(param1BleDataBean.getMinMinusInterval());
        httpBean.setMinPlusInterval(param1BleDataBean.getMinPlusInterval());
        httpBean.setMinMinusInterval(param1BleDataBean.getMinMinusInterval());
        httpBean.setCommonNumber(param1BleDataBean.getCommonNumber());
        httpBean.setInterveneAccMinute(param1BleDataBean.getInterveneAccMinute());
        httpBean.setWeekKeyFre(param1BleDataBean.getWeekKeyFre());
        httpBean.setWeekAccMinute(param1BleDataBean.getWeekAccMinute());
        httpBean.setMonitorDataCMD(param1BleDataBean.getMonitorData_CMD());

        httpBean.setBackWeekAccMinute0(param2BleDataBean.getBackWeekAccMinute0());
        httpBean.setBackWeekAccMinute1(param2BleDataBean.getBackWeekAccMinute1());
        httpBean.setBackWeekAccMinute2(param2BleDataBean.getBackWeekAccMinute2());
        httpBean.setBackWeekAccMinute3(param2BleDataBean.getBackWeekAccMinute3());
        httpBean.setPlusInterval(param2BleDataBean.getPlusInterval());
        httpBean.setMinusInterval(param2BleDataBean.getMinusInterval());
        httpBean.setPlusInc(param2BleDataBean.getPlusInc());
        httpBean.setMinusInc(param2BleDataBean.getMinusInc());
        httpBean.setIncPer(param2BleDataBean.getIncPer());

        httpBean.setRunNumber(param3BleDataBean.getRunNumber());
        httpBean.setRunSpeed(param3BleDataBean.getRunSpeed());
        httpBean.setSpeedInc(param3BleDataBean.getSpeedInc());
        httpBean.setSpeedSegment(param3BleDataBean.getSpeedSegment());
        httpBean.setIntervalSegment(param3BleDataBean.getIntervalSegment());
        httpBean.setBackSpeedSegment(param3BleDataBean.getBackSpeedSegment());
        httpBean.setBackIntervalSegment(param3BleDataBean.getBackIntervalSegment());
        httpBean.setSpeedKeyFre(param3BleDataBean.getSpeedKeyFre());
        httpBean.setInterveneKeyFre(param3BleDataBean.getInterveneKeyFre());
        httpBean.setIntervalAccMinute(param3BleDataBean.getIntervalAccMinute());
        httpBean.setMinusInterval2(param3BleDataBean.getMinusInterval2());
        httpBean.setPlusInterval2(param3BleDataBean.getPlusInterval2());

        httpBean.setMinusInc2(param4BleDataBean.getMinusInc2());
        httpBean.setPlusInc2(param4BleDataBean.getPlusInc2());
        httpBean.setIncPer2(param4BleDataBean.getIncPer2());
        httpBean.setRunNumber2(param4BleDataBean.getRunNumber2());
        httpBean.setRunSpeed2(param4BleDataBean.getRunSpeed2());
        httpBean.setSpeedSegment2(param4BleDataBean.getSpeedSegment2());
        httpBean.setSpeedInc2(param4BleDataBean.getSpeedInc2());
        httpBean.setIntervalSegment2(param4BleDataBean.getIntervalSegment2());
        httpBean.setBackSpeedSegment2(param4BleDataBean.getBackSpeedSegment2());
        httpBean.setBackIntervalSegment2(param4BleDataBean.getBackIntervalSegment2());
        httpBean.setSpeedKeyFre2(param4BleDataBean.getSpeedKeyFre2());
        httpBean.setInterveneKeyFre2(param4BleDataBean.getInterveneKeyFre2());
        httpBean.setIntervalAccMinute2(param4BleDataBean.getIntervalAccMinute2());
        httpBean.setCurrentUserNewUser(param4BleDataBean.getCurrentUserNewUser());

        httpBean.setTrainingState(param5BleDataBean.getTrainingState());
        httpBean.setTrainingState2(param5BleDataBean.getTrainingState2());
        httpBean.setAdjustSpeed(param5BleDataBean.getAdjustSpeed());
        httpBean.setMaxRunSpeed(param5BleDataBean.getMaxRunSpeed());
        httpBean.setMinRunSpeed(param5BleDataBean.getMinRunSpeed());
        httpBean.setAdjustSpeed2(param5BleDataBean.getAdjustSpeed2());
        httpBean.setMaxRunSpeed2(param5BleDataBean.getMaxRunSpeed2());
        httpBean.setMinRunSpeed2(param5BleDataBean.getMinRunSpeed2());
        httpBean.setTxByte12(param5BleDataBean.getTxByte12());
        httpBean.setTxByte13(param5BleDataBean.getTxByte13());
        httpBean.setTxByte14(param5BleDataBean.getTxByte14());
        httpBean.setTxByte15(param5BleDataBean.getTxByte15());
        httpBean.setTxByte16(param5BleDataBean.getTxByte16());
        httpBean.setTxByte17(param5BleDataBean.getTxByte17());
        httpBean.setTxByte18(param5BleDataBean.getTxByte18());
        return httpBean;
    }

    public static SparseArray<BaseCmdBean> httpResponseBleDataBean2BleCmdBean(HttpResponseGlassesRunParamBean httpResponseGlassesRunParamBean) {
        SparseArray<BaseCmdBean> bleDataBeanArray = new SparseArray<>();
        SendGlassesRunParam1BleCmdBeaan sendBleBean1 = new SendGlassesRunParam1BleCmdBeaan();
        SendGlassesRunParam2BleCmdBeaan sendBleBean2 = new SendGlassesRunParam2BleCmdBeaan();
        SendGlassesRunParam3BleCmdBeaan sendBleBean3 = new SendGlassesRunParam3BleCmdBeaan();
        SendGlassesRunParam4BleCmdBeaan sendBleBean4 = new SendGlassesRunParam4BleCmdBeaan();
        SendGlassesRunParam5BleCmdBeaan sendBleBean5 = new SendGlassesRunParam5BleCmdBeaan();

        sendBleBean1.setMinMinusInterval(httpResponseGlassesRunParamBean.getMinMinusInterval());
        sendBleBean1.setMinPlusInterval(httpResponseGlassesRunParamBean.getMinPlusInterval());
        sendBleBean1.setCommonNumber(httpResponseGlassesRunParamBean.getCommonNumber());
        sendBleBean1.setInterveneAccMinute(httpResponseGlassesRunParamBean.getInterveneAccMinute());
        sendBleBean1.setWeekKeyFre(httpResponseGlassesRunParamBean.getWeekKeyFre());
        sendBleBean1.setWeekAccMinute(httpResponseGlassesRunParamBean.getWeekAccMinute());
        sendBleBean1.setBackWeekAccMinute0(httpResponseGlassesRunParamBean.getBackWeekAccMinute0());
        sendBleBean1.setBackWeekAccMinute1(httpResponseGlassesRunParamBean.getBackWeekAccMinute1());
        int backWeekAccMinute2  = httpResponseGlassesRunParamBean.getBackWeekAccMinute2();
        /**
         * 拆分高低位，分装到 Param1 Bean（低位） 和 Param2 Bean（高位）中
         */
        String lowHighHexStr = BaseCmdBean.decimalism2Hex(backWeekAccMinute2, 4);
        String[]  backWeekAccMinute2Array = BaseCmdBean.reverseStringByte(lowHighHexStr);
        sendBleBean1.setBackWeekAccMinute2(Integer.parseInt(backWeekAccMinute2Array[0], 16));

        sendBleBean2.setBackWeekAccMinute2(Integer.parseInt(backWeekAccMinute2Array[1], 16));
        sendBleBean2.setBackWeekAccMinute3(httpResponseGlassesRunParamBean.getBackWeekAccMinute3());
        sendBleBean2.setPlusInterval(httpResponseGlassesRunParamBean.getPlusInterval());
        sendBleBean2.setMinusInterval(httpResponseGlassesRunParamBean.getMinusInterval());
        sendBleBean2.setPlusInc(httpResponseGlassesRunParamBean.getPlusInc());
        sendBleBean2.setMinusInc(httpResponseGlassesRunParamBean.getMinusInc());
        sendBleBean2.setIncPer(httpResponseGlassesRunParamBean.getIncPer());
        sendBleBean2.setRunNumber(httpResponseGlassesRunParamBean.getRunNumber());
        sendBleBean2.setRunSpeed(httpResponseGlassesRunParamBean.getRunSpeed());
        sendBleBean2.setSpeedInc(httpResponseGlassesRunParamBean.getSpeedInc());
        sendBleBean2.setSpeedSegment(httpResponseGlassesRunParamBean.getSpeedSegment());
        sendBleBean2.setIntervalSegment(httpResponseGlassesRunParamBean.getIntervalSegment());


        sendBleBean3.setBackSpeedSegment(httpResponseGlassesRunParamBean.getBackSpeedSegment());
        sendBleBean3.setBackIntervalSegment(httpResponseGlassesRunParamBean.getBackIntervalSegment());
        sendBleBean3.setSpeedKeyFre(httpResponseGlassesRunParamBean.getSpeedKeyFre());
        sendBleBean3.setInterveneKeyFre(httpResponseGlassesRunParamBean.getInterveneKeyFre());
        sendBleBean3.setIntervalAccMinute(httpResponseGlassesRunParamBean.getIntervalAccMinute());
        sendBleBean3.setMinusInterval2(httpResponseGlassesRunParamBean.getMinusInterval2());
        sendBleBean3.setPlusInterval2(httpResponseGlassesRunParamBean.getPlusInterval2());
        sendBleBean3.setMinusInc2(httpResponseGlassesRunParamBean.getMinusInc2());
        sendBleBean3.setPlusInc2(httpResponseGlassesRunParamBean.getPlusInc2());
        sendBleBean3.setIncPer2(httpResponseGlassesRunParamBean.getIncPer2());
        sendBleBean3.setRunNumber2(httpResponseGlassesRunParamBean.getRunNumber2());
        sendBleBean3.setRunSpeed2(httpResponseGlassesRunParamBean.getRunSpeed2());

        sendBleBean4.setSpeedSegment2(httpResponseGlassesRunParamBean.getSpeedSegment2());
        sendBleBean4.setSpeedInc2(httpResponseGlassesRunParamBean.getSpeedInc2());
        sendBleBean4.setIntervalSegment2(httpResponseGlassesRunParamBean.getIntervalSegment2());
        sendBleBean4.setBackSpeedSegment2(httpResponseGlassesRunParamBean.getBackSpeedSegment2());
        sendBleBean4.setBackIntervalSegment2(httpResponseGlassesRunParamBean.getBackIntervalSegment2());
        sendBleBean4.setSpeedKeyFre2(httpResponseGlassesRunParamBean.getSpeedKeyFre2());
        sendBleBean4.setInterveneKeyFre2(httpResponseGlassesRunParamBean.getInterveneKeyFre2());
        sendBleBean4.setIntervalAccMinute2(httpResponseGlassesRunParamBean.getIntervalAccMinute2());
        sendBleBean4.setTrainingState(httpResponseGlassesRunParamBean.getTrainingState());
        sendBleBean4.setTrainingState2(httpResponseGlassesRunParamBean.getTrainingState2());
        sendBleBean4.setAdjustSpeed(httpResponseGlassesRunParamBean.getAdjustSpeed());
        sendBleBean4.setMaxRunSpeed(httpResponseGlassesRunParamBean.getMaxRunSpeed());
        sendBleBean4.setMinRunSpeed(httpResponseGlassesRunParamBean.getMinRunSpeed());
        sendBleBean4.setAdjustSpeed2(httpResponseGlassesRunParamBean.getAdjustSpeed2());
        sendBleBean4.setMaxRunSpeed2(httpResponseGlassesRunParamBean.getMaxRunSpeed2());
        sendBleBean5.setMinRunSpeed2(httpResponseGlassesRunParamBean.getMinRunSpeed2());

        sendBleBean5.setTxByte12(httpResponseGlassesRunParamBean.getTxByte12());
        sendBleBean5.setTxByte13(httpResponseGlassesRunParamBean.getTxByte13());
        sendBleBean5.setTxByte14(httpResponseGlassesRunParamBean.getTxByte14());
        sendBleBean5.setTxByte15(httpResponseGlassesRunParamBean.getTxByte15());
        sendBleBean5.setTxByte16(httpResponseGlassesRunParamBean.getTxByte16());
        sendBleBean5.setTxByte17(httpResponseGlassesRunParamBean.getTxByte17());
        sendBleBean5.setTxByte18(httpResponseGlassesRunParamBean.getTxByte18());
        sendBleBean5.setTxByte19(httpResponseGlassesRunParamBean.getTxByte19());


        bleDataBeanArray.put(0, sendBleBean1);
        bleDataBeanArray.put(1, sendBleBean2);
        bleDataBeanArray.put(2, sendBleBean3);
        bleDataBeanArray.put(3, sendBleBean4);
        bleDataBeanArray.put(4, sendBleBean5);
        return bleDataBeanArray;
    }


    public static HttpResponseGlassesRunParamBean httpRequestBleDataBean2ResponseBean(HttpRequestGlassesRunParamBean reqeustBean) {
        HttpResponseGlassesRunParamBean responseBean = new HttpResponseGlassesRunParamBean();

        responseBean.setMinMinusInterval(reqeustBean.getMinMinusInterval());
        responseBean.setMinPlusInterval(reqeustBean.getMinPlusInterval());
        responseBean.setCommonNumber(reqeustBean.getCommonNumber());
        responseBean.setInterveneAccMinute(reqeustBean.getInterveneAccMinute());
        responseBean.setWeekKeyFre(reqeustBean.getWeekKeyFre());
        responseBean.setWeekAccMinute(reqeustBean.getWeekAccMinute());
        responseBean.setBackWeekAccMinute0(reqeustBean.getBackWeekAccMinute0());
        responseBean.setBackWeekAccMinute1(reqeustBean.getBackWeekAccMinute1());
        int backWeekAccMinute2  = reqeustBean.getBackWeekAccMinute2();

        responseBean.setBackWeekAccMinute2(backWeekAccMinute2);

        responseBean.setBackWeekAccMinute3(reqeustBean.getBackWeekAccMinute3());
        responseBean.setPlusInterval(reqeustBean.getPlusInterval());
        responseBean.setMinusInterval(reqeustBean.getMinusInterval());
        responseBean.setPlusInc(reqeustBean.getPlusInc());
        responseBean.setMinusInc(reqeustBean.getMinusInc());
        responseBean.setIncPer(reqeustBean.getIncPer());
        responseBean.setRunNumber(reqeustBean.getRunNumber());
        responseBean.setRunSpeed(reqeustBean.getRunSpeed());
        responseBean.setSpeedInc(reqeustBean.getSpeedInc());
        responseBean.setSpeedSegment(reqeustBean.getSpeedSegment());
        responseBean.setIntervalSegment(reqeustBean.getIntervalSegment());


        responseBean.setBackSpeedSegment(reqeustBean.getBackSpeedSegment());
        responseBean.setBackIntervalSegment(reqeustBean.getBackIntervalSegment());
        responseBean.setSpeedKeyFre(reqeustBean.getSpeedKeyFre());
        responseBean.setInterveneKeyFre(reqeustBean.getInterveneKeyFre());
        responseBean.setIntervalAccMinute(reqeustBean.getIntervalAccMinute());
        responseBean.setMinusInterval2(reqeustBean.getMinusInterval2());
        responseBean.setPlusInterval2(reqeustBean.getPlusInterval2());
        responseBean.setMinusInc2(reqeustBean.getMinusInc2());
        responseBean.setPlusInc2(reqeustBean.getPlusInc2());
        responseBean.setIncPer2(reqeustBean.getIncPer2());
        responseBean.setRunNumber2(reqeustBean.getRunNumber2());
        responseBean.setRunSpeed2(reqeustBean.getRunSpeed2());

        responseBean.setSpeedSegment2(reqeustBean.getSpeedSegment2());
        responseBean.setSpeedInc2(reqeustBean.getSpeedInc2());
        responseBean.setIntervalSegment2(reqeustBean.getIntervalSegment2());
        responseBean.setBackSpeedSegment2(reqeustBean.getBackSpeedSegment2());
        responseBean.setBackIntervalSegment2(reqeustBean.getBackIntervalSegment2());
        responseBean.setSpeedKeyFre2(reqeustBean.getSpeedKeyFre2());
        responseBean.setInterveneKeyFre2(reqeustBean.getInterveneKeyFre2());
        responseBean.setIntervalAccMinute2(reqeustBean.getIntervalAccMinute2());

        responseBean.setTrainingState(reqeustBean.getTrainingState());
        responseBean.setTrainingState2(reqeustBean.getTrainingState2());
        responseBean.setAdjustSpeed(reqeustBean.getAdjustSpeed());
        responseBean.setMaxRunSpeed(reqeustBean.getMaxRunSpeed());
        responseBean.setMinRunSpeed(reqeustBean.getMinRunSpeed());
        responseBean.setAdjustSpeed2(reqeustBean.getAdjustSpeed2());
        responseBean.setMaxRunSpeed2(reqeustBean.getMaxRunSpeed2());
        responseBean.setMinRunSpeed2(reqeustBean.getMinRunSpeed2());
        responseBean.setTxByte12(reqeustBean.getTxByte12());
        responseBean.setTxByte13(reqeustBean.getTxByte13());
        responseBean.setTxByte14(reqeustBean.getTxByte14());
        responseBean.setTxByte15(reqeustBean.getTxByte15());
        responseBean.setTxByte16(reqeustBean.getTxByte16());
        responseBean.setTxByte17(reqeustBean.getTxByte17());
        responseBean.setTxByte18(reqeustBean.getTxByte18());

        return responseBean;
    }
}
