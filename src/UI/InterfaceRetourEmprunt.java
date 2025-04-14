
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterfaceRetourEmprunt extends JFrame {

    private EmpruntDAO empruntDAO;
    private LivreDAO livreDAO;
    private JList<Emprunt> listeEmpruntsEnCours;
    private JTextArea detailsEmpruntTextArea;
    private JTextField dateRetourTextField;
    private JButton validerRetourButton;
    private DefaultListModel<Emprunt> listeEmpruntsModel;
    private JLabel listeEmpruntsLabel;
    private JLabel detailsEmpruntLabel;
    private JLabel dateRetourLabel;

    public InterfaceRetourEmprunt() {
        // Initialisation des DAOs
        empruntDAO = new EmpruntDAO();
        livreDAO = new LivreDAO();

        // Configuration de la fenêtre
        setTitle("Retour d'Emprunt");
        setSize(800, 600); // Ajuster la taille pour plus d'espace
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); désactiver la fermeture par défaut
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout()); // Utiliser GridBagLayout pour une meilleure gestion de l'espace

        // Création des composants
        listeEmpruntsLabel = new JLabel("Emprunts en cours :");
        listeEmpruntsModel = new DefaultListModel<>();
        listeEmpruntsEnCours = new JList<>(listeEmpruntsModel);
        listeEmpruntsEnCours.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permettre une seule sélection
        detailsEmpruntLabel = new JLabel("Détails de l'emprunt sélectionné :");
        detailsEmpruntTextArea = new JTextArea(10, 30); // Ajuster la taille pour afficher plus d'informations
        detailsEmpruntTextArea.setEditable(false); // Empêcher l'édition du texte
        dateRetourLabel = new JLabel("Date de retour :");
        dateRetourTextField = new JTextField(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)); // Initialiser avec la date du jour
        validerRetourButton = new JButton("Valider le retour");

        // Configuration du GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Ajouter des marges autour des composants
        gbc.fill = GridBagConstraints.BOTH; // Permettre aux composants de s'étendre dans les deux directions
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1; // Donner du poids aux colonnes et lignes pour qu'elles se répartissent l'espace
        gbc.weighty = 0;

        // Ajout des composants à la fenêtre
        add(listeEmpruntsLabel, gbc);
        gbc.gridy++;
        JScrollPane listeEmpruntsScrollPane = new JScrollPane(listeEmpruntsEnCours); // Ajouter une barre de défilement
        add(listeEmpruntsScrollPane, gbc);
        gbc.gridy++;
        add(detailsEmpruntLabel, gbc);
        gbc.gridy++;
        JScrollPane detailsEmpruntScrollPane = new JScrollPane(detailsEmpruntTextArea);
        add(detailsEmpruntScrollPane, gbc);
        gbc.gridy++;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(dateRetourLabel, gbc);
        gbc.gridy++;
        add(dateRetourTextField, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER; // Centrer le bouton
        add(validerRetourButton, gbc);

        // Remplir la liste des emprunts en cours
        chargerEmpruntsEnCours();

        // Configuration des listeners
        listeEmpruntsEnCours.addListSelectionListener(e -> afficherDetailsEmprunt());
        validerRetourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    validerRetourEmprunt();
                } catch (SQLException ex) {
                    Logger.getLogger(InterfaceRetourEmprunt.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Affichage de la fenêtre
        setVisible(true);
    }

    private void chargerEmpruntsEnCours() {
        try {
            List<Emprunt> emprunts = empruntDAO.EmpruntsEnCours();
            listeEmpruntsModel.clear(); // Effacer le modèle avant d'ajouter les nouveaux emprunts
            for (Emprunt emprunt : emprunts) {
                listeEmpruntsModel.addElement(emprunt);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des emprunts : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherDetailsEmprunt() {
        Emprunt empruntSelectionne = listeEmpruntsEnCours.getSelectedValue();
        if (empruntSelectionne != null) {
            detailsEmpruntTextArea.setText(empruntSelectionne.toString());
        } else {
            detailsEmpruntTextArea.setText(""); // Effacer le texte si aucun emprunt n'est sélectionné
        }
    }

    private void validerRetourEmprunt() throws SQLException {
        Emprunt empruntSelectionne = listeEmpruntsEnCours.getSelectedValue();
        if (empruntSelectionne == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un emprunt à retourner.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dateRetourStr = dateRetourTextField.getText();
        if (dateRetourStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer la date de retour.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date dateRetour = Date.valueOf(dateRetourStr);
        if (dateRetour.before(empruntSelectionne.getDate_emprunt()))
        {
            JOptionPane.showMessageDialog(this, "La date de retour ne peut pas être antérieure à la date d'emprunt", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idLivre = empruntDAO.IdLivreFromEmprunt(empruntSelectionne.getId_emprunt()); //a revoir
        // Mettre à jour l'emprunt
        empruntDAO.GererEmprunt(empruntSelectionne.getId_emprunt(), dateRetour, "Récupéré");

        // Mettre à jour le statut du livre
        livreDAO.mettreAJourStatutLivre(idLivre, "Disponible");

        JOptionPane.showMessageDialog(this, "Retour d'emprunt validé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);

        // Rafraîchir la liste des emprunts en cours
        chargerEmpruntsEnCours();
        detailsEmpruntTextArea.setText("");
        dateRetourTextField.setText(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfaceRetourEmprunt();
            }
        });
    }
}

