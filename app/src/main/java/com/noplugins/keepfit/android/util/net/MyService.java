package com.noplugins.keepfit.android.util.net;



import com.noplugins.keepfit.android.util.net.entity.Bean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by limengtao on 2017/3/17.
 */

public interface MyService {

    /**
     * 登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("answerApi/login/login")
    Observable<Bean<Object>> fast_login(@FieldMap Map<String, String> map);






}
