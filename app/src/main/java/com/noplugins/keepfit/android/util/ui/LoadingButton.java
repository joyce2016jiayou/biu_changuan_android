package com.noplugins.keepfit.android.util.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.noplugins.keepfit.android.R;


/**
 * Created by channey on 2017/12/3.
 * 按钮带loading效果
 *
 */

public class LoadingButton extends RelativeLayout {
    private static final String TAG = "LoadingBtn";
    private FrameLayout mWrapper;
    private TextView mBtn;
    private TextView mLoadingView;
    private TextView mNoticeTv;
    private OnClickListener mOnClickListener;
    private long mLastClickTime = 0;
    private static final int QUICK_CLICK_LIMIT_DEFAULT = 2000; //unit:ms
    private String mBtnText;
    private int mQuickClickLimit; //unit:ms
    private int mBackgroundRes;

    public LoadingButton(Context context) {
        super(context);
        initView(context);
    }

    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context,attrs);
    }

    public LoadingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context,attrs);
    }

    public void initView(final Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.widght_loading_btn,this,true);
        mWrapper = (FrameLayout) view.findViewById(R.id.loading_btn_wrapper);
        mBtn = (TextView) view.findViewById(R.id.loading_btn_content);
        mNoticeTv = (TextView) view.findViewById(R.id.loading_btn_notice);
        mLoadingView = (TextView) view.findViewById(R.id.loading_btn_loading_view);
        mBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuickClick()){
                    showNotice("请勿频繁点击");
                }else {
                    hideNotice();
                    if (mOnClickListener != null) mOnClickListener.onClick(v);
                }
            }
        });
    }

    public void initAttrs(Context context, AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton);
        mBtnText = array.getString(R.styleable.LoadingButton_btnText);
        mQuickClickLimit = array.getInt(R.styleable.LoadingButton_quickClickLimit,QUICK_CLICK_LIMIT_DEFAULT);
        mBackgroundRes = array.getResourceId(R.styleable.LoadingButton_bg,R.drawable.shape_btn_bg_40);
        array.recycle();
        mBtn.setText(mBtnText);
        mWrapper.setBackgroundResource(mBackgroundRes);
    }
    public void startLoading(){
        mBtn.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
        RotateAnimation rotateAnimation = new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setDuration(1500);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        mLoadingView.startAnimation(rotateAnimation);
    }

    public void loadingComplete(){
        mLoadingView.setVisibility(View.GONE);
        mLoadingView.clearAnimation();
        mBtn.setVisibility(View.VISIBLE);
    }

    public void setBtnOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    public boolean isQuickClick(){
        long currentTime = System.currentTimeMillis();
        long temp = currentTime - mLastClickTime;
        mLastClickTime = currentTime;
        if (temp < mQuickClickLimit){
            return true;
        }
        return false;
    }

    /**
     * show error notice
     * @param notice
     */
    public void showNotice(String notice){
        StringBuilder sb = new StringBuilder();
        sb.append("*");
        sb.append(notice);
        mNoticeTv.setText(sb);
        mNoticeTv.setVisibility(View.VISIBLE);
    }

    /**
     * hide notice view
     */
    public void hideNotice(){
        mNoticeTv.setVisibility(View.INVISIBLE);
    }

    public void setClickable(boolean clickable){
        mBtn.setEnabled(clickable);
        mBtn.setClickable(clickable);
    }
}
