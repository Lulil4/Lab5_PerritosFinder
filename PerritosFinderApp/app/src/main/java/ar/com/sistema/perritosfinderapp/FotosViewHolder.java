package ar.com.sistema.perritosfinderapp;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FotosViewHolder extends RecyclerView.ViewHolder{

    ImageView fotoPerro;
    private int position;

    public FotosViewHolder(@NonNull View itemView) {
        super(itemView);
        this.fotoPerro = itemView.findViewById(R.id.fotoPerro);
    }

    public void setPosition(int position){
        this.position = position;
    }

}
