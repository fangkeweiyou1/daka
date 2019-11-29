package com.jiyun_greendao.info;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by zhangyuncai on 2019/11/9.
 */
@Entity
public class WordInfo implements Serializable {
    static final long serialVersionUID = 42L;
    @Id(autoincrement = true)
    private Long id;
    private String alpha;//头字母
    private String wordCn;//中文
    private String wordEn;//英文
    private String type;//类型
    @Generated(hash = 574812343)
    public WordInfo(Long id, String alpha, String wordCn, String wordEn,
            String type) {
        this.id = id;
        this.alpha = alpha;
        this.wordCn = wordCn;
        this.wordEn = wordEn;
        this.type = type;
    }
    @Generated(hash = 112235395)
    public WordInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAlpha() {
        return this.alpha;
    }
    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }
    public String getWordCn() {
        return this.wordCn;
    }
    public void setWordCn(String wordCn) {
        this.wordCn = wordCn;
    }
    public String getWordEn() {
        return this.wordEn;
    }
    public void setWordEn(String wordEn) {
        this.wordEn = wordEn;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
