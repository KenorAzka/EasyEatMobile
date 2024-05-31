package com.example.aplikasipesanmakanan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {

    ViewHolder holder;

    public MakananAdapter(ArrayList<Makanan> listMakanan) {
        this.listMakanan = listMakanan;
    }

    private ArrayList<Makanan> listMakanan;

    @NonNull
    @Override
    public MakananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder =  new ViewHolder(inflater.inflate(R.layout.item_menu, parent, false));

        return holder;
    }

    public String rp(int txt){
        Locale locale = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setMaximumFractionDigits(0);
        return format.format(txt); // Integer.toString(total);
    }

    @Override
    public void onBindViewHolder(@NonNull MakananAdapter.ViewHolder holder, int position) {
        Makanan makanan = listMakanan.get(position);
        holder.txtNamaPenyetan.setText(makanan.getNamaPenyetan());
        holder.txtHargaPenyetan.setText(rp(Integer.parseInt(makanan.getHargaPenyetan())));
        holder.imgPenyetan.setImageResource(makanan.getImgPenyetan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listMakanan.get(position).getNamaPenyetan().equals("Nasi Goreng")) {
                    Intent intent = new Intent(holder.itemView.getContext(), MenuDetailActivity.class);
                    intent.putExtra("GAMBAR_DEFAULT", R.drawable.nasgor);
                    intent.putExtra("NAMA_DEFAULT", "Nasi Goreng");
                    intent.putExtra("DESKRIPSI_DEFAULT", "Nasi Goreng dijamin enak dan memuaskan serta mengatasi perut kosong anda");
                    intent.putExtra("HARGA_DEFAULT", "4000");
                    holder.itemView.getContext().startActivity(intent);
                }
                if (listMakanan.get(position).getNamaPenyetan().equals("Soto")) {
                    Intent intent = new Intent(holder.itemView.getContext(), MenuDetailActivity.class);
                    intent.putExtra("GAMBAR_DEFAULT", R.drawable.soto);
                    intent.putExtra("NAMA_DEFAULT", "Soto");
                    intent.putExtra("DESKRIPSI_DEFAULT", "Soto Ayam yang enak dan menyegarkan, paling enak di santap saat pagi");
                    intent.putExtra("HARGA_DEFAULT", "3500");
                    holder.itemView.getContext().startActivity(intent);
                }
                if (listMakanan.get(position).getNamaPenyetan().equals("Ayam Geprek")) {
                    Intent intent = new Intent(holder.itemView.getContext(), MenuDetailActivity.class);
                    intent.putExtra("GAMBAR_DEFAULT", R.drawable.ayamgep);
                    intent.putExtra("NAMA_DEFAULT", "Ayam Geprek");
                    intent.putExtra("DESKRIPSI_DEFAULT", "Ayam Geprek? hmm inilah favorit anak-anak muda jaman sekarang");
                    intent.putExtra("HARGA_DEFAULT", "4000");
                    holder.itemView.getContext().startActivity(intent);
                }
                if (listMakanan.get(position).getNamaPenyetan().equals("Brownies")) {
                    Intent intent = new Intent(holder.itemView.getContext(), MenuDetailActivity.class);
                    intent.putExtra("GAMBAR_DEFAULT", R.drawable.brownies);
                    intent.putExtra("NAMA_DEFAULT", "Brownies");
                    intent.putExtra("DESKRIPSI_DEFAULT", "Brownies ini kalo dimakan sama liatin crush kalian dijamin tambah manis:)");
                    intent.putExtra("HARGA_DEFAULT", "2500");
                    holder.itemView.getContext().startActivity(intent);
                }
                if (listMakanan.get(position).getNamaPenyetan().equals("Risol")) {
                    Intent intent = new Intent(holder.itemView.getContext(), MenuDetailActivity.class);
                    intent.putExtra("GAMBAR_DEFAULT", R.drawable.ayam_geprek);
                    intent.putExtra("NAMA_DEFAULT", "Risol");
                    intent.putExtra("DESKRIPSI_DEFAULT", "Hmmm, Crispy di luar lembut di dalam seakan akan terbang dan mendarat di pelukanmu");
                    intent.putExtra("HARGA_DEFAULT", "2500");
                    holder.itemView.getContext().startActivity(intent);
                }
                if (listMakanan.get(position).getNamaPenyetan().equals("Nasi Bandeng")) {
                    Intent intent = new Intent(holder.itemView.getContext(), MenuDetailActivity.class);
                    intent.putExtra("GAMBAR_DEFAULT", R.drawable.nasiban);
                    intent.putExtra("NAMA_DEFAULT", "Nasi Bandeng");
                    intent.putExtra("DESKRIPSI_DEFAULT", "Nasi Bandeng apa Nasi Kucing ya, terserah yang penting enak");
                    intent.putExtra("HARGA_DEFAULT", "3000");
                    holder.itemView.getContext().startActivity(intent);
                }
                if (listMakanan.get(position).getNamaPenyetan().equals("Gorengan")) {
                    Intent intent = new Intent(holder.itemView.getContext(), MenuDetailActivity.class);
                    intent.putExtra("GAMBAR_DEFAULT", R.drawable.gorengan);
                    intent.putExtra("NAMA_DEFAULT", "Gorengan");
                    intent.putExtra("DESKRIPSI_DEFAULT", "Ga makan dengan gorengan seperti dunia tanpa kamu, Kayak ada yang kurang ");
                    intent.putExtra("HARGA_DEFAULT", "1000");
                    holder.itemView.getContext().startActivity(intent);
                }
                if (listMakanan.get(position).getNamaPenyetan().equals("Tempura")) {
                    Intent intent = new Intent(holder.itemView.getContext(), MenuDetailActivity.class);
                    intent.putExtra("GAMBAR_DEFAULT", R.drawable.tempura);
                    intent.putExtra("NAMA_DEFAULT", "Tempura");
                    intent.putExtra("DESKRIPSI_DEFAULT", "Jangan beli deh kalo ga mau dimntain sama temen-temen");
                    intent.putExtra("HARGA_DEFAULT", "5000");
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMakanan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNamaPenyetan, txtHargaPenyetan;
        public ImageView imgPenyetan;
        public ConstraintLayout itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNamaPenyetan = (TextView) itemView.findViewById(R.id.txtNamaItem);
            txtHargaPenyetan = (TextView) itemView.findViewById(R.id.txtHargaItem);
            imgPenyetan = (ImageView) itemView.findViewById(R.id.imgItem);
            this.itemView = (ConstraintLayout) itemView.findViewById(R.id.itemLayout);
        }
    }
}
