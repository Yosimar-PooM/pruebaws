package com.example.genesis.pruebaws;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.File;

import WebServices.HttpRequest;
import WebServices.SOAP;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    String nombre,apellido,email,movil,fijo,trabajo,base64;
    public static final int CAMERA_PERMISSIONS_REQUEST = 3;
    double lat,lng;
    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    public Fragment1() {
        // Required empty public constructor
    }
    View myFragmentView;
    Button btnCapturar,btnGaleria;
    TextView txtnombre, txtapellidos,txtemail,txtfijo,txttrabajo,txtmovil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView =inflater.inflate(R.layout.fragment_fragment1, container, false);

        //para llamar a un fragmento dentro de otro fragmento
        SupportMapFragment mapFragment = (SupportMapFragment )this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton my_fab = (FloatingActionButton) myFragmentView.findViewById(R.id.btnenviar);
        my_fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                GuardarContacto();
            }
        });
        txtnombre=(TextView)myFragmentView.findViewById(R.id.txtNombres);
        txtapellidos=(TextView)myFragmentView.findViewById(R.id.txtApellidos);
        txtemail=(TextView)myFragmentView.findViewById(R.id.txtCorreo);
        txtfijo=(TextView)myFragmentView.findViewById(R.id.txtFijo);
        txtmovil=(TextView)myFragmentView.findViewById(R.id.txtMovil);
        txttrabajo=(TextView)myFragmentView.findViewById(R.id.txtTrabajo);
        return myFragmentView;
    }
    GoogleMap googleMap;
    Marker marker=null;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        showDefaultLocation();

        //Mostrar posición actual
        googleMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        googleMap.setOnMyLocationClickListener(onMyLocationClickListener);
        enableMyLocationIfPermitted();

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMinZoomPreference(11);

        //Definimos el listener en la misma clase
        googleMap.setOnMapClickListener(this);
    }

    //Metodos para mostrar posición por defecto
    private void showDefaultLocation() {

        LatLng latLng = new LatLng(-1.0127, -79.4695);
        CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(latLng,15);
        googleMap.moveCamera(cameraUpdate);
    }
    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else if (googleMap != null) {
            googleMap.setMyLocationEnabled(true);
        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    googleMap.setMinZoomPreference(5);
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {

                    googleMap.setMinZoomPreference(5);

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));

                    circleOptions.radius(200);
                    circleOptions.fillColor(Color.BLUE);
                    circleOptions.strokeWidth(6);

                    googleMap.addCircle(circleOptions);
                    if(marker!=null){
                        marker.remove();
                    }
                    marker= googleMap.addMarker(new MarkerOptions().title("Ubicación actual").
                            position(new LatLng(
                                    location.getLatitude(),
                                    location.getLongitude())));

                }
            };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }

    private void GuardarContacto()
    {

        nombre=txtnombre.getText().toString();

        apellido=txtapellidos.getText().toString();

        email=txtemail.getText().toString();

        fijo=txtfijo.getText().toString();

        movil=txtmovil.getText().toString();

        trabajo=txttrabajo.getText().toString();

        new webService().execute(nombre,apellido,email,movil,trabajo,fijo);
        Intent intent=new Intent(getContext(),MainActivity.class);
        startActivity(intent);
    }


    int caso=0;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imagen=(ImageView)myFragmentView.findViewById(R.id.imagen);
        if(caso==1 && data != null){
                Bitmap bitmap=(Bitmap)data.getExtras().get("data");

                imagen.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] bytes=byteArrayOutputStream.toByteArray();

                base64=Base64.encodeToString(bytes,Base64.DEFAULT);
        }
        else
            if(caso==2 && data!=null){
                Uri path=data.getData();
                imagen.setImageURI(path);
                imagen.buildDrawingCache();
                Bitmap bitmap=imagen.getDrawingCache();
                ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] bytes=byteArrayOutputStream.toByteArray();

                base64=Base64.encodeToString(bytes,Base64.DEFAULT);
            }


    }

    @Override
    public void onMapClick(final LatLng latLng) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                Projection proj = googleMap.getProjection();
                Point coord = proj.toScreenLocation(point);

                if(marker!=null)marker.remove();
                marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(point.latitude, point.longitude))
                        .title("Ubicación"));
                TextView txtLoc=(TextView)myFragmentView.findViewById(R.id.txtloc);
                txtLoc.setText("Coordenada seleccionada");
                lat=marker.getPosition().latitude;
                lng=marker.getPosition().longitude;

            }
        });



    }

    static String respuesta="";
    private class webService extends AsyncTask<String,String,String> {
        static final String NAMESPACE="http://WebServices/";
        static final String METODNAME="insertarContacto";
        static final String URL="http://192.168.1.50:8084/Webservice-Soap-Restful/wscontactos?wsdl";
        static final String SOAP_ACTION=NAMESPACE+METODNAME;
        @Override
        protected String doInBackground(String... strings) {
            SoapObject request= new SoapObject(NAMESPACE,METODNAME);
            request.addProperty("nombre",strings[0]);
            request.addProperty("apellido",strings[1]);
            request.addProperty("email",strings[2]);
            request.addProperty("movil",strings[3]);
            request.addProperty("fijo",strings[4]);
            request.addProperty("trabajo",strings[5]);
            SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte= new HttpTransportSE(URL);
            Log.d("transporte",request.toString());
            String resultado="";
            try{
                transporte.call(SOAP_ACTION,envelope);
                SoapPrimitive response=(SoapPrimitive)envelope.getResponse();
                Log.d("Resp",response.toString());
                resultado=response.toString();
            }
            catch (Exception e){
                Log.d("Error",e.getMessage());
            }
            respuesta=resultado;
            return resultado;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

    }
}
