package com.example.pronosticapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View;

public class AjouterRencontre extends AppCompatActivity {

    //Déclaration des attributs de la classe qui correspondent aux éléments XML qui nous intéresse
    int i=0;
    private EditText Equipe_Locale;
    private EditText Equipe_Visiteuse;
    private EditText Championnat;
    private EditText Date;
    private RadioGroup Equipe_favoriteRadioGroup;
    private RadioButton Equipe1;
    private RadioButton Equipe2;
    private String Equipe_Favorite_Choisie;
    private Button Confirmer;
    private Rencontre Rencontre;
    private PronosticDbContext DbContext;
    private BottomNavigationView MenuNavigateur;
    private long IdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_rencontre);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Ajouter Rencontre");
        Intent intent = getIntent();
        IdUser = intent.getLongExtra("UserId",0);

        //Mise en fonction du menu de navigation
        MenuNavigateur=(BottomNavigationView)findViewById(R.id.NavigationView);
        MenuNavigateur.setSelectedItemId(R.id.RencontreClick);

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


        //Liaison entre les attributs Java et les attributs XML
        Equipe_Locale=(EditText) findViewById(R.id.AjouterRencontre_Equipe1EditText);
        Equipe_Visiteuse=(EditText) findViewById(R.id.AjouterRencontre_Equipe2EditText);
        Championnat=(EditText) findViewById(R.id.AjouterRencontre_ChampionnatEditText);
        Date=(EditText) findViewById(R.id.AjouterRencontre_DatEditText);
        Equipe_favoriteRadioGroup=(RadioGroup) findViewById(R.id.AjouterRencontre_EquipeFavoriteRadioGroupe);
        Equipe1=(RadioButton) findViewById(R.id.AjouterRencontre_EquipeFavoriteRadioButton1);
        Equipe2=(RadioButton) findViewById(R.id.AjouterRencontre_EquipeFavoriteRadioButton2);
        Confirmer=(Button) findViewById(R.id.AjouterRencontre_ConfirmerButton);


        DbContext= new PronosticDbContext(this);

        //Si le user n'est pas un admin, on enlève la fonctionnalité de suppression//
        User AdminOrUser = DbContext.getUser(IdUser);
        if(AdminOrUser.getRole()==Role.User) {
            MenuNavigateur.getMenu().
                    findItem(R.id.RencontreClick)
                    .setVisible(false);
        }

    }

    //Méthode qui va ajouter une rencontre dans la base de données
    public void AddRencontre(View v) {
        //Structure conditionnelle qui vérifie que l'on a bien rempli tous les champs
        if (Equipe_favoriteRadioGroup.getCheckedRadioButtonId() == -1
                || TextUtils.isEmpty(Date.getText().toString())
                || TextUtils.isEmpty(Championnat.getText().toString())
                || TextUtils.isEmpty(Equipe_Locale.getText().toString())
                || TextUtils.isEmpty(Equipe_Visiteuse.getText().toString())){
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            //Récupération de l'information de l'équipe favorite choisie
            if (Equipe_favoriteRadioGroup.getCheckedRadioButtonId() == R.id.AjouterRencontre_EquipeFavoriteRadioButton1) {
                Equipe_Favorite_Choisie = Equipe_Locale.getText().toString();
            } else {
                Equipe_Favorite_Choisie = Equipe_Visiteuse.getText().toString();
            }
            //Création d'un objet Rencontre
            Rencontre = new Rencontre("rencontre_" + i,
                    Date.getText().toString(),
                    Championnat.getText().toString(),
                    Equipe_Locale.getText().toString(),
                    Equipe_Visiteuse.getText().toString(),
                    Equipe_Favorite_Choisie);

            //Insertion de la rencontre dans la base de données
            long insertion = DbContext.insertRencontre(Rencontre);
            if (insertion == -1) {
                Toast.makeText(this, "Erreur, La rencontre n'a pas pu être ajoutée", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "La rencontre a bien été ajoutée", Toast.LENGTH_SHORT).show();
                i++;
                finish();
            }
        }
    }
}
