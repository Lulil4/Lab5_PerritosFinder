package ar.com.sistema.perritosfinderapp;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class FotosTmpAdapter extends RecyclerView.Adapter<FotosTmpViewHolder>{
    List<Uri> fotos;

    public FotosTmpAdapter(List<Uri>fotos){
        this.fotos = fotos;
    }

    @NonNull
    @Override
    public FotosTmpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemtmp, parent, false);
        FotosTmpViewHolder fotoTmpViewHolder = new FotosTmpViewHolder(v);
        return fotoTmpViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FotosTmpViewHolder holder, int position) {
        Uri foto = this.fotos.get(position);
        holder.fotoPerroTmp.setImageURI(foto);
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return this.fotos.size();
    }
}