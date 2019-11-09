package com.jiyun_greendao.info;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangyuncai on 2019/11/9.
 */
@Entity
public class WordInfo {
    @Id(autoincrement = true)
    private long id;
    private String alpha;//头字母
    private String word;//单词
    private String type;//类型
    @Generated(hash = 1861564821)
    public WordInfo(long id, String alpha, String word, String type) {
        this.id = id;
        this.alpha = alpha;
        this.word = word;
        this.type = type;
    }
    @Generated(hash = 2101857135)
    public WordInfo() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getAlpha() {
        return this.alpha;
    }
    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }
    public String getWord() {
        return this.word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
