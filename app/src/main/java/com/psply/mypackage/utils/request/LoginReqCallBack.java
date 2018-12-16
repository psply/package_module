package com.psply.mypackage.utils.request;

import com.psply.mypackage.utils.network.Request.BaseReqCallBack;
import com.psply.mypackage.utils.network.Request.BaseResult;

/**
 * Data: 2018/12/15
 * Author: shipan
 * Description:
 */
public interface LoginReqCallBack extends BaseReqCallBack {

    @Override
    public void onFailure(Exception e);

    @Override
    public void onSuccess(BaseResult result);
}
