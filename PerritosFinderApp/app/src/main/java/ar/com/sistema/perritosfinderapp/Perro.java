package ar.com.sistema.perritosfinderapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Perro {
    private String descripcion;
    private ArrayList<String> fotos;
    private boolean retenido;
    private String telefono;
    private ArrayList<String> medios;
    private LatLng ubicacion;
    private String fecha;


    public Perro(String descripcion, ArrayList<String> fotos, boolean retenido, String telefono, ArrayList<String> medios, LatLng ubicacion, String fecha) {
        this.descripcion = descripcion;
        this.fotos = fotos;

        this.retenido = retenido;
        if (retenido == true){
            this.telefono = "+54" + telefono;
            this.medios = medios;
        }
        else{
            this.telefono = "";
            this.medios = new ArrayList<>();
        }
        Log.d("TELEFONO; ", this.telefono);
        this.ubicacion = ubicacion;
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<String> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<String> fotos) {
        this.fotos = fotos;
    }

    public boolean isRetenido() {
        return retenido;
    }

    public void setRetenido(boolean retenido) {
        this.retenido = retenido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<String> getMedios() {
        return medios;
    }

    public void setMedios(ArrayList<String> medios) {
        this.medios = medios;
    }

    public LatLng getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(LatLng ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perro perro = (Perro) o;
        return fotos.equals(perro.fotos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fotos);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb = sb.append("Descripción: ");
        sb = sb.append(this.descripcion);
        sb = sb.append(" - Fotos: ");
        for (String f : this.fotos) {
            sb = sb.append(f);
            sb = sb.append(" ");
        }
        if(this.retenido){
            sb = sb.append(" - Telefono:");
            sb = sb.append(this.telefono);
            sb = sb.append(" - Medios:");
            for (String m: this.medios) {
                sb = sb.append(m);
                sb = sb.append(" ");
            }
        }
        sb = sb.append(" - Ubicacion: ");
        sb = sb.append(this.ubicacion);
        sb = sb.append(" - Fecha: ");
        sb = sb.append(this.fecha);

        return sb.toString();
    }
}
