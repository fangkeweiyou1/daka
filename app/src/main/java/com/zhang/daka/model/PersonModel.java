package com.zhang.daka.model;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class PersonModel extends BmobObject {
    private String name;
    private List<String> texts;

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
