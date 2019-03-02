package bphc.sih.demo.sihdemoapp;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Set;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    public static Set<Pair<LatLng, String>> hotspots;


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
        map = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng canberra = new LatLng(-35, 149);
//        ArrayList<Pair<LatLng, String>> markers = new ArrayList<>();
////        TODO: Get all markers (call some function)
//        for (Pair<LatLng, String> marker : markers) {
//            map.addMarker(new MarkerOptions().position(marker.first).title(marker.second));
//        }
        float zoomLevel = 9.0f;
        for (Pair<LatLng, String> hotspot : hotspots) {
            Location hotspotLoc = new Location("hotspot");
            hotspotLoc.setLatitude(hotspot.first.latitude);
            hotspotLoc.setLongitude(hotspot.first.longitude);
            map.addMarker(new MarkerOptions().position(hotspot.first).title(hotspot.second));
        }
//        map.addMarker(new MarkerOptions().position(sydney).title("Test Message"));
//        map.addMarker(new MarkerOptions().position(canberra).title("Message 2"));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
    }
}
