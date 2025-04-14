package Models;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private int anneePublication;
    private String statut;
    private int nbLivre;

    // Constructeur
    public Livre(int id, String titre, String auteur, int anneePublication, String statut, int nbLivre) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.statut = statut;
        this.nbLivre = nbLivre;
    }


    // Getters
    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public String getStatut() {
        return statut;
    }

    public int getNbLivre() {
        return nbLivre;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setNbLivre(int nbLivre) {
        this.nbLivre = nbLivre;
    }

    // Redéfinition de la méthode toString
    @Override
    public String toString() {
        return "Le Livre : " + id + ", " + titre + ", " + auteur + ", " + anneePublication+ ", "+statut;
    }


}

