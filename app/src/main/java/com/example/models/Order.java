package com.example.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Order implements Serializable {
    private String orderId;
    private String customerId;
    private String id; // Employee ID
    private Date orderDate;

    private OrderStatus orderStatus;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    static SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";

    public Order() {
    }

    public Order(String orderId, String customerId, String id, Date orderDate, OrderStatus orderStatus) {
        this(orderId,  customerId,  id,  orderDate);
        this.orderStatus = orderStatus;
    }

    public Order(String orderId, String customerId, String id, Date orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.id = id;
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        String amount=orderId+"\t"+sdf.format(orderDate)+"\t"+DataWarehouse.sumOfMoneyForOrder(this);
        return amount;

    }
}
