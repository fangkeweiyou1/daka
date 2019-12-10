package com.zhang.daka.kaoshi.danci.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.wushiyi.mvp.MvpExtendsKt;
import com.wushiyi.mvp.base.BaseFragmentPagerAdapter;
import com.wushiyi.mvp.base.SimpleAppCompatActivity;
import com.wushiyi.mvp.base.SimpleFragment;
import com.zhang.daka.DakaMainActivity;
import com.zhang.daka.R;
import com.zhang.daka.event.FullScreenEvent;
import com.zhang.daka.event.IntervalEvent;
import com.zhang.daka.event.ShowTypeEvent;
import com.zhang.daka.kaoshi.danci.fragment.CavasFragment;
import com.zhang.daka.kaoshi.danci.fragment.DanciVerticalFragment;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

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
    TextView tv_danci_intervaltime;
    private List<DanciVerticalFragment> fragments;
    private FrameLayout fl_canci_cavas;

    @Override
    public int getLayoutId() {
        return R.layout.activity_danci;
    }

    @Override
    public void initView() {
        mViewPager = findViewById(R.id.vp_main);
        tv_danci_intervaltime = findViewById(R.id.tv_danci_intervaltime);
        initViewPager();

        fl_canci_cavas = findViewById(R.id.fl_danci_cavas);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_danci_cavas, SimpleFragment.instantiate(this, CavasFragment.class.getName())).commit();
    }

    @Override
    public void initEvent() {
        ((RadioGroup) findViewById(R.id.rg_danci_showtype)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int showType = 0;
                if (checkedId == R.id.rb_danci_onlycn) {
                    showType = 1;
                } else if (checkedId == R.id.rb_danci_onlyen) {
                    showType = 2;
                } else {
                    showType = 0;
                }
                EventBus.getDefault().post(new ShowTypeEvent(showType));
            }
        });
    }

    @Override
    public void onClick(@NotNull View v) {
        switch (v.getId()) {
            case R.id.sw_danci_fullscreen:
                EventBus.getDefault().post(new FullScreenEvent(((Switch) v).isChecked()));
                break;
            case R.id.sw_danci_cavas:
                fl_canci_cavas.setVisibility((((Switch) v).isChecked()) ? View.VISIBLE : View.GONE);
                break;
            case R.id.tv_danci_daka:
                startActivity(new Intent(mActivity, DakaMainActivity.class));
                break;
            case R.id.tv_danci_guess:
                startActivity(new Intent(mActivity, GuessWordActivity.class));
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
            case R.id.tv_danci_addition:
                tv_danci_intervaltime.setText(++DanciVerticalFragment.intervalTime + "");
                break;
            case R.id.tv_danci_substract:
                tv_danci_intervaltime.setText(--DanciVerticalFragment.intervalTime + "");
                break;
        }
    }

    @Override
    public void initData() {

    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        String alphas = "abcdefghijklmnopqrstuvwxyz";
        int position = 0;
        for (char c : alphas.toCharArray()) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position++);
            bundle.putString("alphabet", c + "");
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
