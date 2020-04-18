package com.allen.aplib.http.nohttp;

import com.allen.aplib.http.ResponseBean;
import com.allen.aplib.http.NohttpCallBack;
import com.yanzhenjie.nohttp.rest.Response;

import org.jetbrains.annotations.NotNull;

/**
 * @user Allen
 * @date 2020/4/8 0008 22:29
 * @purpose
 */
public class Test {
    public void test11(){
        ModeJsonRequest ss = new ModeJsonRequest("", ResponseBean.class);
        NohttpUtils.Companion.getInst().request(0, ss, new NohttpCallBack<ResponseBean>() {
            @Override
            public void onFinish(int what) {

            }

            @Override
            public void onFailed(int what, @NotNull Response<ResponseBean> response) {

            }

            @Override
            public void onSucceed(int what, @NotNull Response<ResponseBean> response) {
                ResponseBean responseBean = response.get();
            }

            @Override
            public void onStart(int what) {

            }
        });
    }
}
