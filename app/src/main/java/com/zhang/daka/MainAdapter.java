package com.zhang.daka;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wushiyi.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.System.out;

/**
 * Created by zhangyuncai on 2019/10/12.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private static final String TAG = "MainAdapter";


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_adapter2, viewGroup, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
        //日期
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
        String date = simpleDateFormat.format(calendar.getTime());
        date = date + DataHelper.getDayText(i) + "日";
        mainViewHolder.tv_main_date.setText(date);
        //上午
        Glide.with(mainViewHolder.context)
                .load(DataHelper.getMeinvImage())
                .into(mainViewHolder.iv_img_am);
        //下午
        Glide.with(mainViewHolder.context)
                .load(DataHelper.getMeinvImage())
                .into(mainViewHolder.iv_img_pm);
    }

    @Override
    public int getItemCount() {
        Calendar calendar = Calendar.getInstance();
        int monthOfDay = DataHelper.getMonthOfDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        Log.d(TAG, "本月天数:" + monthOfDay);
        return monthOfDay;
    }

    class MainViewHolder extends RecyclerView.ViewHolder {

        public final Context context;
        public final ImageView iv_img_am;//上午图片
        public final ImageView iv_img_pm;//下午图片
        private final TextView tv_main_date;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            iv_img_am = itemView.findViewById(R.id.iv_img_am);
            iv_img_pm = itemView.findViewById(R.id.iv_img_pm);
            tv_main_date = itemView.findViewById(R.id.tv_main_date);
        }
    }
}
