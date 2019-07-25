package com.noplugins.keepfit.android.util.net;



import com.alibaba.fastjson.annotation.JSONField;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.entity.Token;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by limengtao on 2017/3/17.
 */

public interface MyService {

    /**
     * 登录
     * @return
     */
    @FormUrlEncoded
    @POST("answerApi/login/login")
    Observable<Bean<Object>> fast_login(@FieldMap Map<String, String> map);

    /**
     * 获取验证码
     * @return
     */
    @FormUrlEncoded
    @POST("getVerifyCode")
    Observable<Bean<Object>> get_yanzhengma(@FieldMap Map<String, String> map);
    /**
     * 验证验证码
     * @return
     */
    @FormUrlEncoded
    @POST("verifyCode")
    Observable<Bean<Object>> check_yanzhengma(@FieldMap Map<String, String> map);
    /**
     * 注册
     * @return
     */
    @FormUrlEncoded
    @POST("setPassword")
    Observable<Bean<Object>> register(@FieldMap Map<String, String> map);
    /**
     * 登录
     * @return
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("userlogin")
    Observable<Bean<Object>> login(@Body RequestBody json);

    /**
     * 修改密码
     * @return
     */
    @FormUrlEncoded
    @POST("resetPassword")
    Observable<Bean<Object>> update_password(@FieldMap Map<String, String> map);

    /**
     * 修改密码
     * @return
     */
    @FormUrlEncoded
    @POST("choiceRole")
    Observable<Bean<Object>> select_role(@FieldMap Map<String, String> map);

    /**
     * 获取七牛token
     * @return
     */
    @FormUrlEncoded
    @POST("getPicToken")
    Observable<Bean<Object>> get_qiniu_token(@FieldMap Map<String, String> map);
    /**
     * 获取七牛token
     * @return
     */
    @FormUrlEncoded
    @POST("getPicUrl")
    Observable<Bean<Object>> get_qiniu_url(@FieldMap Map<String, String> map);



}
