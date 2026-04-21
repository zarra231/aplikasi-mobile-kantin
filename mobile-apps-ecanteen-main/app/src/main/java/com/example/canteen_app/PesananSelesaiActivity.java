package com.example.canteen_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PesananSelesaiActivity extends AppCompatActivity {
private LinearLayout tabPesananAktif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pesanan_selesai);

        tabPesananAktif = findViewById(R.id.tabAktif);

        tabPesananAktif.setOnClickListener(v -> {
            Intent intent = new Intent(PesananSelesaiActivity.this, PesananAktifActivity.class);
            startActivity(intent);
        });
    }
}