package ar.com.sistema.perritosfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import io.grpc.okhttp.internal.framed.FrameReader;

public class cargarPerro extends AppCompatActivity{
    FotosAdapter adapter;
    private TextView tvDescripcion;
    private ImageView imgWsp;
    private ImageView imgCall;
    private TextView tvFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_perro);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Perro Visto");

        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(this, R.color.colorPastelGreen));

        ab.setBackgroundDrawable(colorDrawable);

        ab.setDisplayHomeAsUpEnabled(true);

        Bundle extras = super.getIntent().getExtras();
        ArrayList<String> fotos = extras.getStringArrayList("fotos");
        String descripcion = extras.getString("descripcion");
        boolean retenido = extras.getBoolean("retenido");
        String telefono = extras.getString("telefono");
        ArrayList<String> medios = extras.getStringArrayList("medios");
        String fecha = extras.getString("fecha");

        this.adapter = new FotosAdapter(fotos);
        RecyclerView rv = super.findViewById(R.id.rvFotos);
        rv.setAdapter(this.adapter);

        this.tvDescripcion = this.findViewById(R.id.tvDescripcion);
        this.tvFecha = this.findViewById(R.id.tvFecha);
        this.imgCall = this.findViewById(R.id.llamada);
        this.imgWsp = this.findViewById(R.id.mandarWsp);

        this.tvDescripcion.setText(descripcion);
        this.tvFecha.setText("Visto: " + fecha);

        if(retenido){
            for (String m: medios) {
                if ("whatsapp".equals(m)){
                    this.imgWsp.setTag(telefono);
                    this.imgWsp.setVisibility(View.VISIBLE);
                    this.imgWsp.setOnClickListener(new cargarPerroListener());
                }
                else if("llamada".equals(m)){
                    this.imgCall.setTag(telefono);
                    this.imgCall.setVisibility(View.VISIBLE);
                    this.imgCall.setOnClickListener(new cargarPerroListener());
                }
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rv.setLayoutManager(layoutManager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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