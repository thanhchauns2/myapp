package com.example.googlemap;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment\#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("viTri");
    MarkerOptions markerOptions = new MarkerOptions();

    ArrayList<String> mang = new ArrayList<>();
    ArrayList<ViTri> vt = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment supportMapFragment =(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mang.clear();
                        vt.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ViTri viTri = snapshot.getValue(ViTri.class);
                            String tenViTri = viTri.tenViTri;
                            String moTa = viTri.moTa;
                            vt.add(viTri);
                            mang.add(tenViTri + " - " + moTa);
                        }


                        for (ViTri x : vt){
                            LatLng latLng = new LatLng(x.lat, x.lon);
                            markerOptions.title(x.moTa);
                            markerOptions.position(latLng);
                            googleMap.addMarker(markerOptions);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Do something if the request is cancelled.
                    }
                });

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                    }
                });
            }
        });

        return view;
    }
}