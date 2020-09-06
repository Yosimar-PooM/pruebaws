package com.example.genesis.pruebaws;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import Clases.Contactos;

public class MainActivity extends AppCompatActivity  implements EnviarMensaje{

    String nombre="Lorena",apellido="Mar",email="lore@yahoo.es",movil="123",trabajo="123",fijo="123",base64="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";
    double lat=123,lng=123;
    DrawerLayout drawerLayout;
    NavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.img_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         navView = (NavigationView) findViewById(R.id.navview);

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                fragment = new Fragment1();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                fragment = new Fragment2();
                                fragmentTransaction = true;
                                break;
                        }
                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();
                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void enviarDatos(Contactos mensaje) {
        Fragmento3 fragmento3= new Fragmento3();
        Intent intent= new Intent();
        Bundle b= new Bundle();
        b.putString("id_contacto",String.valueOf(mensaje.getId()));
        b.putString("nombres",String.valueOf(mensaje.getNombres()));
        b.putString("apellidos",String.valueOf(mensaje.getApellidos()));
        b.putString("movil",String.valueOf(mensaje.getMovil()));
        b.putString("fijo",String.valueOf(mensaje.getFijo()));
        b.putString("trabajo",String.valueOf(mensaje.getTrabajo()));
        fragmento3.setArguments(b);
        boolean fragmentTransaction = true;
        if(fragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragmento3)
                    .commit();
        }
    }

    @Override
    public void enviarFragmento2(Contactos mensaje) {

    }
}
