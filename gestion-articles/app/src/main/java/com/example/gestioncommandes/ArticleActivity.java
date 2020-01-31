package com.example.gestioncommandes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;



public class ArticleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView txtCodeCmd , txtCodeArt ,txtPuArt, txtMessage;
    Spinner txtLibelle ;
    Commande commande = null ;
    EditText txtQte ;
    dbHandler db ;
    JsonData jsonData ;
    List<Article> articles ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        txtCodeCmd = (TextView)findViewById(R.id.txtCodeCmd) ;
        txtCodeArt = (TextView)findViewById(R.id.txtCodeArt) ;
        txtLibelle = (Spinner)findViewById(R.id.txtLibelle) ;
        txtQte = (EditText) findViewById(R.id.txtQte) ;
        txtPuArt = (TextView) findViewById(R.id.txtPuArt) ;
        txtMessage = (TextView)findViewById(R.id.txtMessage) ;
        articles =new ArrayList<>();
/*
        articles = new HashMap<String, String[]>();
        articles.put("art1" , new String[]{ "1" , "5.2f"});
        articles.put("art2" , new String[]{ "2" , "3.2f"});
        articles.put("art3" , new String[]{ "3" , "10.0f"});
        articles.put("art4" , new String[]{ "4" ,"7.8f"});
        articles.put("art5" , new String[]{ "5" ,"6.5f"});
        articles.put("art6" , new String[]{ "6" ,"4.6f"});
        articles.put("art7" , new String[]{ "7" ,"1.5f"});
        articles.put("art8" , new String[]{ "8" ,"6.5f"});
        articles.put("art9" , new String[]{ "9" ,"5.5f"});
        articles.put("art10" , new String[]{ "10" ,"6.4f"});

        Set<String> keySet = articles.keySet();
        ArrayList<String> arts = new ArrayList<String>(keySet);
*/

        jsonData = this.getClient().create(JsonData.class);
        Call<List<Article>> call = jsonData.getArticle();

        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                Log.d("TAG",response.code()+"");
                String displayResponse = "";
                articles = response.body();
                //Creating the ArrayAdapter instance having the articles list
                ArrayAdapter aa = new ArrayAdapter(ArticleActivity.this,android.R.layout.simple_spinner_item,articles);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                txtLibelle.setAdapter(aa);

            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                call.cancel();
                Log.e("err","error");
            }
        });


        db = new dbHandler(this) ;
        Intent intent= getIntent();
        commande =(Commande) intent.getSerializableExtra("commande") ;
        txtCodeCmd.setText(""+commande.getCode());
        txtLibelle.setOnItemSelectedListener(this);


    }

    // button add article
    public void btnAjouter(View v){

        if( txtLibelle != null && txtLibelle.getSelectedItem() !=null){
            String code= txtCodeArt.getText().toString() ;
            String libelle = txtLibelle.getSelectedItem().toString() ;
            String qte = txtQte.getText().toString();
            String pu = txtPuArt.getText().toString();
            if( qte.isEmpty()){txtMessage.setText("Qte is empty ?!");}
            else{
                int code_art = Integer.parseInt(code);
                int qte_art = Integer.parseInt(qte);
                float pu_art = Float.parseFloat(pu) ;
                Article art = new Article(code_art, libelle, qte_art, pu_art , commande);
                if(db.articleExist(art , commande)){
                    db.updateArticle(art , commande);
                    txtMessage.setText( libelle+" updated ! ");
                }
                else {
                    db.addArticle(art);
                    txtMessage.setText( libelle+" Added ! ");
                }
            }
        }
        else {
            txtMessage.setText(" No article added ! check your connection.");
        }


    }

    // button Facture
    public void btnFacture(View v){

            Intent myIntent = new Intent(ArticleActivity.this,   FactureActivity.class);
            myIntent.putExtra("commande" ,commande) ;
            startActivity(myIntent);
    }

    // Spinner items
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Article art = (Article) parent.getSelectedItem();
        txtCodeArt.setText(""+art.getCode());
        txtPuArt.setText(""+art.getPu());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // web api data
    private static Retrofit retrofit = null;
    static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.20.0.56:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;

    }
}
