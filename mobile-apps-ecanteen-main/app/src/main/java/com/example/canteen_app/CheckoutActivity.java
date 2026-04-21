package com.example.canteen_app;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
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
import java.util.Calendar;

public class CheckoutActivity extends AppCompatActivity {
//    Inisialisasi Komponen
private RecyclerView rvCheckout;
    private CheckoutAdapter adapter;
    private TextView tvTotalBayar, tvPilihJam;
    private Button btnKonfirmasi;
    private LinearLayout btnSetWaktu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Setup data
        initView();
        setupRecyclerView();
        setupLogic();
    }

//    Tangkap Komponen Berdasarkan ID
private void initView() {
    rvCheckout = findViewById(R.id.rvCheckoutItems);
    tvTotalBayar = findViewById(R.id.tvTotalBayar);
    tvPilihJam = findViewById(R.id.tvPilihJam);
    btnSetWaktu = findViewById(R.id.btnSetWaktu);
    btnKonfirmasi = findViewById(R.id.btnKonfirmasi);

    // Tombol Back
    findViewById(R.id.btnBackCheckout).setOnClickListener(v -> finish());
}

    private void setupRecyclerView() {
        // Ambil data dari CartManager
        List<Menu> listPesanan = CartManager.getInstance().getCartList();

        // Setup Adapter
        adapter = new CheckoutAdapter(this, listPesanan);
        rvCheckout.setLayoutManager(new LinearLayoutManager(this));
        rvCheckout.setAdapter(adapter);

        // Set Total Harga
        int total = CartManager.getInstance().getGlobalTotal();
        tvTotalBayar.setText("Rp " + String.format("%,d", total));
    }

    private void setupLogic() {
        // 1. Klik area box untuk pilih jam
        btnSetWaktu.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hourOfDay, selectedMinute) -> {
                        String time = String.format("%02d:%02d WIB", hourOfDay, selectedMinute);
                        tvPilihJam.setText(time);
                    }, hour, minute, true);
            timePickerDialog.show();
        });

//        Konfirmasi jam
        btnKonfirmasi.setOnClickListener(v -> {
            String jamAmbil = tvPilihJam.getText().toString();

            // Validasi jam
            if (jamAmbil.contains(">")) {
                Toast.makeText(this, "Tentukan jam pengambilan terlebih dahulu!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Buat Objek Order Baru
            processOrder(jamAmbil);
        });
    }

    private void processOrder(String jam) {
        // Pindah ke halaman metode pembayaran
        Intent intent = new Intent(CheckoutActivity.this, metodepembayaran.class);
        intent.putExtra("PICKUP_TIME", jam);
        startActivity(intent);
    }

}