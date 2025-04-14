
package DAO;

import Models.Emprunt;//Objets
import BDD.BiblioData; //connexion BDD

import java.sql.*;//requete
import java.util.ArrayList;//liste dynamique
import java.util.List;// Liste

public class EmpruntDAO {

    // ajouterEmprunt : Méthode avec requête pour ajouter un nouvel emprunt
    public void ajouterEmprunt(Emprunt emprunt) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "INSERT INTO emprunts (Id_livre, Id_adhérant, Date_emprunt, Date_retour, Statut_emprunt) VALUES (?, ?, ?, ?, 'Emprunté')"
        );
        //pas de emprunt.getid_emprunt vue qu il est auto-incremente sur la BDD
        preparedStatement.setInt(1, emprunt.getId_livre());
        preparedStatement.setInt(2, emprunt.getId_adhérant());
        preparedStatement.setDate(3, emprunt.getDate_emprunt());
        preparedStatement.setDate(4, emprunt.getDate_retour());
        //pas de emprunt.getstatut_emprunt vue qu'il est force "Emprunté" dans la requet sql(ligne17)
        preparedStatement.executeUpdate();
        preparedStatement.close();
        //connexion.close();
    }

    // IdLivreFromEmprunt : Méthode avec requête pour récupérer l'ID du livre à partir de l'ID de l'emprunt
    public int IdLivreFromEmprunt(int idEmprunt) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "SELECT Id_livre FROM emprunts WHERE Id_emprunt = ?"
        );
        preparedStatement.setInt(1, idEmprunt);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int idLivre = resultSet.getInt("Id_livre");
            preparedStatement.close();
            resultSet.close();
            //connexion.close();
            return idLivre;
        }
        preparedStatement.close();
        resultSet.close();
        //connexion.close();
        return 0; // Retourne 0 si l'emprunt n'est pas trouvé
    }

    // ExempleEmprunte : méthode qui calcul le nombre d’emprunt par Id_livre où Statut_emprunt est égale à « En cours »
    public int ExempleEmprunte(int idLivre) throws SQLException {
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
        //connexion.close();
        return count;
    }

    // GererEmprunt : Méthode pour mettre à jour les données d’un emprunt, mise à jour uniquement des données, « Date_retour » et « Statut_emprunt »
    public void GererEmprunt(int idEmprunt, Date dateRetour, String statutEmprunt) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "UPDATE emprunts SET Date_retour = ?, Statut_emprunt = ? WHERE Id_emprunt = ?"
        );
        preparedStatement.setDate(1, dateRetour);
        preparedStatement.setString(2, statutEmprunt);
        preparedStatement.setInt(3, idEmprunt);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        //connexion.close();
    }

    // EmpruntID : Méthode avec requête pour récupérer un emprunt par son ID
    /*public Emprunt EmpruntID(int idEmprunt) throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "SELECT Id_emprunt, Id_livre, Id_adhérant, Date_emprunt, Date_retour, Statut_emprunt FROM emprunts WHERE Id_emprunt = ?"
        );
        preparedStatement.setInt(1, idEmprunt);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Emprunt emprunt = new Emprunt(
                    resultSet.getInt("Id_emprunt"),
                    resultSet.getInt("Id_livre"),
                    resultSet.getInt("Id_adhérant"),
                    resultSet.getDate("Date_emprunt"),
                    resultSet.getDate("Date_retour"),
                    resultSet.getString("Statut_emprunt")
            );
            preparedStatement.close();
            resultSet.close();
            //connexion.close();
            return emprunt;
        }
        preparedStatement.close();
        resultSet.close();
        //connexion.close();
        return null;
    }*/

    // HistEmprunts : Méthode pour récupérer toutes les opérations d'emprunts
    /*public List<Emprunt> HistEmprunts() throws SQLException {
        Connection connexion = BiblioData.getConnection();
        Statement statement = connexion.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT Id_emprunt, Id_livre, Id_adhérant, Date_emprunt, Date_retour, Statut_emprunt FROM emprunts");
        List<Emprunt> emprunts = new ArrayList<>();
        while (resultSet.next()) {
            Emprunt emprunt = new Emprunt(
                    resultSet.getInt("Id_emprunt"),
                    resultSet.getInt("Id_livre"),
                    resultSet.getInt("Id_adhérant"),
                    resultSet.getDate("Date_emprunt"),
                    resultSet.getDate("Date_retour"),
                    resultSet.getString("Statut_emprunt")
            );
            emprunts.add(emprunt);
        }
        statement.close();
        resultSet.close();
        //connexion.close();
        return emprunts;
    }
*/
    // EmpruntsEnCours : Méthode avec requête pour récupérer les emprunts avec Statut_emprunt égale à « Emprunté »
    public List<Emprunt> EmpruntsEnCours() throws SQLException {
        Connection connexion = BiblioData.getConnection();
        PreparedStatement preparedStatement = connexion.prepareStatement(
                "SELECT Id_emprunt, Id_livre, Id_adhérant, Date_emprunt, Date_retour, Statut_emprunt FROM emprunts WHERE Statut_emprunt = 'Emprunté'"
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Emprunt> emprunts = new ArrayList<>();
        while (resultSet.next()) {
            Emprunt emprunt = new Emprunt(
                    resultSet.getInt("Id_emprunt"),
                    resultSet.getInt("Id_livre"),
                    resultSet.getInt("Id_adhérant"),
                    resultSet.getDate("Date_emprunt"),
                    resultSet.getDate("Date_retour"),
                    resultSet.getString("Statut_emprunt")
            );
            emprunts.add(emprunt);
        }
        preparedStatement.close();
        resultSet.close();
        //connexion.close();
        return emprunts;
    }

}




