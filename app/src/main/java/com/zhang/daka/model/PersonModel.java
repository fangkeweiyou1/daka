package com.zhang.daka.model;

import cn.bmob.v3.BmobObject;

public class PersonModel extends BmobObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
