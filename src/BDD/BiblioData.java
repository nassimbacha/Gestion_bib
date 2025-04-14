package BDD;

// Importation des class qui permet la gestion de BDD sur java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Class BiblioData pour établir une connexion à la BDD MySQL
public class BiblioData {

    private static final String URL = "jdbc:mysql://localhost:3306/bibliodata";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "Ryamou@2012";
    private static Connection connexion;

    public static Connection getConnection() throws SQLException {
        if (connexion == null || connexion.isClosed()) {
            connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
        }
        return connexion;
    }

}

