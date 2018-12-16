package com.psply.mypackage.utils.network.Request;

/**
 * Data: 2018/12/15
 * Author: shipan
 * Description:
 */
public interface BaseReqCallBack {

    void onFailure(Exception e);

    void onSuccess(BaseResult result);

}
