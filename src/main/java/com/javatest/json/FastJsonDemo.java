package com.javatest.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastJsonDemo {
    public static void main(String[] args) {
//        ObjectToJson();
        JsonToString();
    }

    public static void  ObjectToJson(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id","123");
        map.put("name","张三");
        map.put("sex","男");
        map.put("age","15");
        /**
         * 1、先将map 进行json格式化，JSON.toJSONString(map) 返回json字符串
         * 2、然后将json字符串转换成对象
         */
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(map));
        String age = jsonObject.get("age").toString();
        System.out.println(age);//15


        List<String> list = new ArrayList<String>();
        list.add("{a,b,c}");
        list.add("{a1,b1,c1}");
        list.add("{a2,b2,c2}");
        list.add("{a3,b3,c3}");
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(list));
        System.out.println(jsonArray.get(2));//{a2,b2,c2}


        map.put("list",list);
        JSONObject jsonObject1 = JSON.parseObject(JSON.toJSONString(map));
        JSONArray jsonArray1 = jsonObject1.getJSONArray("list");
        System.out.println(jsonArray1.get(3));//{a3,b3,c3}

    }


    public static void JsonToString(){
        String jsonStr1 = "{\"param5\":\"value5\",\"param3\":\"value3\",\"param4\":\"value4\",\"param1\":\"value1\",\"param2\":\"value2\"}";
        Object object = JSON.parse(jsonStr1);
        System.out.println(object.toString());

        Map<String,Object> map = (Map<String, Object>) JSON.parse(jsonStr1);
        System.out.println(map.get("param4").toString());
        System.out.println("---------第一种写法----------");
        //复杂json转换成List
        //第一种写法
        String jsonStr2 = "{\"count\":2,\"list\":[{\"param5\":\"value5\",\"param3\":\"value3\",\"param4\":\"value4\",\"param1\":\"value1\",\"param2\":\"value2\"},{\"p1\":\"v1\",\"p2\":\"v2\",\"p3\":\"v3\",\"p4\":\"v4\",\"p5\":\"v5\"}]}";
        JSONObject jsonObject = (JSONObject) JSON.parse(jsonStr2);
        JSONArray array = (JSONArray) jsonObject.get("list");
        for (Object o : array){
            System.out.println(o.toString());
        }
        System.out.println("---------第二种写法----------");
        //第二种写法
        String jsonStr3 = "{\"count\":2,\"list\":[{\"param5\":\"value5\",\"param3\":\"value3\",\"param4\":\"value4\",\"param1\":\"value1\",\"param2\":\"value2\"},{\"p1\":\"v1\",\"p2\":\"v2\",\"p3\":\"v3\",\"p4\":\"v4\",\"p5\":\"v5\"}]}";
        Map<String,Object> map2 = (Map<String, Object>) JSON.parse(jsonStr3);
        List<Object> list2 = (List<Object>) map2.get("list");
        for(Object o:list2){
            System.out.println(o.toString());
        }
        System.out.println("---------第三种写法-------------");
        //第三种写法
        String jsonStr4 = "{\"count\":2,\"list\":[{\"param5\":\"value5\",\"param3\":\"value3\",\"param4\":\"value4\",\"param1\":\"value1\",\"param2\":\"value2\"},{\"p1\":\"v1\",\"p2\":\"v2\",\"p3\":\"v3\",\"p4\":\"v4\",\"p5\":\"v5\"}]}";
        Map<String,Object> map4 = JSON.parseObject(jsonStr4,new TypeReference<Map<String,Object>>(){});
        String jsonList = map4.get("list").toString();
        List<Object> list4 =JSON.parseObject(jsonList,new TypeReference<List<Object>>(){});
        for(Object o : list4){
            System.out.println(o.toString());
        }
        System.out.println("---------第四种写法-------------");
        String jsonStr5 = "{\"count\":2,\"list\":[{\"param5\":\"value5\",\"param3\":\"value3\",\"param4\":\"value4\",\"param1\":\"value1\",\"param2\":\"value2\"},{\"p1\":\"v1\",\"p2\":\"v2\",\"p3\":\"v3\",\"p4\":\"v4\",\"p5\":\"v5\"}]}";
        JSONObject jsonEntry = JSON.parseObject(jsonStr5);
        for(Map.Entry<String,Object> entry:jsonEntry.entrySet()){
            if(entry.getKey().equals("list")){
                JSONArray  jsonArray = JSON.parseArray(entry.getValue().toString());
                for(Object o: jsonArray){
                    System.out.println(o.toString());
                }
            }
        }

    }
}