package com.allen.aplib.http.rxjava2;

import com.allen.aplib.bean.BaseBean;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

/**
 * @user Allen
 * @date 2020/4/18 0018 22:45
 * @purpose
 */
public class XXXX<T> {
    public void createService(Class<T> tClass){
        AppService ss =  RxUtils.Companion.getIns().getService(AppService.class);
        ss.getMainData();

    }
}
