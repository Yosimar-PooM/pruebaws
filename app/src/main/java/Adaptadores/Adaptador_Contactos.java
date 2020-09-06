package Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.genesis.pruebaws.R;
import com.mindorks.placeholderview.PlaceHolderView;

import Clases.Contactos;

public class Adaptador_Contactos extends PlaceHolderView.Adapter<Adaptador_Contactos.ViewHolderDatos> {
    Contactos[] listaDatos;
    Context context;
    private Posicion posicion;
    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }
    public Adaptador_Contactos(Contactos[] listaDatos) {
        this.listaDatos = listaDatos;
    }
    public Adaptador_Contactos(Contactos[] listaDatos, Context context) {
        this.context=context;
        this.listaDatos = listaDatos;
    }

    public Adaptador_Contactos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Enlaza el adaptador con el archivo layout_paises
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_contactos,null,false);
        return new Adaptador_Contactos.ViewHolderDatos(view,posicion);
    }

    @Override
    public void onBindViewHolder(Adaptador_Contactos.ViewHolderDatos viewHolderDatos, int i) {
        //establece la comunicación entre el adaptador y la clase viewHolderDatos
        viewHolderDatos.lblNombre.setText(listaDatos[i].getNombres());
        viewHolderDatos.lblApellido.setText(listaDatos[i].getApellidos());
    }

    @Override
    public int getItemCount() {
        //Retornara el tamaño de la lista
        return listaDatos.length;
    }

    public class ViewHolderDatos extends PlaceHolderView.ViewHolder {

        ImageView imgPais;
        TextView lblNombre;
        TextView lblApellido;

        public ViewHolderDatos(@NonNull View itemView, final Posicion pos) {
            super(itemView);
            imgPais=itemView.findViewById(R.id.imgContacto);
            lblNombre=itemView.findViewById(R.id.lblNombre);
            lblApellido=itemView.findViewById(R.id.lblApellido);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos.position(getLayoutPosition());
                    //Log.d("Log viewHolder","Click" + getLayoutPosition());
                }
            });
        }


    }
}
