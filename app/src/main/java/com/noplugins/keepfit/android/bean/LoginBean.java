package com.noplugins.keepfit.android.bean;

public class LoginBean {

    /**
     * gymAreaNum : GYM19091236750176
     * havePassword : 1
     * gymUserNum : GYM19081902386801
     * firstLoad : 0
     * type : 1
     * MasterName :
     * token : eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOiJHWU0xOTA4MTkwMjM4NjgwMSIsInBlcmlvZCI6NjA0ODAwMDAwLCJsb2dpblRpbWUiOjE1NzExMDU0MjcyMjUsInJvbGVzIjpbIuWcuummhuS4uyJdLCJ0eXBlIjoiT1BFUkFUT1IiLCJhY2NvdW50IjoiIn0.UJkr-kDt4yh-X5WG4r3KoDmlBc7oIHDtv3lgTuRYH1DdIh_vByXoqkxsgkc-zubekmCgnxRcEA85Ge37dHgF2w
     */

    private String gymAreaNum;
    private int havePassword;
    private String gymUserNum;
    private int firstLoad;
    private int type;//3.前台 2经理，1场馆主,0默认状态
    private String MasterName;
    private String token;
    private int havePayPassWord;
    private int memberService;

    public int getMemberService() {
        return memberService;
    }

    public void setMemberService(int memberService) {
        this.memberService = memberService;
    }

    public int getHavePayPassWord() {
        return havePayPassWord;
    }

    public void setHavePayPassWord(int havePayPassWord) {
        this.havePayPassWord = havePayPassWord;
    }

    public String getGymAreaNum() {
        return gymAreaNum;
    }

    public void setGymAreaNum(String gymAreaNum) {
        this.gymAreaNum = gymAreaNum;
    }

    public int getHavePassword() {
        return havePassword;
    }

    public void setHavePassword(int havePassword) {
        this.havePassword = havePassword;
    }

    public String getGymUserNum() {
        return gymUserNum;
    }

    public void setGymUserNum(String gymUserNum) {
        this.gymUserNum = gymUserNum;
    }

    public int getFirstLoad() {
        return firstLoad;
    }

    public void setFirstLoad(int firstLoad) {
        this.firstLoad = firstLoad;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMasterName() {
        return MasterName;
    }

    public void setMasterName(String MasterName) {
        this.MasterName = MasterName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
