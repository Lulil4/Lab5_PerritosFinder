package ar.com.sistema.perritosfinderapp;


import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
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
                                    Object ubicacionTraida = pMapeado.get("ubicacion");
                                    String ubicacionTxt = ubicacionTraida.toString().replace("[", "");
                                    ubicacionTxt = ubicacionTxt.replace("]", "");
                                    ubicacionTxt = ubicacionTxt.replace("{latitude=", "");
                                    ubicacionTxt = ubicacionTxt.replace("longitude=", "");
                                    ubicacionTxt = ubicacionTxt.replace("}", "");

                                    String[] ubicacionArray = ubicacionTxt.split(", ");
                                    Log.d("TRAIGOUBICACIONARRAY", ubicacionArray[0]);
                                    Log.d("TRAIGOUBICACIONARRAY", ubicacionArray[1]);
                                    LatLng ubicacion = new LatLng(Double.parseDouble(ubicacionArray[0]), Double.parseDouble(ubicacionArray[1]));
                                    Perro p = new Perro((String)pMapeado.get("descripcion"),
                                            (ArrayList)pMapeado.get("fotos"),
                                            (boolean)pMapeado.get("retenido"),
                                            (String)pMapeado.get("telefono"),
                                            (ArrayList)pMapeado.get("medios"),
                                            ubicacion,
                                            (String)pMapeado.get("fecha")
                                            );
                                    perros.add(p);
                                });

                                ponerMarcadores(map);
                            } else {
                                Log.d("MAL", "Error getting documents: ", task.getException());
                            }
                        }
                    });
    }

    public void subirUno(Perro p, View espera, View layout){
       layout.setVisibility(View.GONE);
       espera.setVisibility(View.VISIBLE);

       if (layout.getVisibility() == View.GONE) {

           FirebaseStorage storage = FirebaseStorage.getInstance();
           StorageReference storageRef = storage.getReference();

           String child;
           StorageReference perrosRef;
           ArrayList<String> fotosUrl = new ArrayList<String>();
           int i = 0;
           for (String f : p.getFotos()) {
               child = p.getDescripcion() + "." + p.getTelefono() + i + ".jpg";
               perrosRef = storageRef.child(child);
               Uri uri = Uri.parse(f);
               UploadTask task = perrosRef.putFile(uri);
               while (!task.isSuccessful()) {
               }
               Task<Uri> task2 = perrosRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       String downloadUrl = uri.toString();
                       fotosUrl.add(downloadUrl);
                       Log.d("TRAIGOURL: ", uri.toString());

                       if (p.getFotos().size() == fotosUrl.size()) {
                           Log.d("TRAIGOURL TAMANIO", " ES: " + fotosUrl.size());
                           FirebaseFirestore db = FirebaseFirestore.getInstance();

                           Map<String, Object> data = new HashMap<>();
                           data.put("descripcion", p.getDescripcion());
                           data.put("fecha", p.getFecha());
                           data.put("fotos", fotosUrl);
                           data.put("medios", p.getMedios());
                           data.put("retenido", p.isRetenido());
                           data.put("telefono", p.getTelefono());
                           data.put("ubicacion", p.getUbicacion());

                           db.collection("perritos").document().set(data);
                       }
                   }
               });

               while (!task2.isSuccessful()) {
               }
               i++;
           }
       }
    }

    private void ponerMarcadores(GoogleMap map){
        LatLng latLngPerro = null;
        for (Perro p: this.perros) {
            latLngPerro = p.getUbicacion();
            map.addMarker(new MarkerOptions().position(latLngPerro).title(p.getDescripcion()));
        }
    }
}
