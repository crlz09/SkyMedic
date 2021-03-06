package mssolutions.skymedic1;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by marci y CARLOSMOLINA on 14/6/2017.
 */

public class layout_deschombre  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_descrhombre);

        String nom = getIntent().getExtras().getString("nombre");
        String esp = getIntent().getExtras().getString("especialidad");
        String dir = getIntent().getExtras().getString("direccion");
        String tel = getIntent().getExtras().getString("telefono");
        String cor = getIntent().getExtras().getString("correo");
        String ciu = getIntent().getExtras().getString("ciudad");
        TextView nombreTV = (TextView) findViewById(R.id.doctor);
        TextView especialidadTV = (TextView) findViewById(R.id.especialidad_doctor);
        TextView direccionTV = (TextView) findViewById(R.id.direccion_doctor);
        TextView telefonoTV = (TextView) findViewById(R.id.telefono_doctor);
        TextView correoTV = (TextView) findViewById(R.id.correo_doctor);
        TextView telf = (TextView) findViewById(R.id.telefono_doctor);
        TextView ciud= (TextView) findViewById(R.id.ciudad_dr);
        ciud.setText(ciu);
        nombreTV.setText(nom);
        especialidadTV.setText(esp);
        direccionTV.setText(dir);
        telefonoTV.setText(tel);
        correoTV.setText(cor);
        LinearLayout laycorreo = (LinearLayout) findViewById(R.id.layoutcorreohombre);
        LinearLayout layaux= (LinearLayout) findViewById(R.id.layouxhombre);
        LinearLayout layoutDireccion = (LinearLayout) findViewById(R.id.direccionHombre);

        if (cor.length()>5){
            laycorreo.setVisibility(View.VISIBLE);
        }else {
            laycorreo.setVisibility(View.INVISIBLE);
            layoutDireccion.setBackgroundResource(R.drawable.border5);
        }

        ImageView sky=(ImageView) findViewById(R.id.skyenhombre);
        sky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vete=new Intent(getApplicationContext(),MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                finish();
                startActivity(vete);
                finish();
            }
        });

        final String num ="tel:"+ telf.getText().toString();
        telf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse(num));
                startActivity(i);
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
                Toast.makeText(layout_deschombre.this, query.toString(), Toast.LENGTH_SHORT).show();
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


}
