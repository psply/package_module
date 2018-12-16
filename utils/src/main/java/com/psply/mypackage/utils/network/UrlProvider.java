package com.psply.mypackage.utils.network;

/**
 * Data: 2018/12/15
 * Author: shipan
 * Description:
 */
public class UrlProvider {

    private static UrlProvider mUrlProvider;

    private UrlProvider() {

    }

    public static UrlProvider getInstance() {
        if (mUrlProvider == null) {
            synchronized (UrlProvider.class) {
                if (mUrlProvider == null) {
                    mUrlProvider = new UrlProvider();
                }
            }
        }
        return mUrlProvider;
    }

    public String getLoginRequestUrl() {
        return "login";
    }

}
