package com.example.canteen_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DetailTokoActivity extends AppCompatActivity implements CardListener {

//    Deklarasi Komponen
    private RecyclerView rvMenu;
    private TextView tvTotalProduct, tvNamaTokoHeader;
    private MenuAdapter adapter;
    private List<Menu> listDataMenu;
    private Button btnCart;

    // Variabel untuk menampung data dari Intent
    private int shopId;
    private String shopName;
    private int shopImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_toko);

        // Handle Window Insets (Agar layout tidak tertutup status bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi View berdasarkan id
        rvMenu = findViewById(R.id.rv_daftar_toko);
        tvTotalProduct = findViewById(R.id.tv_total_product);
        tvNamaTokoHeader = findViewById(R.id.tv_nama_toko); // Pastikan ID ini ada di XML
        btnCart = findViewById(R.id.btn_cart);    // Pastikan ID ini ada di XML

//        Tombol back
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        // Tangkap Data dari Intent (TokoAdapter)
        shopId = getIntent().getIntExtra("ID_TOKO", 0);
        shopName = getIntent().getStringExtra("NAMA_TOKO");
        shopImage = getIntent().getIntExtra("GAMBAR_TOKO", R.drawable.ic_user_avatar);

        // Set nama toko di header
        if (shopName != null) {
            tvNamaTokoHeader.setText(shopName);
        }

//        Manipulasi Foto
        ImageView imgHeader = findViewById(R.id.img_detail_toko);
        if (imgHeader != null) {
            imgHeader.setImageResource(shopImage);
        }

//        Update diawal reload
        updateBottomBar();

        // Siapkan Data Menu (Filter berdasarkan idToko)
        listDataMenu = new ArrayList<>();

//        Siapkan data navbar bottom
        populateMenuData();

        // Set Adapter
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuAdapter(this, listDataMenu, this);
        rvMenu.setAdapter(adapter);

        // Logika Tombol Cart
        btnCart.setOnClickListener(v -> {
            if (CartManager.getInstance().getCartList().isEmpty()) {
                Toast.makeText(this, "Keranjang masih kosong!", Toast.LENGTH_SHORT).show();
            } else {
                // Untuk sementara kita biarkan begini sampai Anda siap membuat CartActivity
                Toast.makeText(this, "Menuju Halaman Keranjang...", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(this, KeranjangActivity.class);
                 startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateMenuData();
        updateBottomBar();
    }

    private void populateMenuData() {
        List<Menu> tempMenu = new ArrayList<>();

        if (shopId == 1) {
            tempMenu.add(new Menu(101, shopId, shopName, "Nasi Goreng", 10000, R.drawable.nasi_goreng, shopImage));
            tempMenu.add(new Menu(102, shopId, shopName, "Mie Rebus", 7000, R.drawable.mie_rebus, shopImage));
            tempMenu.add(new Menu(103, shopId, shopName, "Mie Goreng", 7000, R.drawable.mie_goreng, shopImage));
            tempMenu.add(new Menu(104, shopId, shopName, "Pop Ice", 5000, R.drawable.pop_ice, shopImage));
        } else if (shopId == 2) {
            tempMenu.add(new Menu(201, shopId, shopName, "Ayam Geprek", 12000, R.drawable.ayam_geprek, shopImage));
            tempMenu.add(new Menu(202, shopId, shopName, "Ayam Katsu", 12000, R.drawable.ayam_katsu, shopImage));
        } else if (shopId == 3) {
            tempMenu.add(new Menu(301, shopId, shopName, "Bakso", 12000, R.drawable.bakso, shopImage));
            tempMenu.add(new Menu(302, shopId, shopName, "Mie Ayam", 12000, R.drawable.mie_ayam, shopImage));
            tempMenu.add(new Menu(303, shopId, shopName, "Teh sosro", 6000, R.drawable.teh_sosro, shopImage));
        } else if (shopId == 4) {
            tempMenu.add(new Menu(401, shopId, shopName, "Seblak", 8000, R.drawable.seblak, shopImage));
            tempMenu.add(new Menu(402, shopId, shopName, "Bakso Aci", 8000, R.drawable.baso_aci, shopImage));
            tempMenu.add(new Menu(403, shopId, shopName, "Le Mineral", 6000, R.drawable.le_mineral, shopImage));
        }

        listDataMenu.clear();
        List<Menu> currentCart = CartManager.getInstance().getCartList();

        // Sinkronisasi data lokal dengan CartManager agar Qty tidak reset ke 0
        for (Menu mRaw : tempMenu) {
            Menu foundInCart = null;
            for (Menu mCart : currentCart) {
                if (mRaw.getProductId() == mCart.getProductId()) {
                    foundInCart = mCart;
                    break;
                }
            }

            if (foundInCart != null) {
//                Pakai objek yang ada di CartManager
                listDataMenu.add(foundInCart);
            } else {
//                Pakai objek yang ada di Menu
                listDataMenu.add(mRaw);
            }
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
    // Fungsi pembantu untuk update tampilan jumlah produk di bar bawah
    private void updateBottomBar() {
        int totalQty = 0;
        for (Menu item : CartManager.getInstance().getCartList()) {
            totalQty += item.getQty();
        }

        // Tampilkan: "X Produk"
        tvTotalProduct.setText(totalQty + " Produk");

        // Opsional: Sembunyikan bar jika keranjang kosong
        if (totalQty == 0) {
            findViewById(R.id.layout_checkout_bar).setVisibility(View.GONE);
        } else {
            findViewById(R.id.layout_checkout_bar).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTotalChanged(int newTotal) {
        // Ketika ada perubahan +/- di adapter, panggil update bar bawah
        updateBottomBar();
    }
}

