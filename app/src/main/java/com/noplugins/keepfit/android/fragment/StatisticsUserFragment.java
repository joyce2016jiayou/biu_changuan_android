package com.noplugins.keepfit.android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.PieBean;
import com.noplugins.keepfit.android.entity.StatisticsUserEntity;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.BarVerticalChart;
import com.openxu.cview.chart.bean.BarBean;
import com.openxu.cview.chart.bean.ChartLable;
import com.openxu.cview.chart.piechart.PieChartLayout;
import com.openxu.utils.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import rx.Subscription;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

public class StatisticsUserFragment extends Fragment {
    private View view;
    //    @BindView(R.id.chart_ratio)
    private PieChartLayout chart_ratio;

    @BindView(R.id.tv_user_sum)
    TextView tv_user_sum;
    @BindView(R.id.tv_age_sum)
    TextView tv_age_sum;
    private BarVerticalChart chart_user_time;
    private BarVerticalChart chart_user_age;

    public static StatisticsUserFragment newInstance(String title) {
        StatisticsUserFragment fragment = new StatisticsUserFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_tongji_user, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            chart_ratio = view.findViewById(R.id.chart_ratio);
            chart_user_time = view.findViewById(R.id.chart_user_time);
            chart_user_age = view.findViewById(R.id.chart_user_age);
//            initView();
            get_user_statistics();
        }


        return view;
    }

    private void get_user_statistics(){
        Map<String, Object> params = new HashMap<>();
        params.put("type", 1);
        params.put("gymAreaNum",1001);//场馆编号

        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e(TAG, "用户统计：" + json_params);

        Subscription subscription = Network.getInstance("用户统计", getContext())
                .get_statistics(requestBody,new ProgressSubscriberNew<>(StatisticsUserEntity.class,
                        (statisticsUserEntity, message_id) -> {
                    initView(statisticsUserEntity);
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> objectBean) {

                    }

                    @Override
                    public void onError(String error) {

                    }
                },getContext(),true));
    }

    private void initView(StatisticsUserEntity statisticsUserEntity) {
        tv_user_sum.setText("进馆总人数："+statisticsUserEntity.getNum());
        tv_age_sum.setText("进馆总人数："+statisticsUserEntity.getNum());
        /*
         * 圆环宽度
         * ringWidth > 0 :空心圆环，内环为白色，可以在内环中绘制字
         * ringWidth <=0 :实心
         */
        chart_ratio.setRingWidth(30);
        chart_ratio.setLineLenth(8); // //指示线长度
        chart_ratio.setTagModul(PieChartLayout.TAG_MODUL.MODUL_LABLE);       //在扇形图上不显示tag
        chart_ratio.setDebug(false);
        chart_ratio.setLoading(true);
        //请求数据
        List<PieBean> datalist = new ArrayList<>();
        datalist.add(new PieBean(statisticsUserEntity.getPerson().getMan(), "男"));
        datalist.add(new PieBean(statisticsUserEntity.getPerson().getWomen(), "女"));
        //显示在中间的lable
        List<ChartLable> tableList = new ArrayList<>();
        tableList.add(new ChartLable("总人数："+statisticsUserEntity.getNum()+"人", 24, getResources().getColor(R.color.text_color_light_gray)));
        chart_ratio.setLoading(false);
        //参数1：数据类型   参数2：数量字段名称   参数3：名称字段   参数4：数据集合   参数5:lable集合
        chart_ratio.setChartData(PieBean.class, "Numner", "Name",datalist ,tableList);

        //健身时间柱形图
        chart_user_time.setBarWidth(50);
        chart_user_time.setBarItemSpace(15);  //柱间距
        chart_user_time.setDebug(false);
        chart_user_time.setBarColor(new int[]{Color.parseColor("#5F93E7")});
        //X轴
        List<String> strXList = new ArrayList<>();
        //柱状图数据
        List<List<BarBean>> dataList = new ArrayList<>();
        for(int i = 0; i<statisticsUserEntity.getFitness().size(); i++){
            //此集合为柱状图上一条数据，集合中包含几个实体就是几个柱子
            List<BarBean> list = new ArrayList<>();
            list.add(new BarBean(statisticsUserEntity.getFitness().get(i).getResult(), ""));
            dataList.add(list);
            strXList.add(statisticsUserEntity.getFitness().get(i).getTime());
        }
        chart_user_time.setLoading(false);
        chart_user_time.setData(dataList, strXList);

        //年龄柱形图
        chart_user_age.setBarWidth(60);
        chart_user_age.setBarItemSpace(20);  //柱间距
        chart_user_age.setDebug(false);
        chart_user_age.setBarColor(new int[]{Color.parseColor("#5F93E7")});
        //X轴
        List<String> ageStrXList = new ArrayList<>();
        //柱状图数据
        List<List<BarBean>> ageDataList = new ArrayList<>();
        for(int i = 0; i<statisticsUserEntity.getAge().size(); i++){
            //此集合为柱状图上一条数据，集合中包含几个实体就是几个柱子
            List<BarBean> list = new ArrayList<>();
            list.add(new BarBean(statisticsUserEntity.getAge().get(i).getResult(), ""));
            ageDataList.add(list);
            ageStrXList.add(statisticsUserEntity.getAge().get(i).getTime());
        }
        chart_user_age.setLoading(false);
        chart_user_age.setData(ageDataList, ageStrXList);

    }
}
