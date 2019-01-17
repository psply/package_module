package com.psply.mypackage.utils.context;

import android.app.Application;
import android.content.Context;

/**
 * Data: 2018/12/24
 * Author: shipan
 * Description:
 */
public class CacheContextUtil {

    private static Context mCacheContext = null;

    public static Context getCacheContext() {
        return mCacheContext;
    }

    public static void setContext(Application application) {

        if (application == null) {
            throw new RuntimeException("context must be Application");
        }

        if (mCacheContext == null) {
            CacheContextUtil.mCacheContext = application;
        }

    }
}
