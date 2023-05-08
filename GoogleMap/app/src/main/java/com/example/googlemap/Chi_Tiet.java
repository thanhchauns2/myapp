package com.example.googlemap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Chi_Tiet extends AppCompatActivity {

    TextView textView_in4;
    ImageButton bt_back_in4;
    EditText text_ten_in4, text_moTa_in4;
    Button bt_show, bt_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        text_moTa_in4 = findViewById(R.id.text_moTa_in4);
        textView_in4 = findViewById(R.id.textView_in4);
        bt_back_in4 = findViewById(R.id.bt_back_in4);
        text_ten_in4 = findViewById(R.id.text_ten_in4);
        bt_show = findViewById(R.id.bt_show);
        bt_update = findViewById(R.id.bt_update);

        final double longtitude = getIntent().getIntExtra("lon", 0);
        final double latitude = getIntent().getIntExtra("lat", 0);
        String tenViTri = getIntent().getStringExtra("tenViTri");
        String moTa = getIntent().getStringExtra("moTa");
        String id = getIntent().getStringExtra("id");

        text_moTa_in4.setText(moTa);
        text_ten_in4.setText(tenViTri);

        bt_back_in4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(Chi_Tiet.this, List_addr.class);
                startActivity(switchActivityIntent);
            }
        });

        bt_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở ứng dụng Google Maps với vị trí hiện tại
                Uri gmmIntentUri = Uri.parse("geo:"
                        +  latitude + ","
                        +  longtitude
                        + "?q=" +  tenViTri
                        + "(" + moTa + ")"
                );
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Chi_Tiet.this, UpdateLocation.class);
                intent.putExtra("id", id);
                intent.putExtra("lon", longtitude);
                intent.putExtra("lat", latitude);
                intent.putExtra("tenViTri", tenViTri);
                intent.putExtra("moTa", moTa);
                startActivity(intent);
            }
        });
    }
}