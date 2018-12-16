package com.psply.mypackage.utils.network.Request;

import com.psply.mypackage.utils.network.params.RequestParamsBuilder;

/**
 * Data: 2018/12/15
 * Author: shipan
 * Description:
 */
public abstract class BaseRequest {

    protected RequestParamsBuilder mReqParamsBuilder = new RequestParamsBuilder();

    public abstract String getReqUrl();

    public abstract String getPostReqBody();

    public String toString() {
        String url = getReqUrl();
        url += mReqParamsBuilder.buildParamString();
        return url;
    }

}
