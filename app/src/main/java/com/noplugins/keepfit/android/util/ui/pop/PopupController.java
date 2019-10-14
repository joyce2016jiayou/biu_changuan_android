package com.noplugins.keepfit.android.util.ui.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by ThinkPad on 2017/12/8.
 */

public class PopupController {
    private int mLayoutResId;//布局
    private Context mContext;
    private PopupWindow mPopupWindow;
    View mPopupView; //弹窗布局
    private View mView;
    private Window mWindow;

    public PopupController(Context context, PopupWindow popupWindow1) {
        this.mContext = context;
        this.mPopupWindow = popupWindow1;
    }

    public void setView(int layoutResId) {
        this.mLayoutResId = layoutResId;
        this.mView = null;
        installContent();
    }

    public void setView(View mContentView) {
        this.mView = mContentView;
        this.mLayoutResId = 0;
        installContent();

    }

    private void installContent() {
        if (mLayoutResId != 0) {
            mPopupView = LayoutInflater.from(mContext).inflate(mLayoutResId, null);
        } else if (mView != null) {
            mPopupView = mView;
        }
        mPopupWindow.setContentView(mPopupView);
    }

    /**
     * 设置宽高
     *
     * @param width
     * @param height
     */
    public void setWidthAndHeight(int width, int height) {

        if (width == 0 || height == 0) {
            mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            mPopupWindow.setWidth(width);
            mPopupWindow.setHeight(height);
        }
    }

    /**
     * 设置动画
     *
     * @param animationStyle
     */
    public void setAnimationStyle(int animationStyle) {
        mPopupWindow.setAnimationStyle(animationStyle);
    }

    /**
     * 设置背景阴影等级 0.0-1.0
     *
     * @param level
     */
    public void setBackGroundLevel(float level) {
        mWindow = ((Activity) mContext).getWindow();
        WindowManager.LayoutParams layoutParams = mWindow.getAttributes();
        layoutParams.alpha = level;
        mWindow.setAttributes(layoutParams);
    }

    /**
     * 设置outside是否可点击
     *
     * @param touchable
     */
    public void setOutSideTouchable(boolean touchable) {
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setOutsideTouchable(touchable);
        mPopupWindow.setFocusable(touchable);

    }

    static class PopupParams {
        public int layoutResId;
        public Context context;
        public int mWidth, mHeight;
        public boolean isShowAnim, isShowBg;
        public float bg_level;
        public int animationStyle;
        public View mView;
        public boolean isTouchable = true;

        public PopupParams(Context mContext) {
            this.context = mContext;
        }

        public void apply(PopupController controller) {
            if (mView != null) {
                controller.setView(mView);
            } else if (layoutResId != 0) {
                controller.setView(layoutResId);
            } else {
                throw new IllegalArgumentException("popupwindow's contentView is null");
            }
            controller.setWidthAndHeight(mWidth, mHeight);

            controller.setOutSideTouchable(isTouchable);
            if (isShowBg) {
                //设置背景
                controller.setBackGroundLevel(bg_level);
            }
            if (isShowAnim) {
                controller.setAnimationStyle(animationStyle);
            }
        }
    }
}
