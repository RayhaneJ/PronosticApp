package com.example.pronosticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ModifierRencontre extends AppCompatActivity {


    //Déclaration des attributs de la classe qui correspondent aux éléments XML qui nous intéresse
    int i = 0;
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
        setContentView(R.layout.modifier_rencontre);

        DbContext= new PronosticDbContext(this);

        Rencontre Amodifier = DbContext.getRencontre(1);

        //Liaison entre les attributs Java et les attributs XML
        Equipe_Locale = (EditText) findViewById(R.id.ModifierRencontre_Equipe1EditText);
        Equipe_Visiteuse = (EditText) findViewById(R.id.ModifierRencontre_Equipe2EditText);
        Championnat = (EditText) findViewById(R.id.ModifierRencontre_ChampionnatEditText);
        Date = (EditText) findViewById(R.id.ModifierRencontre_DatEditText);
        Equipe_favoriteRadioGroup = (RadioGroup) findViewById(R.id.ModifierRencontre_EquipeFavoriteRadioGroupe);
        Equipe1 = (RadioButton) findViewById(R.id.ModifierRencontre_EquipeFavoriteRadioButton1);
        Equipe2 = (RadioButton) findViewById(R.id.ModifierRencontre_EquipeFavoriteRadioButton2);
        Confirmer = (Button) findViewById(R.id.ModifierRencontre_ConfirmerButton);

        //On remplit les champs par les valeurs de la rencontre que l'on souhaite modifier
        Equipe_Locale.setText(Amodifier.getEquipeLocal());
        Equipe_Visiteuse.setText(Amodifier.getEquipeVisiteur());
        Championnat.setText(Amodifier.getChampionnat());
        Date.setText(Amodifier.getDate());
    }

    //Méthode de mise à jour de rencontre
    public void UpdateRencontre(View v) {
        DbContext= new PronosticDbContext(this);

        Rencontre Amodifier = DbContext.getRencontre(1);
        if (Equipe_favoriteRadioGroup.getCheckedRadioButtonId() == -1
                || TextUtils.isEmpty(Date.getText().toString())
                || TextUtils.isEmpty(Championnat.getText().toString())
                || TextUtils.isEmpty(Equipe_Locale.getText().toString())
                || TextUtils.isEmpty(Equipe_Visiteuse.getText().toString())) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            //Récupération de l'information de l'équipe favorite choisie
            if (Equipe_favoriteRadioGroup.getCheckedRadioButtonId() == R.id.AjouterRencontre_EquipeFavoriteRadioButton1) {
                Equipe_Favorite_Choisie = Equipe_Locale.getText().toString();
            } else {
                Equipe_Favorite_Choisie = Equipe_Visiteuse.getText().toString();
            }
            //Création d'un objet Rencontre
            Rencontre = new Rencontre(Amodifier.getId(),
                    Amodifier.getNom(),
                    Date.getText().toString(),
                    Championnat.getText().toString(),
                    Equipe_Locale.getText().toString(),
                    Equipe_Visiteuse.getText().toString(),
                    Equipe_Favorite_Choisie);

            //Modification de la rencontre dans la base de données
            int modification =DbContext.updateRencontre(Rencontre);

            if(modification==0){
                Toast.makeText(this, "Aucune modification n'a été enregitrée", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "La modification a bien été enregistrée", Toast.LENGTH_SHORT).show();
            }
        }
    }
}