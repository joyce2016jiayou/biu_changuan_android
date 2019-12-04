package com.noplugins.keepfit.android.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.DictionaryeBean;
import java.util.List;

public class SelectChangguanAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<DictionaryeBean> list;
    private Context context;
    private static final int EMPTY_VIEW = 2;
    private static final int RESOURCE_VIEW = 1;

    public SelectChangguanAdapter(List<DictionaryeBean> mlist, Context mcontext) {
        list = mlist;
        context = mcontext;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {

        ViewHolder youYangViewHolder = new ViewHolder(view, false);
        return youYangViewHolder;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        RecyclerView.ViewHolder holder = getViewHolderByViewType(viewType, parent);
        return holder;
    }

    RecyclerView.ViewHolder holder = null;

    private RecyclerView.ViewHolder getViewHolderByViewType(int viewType, ViewGroup parent) {
        View item_view = null;
        if (viewType == EMPTY_VIEW) {
            item_view = LayoutInflater.from(context).inflate(R.layout.select_date_empty_view, parent, false);
            holder = new EmptyViewHolder(item_view, false);
        } else if (viewType == RESOURCE_VIEW) {
            item_view = LayoutInflater.from(context).inflate(R.layout.select_changguan_item, parent, false);
            holder = new ViewHolder(item_view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {
        if (view_holder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) view_holder;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });



        }
    }




    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public int getAdapterItemViewType(int position) {
        if (list.size() == 0) {
            return EMPTY_VIEW;
        } else {
            return RESOURCE_VIEW;
        }
    }


    @Override
    public int getAdapterItemCount() {
        return list.size() > 0 ? list.size() : 1;
    }




    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public EmptyViewHolder(View item_view, boolean isItem) {
            super(item_view);
            if (isItem) {
                this.view = item_view;
            }
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;

            }
        }
    }
}
