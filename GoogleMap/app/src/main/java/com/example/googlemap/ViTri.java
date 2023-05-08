package com.example.googlemap;

public class ViTri {
    public String id;
    public String tenViTri;
    public String moTa;
    public double lat;
    public double lon;

    public ViTri() {
        // Default constructor required for calls to DataSnapshot.getValue(ViTri.class)
    }

    public ViTri(String id, String tenViTri, String moTa) {
        this.id = id;
        this.tenViTri = tenViTri;
        this.moTa = moTa;
    }

    public ViTri(String id, String tenViTri, String moTa, double lat, double lon){
        this.id = id;
        this.tenViTri = tenViTri;
        this.moTa = moTa;
        this.lat = lat;
        this.lon = lon;
    }
}

