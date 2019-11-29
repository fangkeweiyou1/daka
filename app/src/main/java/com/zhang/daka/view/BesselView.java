package com.zhang.daka.view;

/**
 * Created by zhangyuncai on 2019/11/14.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyuncai on 2019/9/26.
 * 一阶贝塞尔曲线
 */
public class BesselView extends View {

    private final List<PathBean> pathBeans = new ArrayList<>();

    public void clear() {
        pathBeans.clear();
        invalidate();
    }

    class PathBean {
        float x;
        float y;
        boolean isStart;

        public PathBean(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public PathBean(float x, float y, boolean isStart) {
            this.x = x;
            this.y = y;
            this.isStart = isStart;
        }
    }

    public BesselView(Context context) {
        super(context);
    }

    public BesselView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BesselView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            pathBeans.add(new PathBean(event.getX(), event.getY()));
            invalidate();
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pathBeans.add(new PathBean(event.getX(), event.getY(), true));
            invalidate();
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Path path = new Path();
        for (int i = 0; i < pathBeans.size(); i++) {
            PathBean pathBean = pathBeans.get(i);
            if (pathBean.isStart) {
                path.moveTo(pathBean.x, pathBean.y);
            }
            path.lineTo(pathBean.x, pathBean.y);
        }
//        path.close();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        canvas.drawPath(path, paint);


    }
}