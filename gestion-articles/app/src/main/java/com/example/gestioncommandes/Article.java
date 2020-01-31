package com.example.gestioncommandes;

public class Article {

    private int code ;
    private String libelle ;
    private int qte ;
    private float pu ;
    private Commande commande ;


    public Article(int code, String libelle, int qte, float pu , Commande commande) {

        this.code = code;
        this.libelle = libelle;
        this.qte = qte;
        this.pu = pu ;
        this.commande = commande;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public float getPu() {
        return pu;
    }

    public void setPu(float pu) {
        this.pu = pu;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

   public String toString(){
        return this.getLibelle() ;
   }




}
