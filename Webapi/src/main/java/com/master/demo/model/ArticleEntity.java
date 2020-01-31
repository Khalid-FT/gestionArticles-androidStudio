package com.master.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "Article", schema = "public", catalog = "mbd")
public class ArticleEntity {
    private int code;
    private String libelle;
    private Double pu;


    @Id
    @Column(name = "code_art")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Basic
    @Column(name = "lib_art")
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Basic
    @Column(name = "pu_art")
    public Double getPu() {
        return pu;
    }

    public void setPu(Double pu) {
        this.pu = pu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticleEntity that = (ArticleEntity) o;

        if (code != that.code) return false;
        if (libelle != null ? !libelle.equals(that.libelle) : that.libelle != null) return false;
        if (pu != null ? !pu.equals(that.pu) : that.pu != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        result = 31 * result + (pu != null ? pu.hashCode() : 0);
        return result;
    }
}
