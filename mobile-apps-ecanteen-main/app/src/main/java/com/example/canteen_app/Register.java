package com.example.canteen_app;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {

    private Button btnPembeli, btnPenjual, btnDaftar;
    private LinearLayout layoutPenjual;
    private TextView tvMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // INIT VIEW
        btnPembeli = findViewById(R.id.btn_pembeli);
        btnPenjual = findViewById(R.id.btn_penjual);
        btnDaftar = findViewById(R.id.btn_daftar);
        layoutPenjual = findViewById(R.id.layout_penjual);
        tvMasuk = findViewById(R.id.tv_masuk);

        // underline text
        tvMasuk.setPaintFlags(tvMasuk.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // default pembeli
        btnPembeli.setSelected(true);
        layoutPenjual.setVisibility(View.GONE);

        // klik pembeli
        btnPembeli.setOnClickListener(v -> {
            btnPembeli.setSelected(true);
            btnPenjual.setSelected(false);
            layoutPenjual.setVisibility(View.GONE);
        });

        // klik penjual
        btnPenjual.setOnClickListener(v -> {
            btnPenjual.setSelected(true);
            btnPembeli.setSelected(false);
            layoutPenjual.setVisibility(View.VISIBLE);
        });

        // 🔹 MASUK SEKARANG → LOGIN
        tvMasuk.setOnClickListener(v -> {
            startActivity(new Intent(Register.this, LoginActivity.class));
            finish();
        });

        // DAFTAR → LOGIN
        btnDaftar.setOnClickListener(v -> {
            startActivity(new Intent(Register.this, LoginActivity.class));
            finish();
        });
    }
}
