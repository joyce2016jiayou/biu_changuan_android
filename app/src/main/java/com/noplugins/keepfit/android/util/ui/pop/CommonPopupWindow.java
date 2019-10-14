package com.noplugins.keepfit.android.util.ui.pop;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by ThinkPad on 2017/12/8.
 */

public class CommonPopupWindow extends PopupWindow {
    final PopupController popupController;

    public CommonPopupWindow(Context context) {
        popupController = new PopupController(context, this);
    }

    public interface ViewInterface {
        void getChildView(View view, int layoutResId);
    }

    @Override
    public int getWidth() {
        return popupController.mPopupView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return popupController.mPopupView.getMeasuredHeight();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        popupController.setBackGroundLevel(1.0f);
    }

    public static class Builder {
        private final PopupController.PopupParams params;
        private ViewInterface listener;

        public Builder(Context context) {
            params = new PopupController.PopupParams(context);
        }

        public Builder setView(int layoutResId) {
            params.mView = null;
            params.layoutResId = layoutResId;
            return this;
        }

        public Builder setView(View view) {
            params.mView = view;
            params.layoutResId = 0;
            return this;
        }

        public Builder setViewOnClickListener(ViewInterface listener) {
            this.listener = listener;
            return this;
        }

        public Builder setWidthAndHeight(int width, int height) {
            params.mWidth = width;
            params.mHeight = height;
            return this;
        }

        public Builder setBackGroundLevel(float level) {
            params.isShowBg = true;
            params.bg_level = level;
            return this;
        }

        public Builder setOutSideTouchable(boolean touchable) {
            params.isTouchable = touchable;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle) {
            params.animationStyle = animationStyle;
            params.isShowAnim = true;
            return this;
        }

        public CommonPopupWindow create() {
            final CommonPopupWindow popupWindow = new CommonPopupWindow(params.context);
            params.apply(popupWindow.popupController);
            if (listener != null && params.layoutResId != 0) {
                listener.getChildView(popupWindow.popupController.mPopupView, params.layoutResId);
            }
            CommonUtil.measureWidthAndHeight(popupWindow.popupController.mPopupView);
            return popupWindow;

        }

    }

}
