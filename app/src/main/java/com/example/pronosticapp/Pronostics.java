package com.example.pronosticapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Pronostics extends AppCompatActivity {
    private ListView rencontresListView;
    PronosticDbContext pronosticDbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronostics);
        setTitle("Rencontres");
        ArrayList<Rencontre> rencontres = new ArrayList<>();
        rencontres.add(new Rencontre("Rencontre des titans", "10/20/09", "Ligue des champions", "PSG", "Lyon", "PSG"));
        rencontres.add(new Rencontre("Rencontre des null", "10/20/09", "Ligue des null", "Lyon", "PSG", "Lyon"));
        rencontres.add(new Rencontre("Rencontre des mandarins", "10/20/09", "Ligue des mandarins", "Lille", "Lyon", "Lyon"));
        rencontresListView = (ListView)findViewById(R.id.rencontresListView);
        RencontreAdapter rencontreAdapter = new RencontreAdapter(getApplicationContext(), rencontres);
        rencontresListView.setAdapter(rencontreAdapter);
    }
}