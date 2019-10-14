package com.noplugins.keepfit.android.adapter;

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
import com.noplugins.keepfit.android.bean.PriceBean;

import java.util.List;

public class DatePriceTestAdapter extends BaseAdapter {
    private List<PriceBean> strings;
    private LayoutInflater layoutInflater;
    private Context context;

    public DatePriceTestAdapter(Context mcontext, List<PriceBean> mstrings) {
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
        DatePriceTestAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_date_price, parent,false);
            holder = new DatePriceTestAdapter.ViewHolder();
            holder.time = (TextView) convertView.findViewById(R.id.tv_test_time);
            holder.price = (TextView) convertView.findViewById(R.id.tv_test_price);
            convertView.setTag(holder);
        } else {
            holder = (DatePriceTestAdapter.ViewHolder) convertView.getTag();
        }
        holder.time.setText(strings.get(position).getData());
        holder.price.setText(strings.get(position).getPrice());


        return convertView;
    }

    class ViewHolder {
        TextView time;
        TextView price;
    }
}
