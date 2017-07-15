package francefrancerevolution.gonolesdatabase;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import francefrancerevolution.gonolesdatabase.database.DatabaseHelper;
import francefrancerevolution.gonolesdatabase.model.Building;

import static android.R.attr.enabled;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Building building_details;
    private DatabaseHelper bDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Check if GPS is enabled
        LocationManager mlocManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if(!mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showDialogGPS();
        }

        //Create database helper
        bDBHelper = new DatabaseHelper(this);

        //Pull building name from bundle and query the Database for building details
        if(getIntent()!= null && getIntent().getExtras() != null) {
            Intent args = getIntent();
            Bundle bundle = args.getExtras();
            String name = bundle.getString("BldName");

            building_details = bDBHelper.getBuildingbyName(name);
            //Toast.makeText(getBaseContext(), bundle.getString("BldName"), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList<Building> userBuildingList= new ArrayList<>();

        //Check GPS Permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Add a marker in Sydney and move the camera
        LatLng fsu = new LatLng(30.441668, -84.298517);
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(fsu , 15.0f) );

        //TODO: Add marker for buildings
    if (building_details!=null) {
        LatLng toGo = new LatLng(Double.parseDouble(building_details.getBuilding_Lat()),
            Double.parseDouble(building_details.getBuilding_Lng()));
        mMap.addMarker(new MarkerOptions().position(toGo).title(building_details.getBuilding_name()));
    }
    else {
        //Add markers from Saved List
        userBuildingList = bDBHelper.getListBuilding2();
        LatLng toGo;

        for (int i = 0; i < userBuildingList.size(); i++) {
            toGo = new LatLng(Double.parseDouble(userBuildingList.get(i).getBuilding_Lat()),
                    Double.parseDouble(userBuildingList.get(i).getBuilding_Lng()));
             mMap.addMarker(new MarkerOptions().position(toGo).title(userBuildingList.get(i).getBuilding_name()));
           // Toast.makeText(getBaseContext(),userBuildingList.get(i).getBuilding_name(),Toast.LENGTH_SHORT).show();
        }

    }

       // mMap.addMarker(new MarkerOptions().position(fsu).title("FSU"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(fsu));


        mMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean
                    onMarkerClick(Marker arg0) {
                        LatLng position = arg0.getPosition();
                        Toast.makeText(getApplicationContext(),"Lat: " + position.latitude + ", Lng: "+ position.longitude,Toast.LENGTH_LONG).show();
                                return true;
                            }
                        });
        mMap.setOnMapClickListener(
                new GoogleMap.OnMapClickListener() {
                    @Override
                    public void
                    onMapClick(LatLng arg0) {
                        Toast.makeText(getApplicationContext(),"Lat: "+ arg0.latitude + ", Lng: " + arg0.longitude,Toast.LENGTH_LONG).show();
                            }
                        });
    }

    private void showDialogGPS() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);

        Button buttonOk = (Button) dialog.findViewById(R.id.dialogOkButton);
        Button buttonCancel = (Button) dialog.findViewById(R.id.dialogCancelButton);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                getBaseContext().startActivity(intent);
                dialog.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        dialog.show();
    }
}
