package com.noplugins.keepfit.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.noplugins.keepfit.android.R;

import java.util.List;

public class TypeAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    private List<String> list;

    public TypeAdapter(List<String> mlist, Context context) {
        this.context = context;
        this.list = mlist;
        this.inflater = LayoutInflater.from(context);
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TypeAdapter.viewHolder holder;
        if (convertView == null) {
            holder = new TypeAdapter.viewHolder();
            convertView = inflater.inflate(R.layout.select_type_item, null);
            holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);

            convertView.setTag(holder);
        } else {
            holder = (TypeAdapter.viewHolder) convertView.getTag();
        }
        holder.name_tv.setText(list.get(position));

        return convertView;
    }


    private class viewHolder {
        private TextView name_tv;
    }
}
