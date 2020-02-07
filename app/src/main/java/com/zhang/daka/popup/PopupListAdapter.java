package com.zhang.daka.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zhang.daka.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangyuncai on 2019/3/15.
 */
public class PopupListAdapter<T extends PopupItemTextInterface> extends ArrayAdapter<T> {
    private List<T> mDatas;
    private Context context;
    private int selectPosition = -1;//默认没有任何选中
    private int resource;

    public List<T> getDatas() {
        return mDatas;
    }

    public void setNewDatas(List<T> datas) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        this.mDatas = datas;
        selectPosition = -1;
        notifyDataSetChanged();
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        if (mDatas == null || selectPosition >= mDatas.size()) {
            this.selectPosition = -1;
        }
    }

    public T getSelectItem() {
        if (mDatas != null && mDatas.size() > selectPosition && selectPosition >= 0) {
            return mDatas.get(selectPosition);
        }
        return null;
    }

    public String getSelectItemText() {
        T selectItem = getSelectItem();
        if (selectItem != null) {
            return selectItem.getText();
        }
        return "";
    }

    public PopupListAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
        this.mDatas = objects;
        this.context = context;
        this.resource = resource;
        selectPosition = -1;
    }

    public PopupListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.mDatas = new ArrayList<>();
        this.context = context;
        this.resource = resource;
        selectPosition = -1;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Nullable
    @Override
    public T getItem(int position) {
        if (mDatas != null && mDatas.size() > position) {
            return mDatas.get(position);
        }
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = View.inflate(context, resource, null);
        TextView textView = view.findViewById(R.id.tv_popupitem_text);
        if (mDatas != null && mDatas.size() > position) {
            textView.setText(mDatas.get(position).getText());
        }
        return view;
    }
}
