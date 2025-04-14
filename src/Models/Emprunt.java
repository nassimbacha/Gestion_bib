package Models;

import java.sql.Date;

public class Emprunt {
    private int id_emprunt;
    private int id_livre;
    private int id_adhérant;
    private Date date_emprunt;
    private Date date_retour;
    private String statut_emprunt;



    // Constructeur avec tous les attributs
    public Emprunt(int id_emprunt, int id_livre, int id_adhérant, Date date_emprunt, Date date_retour, String statut_emprunt) {
        this.id_emprunt = id_emprunt;
        this.id_livre = id_livre;
        this.id_adhérant = id_adhérant;
        this.date_emprunt = date_emprunt;
        this.date_retour = date_retour;
        this.statut_emprunt = statut_emprunt;
    }

    // Getters
    public int getId_emprunt() {
        return id_emprunt;
    }

    public int getId_livre() {
        return id_livre;
    }

    public int getId_adhérant() {
        return id_adhérant;
    }

    public Date getDate_emprunt() {
        return date_emprunt;
    }

    public Date getDate_retour() {
        return date_retour;
    }

    public String getStatut_emprunt() {
        return statut_emprunt;
    }

    // Setters
    public void setId_emprunt(int id_emprunt) {
        this.id_emprunt = id_emprunt;
    }

    public void setId_livre(int id_livre) {
        this.id_livre = id_livre;
    }

    public void setId_adhérant(int id_adhérant) {
        this.id_adhérant = id_adhérant;
    }

    public void setDate_emprunt(Date date_emprunt) {
        this.date_emprunt = date_emprunt;
    }

    public void setDate_retour(Date date_retour) {
        this.date_retour = date_retour;
    }

    public void setStatut_emprunt(String statut_emprunt) {
        this.statut_emprunt = statut_emprunt;
    }

    @Override
    public String toString() {
        return "Emprunt : " + id_livre + ", " + id_adhérant + ", " + date_emprunt + ", " + date_retour+ ", "+statut_emprunt;
    }
}
