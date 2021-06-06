package ar.com.sistema.perritosfinderapp;

import android.graphics.Bitmap;
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

import java.util.List;

public class FotosAdapter extends RecyclerView.Adapter<FotosViewHolder> implements Handler.Callback {
    List<String> fotos;
    private Handler handlerImagen;
    private FotosViewHolder holderTmp;
    private int posTmp;

    public FotosAdapter(List<String>fotos){
        this.fotos = fotos;
    }

    @NonNull
    @Override
    public FotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        FotosViewHolder personaViewHolder = new FotosViewHolder(v);
        return personaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FotosViewHolder holder, int position) {
        String foto = this.fotos.get(position);

        this.handlerImagen = new Handler(Looper.myLooper(), this);

        this.holderTmp = holder;
        this.posTmp = position;

        HiloConexion hiloImagen = new HiloConexion(this.handlerImagen, foto);
        hiloImagen.start();

    }

    @Override
    public int getItemCount() {
        return this.fotos.size();
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        byte[] bytesImg = (byte[])message.obj;

        this.holderTmp.fotoPerro.setImageBitmap(BitmapFactory.decodeByteArray(bytesImg, 0, bytesImg.length));
        this.holderTmp.setPosition(this.posTmp);

        this.holderTmp = null;
        this.posTmp = -1;
        return false;
    }
}
