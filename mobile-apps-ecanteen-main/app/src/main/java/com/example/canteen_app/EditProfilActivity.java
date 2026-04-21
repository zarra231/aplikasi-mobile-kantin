package com.example.canteen_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditProfilActivity extends AppCompatActivity {
    private ImageButton backEditProfil;
    private TextView cancelEditProfil;
    private Button simpanEditProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backEditProfil      = findViewById(R.id.imgEdtPflArrowLeft);
        cancelEditProfil    = findViewById(R.id.tvEdtPflCancel);
        simpanEditProfil    = findViewById(R.id.btnSimpanEditProfil);

        backEditProfil.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfilActivity.this, ProfilActivity.class);
            startActivity(intent);
        });

        cancelEditProfil.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfilActivity.this, ProfilActivity.class);
            startActivity(intent);
        });

        simpanEditProfil.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfilActivity.this, ProfilActivity.class);
            startActivity(intent);
        });
        };
    }