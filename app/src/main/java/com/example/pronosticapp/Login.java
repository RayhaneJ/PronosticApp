package com.example.pronosticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private CheckBox showpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.edTxtUsername);
        password = (EditText)findViewById(R.id.edTxtPassword);
        info = (TextView) findViewById(R.id.textView);
        login = (Button) findViewById(R.id.btLogin);
        signUp = (Button) findViewById(R.id.btInscription);
        showpassword = findViewById(R.id.cbShowPassword);
        dataDb = new PronosticDbContext(getApplicationContext());
        dataDb.insertUser(new User("gui","okok","Guillaume","WURM",Role.Admin));
        dataDb.insertUser(new User("gui3","ok","Guillaume","WURM",Role.User));
        info.setText("nb de tentatives : 5");

        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(username.getText().toString()) && !TextUtils.isEmpty(password.getText().toString()) ){
                    validate(username.getText().toString(), password.getText().toString());
                }
                else{
                    controleValidate();
                }

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
            User user = dataDb.getUserLogin(userName);
            if (!user.getMotDePasse().equals(userPassword)) {
                counter--;
                info.setText("nb de tentatives :" + String.valueOf(counter));
                if (counter == 0) {
                    login.setEnabled(false);
                }
            }
            else{
                if (user.getRole() == Role.Admin) {
                    Toast.makeText(this, " Bonjour "+ userName +" vous êtes connecté en tant qu'admin", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, Pronostics.class);
                    intent.putExtra("UserId",user.getId());
                    startActivity(intent);
                } else {
                    Toast.makeText(this, " Bonjour "+ userName +" vous êtes connecté", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, Pronostics.class);
                    intent.putExtra("UserId",user.getId());
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

    private void controleValidate(){
        Toast.makeText(this, "veuillez saisir tous les champs", Toast.LENGTH_LONG).show();
    }
}