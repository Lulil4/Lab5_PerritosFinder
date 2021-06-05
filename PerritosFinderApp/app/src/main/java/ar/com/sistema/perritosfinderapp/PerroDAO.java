package ar.com.sistema.perritosfinderapp;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PerroDAO {
    private static PerroDAO pdao;
    private static List<Perro> perros;

    private PerroDAO() {}

    public static PerroDAO generarObjeto() {
        if(pdao == null) {
            pdao = new PerroDAO();
            perros = new ArrayList<Perro>();
        }
            return pdao;
    }


    public static List<Perro> getPerros() {
        return perros;
    }

    public static void guardar(Perro p) {
        //insert
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarTodos(GoogleMap map) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Task t = db.collection("perritos")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<Map> datos = new ArrayList<>();
                                for (QueryDocumentSnapshot d : task.getResult()) {
                                    Map pMap = d.getData();
                                    datos.add(pMap);
                                }

                                datos.forEach((pMapeado)->{
                                    Perro p = new Perro((String)pMapeado.get("descripcion"),
                                            (ArrayList)pMapeado.get("fotos"),
                                            (boolean)pMapeado.get("retenido"),
                                            (Long)pMapeado.get("telefono"),
                                            (ArrayList)pMapeado.get("medios"),
                                            (String)pMapeado.get("ubicacion"));
                                    perros.add(p);
                                });

                                ponerMarcadores(map);
                            } else {
                                Log.d("MAL", "Error getting documents: ", task.getException());
                            }
                        }
                    });
    }

    private void ponerMarcadores(GoogleMap map){
        LatLng latLngPerro = null;
        for (Perro p: this.perros) {
            latLngPerro = p.getUbicacion();
            map.addMarker(new MarkerOptions().position(latLngPerro).title(p.getDescripcion()));
        }
    }
}
