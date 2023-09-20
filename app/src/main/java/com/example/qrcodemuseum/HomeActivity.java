package com.example.qrcodemuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    //Components
    private ListView listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listItems = findViewById(R.id.HomeListViewList);

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                populateItems()
        );

        //Add adapter
        listItems.setAdapter(adapter);

        //Add click item list
        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = populateItems()[position];

                // Display a toast message with the selected item
                Toast.makeText(getApplicationContext(), "Selected Item: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

    }


    public String[] populateItems(){
        String[] items = {
                "Mac 1990", "Windows Phone 2000", "Leitor de DVD", "asd", "dfasd", "saddas", "saddas", "saddas", "saddas", "saddas", "saddas", "saddas"
        };

        return items;
    }
}
