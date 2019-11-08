package com.zhang.daka.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wushiyi.util.DisplayUtil;
import com.wushiyi.util.WCallBack;
import com.zhang.daka.R;


/**
 * Created by gxj on 2017/9/4.
 * dialog 基类
 */

public abstract class BaseDialog extends Dialog implements View.OnClickListener {
    public BaseDialog(Context context) {
        super(context, R.style.MMTheme);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WCallBack callBack;

    public <T> void setCallBack(WCallBack<T> callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowsStyle();
        setContentView(getLayoutId());
        initView();
        initEvent();
    }

    protected abstract int getLayoutId();

    /**
     * 初始化View布局
     */
    protected abstract void initView();

    /**
     * 初始化控件监听事件
     */
    protected abstract void initEvent();

    protected void setWindowsStyle() {
        //去掉dialog的标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* 设置与屏幕等宽 */
        Window window = getWindow();

        /*
         * dialog 默认的样式@android:style/Theme.Dialog 对应的style 有pading属性 就能够水平占满
         */
        assert window != null;
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = getWidthStyle();
        lp.height = getHeightStyle();
        lp.gravity = getGravity();
        window.setAttributes(lp);
        int animations = getAnimations();
        if (animations > 0) {
            window.setWindowAnimations(animations);
        }
    }

    /**
     * 获取 dialog 显示在界面的位置样式
     *
     * @return 位置样式
     */
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    /**
     * 获取 dialog 显示在界面的动画样式
     *
     * @return 位置样式
     */
    protected int getAnimations() {
        return 0;
    }

    /**
     * 子类复写 去实现 dialog 宽度的选择
     *
     * @return dialog宽度
     */
    protected int getWidthStyle() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    /**
     * 子类复写 去实现 dialog 高度的选择
     *
     * @return dialog高度
     */
    protected int getHeightStyle() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }


    @Override
    public void onClick(View v) {

    }

    /**
     * 获取字符串资源
     *
     * @param s
     * @return
     */
    protected String getString(@StringRes int s) {
        return getContext().getString(s);
    }

    //获取屏幕宽度的百分比
    protected int getScreenWidth(@FloatRange(from = 0.1f, to = 1f) float percent) {
        return (int) (DisplayUtil.INSTANCE.getScreenWidthAndHight()[0] * percent);
    }
}
