package com.example.jerrol.samplegooglemap;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnCameraIdleListener {

    private final String TAG = this.getClass().getSimpleName();

    private GoogleMap mMap;
    private ArrayList<Marker> markerPlaces = new ArrayList<>();
    private ArrayList<Marker> markerIndicator = new ArrayList<>();
    private ArrayList<Polyline> polylineArrayList = new ArrayList<>();
    private ArrayList<Marker> markerNotVisible = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap = googleMap;

        mMap.setOnMapClickListener(this);
        mMap.setOnCameraIdleListener(this);
        // Add a marker in Sydney and move the camera
        LatLng marker1 = new LatLng(-34, 151);
        LatLng marker2 = new LatLng(-33.999238060379746, 150.99955581128597);
        LatLng marker3 = new LatLng(-34.00018756301555, 151.00139681249857);
        LatLng marker4 = new LatLng(-34.002144627909736, 150.9996818751097);
        LatLng marker5 = new LatLng(-33.99743047377439, 151.00403107702732);
        LatLng marker6 = new LatLng(-33.99825241204913, 150.99818922579288);

        markerPlaces.add(mMap.addMarker(new MarkerOptions().position(marker1).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon))));
        markerPlaces.add(mMap.addMarker(new MarkerOptions().position(marker2).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon))));
        markerPlaces.add(mMap.addMarker(new MarkerOptions().position(marker3).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon))));
        markerPlaces.add(mMap.addMarker(new MarkerOptions().position(marker4).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon))));
        markerPlaces.add(mMap.addMarker(new MarkerOptions().position(marker5).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon))));
        markerPlaces.add(mMap.addMarker(new MarkerOptions().position(marker6).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon))));

        // mMap.moveCamera(CameraUpdateFactory.newLatLng(marker1));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker1, 19f));
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.i(TAG, "Latitude: " + latLng.latitude + " Longitude: " + latLng.longitude);
    }

    private void showMarkerVisible() {
        for (Marker marker : markerIndicator) {
            // Here remove the marker indicator if it is drawn in map.
            marker.remove();
        }
        for (Polyline polyline : polylineArrayList) {
            polyline.remove();
        }
        LatLngBounds latLngBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
        for (Marker marker : markerPlaces) {
            if (latLngBounds.contains(marker.getPosition())) {
                // The marker is visible to map.
            } else {
                // The marker is not visible to map.
                // here to create an indicator.
                // double distance = SphericalUtil.computeDistanceBetween(mMap.getCameraPosition().target, marker.getPosition());
               polylineArrayList.add( mMap.addPolyline(new PolylineOptions()
                       .add(mMap.getCameraPosition().target)
                       .add(marker.getPosition())
               ));
               markerNotVisible.add(marker);
            }
        }

        for (Marker marker : markerNotVisible) {
            // SphericalUtil.
        }
    }

    @Override
    public void onCameraIdle() {
        showMarkerVisible();
    }
}
