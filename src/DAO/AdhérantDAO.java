package DAO;

import Models.Adhérant;//Objets
import BDD.BiblioData;//connexion BDD

import java.sql.*;//class avec les methodes qui permet de gerer une BDD
import java.util.ArrayList;//class qui permet de cree listes dynamique
// (pour afficher par ex liste des livres) la liste est une variable qui peut stocker dans des tableaux
import java.util.List;

public class AdhérantDAO {

    // ajouterAdhérant : Méthode avec requête pour ajouter un nouvel adhérent dans la table Adhérant
    public void ajouterAdhérant(Adhérant adhérant) throws SQLException {
        //Ouverture d'une connexion avec appel de la methode getConnection() de la class bibliodata
        Connection connexion = BiblioData.getConnection();
        //Requete sql qui permet d'ajouter un adherent en utilisant les methodes de la class java.sql.*;
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "INSERT INTO adhérant (Nom_adhérant, Prénom_adhérant, Date_adhésion) VALUES (?, ?, ?)"
        );
        //Ecriture sur la BDD en ignorant id_adhérant, vue qu il est autoincremete sur la BDD
        preparedStatement.setString(1, adhérant.getNom_adhérant());
        preparedStatement.setString(2, adhérant.getPrénom_adhérant());
        preparedStatement.setDate(3, adhérant.getDate_adhésion());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        //on a laisse la connexion ouverte pour ne pas etre oblige de gerer l'ouverture et fermeture de la connexion
        //connexion.close();
    }

    // modifierAdhérant : Méthode avec requête pour modifier un adhérent dans la table Adhérant
    public void modifierAdhérant(Adhérant adhérant) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "UPDATE adhérant SET Nom_adhérant = ?, Prénom_adhérant = ?, Date_adhésion = ? WHERE Id_adhérant = ?"
        );
        preparedStatement.setString(1, adhérant.getNom_adhérant());
        preparedStatement.setString(2, adhérant.getPrénom_adhérant());
        preparedStatement.setDate(3, adhérant.getDate_adhésion());
        preparedStatement.setInt(4, adhérant.getId_adhérant());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        //connexion.close();
    }

    // supprimerAdhérant : Méthode avec requête pour supprimer un adhérent de la table Adhérant
    public void supprimerAdhérant(int idAdhérant) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "DELETE FROM adhérant WHERE Id_adhérant = ?"
        );
        preparedStatement.setInt(1, idAdhérant);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        //connexion.close();
    }

    // AdhérantEmprunt : Méthode avec requête pour vérifier si un adhérent a des emprunts dans la table emprunts avec Statut_emprunt égale à « Emprunté »
    public boolean AdhérantEmprunt(int idAdhérant) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "SELECT COUNT(*) FROM emprunts WHERE Id_adhérant = ? AND Statut_emprunt = 'Emprunté'"
        );
        preparedStatement.setInt(1, idAdhérant);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        preparedStatement.close();
        resultSet.close();
        //connexion.close();
        return count > 0;
    }

    // AdhérantById : Méthode avec requête pour récupérer un adhérent par son ID
    public Adhérant AdhérantById(int idAdhérant) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "SELECT Id_adhérant, Nom_adhérant, Prénom_adhérant, Date_adhésion FROM adhérant WHERE Id_adhérant = ?"
        );
        preparedStatement.setInt(1, idAdhérant);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Adhérant adhérant = new Adhérant(
                    resultSet.getInt("Id_adhérant"),
                    resultSet.getString("Nom_adhérant"),
                    resultSet.getString("Prénom_adhérant"),
                    resultSet.getDate("Date_adhésion")
            );
            preparedStatement.close();
            resultSet.close();
            //connexion.close();
            return adhérant;
        }
        preparedStatement.close();
        resultSet.close();
        //connexion.close();
        return null;
    }


    // ListAdhérant : Méthode avec requête pour récupérer tous les adhérents
    public List<Adhérant> ListAdhérant() throws SQLException {
        Connection connexion = BiblioData.getConnection();
        Statement statement = connexion.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT Id_adhérant, Nom_adhérant, Prénom_adhérant, Date_adhésion FROM adhérant");
        List<Adhérant> adhérants = new ArrayList<>();
        while (resultSet.next()) {
            Adhérant adhérant = new Adhérant(
                    resultSet.getInt("Id_adhérant"),
                    resultSet.getString("Nom_adhérant"),
                    resultSet.getString("Prénom_adhérant"),
                    resultSet.getDate("Date_adhésion")
            );
            adhérants.add(adhérant);
        }
        statement.close();
        resultSet.close();
        //connexion.close();
        return adhérants;
    }

}

