package com.zhang.daka.ui.dialog;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wushiyi.util.ToastUtilKt;
import com.zhang.daka.R;
import com.zhang.daka.base.BaseDialog;
import com.zhang.daka.model.MusicCateModel;
import com.zhang.daka.model.MusicModel;
import com.zhang.daka.ui.MainActivity;
import com.zhang.daka.ui.adapter.MusicCateListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MusicCateListDialog extends BaseDialog {
    final MainActivity mainActivity;
    private EditText et_addmusiccate_name;
    private TextView tv_addmusiccate_no;
    private TextView tv_addmusiccate_yes;
    private RecyclerView mRecyclerView;
    private MusicCateListAdapter mAdapter;
    public MusicModel musicModel;

    public MusicCateListDialog(MainActivity mainActivity) {
        super(mainActivity);
        this.mainActivity = mainActivity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_musiccatelist;
    }

    @Override
    protected void initView() {
        et_addmusiccate_name = findViewById(R.id.et_addmusiccate_name);
        tv_addmusiccate_no = findViewById(R.id.tv_addmusiccate_no);
        tv_addmusiccate_yes = findViewById(R.id.tv_addmusiccate_yes);
        mRecyclerView = findViewById(R.id.rv_musiccatelist);

        mAdapter = new MusicCateListAdapter(mainActivity.musicCateModels);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        /**
         * 再次更新一次
         */
        mainActivity.loadMusicCates();
    }

    public void notifyList() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initEvent() {
        tv_addmusiccate_no.setOnClickListener(v -> {
            dismiss();
        });
        tv_addmusiccate_yes.setOnClickListener(v -> {
            String name = et_addmusiccate_name.getText().toString().trim();
            if (!TextUtils.isEmpty(name)) {
                MusicCateModel musicCateModel = new MusicCateModel();
                musicCateModel.setCateName(name);
                musicCateModel.saveObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            ToastUtilKt.showToast("音乐分类名添加成功");
                            mainActivity.loadMusicCates();
                        }, throwable -> {
                            throwable.printStackTrace();
                        });
            } else {
                ToastUtilKt.showToast("音乐分类名不能为空");
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MusicCateModel model = mAdapter.getItem(position);
                if (view.getId() == R.id.tv_musiccatelist_delete) {
                    model.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            ToastUtilKt.showToast("音乐分类名删除成功");
                            mainActivity.loadMusicCates();
                        }
                    });
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (musicModel != null) {
                    MusicCateModel model = mAdapter.getItem(position);
                    List<String> collect = model.getCollect();
                    if (collect == null) {
                        collect = new ArrayList<>();
                    }
                    collect.add(musicModel.getDurationSize());
                    model.setCollect(collect);
                    model.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            ToastUtilKt.showToast("音乐分类名分类成功");
                            mainActivity.loadMusicCates();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    protected int getWidthStyle() {
        return getScreenWidth(0.9f);
    }
}
