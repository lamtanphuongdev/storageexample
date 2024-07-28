package com.example.storageexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText edtName,edtPhone;
    Button btnSave, btnLoad, btnTest, btnDelete, btnCache, btnCacheRead;
    TextView tvShow;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        Act();
    }

    private void Act() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveEventButton();
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadEventButton();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteEventButton();
            }
        });

        btnCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheEventButton();
            }
        });

        btnCacheRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cacheReadData();
            }
        });
    }

    private void cacheReadData() {
        String name, phone;
        String filename = getCacheDir() + "/mydata.txt";
        File file = new File(filename);

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            name = bufferedReader.readLine();
            phone = bufferedReader.readLine();
            bufferedReader.close();

            if (name.isEmpty()){
                edtName.setText("Blank");
                edtPhone.setText("Blank");
            }else {
                edtName.setText(name);
                edtPhone.setText(phone);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void CacheEventButton() {
        String filename = getCacheDir() + "/mydata.txt";
        File file = new File(filename);
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        try
        {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(name);
            fileWriter.write("\n");
            fileWriter.write(phone);
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void DeleteEventButton() {
        sharedPreferences = getSharedPreferences("mydata",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void LoadEventButton() {
        sharedPreferences = getSharedPreferences("mydata",MODE_PRIVATE);
        String name = sharedPreferences.getString("NAME","blank");
        String phone = sharedPreferences.getString("PHONE","blank");

        edtName.setText(name);
        edtPhone.setText(phone);
    }

    private void SaveEventButton() {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        sharedPreferences = getSharedPreferences("mydata",MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("NAME",name);
        editor.putString("PHONE",phone);
        editor.apply();

        //tvShow.setText(name);

    }

    private void Init() {
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);
        btnTest = findViewById(R.id.btnTest);
        btnDelete = findViewById(R.id.btnDelete);
        tvShow = findViewById(R.id.tvShow);
        btnCache = findViewById(R.id.btnCacheEx);
        btnCacheRead = findViewById(R.id.btnCacheRead);
    }
}