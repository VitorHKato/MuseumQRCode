package com.example.qrcodemuseum;

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

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    //Components
    private ListView listItems;
    private Button createItemButton;

    private Long userId;
    private User  user = new User();

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
        Toast.makeText(
                getApplicationContext(),
                "Scan QRCode.",
                Toast.LENGTH_LONG
        ).show();

    }

    public void checkIn(View view) {
        Toast.makeText(
                getApplicationContext(),
                "Check In.",
                Toast.LENGTH_LONG
        ).show();

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

//    @Override
//    public void onBackPressed()
//    {
//        Intent intent = new Intent(getApplicationContext(), Cadastro2Activity.class);
//        startActivity(intent);
//        finish();
//    }

    //add scroll in all activities

}
