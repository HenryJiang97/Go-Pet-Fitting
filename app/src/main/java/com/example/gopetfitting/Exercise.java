package com.example.gopetfitting;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;
import java.util.Timer;
import java.util.UUID;

public class Exercise extends AppCompatActivity implements
        OnDataPointListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    private static final String TAG = "Exercise Activity";

    private final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;
    private final String MAPVIEW_BUNDLE_KEY = "1";
    private static final String AUTH_PENDING = "auth_state_pending";
    private static final int REQUEST_OAUTH = 1;

    private User user;

    private TextView stepsTV;
    private TextView distanceTV;
    private TextView caloriesTV;
    private TextView timeTV;
    private MapView mapView;
    private GoogleMap map;
    private Button recordBT;
    private FloatingActionButton myLocationBT;

    private GoogleApiClient apiClient;
    private boolean authInProgress = false;

    private FitnessOptions fitnessOptions;
    private GoogleSignInAccount account;

    // Exercise stop watch
    private boolean isRecording;
    private long exerciseStartTime;
    private long exerciseTime;
    private long period;
    private Timer timer;
    private Handler handler;
    private String exerciseTimeStr;

    // Distance
    private LocationListener locationListener;
    private LocationManager locationManager;
    static double distance;
    static Double lat1 = null;
    static Double lon1 = null;
    static Double lat2 = null;
    static Double lon2 = null;
    static int status = 0;

    // Steps
    private int steps;

    // Calories
    private int calories;

    // Activity
    private String activityId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.exercise);

        // Initialize variables
        stepsTV = findViewById(R.id.steps_exercise);
        distanceTV = findViewById(R.id.distance_exercise);
        caloriesTV = findViewById(R.id.calories_exercise);
        timeTV = findViewById(R.id.time_exercise);
        recordBT = findViewById(R.id.record_exercise);
        myLocationBT = findViewById(R.id.myLocationButton_exercise);
        mapView = findViewById(R.id.map_exercise);
        isRecording = false;
        handler = new Handler();
        distance = 0.0;
        steps = 0;
        calories = 0;

        // Check location permission
        if (ActivityCompat.checkSelfPermission(Exercise.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Exercise.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Exercise.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {
            // Set up google map service
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;
                    map.getUiSettings().setMyLocationButtonEnabled(false);

                    // Check my location permission
                    if (ActivityCompat.checkSelfPermission(Exercise.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Exercise.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Exercise.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        return;
                    }

                    map.setMyLocationEnabled(true);
                    getCurrentLocation();
                }
            });


        }


        // TODO: Set google fit api
//        // Set Google API client
//        if (savedInstanceState != null) {
//            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
//        }
//
//        apiClient = new GoogleApiClient.Builder(Exercise.this)
//                .addApi(Fitness.SENSORS_API)
//                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
//                .addConnectionCallbacks(Exercise.this)
//                .addOnConnectionFailedListener(Exercise.this)
//                .build();
//
//
//        // Define google fit api
//        fitnessOptions = FitnessOptions.builder()
//                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
//                .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_READ)
//                .addDataType(DataType.TYPE_STEP_COUNT_CADENCE, FitnessOptions.ACCESS_READ)
//                .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
//                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
//                .build();
//
//        account = GoogleSignIn.getAccountForExtension(this, fitnessOptions);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        initVal();

        user = (User)getIntent().getSerializableExtra("user");


        // Init locationManager and locationListener
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();


//        apiClient.connect();

        // Check Google fitness permission
//        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
//            GoogleSignIn.requestPermissions(
//                    Exercise.this,
//                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
//                    account,
//                    fitnessOptions);
//        }



        // Change the button state
        if (isRecording) {
            recordBT.setText("STOP");
        } else {
            recordBT.setText("RECORD");
        }


        // Tap the record button
        recordBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getFitnessData();

                // RECORD -> STOP
                if (isRecording) {
                    isRecording = false;
                    recordBT.setText("RECORD");
                    handler.removeCallbacks(runnable);
                    generateActivity();

                    // Parse activity id back to profile page
//                    Intent intent = new Intent(getBaseContext(), Profile.class);
//                    intent.putExtra("activityId", activityId);
//                    startActivity(intent);

                    addMarker("END");
                }

                // STOP -> RECORD
                else {
                    initVal();
                    exerciseStartTime = SystemClock.uptimeMillis();
                    isRecording = true;
                    recordBT.setText("STOP");

                    distance = 0.0;
                    handler.postDelayed(runnable, 0);
                    handler.postDelayed(runnable2,5000);

                    // Check for permission
                    if (ActivityCompat.checkSelfPermission(Exercise.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(Exercise.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(Exercise.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        return;
                    }

                    // Listen for location change
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);

                    addMarker("START");
                }


                // TODO: GOOGLE FIT IMPLEMENTATION
//                accessGoogleFit();
//                Task<Void> response = Fitness.getSensorsClient(Exercise.this, account)
//                        .add(
//                                new SensorRequest.Builder()
//                                        .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
//                                        .setSamplingRate(1, TimeUnit.MINUTES)  // sample once per minute
//                                        .build(),
//                                myStepCountListener);
            }
        });



        // Tap the my location button (Get my current location and zoom)
        myLocationBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if( !authInProgress ) {
            try {
                authInProgress = true;
                connectionResult.startResolutionForResult( Exercise.this, REQUEST_OAUTH );
            } catch(IntentSender.SendIntentException e ) {

            }
        } else {
            Log.e( "GoogleFit", "authInProgress" );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == REQUEST_OAUTH ) {
            authInProgress = false;
            if( resultCode == RESULT_OK ) {
                if( !apiClient.isConnecting() && !apiClient.isConnected() ) {
                    apiClient.connect();
                }
            } else if( resultCode == RESULT_CANCELED ) {
                Log.e( "GoogleFit", "RESULT_CANCELED" );
            }
        } else {
            Log.e("GoogleFit", "requestCode NOT request_oauth");
        }

    }

    @Override
    public void onDataPoint(DataPoint dataPoint) {

    }

    // Get current location and zoom in the map
    private void getCurrentLocation() {
        LatLng myLocation;
        Location location = map.getMyLocation();

        if(location != null) {
            final double latitude = location.getLatitude();
            final double longitude = location.getLongitude();
            myLocation = new LatLng(latitude, longitude);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                    15));
        }
    }


    // Init value
    private void initVal() {
        stepsTV.setText("0");
        caloriesTV.setText("0 kcal");
        distanceTV.setText("0.0 m");
        timeTV.setText("00:00");
    }


    // My location listener
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (status == 0) {
                lat1 = location.getLatitude();
                lon1 = location.getLongitude();
            } else if ((status % 2) != 0) {
                lat2 = location.getLatitude();
                lon2 = location.getLongitude();
                distance += distanceBetweenTwoPoint(lat1, lon1, lat2, lon2);
            } else if ((status % 2) == 0) {
                lat1 = location.getLatitude();
                lon1 = location.getLongitude();
                distance += distanceBetweenTwoPoint(lat2, lon2, lat1, lon1);
            }
            status++;
            distanceTV.setText(String.valueOf(distance) + " m");
        }

        double distanceBetweenTwoPoint(double srcLat, double srcLng, double desLat, double desLng) {
            double earthRadius = 3958.75;
            double dLat = Math.toRadians(desLat - srcLat);
            double dLng = Math.toRadians(desLng - srcLng);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(srcLat))
                    * Math.cos(Math.toRadians(desLat)) * Math.sin(dLng / 2)
                    * Math.sin(dLng / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double dist = earthRadius * c;

            double meterConversion = 1609;

            return (int) (dist * meterConversion);
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


    // Thread for stop watch
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            exerciseTime = (SystemClock.uptimeMillis() - exerciseStartTime) / 60;
            int seconds = (int)exerciseTime % 60;
            int minutes = ((int)exerciseTime / 60) % 60;
            int hours = (int)exerciseTime / 3600;
            String secondsVal = seconds < 10 ? "0" + String.valueOf(seconds) : String.valueOf(seconds);
            String minutesVal = minutes < 10 ? "0" + String.valueOf(minutes) + ":" : String.valueOf(minutes) + ":";
            String hoursVal = hours == 0 ? "" : String.valueOf(hours) + ":";
            exerciseTimeStr = hoursVal + minutesVal + secondsVal;
            timeTV.setText(exerciseTimeStr);
            getCaloriesBurned();
            handler.postDelayed(this, 60);
        }
    };

    // Thread for stop watch
    public Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            Random rand = new Random();
            steps += rand.nextInt(10);
            stepsTV.setText(String.valueOf(steps));
            handler.postDelayed(this, 5000);
        }
    };


    // Print out exercise summary
    private void printSummary(int steps, int calories, double distance, long exerciseTime) {
        Log.d("STEPS: ", String.valueOf(steps));
        Log.d("CALORIES: ", String.valueOf(calories));
        Log.d("DISTANCE: ", String.valueOf(distance));
        Log.d("TIME: ", String.valueOf(exerciseTime));
    }

    // Generate and add new activity to database
    private void generateActivity() {
        activityId = String.valueOf(UUID.randomUUID());
        Activity activity = new Activity(activityId, exerciseStartTime, exerciseTime, steps, distance, calories);
        printSummary(activity.getSteps(), activity.getBurnedCalories(), activity.getDistance(), activity.getExerciseTime());
        addToDB(activity);
    }


    // Connect and add new Activity to database
    private void addToDB(Activity activity) {
        activity.addToDB();
    }

    // Add score
    private int addScore() {
        return (int)(calories * steps * distance * exerciseTime / (int)(1e11));
    }

    // Calories calculator based on exercise
    private void getCaloriesBurned() {
        int MET = 8;
        int cal = (int)(exerciseTime / 60 * (MET * 3.5 * 55) / 200);
        calories = cal;
        caloriesTV.setText(String.valueOf(calories));
    }


    // Add marker on the map
    private void addMarker(String name) {
        if (ActivityCompat.checkSelfPermission(Exercise.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Exercise.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Exercise.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            // Got permission
            LocationManager lm = (LocationManager) Exercise.this.getSystemService(getBaseContext().LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            map.addMarker(new MarkerOptions().position(latLng).title(name));
        }
    }



}
