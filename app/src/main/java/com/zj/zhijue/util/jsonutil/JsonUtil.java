package com.zj.zhijue.util.jsonutil;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Type;
import com.alibaba.fastjson.TypeReference;
import com.zj.zhijue.http.request.IBaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
    public static Object json2objectWithDataCheck(String json, TypeToken typeToken) {
        try {
            return new Gson().fromJson(json, typeToken.getType());
        } catch (Exception e) {
            return null;
        }
    }

    public static Object json2objectWithDataCheck(String json, Type type) {
        try {
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
            return null;
        }
    }

    public static Object json2objectWithDataCheck(Reader charStream, TypeToken typeToken) {
        try {
            return new Gson().fromJson(charStream, typeToken.getType());
        } catch (Exception e) {
            return null;
        }
    }

    public static Object json2objectWithDataCheck(String json, TypeReference t) {
        try {
            return new Gson().fromJson(json, t.getType());
        } catch (JsonSyntaxException ie) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has(IBaseRequest.DATA)) {
                    jsonObject.put(IBaseRequest.DATA, null);
                }
                return new Gson().fromJson(jsonObject.toString(), t.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return null;
    }

    public static String object2json(Object obj) {
        return new Gson().toJson(obj);
    }
}
