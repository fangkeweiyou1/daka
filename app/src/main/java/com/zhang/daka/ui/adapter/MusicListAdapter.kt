package com.zhang.daka.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhang.daka.R
import com.zhang.daka.model.MusicModel
import com.zhang.daka.ui.MainActivity

class MusicListAdapter(val mainActivity: MainActivity,list:ArrayList<MusicModel>):
BaseQuickAdapter<MusicModel,BaseViewHolder>(R.layout.item_musiclist_adapter,list){
    override fun convert(helper: BaseViewHolder, item: MusicModel) {
        val position=helper.layoutPosition
        helper.setText(R.id.tv_musiclist_author,item.artist)
        helper.setText(R.id.tv_musiclist_name,item.musicName)
        val tv_musiclist_author=helper.getView<TextView>(R.id.tv_musiclist_author)
        val tv_musiclist_name=helper.getView<TextView>(R.id.tv_musiclist_name)
        tv_musiclist_author.isSelected=position==mainActivity.currentPosition
        tv_musiclist_name.isSelected=position==mainActivity.currentPosition

        helper.addOnClickListener(R.id.iv_musiclist_more)
    }
}