package com.example.sqlite_recyclerview.ui.main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlite_recyclerview.Controller.DataBase_Helper;
import com.example.sqlite_recyclerview.Model.Data;
import com.example.sqlite_recyclerview.R;

public class AddData extends AppCompatActivity {

    private EditText nameText , l_nameText , descriptionText,ageText ;
    private Button btnSave ;
    DataBase_Helper dataBaseHelper ;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        dataBaseHelper = new DataBase_Helper(this);
        nameText =findViewById(R.id.name);
        l_nameText =findViewById(R.id.lname);
        descriptionText =findViewById(R.id.description);
        ageText =findViewById(R.id.age);
        btnSave =findViewById(R.id.btnsave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameText.getText().toString();
                String LName = l_nameText.getText().toString();
                String description = descriptionText.getText().toString();
                String age = ageText.getText().toString();

                dataBaseHelper.insertData(new Data(name, LName, description, age));
                Intent intent = new Intent(AddData.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}