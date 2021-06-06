package ar.com.sistema.perritosfinderapp;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FotosTmpViewHolder extends RecyclerView.ViewHolder{
    ImageView fotoPerroTmp;
    private int position;

    public FotosTmpViewHolder(@NonNull View itemView) {
        super(itemView);
        this.fotoPerroTmp = itemView.findViewById(R.id.fotoPerroTmp);
    }

    public void setPosition(int position){
        this.position = position;
    }
}
