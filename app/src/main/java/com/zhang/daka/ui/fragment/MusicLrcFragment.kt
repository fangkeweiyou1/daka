package com.zhang.daka.ui.fragment

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.wushiyi.mvp.base.SimpleFragment
import com.wushiyi.util.DisplayUtil
import com.wushiyi.util.showToast
import com.zhang.daka.R
import com.zhang.daka.config.Constans
import com.zhang.daka.kugou.Candidates
import com.zhang.daka.kugou.KuGouApiService
import com.zhang.daka.net.NetworkModule
import com.zhang.daka.ui.MainActivity
import com.zhang.daka.ui.adapter.SearchLrcAdapter
import com.zhang.daka.utils.LyricUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_musiclrc.*

class MusicLrcFragment : SimpleFragment() {
    val kuGouApiService by lazy { NetworkModule.getRetrofit(Constans.BASE_KUGOU_URL).create(KuGouApiService::class.java); }
    val mainActivity by lazy { mActivity as MainActivity }
    val searchLrcList = arrayListOf<Candidates>()
    val searchLrcAdapter by lazy { SearchLrcAdapter(searchLrcList) }
    val searchLrcRecyclerView by lazy { rv_searchlrc_list }
    override fun getLayoutId(): Int {
        return R.layout.fragment_musiclrc
    }

    override fun initEvent() {
        tv_musiclrc_search.setOnClickListener {
            if (searchLrcRecyclerView.visibility == View.GONE) {
                searchLyric()
            } else {
                searchLrcRecyclerView.visibility = View.GONE
            }
        }
        searchLrcAdapter.setOnItemChildClickListener { adapter, view, position ->
            val candidates = searchLrcAdapter.getItem(position) as Candidates
            kuGouApiService.getRawLyric(candidates.id, candidates.accesskey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer {
                        showLyric(LyricUtil.decryptBASE64(it.content), true)
                    }, Consumer {
                        it.printStackTrace()
                        showToast("没有下载到歌词")
                    })
//            showLyric(candidates.soundname)
        }
    }

    fun searchLyric() {
        val musicModel = mainActivity.musicList.get(mainActivity.currentPosition)
        kuGouApiService.searchLyric(musicModel.musicName, "${musicModel.duration}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    it.candidates?.run {
                        searchLrcList.clear()
                        searchLrcList.addAll(this)
                    }
                    if (searchLrcList.isEmpty()) {
                        showToast("没有搜索到歌词")
                    } else {
                        searchLrcRecyclerView.visibility = View.VISIBLE
                        searchLrcAdapter.notifyDataSetChanged()
                    }
                }, Consumer {
                    it.printStackTrace()
                    showToast("没有搜索到歌词")
                })
    }

    override fun initView() {
        searchLrcRecyclerView.run {
            layoutManager = LinearLayoutManager(mActivity)!!
            adapter = searchLrcAdapter
            addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
        }
    }

    /**
     *显示歌词
     */
    fun showLyric(lyric: String?, init: Boolean) {
        searchLrcRecyclerView.visibility = View.GONE
        if (init) {
            //初始化歌词配置
            lyricShow?.setTextSize(DisplayUtil.sp2px(14f))
//            lyricShow?.setHighLightTextColor(Color.BLACK)
            lyricShow?.setTouchable(true)
        }
        lyricShow?.setLyricContent(lyric)
    }

    fun setCurrentTimeMillis(current: Long = 0) {
        lyricShow?.setCurrentTimeMillis(current)
    }

    override fun lazyFetchData() {
    }
}