package com.zj.zhijue.util.jsonutil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializer;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class XGsonUtil {
    private static JsonParser jsonParser = new JsonParser();

    public static JsonObject getJsonObject(String json) throws Exception {
        if (json == null || json.length() == 0) {
            return new JsonObject();
        }
        return jsonParser.parse(json).getAsJsonObject();
    }

    public static JsonArray getJsonArray(String json) throws Exception {
        if (json == null || json.length() == 0) {
            return new JsonArray();
        }
        return jsonParser.parse(json).getAsJsonArray();
    }

    public static Object getObjectFromJson(boolean useExpose, String json, Class cls) throws Exception {
        Gson gson;
        if (useExpose) {
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        } else {
            gson = new Gson();
        }
        return gson.fromJson(json, cls);
    }

    public static Object getObjectFromJson(String json, Type type) {
        return new Gson().fromJson(json, type);
    }

    public static List getListFromJson(boolean useExpose, String json, Class cls) throws Exception {
        List list = new ArrayList();
        JsonArray array = getJsonArray(json);
        for (int i = 0; i < array.size(); i++) {
            list.add(getObjectFromJson(useExpose, array.get(i).getAsJsonObject().toString(), cls));
        }
        return list;
    }

    public static List getListFromJson(boolean useExpose, JsonArray array, Class cls) throws Exception {
        List list = new ArrayList();
        for (int i = 0; i < array.size(); i++) {
            list.add(getObjectFromJson(useExpose, array.get(i).getAsJsonObject().toString(), cls));
        }
        return list;
    }

    public static String getJsonFromObject(boolean useExpose, Object object) {
        Gson gson;
        if (useExpose) {
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        } else {
            gson = new Gson();
        }
        return gson.toJson(object);
    }

    public static String getJsonFromList(boolean useExpose, Collection list) {
        Gson gson;
        if (useExpose) {
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        } else {
            gson = new Gson();
        }
        return gson.toJson(list);
    }

    public static String getJsonFromObject(boolean useExpose, JsonSerializer serializer, Object object) {
        GsonBuilder builder = new GsonBuilder();
        if (serializer != null) {
            builder.registerTypeAdapter(serializer.getClass(), serializer);
        }
        if (useExpose) {
            builder.excludeFieldsWithoutExposeAnnotation();
        }
        return builder.create().toJson(object);
    }

    @Deprecated
    public static Object getParamFromJsonObject(JsonObject jsonObject, String string, Class clazz) {
        Object object = null;

        try {
            if (clazz.getClass().getSimpleName().equals(String.class.getClass().getSimpleName())) {
                object = jsonObject.get(string).getAsString();
            } else if (clazz.getClass().getSimpleName().equals(Long.class.getClass().getSimpleName())) {
                object = Long.valueOf(jsonObject.get(string).getAsLong());
            } else if (clazz.getClass().getSimpleName().equals(Integer.class.getClass().getSimpleName())) {
                object = Integer.valueOf(jsonObject.get(string).getAsInt());
            } else if (clazz.getClass().getSimpleName().equals(Short.class.getClass().getSimpleName())) {
                object = Short.valueOf(jsonObject.get(string).getAsShort());
            } else if (clazz.getClass().getSimpleName().equals(Byte.class.getClass().getSimpleName())) {
                object = Byte.valueOf(jsonObject.get(string).getAsByte());
            } else if (clazz.getClass().getSimpleName().equals(Double.class.getClass().getSimpleName())) {
                object = Double.valueOf(jsonObject.get(string).getAsDouble());
            } else if (clazz.getClass().getSimpleName().equals(Float.class.getClass().getSimpleName())) {
                object = Float.valueOf(jsonObject.get(string).getAsFloat());
            } else if (clazz.getClass().getSimpleName().equals(Boolean.class.getClass().getSimpleName())) {
                object = Boolean.valueOf(jsonObject.get(string).getAsBoolean());
            } else if (clazz.getClass().getSimpleName().equals(Character.class.getClass().getSimpleName())) {
                object = Character.valueOf(jsonObject.get(string).getAsCharacter());
            }

            return Primitives.wrap(clazz).cast(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static Object json2object(String json, TypeToken typeToken) {
        try {
            return new Gson().fromJson(json, typeToken.getType());
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

    public static Object json2object(String json, Type type) {
        try {
            return new Gson().fromJson(json, type);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

    public static String object2json(Object obj) {
        return new Gson().toJson(obj);
    }
}
