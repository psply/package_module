package com.psply.mypackage.utils.request;

import com.psply.mypackage.utils.network.Request.BaseParse;
import com.psply.mypackage.utils.network.Request.BaseResult;

/**
 * Data: 2018/12/15
 * Author: shipan
 * Description:
 */
public class LoginParse extends BaseParse {

    @Override
    public BaseResult parseSuccessResult(String result) {
        LoginResult loginResult = new LoginResult();

        // TODO

        return loginResult;
    }


}
