package com.zhang.daka.ui.fragment

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.widget.AdapterView
import android.widget.ListPopupWindow
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.wushiyi.mvp.base.SimpleFragment
import com.wushiyi.mvp.dddBug
import com.wushiyi.util.showToast
import com.zhang.daka.R
import com.zhang.daka.model.MusicActionModel
import com.zhang.daka.model.MusicModel
import com.zhang.daka.popup.ListPopupWindowHelper
import com.zhang.daka.popup.PopupListAdapter
import com.zhang.daka.ui.MainActivity
import com.zhang.daka.ui.adapter.MusicListAdapter
import com.zhang.daka.ui.dialog.MusicCateListDialog
import com.zhang.daka.utils.MusicUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_musiclist.*
import java.util.*

class MusicListFragment : SimpleFragment() {
    lateinit var mainActivity: MainActivity
    val musicList by lazy { mainActivity.musicList }
    val mRecyclerView by lazy { rv_musiclist }
    val mAdapter by lazy { MusicListAdapter(mainActivity, musicList) }
    val linearLayoutManager by lazy { LinearLayoutManager(mActivity)!! }

    /**
     * popup
     */
    val musicActions = ArrayList<MusicActionModel>()
    private lateinit var musicActionAdapter: PopupListAdapter<MusicActionModel>
    private lateinit var musicActionWindow: ListPopupWindow
    override fun getLayoutId(): Int {
        mainActivity = mActivity as MainActivity
        return R.layout.fragment_musiclist
    }


    override fun initEvent() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            mainActivity.playMusic(position)
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.iv_musiclist_more) {
                initMusicCateWindow(mAdapter.getItem(position) as MusicModel)
                ListPopupWindowHelper.showPopupWindow(musicActionWindow)
            }
        }
    }

    override fun initView() {
        musicActions.add(MusicActionModel("添加至文件夹"))
        musicActions.add(MusicActionModel("删除至文件夹"))
        musicActions.add(MusicActionModel("删除"))
        mRecyclerView.run {
            layoutManager = linearLayoutManager
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
        }


        if (mainActivity.allMusicList.isNullOrEmpty()) {
            Observable.just(1)
                    .map {
                        return@map MusicUtils.queryMusic(mActivity)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer {
                        it.getOrNull(0)?.run {
                            dddBug("model:${this.toString()}")
                        }
                        mainActivity.allMusicList.addAll(it)
                        mainActivity.notifyMusicCate()
                    }, Consumer {
                        it.printStackTrace()
                    })
        }
    }

    private fun initMusicCateWindow(musicModel: MusicModel) {
        musicActionAdapter = PopupListAdapter(mActivity, R.layout.item_listpopupwindow_adapter, musicActions)
        musicActionWindow = ListPopupWindowHelper.getListPopupWindow(mActivity, musicActionAdapter, view_musiclist_attach, ListPopupWindow.WRAP_CONTENT)
        musicActionWindow.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            musicActionWindow.dismiss()
            if (TextUtils.equals(musicActions.get(position).text, "添加至文件夹")) {
                val dialog = MusicCateListDialog(mainActivity)
                dialog.musicModel = musicModel
                dialog.show()
            } else if (TextUtils.equals(musicActions.get(position).text, "删除至文件夹")) {
                val currentMusicCateModel = mainActivity.currentMusicCateModel
                if (!currentMusicCateModel.objectId.isNullOrEmpty()) {
                    val collect = currentMusicCateModel.collect
                    if (!collect.isNullOrEmpty()) {
                        collect.remove(musicModel.durationSize)
                        currentMusicCateModel.collect = collect
                        currentMusicCateModel.update(object : UpdateListener() {
                            override fun done(p0: BmobException?) {
                                showToast("删除成功!")
                                mainActivity.loadMusicCates()
                                musicList.remove(musicModel)
                                mAdapter.notifyDataSetChanged()
                            }

                        })
                    }
                }
            }
        })
    }

    override fun lazyFetchData() {
    }

    /**
     * 更新分类
     */
    fun notifyMusicCate() {
        mAdapter.notifyDataSetChanged()
    }

    fun notifyCurrent() {
        mAdapter.notifyDataSetChanged()
        linearLayoutManager.scrollToPositionWithOffset(mainActivity.currentPosition - 5, 0)
    }
}