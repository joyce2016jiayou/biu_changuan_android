package com.noplugins.keepfit.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.DictionaryeBean;

import java.util.List;

public class BaseInformationTagAdapter extends BaseAdapter {
    private List<DictionaryeBean> strings;
    private LayoutInflater layoutInflater;
    private Context context;

    public BaseInformationTagAdapter(Context mcontext, List<DictionaryeBean> mstrings) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.mine_tag_item, null);
            holder = new ViewHolder();
            holder.tag_value = (TextView) convertView.findViewById(R.id.tag_value);
            holder.tag_layout = (LinearLayout) convertView.findViewById(R.id.tag_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(strings.get(position).isCheck()){
            holder.tag_layout.setBackgroundResource(R.drawable.check_information_tag_bg);
        }else{
            holder.tag_layout.setBackgroundResource(R.drawable.information_tag_bg);
        }
        holder.tag_value.setText(strings.get(position).getName());

        return convertView;
    }

    class ViewHolder {
        TextView tag_value;
        LinearLayout tag_layout;
    }
}
