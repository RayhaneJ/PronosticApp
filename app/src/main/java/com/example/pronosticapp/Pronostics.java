package com.example.pronosticapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Pronostics extends AppCompatActivity {
    private ListView rencontresListView;
    private PronosticDbContext pronosticDbContext;
    private ArrayList<Rencontre> rencontres;
    private RencontreAdapter rencontreAdapter;
    private int selectedRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronostics);
        setTitle("Rencontres");
        rencontres = new ArrayList<>();
        rencontres.add(new Rencontre("Rencontre des titans", "10/20/09", "Ligue des champions", "PSG", "Lyon", "PSG"));
        rencontres.add(new Rencontre("Rencontre des null", "10/20/09", "Ligue des null", "Lyon", "PSG", "Lyon"));
        rencontres.add(new Rencontre("Rencontre des mandarins", "10/20/09", "Ligue des mandarins", "Lille", "Lyon", "Lyon"));
        rencontresListView = (ListView)findViewById(R.id.rencontresListView);
        rencontreAdapter = new RencontreAdapter(getApplicationContext(), rencontres);
        rencontresListView.setAdapter(rencontreAdapter);

        final Animation animation = AnimationUtils.loadAnimation(this,
                R.anim.slide_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rencontres.remove(selectedRow);
                rencontreAdapter.notifyDataSetChanged();
            }
        });

        rencontresListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.startAnimation(animation);
                selectedRow = i;
            }
        });
    }


}