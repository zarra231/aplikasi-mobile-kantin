package com.example.canteen_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TokoAdapter extends RecyclerView.Adapter<TokoAdapter.TokoViewHolder> {
    private Context context;
    private List<Toko> listToko;

    public TokoAdapter(Context context, List<Toko> listToko) {
        this.context = context;
        this.listToko = listToko;
    }

    @NonNull
    @Override
    public TokoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_toko, parent, false);
        return new TokoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TokoViewHolder holder, int position) {
        Toko toko = listToko.get(position);

        holder.tvNama.setText(toko.getShop_name());
        holder.tvLokasi.setText(toko.getShop_location());
        holder.imgToko.setImageResource(toko.getShop_src());

//        Persiapan untuk pindah ke daftar menu
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailTokoActivity.class);

//            Mengirim ID, nama dan gambar toko
            intent.putExtra("NAMA_TOKO", toko.getShop_name());
            intent.putExtra("ID_TOKO", toko.getShop_id());
            intent.putExtra("GAMBAR_TOKO", toko.getShop_src());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listToko.size();
    }

    public static class TokoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgToko;
        TextView tvNama, tvLokasi;

        public TokoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgToko = itemView.findViewById(R.id.img_toko);
            tvNama = itemView.findViewById(R.id.tv_nama_toko);
            tvLokasi = itemView.findViewById(R.id.tv_lokasi_toko);
        }
    }
}
