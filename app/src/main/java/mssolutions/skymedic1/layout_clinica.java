package mssolutions.skymedic1;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by marci y CARLOSMOLINA on 14/6/2017.
 */

public class layout_clinica  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_clinica);

        final String nombre,img,ciudad,desc,estado,direccion,telefono,correo,latitud,longitud,tipo;

        nombre=getIntent().getExtras().getString("Nombre");
        img=getIntent().getExtras().getString("Imagen");
        ciudad=getIntent().getExtras().getString("Ciudad");
        desc=getIntent().getExtras().getString("Descripcion");
        estado=getIntent().getExtras().getString("Estado");
        direccion=getIntent().getExtras().getString("Direccion");
        correo=getIntent().getExtras().getString("Correo");
        telefono=getIntent().getExtras().getString("Telefono");
        latitud=getIntent().getExtras().getString("Latitud");
        longitud=getIntent().getExtras().getString("Longitud");
        tipo=getIntent().getExtras().getString("tipo");




        final ImageView imagen = (ImageView) findViewById(R.id.imgclinicaf);
        TextView TVnombre = (TextView) findViewById(R.id.nombreclinicaf);
        TextView TVciudad = (TextView) findViewById(R.id.ciudadclinicaf);
        TextView TVdesc = (TextView) findViewById(R.id.descclinicaf);
        TextView TVcorreo = (TextView) findViewById(R.id.correoclinicaf);
        TextView TVdireccion = (TextView) findViewById(R.id.direccionclinicaf);
        TextView telf = (TextView) findViewById(R.id.telefonoclinicaf);
        LinearLayout fondo= (LinearLayout) findViewById(R.id.fondo_cli_far);
        if (tipo.equals("CLINICA")){
                fondo.setBackgroundColor((Color.parseColor("#3dbb76")));
                }else {
            fondo.setBackgroundColor((Color.parseColor("#1996bd")));
        }

        Picasso.with(getApplicationContext()).load(img).fit().into(imagen);
        TVnombre.setText(nombre);
        TVciudad.setText(ciudad+" - " + estado);
        TVcorreo.setText(correo);
        TVdesc.setText(desc);
        TVdireccion.setText(direccion);

        ImageView sky = (ImageView) findViewById(R.id.skyhome);

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




        telf.setText(telefono);
        final String num ="tel:"+ telf.getText().toString();
        telf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse(num));
                startActivity(i);
            }
        });

        TextView usuarioInstagram = (TextView)findViewById(R.id.usuInstagram);

        usuarioInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://instagram.com/_u/migueelsv");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/migueelsv")));
                }
            }
        });



        final ImageView image = new ImageView(this);



        picasso(img,image);
        final AlertDialog.Builder builder =

               new AlertDialog.Builder(this).setView(image);


        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

                   /* builder.setView(null);
                dialog.dismiss();*/
            }
        });


        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                picasso(img,image);
                if(image.getParent()!=null)
                    ((ViewGroup)image.getParent()).removeView(image);
                 builder.show();
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
        navigationView.getMenu().getItem(1).setChecked(true);
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
        searchView.setQueryHint("Busca doctores, clinicas, farmacias");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(layout_clinica.this, query.toString(), Toast.LENGTH_SHORT).show();
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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void picasso(String u, final ImageView im){
        try{ Picasso.with(layout_clinica.this)
                .load(u).resize(600,600)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
            /* Save the bitmap or do something with it here */

                        //Set it in the ImageView
                        im.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        } catch (Exception ex) {

        }
    }


}
