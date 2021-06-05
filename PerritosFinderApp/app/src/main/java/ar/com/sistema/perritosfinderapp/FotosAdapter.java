package ar.com.sistema.perritosfinderapp;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FotosAdapter extends RecyclerView.Adapter<FotosViewHolder>{
    List<String> fotos;

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
        Log.d("FOTOSS:", "pos:"+ position);
        String foto = this.fotos.get(position);
        Log.d("FOTOSS:", "pos:"+ position);
        holder.fotoPerro.setImageURI(Uri.parse(foto));
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return this.fotos.size();
    }
}
