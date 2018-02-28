package com.open.learn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.learn.R;
import com.open.learn.activity.DetailActivity;
import com.open.learn.adapter.HomeRecyclerAdapter;
import com.open.learn.listener.BaseMessageEvent;
import com.open.learn.listener.HomeMessageEvent;
import com.open.learn.model.HomeModel;
import com.open.learn.presenter.HomePresenter;
import com.open.learn.utils.Const;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * first fragment.
 */
public class HomeFragment extends Fragment {
    private final static String TAG = "HomeFragment";

    private HomePresenter mPresenter;
    private HomeRecyclerAdapter mAdapter;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewHierarchy;
        viewHierarchy = inflater.inflate(R.layout.fragment_frist, container, false);

        initView(viewHierarchy);

        loadData();

        return viewHierarchy;
    }

    private void initView(View viewHierarchy) {
        RecyclerView recyclerView = (RecyclerView) viewHierarchy.findViewById(R.id.recycler_first);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HomeRecyclerAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> gotoDetailPage(position));
    }

    private void loadData() {
        mPresenter = new HomePresenter();
        mPresenter.getData();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        mPresenter.destroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHomeMessageEvent(HomeMessageEvent event) {
        // if not current fragment data load
        if (event.moduleTag != Const.MODULE_FIRST) {
            return;
        }
        // deal with data load
        switch (event.loadStatus) {
            case BaseMessageEvent.STATUS_SUCCESS:
                mAdapter.setData((HomeModel) event.dataModel);
                break;
            case BaseMessageEvent.STATUS_FAIL:
                // load fail
                break;
            default:
                break;
        }
    }

    private void gotoDetailPage(int position) {
        String id = mAdapter.getSelectedItemId(position);
        if (id == null || id.isEmpty()) {
            return;
        }
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(Const.KEY_KNOWLEDGE_ID, id);
        startActivity(intent);
    }
}
