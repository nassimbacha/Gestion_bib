package Models;

import java.sql.Date;

public class Adhérant {
    private int id_adhérant;
    private String nom_adhérant;
    private String prénom_adhérant;
    private Date date_adhésion;



    // Constructeur avec tous les attributs
    public Adhérant(int id_adhérant, String nom_adhérant, String prénom_adhérant, Date date_adhésion) {
        this.id_adhérant = id_adhérant;
        this.nom_adhérant = nom_adhérant;
        this.prénom_adhérant = prénom_adhérant;
        this.date_adhésion = date_adhésion;
    }

    // Getters
    public int getId_adhérant() {
        return id_adhérant;
    }

    public String getNom_adhérant() {
        return nom_adhérant;
    }

    public String getPrénom_adhérant() {
        return prénom_adhérant;
    }

    public Date getDate_adhésion() {
        return date_adhésion;
    }

    // Setters
    public void setId_adhérant(int id_adhérant) {
        this.id_adhérant = id_adhérant;
    }

    public void setNom_adhérant(String nom_adhérant) {
        this.nom_adhérant = nom_adhérant;
    }

    public void setPrénom_adhérant(String prénom_adhérant) {
        this.prénom_adhérant = prénom_adhérant;
    }

    public void setDate_adhésion(Date date_adhésion) {
        this.date_adhésion = date_adhésion;
    }

    // Redéfinition de la méthode toString
    @Override
    public String toString() {
        return "L'Adhérent: " + id_adhérant + ", " + nom_adhérant + ", " + prénom_adhérant + ", " + date_adhésion;

    }





}
