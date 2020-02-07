package com.zhang.daka.popup;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.TextView;

import com.zhang.daka.R;

import java.util.List;


/**
 * Created by zhangyuncai on 2019/8/15.
 * ListPopupWindow 的使用方法
 */
public class ListPopupWindowHelper<T extends PopupItemTextInterface> {
    public static ListPopupWindowHelper newInstance() {
        return new ListPopupWindowHelper();
    }

    public ListPopupWindow newListPopupWindow(Context context, final View anchorView, List<T> list) {
        final PopupListAdapter<T> listAdapter = new PopupListAdapter<T>(context, R.layout.item_listpopupwindow_adapter, list);
        final ListPopupWindow listPopupWindow = getListPopupWindow(context, listAdapter
                , anchorView, ListPopupWindow.WRAP_CONTENT);
        if (anchorView instanceof TextView && list.size() > 0) {
            TextView anchorView2 = (TextView) anchorView;
            anchorView2.setText(list.get(0).getText());
        }
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listPopupWindow.dismiss();
                listAdapter.setSelectPosition(position);
                if (anchorView instanceof TextView) {
                    TextView anchorView2 = (TextView) anchorView;
                    anchorView2.setText(listAdapter.getSelectItemText());
                }
                anchorView.setTag(list.get(position));
            }
        });
        anchorView.setOnClickListener(v -> {
            showPopupWindow(listPopupWindow);
        });
        return listPopupWindow;
    }

    public static ListPopupWindow getListPopupWindow(Context context, ArrayAdapter adapter, View anchorView, int height) {
        ListPopupWindow listPopupWindow = new ListPopupWindow(context);
        listPopupWindow.setWidth(ListPopupWindow.WRAP_CONTENT);
//        listPopupWindow.setHeight(DisplayUtils.dip2px(context, 150));
        listPopupWindow.setHeight(height);
        listPopupWindow.setAnchorView(anchorView);
        listPopupWindow.setModal(true);
        listPopupWindow.setAdapter(adapter);
        return listPopupWindow;
    }

    public static void showPopupWindow(ListPopupWindow listPopupWindow) {
        listPopupWindow.show();
        ListView listView = listPopupWindow.getListView();
        if (listView != null) {
            //设置分割线
            listView.setDividerHeight(0);
        }
    }
}
