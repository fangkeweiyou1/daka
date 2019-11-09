package com.jiyun_greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangyuncai on 2019/10/17.
 */
@Entity
public class WaitDetailModelInfo {
    @Id(autoincrement = true)
    private long id;
    private String waitDetailModelJson;
    private String orderWaitModelPackId;
    @Generated(hash = 1323162104)
    public WaitDetailModelInfo(long id, String waitDetailModelJson,
            String orderWaitModelPackId) {
        this.id = id;
        this.waitDetailModelJson = waitDetailModelJson;
        this.orderWaitModelPackId = orderWaitModelPackId;
    }
    @Generated(hash = 1381871335)
    public WaitDetailModelInfo() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getWaitDetailModelJson() {
        return this.waitDetailModelJson;
    }
    public void setWaitDetailModelJson(String waitDetailModelJson) {
        this.waitDetailModelJson = waitDetailModelJson;
    }
    public String getOrderWaitModelPackId() {
        return this.orderWaitModelPackId;
    }
    public void setOrderWaitModelPackId(String orderWaitModelPackId) {
        this.orderWaitModelPackId = orderWaitModelPackId;
    }
}
