package com.open.learn.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.hugerecyclerview.EndlessFooterView;
import com.open.hugerecyclerview.listener.HugeRecyclerOnScrollListener;
import com.open.hugerecyclerview.utils.EndlessFooterUtils;
import com.open.learn.R;
import com.open.learn.adapter.SecondRecyclerAdapter;
import com.open.learn.listener.BaseMessageEvent;
import com.open.learn.listener.HomeMessageEvent;
import com.open.learn.model.SecondModel;
import com.open.learn.presenter.SecondPresenter;
import com.open.learn.utils.Const;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * second fragment.
 */
public class SecondFragment extends Fragment {
    private final static String TAG = "SecondFragment";

    private SecondPresenter mPresenter;
    private TextView mHint;

    // endless recycler view
    private RecyclerView mRecyclerView;
    private SecondRecyclerAdapter mAdapter;
    private HugeRecyclerOnScrollListener mScrollListener;
    protected EndlessFooterUtils mFooterUtil;
    // all data account in server
    private static final int TOTAL_SIZE = 64;
    // each page size count
    private static final int PAGE_SIZE = 10;
    // current data for test
    private int mCurrentNum = 0;

    public SecondFragment() {
    }

    public static SecondFragment newInstance() {
        return new SecondFragment();
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
        viewHierarchy = inflater.inflate(R.layout.fragment_second, container, false);

        initView(viewHierarchy);

        loadData();

        return viewHierarchy;
    }

    private void initView(View viewHierarchy) {
        mRecyclerView = (RecyclerView) viewHierarchy.findViewById(R.id.recycler_second);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(lm);
        mAdapter = new SecondRecyclerAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mFooterUtil = new EndlessFooterUtils(new EndlessFooterView(getContext()));
        // set scroll listener
        mScrollListener = new HugeRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                EndlessFooterView.State state = mFooterUtil.getFooterViewState(mRecyclerView);
                // still loading, do nothing
                if (EndlessFooterView.State.Loading == state) {
                    Log.d(TAG, "still loading, now return");
                    return;
                }
                // no more data
                if (mCurrentNum > TOTAL_SIZE) {
                    mFooterUtil.setEnd(mRecyclerView, PAGE_SIZE);
                    return;
                }

                refreshData();
            }
        };
        // add scroll listener
        mRecyclerView.addOnScrollListener(mScrollListener);

        mHint = (TextView) viewHierarchy.findViewById(R.id.tv_hint);
    }

    private void refreshData() {
        // loading more data
        mFooterUtil.setLoading(mRecyclerView, PAGE_SIZE);
        mPresenter.refreshData();
    }

    private void loadData() {
        mPresenter = new SecondPresenter();
        mPresenter.getData();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        mPresenter.destroy();

        EventBus.getDefault().unregister(this);
    }

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            refreshData();
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHomeMessageEvent(HomeMessageEvent event) {
        // if not current fragment data load
        if (event.moduleTag != Const.MODULE_SECOND) {
            return;
        }
        // deal with data load
        switch (event.loadStatus) {
            case BaseMessageEvent.STATUS_START:
                mHint.setText(getString(R.string.load_start));
                break;
            case BaseMessageEvent.STATUS_SUCCESS:
                mHint.setText(getString(R.string.load_success));
                mAdapter.setData((SecondModel) event.dataModel);
                break;
            case BaseMessageEvent.STATUS_FAIL:
                mHint.setText(getString(R.string.load_fail) + event.errorMsg);
                break;
            case BaseMessageEvent.STATUS_REFRESH_SUCCESS:
                mHint.setText(getString(R.string.refresh_success));
                mAdapter.addData((SecondModel) event.dataModel);
                mCurrentNum += ((SecondModel) event.dataModel).titleArray.size();
                mFooterUtil.setNormal(mRecyclerView, PAGE_SIZE);
                break;
            case BaseMessageEvent.STATUS_REFRESH_FAIL:
                mHint.setText(getString(R.string.refresh_fail) + event.errorMsg);
                mFooterUtil.setError(mRecyclerView, PAGE_SIZE, mFooterClick);
                break;
            default:
                break;
        }
    }

}
