package com.zhang.daka.kaoshi.danci;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wushiyi.mvp.base.SimpleAppCompatActivity;
import com.zhang.daka.R;
import com.zhang.daka.kaoshi.danci.adapter.AlphabetAdapter;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by zhangyuncai on 2019/11/13.
 * 猜一猜单词
 */
public class GuessWordActivity extends SimpleAppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView tv_guess_resultword;

    private final List<String> alphabets = new ArrayList<>();
    private AlphabetAdapter mAdapter;
    private String resultWord = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_guessword;
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.rv_guess);
        tv_guess_resultword = findViewById(R.id.tv_guess_resultword);
        alphabets.clear();
        for (char c = 'a'; c <= 'z'; c++) {
            alphabets.add(String.valueOf(c));
        }
        alphabets.add(".");
        alphabets.add(".");
        alphabets.add(".");
        alphabets.add("delete");
        mAdapter = new AlphabetAdapter(alphabets);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void additionResultWord(String alphabet) {
        resultWord += alphabet;
        tv_guess_resultword.setText(resultWord);
    }

    private void substractResultWord() {
        if (resultWord.length() <= 1) {
            resultWord = "";
        } else {
            resultWord = resultWord.substring(0, resultWord.length() - 1);
        }
        tv_guess_resultword.setText(resultWord);
    }

    @Override
    public void initEvent() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String alphabet = mAdapter.getItem(position);
                Timber.d("----------->>>>>>>>-----------alphabet:" + alphabet);
                switch (alphabet) {
                    case ".":
                        break;
                    case "delete":
                        substractResultWord();
                        break;
                    default:
                        additionResultWord(alphabet);
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {

    }


}
