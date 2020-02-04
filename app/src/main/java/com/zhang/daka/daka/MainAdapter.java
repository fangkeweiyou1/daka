package com.zhang.daka.daka;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhang.daka.R;
import com.zhang.daka.utils.DataHelper;
import com.zhang.daka.utils.WFileUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    public void onBindViewHolder(@NonNull final MainViewHolder mainViewHolder, int i) {
        final Context context = mainViewHolder.context;
        //打卡操作
        mainViewHolder.bt_main_daka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
//                if (camera >= 0) {
//                    context.startActivity(new Intent(context, Camera4Activity.class));
////                    context.startActivity(new Intent(context, Camera5Activity.class));
//                } else {
//                    ToastUtilKt.showToast("请赋予拍照权限");
//                }
            }
        });
        //日期
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat_show = new SimpleDateFormat("yyyy年MM月");
        SimpleDateFormat simpleDateFormat_parse = new SimpleDateFormat("yyyy-MM-");
        //显示当天日期
        String showCurrentDate = simpleDateFormat_show.format(calendar.getTime());
        String parseDate = simpleDateFormat_parse.format(calendar.getTime());
        showCurrentDate = showCurrentDate + DataHelper.getDayText(i) + "日";
        parseDate = parseDate + DataHelper.getDayText(i) + "-";
        mainViewHolder.tv_main_date.setText(showCurrentDate);
        //上午
        String amImagePath = WFileUtil.getCacheImagePath(context, parseDate + "am");
        final File amFile = new File(amImagePath);
        if (amFile.exists()) {
            Glide.with(context)
                    .load(amFile)
                    .skipMemoryCache(true)
                    .into(mainViewHolder.iv_img_am);
            mainViewHolder.iv_img_am.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new PreviewImageDialog(context, amFile).show();
                }
            });
        } else {
            Glide.with(context)
                    .load(DataHelper.getMeinvImage())
                    .into(mainViewHolder.iv_img_am);
            mainViewHolder.iv_img_am.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        //下午
        String pmImagePath = WFileUtil.getCacheImagePath(context, parseDate + "pm");
        final File pamFile = new File(pmImagePath);
        if (pamFile.exists()) {
            Glide.with(context)
                    .load(pamFile)
                    .skipMemoryCache(true)
                    .into(mainViewHolder.iv_img_pm);
            mainViewHolder.iv_img_pm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new PreviewImageDialog(context, pamFile).show();
                }
            });
        } else {
            mainViewHolder.iv_img_pm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            Glide.with(context)
                    .load(DataHelper.getMeinvImage())
                    .into(mainViewHolder.iv_img_pm);
        }
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
        private final Button bt_main_daka;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            iv_img_am = itemView.findViewById(R.id.iv_img_am);
            iv_img_pm = itemView.findViewById(R.id.iv_img_pm);
            tv_main_date = itemView.findViewById(R.id.tv_main_date);
            bt_main_daka = itemView.findViewById(R.id.bt_main_daka);
        }
    }
}
