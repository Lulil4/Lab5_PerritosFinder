package ar.com.sistema.perritosfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class cargarPerro extends AppCompatActivity {
    FotosAdapter adapter;
    private TextView tvDescripcion;
    private ImageView imgWsp;
    private ImageView imgCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_perro);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Perro Visto");
        ab.setDisplayHomeAsUpEnabled(true);

        RecyclerView rv = super.findViewById(R.id.rvFotos);

        rv.setAdapter(this.adapter);

        Bundle extras = super.getIntent().getExtras();

        ArrayList<String> fotos = extras.getStringArrayList("fotos");
        String descripcion = extras.getString("descripcion");
        boolean retenido = extras.getBoolean("retenido");
        Long telefono = extras.getLong("telefono");
        ArrayList<String> medios = extras.getStringArrayList("medios");


        this.adapter = new FotosAdapter(fotos);

        this.tvDescripcion = this.findViewById(R.id.tvDescripcion);
        this.imgCall = this.findViewById(R.id.llamada);
        this.imgWsp = this.findViewById(R.id.mandarWsp);

        tvDescripcion.setText(descripcion);

        if(retenido){
            for (String m: medios) {
                if ("whatsapp".equals(m)){
                    this.imgWsp.setVisibility(View.VISIBLE);
                }
                else if("llamada".equals(m)){
                    this.imgCall.setVisibility(View.VISIBLE);
                }
            }
            //HACER LOS INTENTS CON EL NUMERO!!
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