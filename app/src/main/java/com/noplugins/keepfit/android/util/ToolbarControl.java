package com.noplugins.keepfit.android.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;


import com.noplugins.keepfit.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shiyujia02 on 2017/8/30.
 */

public class ToolbarControl extends Toolbar {

    private String titleText;

    @BindView(R.id.toolbar_left_button1)
    public ImageView leftButton;
    @BindView(R.id.toolbar_title1)
    public TextView titleTextView;
    @BindView(R.id.linear_left)
    public LinearLayout linear_left;
    @BindView(R.id.toolbar_right_button1)
    public TextView toolbar_right_button1;
    @BindView(R.id.rel_quanju)
    public RelativeLayout rel_quanju;

    public ToolbarControl(Context context) {
        super(context);
        init(context, null);
    }

    public ToolbarControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.title_relayout, this, true);
        ButterKnife.bind(this, view);

        //很重要
        setContentInsetsRelative(0, 0);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ToolbarControl, 0, 0);
        titleText = a.getString(R.styleable.ToolbarControl_titleText);
//        Log.d(TAG, titleText);
        if(null!=titleText){
            titleTextView.setText(titleText);
        }

        a.recycle();
    }

    public void setTitle(String titleStr) {
        if (titleTextView != null) {
            titleTextView.setText(titleStr);
        }
    }

    public void setQuanJuColor(int color){
        if(rel_quanju!=null){
            rel_quanju.setBackgroundColor(color);
        }

    }

    public void setRightNumbers(String titleStr) {
        if (toolbar_right_button1 != null) {
            toolbar_right_button1.setText(titleStr);
        }
    }

    public void setTitleByResourceId(int rid) {
        if (titleTextView != null) {
            titleTextView.setText(rid);
        }
    }

    public void showRight(){
        if(toolbar_right_button1!=null){
            toolbar_right_button1.setVisibility(VISIBLE);
        }
    }

    public void hideRight(){
        if(toolbar_right_button1!=null){
            toolbar_right_button1.setVisibility(INVISIBLE);
        }
    }

    public void showLeft(){
        if(leftButton !=null){
            leftButton.setVisibility(VISIBLE);
        }
    }

    public void hideLeft(){
        if(leftButton !=null){
            leftButton.setVisibility(INVISIBLE);
        }
    }



    public void hide() {
        this.setVisibility(View.GONE);
    }

    public void setBackButtonOnClickListerner(OnClickListener listerner) {
        if (linear_left != null && listerner != null) {
            linear_left.setOnClickListener(listerner);
        }
    }


    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }
}
