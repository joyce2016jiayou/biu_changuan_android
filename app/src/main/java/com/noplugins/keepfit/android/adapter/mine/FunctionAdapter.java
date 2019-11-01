package com.noplugins.keepfit.android.adapter.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.mine.MineFunctionBean;

import java.util.List;

public class FunctionAdapter extends BaseAdapter {
    private List<MineFunctionBean> strings;
    private LayoutInflater layoutInflater;
    private Context context;

    public FunctionAdapter(Context mcontext, List<MineFunctionBean> mstrings) {
        this.strings = mstrings;
        context = mcontext;
        layoutInflater = LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FunctionAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_mine_function, parent,false);
            holder = new FunctionAdapter.ViewHolder();
            holder.tag_value = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tag_layout = (LinearLayout) convertView.findViewById(R.id.ll_click);
            holder.iv_img = convertView.findViewById(R.id.iv_img);
            convertView.setTag(holder);
        } else {
            holder = (FunctionAdapter.ViewHolder) convertView.getTag();
        }
        holder.tag_value.setText(strings.get(position).getName());
        Glide.with(context)
                .load(strings.get(position).getDrawImg())
                .placeholder(R.drawable.logo_gray)
                .into(holder.iv_img);


        return convertView;
    }

    class ViewHolder {
        TextView tag_value;
        ImageView iv_img;
        LinearLayout tag_layout;
    }
}
