package com.noplugins.keepfit.android.util.ui.jiugongge.listen;

import android.view.MotionEvent;
import android.view.View;

import com.noplugins.keepfit.android.util.ui.jiugongge.adapter.CCRRecyclerViewHolder;


/**
 * 在此写用途
 *
 * @Author: Acheng
 * @Email: 345887272@qq.com
 * @Date: 2017-09-22 13:59
 * @Version: V1.0 <描述当前版本功能>
 */

public interface CCROnRVItemChildTouchListener {
    boolean onRvItemChildTouch(CCRRecyclerViewHolder holder, View childView, MotionEvent event);
}
