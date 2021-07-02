package ar.com.sistema.perritosfinderapp;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class mapaListener implements GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener{

    private GoogleMap map;
    private Activity a;
    public mapaListener(GoogleMap m, Activity a){
        this.map = m;
        this.a = a;
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        Intent intent = new Intent(a, guardarPerro.class);
        intent.putExtra("ubicacion", latLng.toString());
        a.startActivity(intent);
    }

    @Override
    public boolean onMarkerClick(@NonNull @NotNull Marker marker) {
        List<Perro> perros = PerroDAO.getPerros();

        for (Perro p: perros) {
            if (p.getUbicacion().toString().equals(marker.getPosition().toString())){

                Intent intent = new Intent(a, cargarPerro.class);
                intent.putExtra("fotos", p.getFotos());
                intent.putExtra("descripcion", p.getDescripcion());
                intent.putExtra("retenido", p.isRetenido());
                intent.putExtra("fecha", p.getFecha());
                if (p.isRetenido()){
                    intent.putExtra("telefono", p.getTelefono());
                    intent.putExtra("medios", p.getMedios());
                }
                a.startActivityForResult(intent, 222);
                break;
            }
        }

        return false;
    }
}
