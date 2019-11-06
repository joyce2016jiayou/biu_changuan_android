package com.noplugins.keepfit.android.util.data;

public class PwdCheckUtil {

    public static boolean isLetterDigit(String str) {

        if (str.length()< 6 || str.length()> 18){
            return false;
        }
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        return isDigit && isLetter && str.matches(regex);
    }


    /**
     * 验证支付密码是否合法
     * @param str
     * @return
     */
    public static boolean isPayPassWord(String str) {

        if (str.length()!= 6){
            return false;
        }
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        return isDigit && str.matches(regex);
    }

}
