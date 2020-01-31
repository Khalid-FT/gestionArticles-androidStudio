package com.example.gestioncommandes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;
import java.util.Calendar;

public class CommandeActivity extends AppCompatActivity {

    TextView txtCodeCmd , txtMessage ;
    EditText txtDateCmd ;
    public static int code_cmd = 0 ;
    private int mYear, mMonth, mDay;
    Commande commande ;
    dbHandler db ;
    boolean cmdCreated = false  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        txtCodeCmd = (TextView) findViewById(R.id.txtCodeCmd);
        txtMessage = (TextView) findViewById(R.id.txtMessage);
        txtDateCmd = (EditText) findViewById(R.id.txtDateCmd);
        txtDateCmd.setText(dateToday());
        commande = null ;

        db = new dbHandler(this);
        /*
        db.dropRecords("commande");
        db.dropRecords("article");
        */
        if(db.getLastCode() == 0) { code_cmd=1; }
        else {code_cmd = db.getLastCode()+ 1; }
        txtCodeCmd.setText( "" + code_cmd);

    }

    public void btnCreer(View v){

            String date_cmd =  txtDateCmd.getText().toString().trim() ;
            commande = new Commande(code_cmd , date_cmd ) ;
            if(db.commandeExist(commande)){
                db.updateCommande(commande);
                txtMessage.setText("commande updated ! "+ db.getCommande(commande).getDate());
            }
            else {
                db.addCommande(commande);
                txtMessage.setText("Commande added ! " +db.getCommande(commande).getDate());
            }

            cmdCreated = true ;
    }

    public void insertDateCmd(View v){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txtDateCmd.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    public void btnComposer(View v){

        if(!cmdCreated){
            txtMessage.setText("You have to create command first !");
        }
        else{
            Intent myIntent = new Intent(CommandeActivity.this,   ArticleActivity.class);
            myIntent.putExtra("commande" ,commande) ;
            startActivity(myIntent);
        }

    }

    public  String dateToday(){

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);

        return mDay+"-"+mMonth+"-"+mYear;
    }


}
