package mssolutions.skymedic1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

/**
 * Created by Carlosm on 18/07/2017.
 */

public class activity_clinica extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    LinearLayout clinicas, layoutclinicas;
    private static String TAG = activity_clinica.class.getSimpleName();
    String tipo;
    private ProgressDialog pDialog;
    String urlconsulta= "http://arsus.nnbiocliniccenter.com.ve/json/last5.php?tipo=";
    private TextView txtResponse;

    // temporary string to show the parsed response
    private String jsonResponse;


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setContentView(R.layout.clinicas);
            tipo = getIntent().getExtras().getString("Tipo");
            layoutclinicas = (LinearLayout) findViewById(R.id.clinicapapa);

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Actualizando");
            pDialog.setCancelable(false);
        makeJsonArrayRequest(urlconsulta+tipo);









            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            if (tipo.equals("CLINICA")) {
                navigationView.getMenu().getItem(1).setChecked(true);
            } else {
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
                try{query=query.replace(" ","%20");

                }catch (Exception ex){}

/*
                String urlfinal= urlnombreydesc+descripcion+"&nombre="+query;

                Intent ListSong = new Intent(getApplicationContext(), especialidad.class);
                ListSong.putExtra("urlfinal123",urlfinal);
                ListSong.putExtra("busqueda",true);
                ListSong.putExtra("Descripcion",descripcion);
                ListSong.putExtra("icono",iconoX);
                finish();
                startActivity(ListSong);*/


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
        }  else if (id == R.id.nav_suscribase) {
            Intent ListSong = new Intent(getApplicationContext(),layout_contacto.class);
            ListSong.putExtra("IDMENU",id);
            ListSong.putExtra("Opcion","Suscribase");
            startActivity(ListSong);

        } else if (id == R.id.nav_opinion) {
            Intent ListSong = new Intent(getApplicationContext(), layout_contacto.class);
            ListSong.putExtra("IDMENU",id);
            ListSong.putExtra("Opcion","Opinion");
            startActivity(ListSong);

        } else if (id == R.id.nav_acercade) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                                String nombre = person.getString("nombreCli");
                                String img = person.getString("urlCli");
                                String desc = person.getString("descripcionCli");
                                String ciudad = person.getString("ciudadCli");
                                String estado= person.getString("estadoCli");
                                String latitud = person.getString("latitudCli");
                                String longitud = person.getString("longitudCli");
                                String direccion= person.getString("direccionCli");
                                String telefono=person.getString("telefonoCli");
                                String correo = person.getString("correoCli");

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
                                ciudadcli.setText(ciudad+" - " + estado);

                                Picasso.with(getApplicationContext()).load(img).into(imagen);

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


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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


