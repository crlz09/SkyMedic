package mssolutions.skymedic1;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mssolutions.skymedic1.app.correo;

/**
 * Created by marci on 14/6/2017.
 */

public class layout_contacto extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    correo nuevocorreo= new correo();

    String asunto;

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



        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isOnline()){
                    showpDialog();

                    nuevocorreo.enviarcorreo(ETcorreo.getText().toString(),asunto,ETmensaje.getText().toString(),
                            ETtelefono.getText().toString(),ETnombre.getText().toString());

                    hidepDialog();
                    Toast.makeText(layout_contacto.this, ""+nuevocorreo.respuesta, Toast.LENGTH_SHORT).show();

                    ETtelefono.setText("");
                    ETcorreo.setText("");
                    ETmensaje.setText("");
                    ETnombre.setText("");
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


        } else if (id == R.id.nav_clinicas) {

            Intent ListSong = new Intent(getApplicationContext(), layout_clinica.class);
            startActivity(ListSong);

        } else if (id == R.id.nav_farmacias) {
            Intent ListSong = new Intent(getApplicationContext(), layout_clinica.class);
            startActivity(ListSong);
        }   else if (id == R.id.nav_suscribase) {
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



    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
