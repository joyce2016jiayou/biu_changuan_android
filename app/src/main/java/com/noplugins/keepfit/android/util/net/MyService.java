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
     *
     * @return
     */
    @FormUrlEncoded
    @POST("answerApi/login/login")
    Observable<Bean<Object>> fast_login(@FieldMap Map<String, String> map);

    /**
     * 获取验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("getVerifyCode")
    Observable<Bean<Object>> get_yanzhengma(@FieldMap Map<String, String> map);

    /**
     * 验证验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("verifyCode")
    Observable<Bean<Object>> check_yanzhengma(@FieldMap Map<String, String> map);

    /**
     * 注册
     *
     * @return
     */
    @FormUrlEncoded
    @POST("setPassword")
    Observable<Bean<Object>> register(@FieldMap Map<String, String> map);

    /**
     * 登录
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("userlogin")
    Observable<Bean<Object>> login(@Body RequestBody json);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("resetPassword")
    Observable<Bean<Object>> update_password(@FieldMap Map<String, String> map);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("choiceRole")
    Observable<Bean<Object>> select_role(@FieldMap Map<String, String> map);

    /**
     * 获取七牛token
     *
     * @return
     */
    @FormUrlEncoded
    @POST("getPicToken")
    Observable<Bean<Object>> get_qiniu_token(@FieldMap Map<String, String> map);

    /**
     * 获取七牛token
     *
     * @return
     */
    @FormUrlEncoded
    @POST("getPicUrl")
    Observable<Bean<Object>> get_qiniu_url(@FieldMap Map<String, String> map);

    /**
     * 提交审核资料
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("submitAudit")
    Observable<Bean<Object>> submit_information(@Body RequestBody json);

    /**
     * 获取审核状态
     *
     * @return
     */
    @FormUrlEncoded
    @POST("getAuditResult")
    Observable<Bean<Object>> get_check_status(@FieldMap Map<String, String> map);

    /**
     * 获取审核状态
     *
     * @return
     */
    @FormUrlEncoded
    @POST("dayView")
    Observable<Bean<Object>> get_class_resource(@FieldMap Map<String, String> map);

    /**
     * 获取审核状态
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("addClass")
    Observable<Bean<Object>> add_class(@Body RequestBody map);
    /**
     * 获取审核状态
     * @return
     */
    @FormUrlEncoded
    @POST("resourceList")
    Observable<Bean<Object>> class_list(@FieldMap Map<String, Object> map);

    /**
     * 获取审核状态
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getMaxPerson")
    Observable<Bean<Object>> get_max_num(@Body RequestBody json);

    /**
     * 获取审核状态
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("dayViewList")
    Observable<Bean<Object>> get_month_view(@Body RequestBody json);

    /**
     * 获取审核状态
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("searchMessage")
    Observable<Bean<Object>> zhanghu_message_list(@Body RequestBody json);
    /**
     * 获取审核状态
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("agreeApply")
    Observable<Bean<Object>> agreeApply(@Body RequestBody json);

    /**
     * 获取申请详情
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("classDetail")
    Observable<Bean<Object>> get_shenqing_detail(@Body RequestBody json);
    /**
     * 获取申请详情
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("alreadyRead")
    Observable<Bean<Object>> change_status(@Body RequestBody json);
    /**
     * 获取申请详情
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("initializeMessage")
    Observable<Bean<Object>> get_message_all(@Body RequestBody json);


}
