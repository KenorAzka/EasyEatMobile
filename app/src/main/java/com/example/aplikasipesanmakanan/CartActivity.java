package com.example.aplikasipesanmakanan;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    TextView txtTotal;
    RecyclerView recPesanan;
    String namaUser;

    FirebaseUser user;
    private EditText editTextNote;

    FirebaseAuth mAuth;
    private FirebaseFirestore db;

    FirebaseFirestore fireDb;
    PesananAdapter adapter;

    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recPesanan = findViewById(R.id.rec_records);
        recPesanan.setLayoutManager(new LinearLayoutManager(this));

        fireDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        namaUser = user.getEmail();

        txtTotal = findViewById(R.id.txt_total);
        txtTotal.setText("0");
        getSumTotal();

        editTextNote = findViewById(R.id.editTextNote);
        db = FirebaseFirestore.getInstance();

        Button buttonBeli = findViewById(R.id.btn_purchase_all);
        buttonBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = editTextNote.getText().toString().trim();
                saveNoteToFirestore(note);
                purchaseAllItems();
            }
        });
    }

    private void saveNoteToFirestore(String note) {
        Map<String, Object> data = new HashMap<>();
        data.put("note", note);

        db.collection("notes")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CartActivity.this, "Catatan disimpan", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CartActivity.this, "Gagal menyimpan catatan", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Query query = fireDb.collection("pesanan")
                .whereEqualTo("userId", user.getUid());

        FirestoreRecyclerOptions<Pesanan> options = new FirestoreRecyclerOptions.Builder<Pesanan>()
                .setQuery(query, Pesanan.class).build();
        adapter = new PesananAdapter(options);
        recPesanan.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getSumTotal() {
        Query query = fireDb.collection("pesanan").whereEqualTo("userId", user.getUid());

        // Inisialisasi referensi ke koleksi di database Firestore
        Query collection = fireDb.collection("pesanan").whereEqualTo("userId", user.getUid());

        // Menggunakan metode get() untuk mengambil semua dokumen dalam koleksi
        collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Looping melalui setiap dokumen untuk menjumlahkan nilai yang diinginkan
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        int value = document.getLong("hargaPesanan").intValue();
                        int value2 = document.getLong("jumlahPesanan").intValue();
                        total += value * value2;
                    }
                    // Menetapkan hasil jumlah ke TextView
                    Locale locale = new Locale("in", "ID");
                    NumberFormat format = NumberFormat.getCurrencyInstance(locale);
                    format.setMaximumFractionDigits(0);
                    txtTotal.setText(format.format(total));
                } else {
                    // Menangani kesalahan jika terjadi saat mengambil dokumen
                }
            }
        });
    }

    public void purchaseAllItems() {
        // Mengambil referensi koleksi baru di Firestore untuk menyimpan semua item keranjang
        CollectionReference purchaseRef = fireDb.collection("purchases");

        // Mengambil semua item dari keranjang pengguna
        fireDb.collection("pesanan")
                .whereEqualTo("userId", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Menyimpan setiap item keranjang ke koleksi pembelian baru dengan email
                                Map<String, Object> purchaseData = new HashMap<>(document.getData());
                                purchaseData.put("email", namaUser);

                                purchaseRef.add(purchaseData)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error adding document", e);
                                            }
                                        });
                            }
                            // Setelah semua item disimpan, hapus item dari keranjang
                            deleteAllItemsFromCart();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void deleteAllItemsFromCart() {
        fireDb.collection("pesanan")
                .whereEqualTo("userId", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Hapus setiap item dari keranjang
                                fireDb.collection("pesanan").document(document.getId()).delete();
                            }
                            // Setelah semua item dihapus, reset total dan tampilkan pesan berhasil
                            total = 0;
                            txtTotal.setText("0");
                            Toast.makeText(CartActivity.this, "Pembelian berhasil!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
