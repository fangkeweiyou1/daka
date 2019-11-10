package com.zhang.daka.kaoshi.danci;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.jiyun_greendao.DBOpenHelper;
import com.jiyun_greendao.info.WordInfo;
import com.jiyun_greendao.info.WordInfoDao;
import com.wushiyi.mvp.base.SimpleAppCompatActivity;
import com.zhang.daka.R;
import com.zhang.daka.model.WordModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by zhangyuncai on 2019/11/9.
 */
public class AddDanciActivity extends SimpleAppCompatActivity {


    private final List<WordModel> wordModels = new ArrayList<>();
    private AddDanciAdapter mAdapter = new AddDanciAdapter(wordModels);
    private RecyclerView mRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_adddanci;
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.rv_adddanci);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        findViewById(R.id.tv_adddanci_import).setOnClickListener(v -> {
            importWords();
        });

        List<WordInfo> list = DBOpenHelper.getWordInfoDao().queryBuilder().build().list();
        if (list != null) {
            for (WordInfo info : list) {
                WordModel wordModel = new WordModel();
                wordModel.wordEn = info.getWordEn();
                wordModel.wordCn = info.getWordCn();
                wordModel.alpha = info.getAlpha();
                wordModel.type = info.getType();
                wordModel.content = wordModel.wordEn + "      " + wordModel.wordCn;
                wordModels.add(wordModel);
            }
            mAdapter.notifyDataSetChanged();
        }

    }

    private void importWords() {

        new Thread() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = getAssets().open("danci.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String lineContent = null;
                    while ((lineContent = reader.readLine()) != null) {
                        addWordModel(lineContent);
//                        if (wordModels.size() > 10) {
//                            break;
//                        }
                    }
                    inputStream.close();

                    runOnUiThread(() -> {
                        mAdapter.notifyDataSetChanged();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 新增WordModel
     *
     * @param content
     */
    private void addWordModel(String content) {
        if (!TextUtils.isEmpty(content)) {
            //去掉首尾空格
            content = content.trim();
            //分开多个空格的字符串
            String[] strings = content.split("\\s+");
            if (strings != null && strings.length >= 2) {
                String wordCn = strings[1];
                String wordEn = strings[0];
                String alpha = wordEn.charAt(0) + "";

                WordModel model = new WordModel();
                model.content = content;
                model.alpha = alpha;
                model.wordCn = wordCn;
                model.wordEn = wordEn;
                wordModels.add(model);
                addWordInfo(model);
            }
        }
    }

    private void addWordInfo(WordModel model) {

        WordInfoDao dao = DBOpenHelper.getWordInfoDao();
        List<WordInfo> list = dao.queryBuilder().where(WordInfoDao.Properties.WordEn.eq(model.wordEn)).build().list();
        if (list == null || list.size() == 0) {//不存在
            WordInfo info = new WordInfo();
            info.setAlpha(model.alpha);
            info.setWordCn(model.wordCn);
            info.setWordEn(model.wordEn);
            DBOpenHelper.getWordInfoDao().insert(info);
            Timber.d("----------->>>>>>>>-----------已添加:" + model.wordEn);
        } else {//已存在
            WordInfo info = list.get(0);
            info.setAlpha(model.alpha);
            info.setWordCn(model.wordCn);
            info.setWordEn(model.wordEn);
            DBOpenHelper.getWordInfoDao().insertOrReplace(info);
            Timber.d("----------->>>>>>>>-----------已添加并替换:" + model.wordEn);
        }

    }

    @Override
    public void initEvent() {
    }

    @Override
    public void initData() {

    }


}
