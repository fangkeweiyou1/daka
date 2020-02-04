package com.zhang.daka.model

import java.io.Serializable

class MenuModel:Serializable {
    var iconRes=0
    var menuName=""

    constructor(iconRes: Int, menuName: String) {
        this.iconRes = iconRes
        this.menuName = menuName
    }
}