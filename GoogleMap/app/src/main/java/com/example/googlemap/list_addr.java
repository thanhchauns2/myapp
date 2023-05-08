package com.example.googlemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List_addr extends AppCompatActivity {
    ListView lv;
    SearchView searchView;
    ImageButton bt_quaylai;
    ArrayList<String> mang;
    ArrayAdapter ad; // thêm biến ArrayAdapter vào đây

    DatabaseReference databaseReference;

    ArrayList<ViTri> vt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_addr);
        lv = (ListView) findViewById(R.id.list);
        mang = new ArrayList<>();
        vt = new ArrayList<>();
        ad = new ArrayAdapter<>(
                List_addr.this,
                android.R.layout.simple_list_item_1,
                mang
        ); // tạo ArrayAdapter ở đây
        lv.setAdapter(ad); // đặt ArrayAdapter vào ListView
        bt_quaylai = findViewById(R.id.imageButton);
        bt_quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(List_addr.this, MainActivity.class);
                startActivity(switchActivityIntent);
            }
        });
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

        // Khởi tạo đối tượng databaseReference để truy vấn cơ sở dữ liệu Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("viTri");

        // Thêm listener để lấy dữ liệu từ Firebase và hiển thị lên ListView
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mang.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ViTri viTri = snapshot.getValue(ViTri.class);
                    String tenViTri = viTri.tenViTri;
                    String moTa = viTri.moTa;
                    vt.add(viTri);
                    mang.add(tenViTri + " - " + moTa);
                }
                ad.notifyDataSetChanged(); // cập nhật dữ liệu trên ListView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(List_addr.this, "Không thể kết nối đến cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Mở Activity2 khi click vào mục trong ListView

                Intent intent = new Intent(List_addr.this, Chi_Tiet.class);
                intent.putExtra("id", vt.get(position).id);
                intent.putExtra("lon", vt.get(position).lon);
                intent.putExtra("lat", vt.get(position).lat);
                intent.putExtra("tenViTri", vt.get(position).tenViTri);
                intent.putExtra("moTa", vt.get(position).moTa);
                startActivity(intent);

//                Uri gmmIntentUri = Uri.parse("geo:"
//                        +  vt.get(position).lat + ","
//                        +  vt.get(position).lon
//                        + "?q=" +  vt.get(position).tenViTri
//                        + "(" + vt.get(position).moTa + ")"
//                );
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                startActivity(mapIntent);
            }
        });
    }
}

