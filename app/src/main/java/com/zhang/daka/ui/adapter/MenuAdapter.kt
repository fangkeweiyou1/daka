package com.zhang.daka.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhang.daka.R
import com.zhang.daka.model.MenuModel

class MenuAdapter(list:ArrayList<MenuModel>):
        BaseQuickAdapter<MenuModel,BaseViewHolder>(R.layout.item_menu_adapter,list){
    override fun convert(helper: BaseViewHolder, item: MenuModel) {
        helper.setImageResource(R.id.iv_menu_icon,item.iconRes)
        helper.setText(R.id.tv_menu_name,item.menuName)
    }

}