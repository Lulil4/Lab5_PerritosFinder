package ar.com.sistema.perritosfinderapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class guardarPerroListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {

        Activity a = (guardarPerro)view.getContext();

        if(R.id.ckbRetenido == view.getId()){
            if (((guardarPerro)a).getRetenido().isChecked()) {
                ((guardarPerro) a).getTvMedios().setVisibility(View.VISIBLE);
                ((guardarPerro) a).getWsp().setVisibility(View.VISIBLE);
                ((guardarPerro) a).getLlamada().setVisibility(View.VISIBLE);
                ((guardarPerro) a).getTvTelefono().setVisibility(View.VISIBLE);
            }
            else{
                ((guardarPerro) a).getTvMedios().setVisibility(View.INVISIBLE);
                ((guardarPerro) a).getWsp().setVisibility(View.INVISIBLE);
                ((guardarPerro) a).getLlamada().setVisibility(View.INVISIBLE);
                ((guardarPerro) a).getTvTelefono().setVisibility(View.INVISIBLE);
            }
        }
        else if(R.id.btnCargarFotos == view.getId()){

            Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            i.setType("image/*");
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            a.startActivityForResult(Intent.createChooser(i,"Select Picture"), 111);

        }
        else if(R.id.btnGuardar == view.getId()){
            boolean validado = validarDatos((guardarPerro)a);

            if (validado){

                String descripcion = ((guardarPerro) a).getEdtDescripcion().getText().toString();
                ArrayList<String> fotos = new ArrayList<String>();
                boolean retenido = false;
                String telefono = null;
                ArrayList<String> medios = null;
                CheckBox cbkWhatsapp = ((guardarPerro)a).getWsp();
                CheckBox cbkLlamada = ((guardarPerro)a).getLlamada();


                if (((guardarPerro) a).getRetenido().isChecked()){
                    medios = new ArrayList<String>();
                    retenido = true;
                    telefono = ((guardarPerro) a).getTvTelefono().getText().toString();
                    if (cbkWhatsapp.isChecked()){
                        medios.add("whatsapp");
                    }
                    else if(cbkLlamada.isChecked()) {
                        medios.add("llamada");
                    }
                }
                LatLng ubicacion = ((guardarPerro)a).getUbicacion();
                String fecha = ((guardarPerro)a).getEdtFecha().getText().toString();
                List<Uri> uris = ((guardarPerro) a).getUris();
                for (Uri u: uris) {
                    fotos.add(u.toString());
                }

                Perro p = new Perro(descripcion, fotos, retenido, telefono, medios, ubicacion, fecha);
                PerroDAO pDAO = PerroDAO.generarObjeto();


                pDAO.subirUno(p, ((guardarPerro) a).getTvEspere(), ((guardarPerro) a).getLayoutTodoGuardar());

                a.finish();
            }

        }

    }

    public boolean validarDatos(guardarPerro a){
        EditText edtDescripcion = a.getEdtDescripcion();
        String descripcion = edtDescripcion.getText().toString();
        CheckBox retenido = a.getRetenido();
        CheckBox cbkWhatsapp = a.getWsp();
        CheckBox cbkLlamada = a.getLlamada();
        String fecha = a.getEdtFecha().getText().toString();

        boolean validado = false;

        if (retenido.isChecked()){
            if (!cbkWhatsapp.isChecked() && !cbkLlamada.isChecked()){
                Toast.makeText(a ,"Por favor, seleccione un medio de comunicación", Toast.LENGTH_SHORT).show();
            }
            else{
                validado = true;
            }
        }
        else if (descripcion.length() < 5){
            Toast.makeText(a ,"Por favor, escriba una mejor descripción", Toast.LENGTH_SHORT).show();
        }
        else if("".equals(fecha) || fecha.length() < 10){
            Toast.makeText(a ,"Por favor, ingrese fecha y hora", Toast.LENGTH_SHORT).show();
        }
        else if (a.getUris() != null && a.getUris().size() > 3){
            Toast.makeText(a ,"Por favor, sólo cargue 3 fotos", Toast.LENGTH_SHORT).show();
        }
        else if (a.getUris() == null){
            Toast.makeText(a ,"Por favor, cargue al menos una foto", Toast.LENGTH_SHORT).show();
        }
        else{
            validado = true;
        }

        return validado;
    }
}
