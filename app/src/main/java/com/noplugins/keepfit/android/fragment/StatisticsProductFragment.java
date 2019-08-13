package com.noplugins.keepfit.android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.PieBean;
import com.noplugins.keepfit.android.entity.StatisticsProductEntity;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.BarVerticalAndPolylineChart;
import com.openxu.cview.chart.bean.BarBean;
import com.openxu.cview.chart.bean.ChartLable;
import com.openxu.cview.chart.piechart.PieChartLayout;

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

public class StatisticsProductFragment extends Fragment {
    private View view;

    @BindView(R.id.tv_pinxiao)
    TextView tv_pinxiao;
    @BindView(R.id.tv_danjia)
    TextView tv_danjia;

    @BindView(R.id.tv_consume_null)
    TextView tv_consume_null;

    @BindView(R.id.tv_age_null)
    TextView tv_age_null;

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
            get_product_statistics();
        }


        return view;
    }

    private void get_product_statistics(){
        Map<String, Object> params = new HashMap<>();
        params.put("type", 2);
        params.put("gymAreaNum",1001);//场馆编号

        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e(TAG, "用户统计：" + json_params);

        Subscription subscription = Network.getInstance("用户统计", getContext())
                .get_statistics(requestBody,new ProgressSubscriberNew<>(StatisticsProductEntity.class,
                        (statisticsProductEntity, message_id) -> {
                            if (statisticsProductEntity.getConsume() == null) {
                                tv_consume_null.setVisibility(View.VISIBLE);
                                chart_consume.setVisibility(View.GONE);
                            } else {
                                tv_consume_null.setVisibility(View.GONE);
                                chart_consume.setVisibility(View.VISIBLE);
                            }
                            if (statisticsProductEntity.getSale() == null) {
                                tv_age_null.setVisibility(View.VISIBLE);
                                chart_user_age.setVisibility(View.GONE);
                            } else {
                                tv_age_null.setVisibility(View.GONE);
                                chart_user_age.setVisibility(View.VISIBLE);
                            }
                            initView(statisticsProductEntity);
                        }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> objectBean) {

                    }

                    @Override
                    public void onError(String error) {
                        tv_consume_null.setVisibility(View.VISIBLE);
                        chart_consume.setVisibility(View.GONE);
                        tv_age_null.setVisibility(View.VISIBLE);
                        chart_user_age.setVisibility(View.GONE);
                        Log.e(TAG, "获取产品统计数据失败：" + error);
                        Toast.makeText(getActivity().getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                },getContext(),true));
    }

    private void initView(StatisticsProductEntity statisticsProductEntity) {

        tv_pinxiao.setText("过去30天的平效："+statisticsProductEntity.getEfficiency()+"元/m2");
        tv_danjia.setText("过去30天的客单价："+statisticsProductEntity.getPrice()+"元/人");
        /*
         * 圆环宽度
         * ringWidth > 0 :空心圆环，内环为白色，可以在内环中绘制字
         * ringWidth <=0 :实心
         */
        if (statisticsProductEntity.getConsume().size() > 0){
            chart_consume.setRingWidth(30);
            chart_consume.setLineLenth(8); // //指示线长度
            chart_consume.setTagModul(PieChartLayout.TAG_MODUL.MODUL_LABLE);       //在扇形图上不显示tag
            chart_consume.setDebug(false);
            chart_consume.setLoading(true);
            //请求数据
            List<PieBean> datalist = new ArrayList<>();
            for (int i = 0; i < statisticsProductEntity.getConsume().size(); i++) {
                datalist.add(new PieBean(statisticsProductEntity.getConsume().get(i).getValue(),
                        statisticsProductEntity.getConsume().get(i).getTime()));
            }
            //显示在中间的lable
            List<ChartLable> tableList = new ArrayList<>();
            tableList.add(new ChartLable("总消费量：1000件", 24, getResources().getColor(R.color.text_color_light_gray)));
            chart_consume.setLoading(false);
            //参数1：数据类型   参数2：数量字段名称   参数3：名称字段   参数4：数据集合   参数5:lable集合
            chart_consume.setChartData(PieBean.class, "Numner", "Name",datalist ,tableList);
        }


        //年龄柱形图

        if (statisticsProductEntity.getSale().size() > 0){
            chart_user_age.setBarWidth(60);
            chart_user_age.setBarItemSpace(20);  //柱间距
            chart_user_age.setDebug(false);
            chart_user_age.setBarColor(new int[]{Color.parseColor("#5F93E7")});
            //X轴
            List<String> ageStrXList = new ArrayList<>();
            //柱状图数据
            List<List<BarBean>> ageDataList = new ArrayList<>();
            for(int i = 0; i<statisticsProductEntity.getSale().size(); i++){
                //此集合为柱状图上一条数据，集合中包含几个实体就是几个柱子
                List<BarBean> list = new ArrayList<>();
                list.add(new BarBean(statisticsProductEntity.getSale().get(i).getResult(), ""));
                ageDataList.add(list);
                ageStrXList.add(statisticsProductEntity.getSale().get(i).getTime());
            }
            chart_user_age.setLoading(false);
            chart_user_age.setData(ageDataList, ageStrXList);
        }
    }
}
