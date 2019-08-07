package com.noplugins.keepfit.android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.PieBean;
import com.noplugins.keepfit.android.util.ui.BarVerticalAndPolylineChart;
import com.openxu.cview.chart.bean.BarBean;
import com.openxu.cview.chart.bean.ChartLable;
import com.openxu.cview.chart.piechart.PieChartLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;

public class StatisticsProductFragment extends Fragment {
    private View view;

    private PieChartLayout chart_consume;
    private BarVerticalAndPolylineChart chart_user_age;

    public static StatisticsProductFragment newInstance(String title) {
        StatisticsProductFragment fragment = new StatisticsProductFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_tongji_product, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            chart_consume = view.findViewById(R.id.chart_consume);
            chart_user_age = view.findViewById(R.id.chart_user_age);
            initView();
        }


        return view;
    }

    private void initView() {
        /*
         * 圆环宽度
         * ringWidth > 0 :空心圆环，内环为白色，可以在内环中绘制字
         * ringWidth <=0 :实心
         */
        chart_consume.setRingWidth(30);
        chart_consume.setLineLenth(8); // //指示线长度
        chart_consume.setTagModul(PieChartLayout.TAG_MODUL.MODUL_LABLE);       //在扇形图上不显示tag
        chart_consume.setDebug(false);
        chart_consume.setLoading(true);
        //请求数据
        List<PieBean> datalist = new ArrayList<>();
        datalist.add(new PieBean(20, "健身"));
        datalist.add(new PieBean(20, "私教"));
        datalist.add(new PieBean(20, "团建"));
        //显示在中间的lable
        List<ChartLable> tableList = new ArrayList<>();
        tableList.add(new ChartLable("总消费量：1000件", 24, getResources().getColor(R.color.text_color_light_gray)));
        chart_consume.setLoading(false);
        //参数1：数据类型   参数2：数量字段名称   参数3：名称字段   参数4：数据集合   参数5:lable集合
        chart_consume.setChartData(PieBean.class, "Numner", "Name",datalist ,tableList);


        //年龄柱形图

        chart_user_age.setBarWidth(60);
        chart_user_age.setBarItemSpace(20);  //柱间距
        chart_user_age.setDebug(false);
        chart_user_age.setBarColor(new int[]{Color.parseColor("#5F93E7")});
        //X轴
        List<String> ageStrXList = new ArrayList<>();
        //柱状图数据
        List<List<BarBean>> ageDataList = new ArrayList<>();
        for(int i = 0; i<6; i++){
            //此集合为柱状图上一条数据，集合中包含几个实体就是几个柱子
            List<BarBean> list = new ArrayList<>();
            list.add(new BarBean(new Random().nextInt(30), ""));
            ageDataList.add(list);
        }
        ageStrXList.add("0-12");
        ageStrXList.add("12-18");
        ageStrXList.add("18-25");
        ageStrXList.add("25-32");
        ageStrXList.add("35-50");
        ageStrXList.add(">50");
        chart_user_age.setLoading(false);
        chart_user_age.setData(ageDataList, ageStrXList);
    }
}