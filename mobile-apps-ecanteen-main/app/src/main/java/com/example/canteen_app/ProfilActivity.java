package com.example.canteen_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class ProfilActivity extends AppCompatActivity {
    private LinearLayout menuEditProfil, btnLogout, menuTentangAplikasi;
    private ImageView btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Tombol back
        ImageView btnBack = findViewById(R.id.btnBackProfil);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilActivity.this, BerandaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        menuEditProfil = findViewById(R.id.menu_edit_profile);
        menuEditProfil.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilActivity.this, EditProfilActivity.class);
            startActivity(intent);
        });

        // MENU TENTANG APLIKASI
        menuTentangAplikasi = findViewById(R.id.menuTentangApk);
        menuTentangAplikasi.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilActivity.this, TentangAplikasiActivity.class);
            startActivity(intent);
        });

        // BUTTON LOGOUT
        btnLogout = findViewById(R.id.btn_logout_profil);
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilActivity.this, LogoutActivity.class);
            startActivity(intent);

        });
    }
}