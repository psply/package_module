package com.psply.mypackage.utils.network.params;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Data: 2018/12/15
 * Author: shipan
 * Description:
 */
public class RequestParamsBuilder {

    private Map<String, String> params;

    public RequestParamsBuilder params(Map<String, String> params)
    {
        this.params = params;
        return this;
    }

    public RequestParamsBuilder addParams(String key, String val)
    {
        if (this.params == null)
        {
            params = new LinkedHashMap<String, String>();
        }
        params.put(key, val);
        return this;
    }

    public String buildParamString() {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder urlstr = new StringBuilder();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        int i = 0;
        while (iterator.hasNext())
        {
            String key = iterator.next();
            String value = params.get(key);
            if (i == 0) {
                urlstr.append(key).append("=").append(value);
            } else {
                urlstr.append("&").append(key).append("=").append(value);
            }
            i++;
        }
        return urlstr.toString();
    }

}
