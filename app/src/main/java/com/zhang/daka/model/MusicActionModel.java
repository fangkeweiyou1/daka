package com.zhang.daka.model;

import com.zhang.daka.popup.PopupItemTextInterface;

public class MusicActionModel implements PopupItemTextInterface {
    public MusicActionModel(String actionName) {
        this.actionName = actionName;
    }

    private String actionName;
    @Override
    public String getText() {
        return actionName;
    }
}
