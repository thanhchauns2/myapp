package com.example.googlemap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.Manifest;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Them_vi_tri extends AppCompatActivity {
    ImageButton bt_back;
    Button bt_save;
    EditText text_moTa_save;
    EditText text_ten_save;
    private LocationManager locationManager;
    private LocationListener locationListener;
    double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo FirebaseApp ở đây trước khi thực hiện bất kỳ thao tác nào với Firebase
        FirebaseApp.initializeApp(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                lat = latitude;
                lon = longitude;
                // Update UI with current latitude and longitude
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        setContentView(R.layout.activity_them_vi_tri);
        bt_back = findViewById(R.id.back_save);
        bt_save = findViewById(R.id.bt_save);
        text_moTa_save = findViewById(R.id.text_moTa_save);
        text_ten_save = findViewById(R.id.text_ten_save);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(Them_vi_tri.this, MainActivity.class);
                startActivity(switchActivityIntent);
            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tenViTriEditText = findViewById(R.id.text_ten_save);
                EditText moTaEditText = findViewById(R.id.text_moTa_save);

                String tenViTri = tenViTriEditText.getText().toString().trim();
                String moTa = moTaEditText.getText().toString().trim();

                if (!TextUtils.isEmpty(tenViTri) && !TextUtils.isEmpty(moTa)) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("viTri");
                    String id = databaseReference.push().getKey();
                    ViTri viTri = new ViTri(id, tenViTri, moTa, lat, lon);
                    databaseReference.child(id).setValue(viTri);
                    System.out.println("print sample");
                    Toast.makeText(getApplicationContext(), "Thêm vị trí thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
