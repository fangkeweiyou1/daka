package com.zhang.daka.ui.fragment

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.wushiyi.mvp.base.SimpleFragment
import com.wushiyi.mvp.dddBug
import com.zhang.daka.R
import com.zhang.daka.ui.MainActivity
import com.zhang.daka.ui.adapter.MusicListAdapter
import com.zhang.daka.utils.MusicUtils
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_musiclist.*

class MusicListFragment : SimpleFragment() {
    lateinit var mainActivity: MainActivity
    val musicList by lazy { mainActivity.musicList }
    val mRecyclerView by lazy { rv_musiclist }
    val mAdapter by lazy { MusicListAdapter(musicList) }
    override fun getLayoutId(): Int {
        mainActivity = mActivity as MainActivity
        return R.layout.fragment_musiclist
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            mainActivity.playMusic(position)
        }
    }

    override fun initView() {

        mRecyclerView.run {
            layoutManager = LinearLayoutManager(mActivity)!!
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
        }
        if (musicList.isNullOrEmpty()) {
            Observable.just(1)
                    .map {
                        return@map MusicUtils.queryMusic(mActivity)
                    }
                    .subscribeOn(Schedulers.io())
                    .subscribe(Consumer {
                        it.getOrNull(0)?.run {
                            dddBug("model:${this.toString()}")
                        }
                        musicList.addAll(it)
                        mAdapter.notifyDataSetChanged()
                    }, Consumer {
                        it.printStackTrace()
                    })
        }
    }

    override fun lazyFetchData() {
    }
}