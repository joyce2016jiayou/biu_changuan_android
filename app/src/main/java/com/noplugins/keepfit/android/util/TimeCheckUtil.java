package com.noplugins.keepfit.android.util;

import com.noplugins.keepfit.android.entity.TimeSelectEntity;

import java.util.ArrayList;
import java.util.List;

public class TimeCheckUtil {


    /**
     * 判断当前选择的时间是否在 已选择的时间段内
     * @param timeSelectEntities
     * @param nowSelectTime
     * @return
     */
    public static boolean isCoincide(List<TimeSelectEntity> timeSelectEntities,String nowSelectTime){
        if (timeSelectEntities.size() == 0){
            return false;
        }

        int timeHour = Integer.parseInt(nowSelectTime.split(":")[0]);
        int timeMinute = Integer.parseInt(nowSelectTime.split(":")[1]);
        for (int i = 0; i < timeSelectEntities.size(); i++) {
            int a = timeSelectEntities.get(i).getStartTimeHour()*60
                    +timeSelectEntities.get(i).getStartTimeMinute();
            int b = timeSelectEntities.get(i).getEndTimeHour()*60
                    +timeSelectEntities.get(i).getEndTimeMinute();
            int c = timeHour*60 + timeMinute;
            if (c > a && c< b){
                return true;
            }
        }
        return false;
    }

    /**
     * 点击选择结束时间
     * @param timeSelectEntities
     * @param nowSelectTime
     * @param nowEndTime
     * @return
     */
    public static boolean isCoincide(List<TimeSelectEntity> timeSelectEntities,
                                     String nowSelectTime,String nowEndTime){
        if (timeSelectEntities.size() == 0){
            return false;
        }
        int timeHour = Integer.parseInt(nowSelectTime.split(":")[0]);
        int timeMinute = Integer.parseInt(nowSelectTime.split(":")[1]);

        int timeEndHour = Integer.parseInt(nowEndTime.split(":")[0]);
        int timeEndMinute = Integer.parseInt(nowEndTime.split(":")[1]);

        for (int i = 0; i < timeSelectEntities.size(); i++) {
            int a = timeSelectEntities.get(i).getStartTimeHour()*60
                    +timeSelectEntities.get(i).getStartTimeMinute();
            int b = timeSelectEntities.get(i).getEndTimeHour()*60
                    +timeSelectEntities.get(i).getEndTimeMinute();
            int c = timeHour*60 + timeMinute;
            int d = timeEndHour*60 + timeEndMinute;

            //当选择的开始时间大于当前区间段的开始时间，且选择的开始时间小于当前区间的结束时间
            if (c > a  && c< b ){
                return true;
            }
            //当选择的结束时间大于当前区间段的开始时间，且选择的结束时间小于当前区间的结束时间
            if (d > a && d< b){
                return true;
            }
            //当选择的开始时间小于当前区间段的开始时间，且选择的结束时间大于当前区间的开始时间
            if (c< a && d > a){
                return true;
            }

        }
        return false;
    }



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
    public static ArrayList<Integer> checkList(TimeSelectEntity ent) {
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
