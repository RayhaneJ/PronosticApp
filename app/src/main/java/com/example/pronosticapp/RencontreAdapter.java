package com.example.pronosticapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RencontreAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Rencontre> rencontres;
    private LayoutInflater inflater;
    private Intent intent;
    PronosticDbContext DbContext;
    private long IdUser;




    public RencontreAdapter(Context context, ArrayList<Rencontre> rencontres, Intent intent){
        this.context = context;
        this.rencontres = rencontres;
        this.intent=intent;
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


        IdUser=intent.getLongExtra("UserId",0);
        DbContext= new PronosticDbContext(context);
        User AdminOrUser = DbContext.getUser(IdUser);


        convertView = inflater.inflate(R.layout.activity_pronostics_list_view, null);
        TextView nomTextView = (TextView)convertView.findViewById(R.id.nom);
        nomTextView.setText(rencontres.get(position).getNom());

        TextView equipeLocTextView = (TextView)convertView.findViewById(R.id.equipeLoc);
        equipeLocTextView.setText(rencontres.get(position).getEquipeLocal());

        TextView equipeVisTextView = (TextView)convertView.findViewById(R.id.equipeVis);
        equipeVisTextView.setText(rencontres.get(position).getEquipeVisiteur());

        TextView equipeFav = (TextView)convertView.findViewById(R.id.equipeFav);
        equipeFav.setText(rencontres.get(position).getEquipeFavorite() + " Vainqueur");

        //Layout qui affiche les boutons Modifier et Supprimer, on les cache si le User n'est pas admin
        LinearLayout LayoutACacher= (LinearLayout)convertView.findViewById(R.id.RencontreAdapter_LayoutACacherSiSimpleUser);

        final Animation animation = AnimationUtils.loadAnimation(parent.getContext(),
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
                rencontres.remove(position);
                notifyDataSetChanged();
            }
        });



        Button buttonSupprimer = (Button) convertView.findViewById(R.id.supprimer);
        View finalConvertView = convertView;
        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalConvertView.startAnimation(animation);
            }
        });
        Button modifierButton = (Button) convertView.findViewById(R.id.modifier);
        modifierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), ModifierRencontre.class);
                intent.putExtra("rencontreId", rencontres.get(position).getId());
                ((Activity)parent.getContext()).startActivityForResult(intent, 1);
            }
        });
        if(AdminOrUser.getRole()==Role.User){
            buttonSupprimer.setVisibility(View.INVISIBLE);
            modifierButton.setVisibility(View.INVISIBLE);
            LayoutACacher.setVisibility(View.GONE);
        }

        return convertView;
    }
}
