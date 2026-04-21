package com.example.canteen_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StatusCodActivity extends AppCompatActivity {

    private ImageView backCodAktif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_status_cod);

        backCodAktif     = findViewById(R.id.backCod);

        backCodAktif.setOnClickListener(v -> {
            Intent intent = new Intent(StatusCodActivity.this, PesananAktifActivity.class);
            startActivity(intent);
        });
    }
}