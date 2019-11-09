package com.zhang.daka.kaoshi.danci;

import android.support.annotation.Nullable;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiyun_greendao.DBOpenHelper;
import com.jiyun_greendao.info.WordInfo;
import com.jiyun_greendao.info.WordInfoDao;
import com.zhang.daka.R;
import com.zhang.daka.model.WordModel;

import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/9.
 */
public class AddDanciAdapter extends BaseQuickAdapter<WordModel, BaseViewHolder> {
    public AddDanciAdapter(@Nullable List<WordModel> data) {
        super(R.layout.item_adddanci_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WordModel item) {
        //内容
        helper.setText(R.id.et_adddanci_content, item.content);
        //首字母
        helper.setText(R.id.et_adddanci_alpha, item.alpha);
        //中文
        helper.setText(R.id.et_adddanci_wordcn, item.wordCn);
        //英文
        helper.setText(R.id.et_adddanci_worden, item.wordEn);
        //类型
        helper.setText(R.id.et_adddanci_type, item.type);
        //是否可以保存
        CheckBox checkBox = helper.getView(R.id.cb_adddanci_check);
        checkBox.setChecked(item.check);
        if (hasWordByDao(item.wordEn)) {
            checkBox.setChecked(false);
            item.check = false;
        }
        checkBox.setOnClickListener(v -> {
            item.check = checkBox.isChecked();
        });

    }

    private boolean hasWordByDao(String wordEn) {
        WordInfoDao wordInfoDao = DBOpenHelper.getWordInfoDao();
        List<WordInfo> list = wordInfoDao.queryBuilder().where(WordInfoDao.Properties.WordEn.eq(wordEn)).build().list();
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }
}
