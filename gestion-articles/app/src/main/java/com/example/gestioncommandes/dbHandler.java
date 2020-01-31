package com.example.gestioncommandes;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db";
    private static final String table_Commande= "Commande";
    private static final String code_cmd = "code_cmd";
    private static final String date_cmd = "date_cmd";
    private static final String table_Article = "Article";
    private static final String id_art = "id_art";
    private static final String code_art = "code_art";
    private static final String lib_art = "lib_art";
    private static final String qte_art = "qte_art";
    private static final String pu_art = "pu_art" ;


    public dbHandler(@Nullable Context context) {
        super(context, DATABASE_NAME,null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_COMMANDE_TABLE = "CREATE TABLE \"Commande\" (\n" +
                "\t\"code_cmd\"\tINTEGER,\n" +
                "\t\"date_cmd\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"code_cmd\")\n" +
                ");" ;

        db.execSQL(CREATE_COMMANDE_TABLE);

        String CREATE_ARTICLE_TABLE = " CREATE TABLE \"Article\" (\n" +
                "\t\"id_art\"\tINTEGER,\n" +
                "\t\"code_art\"\tINTEGER,\n" +
                "\t\"lib_art\"\tTEXT,\n" +
                "\t\"qte_art\"\tINTEGER,\n" +
                "\t\"pu_art\"\tTEXT,\n" +
                "\t\"code_cmd\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"id_art\"),\n" +
                "\tFOREIGN KEY(\"code_cmd\") REFERENCES \"Commande\"(\"code_cmd\")\n" +
                "); ";

        db.execSQL(CREATE_ARTICLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + table_Commande);
        db.execSQL("DROP TABLE IF EXISTS " + table_Article);

        // Create tables again
        onCreate(db);

    }

    // code to add the new commande
    public void addCommande(Commande cmd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(date_cmd, cmd.getDate()); // Commande date
        // Inserting Row
        db.insert(table_Commande, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection

    }

    // code to update commande
    public void updateCommande(Commande commande){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(date_cmd, commande.getDate()); // Commande date
        // updating Row
        db.update(table_Commande, values,"code_cmd="+commande.getCode(), null);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        System.out.println("Commande updated !");
    }

    // code to get the single commande
    public Commande getCommande(Commande commande) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table_Commande,
                new String[]{code_cmd, date_cmd},
                code_cmd + "=?",
                new String[]{String.valueOf(commande.getCode())}, null, null, null, null);

        Commande cmd = null ;
        if( cursor != null && cursor.moveToFirst() ){
            // prepare our object
            cmd = new Commande(
                    cursor.getInt(cursor.getColumnIndex(code_cmd)),
                    cursor.getString(cursor.getColumnIndex(date_cmd)));
            // close the db connection
            cursor.close();
        }
        return cmd;
    }

    // code to check if commande exist
    public boolean commandeExist(Commande cmd) {
        String countQuery = "SELECT  * FROM  Commande where code_cmd= " +cmd.getCode()  ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        if(count > 0) return true ;
        else return false ;
    }

    // code to get last commande
    public int getLastCode() {
        String countQuery = "SELECT * FROM commande ORDER BY code_cmd DESC LIMIT 1"  ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int code = 0 ;
        if( cursor != null && cursor.moveToFirst() ){
            code = cursor.getInt(cursor.getColumnIndex(code_cmd));
            cursor.close();
        }

        return code ;
    }

    // code to add the new Article
    public void addArticle(Article art) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(code_art, art.getCode()); // Article code
        values.put(lib_art, art.getLibelle()); // Article lib
        values.put(qte_art, art.getQte()); // Article Qte
        values.put(pu_art, art.getPu()); // Article pu
        values.put(code_cmd, art.getCommande().getCode()); // commande code

        // Inserting Row
        db.insert(table_Article, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to update article
    public void updateArticle(Article art , Commande cmd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(qte_art, art.getQte()); // Article Qte

        // updating Row
        db.update(table_Article, values,"code_art="+art.getCode()+" and code_cmd="+cmd.getCode(), null);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to check if article exist
    public boolean articleExist(Article art , Commande cmd) {
        String countQuery = "SELECT  * FROM  Article where code_art="+art.getCode()+" and code_cmd= "+cmd.getCode()  ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        if(count > 0) return true ;
        else return false ;
    }

    // code to get total
    public float getTotal(Commande commande){
        String countQuery = "SELECT  sum(qte_art*pu_art) as total_art FROM  Article where code_cmd= "+commande.getCode()  ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        float total = 0.0f ;
        if (cursor != null && cursor.moveToFirst()) {
            total = cursor.getFloat(cursor.getColumnIndex("total_art"));
        }
        return total ;
    }

    // code to get all articles given a commande
    public List<Article> getArticles(Commande commande){
        List<Article> articles = new ArrayList<Article>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table_Article,
                new String[]{id_art,code_art, lib_art , qte_art ,pu_art , code_cmd},
                code_cmd + "=?",
                new String[]{String.valueOf(commande.getCode())}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                // prepare our object
                Article article = new Article(
                        cursor.getInt(cursor.getColumnIndex(code_art)),
                        cursor.getString(cursor.getColumnIndex(lib_art)),
                        cursor.getInt(cursor.getColumnIndex(qte_art)),
                        Float.parseFloat(cursor.getString(cursor.getColumnIndex(pu_art))), commande);
                articles.add(article);
                cursor.moveToNext();
            }
            // close the db connection
            cursor.close();
        }

        return articles ;
    }

    // code to drop records
    public void dropRecords(String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ table_name);
    }


}
