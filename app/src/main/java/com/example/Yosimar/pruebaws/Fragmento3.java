package com.example.genesis.pruebaws;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmento3 extends Fragment implements OnMapReadyCallback {


    public Fragmento3() {
        // Required empty public constructor
    }
    RelativeLayout relativeLayout;
    View myFragmentView;
    TextView txt_nombre,txt_apellido,txt_movil, txt_fijo, txt_trabajo;
    Marker marcador=null;
    double lat=0,lng=0;
    GoogleMap googleMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView= inflater.inflate(R.layout.fragment_fragmento3, container, false);
        //para llamar a un fragmento dentro de otro fragmento
        SupportMapFragment mapFragment = (SupportMapFragment )this.getChildFragmentManager().findFragmentById(R.id.map_2);
        mapFragment.getMapAsync(this);
        Bundle bundle = this.getArguments();
        txt_nombre=(TextView)myFragmentView.findViewById(R.id.txt_nombre);
        txt_nombre.setText(bundle.get("nombres").toString());
        txt_apellido=(TextView)myFragmentView.findViewById(R.id.txt_apellido);
        txt_apellido.setText(bundle.get("apellidos").toString());
        txt_movil=(TextView) myFragmentView.findViewById(R.id.txt_móvil);
        txt_movil.setText(bundle.get("movil").toString());
        txt_fijo=(TextView) myFragmentView.findViewById(R.id.txt_fijo);
        txt_fijo.setText(bundle.get("fijo").toString());
        txt_trabajo=(TextView) myFragmentView.findViewById(R.id.txt_trabajo);
        txt_trabajo.setText(bundle.get("trabajo").toString());

        Button boton=(Button)myFragmentView.findViewById(R.id.btnLamar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] opciones={txt_movil.getText().toString(),txt_fijo.getText().toString(),txt_trabajo.getText().toString()};
                final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
                alertOpciones.setTitle("Seleccione una Opción");

                alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (opciones[i].equals(txt_movil.getText().toString())){
                            llamada(txt_movil.getText().toString());
                        }else{
                            if (opciones[i].equals(txt_fijo.getText().toString())){
                                llamada(txt_fijo.getText().toString());
                            }else{
                                llamada(txt_trabajo.getText().toString());
                            }
                        }
                    }
                });
                alertOpciones.show();
            }
        });

        return myFragmentView;
    }
    private void llamada(String numero){
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+numero));
        startActivityForResult(i,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        //Cambiar la vista del mapa
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Habilitar botones del zoom
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        marcador=googleMap.addMarker(new MarkerOptions().title("Ubicación de "+txt_nombre.getText().toString()).position(
                new LatLng(lat,lng)
        ));

        //Movernos en el mapa
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(latLng,15);
        googleMap.moveCamera(cameraUpdate);
    }
}
