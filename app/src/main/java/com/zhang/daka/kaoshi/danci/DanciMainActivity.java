package com.zhang.daka.kaoshi.danci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiyun_greendao.DBOpenHelper;
import com.jiyun_greendao.info.WordInfo;
import com.jiyun_greendao.info.WordInfoDao;
import com.wushiyi.mvp.MvpExtendsKt;
import com.wushiyi.mvp.base.BaseFragmentPagerAdapter;
import com.zhang.daka.DakaMainActivity;
import com.zhang.daka.R;
import com.zhang.daka.daka.adapter.MenuAdapter;
import com.zhang.daka.event.IntervalEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/8.
 */
public class DanciMainActivity extends AppCompatActivity {

    private Activity mActivity;

    private final List<String> menus = new ArrayList<>();
    private MenuAdapter menuAdapter = new MenuAdapter(menus);
    private RecyclerView menuRecyclerView;
    private DividerItemDecoration dividerItemDecoration;


    ViewPager mViewPager;
    private List<DanciVerticalFragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danci);
        mActivity = this;
        menuRecyclerView = findViewById(R.id.rv_main_menu);
        mViewPager = findViewById(R.id.vp_main);

        initMenu();

        initViewPager();

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

    /**
     * 初始化左边菜单栏
     */
    private void initMenu() {
        menus.clear();
        menus.add("打卡");
        menus.add("新增单词");
        menus.add("轮询");

        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (dividerItemDecoration == null) {
            dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            menuRecyclerView.addItemDecoration(dividerItemDecoration);
        }
        menuRecyclerView.setAdapter(menuAdapter);
        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (menuAdapter.getItem(position)) {
                    case "打卡":
                        startActivity(new Intent(mActivity, DakaMainActivity.class));
                        break;
                    case "新增单词":
                        startActivity(new Intent(mActivity, AddDanciActivity.class));
                        break;
                    case "轮询":
                        EventBus.getDefault().post(new IntervalEvent(mViewPager.getCurrentItem()));
                        break;

                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
