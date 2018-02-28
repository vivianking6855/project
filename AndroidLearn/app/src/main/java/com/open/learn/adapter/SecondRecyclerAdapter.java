package com.open.learn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.hugerecyclerview.adapter.BaseRecyclerAdapter;
import com.open.learn.R;
import com.open.learn.model.SecondModel;

/**
 * Created by vivian on 2017/9/21.
 * recycler view third party template: https://github.com/captain-miao/RecyclerViewUtils
 */

public class SecondRecyclerAdapter extends
        BaseRecyclerAdapter<String, SecondRecyclerAdapter.ItemViewHolder> {

    private Context mContext;

    public SecondRecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setData(SecondModel model) {
        mItemList.clear();
        mItemList.addAll(model.titleArray);
        notifyDataSetChanged();
    }

    public void addData(SecondModel data) {
        mItemList.addAll(data.titleArray);
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.home_recyclerview_item_, parent, false));
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder holder, int position) {
        holder.hint.setText(mItemList.get(position));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView hint;

        ItemViewHolder(View view) {
            super(view);
            hint = (TextView) view.findViewById(R.id.tv_content);
        }
    }

}
