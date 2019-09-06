package id.alkhidmah.maktabal_khidmah.view_util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.leinardi.android.speeddial.SpeedDialView;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import id.alkhidmah.maktabal_khidmah.R;
import id.alkhidmah.maktabal_khidmah.util.PrefKeys;
import id.alkhidmah.maktabal_khidmah.util.SharedMethods;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar mToolbar;
    private SpeedDialView mFabNavigasi;
    private LocationManager mLocationManager;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentLocationMarker, targetLocationMarker;
    private Location currentLocation, targetLocation;
    private boolean firstTimeFlag = true;
    MarkerOptions markerOptions;
    LatLng templatlng;
    LatLngBounds.Builder builder;
    LatLngBounds bounds;

    Handler mHandler;
    Runnable mAnimation;

    String judul, keterangan;
    TextView mTextjudul, mTextketerangan;
    private GoogleMap mGoogleMap;
    SharedMethods sharedMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        mToolbar = findViewById(R.id.toolbar);
        mFabNavigasi = findViewById(R.id.fab_navigasi);
        settoolbar();
        setTitle("Lokasi");


        judul = "Apartemen,,,,";
        keterangan = "dadadada";

        targetLocation = new Location(judul);
        targetLocation.setLatitude(35.1698125);
        targetLocation.setLongitude(129.1207305);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        Objects.requireNonNull(supportMapFragment).getMapAsync(this);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        sharedMethods = new SharedMethods();
        setfab();

        if(!Prefs.getBoolean(PrefKeys.permission,false)){
            Intent i = new Intent(this, PermissionActivity.class);
            startActivity(i);
        }

    }

    private static class BounceAnimation implements Runnable {

        private final long mStart, mDuration;
        private final Interpolator mInterpolator;
        private final Marker mMarker;
        private final Handler mHandler;

        private BounceAnimation(long start, long duration, Marker marker, Handler handler) {
            mStart = start;
            mDuration = duration;
            mMarker = marker;
            mHandler = handler;
            mInterpolator = new BounceInterpolator();
        }

        @Override
        public void run() {
            long elapsed = SystemClock.uptimeMillis() - mStart;
            float t = Math.max(1 - mInterpolator.getInterpolation((float) elapsed / mDuration), 0f);
            mMarker.setAnchor(0.5f, 1.0f + 0.5f * t);

            if (t > 0.0) {
                // Post again 16ms later.
                mHandler.postDelayed(this, 16L);
            }
        }
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult.getLastLocation() == null) return;

            currentLocation = locationResult.getLastLocation();

            if (firstTimeFlag && mGoogleMap != null) {
                templatlng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                builder.include(templatlng);
                bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
                mGoogleMap.moveCamera(cu);
                firstTimeFlag = false;
            }
        }
    };

    private void settoolbar() {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onMapReady(GoogleMap mGoogleMap) {
        this.mGoogleMap = mGoogleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        }

        mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.info_marker, null);
                mTextjudul = v.findViewById(R.id.text_judul);
                mTextketerangan = v.findViewById(R.id.text_keterangan);
                mTextjudul.setText(judul);
                mTextketerangan.setText(keterangan);
                return v;
            }
        });

        mGoogleMap.setOnMarkerClickListener(marker -> {
            mHandler = new Handler();
            final long start = SystemClock.uptimeMillis();
            final long duration = 400L;
            mHandler.removeCallbacks(mAnimation);
            mAnimation = new BounceAnimation(start, duration, marker, mHandler);
            mHandler.post(mAnimation);
            marker.showInfoWindow();
            return false;
        });

        builder = new LatLngBounds.Builder();
        markerOptions = new MarkerOptions();
        templatlng = new LatLng(targetLocation.getLatitude(), targetLocation.getLongitude());
        markerOptions.position(templatlng);
        builder.include(templatlng);
        markerOptions.title(judul);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mGoogleMap.addMarker(markerOptions);
    }


    private void setfab() {

        mFabNavigasi.setOnChangeListener(new SpeedDialView.OnChangeListener() {
            @Override
            public boolean onMainActionSelected() {
                if(!sharedMethods.checkint(MapViewActivity.this,true) || !sharedMethods.checkgps(MapViewActivity.this,true))  return false;
                Uri uri = Uri.parse("geo:" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "?q=" + Uri.encode(targetLocation.getLatitude() + "," + targetLocation.getLongitude() + "(" +judul+ ")"));
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intent);

                return false;
            }

            @Override
            public void onToggleChanged(boolean isOpen) {
                Log.d(PrefKeys.ErrorTAG, "Speed dial toggle state changed. Open = " + isOpen);
            }
        });

        mFabNavigasi.setOnActionSelectedListener(speedDialActionItem -> {
            switch (speedDialActionItem.getId()) {
                case R.id.fab_navigasi:

                    return false; // true to keep the Speed Dial open
                default:
                    return false;
            }
        });
    }

    private void startCurrentLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(PrefKeys.LOCATION_UPDATE_MIN_TIME);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MapViewActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PrefKeys.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return;
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }


    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(this, "Please Install google play services to use this application", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PrefKeys.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                Toast.makeText(this, "Permission denied by uses", Toast.LENGTH_SHORT).show();
            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startCurrentLocationUpdates();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (fusedLocationProviderClient != null)
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sharedMethods.checkint(this,true) && sharedMethods.checkgps(this,true) && isGooglePlayServicesAvailable()){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            startCurrentLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fusedLocationProviderClient = null;
        mGoogleMap = null;
    }
}