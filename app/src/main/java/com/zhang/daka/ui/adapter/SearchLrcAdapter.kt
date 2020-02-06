package com.zhang.daka.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhang.daka.R
import com.zhang.daka.kugou.Candidates

class SearchLrcAdapter(list:ArrayList<Candidates>):
        BaseQuickAdapter<Candidates,BaseViewHolder>(R.layout.item_searchlrc_adapter,list){
    override fun convert(helper: BaseViewHolder, item: Candidates) {
        val tv_searchlrc_musicname=helper.getView<TextView>(R.id.tv_searchlrc_musicname)
        val tv_searchlrc_author=helper.getView<TextView>(R.id.tv_searchlrc_author)
        val tv_searchlrc_download=helper.getView<TextView>(R.id.tv_searchlrc_download)
        val tv_searchlrc_apply=helper.getView<TextView>(R.id.tv_searchlrc_apply)
        val tv_searchlrc_position=helper.getView<TextView>(R.id.tv_searchlrc_position)

        tv_searchlrc_position.text="${helper.layoutPosition}"
        tv_searchlrc_musicname.text=item.song
        tv_searchlrc_author.text=item.singer

        helper.addOnClickListener(R.id.tv_searchlrc_download)
        helper.addOnClickListener(R.id.tv_searchlrc_apply)
    }

}