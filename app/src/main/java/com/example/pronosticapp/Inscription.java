package com.example.pronosticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class Inscription extends AppCompatActivity {

    private EditText email, motDePasse, prenom, nom;
    private Button trtInscription;
    private PronosticDbContext dataDb;
    private CheckBox showpasswordInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        email = (EditText)findViewById(R.id.etEmail);
        motDePasse = (EditText)findViewById(R.id.etPassword);
        prenom = (EditText)findViewById(R.id.etPrenom);
        nom = (EditText)findViewById(R.id.etNom);
        trtInscription = (Button)findViewById(R.id.btInscriptionTrt);
        showpasswordInscription = findViewById(R.id.cbPasswordInscription);
        dataDb = new PronosticDbContext(getApplicationContext());

        showpasswordInscription.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    motDePasse.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    motDePasse.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        /*trtInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser(view);
            }
        });*/
    }

    public void addUser(View view) {
        try{
            if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(motDePasse.getText().toString()) && !TextUtils.isEmpty(prenom.getText().toString()) && !TextUtils.isEmpty(nom.getText().toString())) {
                dataDb.getUser(email.getText().toString()); // lance l'exception
                Toast.makeText(this, "User déja existant", Toast.LENGTH_SHORT).show();
            }
            else{
                controleValidate();
            }
        }
        catch (CursorIndexOutOfBoundsException ex){
            Toast.makeText(this, " Bonjour "+ prenom.getText().toString() +" utilisateur creer", Toast.LENGTH_LONG).show();
            User user = new User(email.getText().toString(), motDePasse.getText().toString(), prenom.getText().toString(), nom.getText().toString(), Role.User);
            dataDb.insertUser(user);
            Intent intent = new Intent(Inscription.this, Login.class);
            startActivity(intent);
        }


    }

    private void controleValidate(){
        Toast.makeText(this, "veuillez saisir tous les champs", Toast.LENGTH_LONG).show();
    }
}