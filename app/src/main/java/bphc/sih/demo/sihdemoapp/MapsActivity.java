package bphc.sih.demo.sihdemoapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button test = findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MapsActivity.this, "This will open webView", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap map = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(23.0247, 72.5168);
//        LatLng canberra = new LatLng(23.0313, 72.5148);
        float zoomLevel = 12.0f;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<LatLng> locations = extras.getParcelableArrayList("locations");
            ArrayList<String> amenities = extras.getStringArrayList("amenities");
            for (int i = 0; i < locations.size(); i++) {
                map.addMarker(new MarkerOptions().position(locations.get(i)).title(amenities.get(i)));
            }
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(locations.get(0), zoomLevel));
        }

//        map.addMarker(new MarkerOptions().position(sydney).title("Medicine"));
//        map.addMarker(new MarkerOptions().position(canberra).title("Food"));
    }
}
