package ar.com.sistema.perritosfinderapp;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapaListener implements GoogleMap.OnMapClickListener{

    private GoogleMap map;

    public mapaListener(GoogleMap m){
        this.map = m;
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        map.addMarker(new MarkerOptions().position(latLng).title("TEST"));
    }
}
