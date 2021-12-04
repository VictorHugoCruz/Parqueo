package com.victorcruz.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private FloatingActionsMenu fabMenu;
    private FloatingActionButton fabLista, fabReservas, fabCerrarSesion;
    boolean isPermissionGranted;
    GoogleMap mGoogleMap;
   // private ActivityMapsBinding binding;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Double miLatitud, miLongitud;
    private Location mLastLocation;


    private DatabaseReference mDatabase;
    private ArrayList<Marker> tmpRealTimeMarker= new ArrayList<>();
    private ArrayList<Marker> realTimeMarker= new ArrayList<>();

    public LocationManager locationManager;
    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (location != null) {
                locationManager.removeUpdates(locationListener);
                miLatitud = location.getLatitude();
                miLongitud = location.getLongitude();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_maps);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        fabMenu = findViewById(R.id.fab_menu);
        fabLista = findViewById(R.id.fab_lista);
        fabReservas = findViewById(R.id.fab_reservas);


        fabLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this,ListaParqueo.class);
                startActivity(intent);
            }
        });

        fabReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, Reservas.class);
                startActivity(intent);
            }
        });



        verificarPermiso();
        if (isPermissionGranted) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps_activ);
            supportMapFragment.getMapAsync(this);
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        }



    }

    private void verificarPermiso() {
        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                isPermissionGranted = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction((Settings.ACTION_APPLICATION_DETAILS_SETTINGS));
                Uri uri = Uri.fromParts("Package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    public void miUbicaion() {
        /*locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()){
                    mLastLocation = task.getResult();
                    if(mLastLocation != null){
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())), 15));
                        mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
                        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude())).zoom(17).bearing(45).tilt(30).build();
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        //mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        miUbicaion();
        /*LatLng miUbi = new LatLng( miLatitud, miLongitud);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom((miUbi), 15));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 3000, null);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(miLatitud,miLongitud)).zoom(17).bearing(45).tilt(30).build();*/
        mDatabase.child("Parqueo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               /* for(Marker marker: realTimeMarker){
                    marker.remove();
                }*/

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ParqueosPojo misParqueos = snapshot.getValue(ParqueosPojo.class);
                    String nomPark = misParqueos.getNombreParqueo();
                    String disp = misParqueos.getDisponible();
                    String latitud = misParqueos.getLatitud();
                    String longitud = misParqueos.getLongitud();

                    int lleno = Integer.parseInt(disp);
                    int color;
                    if (lleno>0){
                        color = R.mipmap.ic_parqueo;
                    }else{
                        color = R.mipmap.ic_parqueo_lleno;
                    }


                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(Double.parseDouble(latitud), Double.parseDouble(longitud))).title(nomPark).snippet(disp)
                            .icon(BitmapDescriptorFactory.fromResource(color)).anchor(0f,0f);
                    tmpRealTimeMarker.add(mGoogleMap.addMarker(markerOptions));
                }
                realTimeMarker.clear();
                realTimeMarker.addAll(tmpRealTimeMarker);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //metodo para ir al mainActivity
    private void irMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //creacion de opciones de menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;

    }


    //metodo que devuelde la opcion de item seleccionadp
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if(id== R.id.item_historial){
            Toast.makeText(this, "Se lo lavo??", Toast.LENGTH_SHORT ).show();
        }else if(id == R.id.item_cerrar_sesion){
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(MapsActivity.this,"la sesion a finalizado", Toast.LENGTH_SHORT).show();
                    irMainActivity();
                    finish();
                }
            });
        }else if(id == R.id.item_mapa){


        }else if(id == R.id.item_parqueos){

        }else if(id == R.id.item_reservas){

        }
        return super.onOptionsItemSelected(item);
    }
}