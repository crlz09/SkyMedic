package mssolutions.skymedic1;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class especialidad extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView textView;

    LinearLayout layoutDoctores, doctores;
    public ArrayList<String> ciudades=new ArrayList<>();
    public ArrayList<String> nuevasciudades=new ArrayList<>();

    private String urlJsonArry = "http://arsus.nnbiocliniccenter.com.ve/json/last5.php?especialidad=";
    private String urlnombreydesc="http://arsus.nnbiocliniccenter.com.ve/json/last5.php?especi=";

    private static String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    private TextView txtResponse;

    // temporary string to show the parsed response
    private String jsonResponse;

    String descripcion, iconoS, UrlFinal,queryy;

    boolean Busqueda = false;

    int iconoX;

    final int iconos[] = {R.mipmap.alergologia, R.mipmap.anestesiologia, R.mipmap.angiologia, R.mipmap.bariatria,
            R.mipmap.cardiologia, R.mipmap.cirugiaestetica, R.mipmap.cirugiageneral, R.mipmap.cirugiamaxi, R.mipmap.cirugiaplastica,
            R.mipmap.dermatologia, R.mipmap.endocrinologia, R.mipmap.endoscopia, R.mipmap.fisiatria, R.mipmap.gastroenterologia,
            R.mipmap.geriatria, R.mipmap.ginecologia, R.mipmap.hematologia, R.mipmap.homeopatia, R.mipmap.infectologia, R.mipmap.inmunologia, R.mipmap.medicinadeporte,
            R.mipmap.medicinaforense, R.mipmap.medicinageneral, R.mipmap.medicinalaboral, R.mipmap.medicinanuclear, R.mipmap.microcirugia,
            R.mipmap.nefrologia, R.mipmap.neonatologia, R.mipmap.neumonologia, R.mipmap.neurocirugia, R.mipmap.neurologia, R.mipmap.nutricion,
            R.mipmap.odontologia, R.mipmap.oftalmologia, R.mipmap.oncologia, R.mipmap.ortopedia, R.mipmap.otorrino, R.mipmap.patologia,
            R.mipmap.pediatria, R.mipmap.perinatologia, R.mipmap.proctologia, R.mipmap.psiquiatria, R.mipmap.radiologia, R.mipmap.reumatologia,
            R.mipmap.traumatologia, R.mipmap.urologia, R.mipmap.veterinaria};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.especialidad);
        textView  = (TextView) findViewById(R.id.textView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Actualizando");
        pDialog.setCancelable(false);

        LinearLayout filtro=(LinearLayout) findViewById(R.id.filtro);
        filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segundodialogo(view);
            }
        });

        UrlFinal = getIntent().getExtras().getString("urlfinal123");

        Busqueda = getIntent().getExtras().getBoolean("busqueda");
        descripcion = getIntent().getExtras().getString("Descripcion");
        queryy = getIntent().getExtras().getString("Query");
        String desc=descripcion;

          try{ if (descripcion.contains(" ")){

              descripcion=descripcion.replace(" ","%20");
          }

          } catch (Exception e) {

          }


        iconoX = getIntent().getExtras().getInt("icono");

        ImageView iconoEspe = (ImageView)findViewById(R.id.ima);
        iconoEspe.setImageResource(iconos[iconoX]);
        TextView especializacion = (TextView) findViewById(R.id.especializacion);
        especializacion.setText(desc);
       // Toast.makeText(this, ""+descripcion, Toast.LENGTH_LONG).show();

        layoutDoctores = (LinearLayout)findViewById(R.id.especialidadunica);

        if (!Busqueda) {
           // Toast.makeText(this, ""+descripcion, Toast.LENGTH_SHORT).show();
           makeJsonArrayRequest(urlJsonArry+descripcion, "global");
            //Log.d("Entra en el ","falso");
        }else{
            makeJsonArrayRequest(UrlFinal, "global");
            //Log.d("Entra en el ","verdadero");
        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

                try{query=query.replace(" ","%20");

                }catch (Exception ex){}


                String urlfinal= urlnombreydesc+descripcion+"&nombre="+query;

                Intent ListSong = new Intent(getApplicationContext(), especialidad.class);
                ListSong.putExtra("urlfinal123",urlfinal);
                ListSong.putExtra("busqueda",true);
                ListSong.putExtra("Descripcion",descripcion);
                ListSong.putExtra("icono",iconoX);
                ListSong.putExtra("Query",query);
                finish();
                startActivity(ListSong);



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


            Intent ListSong = new Intent(getApplicationContext(), Activity_especialidades.class);
            startActivity(ListSong);


        } else if (id == R.id.nav_clinicas) {
            Intent ListSong = new Intent(getApplicationContext(), activity_clinica.class);
            ListSong.putExtra("Tipo","CLINICA");
            startActivity(ListSong);
        } else if (id == R.id.nav_farmacias) {
            Intent ListSong = new Intent(getApplicationContext(), activity_clinica.class);
            ListSong.putExtra("Tipo","FARMACIA");
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

    public String ultimos[][] = new String [100][100];

    public void makeJsonArrayRequest(final String urlconsulta, final String tipo) {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(urlconsulta,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            LinearLayout[] linea;

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response
                                        .get(i);
                                String id= person.getString("idDoc");
                                final String nombre = person.getString("nombreDoc");
                                final String especialidad = person.getString("especialidadDoc");
                                final String ciudad = person.getString("ciudadDoc");
                                final String sexo = person.getString("sexoDoc");
                                final String direccion= person.getString("direccionDoc");
                                final String telefono=person.getString("telefonoDoc");
                                final String correo = person.getString("correoDoc");
                                ciudades.add(person.getString("ciudadDoc"));

                                    doctores = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_doctor,null);

                                    ImageView imagen = (ImageView) doctores.findViewById(R.id.imaespec1);
                                    TextView textoNombre = (TextView) doctores.findViewById(R.id.nombrespec1);
                                    TextView especi = (TextView) doctores.findViewById(R.id.espec1);

                                    String auxii = "Hombre";

                                    switch (sexo){
                                        case "Hombre":
                                            imagen.setImageResource(R.mipmap.doctor);
                                            break;

                                        case "Mujer":
                                            imagen.setImageResource(R.mipmap.doctora);
                                            break;
                                    }

                                    doctores.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (sexo.equals("Hombre")){
                                                Intent vete = new Intent(getApplicationContext(),layout_deschombre.class);
                                                vete.putExtra("nombre",nombre);
                                                vete.putExtra("especialidad",especialidad);
                                                vete.putExtra("direccion",direccion);
                                                vete.putExtra("telefono",telefono);
                                                vete.putExtra("correo",correo);
                                                vete.putExtra("ciudad",ciudad);
                                                startActivity(vete);
                                            }else {
                                                Intent vete = new Intent(getApplicationContext(),layout_deschombre.class);
                                                vete.putExtra("nombre",nombre);
                                                vete.putExtra("especialidad",especialidad);
                                                vete.putExtra("direccion",direccion);
                                                vete.putExtra("telefono",telefono);
                                                vete.putExtra("correo",correo);
                                                vete.putExtra("ciudad",ciudad);
                                                startActivity(vete);
                                            }


                                        }
                                    });

                                    textoNombre.setText(nombre);
                                    especi.setText(ciudad);

                                    layoutDoctores.addView(doctores);
                                }




                        } catch (JSONException e) {
                            e.printStackTrace();


                            makeJsonObjectRequest(urlconsulta);

                        }

                        hidepDialog();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                hidepDialog();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Intent abreme = new Intent(getApplicationContext(),activity_internet.class);
                    finish();
                    startActivity(abreme);
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                    Intent abreme = new Intent(getApplicationContext(),activity_internet.class);
                    finish();
                    startActivity(abreme);
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Intent abreme = new Intent(getApplicationContext(),activity_internet.class);
                    finish();
                    startActivity(abreme);
                } else if (error instanceof ParseError) {
                    Intent abreme = new Intent(getApplicationContext(),activity_error.class);
                    finish();
                    abreme.putExtra("Consulta",queryy);
                    startActivity(abreme);
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    Intent abreme = new Intent(getApplicationContext(),activity_internet.class);
                    finish();
                    startActivity(abreme);
                    message = "Connection TimeOut! Please check your internet connection.";
                }
            }
        });

        // Adding request to request queue
        // AppController.getInstance().addToRequestQueue(req);
        req.setShouldCache(false);
        Volley.newRequestQueue(this).add(req);

    }

    private void makeJsonObjectRequest(String url) {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String id= response.getString("idDoc");
                    final String nombre = response.getString("nombreDoc");
                    final String especialidad = response.getString("especialidadDoc");
                    final String ciudad = response.getString("ciudadDoc");
                    final String sexo = response.getString("sexoDoc");
                    final String direccion= response.getString("direccionDoc");
                    final String telefono=response.getString("telefonoDoc");
                    final String correo = response.getString("correoDoc");
                    ciudades.add(response.getString("ciudadDoc"));



                    doctores = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_doctor,null);

                    ImageView imagen = (ImageView) doctores.findViewById(R.id.imaespec1);
                    TextView textoNombre = (TextView) doctores.findViewById(R.id.nombrespec1);
                    TextView especi = (TextView) doctores.findViewById(R.id.espec1);


                    String auxii = "Hombre";

                    switch (sexo){
                        case "Hombre":
                            imagen.setImageResource(R.mipmap.doctor);
                            especi.setTextColor(Color.parseColor("#FF6DBCD4"));
                            break;

                        case "Mujer":
                            imagen.setImageResource(R.mipmap.doctora);
                            especi.setTextColor(Color.parseColor("#FFFE797A"));
                            break;
                    }

                    doctores.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (sexo.equals("Hombre")){
                                Intent vete = new Intent(getApplicationContext(),layout_deschombre.class);
                                vete.putExtra("nombre",nombre);
                                vete.putExtra("especialidad",especialidad);
                                vete.putExtra("direccion",direccion);
                                vete.putExtra("telefono",telefono);
                                vete.putExtra("correo",correo);
                                vete.putExtra("ciudad",ciudad);
                                startActivity(vete);
                            }else {
                                Intent vete = new Intent(getApplicationContext(),layout_deschombre.class);
                                vete.putExtra("nombre",nombre);
                                vete.putExtra("especialidad",especialidad);
                                vete.putExtra("direccion",direccion);
                                vete.putExtra("telefono",telefono);
                                vete.putExtra("correo",correo);
                                vete.putExtra("ciudad",ciudad);
                                startActivity(vete);
                            }

                                              }
                    });

                    textoNombre.setText(nombre);
                    especi.setText(ciudad);

                    layoutDoctores.addView(doctores);


                } catch (JSONException e) {
                    e.printStackTrace();

                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                // hide the progress dialog
                hidepDialog();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Intent abreme = new Intent(getApplicationContext(),activity_internet.class);
                    finish();
                    startActivity(abreme);
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                    Intent abreme = new Intent(getApplicationContext(),activity_internet.class);
                    finish();
                    startActivity(abreme);
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Intent abreme = new Intent(getApplicationContext(),activity_internet.class);
                    finish();
                    startActivity(abreme);
                } else if (error instanceof ParseError) {
                    Intent abreme = new Intent(getApplicationContext(),activity_error.class);
                    finish();
                    abreme.putExtra("Consulta",queryy);
                    startActivity(abreme);
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    Intent abreme = new Intent(getApplicationContext(),activity_internet.class);
                    finish();
                    startActivity(abreme);
                    message = "Connection TimeOut! Please check your internet connection.";
                }
            }
        });

        // Adding request to request queue
        jsonObjReq.setShouldCache(false);
        Volley.newRequestQueue(this).add(jsonObjReq);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void doctor(String nombre, String especialidad, String direccion,
                       String telefono, String correo, String sexo, String ciudad) {

        if (sexo.equals("Mujer")) {
            Intent abredoc = new Intent(getApplicationContext(), layout_descmujer.class);

            abredoc.putExtra("nombre", nombre);
            abredoc.putExtra("especialidad", especialidad);
            abredoc.putExtra("direccion", direccion);
            abredoc.putExtra("telefono", telefono);
            abredoc.putExtra("correo", correo);
            abredoc.putExtra("ciudad", ciudad);
            startActivity(abredoc);
        } else {
            Intent abredoc = new Intent(getApplicationContext(), layout_deschombre.class);
            abredoc.putExtra("nombre", nombre);
            abredoc.putExtra("especialidad", especialidad);
            abredoc.putExtra("direccion", direccion);
            abredoc.putExtra("telefono", telefono);
            abredoc.putExtra("correo", correo);
            abredoc.putExtra("ciudad", ciudad);
            startActivity(abredoc);
        }


    }


    public void segundodialogo (View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Filtrar por:");

        for (String ciudad: ciudades) {
            if (nuevasciudades.contains(ciudad)){

            }else {

                nuevasciudades.add(ciudad);
                            }
        }

        final CharSequence[] items = new CharSequence[nuevasciudades.size()];
        for (int i=0; i<nuevasciudades.size();i++){
            items[i]=nuevasciudades.get(i);
        }


                builder.setTitle("Seleccione una opción:")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     //   Toast.makeText(especialidad.this, ""+items[which], Toast.LENGTH_SHORT).show();
                        //url de ubicacion y a jsonear!
                        final CharSequence[] items = new CharSequence[nuevasciudades.size()];
                        for (int i=0; i<nuevasciudades.size();i++){
                            items[i]=nuevasciudades.get(i).replace(" ","%20");
                        }

                        String laurl="http://arsus.nnbiocliniccenter.com.ve/json/last5.php?esp="+descripcion+"&ciudad="+items[which];

                        makeJsonArrayRequest(laurl,"ubicacion");

                        Intent ListSong = new Intent(getApplicationContext(), especialidad.class);
                        ListSong.putExtra("urlfinal123",laurl);
                        ListSong.putExtra("busqueda",true);
                        ListSong.putExtra("Descripcion",descripcion);
                        ListSong.putExtra("icono",iconoX);

                        startActivity(ListSong);


                        dialog.dismiss();
                    }
                });


        Dialog dialog = builder.create();
        dialog.show();
        ;

    }


}