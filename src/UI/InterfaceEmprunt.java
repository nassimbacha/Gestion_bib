
package UI;

import DAO.*;
import Models.Emprunt;
import Models.Livre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class InterfaceEmprunt extends JFrame {

    private EmpruntDAO empruntDAO;
    private AdhérantDAO adherantDAO;
    private LivreDAO livreDAO;
    private JComboBox<Models.Adhérant> adherantComboBox;
    private JComboBox<Livre> livreComboBox;
    private JTextField dateRetourTextField;
    private JButton ajouterEmpruntButton;
    private JLabel adherentLabel;
    private JLabel livreLabel;
    private JLabel dateRetourLabel;
    private JLabel dateEmpruntLabel;
    private JTextField dateEmpruntTextField;
    private JPanel formPanel; // Panel pour organiser les composants du formulaire

    public InterfaceEmprunt() {
        empruntDAO = new EmpruntDAO();
        adherantDAO = new AdhérantDAO();
        livreDAO = new LivreDAO();

        setTitle("Ajouter un Emprunt");
        setSize(600, 500); // Augmenter la hauteur pour un meilleur espacement
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); désactiver la fermeture par défaut
        setLocationRelativeTo(null); // Centrer la fenêtre

        // Créer un JPanel pour organiser les composants
        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout()); // Utiliser GridBagLayout pour plus de flexibilité
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Ajouter des marges autour des composants
        gbc.fill = GridBagConstraints.HORIZONTAL; // Étendre horizontalement les composants
        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 0; // Ligne 0

        adherentLabel = new JLabel("Adhérent :");
        formPanel.add(adherentLabel, gbc);
        gbc.gridx = 1;
        adherantComboBox = new JComboBox<>();
        formPanel.add(adherantComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        livreLabel = new JLabel("Livre :");
        formPanel.add(livreLabel, gbc);
        gbc.gridx = 1;
        livreComboBox = new JComboBox<>();
        formPanel.add(livreComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        dateEmpruntLabel = new JLabel("Date d'emprunt (AAAA-MM-JJ) :");
        formPanel.add(dateEmpruntLabel, gbc);
        gbc.gridx = 1;
        dateEmpruntTextField = new JTextField(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        formPanel.add(dateEmpruntTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        dateRetourLabel = new JLabel("Date de retour (AAAA-MM-JJ) :");
        formPanel.add(dateRetourLabel, gbc);
        gbc.gridx = 1;
        dateRetourTextField = new JTextField(LocalDate.now().plusDays(15).format(DateTimeFormatter.ISO_LOCAL_DATE));
        formPanel.add(dateRetourTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Le bouton s'étend sur deux colonnes
        gbc.fill = GridBagConstraints.NONE; // Ne pas étendre le bouton
        gbc.anchor = GridBagConstraints.CENTER; // Centrer le bouton
        ajouterEmpruntButton = new JButton("Ajouter Emprunt");
        formPanel.add(ajouterEmpruntButton, gbc);

        // Ajouter le JPanel à la JFrame
        add(formPanel);

        try {
            chargerAdherants();
            chargerLivresDisponibles();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }



        ajouterEmpruntButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterEmprunt();
            }
        });

        setVisible(true);
    }

    private void chargerAdherants() throws SQLException {
        List<Models.Adhérant> adherants = adherantDAO.ListAdhérant();
        for (Models.Adhérant adherant : adherants) {
            adherantComboBox.addItem(adherant);
        }
    }

    private void chargerLivresDisponibles() throws SQLException {
        List<Livre> livres = livreDAO.LivresDisponibles();
        for (Livre livre : livres) {
            livreComboBox.addItem(livre);
        }
    }

    private void ajouterEmprunt() {
        Livre livreSelectionne = (Livre) livreComboBox.getSelectedItem();
        Models.Adhérant adherantSelectionne = (Models.Adhérant) adherantComboBox.getSelectedItem();
        String dateRetourStr = dateRetourTextField.getText();
        String dateEmpruntStr = dateEmpruntTextField.getText();


        if (livreSelectionne == null || adherantSelectionne == null || dateRetourStr.isEmpty() || dateEmpruntStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un adhérent, un livre et entrer les dates d'emprunt et de retour.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date dateEmprunt;
        try {
            dateEmprunt = Date.valueOf(dateEmpruntStr);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Format de date incorrect pour la date d'emprunt (AAAA-MM-JJ).", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }


        Date dateRetour;
        try {
            dateRetour = Date.valueOf(dateRetourStr);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Format de date incorrect pour la date de retour (AAAA-MM-JJ).", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }



        if (dateRetour.before(dateEmprunt)) {
            JOptionPane.showMessageDialog(this, "La date de retour doit être après la date d'emprunt.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Emprunt emprunt = new Emprunt(0, livreSelectionne.getId(), adherantSelectionne.getId_adhérant(), dateEmprunt, dateRetour, "Emprunté");
        try {
            empruntDAO.ajouterEmprunt(emprunt);

            int nbEmprunts = empruntDAO.ExempleEmprunte(livreSelectionne.getId());
            if (nbEmprunts >= livreSelectionne.getNbLivre()) {
                livreDAO.mettreAJourStatutLivre(livreSelectionne.getId(), "Non disponible");
            }
            JOptionPane.showMessageDialog(this, "Emprunt ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);

            livreComboBox.removeAllItems();
            chargerLivresDisponibles();
            dateRetourTextField.setText(LocalDate.now().plusDays(15).format(DateTimeFormatter.ISO_LOCAL_DATE)); //reset
            dateEmpruntTextField.setText(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'emprunt : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfaceEmprunt();
            }
        });
    }
}


