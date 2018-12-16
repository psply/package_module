package com.psply.mypackage.utils.network.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.psply.mypackage.utils.network.Request.BaseRequest;
import com.psply.mypackage.utils.network.Request.BaseReqCallBack;
import com.psply.mypackage.utils.network.Request.BaseParse;
import com.psply.mypackage.utils.network.Request.BaseResult;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Data: 2018/12/15
 * Author: shipan
 * Description:
 */
public class RequestManager {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private static OkHttpClient mOkHttpClient;

    private RequestManager() {
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
    }

    private static class OKHttpManagerHolder {
        private static final RequestManager INSTANCE = new RequestManager();
    }

    public static RequestManager getInstance() {
        return OKHttpManagerHolder.INSTANCE;
    }

    public void sendGetReq(BaseRequest baseRquest, final BaseReqCallBack baseReqCallBack, final BaseParse baseParse) {
        if (baseRquest == null || baseReqCallBack == null || baseParse == null) {
            return;
        }
        Request request = new Request.Builder().url(baseRquest.toString()).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                baseReqCallBack.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    if (response.body() != null) {
                        handleSuccessCallBack(response.body().string(), baseReqCallBack, baseParse);
                    } else {
                        baseReqCallBack.onFailure(new Exception("response's body is null"));
                    }
                } catch (Exception e) {
                    baseReqCallBack.onFailure(e);
                }
            }
        });
    }

    public void sendPostReq(BaseRequest baseRequest, final BaseReqCallBack baseReqCallBack, final BaseParse baseParse) {
        if (baseRequest == null || baseReqCallBack == null || baseParse == null) {
            return;
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),baseRequest.getPostReqBody());
        Request request = new Request.Builder().url(baseRequest.getReqUrl()).post(requestBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                baseReqCallBack.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    if (response.body() != null) {
                        handleSuccessCallBack(response.body().string(), baseReqCallBack, baseParse);
                    } else {
                        baseReqCallBack.onFailure(new Exception("response's body is null"));
                    }
                } catch (Exception e) {
                    baseReqCallBack.onFailure(e);
                }
            }
        });
    }

    private void handleSuccessCallBack(String result, final BaseReqCallBack baseReqCallBack, final BaseParse baseParse) {
        final BaseResult baseResult = baseParse.parseSuccessResult(result);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseReqCallBack.onSuccess(baseResult);
            }
        });

    }


}
