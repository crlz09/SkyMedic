package mssolutions.skymedic1;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


public class Activity_especialidades  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

       @Override
    protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);

           setContentView(R.layout.layout_especialidades);

             final String especialidades[] = {"Alergologia","Anestesiologia","Angiologia","Bariatria",
            "Cardiologia","Cirugia Estetica",
            "Cirugia General","Cirugia Maxilar",
            "Cirugia Plastica","Dermatologia",
            "Endocrinologia","Endoscopia","Fisiatria","Gastroenterologia",
            "Geriatria","Ginecologia","Hematologia","Homeopatia",
            "Infectologia", "Inmunologia",
            "Medicina del Deporte", "Medicina Forense", "Medicina General",
            "Medicina Laboral", "Medicina Nuclear", "Microcirugia",
            "Nefrologia", "Neonatologia", "Neumologia",
            "Neurocirugia", "Neurologia", "Nutricion", "Odontologia",
            "Oftalmologia", "Oncologia", "Ortopedia", "Otorrino",
            "Patologia", "Pediatria", "Perinatologia", "Proctologia",
            "Psiquiatria","Radiologia","Reumatologia","Traumatologia",
            "Urologia","Veterinaria"};

            long idespecialidades [] = new long[47];


           LinearLayout [] locura = new LinearLayout[47];

           for (int j=0; j < idespecialidades.length; j++){
               idespecialidades[j]=R.id.alergologia+j;


               locura[j] = (LinearLayout) findViewById(R.id.alergologia+j);
               final int finalJ = j;
               locura[j].setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent nuevo = new Intent(getApplicationContext(),especialidad.class);
                       nuevo.putExtra("Descripcion",especialidades[finalJ]);
                       nuevo.putExtra("icono",finalJ);
                       startActivity(nuevo);
                   }
               });

           }


           Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
           setSupportActionBar(toolbar);

           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                   this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
           drawer.setDrawerListener(toggle);
           toggle.syncState();

           NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
           navigationView.setNavigationItemSelectedListener(this);
           navigationView.getMenu().getItem(0).setChecked(true);
           //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            ListSong.putExtra("IDMENU",id);
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
        }  else if (id == R.id.nav_suscribase) {
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



        }

