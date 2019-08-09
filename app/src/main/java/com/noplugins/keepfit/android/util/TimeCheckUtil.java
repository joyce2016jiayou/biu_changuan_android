package com.noplugins.keepfit.android.util;

import com.noplugins.keepfit.android.entity.TimeEntity;

import java.util.ArrayList;

public class TimeCheckUtil {
    /**
     * 比较两个时间段数组是否有重合
     * @author qiulong
     * @param timeArray1
     * @param timeArray2
     * @return 有重合 true;
     */
    public static boolean compare(ArrayList<Integer> timeArray1,
                                  ArrayList<Integer> timeArray2) {
        for (int i : timeArray1) {
            if (timeArray2.contains(i))
                return true;
        }
        return false;
    }

    /**
     * 将时间段转换成数组
     * @author qiulong
     * @param ent
     * @return
     */
    public static ArrayList<Integer> checkList(TimeEntity ent) {
        // 先将时间转换成分来计算
        int timeStart = (ent.getStartTimeHour() * 60) + ent.getStartTimeMinute();
        int timeEnd = (ent.getEndTimeHour() * 60) + ent.getEndTimeMinute();
        // 将时间段封装成一个数组
        ArrayList<Integer> timeArray = new ArrayList<Integer>();
        if (timeEnd > timeStart) {// 开始时间小于结束时间
            for (int i = timeStart; i <= timeEnd; i++) {
                timeArray.add(i);// 添加开始时间至结束时间为止的时间
            }
        } else {// 开始时间大于结束时间
            for (int i = timeStart; i < 24 * 60; i++) {
                timeArray.add(i);// 添加开始时间至当天0点以前的剩余时间
            }
            for (int i = 0; i <= timeEnd; i++) {
                timeArray.add(i);// 添加0点以后到结束时间为止的时间
            }
        }
        return timeArray;
    }
}
