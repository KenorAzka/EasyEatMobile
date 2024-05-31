package com.example.aplikasipesanmakanan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    String namaUser;
    TextView txtNama;

    FirebaseUser user;
    FirebaseAuth mAuth;

    FirebaseFirestore fireDb;

    private RecyclerView recPenyetan;
    private RecyclerView recDrinks;
    private ArrayList<Makanan> listMakanan;
    private ArrayList<Drinks> listDrinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initFab();

        fireDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        namaUser = user.getEmail();

        recPenyetan = findViewById(R.id.rec_penyetan);
        recDrinks = findViewById((R.id.rec_drinks));
        initDataPenyetan();
        initDataDrinks();

        recPenyetan.setAdapter(new MakananAdapter(listMakanan));
        recPenyetan.setLayoutManager(new LinearLayoutManager(this));

        recDrinks.setAdapter(new DrinksAdapter(listDrinks));
        recDrinks.setLayoutManager(new LinearLayoutManager(this));

        txtNama = findViewById(R.id.txtNama);
        txtNama.setText("Hi, " + namaUser);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initFab(){
        FloatingActionButton fabCart = findViewById(R.id.fabCart);
        fabCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CartActivity.class));
            }
        });

        FloatingActionButton fabLogout = findViewById(R.id.fabLogout);
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getBaseContext(), MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
    }

    private void initDataPenyetan(){
        this.listMakanan =  new ArrayList<>();
        listMakanan.add(new Makanan("Nasi Goreng", "4000", R.drawable.nasgor));
        listMakanan.add(new Makanan("Soto", "3500", R.drawable.soto));
        listMakanan.add(new Makanan("Ayam Geprek", "4000", R.drawable.ayamgep));
        listMakanan.add(new Makanan("Brownies", "2500", R.drawable.brownies));
        listMakanan.add(new Makanan("Risol", "2500", R.drawable.risol));
        listMakanan.add(new Makanan("Nasi Bandeng", "3000", R.drawable.nasiban));
        listMakanan.add(new Makanan("Gorengan", "1000", R.drawable.gorengan));
        listMakanan.add(new Makanan("Tempura", "5000", R.drawable.tempura));
    }

    private void initDataDrinks(){
        this.listDrinks = new ArrayList<>();
        listDrinks.add(new Drinks("Es Jeruk", "3000", R.drawable.es_jeruk));
        listDrinks.add(new Drinks("Es Teh", "3000", R.drawable.es_teh));
        listDrinks.add(new Drinks("Es Hilo", "5000", R.drawable.hllo));
        listDrinks.add(new Drinks("Kopi Susu", "5000", R.drawable.kopi_susu));
        listDrinks.add(new Drinks("Air Mineral", "2000", R.drawable.air_mineral));
        listDrinks.add(new Drinks("Es Mari Oppa", "5000", R.drawable.mari));
        listDrinks.add(new Drinks("Es Tea Jus", "2500", R.drawable.teajus));
    }
}