package com.example.canteen_app;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

//    Inisialisasi singleton
    private static CartManager instance;
    private List<Menu> cartList = new ArrayList<>();
    private CartManager() {}
    private List<Order> historyList = new ArrayList<>();

//    Tambahkan pesanan baru
    public void addOrderToHistory(Order order) {
        historyList.add(0, order);
    }

    // Ambil semua daftar riwayat
    public List<Order> getHistoryList() {
        return historyList;
    }

//    Mengambil instance dari singleton
    public static CartManager getInstance() {
        if (instance == null) instance = new CartManager();
        return instance;
    }

//    Menambahkan barang
    public void addOrUpdateItem(Menu menu) {
        if (menu == null) return;

        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getProductId() == menu.getProductId()) {
                if (menu.getQty() <= 0) {
                    cartList.remove(i);
                } else {
                    cartList.set(i, menu);
                }
                return;
            }
        }
        if (menu.getQty() > 0) cartList.add(menu);
    }

public void removeItem(int productId) {
    for (int i = 0; i < cartList.size(); i++) {
        if (cartList.get(i).getProductId() == productId) {
            cartList.remove(i);
            break;
        }
    }
}

//    Hapus Ketika berhasil checkout
public void clearCart() {
    cartList.clear();
}

//    Mengambil data keranjang
    public List<Menu> getCartList() { return cartList; }

//    Mengambil total harga
    public int getGlobalTotal() {
        int total = 0;
        for (Menu item : cartList) {
            total += (item.getProductPrice() * item.getQty());
        }
        return total;
    }
}