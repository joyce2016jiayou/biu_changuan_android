package com.othershe.calendarview.weiget;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.othershe.calendarview.bean.AttrsBean;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.CalendarViewAdapter;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.utils.SolarUtil;

import java.util.LinkedList;
import java.util.List;

public class CalendarPagerAdapter2 extends PagerAdapter {
    //缓存上一次回收的MonthView
    private LinkedList<MonthView2> cache = new LinkedList<>();
    private SparseArray<MonthView2> mViews = new SparseArray<>();

    private int count;

    private int item_layout;
    private CalendarViewAdapter calendarViewAdapter;

    private AttrsBean mAttrsBean;

    public CalendarPagerAdapter2(int count) {
        this.count = count;
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
        MonthView2 view;
        if (!cache.isEmpty()) {
            view = cache.removeFirst();
        } else {
            view = new MonthView2(container.getContext(),"#ffffff");
        }
        //根据position计算对应年、月
        int[] date = CalendarUtil.positionToDate(position, mAttrsBean.getStartDate()[0], mAttrsBean.getStartDate()[1]);
        view.setAttrsBean(mAttrsBean);
        view.setOnCalendarViewAdapter(item_layout, calendarViewAdapter);

        List<DateBean> dateBeans = CalendarUtil.getMonthDate(date[0], date[1], mAttrsBean.getSpecifyMap());

        view.setDateList(dateBeans, SolarUtil.getMonthDays(date[0], date[1]));
        mViews.put(position, view);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((MonthView2) object);
        cache.addLast((MonthView2) object);
        mViews.remove(position);
    }

    /**
     * 获得ViewPager缓存的View
     *
     * @return
     */
    public SparseArray<MonthView2> getViews() {
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
