package com.zhang.daka.kaoshi.huibian;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wushiyi.mvp.base.SimpleAppCompatActivity;
import com.wushiyi.util.InputMethodUtils;
import com.wushiyi.util.widget.BaseTextWatcher;
import com.zhang.daka.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Cre|ated by zhangyuncai on 2019/11/21.
 * 汇编考试
 */
public class HuibianActivity extends SimpleAppCompatActivity {
    private final List<HuibianModel> allList = HuibianModel.getHuibianModels();
    private final HuibianAdapter allAdapter = new HuibianAdapter(allList);
    private RecyclerView allRecyclerView;
    private int currentPosition = 0;

    private final List<HuibianModel> searchList = new ArrayList<>();
    private final HuibianAdapter searchAdapter = new HuibianAdapter(searchList);
    private RecyclerView searchRecyclerView;


    private TextView tv_search_content;
    private EditText et_huibian_search;
    private Disposable subscribe;
    private RecyclerView rv_huibian_code;
    private HuibianCodeAdapter codeAdapter;
    private final List<String> codeList = new ArrayList<>();
    private String search = "";

    @Override
    public int getLayoutId() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return R.layout.activity_huibian;
    }

    @Override
    public void initView() {
        allRecyclerView = findViewById(R.id.rv_huibian_all);
        searchRecyclerView = findViewById(R.id.rv_huibian_search);
        et_huibian_search = findViewById(R.id.et_huibian_search);
        tv_search_content = findViewById(R.id.tv_search_content);
        rv_huibian_code = findViewById(R.id.rv_huibian_code);
        DrawerLayout drawerlayout = findViewById(R.id.drawerlayout);
        drawerlayout.setScrimColor(Color.TRANSPARENT);

        allRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allRecyclerView.setAdapter(allAdapter);
        allRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchRecyclerView.setAdapter(searchAdapter);
        searchRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        for (int i = 0; i < 10; i++) {
            codeList.add("" + i);
        }
        for (char j = 'a'; j <= 'z'; j++) {
            codeList.add("" + j);
        }
        codeList.add(".");
        codeList.add("、");
        codeList.add(",");
        codeList.add("clear");
        codeList.add("clear");
        codeList.add("clear");
        codeList.add(" ");
        codeList.add(" ");
        codeList.add(" ");
        codeList.add(" ");
        codeList.add(" ");
        codeList.add(" ");
        codeAdapter = new HuibianCodeAdapter(codeList);
        rv_huibian_code.setLayoutManager(new GridLayoutManager(this, 6));
        rv_huibian_code.setAdapter(codeAdapter);
        rv_huibian_code.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        rv_huibian_code.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }


    @Override
    public void initEvent() {
        subscribe = Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    currentPosition++;
                    if (currentPosition >= allList.size()) {
                        currentPosition = 0;
                    }
                    if (allList.size() > currentPosition) {
                        allRecyclerView.scrollToPosition(currentPosition);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });

        et_huibian_search.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(@Nullable CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                search(s + "");
            }
        });
        codeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String item = codeAdapter.getItem(position);
                if (TextUtils.equals(item, "clear")) {
                    search = "";
                } else {
                    search += item;
                }
                search(search);
            }
        });

    }

    private void search(String search) {
        searchList.clear();
        tv_search_content.setText(search);

        try {
            if (!TextUtils.isEmpty(search)) {
                for (HuibianModel model : allList) {
                    if (model.question.toLowerCase().contains(search.toLowerCase())) {
                        searchList.add(model);
                    } else if (model.anwser.toLowerCase().contains(search.toLowerCase())) {
                        searchList.add(model);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(@NotNull View v) {
        switch (v.getId()) {
            case R.id.tv_huibian_searchtag:
                int visibility = et_huibian_search.getVisibility();
                if (visibility == View.GONE) {
                    et_huibian_search.setVisibility(View.VISIBLE);
                    et_huibian_search.setFocusable(true);
                    InputMethodUtils.INSTANCE.showSoftInput(this, et_huibian_search);
                } else {
                    et_huibian_search.setVisibility(View.GONE);
                    InputMethodUtils.INSTANCE.closeKeyBoard(this);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        subscribe.dispose();
        super.onDestroy();
    }
}
