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

public class ModifierRencontre extends AppCompatActivity {
    private PronosticDbContext DbContext;
    private long idToUpdate;
    private EditText equipe_Locale;
    private EditText equipe_Visiteuse;
    private EditText championnat;
    private EditText date;
    private RadioGroup equipe_favoriteRadioGroup;
    private RadioButton equipe1;
    private RadioButton equipe2;
    private String equipe_Favorite_Choisie;
    private Button confirmer;
    private Button Retour;
    private Rencontre rencontre;
    String IdUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifier_rencontre);


        Intent intent = getIntent();
        IdUser = intent.getStringExtra("UserId");


        DbContext= new PronosticDbContext(this);

        idToUpdate = intent.getLongExtra("rencontreId", 0);
        Rencontre Amodifier = DbContext.getRencontre((int)idToUpdate);

        equipe_Locale = (EditText) findViewById(R.id.ModifierRencontre_Equipe1EditText);
        equipe_Visiteuse = (EditText) findViewById(R.id.ModifierRencontre_Equipe2EditText);
        championnat = (EditText) findViewById(R.id.ModifierRencontre_ChampionnatEditText);
        date = (EditText) findViewById(R.id.ModifierRencontre_DatEditText);
        equipe_favoriteRadioGroup = (RadioGroup) findViewById(R.id.ModifierRencontre_EquipeFavoriteRadioGroupe);
        equipe1 = (RadioButton) findViewById(R.id.ModifierRencontre_EquipeFavoriteRadioButton1);
        equipe2 = (RadioButton) findViewById(R.id.ModifierRencontre_EquipeFavoriteRadioButton2);
        confirmer = (Button) findViewById(R.id.ModifierRencontre_ConfirmerButton);
        Retour= (Button) findViewById(R.id.ModifierRencontre_ReturnButton);

        equipe_Locale.setText(Amodifier.getEquipeLocal());
        equipe_Visiteuse.setText(Amodifier.getEquipeVisiteur());
        championnat.setText(Amodifier.getChampionnat());
        date.setText(Amodifier.getDate());
    }

    public void Retour(View v){
        finish();
    }

    public void UpdateRencontre(View v) {
        DbContext= new PronosticDbContext(this);
        Rencontre Amodifier = DbContext.getRencontre((int)idToUpdate);
        if (equipe_favoriteRadioGroup.getCheckedRadioButtonId() == -1
                || TextUtils.isEmpty(date.getText().toString())
                || TextUtils.isEmpty(championnat.getText().toString())
                || TextUtils.isEmpty(equipe_Locale.getText().toString())
                || TextUtils.isEmpty(equipe_Visiteuse.getText().toString())) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            if (equipe_favoriteRadioGroup.getCheckedRadioButtonId() == R.id.AjouterRencontre_EquipeFavoriteRadioButton1) {
                equipe_Favorite_Choisie = equipe_Locale.getText().toString();
            } else {
                equipe_Favorite_Choisie = equipe_Visiteuse.getText().toString();
            }
            rencontre = new Rencontre(Amodifier.getId(),
                    Amodifier.getNom(),
                    date.getText().toString(),
                    championnat.getText().toString(),
                    equipe_Locale.getText().toString(),
                    equipe_Visiteuse.getText().toString(),
                    equipe_Favorite_Choisie);

            int modification = DbContext.updateRencontre(rencontre);

            if(modification==0){
                Toast.makeText(this, "Aucune modification n'a été enregitrée", Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
            else{
                Intent returnIntent = new Intent();
                returnIntent.putExtra("rencontreIdToUpdate", rencontre.getId());
                setResult(Activity.RESULT_OK, returnIntent);
                Toast.makeText(this, "La modification a bien été enregistrée", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}