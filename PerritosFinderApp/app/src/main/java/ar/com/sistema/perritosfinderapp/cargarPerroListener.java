package ar.com.sistema.perritosfinderapp;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.net.URLEncoder;

public class cargarPerroListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        ImageView img = (ImageView) view;
        Activity a = (Activity) view.getContext();
        Intent i;

        if (R.id.mandarWsp == img.getId()){
            PackageManager packageManager = a.getPackageManager();
            i = new Intent(Intent.ACTION_VIEW);

            try {
                String telefono = img.getTag().toString().substring(3);
                String url = "https://api.whatsapp.com/send?phone="+ telefono +"&text=" + URLEncoder.encode("Hola! Vi tu publicación sobre un perro avistado. Quisiera saber..", "UTF-8");
                i.setPackage("com.whatsapp");
                i.setData(Uri.parse(url));
                if (i.resolveActivity(packageManager) != null) {
                    a.startActivity(i);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }


        }
        else if(R.id.llamada == img.getId()){

            i = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + img.getTag()));

            if(ContextCompat.checkSelfPermission(a, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(a, new String[]{Manifest.permission.CALL_PHONE}, 123);

            }
            else{
                a.startActivity(i);
            }

        }
    }
}
