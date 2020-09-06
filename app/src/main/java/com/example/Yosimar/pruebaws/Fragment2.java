package com.example.genesis.pruebaws;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.PlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Adaptadores.Adaptador_Contactos;
import Adaptadores.Posicion;
import Clases.Contactos;
import WebServices.Asynchtask;
import WebServices.WebService;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment implements Asynchtask {


    public Fragment2() {
        // Required empty public constructor
    }

    PlaceHolderView recycler;
    Contactos[] listaDatos;
    EnviarMensaje em;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://192.168.1.50:8084/Webservice-Soap-Restful/webresources/wslista",datos,getActivity(),this);
        ws.execute("");

        View view= inflater.inflate(R.layout.fragment_fragment2, container, false);
        recycler=view.findViewById(R.id.idplaceholder);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject jsonObj= new JSONObject(result);
        JSONArray jsonArray= jsonObj.getJSONArray("result");
        listaDatos= new Contactos[jsonArray.length()];
        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject c = jsonArray.getJSONObject(i);
            JSONObject numeros=c.getJSONObject("numeros");
            listaDatos[i]=new Contactos(c.getInt("id_contacto"),c.getString("nombres"),
                    c.getString("apellidos"),c.getString("email"),
                    numeros.getString("movil"),numeros.getString("fijo"),
                    numeros.getString("trabajo"));
        }
        Adaptador_Contactos adaptador_paises= new Adaptador_Contactos(listaDatos,getContext());
        recycler.setAdapter(adaptador_paises);
        adaptador_paises.setPosicion(new Posicion() {
            @Override
            public int position(int pos) {
                Contactos aux=listaDatos[pos];
                em.enviarDatos(aux);
                return pos;
            }
        });
    }

    //Comunicación con la actividad, es necesario en este caso para asignar la interfaz
    public void onAttach(Activity mainActivity){
        super.onAttach(mainActivity);
        em=(EnviarMensaje)mainActivity; //Con esto nos comunicamos con la interfaz
    }
}
