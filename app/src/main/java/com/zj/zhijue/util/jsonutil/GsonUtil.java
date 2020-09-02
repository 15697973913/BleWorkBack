package com.zj.zhijue.util.jsonutil;

import com.zj.zhijue.bean.BaseBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 使用Gson实现对象，Json互转
 */
public class GsonUtil {
    public static Gson gson;
    /**Json转JavaBean**/
    public static final int JSON_JAVABEAN=0x10001;
    /**Json转List<T>**/
    public static final int JSON_LIST=0x10002;
    /**Json转Map<T>**/
    public static final int JSON_MAP=0x10004;

    /**
     * 将对象转换成Json格式的字符串
     * @param object 要转换成Json的对象
     * @return String:Json格式的字符串
     */
    public static String convertObject2Json(Object object) {
        gson=new Gson();
        return gson.toJson(object);
    }
    /**
     * 将Json转换成Java对象
     * @param inputStream 要转换成Java对象的inputStream
     * @param javaBean List获取Map中所包含的javaBean
     * @param convertFlag 转换类型标识
     * @return Object:Java对象
     */
    public static Object convertJson2Object(InputStream inputStream, Class<?>javaBean , int convertFlag) {
        gson=new Gson();
        Object object=null;
//      String json=inputStream2String(inputStream);
        BufferedReader reader=intputStream2BufferedReader(inputStream);
        Type type=getType(javaBean,convertFlag);
        object=gson.fromJson(reader,type);
        return object;
    }
    /**
     * 获取要转换成的对象类型
     * @param javaBean
     * @param convertFlag
     * @return
     */
    private static Type getType(Class<?> javaBean,int convertFlag) {
        Type type=null;
        switch (convertFlag) {
            case JSON_LIST:
                if (javaBean.equals(BaseBean.class)) {//Json转List泛型
                    type=new TypeToken<List<BaseBean>>(){}.getType();
                }
                break;
            case JSON_MAP:
                if (javaBean.equals(BaseBean.class)) {//Json转Map泛型
                    type=new TypeToken<Map<String,BaseBean>>(){}.getType();
                }
                break;
            case JSON_JAVABEAN://Json转JavaBean
                type=javaBean;
                break;
        }
        return type;
    }
    /**
     * 将InputStream封装成BufferedReader
     * @param inputStream
     * @return
     */
    private static BufferedReader intputStream2BufferedReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}