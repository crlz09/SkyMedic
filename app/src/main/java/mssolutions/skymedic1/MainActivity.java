package mssolutions.skymedic1;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
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


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView textView;
    LinearLayout layoutDoctores, doctores;
    public ArrayList<String> ciudades=new ArrayList<>();
     SwipeRefreshLayout refrescar;
public String consulta;
    // json array response url
    private String urlJsonArry = "http://arsus.nnbiocliniccenter.com.ve/json/last5.php?last5";


    private static String TAG = MainActivity.class.getSimpleName();

    // Progress dialog
    private ProgressDialog pDialog;

    // temporary string to show the parsed response
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Actualizando");
        pDialog.setCancelable(false);

        textView  = (TextView) findViewById(R.id.textView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        refrescar = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        refrescar.setColorSchemeColors(Color.parseColor("#02CCEC"));

        refrescar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                try {
                    makeJsonArrayRequest(urlJsonArry);
                    refrescar.setRefreshing(false);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        try {
            makeJsonArrayRequest(urlJsonArry);
                } catch (Exception e) {
            throw new RuntimeException(e);
                 }

        //dr1
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.dr1);

        layout1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final String nombredr = ultimos[0][1];
                final String especdr=ultimos[0][2];
                final String direccion=ultimos[0][5];
                final String telefono=ultimos[0][6];
                final String correo=ultimos[0][7];
                final String sexo=ultimos[0][4];
                final String ciudad=ultimos[0][3];
                doctor(nombredr,especdr,direccion,telefono,correo,sexo, ciudad);

            }
        });
        //dr2
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.dr2);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombredr = ultimos[1][1];
                final String especdr=ultimos[1][2];
                final String direccion=ultimos[1][5];
                final String telefono=ultimos[1][6];
                final String correo=ultimos[1][7];
                final String sexo=ultimos[1][4];
                final String ciudad=ultimos[1][3];
                doctor(nombredr,especdr,direccion,telefono,correo,sexo, ciudad);
            }
        });

        //3
        LinearLayout layout3 = (LinearLayout) findViewById(R.id.dr3);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombredr = ultimos[2][1];
                final String especdr=ultimos[2][2];
                final String direccion=ultimos[2][5];
                final String telefono=ultimos[2][6];
                final String correo=ultimos[2][7];
                final String sexo=ultimos[2][4];
                final String ciudad=ultimos[2][3];
                doctor(nombredr,especdr,direccion,telefono,correo,sexo, ciudad);
            }
        });
            //4
        LinearLayout layout4 = (LinearLayout) findViewById(R.id.dr4);
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombredr = ultimos[3][1];
                final String especdr=ultimos[3][2];
                final String direccion=ultimos[3][5];
                final String telefono=ultimos[3][6];
                final String correo=ultimos[3][7];
                final String sexo=ultimos[3][4];
                final String ciudad=ultimos[3][3];
                doctor(nombredr,especdr,direccion,telefono,correo,sexo, ciudad);
            }
        });

        LinearLayout layout5 = (LinearLayout)findViewById(R.id.dr5);
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombredr = ultimos[4][1];
                final String especdr=ultimos[4][2];
                final String direccion=ultimos[4][5];
                final String telefono=ultimos[4][6];
                final String correo=ultimos[4][7];
                final String sexo=ultimos[4][4];
                final String ciudad=ultimos[4][3];
                doctor(nombredr,especdr,direccion,telefono,correo,sexo, ciudad);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                ab.setTitle("SKYMEDIC");
                ab.setMessage("¿Abandonar SkyMedic?").setPositiveButton("Si", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    onBackPressed();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

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
                consulta=query;
                try{query=query.replace(" ","%20");

                }catch (Exception ex){}
                String urlfinal= "http://arsus.nnbiocliniccenter.com.ve/json/last5.php?nombreee="+query;

               // makeJsonArrayRequest(urlfinal);
                Intent busca = new Intent(getApplicationContext(),activity_busqueda.class);
                busca.putExtra("Url",urlfinal);
                busca.putExtra("Consulta",consulta);
                startActivity(busca);
                //se oculta el EditText
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
            ListSong.putExtra("IDMENU",id);
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
            Intent ListSong = new Intent(getApplicationContext(), layout_acercade.class);
            ListSong.putExtra("IDMENU",id);
            startActivity(ListSong);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Method to make json array request where response starts with [
     * */
    public String ultimos[][] = new String [5][8];

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
                                String id= person.getString("idDoc");
                                String nombre = person.getString("nombreDoc");
                                String especialidad = person.getString("especialidadDoc");
                                String ciudad = person.getString("ciudadDoc");
                                String sexo = person.getString("sexoDoc");
                                String direccion= person.getString("direccionDoc");
                                String telefono=person.getString("telefonoDoc");
                                String correo = person.getString("correoDoc");

                                ultimos[i][0]= id;
                                ultimos[i][1]= nombre;
                                ultimos[i][2]= especialidad;
                                ultimos[i][3]= ciudad;
                                ultimos[i][4]=sexo;
                                ultimos[i][5]=direccion;
                                ultimos[i][6]=telefono;
                                ultimos[i][7]=correo;
                                                            }
                            //Toast.makeText(MainActivity.this, ""+ultimos[0][0], Toast.LENGTH_SHORT).show();
                            asignar(0,ultimos[0][1],ultimos[0][2],ultimos[0][3],R.id.nomdr1,R.id.especdr1,
                                    R.id.imaespec1);//1
                            asignar(1,ultimos[1][1],ultimos[1][2],ultimos[1][3],R.id.nomdr2,R.id.especdr2,
                                    R.id.imaespec2);//2
                            asignar(2,ultimos[2][1],ultimos[2][2],ultimos[2][3],R.id.nomdr3,R.id.especdr3,
                                    R.id.imaespec3);//3
                            asignar(3,ultimos[3][1],ultimos[3][2],ultimos[3][3],R.id.nomdr4,R.id.especdr4,
                                    R.id.imaespec4);//4
                            asignar(4,ultimos[4][1],ultimos[4][2],ultimos[4][3],R.id.nomdr5,R.id.especdr5,
                                    R.id.imaespec5);

                                    } catch (JSONException e) {

                            makeJsonObjectRequest(url);
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
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);
                    finish();
                    startActivity(abreme);
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);
                    finish();
                    startActivity(abreme);
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);
                    finish();
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
                    finish();
                    startActivity(abreme);
                    message = "Connection TimeOut! Please check your internet connection.";
                }

               // Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();

            }
        });

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


public void asignar(int indice, String nombre, String especialidad, String ciudad, int residN,
                    int residE, int residI)
{

    TextView textN = (TextView) findViewById(residN);
    TextView textE = (TextView) findViewById(residE);
    ImageView imag = (ImageView) findViewById(residI);

    if (ultimos[indice][4].equals("Mujer")){

        textE.setTextColor(Color.parseColor("#FFFE797A"));
        imag.setImageResource(R.mipmap.doctora);
    }else {imag.setImageResource(R.mipmap.doctor);
        textE.setTextColor(Color.parseColor("#FF6DBCD4"));}

    textN.setText(nombre);
    textE.setText(especialidad+" - "+ciudad);


}

public void doctor(String nombre, String especialidad, String direccion,
                   String telefono, String correo, String sexo, String ciudad){

    if(sexo.equals("Mujer")){
        Intent abredoc = new Intent(getApplicationContext(), layout_descmujer.class);
        abredoc.putExtra("nombre", nombre);
        abredoc.putExtra("especialidad", especialidad);
        abredoc.putExtra("direccion", direccion);
        abredoc.putExtra("telefono", telefono);
        abredoc.putExtra("correo", correo);
        abredoc.putExtra("ciudad", ciudad);
        startActivity(abredoc);
    }else{
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

                } catch (JSONException e) {
                    e.printStackTrace();
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
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);
                    finish();
                    startActivity(abreme);
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);
                    finish();
                    startActivity(abreme);
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Intent abreme = new Intent(getApplicationContext(),error_main.class);
                    finish();
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



}
