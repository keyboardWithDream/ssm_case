package com.ssm.domain;

import com.ssm.utils.DateUtils;

import java.util.Date;

/**
 * 订单实体类
 * @Author Harlan
 * @Date 2020/9/22
 */
public class Orders {

    private String id;
    private String orderNum;
    private Date orderTime;
    private Integer peopleCount;
    private String oderDesc;
    private Integer payType;
    private String payTypeStr;
    private Integer orderStatus;
    private String orderStatusStr;
    private Product product;
    private Member member;
    private Traveller traveller;
    private String oderTimeStr;

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusStr() {
        if (orderStatus != null){
            if (orderStatus == 0){
                orderStatusStr = "未支付";
            }
            if (orderStatus == 1){
                orderStatusStr = "已支付";
            }
        }
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayTypeStr() {
        if (payType != null){
            if (payType == 0){
                payTypeStr = "支付宝";
            }
            if (payType == 1){
                payTypeStr = "微信";
            }
            if (payType == 2){
                payTypeStr = "其他";
            }
        }
        return payTypeStr;
    }

    public void setPayTypeStr(String payTypeStr) {
        this.payTypeStr = payTypeStr;
    }

    public String getOderTimeStr() {
        if (orderTime != null){
            oderTimeStr = DateUtils.date2String(orderTime, "yyyy-MM-dd HH:mm");
        }
        return oderTimeStr;
    }

    public void setOderTimeStr(String oderTimeStr) {
        this.oderTimeStr = oderTimeStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getOderDesc() {
        return oderDesc;
    }

    public void setOderDesc(String oderDesc) {
        this.oderDesc = oderDesc;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Traveller getTraveller() {
        return traveller;
    }

    public void setTraveller(Traveller traveller) {
        this.traveller = traveller;
    }
}
