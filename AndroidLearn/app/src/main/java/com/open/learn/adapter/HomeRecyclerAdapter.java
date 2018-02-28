package com.open.learn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.learn.R;
import com.open.learn.listener.OnRecyclerItemClickListener;
import com.open.learn.model.HomeModel;
import com.open.learn.model.IDataModel;

/**
 * Created by vivian on 2017/9/21.
 * recycler view third party template: https://github.com/captain-miao/RecyclerViewUtils
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.RecyclerViewHolder> {

    private Context mContext;
    private HomeModel mHomeModel;

    public OnRecyclerItemClickListener mOnItemClickListener = null;

    public HomeRecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setData(HomeModel model) {
        mHomeModel = model;
        notifyDataSetChanged();
    }

    public String getSelectedItemId(int position) {
        if (mHomeModel == null || mHomeModel.mData == null ||
                position >= mHomeModel.mData.size()) {
            return null;
        }

        return mHomeModel.mData.get(position).id;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.home_recyclerview_item_, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.hint.setText(mHomeModel.mData.get(position).title);
    }

    @Override
    public int getItemCount() {
        return mHomeModel == null ? 0 : mHomeModel.mData.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView hint;

        RecyclerViewHolder(View view) {
            super(view);
            hint = (TextView) view.findViewById(R.id.tv_content);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        mOnItemClickListener = listener;
    }


}
