package com.example.pronosticapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Pronostics extends AppCompatActivity {
    private Context context;
    private ListView rencontresListView;
    private PronosticDbContext pronosticDbContext;
    private ArrayList<Rencontre> rencontres;
    private RencontreAdapter rencontreAdapter;
    private BottomNavigationView MenuNavigateur;
    PronosticDbContext DbContext;
    String IdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronostics);
        setTitle("Rencontres");

        Intent intent = getIntent();
        IdUser = intent.getStringExtra("UserId");

        //Mise en fonction du menu de navigation
        MenuNavigateur=(BottomNavigationView)findViewById(R.id.NavigationView);
        MenuNavigateur.setSelectedItemId(R.id.HomeClick);

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

        pronosticDbContext = new PronosticDbContext(getApplicationContext());
        rencontres = new ArrayList<>();
        long id1 = pronosticDbContext.insertRencontre(new Rencontre("Rencontre des titans", "10/20/09", "Ligue des champions", "PSG", "Lyon", "PSG"));
        long id2 = pronosticDbContext.insertRencontre(new Rencontre("Rencontre des null", "10/20/09", "Ligue des null", "Lyon", "PSG", "Lyon"));
        long id3 = pronosticDbContext.insertRencontre(new Rencontre("Rencontre des mandarins", "10/20/09", "Ligue des mandarins", "Lille", "Lyon", "Lyon"));
        rencontres.add(new Rencontre(id1, "Rencontre des titans", "10/20/09", "Ligue des champions", "PSG", "Lyon", "PSG"));
        rencontres.add(new Rencontre(id2, "Rencontre des null", "10/20/09", "Ligue des null", "Lyon", "PSG", "Lyon"));
        rencontres.add(new Rencontre(id3, "Rencontre des mandarins", "10/20/09", "Ligue des mandarins", "Lille", "Lyon", "Lyon"));
        rencontresListView = (ListView)findViewById(R.id.rencontresListView);
        rencontreAdapter = new RencontreAdapter(getApplicationContext(), rencontres);
        rencontresListView.setAdapter(rencontreAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Rencontre rencontre = pronosticDbContext.getRencontre((int)data.getLongExtra("rencontreIdToUpdate", 0));
                for(int i = 0; i< rencontres.size(); i++){
                    if(rencontres.get(i).getId() == rencontre.getId()){
                        rencontres.set(i, rencontre);
                    }
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}