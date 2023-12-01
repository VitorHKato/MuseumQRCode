package com.example.qrcodemuseum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qrcodemuseum.model.DatabaseHelper;
import com.example.qrcodemuseum.model.Item;
import com.example.qrcodemuseum.model.User;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    //Components
    private ListView listItems;
    private Button createItemButton;

    private Long userId;
    private User user = new User();

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listItems = findViewById(R.id.HomeListViewList);
        createItemButton = findViewById(R.id.HomeButtonCreateItem);

        //Get data from the previous activity
        Intent intent = getIntent();
        userId = intent.getLongExtra("userId", 1);

        dbHelper = new DatabaseHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();

        //Get data from the logged user
        getUserData();

        //Validate create item button visibility
        validateCreateItemButton();

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                returnTitles(populateItems())
        );

        //Add adapter
        listItems.setAdapter(adapter);

        //Add click item list
        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                //String selectedItem = returnTitles(populateItems())[position];

                //Send the object to ItemActivity
                Item item = populateItems().get(position);

                Intent intent = new Intent(getApplicationContext(), ItemActivity.class);
                intent.putExtra("userType", user.getUserType());
                intent.putExtra("userId", userId);
                intent.putExtra("item", item);
                startActivity(intent);
                finish();
            }
        });

    }

    public List<Item> populateItems(){
        List<Item> items = new ArrayList<>();

        db = dbHelper.getWritableDatabase();

        String[] projection = {"id", "title", "year", "description"};
        Cursor cursor = db.query("Item", projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String year = cursor.getString(cursor.getColumnIndexOrThrow("year"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            Integer id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));

            Item i = new Item(id, title, Integer.valueOf(year), description);
            items.add(i);

        }
        cursor.close();

        db.close();
        dbHelper.close();

        return items;
    }

    public String[] returnTitles(List<Item> itens) {
        String[] listItensTitle = new String[itens.size()];

        for(int i=0; i<itens.size(); i++) {
            listItensTitle[i] = itens.get(i).getTitle();
        }

        return listItensTitle;
    }

    public void scanQRCode(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setCameraId(0);
        intentIntegrator.initiateScan();

    }

    private Item getItem(String titleQrCode) {
        db = dbHelper.getWritableDatabase();

        String[] projection = {"id", "title", "year", "description"};
        Cursor cursor = db.query("Item", projection, null, null, null, null, null);

        Item i = new Item();

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));

            if (titleQrCode.equals(title)) {
                String year = cursor.getString(cursor.getColumnIndexOrThrow("year"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                Integer id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));

                i.setId(id);
                i.setTitle(title);
                i.setDescription(description);
                i.setYear(Integer.valueOf(year));
            }

        }
        cursor.close();

        db.close();
        dbHelper.close();

        return i;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(intentResult != null){
            if (intentResult.getContents() !=  null){
                Item a = getItem(intentResult.getContents());

                if (a.getId() != null) {
                    Intent intent = new Intent(getApplicationContext(), ItemActivity.class);
                    intent.putExtra("userType", user.getUserType());
                    intent.putExtra("userId", userId);
                    intent.putExtra("item", a);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Item not found.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void checkIn(View view) {
        Intent intent = new Intent(this, CheckInActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

    public void createItem(View view) {
        Intent intent = new Intent(this, CreateItemActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

    public void getUserData() {
        db = dbHelper.getWritableDatabase();

        String[] projection = {"id", "username", "userType"};
        Cursor cursor = db.query("User", projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            Integer userType = cursor.getInt(cursor.getColumnIndexOrThrow("userType"));

            if (userId.equals(id)) {
                user.setUser(username);
                user.setUserType(userType);
            }
        }
        cursor.close();

        dbHelper.close();
        db.close();
    }

    private void validateCreateItemButton() {
        if (user.getUserType() == 2 || user.getUserType() == 3) {
            createItemButton.setVisibility(View.GONE);
        }
    }
}
