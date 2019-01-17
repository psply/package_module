package com.psply.mypackage.utils.storage.preference;

import android.content.Context;
import android.content.SharedPreferences;
import com.psply.mypackage.utils.context.CacheContextUtil;

import java.util.Map;

/**
 * Data: 2018/12/24
 * Author: shipan
 * Description:
 */
public class PreferenceUtil {

    private static final String SP_CACHE_KEY = "sp_cache";

    private SharedPreferences mSP = null;
    private SharedPreferences.Editor mEditor = null;

    private PreferenceUtil() {
        mSP = CacheContextUtil.getCacheContext().getSharedPreferences(SP_CACHE_KEY,Context.MODE_PRIVATE);
    }

    private static class PreferenceHolder {
        private static final PreferenceUtil INSTANCE = new PreferenceUtil();
    }

    public static PreferenceUtil getInstance() {
        return PreferenceHolder.INSTANCE;
    }

    public boolean putInt(String key, int value) {
        mEditor = mSP.edit();
        mEditor.putInt(key, value);
        return mEditor.commit();
    }

    public boolean putLong(String key, long value) {
        mEditor = mSP.edit();
        mEditor.putLong(key, value);
        return mEditor.commit();
    }

    public boolean putBoolean(String key, boolean value) {
        mEditor = mSP.edit();
        mEditor.putBoolean(key, value);
        return mEditor.commit();
    }

    public boolean putFloat(String key, float value) {
        mEditor = mSP.edit();
        mEditor.putFloat(key, value);
        return mEditor.commit();
    }

    public boolean putString(String key, String value) {
        mEditor = mSP.edit();
        mEditor.putString(key, value);
        return mEditor.commit();
    }

    public boolean removeKey(String key) {
        mEditor = mSP.edit();
        mEditor.remove(key);
        return mEditor.commit();
    }

    public Map<String, ?> getAll() {
        return mSP.getAll();
    }

    public boolean clear() {
        mEditor = mSP.edit();
        mEditor.clear();
        return mEditor.commit();
    }

}
