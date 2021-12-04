package com.victorcruz.parking;

public class ParqueosPojo {
    private String nombreParqueo;
    private  String disponible;
    private String latitud;
    private String longitud;

    //Constructor
    public ParqueosPojo() {
    }

    public String getNombreParqueo() {
        return nombreParqueo;
    }

    public void setNombreParqueo(String nombreParqueo) {
        this.nombreParqueo = nombreParqueo;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }




}
