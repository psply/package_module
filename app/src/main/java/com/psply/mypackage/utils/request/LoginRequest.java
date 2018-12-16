package com.psply.mypackage.utils.request;

import com.psply.mypackage.utils.network.Request.BaseRequest;
import com.psply.mypackage.utils.network.UrlProvider;

/**
 * Data: 2018/12/15
 * Author: shipan
 * Description:
 */
public class LoginRequest extends BaseRequest {


    public void addParams(LoginRequestOption loginRequestOption) {
//        mReqParamsBuilder.addParams(key,value);
    }

    @Override
    public String getReqUrl() {
        return UrlProvider.getInstance().getLoginRequestUrl();
    }

    @Override
    public String getPostReqBody() {
        // TODO ：body具体实现（返回json格式）
        return null;
    }

}
