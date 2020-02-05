package com.zhang.daka.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhang.daka.R
import com.zhang.daka.model.MusicModel

class MusicListAdapter(list:ArrayList<MusicModel>):
BaseQuickAdapter<MusicModel,BaseViewHolder>(R.layout.item_musiclist_adapter,list){
    override fun convert(helper: BaseViewHolder, item: MusicModel) {
        helper.setText(R.id.tv_musiclist_author,item.artist)
        helper.setText(R.id.tv_musiclist_name,item.musicName)
    }
}