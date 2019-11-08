package com.zhang.daka.model;

import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/8.
 */
public class TranslateModel {

    /**
     * from : en
     * to : zh
     * trans_result : [{"src":"hello","dst":"你好"}]
     */

    private String from;
    private String to;
    private String error_code;//错误码
    private List<TransResultBean> trans_result;

    public String getFrom() {
        return from;
    }


    public String getTo() {
        return to;
    }


    public List<TransResultBean> getTrans_result() {
        return trans_result;
    }


    public static class TransResultBean {
        /**
         * src : hello
         * dst : 你好
         */

        private String src;
        private String dst;

        public String getSrc() {
            return src;
        }


        public String getDst() {
            return dst;
        }

    }

    @Override
    public String toString() {
        return "TranslateModel{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", error_code='" + error_code + '\'' +
                ", trans_result=" + trans_result +
                '}';
    }
}
