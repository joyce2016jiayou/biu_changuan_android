package com.noplugins.keepfit.android.util.ui.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.PopUpAdapter;

import java.util.List;

/**
 * 自定义PopupWindow
 * Created by 05 on 2016/9/29.
 */
public class SpinnerPopWindow<T> extends PopupWindow {
    private LayoutInflater inflater;
    private RecyclerView mListView;
    private List<String> list;
    private PopUpAdapter popAdapter;
    private Context context;

    public SpinnerPopWindow(Context context, List<String> list, PopUpAdapter.OnItemClickListener clickListener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
        init(clickListener);
    }

    private void init(PopUpAdapter.OnItemClickListener clickListener){
        View view = inflater.inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = (RecyclerView) view.findViewById(R.id.popup_listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mListView.setLayoutManager(layoutManager);
        popAdapter = new PopUpAdapter(context,list);

        popAdapter.setmOnItemClickListener(clickListener);

        mListView.setAdapter(popAdapter);
    }
//    convertView = inflater.inflate(R.layout.spinner_item_drop,null);
//    viewHolder.textTv = (TextView) convertView.findViewById(R.id.tv_spinner_item);


}