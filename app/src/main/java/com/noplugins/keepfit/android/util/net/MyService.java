package com.noplugins.keepfit.android.util.net;


import com.alibaba.fastjson.annotation.JSONField;
import com.noplugins.keepfit.android.bean.CalenderEntity;
import com.noplugins.keepfit.android.bean.DictionaryeBean;
import com.noplugins.keepfit.android.bean.LoginBean;
import com.noplugins.keepfit.android.bean.PrivateDetailBean;
import com.noplugins.keepfit.android.bean.RiChengBean;
import com.noplugins.keepfit.android.bean.WxPayBean;
import com.noplugins.keepfit.android.bean.mine.BalanceListBean;
import com.noplugins.keepfit.android.bean.mine.WalletBean;
import com.noplugins.keepfit.android.entity.AddClassEntity;
import com.noplugins.keepfit.android.entity.CheckEntity;
import com.noplugins.keepfit.android.entity.ClassTypeEntity;
import com.noplugins.keepfit.android.entity.LoginEntity;
import com.noplugins.keepfit.android.entity.TeacherEntity;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.entity.Token;

import java.util.List;
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
    Observable<Bean<String>> get_yanzhengma(@FieldMap Map<String, String> map);

    /**
     * 验证验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("verifyCode")
    Observable<Bean<String>> check_yanzhengma(@FieldMap Map<String, Object> map);

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
    Observable<Bean<LoginEntity>> login(@Body RequestBody json);


    /**
     * 验证码登陆登录
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("verifyCodeLogin")
    Observable<Bean<LoginBean>> verifyCodeLogin(@Body RequestBody json);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("forgetPassword")
    Observable<Bean<String>> update_password(@FieldMap Map<String, String> map);

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
    Observable<Bean<CheckEntity>> get_check_status(@Body RequestBody json);

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
    Observable<Bean<AddClassEntity>> add_class(@Body RequestBody map);

    /**
     * 获取审核状态
     *
     * @return
     */
    @FormUrlEncoded
    @POST("resourceList")
    Observable<Bean<Object>> class_list(@FieldMap Map<String, Object> map);

    /**
     * 获取审核状态
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getMaxPerson")
    Observable<Bean<Object>> get_max_num(@Body RequestBody json);

    /**
     * 获取审核状态
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("dayViewList")
    Observable<Bean<Object>> get_month_view(@Body RequestBody json);

    /**
     * 获取审核状态
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("searchMessage")
    Observable<Bean<Object>> zhanghu_message_list(@Body RequestBody json);

    /**
     * 获取审核状态
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("agreeApply")
    Observable<Bean<Object>> agreeApply(@Body RequestBody json);

    /**
     * 获取申请详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("classDetail")
    Observable<Bean<Object>> get_shenqing_detail(@Body RequestBody json);

    /**
     * 获取申请详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("alreadyRead")
    Observable<Bean<Object>> change_status(@Body RequestBody json);

    /**
     * 获取申请详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("initializeMessage")
    Observable<Bean<Object>> get_message_all(@Body RequestBody json);

    /**
     * 获取申请详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getFreeTeacher")
    Observable<Bean<TeacherEntity>> get_teacher_list(@Body RequestBody json);

    /**
     * 邀请
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("inviteTeacher")
    Observable<Bean<Object>> invite(@Body RequestBody json);

    /**
     * 取消邀请
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("cancelInvite")
    Observable<Bean<Object>> cancel_invite(@Body RequestBody json);

    /**
     * 获取用户/产品 统计
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("statistics")
    Observable<Bean<Object>> get_statistics(@Body RequestBody json);

    /**
     * 取消邀请
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("leagueClass")
    Observable<Bean<Object>> class_detail(@Body RequestBody json);

    /**
     * 修改密码
     *
     * @return 是否修改成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("modificationPassword")
    Observable<Bean<Object>> update_my_password(@Body RequestBody json);

    /**
     * 设置高低峰时段
     *
     * @return 是否设置成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("setHighAndLowTime")
    Observable<Bean<Object>> setHighAndLowTime(@Body RequestBody json);

    /**
     * 设置高低峰时段
     *
     * @return 是否设置成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("teacherDetail")
    Observable<Bean<Object>> teacherDetail(@Body RequestBody json);


    /**
     * 批量绑定用户
     *
     * @return 是否设置成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("bindingRole")
    Observable<Bean<Object>> bindingRole(@Body RequestBody json);

    /**
     * 获取已绑定列表
     *
     * @return 是否设置成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findBindingRoles")
    Observable<Bean<Object>> findBindingRoles(@Body RequestBody json);

    /**
     * 批量绑定教练
     *
     * @return 是否设置成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("gymBinding")
    Observable<Bean<Object>> bindingTeacher(@Body RequestBody json);

    /**
     * 获取已绑定列表
     *
     * @return 是否设置成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findBindingTeachers")
    Observable<Bean<Object>> findBindingTeachers(@Body RequestBody json);

    /**
     * 产品反馈
     *
     * @return 是否设置成功
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("feeaBackData")
    Observable<Bean<Object>> feedback(@Body RequestBody json);

    /**
     * 获取我的账户
     *
     * @return 账户信息
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("searchWallet")
    Observable<Bean<Object>> searchWallet(@Body RequestBody json);

    /**
     * 获取我的账单列表
     *
     * @return 账户信息
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("searchWalletDetail")
    Observable<Bean<Object>> searchWalletDetail(@Body RequestBody json);

    /**
     * 发送账号
     *
     * @return 账户信息
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("userCheckIn")
    Observable<Bean<Object>> sen_order(@Body RequestBody json);

    /**
     * 获取房间类型
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findAreaPlace")
    Observable<Bean<List<ClassTypeEntity>>> get_class_type(@Body RequestBody json);

    /**
     * 获取支付信息 -支付宝
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("memberOrderPay")
    Observable<Bean<String>> memberOrderPay(@Body RequestBody json);

    /**
     * 获取支付信息 -微信
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("memberOrderPay")
    Observable<Bean<WxPayBean>> memberOrderPayWx(@Body RequestBody json);

    /**
     * 教练详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("memberOrderPay")
    Observable<Bean<PrivateDetailBean>> teacherDetails(@Body RequestBody json);

    /**
     * 钱包
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("myBalance")
    Observable<Bean<WalletBean>> myBalance(@Body RequestBody json);

    /**
     * 明细列表
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("myBalanceList")
    Observable<Bean<BalanceListBean>> myBalanceList(@Body RequestBody json);

    /**
     * 明细详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("myBalance")
    Observable<Bean<BalanceListBean.ListBean>> myBalanceListDetail(@Body RequestBody json);

    /**
     * 修改手机号
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("updatePhone")
    Observable<Bean<String>> updatePhone(@Body RequestBody json);

    /**
     * 设置密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("setPassword")
    Observable<Bean<String>> setPassword(@FieldMap Map<String, Object> params);

    /**
     * 修改密码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("forgetPassword")
    Observable<Bean<String>> forgetPassword(@Body RequestBody json);

    /**
     * 获取日历
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("yearView")
    Observable<Bean<CalenderEntity>> get_rili(@Body RequestBody json);

    /**
     * 获取首页数据
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("areaSchedule")
    Observable<Bean<RiChengBean>> get_shouye_date(@Body RequestBody json);

    /**
     * 获取首页类型
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("searchDict")
    Observable<Bean<List<DictionaryeBean>>> get_types(@Body RequestBody json);


}
