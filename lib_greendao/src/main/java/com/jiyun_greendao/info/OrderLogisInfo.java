package com.jiyun_greendao.info;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangyuncai on 2019/10/22.
 * 物流信息
 */
@Entity
public class OrderLogisInfo {
    @Id(autoincrement = true)
    private long id;
    private String logisJson;
    private String orderNo;
    @Generated(hash = 1545991961)
    public OrderLogisInfo(long id, String logisJson, String orderNo) {
        this.id = id;
        this.logisJson = logisJson;
        this.orderNo = orderNo;
    }
    @Generated(hash = 1640306463)
    public OrderLogisInfo() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getLogisJson() {
        return this.logisJson;
    }
    public void setLogisJson(String logisJson) {
        this.logisJson = logisJson;
    }
    public String getOrderNo() {
        return this.orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
