package com.example.pronosticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ModifierUser extends AppCompatActivity {

    //Déclaration des attributs de la classe qui correspondent aux éléments XML qui nous intéresse
    int i = 0;
    EditText Prenom;
    EditText Nom;
    EditText Email;
    EditText Mdp;
    Button Confirmer;
    User user;
    String idToUpdate;
    PronosticDbContext DbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifier_user);

        DbContext= new PronosticDbContext(this);

        Intent intent = getIntent();
        idToUpdate = intent.getStringExtra("Email");
        User Amodifier = DbContext.getUser(idToUpdate);

        //Liaison entre les attributs Java et les attributs XML
        Prenom = (EditText) findViewById(R.id.ModifierUser_PrenomEditText);
        Nom = (EditText) findViewById(R.id.ModifierUser_NomEditText);
        Email = (EditText) findViewById(R.id.ModifierUser_EmailEditText);
        Mdp = (EditText) findViewById(R.id.ModifierUser_MdpEditText);
        Confirmer = (Button) findViewById(R.id.ModifierUser_ConfirmerButton);

        //On remplit les champs par les valeurs du user que l'on souhaite modifier
        Prenom.setText(Amodifier.getPrenom());
        Nom.setText(Amodifier.getNom());
        Email.setText(Amodifier.getEmail());
        Mdp.setText(Amodifier.getMotDePasse());

    }


    //Méthode de mise à jour de rencontre
    public void UpdateUser(View v) {

        if (TextUtils.isEmpty(Prenom.getText().toString())
                || TextUtils.isEmpty(Nom.getText().toString())
                || TextUtils.isEmpty(Email.getText().toString())
                || TextUtils.isEmpty(Mdp.getText().toString())) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            //Création d'un objet User
            User  Amodifier= new User(Email.getText().toString(),
                    Mdp.getText().toString(),
                    Nom.getText().toString(),
                    Prenom.getText().toString(),
                    DbContext.getUser("sefkan@gmail.com").getRole()
                    );

            //Modification du User dans la base de données
            int modification =DbContext.updateUser(Amodifier);

            if(modification==0){
                Toast.makeText(this, "Aucune modification n'a été enregitrée", Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
            else{
                Toast.makeText(this, "La modification a bien été enregistrée", Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("UserIdToUpdate", Amodifier.getId());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }
    }

}