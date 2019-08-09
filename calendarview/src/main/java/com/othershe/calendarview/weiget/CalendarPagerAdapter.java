package com.othershe.calendarview.weiget;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.othershe.calendarview.bean.AttrsBean;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.bean.MothEntity;
import com.othershe.calendarview.listener.CalendarViewAdapter;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.utils.SolarUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CalendarPagerAdapter extends PagerAdapter {

    //缓存上一次回收的MonthView
    private LinkedList<MonthView> cache = new LinkedList<>();
    private SparseArray<MonthView> mViews = new SparseArray<>();

    private int count;

    private int item_layout;
    private CalendarViewAdapter calendarViewAdapter;

    private AttrsBean mAttrsBean;
    List<MothEntity.DataBean> mothEntities;

    public CalendarPagerAdapter(int count,List<MothEntity.DataBean> m_mothEntities) {
        this.count = count;
        this.mothEntities = m_mothEntities;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MonthView view;
        if (!cache.isEmpty()) {
            view = cache.removeFirst();
        } else {
            view = new MonthView(container.getContext(),"#e0000000");
        }
        //根据position计算对应年、月
        int[] date = CalendarUtil.positionToDate(position, mAttrsBean.getStartDate()[0], mAttrsBean.getStartDate()[1]);
        view.setAttrsBean(mAttrsBean);
        view.setOnCalendarViewAdapter(item_layout, calendarViewAdapter);

        List<DateBean> dateBeans = CalendarUtil.getMonthDate(date[0], date[1], mAttrsBean.getSpecifyMap());
        // TODO: 2019-08-05 在这里注入接口的数据

        //mothEntities
        for (int i = 0; i < dateBeans.size(); i++) {
            for(int k=0;k<mothEntities.size();k++){
                Date month_dates = transForDate(mothEntities.get(k).getDays());

                int year = month_dates.getYear()+1900;
                int month = month_dates.getMonth()+1;
                int date_str = month_dates.getDate();

                String date_item1=year+"."+month+"."+date_str;
                int[] solars = dateBeans.get(i).getSolar();
                String date_item2 = solars[0]+"."+solars[1]+"."+solars[2];
                //Log.e("比较的日期","date_item1:"+date_item1+"date_item2---->"+date_item2);
                //Log.e("日历自带的日期","date_item2---->"+date_item2+"是否过期"+mothEntities.get(k).isPast());

                if(date_item1.equals(date_item2)){
                    dateBeans.get(i).setIs_show_circle(true);//是否显示灰点
                    if(mothEntities.get(k).isPast()){//已过期
                        dateBeans.get(i).setIs_out_class(true);
                    }else{//未过期
                        dateBeans.get(i).setIs_out_class(false);
                    }
                }
//                else{
//                    dateBeans.get(i).setIs_show_circle(false);
//                }
            }
        }
        view.setDateList(dateBeans, SolarUtil.getMonthDays(date[0], date[1]));
        mViews.put(position, view);
        container.addView(view);

        return view;
    }
    /* 时间戳转日期
     * @param ms
     * @return
     */
    public  Date transForDate(Long ms) {
        if (ms == null) {
            ms = (long) 0;
        }
        long msl = (long) ms ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date temp = null;
        if (ms != null) {
            try {
                String str = sdf.format(msl);
                temp = sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((MonthView) object);
        cache.addLast((MonthView) object);
        mViews.remove(position);
    }

    /**
     * 获得ViewPager缓存的View
     *
     * @return
     */
    public SparseArray<MonthView> getViews() {
        return mViews;
    }


    public void setAttrsBean(AttrsBean attrsBean) {
        mAttrsBean = attrsBean;
    }

    public void setOnCalendarViewAdapter(int item_layout, CalendarViewAdapter calendarViewAdapter) {
        this.item_layout = item_layout;
        this.calendarViewAdapter = calendarViewAdapter;
    }
}
