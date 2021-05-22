package com.example.pronosticapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ModifierUser extends AppCompatActivity {

    //Déclaration des attributs de la classe qui correspondent aux éléments XML qui nous intéresse
    int i = 0;
    private EditText Prenom;
    private EditText Nom;
    private EditText Email;
    private EditText Mdp;
    private Button Confirmer;
    private User user;
    private PronosticDbContext DbContext;
    private BottomNavigationView MenuNavigateur;
    private long IdUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifier_user);

        Intent intent = getIntent();
        IdUser = intent.getLongExtra("UserId",0);

        //Mise en fonction du menu de navigation
        MenuNavigateur=(BottomNavigationView)findViewById(R.id.NavigationView);
        MenuNavigateur.setSelectedItemId(R.id.ProfilClick);


        MenuNavigateur.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.RencontreClick:
                        Intent intent = new Intent(getApplicationContext(), AjouterRencontre.class);
                        intent.putExtra("UserId", IdUser);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.HomeClick:
                        intent = new Intent(getApplicationContext(), Pronostics.class);
                        intent.putExtra("UserId", IdUser);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ProfilClick:
                        intent = new Intent(getApplicationContext(), ModifierUser.class);
                        intent.putExtra("UserId", IdUser);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        MenuNavigateur.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.RencontreClick:
                        Intent intent = new Intent(getApplicationContext(), AjouterRencontre.class);
                        intent.putExtra("UserId", IdUser);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.HomeClick:
                        intent = new Intent(getApplicationContext(), Pronostics.class);
                        intent.putExtra("UserId", IdUser);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ProfilClick:
                        intent = new Intent(getApplicationContext(), ModifierUser.class);
                        intent.putExtra("UserId", IdUser);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        break;
                }
            }
        });

        DbContext= new PronosticDbContext(this);

        //Si le user n'est pas un admin, on enlève la fonctionnalité de suppression//
        User AdminOrUser = DbContext.getUser(IdUser);
        if(AdminOrUser.getRole()==Role.User) {
            MenuNavigateur.getMenu().
                    findItem(R.id.RencontreClick)
                    .setVisible(false);
        }

        User Amodifier = DbContext.getUser(IdUser);

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
            User  Amodifier= new User(DbContext.getUser(IdUser).getId(),
                    Email.getText().toString(),
                    Mdp.getText().toString(),
                    Nom.getText().toString(),
                    Prenom.getText().toString(),
                    DbContext.getUser(IdUser).getRole()
                    );

            //Modification du User dans la base de données
            int modification =DbContext.updateUser(Amodifier);

            if(modification==0){
                MenuNavigateur.setSelectedItemId(R.id.HomeClick);
                Toast.makeText(this, "Aucune modification n'a été enregitrée", Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
            else{
                MenuNavigateur.setSelectedItemId(R.id.HomeClick);
                Toast.makeText(this, "La modification a bien été enregistrée", Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("UserId",IdUser);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }
    }

}