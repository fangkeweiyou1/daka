package com.zhang.daka.kaoshi.danci;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.jiyun_greendao.info.WordInfo;
import com.wushiyi.mvp.base.SimpleFragment;
import com.zhang.daka.R;
import com.zhang.daka.event.IntervalEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by zhangyuncai on 2019/11/9.
 */
public class DanciVerticalFragment extends SimpleFragment {
    int position;
    RecyclerView mRecyclerView;
    private Disposable subscribe;
    private ProgressBar pb_dancivertical;

    @Override
    public int getLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_dancivertical;
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initView() {

        pb_dancivertical = getView().findViewById(R.id.pb_dancivertical);
        pb_dancivertical.setMax(10);

        if (getArguments() != null) {
            position = getArguments().getInt("position", 0);
            List<WordInfo> wordInfos = (List<WordInfo>) getArguments().getSerializable("datas");
            mRecyclerView = getView().findViewById(R.id.rv_dancivertical);
            DanciVerticalAdapter mAdapter = new DanciVerticalAdapter(wordInfos);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(mAdapter);
            LinearSnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(mRecyclerView);
        }

    }

    @Override
    public void lazyFetchData() {

    }

    public void inverval() {
        subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Timber.d("----------->>>>>>>>-----------aLong:" + aLong);
                    if (aLong % 10 == 0 && aLong != 0) {
                        int firstVisibleItemPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                        firstVisibleItemPosition++;
                        mRecyclerView.smoothScrollToPosition(firstVisibleItemPosition);
                    }
                    pb_dancivertical.setProgress(Math.toIntExact(aLong % 10), true);
                }, throwable -> throwable.printStackTrace());
    }

    @Override
    public void onPause() {
        super.onPause();
        endInterval();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && isViewPrepared()) {
            endInterval();
        }
    }

    private void endInterval() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onIntervalEvent(IntervalEvent event) {
        if (event != null && event.position == position) {
            inverval();
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
