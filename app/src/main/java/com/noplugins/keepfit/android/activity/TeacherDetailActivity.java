package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.util.ui.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherDetailActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.biaoqian_view)
    FlowLayout biaoqian_view;
    @BindView(R.id.yitie_biaoqian_view)
    FlowLayout yitie_biaoqian_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_teacher_detail);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setFlowlayout1();

        setFlowlayout2();
    }

    private void setFlowlayout2() {
        // 关键字集合
        List<String> list = new ArrayList<>();
        list.add("关键词");
        list.add("关键词");
        list.add("关键词");
        list.add("关键词");
        list.add("关键词");
        // 设置文字大小
        yitie_biaoqian_view.setTextSize(13);
        // 设置文字颜色
        yitie_biaoqian_view.setTextColor(getResources().getColor(R.color.color_6D7278));
        // 设置文字背景
        yitie_biaoqian_view.setBackgroundResource(R.drawable.biaoqian_bg);
        // 设置文字水平margin
        yitie_biaoqian_view.setHorizontalSpacing(15);
        // 设置文字垂直margin
        yitie_biaoqian_view.setVerticalSpacing(15);
        // 设置文字水平padding
        yitie_biaoqian_view.setTextPaddingH(8);
        // 设置文字垂直padding
        yitie_biaoqian_view.setTextPaddingH(4);
        // 设置UI与点击事件监听
        // 最后调用setViews方法
        yitie_biaoqian_view.setViews(list, new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(String content) {
                Toast.makeText(TeacherDetailActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        });

        // 增加关键字
        yitie_biaoqian_view.addView("关键字六", new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(String content) {
                Toast.makeText(TeacherDetailActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFlowlayout1() {
        // 关键字集合
        List<String> list = new ArrayList<>();
        list.add("关键词");
        list.add("关键词");
        list.add("关键词");
        list.add("关键词");
        // 设置文字大小
        biaoqian_view.setTextSize(12);
        // 设置文字颜色
        biaoqian_view.setTextColor(Color.BLACK);
        // 设置文字背景
        biaoqian_view.setBackgroundResource(R.drawable.bg_frame);
        // 设置文字水平margin
        biaoqian_view.setHorizontalSpacing(15);
        // 设置文字垂直margin
        biaoqian_view.setVerticalSpacing(15);
        // 设置文字水平padding
        biaoqian_view.setTextPaddingH(8);
        // 设置文字垂直padding
        biaoqian_view.setTextPaddingH(4);
        // 设置UI与点击事件监听
        // 最后调用setViews方法
        biaoqian_view.setViews(list, new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(String content) {
                Toast.makeText(TeacherDetailActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        });

        // 增加关键字
        biaoqian_view.addView("关键字六", new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(String content) {
                Toast.makeText(TeacherDetailActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
