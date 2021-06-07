package ar.com.sistema.perritosfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class guardarPerro extends AppCompatActivity {
    private EditText edtDescripcion;
    private Button btnGuardar;
    private Button btnCargaFotos;
    private List<Uri> uris;
    private CheckBox retenido;
    private TextView tvTelefono;
    private CheckBox wsp;
    private CheckBox llamada;
    private LatLng ubicacion;
    private TextView tvMedios;
    private EditText edtFecha;
    private View layoutTodoGuardar;
    private TextView tvEspere;

    public EditText getEdtDescripcion() {
        return edtDescripcion;
    }

    public List<Uri> getUris() {
        return uris;
    }

    public CheckBox getRetenido() {
        return retenido;
    }

    public TextView getTvTelefono() {
        return tvTelefono;
    }

    public CheckBox getWsp() {
        return wsp;
    }

    public CheckBox getLlamada() {
        return llamada;
    }

    public LatLng getUbicacion() {
        return ubicacion;
    }

    public TextView getTvMedios() {
        return tvMedios;
    }

    public EditText getEdtFecha() {
        return edtFecha;
    }

    public View getLayoutTodoGuardar() {
        return layoutTodoGuardar;
    }

    public TextView getTvEspere() {
        return tvEspere;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_perro);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Cargar Perro");

        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(this, R.color.colorPastelGreen));
        ab.setBackgroundDrawable(colorDrawable);
        ab.setDisplayHomeAsUpEnabled(true);

        this.btnCargaFotos = this.findViewById(R.id.btnCargarFotos);
        this.btnGuardar = this.findViewById(R.id.btnGuardar);
        this.edtDescripcion = this.findViewById(R.id.edtDescripcion);
        this.retenido = this.findViewById(R.id.ckbRetenido);
        this.tvTelefono= this.findViewById(R.id.edtTelefono);
        this.wsp= this.findViewById(R.id.ckbWhatsapp);
        this.llamada = this.findViewById(R.id.cbkLlamada);
        this.tvMedios = this.findViewById(R.id.tvMedios);
        this.edtFecha = this.findViewById(R.id.edtFecha);
        this.layoutTodoGuardar = this.findViewById(R.id.layoutTodoGuardar);
        this.tvEspere = this.findViewById(R.id.tvEspere);

        this.btnCargaFotos.setOnClickListener(new guardarPerroListener ());
        this.btnGuardar.setOnClickListener(new guardarPerroListener ());
        this.retenido.setOnClickListener(new guardarPerroListener());

        Bundle extras = super.getIntent().getExtras();
        String ubicacionString = extras.getString("ubicacion");
        ubicacionString = ubicacionString.replace("(", "");
        ubicacionString = ubicacionString.replace(")", "");
        ubicacionString = ubicacionString.replace("lat/lng:", "");

        String[] arrayUbicacion = ubicacionString.split(",");


        this.ubicacion = new LatLng(Double.parseDouble(arrayUbicacion[0]), Double.parseDouble(arrayUbicacion[1]));

    }
    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void finishActivity(int requestCode) {
        super.finishActivity(requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent imagenes) {
        super.onActivityResult(requestCode, resultCode, imagenes);
        if (requestCode == 111){
            if (resultCode == -1){
                uris = null;
                uris = new ArrayList<Uri>();
                File file;
                Uri uri;
                if (imagenes.getClipData() != null){
                    ClipData clipData = imagenes.getClipData();
                    for(int i = 0; i < clipData.getItemCount(); i++){
                        ClipData.Item item = clipData.getItemAt(i);
                        uri = item.getUri();
                        uris.add(uri);
                    }
                }else{
                  uri = Uri.parse(imagenes.getData().toString());
                  this.uris.add(uri);
                }

                FotosTmpAdapter adapter = new FotosTmpAdapter(this.uris);
                RecyclerView rv = super.findViewById(R.id.rvFotosTmp);
                rv.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                rv.setLayoutManager(layoutManager);

            }
            else{
                Toast.makeText(this ,"No se cargó ninguna foto", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            super.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}