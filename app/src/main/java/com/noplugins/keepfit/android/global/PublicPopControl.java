package com.noplugins.keepfit.android.global;

import android.content.Context;
import android.view.View;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.callback.PopViewCallBack;
import com.noplugins.keepfit.android.util.ui.pop.base.CenterPopupView;
import com.noplugins.keepfit.android.util.ui.pop.inteface.ViewCallBack;

public class PublicPopControl {

    private static PopViewCallBack mpopViewCallBack;

    /**
     * 提示弹出框
     *
     * @param context
     * @param popViewCallBack
     */
    public static void alert_dialog_center(Context context, PopViewCallBack popViewCallBack) {
        mpopViewCallBack = popViewCallBack;
        new XPopup.Builder(context)
                .autoOpenSoftInput(true)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .asCustom(new CenterPopupView(context, R.layout.back_invite_teacher_pop, new ViewCallBack() {
                    @Override
                    public void onReturnView(View view, BasePopupView popup) {
                        mpopViewCallBack.return_view(view, popup);
                    }

                })).show();
    }


    /**
     * 电话号码弹出框
     *
     * @param context
     * @param popViewCallBack
     */
    public static void alert_call_phone_dialog_center(Context context, PopViewCallBack popViewCallBack) {
        mpopViewCallBack = popViewCallBack;
        new XPopup.Builder(context)
                .autoOpenSoftInput(true)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .asCustom(new CenterPopupView(context, R.layout.call_pop, new ViewCallBack() {
                    @Override
                    public void onReturnView(View view, BasePopupView popup) {
                        mpopViewCallBack.return_view(view, popup);
                    }

                })).show();
    }



}
