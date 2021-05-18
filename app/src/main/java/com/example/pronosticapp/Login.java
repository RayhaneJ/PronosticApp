package com.example.pronosticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class Login extends AppCompatActivity {


    private EditText username, password;
    private TextView info;
    private Button login, signUp;
    private int counter = 5;
    private PronosticDbContext dataDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.edTxtUsername);
        password = (EditText)findViewById(R.id.edTxtPassword);
        info = (TextView) findViewById(R.id.textView);
        login = (Button) findViewById(R.id.btLogin);
        signUp = (Button) findViewById(R.id.btInscription);
        dataDb = new PronosticDbContext(getApplicationContext());
        dataDb.insertUser(new User("gui","okok","Guillaume","WURM",Role.Admin));
        info.setText("nb de tentatives : 5");


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(username.getText().toString(), password.getText().toString());
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Inscription.class);
                startActivity(intent);
            }
        });
    }

    private void validate (String userName, String userPassword){
        try {
            User user = dataDb.getUser(userName);

            if (user.getMotDePasse().equals(userPassword)) {
                if (user.getRole() == Role.Admin) {
                    Intent intent = new Intent(Login.this, SecondActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Login.this, SecondActivity.class);
                    startActivity(intent);
                }
            }
        }
        catch (CursorIndexOutOfBoundsException ex){
            counter--;
            info.setText("nb de tentatives :" + String.valueOf(counter));
            if (counter == 0) {
                login.setEnabled(false);
            }
        }
    }
}