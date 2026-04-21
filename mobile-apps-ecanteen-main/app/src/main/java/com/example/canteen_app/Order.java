package com.example.canteen_app;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable{
//    Inisialisasi Variabel
    private String orderId;
    private List<Menu> items;
    private String pickupTime;
    private int totalHarga;
    private boolean isFinished;
    private String paymentMethod;

    public Order(String orderId, List<Menu> items, String pickupTime, int totalHarga, String paymentMethod) {
        this.orderId = orderId;
        this.items = items;
        this.pickupTime = pickupTime;
        this.totalHarga = totalHarga;
        this.paymentMethod = paymentMethod;

        for (Menu m : items) {
            m.setParentOrderId(orderId);
            m.setParentPickupTime(pickupTime);
        }
    }

    // Gette
    public String getOrderId() { return orderId; }
    public List<Menu> getItems() { return items; }
    public String getPickupTime() { return pickupTime; }
    public int getTotalHarga() { return totalHarga; }
    public boolean isFinished() { return isFinished; }
    public String getPaymentMethod() { return paymentMethod; }

//    Setter
    public void setFinished(boolean finished) { isFinished = finished; }
}
