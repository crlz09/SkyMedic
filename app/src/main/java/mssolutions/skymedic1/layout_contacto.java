package mssolutions.skymedic1;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import mssolutions.skymedic1.app.correo;

/**
 * Created by marci on 14/6/2017.
 */

public class layout_contacto extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    correo nuevocorreo= new correo();

    String asunto, Nnombre, Ncorreo, Ntelefono, Nmensaje;

    // Progress dialog
    private ProgressDialog pDialog;

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contacto);


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Por favor espera...");
        pDialog.setCancelable(false);
        asunto= getIntent().getExtras().getString("Opcion");

        Button enviar = (Button) findViewById(R.id.button);
        final EditText ETnombre = (EditText) findViewById(R.id.ETnombre);
        final EditText ETcorreo = (EditText) findViewById(R.id.ETcorreo);
        final EditText ETtelefono = (EditText) findViewById(R.id.ETtelefono);
        final EditText ETmensaje = (EditText) findViewById(R.id.ETmensaje);

        final EnviarCorreo enviarCorreo = new EnviarCorreo();



        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isOnline()){
                    showpDialog();
                    
                    boolean validator = true;

                    Nnombre = ETnombre.getText().toString();
                    Ncorreo = ETcorreo.getText().toString();
                    Ntelefono = ETtelefono.getText().toString();
                    Nmensaje = ETmensaje.getText().toString();

                    if (Nnombre.matches("")){
                        ETnombre.setError("No puede estar vacio");
                        validator = false;
                    }

                    if (Ncorreo.matches("")){
                        ETcorreo.setError("No puede estar vacio");
                        validator = false;
                    }

                    if (Ntelefono.matches("")){
                        ETtelefono.setError("No puede estar vacio");
                        validator = false;
                    }

                    if (Nmensaje.matches("")){
                        ETmensaje.setError("No puede estar vacio");
                        validator = false;
                    }

                    if (!validarEmail(Ncorreo)){
                        ETcorreo.setError("Email no válido");
                        validator = false;
                    }
                    
                    if (validator){
                        enviarCorreo.execute();

                        ETtelefono.setText("");
                        ETcorreo.setText("");
                        ETmensaje.setText("");
                        ETnombre.setText("");

                        Toast.makeText(layout_contacto.this, "Mensaje enviado...", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(layout_contacto.this, "No se envio", Toast.LENGTH_SHORT).show();
                    }

                    //nuevocorreo.enviarcorreo(ETcorreo.getText().toString(),asunto,ETmensaje.getText().toString(),
                            //ETtelefono.getText().toString(),ETnombre.getText().toString());

                    hidepDialog();
                    //Toast.makeText(layout_contacto.this, ""+nuevocorreo.respuesta, Toast.LENGTH_SHORT).show();

                    /*ETtelefono.setText("");
                    ETcorreo.setText("");
                    ETmensaje.setText("");
                    ETnombre.setText("");*/
                }

            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (asunto.equals("Suscribase")){
            navigationView.getMenu().getItem(3).setChecked(true);
        }else {navigationView.getMenu().getItem(4).setChecked(true); }

    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private class EnviarCorreo extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            nuevocorreo.enviarcorreo(Ncorreo,asunto,Nmensaje,Ntelefono,Nnombre);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchItem.setVisible(false);
        //permite modificar el hint que el EditText muestra por defecto
        searchView.setQueryHint("Busca doctores, clinicas, farmacias");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(layout_contacto.this, query.toString(), Toast.LENGTH_SHORT).show();
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



    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_especialidades) {


            Intent ListSong = new Intent(getApplicationContext(), Activity_especialidades.class);
            startActivity(ListSong);
            finish();


        } else if (id == R.id.nav_clinicas) {
            Intent ListSong = new Intent(getApplicationContext(), activity_clinica.class);
            ListSong.putExtra("Tipo","CLINICA");
            startActivity(ListSong);
            finish();
        } else if (id == R.id.nav_farmacias) {
            Intent ListSong = new Intent(getApplicationContext(), activity_clinica.class);
            ListSong.putExtra("Tipo","FARMACIA");
            startActivity(ListSong);
            finish();
        }   else if (id == R.id.nav_suscribase) {
            Intent ListSong = new Intent(getApplicationContext(),layout_contacto.class);
            ListSong.putExtra("IDMENU",id);
            ListSong.putExtra("Opcion","Suscribase");
            startActivity(ListSong);
            finish();

        } else if (id == R.id.nav_opinion) {
            Intent ListSong = new Intent(getApplicationContext(), layout_contacto.class);
            ListSong.putExtra("IDMENU",id);
            ListSong.putExtra("Opcion","Opinion");
            startActivity(ListSong);
            finish();

        } else if (id == R.id.nav_acercade) {
            Intent ListSong = new Intent(getApplicationContext(), layout_acercade.class);
            ListSong.putExtra("IDMENU",id);
            startActivity(ListSong);
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


}
