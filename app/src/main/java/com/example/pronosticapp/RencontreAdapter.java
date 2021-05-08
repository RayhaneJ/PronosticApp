package com.example.pronosticapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RencontreAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Rencontre> rencontres;
    private LayoutInflater inflater;

    public RencontreAdapter(Context context, ArrayList<Rencontre> rencontres){
        this.context = context;
        this.rencontres = rencontres;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return rencontres.size();
    }

    @Override
    public Rencontre getItem(int position) {
        return rencontres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_pronostics_list_view, null);
        TextView nomTextView = (TextView)convertView.findViewById(R.id.nom);
        nomTextView.setText(rencontres.get(position).getNom());
        TextView equipeLocTextView = (TextView)convertView.findViewById(R.id.equipeLoc);
        equipeLocTextView.setText(rencontres.get(position).getEquipeLocal());
        TextView equipeVisTextView = (TextView)convertView.findViewById(R.id.equipeVis);
        equipeVisTextView.setText(rencontres.get(position).getEquipeVisiteur());
        TextView equipeFav = (TextView)convertView.findViewById(R.id.equipeFav);
        equipeFav.setText(rencontres.get(position).getEquipeFavorite() + " Vainqueur");
        return convertView;
    }
}
