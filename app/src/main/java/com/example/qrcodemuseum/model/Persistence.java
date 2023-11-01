package com.example.qrcodemuseum.model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Persistence {
    private String username;
    private String password;
    private Context context;

    public Persistence() {

    }

    public Persistence(String username, String password, Context context) {
        this.username = username;
        this.password = password;
        this.context = context;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void generateFile() {
        String credentials = username + ";" + password;
        try {
            FileOutputStream fos = context.openFileOutput("user_credentials.txt", Context.MODE_PRIVATE);
            fos.write(credentials.getBytes());
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String[] returnValues() {
        String[] data = null;

        try {
            FileInputStream fis = context.openFileInput("user_credentials.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while((line = br.readLine()) != null) {
                data = line.split(";");
            }

            br.close();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
