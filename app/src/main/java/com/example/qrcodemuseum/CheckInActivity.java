package com.example.qrcodemuseum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qrcodemuseum.model.AddressObj;
import com.example.qrcodemuseum.model.DatabaseHelper;
import com.example.qrcodemuseum.model.Item;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CheckInActivity extends AppCompatActivity {

    //Components
    private ListView listView;
    private ListView listItems;

    private double latitude;
    private double longitude;
    private AddressObj addressOb;

    private Long userId;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        listItems = findViewById(R.id.CheckInListViewList);

        //Get data from the previous activity
        Intent intent = getIntent();
        userId = intent.getLongExtra("userId", 1);

        dbHelper = new DatabaseHelper(getApplicationContext());

        checkIn();
    }

    public void carregarLista() {
        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                returnValues(populateItems())
        );

        //Add adapter
        listItems.setAdapter(adapter);
    }

    public List<AddressObj> populateItems(){
        List<AddressObj> addresses = new ArrayList<>();

        db = dbHelper.getWritableDatabase();

        String[] projection = {"id", "postalCode", "addressLine", "city", "state", "country", "datetime", "user_id"};
        Cursor cursor = db.query("Checkin", projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String userIdDb = cursor.getString(cursor.getColumnIndexOrThrow("user_id"));
            String addressLine = cursor.getString(cursor.getColumnIndexOrThrow("addressLine"));
            String datetime = cursor.getString(cursor.getColumnIndexOrThrow("datetime"));

            if (userIdDb.equals(userId.toString())) {
                AddressObj addressOb = new AddressObj(null, addressLine, null, null, null, datetime, userIdDb);
                addresses.add(addressOb);
            }
        }
        cursor.close();

        db.close();
        dbHelper.close();

        return addresses;
    }

    public String[] returnValues(List<AddressObj> addresses) {
        String[] listCheckins = new String[addresses.size()];

        for(int i=0; i<addresses.size(); i++) {
            listCheckins[i] = addresses.get(i).getAddressLine() + " " + addresses.get(i).getDatetime();
        }

        return listCheckins;
    }

    private void checkIn() {
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                locationManager.removeUpdates(this);

                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                    if (!addresses.isEmpty()) {
                        Address address = addresses.get(0);

                        String addressLine = address.getAddressLine(0);
                        String city = address.getLocality();
                        String state = address.getAdminArea();
                        String country = address.getCountryName();
                        String postalCode = address.getPostalCode();

                        addressOb = new AddressObj(postalCode, addressLine, city, state, country, getDateTime(), userId.toString());

                        if(addressOb != null) {
                            insertDatabase(addressOb);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };

        if (checkLocationPermission()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            requestLocationPermission();
        }
    }

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkIn();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Location permission is required to perform check-in.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getDateTime() {
        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return dateFormat.format(currentDate);
    }

    public void insertDatabase(AddressObj addressObj) {
        db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("postalCode", addressObj.getPostalCode());
        contentValues.put("addressLine", addressObj.getAddressLine());
        contentValues.put("city", addressObj.getCity());
        contentValues.put("state", addressObj.getState());
        contentValues.put("country", addressObj.getCountry());
        contentValues.put("datetime", addressObj.getDatetime());
        contentValues.put("user_id", userId.toString());

        db.insert("Checkin", null, contentValues);

        db.close();
        dbHelper.close();

        carregarLista();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }
}
