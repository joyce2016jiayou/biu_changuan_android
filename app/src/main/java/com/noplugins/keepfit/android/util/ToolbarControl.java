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
    @BindView(R.id.toolbar_right_button_Tex)
    public TextView toolbar_right_button_Tex;
    @BindView(R.id.rel_quanju)
    public RelativeLayout rel_quanju;
    @BindView(R.id.toolbar_right_button_Image)
    public ImageView toolbar_right_button_Image;
    @BindView(R.id.linear_right)
    public LinearLayout linear_right;

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
        if (null != titleText) {
            titleTextView.setText(titleText);
        }

        a.recycle();
    }

    public void setTitle(String titleStr) {
        if (titleTextView != null) {
            titleTextView.setText(titleStr);
        }
    }

    public void setBgColor(int color) {
        if (rel_quanju != null) {
            rel_quanju.setBackgroundColor(color);
        }
    }

    public void setTitleTextByResourceId(int rid) {
        if (titleTextView != null) {
            titleTextView.setText(rid);
        }
    }

    public void showRightTextView() {
        if (toolbar_right_button_Tex != null && toolbar_right_button_Image != null) {
            toolbar_right_button_Tex.setVisibility(VISIBLE);
            toolbar_right_button_Image.setVisibility(GONE);

        }
    }

    public void hideRightTextView() {
        if (toolbar_right_button_Tex != null && toolbar_right_button_Image != null) {
            toolbar_right_button_Tex.setVisibility(GONE);
            toolbar_right_button_Image.setVisibility(GONE);
        }
    }

    public void showRightImageView() {
        if (toolbar_right_button_Image != null && toolbar_right_button_Tex != null) {
            toolbar_right_button_Image.setVisibility(VISIBLE);
            toolbar_right_button_Tex.setVisibility(GONE);

        }
    }

    public void hideRightImageView() {
        if (toolbar_right_button_Image != null && toolbar_right_button_Tex != null) {
            toolbar_right_button_Image.setVisibility(GONE);
            toolbar_right_button_Tex.setVisibility(GONE);
        }
    }

    public void showLeft() {
        if (leftButton != null) {
            leftButton.setVisibility(VISIBLE);
        }
    }

    public void hideLeft() {
        if (leftButton != null) {
            leftButton.setVisibility(GONE);
        }
    }


    public void hideRight() {
        if (leftButton != null) {
            toolbar_right_button_Image.setVisibility(GONE);
            toolbar_right_button_Tex.setVisibility(GONE);
        }
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }


    public void hide() {
        this.setVisibility(View.GONE);
    }

    public void setBackButtonOnClickListerner(OnClickListener listerner) {
        if (linear_left != null && listerner != null) {
            linear_left.setOnClickListener(listerner);
        }
    }

    public void setRightButtonOnClickListerner(OnClickListener listerner) {
        if (linear_right != null && listerner != null) {
            linear_right.setOnClickListener(listerner);
        }
    }


}
