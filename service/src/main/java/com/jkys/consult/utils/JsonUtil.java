package com.jkys.consult.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    private static Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<Map<String, Object>>() {}.getType
            (), new GsonTypeAdapter()).create();


    private JsonUtil() {
    }


    /**
     * 转成json
     */
    public static String GsonString(Object object) {
        return gson.toJson(object);
    }

    /**
     * 转成bean
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        return gson.fromJson(gsonString, cls);
    }

    /**
     * 转成list
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        return gson.fromJson(gsonString, new TypeToken<List<T>>() {}.getType());
    }

    /**
     * 转成list中有map的
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        return gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {}.getType());
    }

    /**
     * 转成map的
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        return gson.fromJson(gsonString, new TypeToken<Map<String, Object>>() {}.getType());
    }
}

