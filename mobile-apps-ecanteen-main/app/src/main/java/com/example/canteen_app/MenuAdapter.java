package com.example.canteen_app;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

//    Inisialisasi
    private Context context;
    private List<Menu> menuList;
    private CardListener listener; // Interface yang kamu buat tadi

    public MenuAdapter(Context context, List<Menu> menuList, CardListener listener) {
        this.context = context;
        this.menuList = menuList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu = menuList.get(position);

//        Get product
        holder.tvNama.setText(menu.getProductName());
        holder.tvHarga.setText("Rp " + String.format("%,d", menu.getProductPrice()).replace(',', '.'));
        holder.imgMenu.setImageResource(menu.getProductPath());
        holder.tvQty.setText(String.valueOf(menu.getQty()));

//        Tampilan awal qty
        if (menu.getQty() > 0) {
            holder.btnMinus.setVisibility(View.VISIBLE);
            holder.tvQty.setVisibility(View.VISIBLE);
        } else {
            holder.btnMinus.setVisibility(View.GONE);
            holder.tvQty.setVisibility(View.GONE);
        }

        // Tombol TAMBAH (+)
        holder.btnPlus.setOnClickListener(v -> {
            int currentPos = holder.getBindingAdapterPosition();
                    if (currentPos != RecyclerView.NO_POSITION) {
                        Menu item = menuList.get(currentPos);
                        if (item.getQty() == 0) {
                            showNoteDialog(item);
                        }
                        item.setQty(item.getQty() + 1);
                        CartManager.getInstance().addOrUpdateItem(item);
                        notifyItemChanged(currentPos);
                        listener.onTotalChanged(CartManager.getInstance().getGlobalTotal());
                    }
        });

        // Tombol KURANG (-)
        holder.btnMinus.setOnClickListener(v -> {
            int currentPos = holder.getBindingAdapterPosition();
            if (currentPos != RecyclerView.NO_POSITION) {
                Menu item = menuList.get(currentPos);
                if (item.getQty() > 0) {
                    item.setQty(item.getQty() - 1);
                    CartManager.getInstance().addOrUpdateItem(item);

                    // Refresh item agar UI update
                    notifyItemChanged(currentPos);
                    listener.onTotalChanged(CartManager.getInstance().getGlobalTotal());
                }
            }
        });
    }

//    Modals Notes untuk penjual
private void showNoteDialog(Menu menu) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("Catatan untuk " + menu.getProductName());
    final EditText input = new EditText(context);
    input.setHint("Contoh: Tidak pakai sambal...");
    input.setText(menu.getNote()); // Tampilkan catatan lama jika ada
    builder.setView(input);
    builder.setPositiveButton("Simpan", (dialog, which) -> menu.setNote(input.getText().toString()));
    builder.setNegativeButton("Batal", (dialog, which) -> dialog.dismiss());
    builder.show();
}

// Get Item Count
    @Override
    public int getItemCount() { return menuList.size(); }

//    Recycle Item
    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvHarga, tvQty;
        ImageView imgMenu;
        ImageButton btnPlus, btnMinus;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama_menu);
            tvHarga = itemView.findViewById(R.id.tv_harga_menu);
            tvQty = itemView.findViewById(R.id.tv_qty);
            imgMenu = itemView.findViewById(R.id.img_menu);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            btnMinus = itemView.findViewById(R.id.btn_minus);
        }
    }
}