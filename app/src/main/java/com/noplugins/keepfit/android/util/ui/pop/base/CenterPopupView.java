package com.noplugins.keepfit.android.util.ui.pop.base;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScaleAlphaAnimator;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.util.ui.pop.inteface.ViewCallBack;

import static com.lxj.xpopup.enums.PopupAnimation.ScaleAlphaFromCenter;

/**
 * Description: 在中间显示的Popup
 * Create by dance, at 2018/12/8
 */
public class CenterPopupView extends BasePopupView {
    protected FrameLayout centerPopupContainer;
    private int layout_view;
    private ViewCallBack viewCallBack;
    public CenterPopupView(@NonNull Context context,int layout,ViewCallBack m_viewCallBack) {
        super(context);
        layout_view = layout;
        viewCallBack = m_viewCallBack;
        centerPopupContainer = findViewById(com.lxj.xpopup.R.id.centerPopupContainer);
    }

    @Override
    protected int getPopupLayoutId() {
        return com.lxj.xpopup.R.layout._xpopup_center_popup_view;
    }

    View contentView;

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        //contentView = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), centerPopupContainer, false);
        contentView = LayoutInflater.from(getContext()).inflate(layout_view, centerPopupContainer, false);
        viewCallBack.onReturnView(contentView,this);
        LayoutParams params = (LayoutParams) contentView.getLayoutParams();
        params.gravity = Gravity.CENTER;
        centerPopupContainer.addView(contentView, params);
        getPopupContentView().setTranslationX(popupInfo.offsetX);
        getPopupContentView().setTranslationY(popupInfo.offsetY);
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight());

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setTranslationY(0);
    }

    /**
     * 具体实现的类的布局
     *
     * @return
     */
    protected int getImplLayoutId() {
        return 0;
    }

    protected int getMaxWidth() {
        return popupInfo.maxWidth == 0 ? (int) (XPopupUtils.getWindowWidth(getContext()) * 0.86f)
                : popupInfo.maxWidth;
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        return new ScaleAlphaAnimator(getPopupContentView(), ScaleAlphaFromCenter);
    }
}
