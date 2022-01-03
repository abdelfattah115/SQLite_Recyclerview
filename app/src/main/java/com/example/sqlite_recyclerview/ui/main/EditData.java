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

public class EditData extends AppCompatActivity {

    private EditText nameText , l_nameText , descriptionText,ageText ;
    private Button btnedit ;
    private DataBase_Helper dataBaseHelper ;
    Data personinfo ;
    String str_position;
    int position;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        dataBaseHelper = new DataBase_Helper(this);

        Bundle bundle = getIntent().getExtras();
        str_position = bundle.getString("position");
        position = Integer.parseInt(str_position);

        personinfo = dataBaseHelper.getData(position);

        nameText =findViewById(R.id.name1);
        l_nameText =findViewById(R.id.lname1);
        ageText =findViewById(R.id.age1);
        descriptionText =findViewById(R.id.description1);

        btnedit =findViewById(R.id.btnedit);

        if(personinfo != null){
            nameText.setText(personinfo.getName());
            l_nameText.setText(personinfo.getLname());
            descriptionText.setText(personinfo.getDescription());
            ageText.setText(personinfo.getAge());
        }

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personinfo.setName(nameText.getText().toString());
                personinfo.setLname(l_nameText.getText().toString());
                personinfo.setDescription(descriptionText.getText().toString());
                personinfo.setAge(ageText.getText().toString());

                dataBaseHelper.updateData(personinfo);
                MainActivity.notifyAdapter();
                Intent intent = new Intent(EditData.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}