package com.example.pronosticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Inscription extends AppCompatActivity {

    private EditText email, motDePasse, prenom, nom;
    private Button trtInscription;
    private PronosticDbContext dataDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        email = (EditText)findViewById(R.id.etEmail);
        motDePasse = (EditText)findViewById(R.id.etPassword);
        prenom = (EditText)findViewById(R.id.etPrenom);
        nom = (EditText)findViewById(R.id.etNom);
        trtInscription = (Button)findViewById(R.id.btInscriptionTrt);
        dataDb = new PronosticDbContext(getApplicationContext());
    }

    public void addUser(View view){
        //if(dataDb.getUser(email.getText().toString()) == null){
            User user = new User(email.getText().toString(),motDePasse.getText().toString(),nom.getText().toString(), prenom.getText().toString(), Role.User);
            long id = dataDb.insertUser(user);
       /* }
        else{
            Toast.makeText(this, "User déja existant", Toast.LENGTH_SHORT).show();
        }*/
    }
}