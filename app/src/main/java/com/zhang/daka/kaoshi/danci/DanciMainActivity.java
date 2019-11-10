package com.zhang.daka.kaoshi.danci;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Switch;

import com.jiyun_greendao.DBOpenHelper;
import com.jiyun_greendao.info.WordInfo;
import com.jiyun_greendao.info.WordInfoDao;
import com.wushiyi.mvp.MvpExtendsKt;
import com.wushiyi.mvp.base.BaseFragmentPagerAdapter;
import com.wushiyi.mvp.base.SimpleAppCompatActivity;
import com.zhang.daka.DakaMainActivity;
import com.zhang.daka.R;
import com.zhang.daka.event.IntervalEvent;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by zhangyuncai on 2019/11/8.
 */
public class DanciMainActivity extends SimpleAppCompatActivity {


    ViewPager mViewPager;
    private List<DanciVerticalFragment> fragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_danci;
    }

    @Override
    public void initView() {
        mViewPager = findViewById(R.id.vp_main);
        initViewPager();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(@NotNull View v) {
        switch (v.getId()) {
            case R.id.tv_danci_daka:
                startActivity(new Intent(mActivity, DakaMainActivity.class));
                break;
            case R.id.tv_danci_dancitxt:
                startActivity(new Intent(mActivity, DanciTxtActivity.class));
                break;
            case R.id.tv_danci_adddanci:
                startActivity(new Intent(mActivity, AddDanciActivity.class));
                break;
            case R.id.sw_danci_interval:
                if (((Switch) v).isChecked()) {
                    interval();
                } else {
                    endInterval();
                }
                break;
        }
    }

    @Override
    public void initData() {

    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        WordInfoDao dao = DBOpenHelper.getWordInfoDao();
        String alphas = "abcdefghijklmnopqrstuvwxyz";
        int position = 0;
        for (char c : alphas.toCharArray()) {
            List<WordInfo> wordInfoList = dao.queryBuilder().where(WordInfoDao.Properties.Alpha.eq(c + "")).build().list();
            Bundle bundle = new Bundle();
            bundle.putSerializable("datas", (Serializable) wordInfoList);
            bundle.putInt("position", position++);
            DanciVerticalFragment fragment = MvpExtendsKt.sNewStanceFragment(this, DanciVerticalFragment.class, bundle);
            fragments.add(fragment);
        }
        BaseFragmentPagerAdapter pagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(pagerAdapter);


    }

    private Disposable subscribe;

    private void interval() {
        subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Timber.d("----------->>>>>>>>-----------aLong:" + aLong);
                    EventBus.getDefault().post(new IntervalEvent(mViewPager.getCurrentItem(), aLong));
                }, throwable -> throwable.printStackTrace());
    }

    private void endInterval() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
    }

    @Override
    protected void onDestroy() {
        endInterval();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //按返回键返回桌面
        moveTaskToBack(true);
    }
}
