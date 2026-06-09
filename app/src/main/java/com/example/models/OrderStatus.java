package com.example.models;

public enum OrderStatus {
    ALL("tất cả các loại hóa đơn"),
    COMPLETED("các hóa đơn đã hoàn tất hành trình"),
    NOT_YET_PAYMENT("hóa đơn chưa thanh toán"),
    GOING_LOGISTIC("hóa đơn đang xử lí logistc"),
    CUSTOMER_COMPLAINT("hóa đơn bị khách hàng phàn nàn");
    private String description;
    private OrderStatus(String description)
    {
        this.description=description;
    }
    public String getDescription()
    {
        return this.description;
    }
}
