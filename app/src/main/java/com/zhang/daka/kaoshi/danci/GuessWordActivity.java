package com.zhang.daka.kaoshi.danci;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiyun_greendao.DBOpenHelper;
import com.jiyun_greendao.info.WordInfo;
import com.jiyun_greendao.info.WordInfoDao;
import com.wushiyi.mvp.base.SimpleAppCompatActivity;
import com.zhang.daka.R;
import com.zhang.daka.kaoshi.danci.adapter.AlphabetAdapter;
import com.zhang.daka.kaoshi.danci.adapter.GuessNoticeAdapter;
import com.zhang.daka.view.BesselView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static timber.log.Timber.d;

/**
 * Created by zhangyuncai on 2019/11/13.
 * 猜一猜单词
 */
public class GuessWordActivity extends SimpleAppCompatActivity {

    private RecyclerView mRecyclerView;

    private final List<String> alphabets = new ArrayList<>();
    private AlphabetAdapter mAdapter;
    private BesselView bv_guess;
    private Switch tv_guess_notice;

    /**
     * 单词1
     */
    private String alphabet1 = "a";
    /**
     * 单词2
     */
    private String alphabet2 = "a";

    /**
     * 总单词库
     */
    private List<WordInfo> wordInfos;

    /**
     * 提示单词表
     */
    private RecyclerView rv_guess_notice;
    private final List<WordInfo> notices = new ArrayList<>();
    private GuessNoticeAdapter guessNoticeAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_guessword;
    }

    @Override
    public void initView() {
        WordInfoDao dao = DBOpenHelper.getWordInfoDao();
        wordInfos = dao.queryBuilder().list();

        bv_guess = findViewById(R.id.bv_guess);
        tv_guess_notice = findViewById(R.id.tv_guess_notice);

        /**
         * 初始化字母表
         */
        mRecyclerView = findViewById(R.id.rv_guess);
        alphabets.clear();
        for (char c = 'a'; c <= 'z'; c++) {
            alphabets.add(String.valueOf(c));
        }
        mAdapter = new AlphabetAdapter(R.layout.item_guess_alphabet, alphabets);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 26));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        /**
         * 初始化字母表1
         */
        RecyclerView recyclerView1 = findViewById(R.id.rv_guess_alphabet1);
        AlphabetAdapter adapter1 = new AlphabetAdapter(R.layout.item_guess_alphabet1_adapter, alphabets);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        recyclerView1.setAdapter(adapter1);
        LinearSnapHelper snapHelper1 = new LinearSnapHelper();
        snapHelper1.attachToRecyclerView(recyclerView1);
        recyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisibleItemPosition = linearLayoutManager1.findFirstVisibleItemPosition();
                    if (alphabets.size() > firstVisibleItemPosition) {
                        alphabet1 = alphabets.get(firstVisibleItemPosition);
                    }
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        /**
         * 初始化字母表2
         */
        RecyclerView recyclerView2 = findViewById(R.id.rv_guess_alphabet2);
        AlphabetAdapter adapter2 = new AlphabetAdapter(R.layout.item_guess_alphabet1_adapter, alphabets);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView2.setAdapter(adapter2);
        LinearSnapHelper snapHelper2 = new LinearSnapHelper();
        snapHelper2.attachToRecyclerView(recyclerView2);
        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisibleItemPosition = linearLayoutManager2.findFirstVisibleItemPosition();
                    if (alphabets.size() > firstVisibleItemPosition) {
                        alphabet2 = alphabets.get(firstVisibleItemPosition);
                    }
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        /**
         * 初始化提示表
         */
        rv_guess_notice = findViewById(R.id.rv_guess_notice);
        guessNoticeAdapter = new GuessNoticeAdapter(notices);
        rv_guess_notice.setLayoutManager(new GridLayoutManager(this, 5));
        rv_guess_notice.setAdapter(guessNoticeAdapter);
        rv_guess_notice.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        rv_guess_notice.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(@NotNull View v) {
        if (v.getId() == R.id.tv_guess_clear) {
            bv_guess.clear();
        } else if (v.getId() == R.id.tv_guess_notice) {
            notice();
        }
    }

    private void notice() {
        if (tv_guess_notice.isChecked()) {
            rv_guess_notice.setVisibility(View.VISIBLE);
            if (wordInfos != null) {
                //找到前3个字母
                String word = alphabet1 + alphabet2 + mAdapter.getSelectAlphabet();
                d("----------->>>>>>>>-----------word:" + word);
                notices.clear();
                for (WordInfo wordInfo : wordInfos) {
                    if (wordInfo.getWordEn().toLowerCase().startsWith(word)) {
                        notices.add(wordInfo);
                    }
                }
                d("----------->>>>>>>>-----------notices.size:"+notices.size());
                guessNoticeAdapter.notifyDataSetChanged();

            }
        } else {
            rv_guess_notice.setVisibility(View.GONE);
        }

    }
}
