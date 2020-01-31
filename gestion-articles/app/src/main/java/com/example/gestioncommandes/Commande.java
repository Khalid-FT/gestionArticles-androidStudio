package com.example.gestioncommandes;

import java.io.Serializable;

public class Commande implements Serializable {

    private int code ;
    private String date ;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Commande(int code, String date) {
        this.code = code;
        this.date = date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
