package com.zj.zhijue.util.jsonutil;

import org.json.JSONException;
import org.json.JSONObject;

public class Json2Util {

    public static String getString(JSONObject obj, String name) {

        if (null == obj) {
            return "";
        }

        String value = "";
        try {
            value = obj.isNull(name) ? "" : obj.getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static boolean getBoolean(JSONObject obj, String name) {

        if (null == obj) {
            return false;
        }

        boolean value = false;
        try {
            value = !obj.isNull(name) && obj.getBoolean(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static int getInt(JSONObject obj, String name) {

        if (null == obj) {
            return 0;
        }

        int value = 0;
        try {
            value = obj.isNull(name) ? 0 : obj.getInt(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static long getLong(JSONObject obj, String name) {

        if (null == obj) {
            return 0;
        }

        long value = 0;
        try {
            value = obj.isNull(name) ? 0 : obj.getLong(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static double getDouble(JSONObject obj, String name) {

        if (null == obj) {
            return 0;
        }

        double value = 0;
        try {
            value = obj.isNull(name) ? 0 : obj.getDouble(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return value;
    }

    /**
     * 获取购物车编号JSON
     *
     * @return JSON字符串
     */
    public static String getShopCartNosJson() {

        String jsonStr = "";
//        try {
//            JSONArray root = new JSONArray();//实例一个JSON数组
//            for (ModelShopCarSettle settle : settles) {
//                String shopCarNos = "";
//                for (ModelShopCarSettleItem item : settle.getModelShopCarSettleItemList()) {
//                    shopCarNos += item.getShopCarNo() + ",";
//                }
//                shopCarNos = shopCarNos.contains(",") ? shopCarNos.substring(0, shopCarNos.length() - 1) : shopCarNos;
//                JSONObject obj = new JSONObject();//实例一个的JSON对象
//                obj.put("shopNo", settle.getShopNo());//店铺编号
//                obj.put("shopCarNos", shopCarNos);//购物车编号
//                obj.put("leaveMsg", settle.getLeaveMsg());//用户备注
//                root.put(obj);
//            }
//            jsonStr = root.toString();
//        } catch (JSONException e) {
//            e.printStackTrace();
//            jsonStr = "";
//        }

        return jsonStr;
    }

    /**
     * 获取运费模版JSON
     *
     * @return JSON字符串
     */
    public static String getJsonStr() {

        String jsonStr = "";
//        try {
//            JSONArray root = new JSONArray();//实例一个JSON数组
//            for (ModelSimple modelSimple : mShipments) {
//                String id = modelSimple.getId();
//                JSONObject obj = new JSONObject();//实例一个的JSON对象
//                obj.put("id", id);//物流分组id
//                obj.put("shipment_method_id", "");//物流模板规则id
//                root.put(obj);
//            }
//            jsonStr = root.toString();
//        } catch (JSONException e) {
//            e.printStackTrace();
//            jsonStr = "";
//        }

        return jsonStr;
    }
}
