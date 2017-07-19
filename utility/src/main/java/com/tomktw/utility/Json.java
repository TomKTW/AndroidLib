package com.tomktw.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Set of utilities for JSONObjects and JSONArrays.
 */
public class Json {

    /**
     * Parse JSONArray from JSONObject string body with specified key to a list of selected class that must contain constructor with single JSONObject.
     *
     * @param body        JSON object as string body.
     * @param key         Key in JSON object to get JSONArray.
     * @param objectClass Class for instantiating objects in JSON objects from array.
     * @param <T>         Type of object to be instantiated from JSON object.
     * @return List of objects with defined class parsed from JSONArray.
     */
    public static <T> List<T> parseList(String body, String key, Class<T> objectClass) {
        return parseList(parseJSONObject(body), key, objectClass);
    }

    /**
     * Parse JSONArray from JSONObject with specified key to a list of selected class that must contain constructor with single JSONObject.
     *
     * @param json        JSON object for parsing.
     * @param key         Key in JSON object to get JSONArray.
     * @param objectClass Class for instantiating objects in JSON objects from array.
     * @param <T>         Type of object to be instantiated from JSON object.
     * @return List of objects with defined class parsed from JSONArray.
     */
    public static <T> List<T> parseList(JSONObject json, String key, Class<T> objectClass) {
        return !json.isNull(key) ? parseList(json.optJSONArray(key), objectClass) : new ArrayList<T>();
    }

    /**
     * Parse JSONArray to a list of selected class that must contain constructor with single JSONObject.
     *
     * @param jsonArray   JSON array for parsing.
     * @param objectClass Class for instantiating objects in JSON objects from array.
     * @param <T>         Type of object to be instantiated from JSON object.
     * @return List of objects with defined class parsed from JSONArray.
     */
    public static <T> List<T> parseList(JSONArray jsonArray, Class<T> objectClass) {
        List<T> list = new ArrayList<>();
        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    list.add(objectClass.getDeclaredConstructor(JSONObject.class).newInstance(jsonArray.getJSONObject(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * Convert JSONObject string body from specified key to selected class that must contain constructor with single JSONObject.
     *
     * @param body        JSON object as string body.
     * @param key         Key in JSON object to get JSONObject to parse.
     * @param objectClass Class for instantiating object in JSON object.
     * @param <T>         Type of object to be instantiated from JSON object.
     * @return Object with defined class from parsed JSONObject.
     */
    public static <T> T toObject(String body, String key, Class<T> objectClass) {
        return toObject(parseJSONObject(body), key, objectClass);
    }

    /**
     * Convert JSONObject from specified key to selected class that must contain constructor with single JSONObject.
     *
     * @param json        JSON object to be parsed with.
     * @param key         Key in JSON object to get JSONObject to parse.
     * @param objectClass Class for instantiating object in JSON object.
     * @param <T>         Type of object to be instantiated from JSON object.
     * @return Object with defined class from parsed JSONObject.
     */
    public static <T> T toObject(JSONObject json, String key, Class<T> objectClass) {
        return !json.isNull(key) ? toObject(json.optJSONObject(key), objectClass) : null;
    }

    /**
     * Convert JSONObject string body to selected class that must contain constructor with single JSONObject.
     *
     * @param body        JSON object as string body.
     * @param objectClass Class for instantiating object in JSON object.
     * @param <T>         Type of object to be instantiated from JSON object.
     * @return Object with defined class from parsed JSONObject.
     */
    public static <T> T toObject(String body, Class<T> objectClass) {
        return toObject(parseJSONObject(body), objectClass);
    }

    /**
     * Convert JSONObject to selected class that must contain constructor with single JSONObject.
     *
     * @param json        JSON object to be parsed with.
     * @param objectClass Class for instantiating object in JSON object.
     * @param <T>         Type of object to be instantiated from JSON object.
     * @return Object with defined class from parsed JSONObject.
     */
    public static <T> T toObject(JSONObject json, Class<T> objectClass) {
        try {
            return objectClass.getDeclaredConstructor(JSONObject.class).newInstance(json);
        } catch (Exception e) {
            try {
                e.printStackTrace();
                return objectClass.newInstance();
            } catch (Exception e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Parse JSONObject from String without throwing JSONException.
     *
     * @param body String of JSON body.
     * @return JSONObject parsed from String or empty JSONObject if JSONException is thrown.
     */
    public static JSONObject parseJSONObject(String body) {
        try {
            return new JSONObject(body);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    /**
     * Parse JSONArray from String without throwing JSONException.
     *
     * @param body String of JSON body.
     * @return JSONArray parsed from String or empty JSONArray if JSONException is thrown.
     */
    public static JSONArray parseJSONArray(String body) {
        try {
            return new JSONArray(body);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    /**
     * Returns string value from specified key of JSONObject.
     *
     * @param jsonObject JSON object that contains specified key.
     * @param key        Key to fetch value from.
     * @param fallback   If key does not exist, return following value.
     * @return String value from key if exists, otherwise return fallback value.
     */
    public static String getString(JSONObject jsonObject, String key, String fallback) {
        return !jsonObject.isNull(key) ? jsonObject.optString(key, fallback) : fallback;
    }

    /**
     * Returns boolean value from specified key of JSONObject.
     *
     * @param jsonObject JSON object that contains specified key.
     * @param key        Key to fetch value from.
     * @param fallback   If key does not exist, return following value.
     * @return Boolean value from key if exists, otherwise return fallback value.
     */
    public static boolean getBoolean(JSONObject jsonObject, String key, boolean fallback) {
        return !jsonObject.isNull(key) ? jsonObject.optBoolean(key, fallback) : fallback;
    }

    /**
     * Returns double value from specified key of JSONObject.
     *
     * @param jsonObject JSON object that contains specified key.
     * @param key        Key to fetch value from.
     * @param fallback   If key does not exist, return following value.
     * @return Double value from key if exists, otherwise return fallback value.
     */
    public static double getDouble(JSONObject jsonObject, String key, double fallback) {
        return !jsonObject.isNull(key) ? jsonObject.optDouble(key, fallback) : fallback;
    }

    /**
     * Returns integer value from specified key of JSONObject.
     *
     * @param jsonObject JSON object that contains specified key.
     * @param key        Key to fetch value from.
     * @param fallback   If key does not exist, return following value.
     * @return Integer value from key if exists, otherwise return fallback value.
     */
    public static int getInt(JSONObject jsonObject, String key, int fallback) {
        return !jsonObject.isNull(key) ? jsonObject.optInt(key, fallback) : fallback;
    }

    /**
     * Returns long value from specified key of JSONObject.
     *
     * @param jsonObject JSON object that contains specified key.
     * @param key        Key to fetch value from.
     * @param fallback   If key does not exist, return following value.
     * @return Long value from key if exists, otherwise return fallback value.
     */
    public static long getLong(JSONObject jsonObject, String key, long fallback) {
        return !jsonObject.isNull(key) ? jsonObject.optLong(key, fallback) : fallback;
    }

    /**
     * Returns JSONArray from specified key of JSONObject.
     *
     * @param jsonObject JSON object that contains specified key.
     * @param key        Key to fetch JSONArray from.
     * @return JSONArray value from key if exists, otherwise return empty JSONArray.
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String key) {
        return !jsonObject.isNull(key) ? jsonObject.optJSONArray(key) : new JSONArray();
    }

    /**
     * Returns JSONObject from specified key of JSONObject.
     *
     * @param jsonObject JSON object that contains specified key.
     * @param key        Key to fetch JSONObject from.
     * @return JSONObject value from key if exists, otherwise return empty JSONObject.
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String key) {
        return !jsonObject.isNull(key) ? jsonObject.optJSONObject(key) : new JSONObject();
    }
}
