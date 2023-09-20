package com.example.qrcodemuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qrcodemuseum.model.Item;

import java.util.ArrayList;
import java.util.List;

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
                returnTitles(populateItems())
        );

        //Add adapter
        listItems.setAdapter(adapter);

        //Add click item list
        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = returnTitles(populateItems())[position];

                // Display a toast message with the selected item
                Toast.makeText(getApplicationContext(), "Selected Item: " + selectedItem, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ItemActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public List<Item> populateItems(){
        List<Item> items = new ArrayList<>();

        Item i = new Item(1, "Mac 1975", 1975, "mac asdasd");
        items.add(i);
        Item i2 = new Item(2, "Windows Phone 1999", 1999, "windows phone asdasd");
        items.add(i2);
        Item i3 = new Item(3, "Nokia 1260", 1260, "Nokia 1260");
        items.add(i3);
        Item i4 = new Item(4, "Mac 1975", 1975, "asdasdasdsad");
        items.add(i4);
        Item i5 = new Item(5, "Mac 1975", 1975, "asdasdasdsad");
        items.add(i5);
        Item i6 = new Item(6, "Mac 1975", 1975, "asdasdasdsad");
        items.add(i6);
        Item i7 = new Item(7, "Mac 1975", 1975, "asdasdasdsad");
        items.add(i7);

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
        Toast.makeText(
                getApplicationContext(),
                "Create Item.",
                Toast.LENGTH_LONG
        ).show();

        Intent intent = new Intent(this, CreateItemActivity.class);
        startActivity(intent);
        finish();
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
