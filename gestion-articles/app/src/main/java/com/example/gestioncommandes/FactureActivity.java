package com.example.gestioncommandes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FactureActivity extends AppCompatActivity {

    TextView txtCodeCmd, txtTotal ;
    ListView lstVwArt ;
    dbHandler db ;
    Commande commande = null ;
    List<Article> articles = new ArrayList<Article>();;
    ArtAdapter artAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facture);

        txtCodeCmd = (TextView)findViewById(R.id.txtCodeCmd) ;
        txtTotal = (TextView)findViewById(R.id.txtTotal) ;
        lstVwArt = findViewById(R.id.lstVwArt);

        db = new dbHandler(this) ;
        Intent intent= getIntent();
        commande =(Commande) intent.getSerializableExtra("commande") ;
        txtCodeCmd.setText(""+commande.getCode());
        txtTotal.setText(""+db.getTotal(commande));
        articles = db.getArticles(commande);
        artAdapter = new ArtAdapter(getApplicationContext(),articles);
        lstVwArt.setAdapter(artAdapter);


    }
}
