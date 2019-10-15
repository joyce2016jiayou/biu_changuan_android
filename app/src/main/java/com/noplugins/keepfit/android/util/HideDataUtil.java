package com.noplugins.keepfit.android.util;


import com.qiniu.android.utils.StringUtils;

/**
 *
 * 隐秘数据工具类
 *
 * @version 1.0
 * @since JDK1.7
 * @author yaomy
 * @date 2018年4月3日 上午10:19:07
 */
public class HideDataUtil {
    /**
     *
     * 方法描述 隐藏银行卡号中间的字符串（使用*号），显示前四后四
     *
     * @param cardNo
     * @return
     *
     * @author yaomy
     * @date 2018年4月3日 上午10:37:00
     */
    public static String hideCardNo(String cardNo) {
        if(StringUtils.isBlank(cardNo)) {
            return cardNo;
        }

        int length = cardNo.length();
        int beforeLength = 4;
        int afterLength = 4;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<length; i++) {
            if(i < beforeLength || i >= (length - afterLength)) {
                sb.append(cardNo.charAt(i));
            } else {
                sb.append(replaceSymbol);
            }
        }

        return sb.toString();
    }
    /**
     *
     * 方法描述 隐藏手机号中间位置字符，显示前三后三个字符
     *
     * @param phoneNo
     * @return
     *
     * @author yaomy
     * @date 2018年4月3日 上午10:38:51
     */
    public static String hidePhoneNo(String phoneNo) {
        if(StringUtils.isBlank(phoneNo)) {
            return phoneNo;
        }

        int length = phoneNo.length();
        int beforeLength = 3;
        int afterLength = 3;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<length; i++) {
            if(i < beforeLength || i >= (length - afterLength)) {
                sb.append(phoneNo.charAt(i));
            } else {
                sb.append(replaceSymbol);
            }
        }

        return sb.toString();
    }
}