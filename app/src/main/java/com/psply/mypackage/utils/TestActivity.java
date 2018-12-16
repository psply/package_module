package com.psply.mypackage.utils;

import android.app.Activity;
import android.os.Bundle;
import com.psply.mypackage.utils.network.Request.BaseResult;
import com.psply.mypackage.utils.network.okhttp.RequestManager;
import com.psply.mypackage.utils.request.LoginReqCallBack;
import com.psply.mypackage.utils.request.LoginRequest;
import com.psply.mypackage.utils.request.LoginParse;

/**
 * Data: 2018/12/15
 * Author: shipan
 * Description:
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestManager.getInstance().sendGetReq(new LoginRequest(),new LoginReqCallBack(){

            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onSuccess(BaseResult result) {

            }
        },new LoginParse());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
