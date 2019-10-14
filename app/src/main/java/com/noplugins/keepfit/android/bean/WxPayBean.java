package com.noplugins.keepfit.android.bean;

import com.google.gson.annotations.SerializedName;

public class WxPayBean {


    /**
     * nonce_str : gcLZuQ8tX8AJ0aRc
     * package : Sign=WXPay
     * sign : 143AB219AE95AED1231B5D35ABCF7AD0
     * return_msg : OK
     * mch_id : 1558386681
     * noncestr : 5K8264ILTKCH16CQ2502SI8ZNMTM67VS
     * prepay_id : wx11161924720879109f8786df1724138200
     * sign2 : 19C1F56B61ACBC5C5D61E7711BB16B7E
     * appid : wx159898ada9f8208a
     * trade_type : APP
     * result_code : SUCCESS
     * return_code : SUCCESS
     * timestamp : 1570781964
     */

    private String nonce_str;
    @SerializedName("package")
    private String packageX;
    private String sign;
    private String return_msg;
    private String mch_id;
    private String noncestr;
    private String prepay_id;
    private String sign2;
    private String appid;
    private String trade_type;
    private String result_code;
    private String return_code;
    private String timestamp;

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getSign2() {
        return sign2;
    }

    public void setSign2(String sign2) {
        this.sign2 = sign2;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
