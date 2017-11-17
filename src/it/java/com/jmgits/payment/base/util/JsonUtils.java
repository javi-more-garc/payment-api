package com.jmgits.payment.base.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by javi.more.garc on 12/11/17.
 */
public final class JsonUtils {

    private static Gson GSON = new GsonBuilder().create();

    public static String write(Object object){
        return GSON.toJson(object);
    }

    public static <T> T read(String json, Class<T> clazz){
        return GSON.fromJson(json, clazz);
    }

    public static <T> T read(String json, Type type){
        return GSON.fromJson(json, type);
    }
}
