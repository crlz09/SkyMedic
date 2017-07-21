package mssolutions.skymedic1;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Carlosm on 18/07/2017.
 */

public class activity_clinica extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView textView;

    LinearLayout layoutDoctores, doctores;
    public ArrayList<String> ciudades = new ArrayList<>();
    public ArrayList<String> nuevasciudades = new ArrayList<>();
    // json array response url
    private String urlJsonArry = "http://arsus.nnbiocliniccenter.com.ve/json/last5.php?especialidad=";
    private String urlnombreydesc = "http://arsus.nnbiocliniccenter.com.ve/json/last5.php?especi=";

    private static String TAG = MainActivity.class.getSimpleName();
    //  private Button btnMakeObjectRequest, btnMakeArrayRequest;

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView txtResponse;

    // temporary string to show the parsed response
    private String jsonResponse;

    String descripcion, iconoS, UrlFinal;

    boolean Busqueda = false;

    LinearLayout clinicas, layoutclinicas;

    String tipo;

    String urlconsulta= "http://arsus.nnbiocliniccenter.com.ve/json/last5.php?tipo=";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinicas);
        textView = (TextView) findViewById(R.id.textView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Actualizando");
        pDialog.setCancelable(false);

        LinearLayout filtro = (LinearLayout) findViewById(R.id.filtro);
        filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segundodialogo(view);
            }
        });


        if(getIntent().getExtras().getString("Tipo")==null){
            UrlFinal = getIntent().getExtras().getString("urlfinal123");
            tipo=getIntent().getExtras().getString("tipito");
        }else {
            tipo = getIntent().getExtras().getString("Tipo");
            UrlFinal=urlconsulta+tipo;
        }


        Busqueda = getIntent().getExtras().getBoolean("busqueda");
        String desc=descripcion;

        try{ if (descripcion.contains(" ")){

            descripcion=descripcion.replace(" ","%20");
        }

        } catch (Exception e) {

        }

        layoutclinicas = (LinearLayout) findViewById(R.id.clinicapapa);
        TextView tv = (TextView) findViewById(R.id.titulo);
        ImageView iv = (ImageView) findViewById(R.id.imag);
        if (tipo.equals("CLINICA")){
            tv.setText("CLINICAS");
            iv.setImageResource(R.mipmap.clinicas);
        } else {
            tv.setText("FARMACIAS");
            iv.setImageResource(R.mipmap.farmacias);
        }
       // Toast.makeText(this, ""+UrlFinal, Toast.LENGTH_LONG).show();
        makeJsonArrayRequest(UrlFinal);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (tipo.equals("CLINICA")) {
            navigationView.getMenu().getItem(1).setChecked(true);
        }else   {
            navigationView.getMenu().getItem(2).setChecked(true);
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //permite modificar el hint que el EditText muestra por defecto
        searchView.setQueryHint("Busca doctores, clinicas, farmacias");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //  Toast.makeText(especialidad.this, query.toString(), Toast.LENGTH_SHORT).show();
                //se oculta el EditText
                try {
                    query = query.replace(" ", "%20");

                } catch (Exception ex) {
                }

                String Urlnomytipo= "http://arsus.nnbiocliniccenter.com.ve/json/last5.php?nomcli=";
                String urlfinal = Urlnomytipo + query + "&nombre=" + query + "&type=" + tipo;

                Intent ListSong = new Intent(getApplicationContext(), activity_clinica.class);
                ListSong.putExtra("urlfinal123", urlfinal);
                ListSong.putExtra("tipito",tipo);
                ListSong.putExtra("busqueda", true);
                ListSong.putExtra("Descripcion", descripcion);
             //   ListSong.putExtra("icono", iconoX);
                finish();
                startActivity(ListSong);


                //makeJsonArrayRequest(urlnombreydesc+descripcion+"&nombre="+query, "nombre");
                //Toast.makeText(especialidad.this, ""+urlfinal, Toast.LENGTH_SHORT).show();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //   textView.setText(newText);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_especialidades) {

            //Toast.makeText(MainActivity.this, "layout especialidades", Toast.LENGTH_SHORT).show();

            Intent ListSong = new Intent(getApplicationContext(), Activity_especialidades.class);
            startActivity(ListSong);


        } else if (id == R.id.nav_clinicas) {

            Intent ListSong = new Intent(getApplicationContext(), layout_clinica.class);
            startActivity(ListSong);

        } else if (id == R.id.nav_farmacias) {
            Intent ListSong = new Intent(getApplicationContext(), layout_clinica.class);
            startActivity(ListSong);
        } else if (id == R.id.nav_suscribase) {
            Intent ListSong = new Intent(getApplicationContext(), layout_contacto.class);
            ListSong.putExtra("IDMENU", id);
            ListSong.putExtra("Opcion", "Suscribase");
            startActivity(ListSong);

        } else if (id == R.id.nav_opinion) {
            Intent ListSong = new Intent(getApplicationContext(), layout_contacto.class);
            ListSong.putExtra("IDMENU", id);
            ListSong.putExtra("Opcion", "Opinion");
            startActivity(ListSong);

        } else if (id == R.id.nav_acercade) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public void segundodialogo(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Filtrar por:");

        for (String ciudad : ciudades) {
            if (nuevasciudades.contains(ciudad)) {

            } else {

                nuevasciudades.add(ciudad);
            }
        }

        final CharSequence[] items = new CharSequence[nuevasciudades.size()];
        for (int i = 0; i < nuevasciudades.size(); i++) {
            items[i] = nuevasciudades.get(i);
        }


        builder.setTitle("Seleccione una opción:")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //   Toast.makeText(especialidad.this, ""+items[which], Toast.LENGTH_SHORT).show();
                        //url de ubicacion y a jsonear!
                        final CharSequence[] items = new CharSequence[nuevasciudades.size()];
                        for (int i = 0; i < nuevasciudades.size(); i++) {
                            items[i] = nuevasciudades.get(i).replace(" ", "%20");
                        }

                        //String ultimaciudad= ciudad.replace(" ","%20");
                        String laurl;
                        laurl="http://arsus.nnbiocliniccenter.com.ve/json/last5.php?ciudadcli="+items[which]+"&typeof="+tipo;

                        if (descripcion==null){
                             laurl="http://arsus.nnbiocliniccenter.com.ve/json/last5.php?ciudadcli="+items[which]+"&typeof="+tipo;
                        }else {
                             laurl ="http://arsus.nnbiocliniccenter.com.ve/json/last5.php?cicl="+items[which]+"&nocli="+descripcion+
                                    "&typeofc="+tipo;
                        }


                        //  Toast.makeText(especialidad.this, ""+laurl, Toast.LENGTH_LONG).show();
                      //  makeJsonArrayRequest(laurl);

                        Intent ListSong = new Intent(getApplicationContext(), activity_clinica.class);

                        ListSong.putExtra("urlfinal123", laurl);
                        ListSong.putExtra("busqueda", true);
                        ListSong.putExtra("Descripcion", descripcion);
                        ListSong.putExtra("tipito", tipo);
                    //    ListSong.putExtra("icono", iconoX);

                        startActivity(ListSong);


                        dialog.dismiss();
                    }
                });


        Dialog dialog = builder.create();
        dialog.show();
        ;

    }


    //JSON ARRAY

    private void makeJsonArrayRequest(final String url) {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response
                                        .get(i);
                                String id= person.getString("idCli");
                                final String nombre = person.getString("nombreCli");
                                final String img = person.getString("urlCli");
                                final String desc = person.getString("descripcionCli");
                                final String ciudad = person.getString("ciudadCli");
                                final String estado= person.getString("estadoCli");
                                final String latitud = person.getString("latitudCli");
                                final String longitud = person.getString("longitudCli");
                                final String direccion= person.getString("direccionCli");
                                final String telefono=person.getString("telefonoCli");
                                final String correo = person.getString("correoCli");
                                ciudades.add(person.getString("ciudadCli"));

                                clinicas = (LinearLayout) LayoutInflater.from(getApplicationContext())
                                        .inflate(R.layout.clinica, null);
                                ArrayList elementos;

                                ImageView imagen = (ImageView) clinicas.findViewById(R.id.imacli);
                                TextView textoNombre = (TextView) clinicas.findViewById(R.id.nomcli);
                                TextView ciudadcli = (TextView) clinicas.findViewById(R.id.ciudadcli);


                                clinicas.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        //  Toast.makeText(activity_clinica.this, "clinica "+ finalI, Toast.LENGTH_SHORT).show();
                                        //aqui el fucking json que rellena ese pan canilla
                                        Intent ListSong = new Intent(getApplicationContext(),layout_clinica.class);
                                        ListSong.putExtra("Nombre",nombre);
                                        ListSong.putExtra("Imagen",img);
                                        ListSong.putExtra("Ciudad",ciudad);
                                        ListSong.putExtra("Descripcion",desc);
                                        ListSong.putExtra("Estado",estado);
                                        ListSong.putExtra("Direccion",direccion);
                                        ListSong.putExtra("Telefono",telefono);
                                        ListSong.putExtra("Correo",correo);
                                        ListSong.putExtra("Latitud",latitud);
                                        ListSong.putExtra("Longitud",longitud);
                                        startActivity(ListSong);
                                    }
                                });
                                textoNombre.setText(nombre);
                                ciudadcli.setText(ciudad+" - " + estado);

                                Picasso.with(getApplicationContext()).load(img).fit().into(imagen);

                                layoutclinicas.addView(clinicas);


                            }

                        } catch (JSONException e) {

                            makeJsonObjectRequest(url);
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override



            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),"internet no hayyy", Toast.LENGTH_SHORT).show();

                hidepDialog();



                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);
                    startActivity(abreme);
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);

                    startActivity(abreme);
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);

                    startActivity(abreme);
                } else if (error instanceof ParseError) {
                    Intent abreme = new Intent(getApplicationContext(),activity_error.class);
                    finish();
                    startActivity(abreme);
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);

                    startActivity(abreme);
                    message = "Connection TimeOut! Please check your internet connection.";
                }

                // Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        // AppController.getInstance().addToRequestQueue(req);
        req.setShouldCache(false);
        Volley.newRequestQueue(this).add(req);
    }

 //FIN DE JSON ARRAY

    //JSON OBJECT
    private void makeJsonObjectRequest(String url) {

        showpDialog();
        //layoutDoctores.removeAllViews();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String id= response.getString("idCli");
                    String nombre = response.getString("nombreCli");
                    String img = response.getString("urlCli");
                    String desc = response.getString("descripcionCli");
                    String ciudad = response.getString("ciudadCli");
                    String estado= response.getString("estadoCli");
                    String latitud = response.getString("latitudCli");
                    String longitud = response.getString("longitudCli");
                    String direccion= response.getString("direccionCli");
                    String telefono=response.getString("telefonoCli");
                    String correo = response.getString("correoCli");

                    clinicas = (LinearLayout) LayoutInflater.from(getApplicationContext())
                            .inflate(R.layout.clinica, null);


                    ImageView imagen = (ImageView) clinicas.findViewById(R.id.imacli);
                    TextView textoNombre = (TextView) clinicas.findViewById(R.id.nomcli);
                    TextView ciudadcli = (TextView) clinicas.findViewById(R.id.ciudadcli);


                    clinicas.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //  Toast.makeText(activity_clinica.this, "clinica "+ finalI, Toast.LENGTH_SHORT).show();
                            //aqui el fucking json que rellena ese pan canilla
                        }
                    });
                    textoNombre.setText(nombre);
                    ciudadcli.setText(ciudad + " - " + estado);

                    Picasso.with(getApplicationContext()).load(img).into(imagen);

                    layoutclinicas.addView(clinicas);




                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                /*Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();*/
                // hide the progress dialog
                hidepDialog();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);

                    startActivity(abreme);
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);

                    startActivity(abreme);
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);

                    startActivity(abreme);
                } else if (error instanceof ParseError) {
                    Intent abreme = new Intent(getApplicationContext(),activity_error.class);
                    finish();
                    startActivity(abreme);
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);

                    startActivity(abreme);
                    message = "Connection TimeOut! Please check your internet connection.";
                }
            }
        });

        // Adding request to request queue
        jsonObjReq.setShouldCache(false);
        Volley.newRequestQueue(this).add(jsonObjReq);
    }



    //FIN JSONO






}