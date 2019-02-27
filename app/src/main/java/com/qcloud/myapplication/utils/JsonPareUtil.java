package com.qcloud.myapplication.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qcloud.myapplication.beans.App;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Description：json解析工具
 * author：Smoker
 * 2019/2/27 13:56
 */
public class JsonPareUtil {

    // 通过 Gson 解析
    public static List<App> parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        return gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType());
    }

    // 通过 JsonObject 解析
    public static List<JSONObject> parseJSONWithJsonObject(String jsonData) {
        List<JSONObject> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // 每个jsonObject都相当于一个实体类
                list.add(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
