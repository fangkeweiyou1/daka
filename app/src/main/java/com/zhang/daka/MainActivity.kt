//package com.zhang.daka
//
//import android.app.AlarmManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.content.res.AssetFileDescriptor
//import android.media.MediaPlayer
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.LinearSnapHelper
//import android.support.v7.widget.RecyclerView
//import android.view.*
//import android.widget.ImageView
//import android.widget.RelativeLayout
//import android.widget.TextView
//import com.bumptech.glide.Glide
//import java.lang.Exception
//import java.text.SimpleDateFormat
//import java.util.*
//
//class MainActivity : AppCompatActivity() {
//
//    val mRecyclerView by lazy { findViewById<RecyclerView>(R.id.rv_main) }
//    val mDatas = mutableListOf<DakaModel>()
//    lateinit var inflater: LayoutInflater
//    lateinit var currentTime: String
//    val yyyyMMdd = SimpleDateFormat("yyyy-MM-dd")
//    val yyyyMMddHHmmss = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//    val HHmmss = SimpleDateFormat("HH:mm:ss")
//    val linearLayoutManager = LinearLayoutManager(this@MainActivity)
//
//    val surfaceView by lazy { findViewById<SurfaceView>(R.id.surface_main) }
//    val mediaPlayer = MediaPlayer()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        dddBug("onCreate")
//        sContext = applicationContext
//        SharedPreferencesUtils.context = sContext
//        setContentView(R.layout.activity_main)
//        inflater = LayoutInflater.from(this)
//
//        mDatas.add(DakaModel(R.drawable.meinv1, "2019-08-01"))
//        mDatas.add(DakaModel(R.drawable.meinv2, "2019-08-02"))
//        mDatas.add(DakaModel(R.drawable.meinv3, "2019-08-03"))
//        mDatas.add(DakaModel(R.drawable.meinv4, "2019-08-04"))
//        mDatas.add(DakaModel(R.drawable.meinv5, "2019-08-05"))
//        mDatas.add(DakaModel(R.drawable.meinv6, "2019-08-06"))
//        mDatas.add(DakaModel(R.drawable.meinv7, "2019-08-07"))
//        mDatas.add(DakaModel(R.drawable.meinv8, "2019-08-08"))
//        mDatas.add(DakaModel(R.drawable.meinv9, "2019-08-09"))
//        mDatas.add(DakaModel(R.drawable.meinv10, "2019-08-10"))
//        mDatas.add(DakaModel(R.drawable.meinv11, "2019-08-11"))
//        mDatas.add(DakaModel(R.drawable.meinv12, "2019-08-12"))
//        mDatas.add(DakaModel(R.drawable.meinv13, "2019-08-13"))
//        mDatas.add(DakaModel(R.drawable.meinv14, "2019-08-14"))
//        mDatas.add(DakaModel(R.drawable.meinv15, "2019-08-15"))
//        mDatas.add(DakaModel(R.drawable.meinv1, "2019-08-16"))
//        mDatas.add(DakaModel(R.drawable.meinv2, "2019-08-17"))
//        mDatas.add(DakaModel(R.drawable.meinv3, "2019-08-18"))
//        mDatas.add(DakaModel(R.drawable.meinv4, "2019-08-19"))
//        mDatas.add(DakaModel(R.drawable.meinv5, "2019-08-20"))
//        mDatas.add(DakaModel(R.drawable.meinv6, "2019-08-21"))
//        mDatas.add(DakaModel(R.drawable.meinv7, "2019-08-22"))
//        mDatas.add(DakaModel(R.drawable.meinv8, "2019-08-23"))
//        mDatas.add(DakaModel(R.drawable.meinv9, "2019-08-24"))
//        mDatas.add(DakaModel(R.drawable.meinv10, "2019-08-25"))
//        mDatas.add(DakaModel(R.drawable.meinv11, "2019-08-26"))
//        mDatas.add(DakaModel(R.drawable.meinv12, "2019-08-27"))
//        mDatas.add(DakaModel(R.drawable.meinv13, "2019-08-28"))
//        mDatas.add(DakaModel(R.drawable.meinv14, "2019-08-29"))
//        mDatas.add(DakaModel(R.drawable.meinv15, "2019-08-30"))
//        mDatas.add(DakaModel(R.drawable.meinv15, "2019-08-31"))
//
//        if (false) {
//            try {
//                val time = mDatas[20].time
//                SharedPreferencesUtils.saveLong("$time $AM", yyyyMMddHHmmss.parse("$time 09:02:00").time)
//                for (index in 0..19) {
//                    val time = mDatas[index].time
//                    SharedPreferencesUtils.saveLong("$time $AM", yyyyMMddHHmmss.parse("$time 09:02:00").time)
//                    SharedPreferencesUtils.saveLong("$time $PM", yyyyMMddHHmmss.parse("$time 18:32:00").time)
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//        if (false) {
//            try {
//                for (model in mDatas) {
//                    val time = model.time
//                    val calendar = Calendar.getInstance()
//                    calendar.time = yyyyMMdd.parse(time)
//                    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
////                    dddBug("dayOfWeek:$dayOfWeek,time:$time")
//                    if (dayOfWeek == 1) {
//                        SharedPreferencesUtils.saveLong("$time $AM", yyyyMMddHHmmss.parse("$time 09:02:00").time)
//                        SharedPreferencesUtils.saveLong("$time $PM", yyyyMMddHHmmss.parse("$time 18:32:00").time)
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//
//        initView()
////        initEvent()
//
//    }
//
//    fun initView() {
//        val mAdapter = MainAdapter()
//
//        mRecyclerView.run {
//            layoutManager = linearLayoutManager
//            adapter = mAdapter
//        }
//        val pagerSnapHelper = LinearSnapHelper()
//        pagerSnapHelper.attachToRecyclerView(mRecyclerView)
//
//
////        initPlayer()
//    }
//
//    /**
//     * 初始化播放器
//     */
//    fun initPlayer() {
////        val openFd = assets.openFd("linyuner.mp4")
//        val openFd = assets.openFd("message.wav")
//        mediaPlayer.reset()
//        mediaPlayer.setDataSource(openFd)
//        mediaPlayer.prepareAsync()
////        val holder = surfaceView.holder
////        holder.addCallback(MyCallBack())
//        mediaPlayer.setOnPreparedListener {
//            mediaPlayer.start()
//            mediaPlayer.isLooping = false
//        }
//    }
//
//
//    fun initEvent() {
//        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
//
//        val calendar = Calendar.getInstance()!!
//        // 这里时区需要设置一下，不然会有8个小时的时间差
//        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"))
//        calendar.timeInMillis = System.currentTimeMillis()
//        calendar.add(Calendar.SECOND, 10)
//
//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,5000, pendingIntent)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        //设置滑动到当天日期
//        val currentDate = yyyyMMdd.format(Date())
//        for ((index, date) in mDatas.withIndex()) {
//            if (currentDate == date.time) {
//                dddBug(currentDate)
//                linearLayoutManager.scrollToPosition(index)
//                break
//            }
//        }
//
//        //设置林允儿背景MV
//    }
//
//    override fun onBackPressed() {
//        moveTaskToBack(true)
//    }
//
//    inner class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
//        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainViewHolder {
//            val view = inflater.inflate(R.layout.item_main_adapter, p0, false)
//            val viewHolder = MainViewHolder(view)
////            viewHolder.setIsRecyclable(false)
//            return viewHolder
//        }
//
//        override fun getItemCount(): Int {
//            return mDatas.size
//        }
//
//        override fun onBindViewHolder(p0: MainViewHolder, p1: Int) {
//            //加载背景图片
//            Glide.with(this@MainActivity)
//                    .load(mDatas[p1].img)
//                    .into(p0.iv_img)
//            //设置当前时间
//            p0.tv_current_ime.text = mDatas[p1].time
//
//            //获取上午打卡时间
//            val amDakaLong = SharedPreferencesUtils.loadLong("${mDatas[p1].time} $AM")
//            //获取下午打卡时间
//            val pmDakaLong = SharedPreferencesUtils.loadLong("${mDatas[p1].time} $PM")
//
//            //判断上午是否打卡
//            if (amDakaLong > 0) {//打卡
//                val amText = HHmmss.format(amDakaLong)
//                p0.iv_zhiwen_am.visibility = View.GONE
//                p0.tv_am.visibility = View.VISIBLE
//                p0.tv_am.text = amText
//            } else {//未打卡
//                p0.tv_am.visibility = View.GONE
//                p0.iv_zhiwen_am.visibility = View.VISIBLE
//            }
//            //判断下午是否打开
//            if (pmDakaLong > 0) {//打卡
//                val pmText = HHmmss.format(pmDakaLong)
//                p0.iv_zhiwen_pm.visibility = View.GONE
//                p0.tv_pm.visibility = View.VISIBLE
//                p0.tv_pm.text = pmText
//            } else {//未打卡
//                p0.iv_zhiwen_pm.visibility = View.VISIBLE
//                p0.tv_pm.visibility = View.GONE
//            }
//
//            //长按打卡
//            p0.iv_zhiwen_am.setOnLongClickListener {
//
//                //限制最后打卡时间
//                var amMax: Long = 0
//                //限制最前打卡时间
//                var amMin: Long = 0
//
//                try {
//                    //最后打卡时间文本
//                    val amMaxText = "${mDatas[p1].time} $amMaxTag"
//                    //最前打卡时间文本
//                    val amMinText = "${mDatas[p1].time} $amMinTag"
//                    amMax = yyyyMMddHHmmss.parse(amMaxText).time
//                    amMin = yyyyMMddHHmmss.parse(amMinText).time
//                    //当前时间数字
//                    val currentTime = Date().time
//                    //当前打卡时间在范围内
//                    if (currentTime in amMin..amMax) {
//                        //播放铃声
//                        initPlayer()
//                        toastMsg("打卡成功!")
//                        //存档
//                        SharedPreferencesUtils.saveLong("${mDatas[p1].time} $AM", currentTime)
//                        //更新
//                        notifyItemChanged(p1)
//                    } else {
//                        toastMsg("不在时间范围内!")
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//
//                true
//            }
//            //长按打卡
//            p0.iv_zhiwen_pm.setOnLongClickListener {
//                //限制最后打卡时间
//                var pmMax: Long = 0
//                //限制最前打卡时间
//                var pmMin: Long = 0
//
//                try {
//                    //最后打卡时间文本
//                    val pmMaxText = "${mDatas[p1].time} $pmMaxTag"
//                    //最前打卡时间文本
//                    val pmMinText = "${mDatas[p1].time} $pmMinTag"
//                    pmMax = yyyyMMddHHmmss.parse(pmMaxText).time
//                    pmMin = yyyyMMddHHmmss.parse(pmMinText).time
//                    //当前时间数字
//                    val currentTime = Date().time
//                    //当前打卡时间在范围内
//                    if (currentTime in pmMin..pmMax) {
//                        //播放铃声
//                        initPlayer()
//                        toastMsg("打卡成功!")
//
//                        //存档
//                        SharedPreferencesUtils.saveLong("${mDatas[p1].time} $PM", currentTime)
//                        //更新
//                        notifyItemChanged(p1)
//                    } else {
//                        toastMsg("不在时间范围内!")
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//
//                true
//            }
//
//
//            //判断是否是星期天
//            val time = mDatas[p1].time
//            val calendar = Calendar.getInstance()
//            calendar.time = yyyyMMdd.parse(time)
//            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
//            p0.rl_zhoumo.visibility = if (dayOfWeek == 1) View.VISIBLE else View.GONE
//        }
//
//        inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//            //当前时间
//            val tv_current_ime = view.findViewById<TextView>(R.id.tv_current_ime)
//            //上午打卡时间
//            val tv_am = view.findViewById<TextView>(R.id.tv_am)
//            //下午打卡时间
//            val tv_pm = view.findViewById<TextView>(R.id.tv_pm)
//            //美女背景
//            val iv_img = view.findViewById<ImageView>(R.id.iv_img)
//            //上午打卡指纹
//            val iv_zhiwen_am = view.findViewById<ImageView>(R.id.iv_zhiwen_am)
//            //下午打卡指纹
//            val iv_zhiwen_pm = view.findViewById<ImageView>(R.id.iv_zhiwen_pm)
//            //周末
//            val rl_zhoumo = view.findViewById<RelativeLayout>(R.id.rl_zhoumo)
//
//            init {
//
//            }
//
//        }
//    }
//
//    open fun skiptest(v: View) {
//    }
//
//    data class DakaModel(val img: Int, val time: String)
//}
