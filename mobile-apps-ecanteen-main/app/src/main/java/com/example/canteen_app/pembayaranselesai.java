package com.example.canteen_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class pembayaranselesai extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaranselesai);

//        Tombol back
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goToBeranda();
            }
        });

//        Tangkap data
        String id = getIntent().getStringExtra("ID");
        int total = getIntent().getIntExtra("TOTAL", 0);
        String metode = getIntent().getStringExtra("METODE");
        String jam = getIntent().getStringExtra("JAM");

//        Menangambil komponen berdasarkan ID
        TextView tvMetode = findViewById(R.id.tvSelesaiMetode);
        TextView tvTotal = findViewById(R.id.tvSelesaiTotal);
        TextView tvWaktu = findViewById(R.id.tvSelesaiWaktu);
        TextView tvId = findViewById(R.id.tvSelesaiId);
        Button btnBeranda = findViewById(R.id.btnKembaliBeranda);
        Button btnLihat = findViewById(R.id.btnLihatPesananSelesai);

//        Ganti teks
        tvId.setText("#" + id);
        tvMetode.setText(metode);
        tvTotal.setText("Rp " + String.format("%,d", total));
        tvWaktu.setText(jam);

//        Tombol Kembali ke beranda
        btnBeranda.setOnClickListener(v -> {
            Intent intent = new Intent(this, BerandaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

//        Tombol Lihat Pesanan
        btnLihat.setOnClickListener(v -> {
            Toast.makeText(this, "Terima Kasih Telah Memesan", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, PesananAktifActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
    // Fungsi helper untuk navigasi ke Beranda
    private void goToBeranda() {
        Intent intent = new Intent(this, BerandaActivity.class);
        // Menghapus semua riwayat aktivitas (Checkout, Metode Bayar, Qris) dari memory
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
