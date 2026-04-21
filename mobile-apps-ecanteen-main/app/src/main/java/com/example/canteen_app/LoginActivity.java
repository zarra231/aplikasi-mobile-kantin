package com.example.canteen_app;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btnPembeli, btnPenjual, btnMasuk;
    private TextView tvDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // INIT VIEW
        btnPembeli = findViewById(R.id.btn_pembeli);
        btnPenjual = findViewById(R.id.btn_penjual);
        tvDaftar   = findViewById(R.id.tv_daftar);
        btnMasuk = findViewById(R.id.btn_masuk);

        // underline "Daftar"
        tvDaftar.setPaintFlags(tvDaftar.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // DEFAULT: Pembeli aktif
        btnPembeli.setSelected(true);
        btnPenjual.setSelected(false);

        // Klik Pembeli
        btnPembeli.setOnClickListener(v -> {
            btnPembeli.setSelected(true);
            btnPenjual.setSelected(false);
        });

        // Klik Penjual
        btnPenjual.setOnClickListener(v -> {
            btnPenjual.setSelected(true);
            btnPembeli.setSelected(false);
        });

        // Klik Daftar → Register
        tvDaftar.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, Register.class));
        });

//        Redirect login ke beranda
        btnMasuk.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, BerandaActivity.class));
            finish();
        });
    }
}
