package DAO;


import Models.Livre; //Objets
import BDD.BiblioData;//connexion BDD

import java.sql.*;//requete
import java.util.ArrayList;//liste dynamique
import java.util.List; // Liste

public class LivreDAO {

    // ajouterLivre : pour ajouter un nouveau livre sur la BDD, à mettre le Statut égale par défaut à « Disponible » dans la table Livres
    public void ajouterLivre(Livre livre) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "INSERT INTO Livres (Titre, Auteur, Annee_publication, Statut, Nb_livre) VALUES (?, ?, ?, 'Disponible', ?)"
        );
        preparedStatement.setString(1, livre.getTitre());
        preparedStatement.setString(2, livre.getAuteur());
        preparedStatement.setInt(3, livre.getAnneePublication());
        preparedStatement.setInt(4, livre.getNbLivre());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        //connexion.close(); // Laisser la fermeture de la connexion à l'appelant
    }

    // modifierLivre : pour mettre à jour les données d’un livre sur la BDD
    public void modifierLivre(Livre livre) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "UPDATE Livres SET Titre = ?, Auteur = ?, Annee_publication = ?, Statut = ?, Nb_livre = ? WHERE Id = ?"
        );
        preparedStatement.setString(1, livre.getTitre());
        preparedStatement.setString(2, livre.getAuteur());
        preparedStatement.setInt(3, livre.getAnneePublication());
        preparedStatement.setString(4, livre.getStatut());
        preparedStatement.setInt(5, livre.getNbLivre());
        preparedStatement.setInt(6, livre.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        //connexion.close();  // Laisser la fermeture de la connexion à l'appelant
    }

    // LivreEmprunte : vérifie si le livre existe dans la table Emprunt avec Statut_emprunt égale à « Emprunté »
    public boolean LivreEmprunte(int idLivre) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "SELECT COUNT(*) FROM emprunts WHERE Id_livre = ? AND Statut_emprunt = 'Emprunté'"
        );
        preparedStatement.setInt(1, idLivre);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        preparedStatement.close();
        resultSet.close();
        //connexion.close();  // Laisser la fermeture de la connexion à l'appelant
        return count > 0;
    }

    // LivreById : méthode avec requête pour récupérer un livre par ID
    public Livre LivreById(int id) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "SELECT Id, Titre, Auteur, Annee_publication, Statut, Nb_livre FROM Livres WHERE Id = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Livre livre = new Livre(
                    resultSet.getInt("Id"),
                    resultSet.getString("Titre"),
                    resultSet.getString("Auteur"),
                    resultSet.getInt("Annee_publication"),
                    resultSet.getString("Statut"),
                    resultSet.getInt("Nb_livre")
            );
            preparedStatement.close();
            resultSet.close();
            //connexion.close(); // Laisser la fermeture de la connexion à l'appelant
            return livre;
        }
        preparedStatement.close();
        resultSet.close();
        //connexion.close();  // Laisser la fermeture de la connexion à l'appelant
        return null; // Retourne null si aucun livre n'est trouvé
    }

    // AllLivres : méthode avec requête pour récupérer tous les livres
    public List<Livre> AllLivres() throws SQLException {
        Connection connexion = BiblioData.getConnection();
        Statement statement = connexion.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT Id, Titre, Auteur, Annee_publication, Statut, Nb_livre FROM Livres");
        List<Livre> livres = new ArrayList<>();
        while (resultSet.next()) {
            Livre livre = new Livre(
                    resultSet.getInt("Id"),
                    resultSet.getString("Titre"),
                    resultSet.getString("Auteur"),
                    resultSet.getInt("Annee_publication"),
                    resultSet.getString("Statut"),
                    resultSet.getInt("Nb_livre")
            );
            livres.add(livre);
        }
        statement.close();
        resultSet.close();
        //connexion.close();  // Laisser la fermeture de la connexion à l'appelant
        return livres;
    }

    // LivresDisponibles : Méthode avec requête pour récupérer les livres avec Statut égale à « Disponible » dans la table Livres
    public List<Livre> LivresDisponibles() throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "SELECT Id, Titre, Auteur, Annee_publication, Statut, Nb_livre FROM Livres WHERE Statut = 'Disponible'"
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Livre> livres = new ArrayList<>();
        while (resultSet.next()) {
            Livre livre = new Livre(
                    resultSet.getInt("Id"),
                    resultSet.getString("Titre"),
                    resultSet.getString("Auteur"),
                    resultSet.getInt("Annee_publication"),
                    resultSet.getString("Statut"),
                    resultSet.getInt("Nb_livre")
            );
            livres.add(livre);
        }
        preparedStatement.close();
        resultSet.close();
        //connexion.close();  // Laisser la fermeture de la connexion à l'appelant
        return livres;
    }

    // LivresEmpruntes : Méthode avec requête pour récupérer les livres avec Statut_emprunt égale à « Emprunté » dans la table Emprunt
    public List<Livre> LivresEmpruntes() throws SQLException {
        Connection connexion = BiblioData.getConnection();
        String query = "SELECT l.Id, l.Titre, l.Auteur, l.Annee_publication, l.Statut, l.Nb_livre " +
                "FROM Livres l " +
                "INNER JOIN emprunts e ON l.Id = e.Id_livre " +
                "WHERE e.Statut_emprunt = 'Emprunté'";
        Statement statement = connexion.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<Livre> livres = new ArrayList<>();
        while (resultSet.next()) {
            Livre livre = new Livre(
                    resultSet.getInt("Id"),
                    resultSet.getString("Titre"),
                    resultSet.getString("Auteur"),
                    resultSet.getInt("Annee_publication"),
                    resultSet.getString("Statut"),
                    resultSet.getInt("Nb_livre")
            );
            livres.add(livre);
        }
        statement.close();
        resultSet.close();
        // connexion.close();
        return livres;
    }

    // mettreAJourStatutLivre : Méthode avec requête pour mettre à jour le statut d'un livre dans la table Livres
    public void mettreAJourStatutLivre(int idLivre, String nouveauStatut) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "UPDATE Livres SET Statut = ? WHERE Id = ?"
        );
        preparedStatement.setString(1, nouveauStatut);
        preparedStatement.setInt(2, idLivre);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        // connexion.close();
    }

    //SupprimerLivre : Méthode pour supprimer un livre
    public void SupprimerLivre(int idLivre) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "DELETE FROM Livres WHERE Id = ?"
        );
        preparedStatement.setInt(1, idLivre);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        //connexion.close();
    }
}

