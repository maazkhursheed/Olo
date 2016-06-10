package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by attribe on 6/9/16.
 */
public class Orders {
    private String deviceId;
    private String name;
    private String phone;
    private String address;
    private Integer orderTotal;
    private Integer orderTime;
    private List<OrderDetail> orderDetail = new ArrayList<OrderDetail>();

    public Orders(String deviceId, String name,
                  String phone, Integer orderTotal, String address,
                  Integer orderTime, List<OrderDetail> orderDetail) {
        this.deviceId = deviceId;
        this.name = name;
        this.phone = phone;
        this.orderTotal = orderTotal;
        this.address = address;
        this.orderTime = orderTime;
        this.orderDetail = orderDetail;
    }





}
