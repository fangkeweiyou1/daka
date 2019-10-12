//package com.zhang.daka;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//
///**
// * Created by zhangyuncai on 2018/9/11.
// */
//public class SharedPreferencesUtils {
//
//    private static final String SP_NAME = "asia5b_sp";
//    private static final int SP_MODE = Context.MODE_PRIVATE;
//    private static SharedPreferences sp;
//    public static Context context;
//
//    public static SharedPreferences getSp() {
//        if (sp == null) {
//            sp = context.getSharedPreferences(SP_NAME, SP_MODE);
//        }
//        return sp;
//    }
//
//
//    public static void saveBoolean(String key, boolean value) {
//        if (getSp() != null)
//            getSp().edit().putBoolean(key, value).apply();
//    }
//
//    public static boolean loadBoolean(String key) {
//        if (getSp() != null)
//            return getSp().getBoolean(key, true);
//        return true;
//    }
//
//    public static boolean loadBoolean(String key, boolean defValue) {
//        if (getSp() != null)
//            return getSp().getBoolean(key, defValue);
//        return true;
//    }
//
//    public static void saveString(String key, String value) {
//        if (getSp() != null)
//            getSp().edit().putString(key, value).apply();
//    }
//
//
//    public static String loadString(String key) {
//        if (getSp() != null)
//            return getSp().getString(key, "");
//        return "";
//    }
//
//    public static String loadString(String key, String defValue) {
//        if (getSp() != null)
//            return getSp().getString(key, defValue);
//        return "";
//    }
//
//    public static void saveint(String key, int value) {
//        if (getSp() != null)
//            getSp().edit().putInt(key, value).apply();
//    }
//
//    public static void saveLong(String key, long value) {
//        if (getSp() != null)
//            getSp().edit().putLong(key, value).apply();
//    }
//
//    public static int loadint(String key) {
//        if (getSp() != null)
//            return getSp().getInt(key, 0);
//        return 0;
//    }
//
//    public static long loadLong(String key) {
//        if (getSp() != null)
//            return getSp().getLong(key, 0);
//        return 0;
//    }
//
//    public static int loadint(String key, int defValue) {
//        if (getSp() != null)
//            return getSp().getInt(key, defValue);
//        return 0;
//    }
//}
