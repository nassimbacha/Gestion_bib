package UI;

import DAO.AdhérantDAO;
import Models.Adhérant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class InterfaceAdhérant extends JFrame {

    private AdhérantDAO adhérantDAO;
    private JTextField nomTextField;
    private JTextField prenomTextField;
    private JTextField dateAdhesionTextField;
    private JList<String> adhérantList; // Change to JList<String>
    private DefaultListModel<String> adhérantListModel; // Change to DefaultListModel<String>
    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;

    public InterfaceAdhérant() {
        // Initialisation de la DAO
        adhérantDAO = new AdhérantDAO();

        // Configuration de la fenêtre
        setTitle("Gestion des Adhérents");
        setSize(600, 500);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;

        // Création des labels
        JLabel nomLabel = new JLabel("Nom :");
        JLabel prenomLabel = new JLabel("Prénom :");
        JLabel dateAdhesionLabel = new JLabel("Date d'adhésion (AAAA-MM-JJ) :");

        // Création des champs de texte
        nomTextField = new JTextField(20);
        prenomTextField = new JTextField(20);
        dateAdhesionTextField = new JTextField(20);

        // Création de la liste
        adhérantListModel = new DefaultListModel<>();
        adhérantList = new JList<>(adhérantListModel);
        adhérantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(adhérantList);
        listScrollPane.setPreferredSize(new Dimension(250, 150));

        // Création des boutons
        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        // Ajout des composants à la fenêtre
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nomLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(nomTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(prenomLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        add(prenomTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(dateAdhesionLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        add(dateAdhesionTextField, gbc);


        gbc.gridx = 0; // Position en X (colonne)
        gbc.gridy = 5; // Position en Y (ligne)
        gbc.gridwidth = 9; // Le panneau déroulant s'étend sur 9 colonnes
        gbc.weightx = 1.0; // Le panneau déroulant occupe l'espace horizontal disponible
        gbc.weighty = 1.0; // Le panneau déroulant occupe l'espace vertical disponible
        gbc.fill = GridBagConstraints.BOTH; // Le panneau déroulant remplit la cellule dans les deux directions
        add(listScrollPane, gbc); // Ajouter le panneau déroulant contenant la liste à la fenêtre


        gbc.gridwidth = 1; // Chaque bouton occupe une seule colonne
        gbc.gridx = 2; // Les boutons sont sur la même ligne
        gbc.gridy = 6; // Les boutons sont placés une ligne après la liste
        gbc.weightx = 0; // Les boutons n'ont pas de poids horizontal
        gbc.weighty = 0; // Les boutons n'ont pas de poids vertical
        gbc.fill = GridBagConstraints.HORIZONTAL; // Les boutons s'étendent horizontalement
        add(ajouterButton, gbc); // Ajouter le bouton "Ajouter" à la fenêtre

        gbc.gridx = 3; // Position du bouton "Modifier"
        gbc.gridy = 6;
        add(modifierButton, gbc); // Ajouter le bouton "Modifier" à la fenêtre

        gbc.gridx = 4; // Position du bouton "Supprimer"
        gbc.gridy = 6;
        add(supprimerButton, gbc); // Ajouter le bouton "Supprimer" à la fenêtre


        // Configuration des listeners pour les boutons
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterAdhérant();
            }
        });

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierAdhérant();
            }
        });

        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerAdhérant();
            }
        });

        // Listener pour la sélection dans la liste
        adhérantList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && adhérantList.getSelectedValue() != null) {
                    // Get the selected string, which contains Adhérant info
                    String selectedAdhérantString = adhérantList.getSelectedValue();
                    // Split the string to extract the Adhérant's ID (assuming it's the first part)
                    String[] parts = selectedAdhérantString.split(" - "); // Splitting by " - "
                    if (parts.length > 0) {
                        try {
                            int idAdhérant = Integer.parseInt(parts[0].substring(4)); // Extracting the ID
                            Adhérant adhérant = adhérantDAO.AdhérantById(idAdhérant);
                            if (adhérant != null) {
                                nomTextField.setText(adhérant.getNom_adhérant());
                                prenomTextField.setText(adhérant.getPrénom_adhérant());
                                dateAdhesionTextField.setText(adhérant.getDate_adhésion().toString());
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(InterfaceAdhérant.this, "Erreur de format de l'ID de l'adhérent.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(InterfaceAdhérant.this, "Erreur lors de la récupération de l'adhérent par ID : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        // Initialisation de la liste des adhérents
        try {
            afficherListeAdhérants();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des adhérents : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        // Affichage de la fenêtre
        setVisible(true);
    }

    private void ajouterAdhérant() {
        String nom = nomTextField.getText();
        String prenom = prenomTextField.getText();
        String dateAdhesionStr = dateAdhesionTextField.getText();

        if (nom.isEmpty() || prenom.isEmpty() || dateAdhesionStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Date dateAdhesion = Date.valueOf(dateAdhesionStr);
            Adhérant adhérant = new Adhérant(0, nom, prenom, dateAdhesion);
            adhérantDAO.ajouterAdhérant(adhérant);
            JOptionPane.showMessageDialog(this, "Adhérent ajouté avec succès.");
            afficherListeAdhérants();
            nomTextField.setText("");
            prenomTextField.setText("");
            dateAdhesionTextField.setText("");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Format de date invalide. Veuillez utiliser le format AAAA-MM-JJ.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'adhérent : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierAdhérant() {
        String selectedAdhérantString = adhérantList.getSelectedValue();
        if (selectedAdhérantString == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un adhérent à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] parts = selectedAdhérantString.split(" - ");
        if (parts.length <= 0) {
            JOptionPane.showMessageDialog(this, "Erreur : ID de l'adhérent non trouvé dans la sélection.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idAdhérant;
        try {
            idAdhérant = Integer.parseInt(parts[0].substring(4));
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erreur : ID de l'adhérent invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nom = nomTextField.getText();
        String prenom = prenomTextField.getText();
        String dateAdhesionStr = dateAdhesionTextField.getText();

        if (nom.isEmpty() || prenom.isEmpty() || dateAdhesionStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Date dateAdhesion = Date.valueOf(dateAdhesionStr);
            Adhérant adhérantModifie = new Adhérant(idAdhérant, nom, prenom, dateAdhesion);
            adhérantDAO.modifierAdhérant(adhérantModifie);
            JOptionPane.showMessageDialog(this, "Adhérent modifié avec succès.");
            afficherListeAdhérants();
            nomTextField.setText("");
            prenomTextField.setText("");
            dateAdhesionTextField.setText("");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Format de date invalide. Veuillez utiliser le format AAAA-MM-JJ.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification de l'adhérent : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerAdhérant() {
        String selectedAdhérantString = adhérantList.getSelectedValue();
        if (selectedAdhérantString == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un adhérent à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] parts = selectedAdhérantString.split(" - ");
        if (parts.length <= 0) {
            JOptionPane.showMessageDialog(this, "Erreur : ID de l'adhérent non trouvé dans la sélection.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idAdhérant;
        try {
            idAdhérant = Integer.parseInt(parts[0].substring(4)); // Extracting the ID
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erreur : ID de l'adhérent invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (adhérantDAO.AdhérantEmprunt(idAdhérant)) {
                JOptionPane.showMessageDialog(this, "Impossible de supprimer cet adhérent car il a des emprunts en cours.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                int confirmation = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cet adhérent ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    adhérantDAO.supprimerAdhérant(idAdhérant);
                    JOptionPane.showMessageDialog(this, "Adhérent supprimé avec succès.");
                    afficherListeAdhérants();
                    nomTextField.setText("");
                    prenomTextField.setText("");
                    dateAdhesionTextField.setText("");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'adhérent : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherListeAdhérants() throws SQLException {
        List<Adhérant> adhérants = adhérantDAO.ListAdhérant();
        adhérantListModel.clear();
        for (Adhérant adhérant : adhérants) {
            boolean aDesEmprunts = adhérantDAO.AdhérantEmprunt(adhérant.getId_adhérant());
            String empruntStatus = aDesEmprunts ? " (Emprunts en cours)" : " (Aucun emprunt)";
            adhérantListModel.addElement("ID: " + adhérant.getId_adhérant() + " - " + adhérant.getNom_adhérant() + " " + adhérant.getPrénom_adhérant() + empruntStatus);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfaceAdhérant();
            }
        });
    }
}

