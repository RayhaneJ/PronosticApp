package com.example.pronosticapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
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
    EditText Equipe_Locale;
    EditText Equipe_Visiteuse;
    EditText Championnat;
    EditText Date;
    RadioGroup Equipe_favoriteRadioGroup;
    RadioButton Equipe1;
    RadioButton Equipe2;
    String Equipe_Favorite_Choisie;
    Button Confirmer;
    Rencontre Rencontre;
    PronosticDbContext DbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_rencontre);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

    }


    public void AddRencontre(View v) {
        if (Equipe_favoriteRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Veuillez choisir une équipe favorite", Toast.LENGTH_SHORT).show();
        } else {
            if (Equipe_favoriteRadioGroup.getCheckedRadioButtonId() == R.id.AjouterRencontre_EquipeFavoriteRadioButton1) {
                Equipe_Favorite_Choisie = Equipe_Locale.getText().toString();
            } else {
                Equipe_Favorite_Choisie = Equipe_Visiteuse.getText().toString();
            }
            Rencontre = new Rencontre("rencontre_" + i,
                    Date.getText().toString(),
                    Championnat.getText().toString(),
                    Equipe_Locale.getText().toString(),
                    Equipe_Visiteuse.getText().toString(),
                    Equipe_Favorite_Choisie);

            long insertion = DbContext.insertRencontre(Rencontre);
            if (insertion == -1) {
                Toast.makeText(this, "Erreur, La rencontre n'a pas pu être ajoutée", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "La rencontre a bien été ajoutée", Toast.LENGTH_SHORT).show();
                i++;
            }
        }
    }
}
