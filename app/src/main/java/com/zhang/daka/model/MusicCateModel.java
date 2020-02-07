package com.zhang.daka.model;

import com.zhang.daka.popup.PopupItemTextInterface;

import cn.bmob.v3.BmobObject;

/**
 * 音乐分类
 */
public class MusicCateModel extends BmobObject implements PopupItemTextInterface {
    //分类名
    private String cateName;
    private int cateId;



    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    @Override
    public String getText() {
        return cateName;
    }
}
