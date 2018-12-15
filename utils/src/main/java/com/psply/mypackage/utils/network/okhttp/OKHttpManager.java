package com.psply.mypackage.utils.network.okhttp;

/**
 * OKHttp的封装类
 */
public class OKHttpManager {


    private static class OKHttpManagerHolder {
        private static final OKHttpManager INSTANCE = new OKHttpManager();
    }

    public static OKHttpManager getInstance() {
        return OKHttpManagerHolder.INSTANCE;
    }



}
