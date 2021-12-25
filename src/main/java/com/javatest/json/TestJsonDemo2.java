package com.javatest.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * @description:
 * @author: xiaozhi
 * @create: 2020-05-21 20:51
 */
public class TestJsonDemo2 {
    /**
     * JSON实际上也是键值对（"key":"value"）
     * key 必须是字符串，value 可以是合法的 JSON 数据类型（字符串, 数字, 对象, 数组, 布尔值或 null）
     * value如果是字符串，用jsonobj.getString("key")获取
     * value如果是数  字，用jsonobj.getIntValue("key"),jsonobj.getFloatValue("key")，jsonobj.getInteger("key")等基本数据类型及其包装类的方法获取
     * value如果是布尔值，用jsonobj.getBoolean("key"),jsonobj.getBooleanValue("key")获取
     * value如果是数  组，用jsonobj.getJSONArray("key")获取
     * value如果是Object对象，用jsonobj.get("key")，获取
     * value如果是JSONObject对象，用jsonobj.getJSONObject("key")获取
     */
    /**
     * 该方法用于将已有的json字符串转换为json对象，并取出该对象中相应的key对应的value值
     * 将已有的字符串转换成jsonobject，用JSON.parseObject(jsonStr)方法
     * json中只要是{}就代表一个JSONObject,[]就代表一个JSONArray
     * 获取JSONObject对象用JSONObject jsonobject.getJSONObject("key")方法
     * 获取JSONArray对象用JSONObject jsonobject.getJSONArray("key")方法
     */
    private static void parseJSON() {
        String JsonData = "{\n" +
                "    \"name\":\"课程\",\n" +
                "    \"id\":3,\n" +
                "    \"sysProperty\": [\n" +
                "        { \"name\":\"java课程\", \"info\":[ \"js\", \"sql\", \"rabbitmq\" ] },\n" +
                "        { \"name\":\"spring课程\", \"info\":[ \"spring简介\", \"spring注解\", \"spring配置文件\" ] },\n" +
                "        { \"name\":\"python课程\", \"info\":[ \"语句\", \"语法\" ] }\n" +
                "    ]\n" +
                "}";
        JSONObject jsonobj = JSON.parseObject(JsonData); //将json字符串转换成jsonObject对象
        System.out.println(jsonobj.get("sysProperty"));
        System.out.println(jsonobj.getString("sysProperty"));
        System.out.println(jsonobj.getJSONArray("sysProperty")); //取出sysProperty对应的value值，得到一个JSONOArray对象
        System.out.println("----------------------------------------------");
        //System.out.println(jsonobj.getJSONObject("sysProperty")); 不能用该方法，因为sysProperty是一个JSONOArray对象
        //取出json对象中sysProperty对应数组中第一个json子对象的值
        System.out.println(jsonobj.getJSONArray("sysProperty").getJSONObject(0));
        System.out.println(jsonobj.getJSONArray("sysProperty").get(0));
        System.out.println(jsonobj.getJSONArray("sysProperty").getString(0));
        System.out.println("=====================================");
        //取出json对象中sysProperty对应数组中第一个json子对象下面info对应的嵌套子数组值
        System.out.println(jsonobj.getJSONArray("sysProperty").getJSONObject(0).getJSONArray("info"));
        //取出json对象中sysProperty对应数组中第一个json子对象下面info对应的嵌套子数组中第二个值
        System.out.println(jsonobj.getJSONArray("sysProperty").getJSONObject(0).getJSONArray("info").getString(1));
        System.out.println("####################################################");
        //依次取出json对象中sysProperty对应数组中的值
        JSONArray array = jsonobj.getJSONArray("sysProperty");
        getJsonArrayItem(array);
        System.out.println("--------------------------------------");
        //依次取出json对象中sysProperty对应数组中第二个json子对象下面info对应的嵌套子数组值
        JSONArray arr = jsonobj.getJSONArray("sysProperty").getJSONObject(1).getJSONArray("info");
        getJsonArrayItem(arr);

    }
    /**
     * 依次取出JSONArray中的值
     */
    private static void getJsonArrayItem(JSONArray array) {
        for (int i=0; i<array.size(); i++) {
            System.out.println(array.get(i));
        }
    }


    public static void main(String[] args) {
        parseJSON();
    }
}

