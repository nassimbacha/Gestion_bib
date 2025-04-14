package UI;

import DAO.LivreDAO;
import Models.Livre;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class InterfaceLivre extends JFrame {

    private LivreDAO livreDAO; // Instance de la classe DAO pour accéder à la base de données
    private JTextField titreTextField; // Champ de texte pour le titre du livre
    private JTextField auteurTextField; // Champ de texte pour l'auteur du livre
    private JTextField anneePublicationTextField; // Champ de texte pour l'année de publication du livre
    private JTextField statutTextField; // Champ de texte pour le statut du livre
    private JTextField nbLivreTextField; // Champ de texte pour le nombre d'exemplaires du livre
    private JList<String> livreList; // Liste pour afficher les livres
    private DefaultListModel<String> livreListModel; // Modèle de la liste pour gérer les données
    private JButton ajouterButton; // Bouton pour ajouter un livre
    private JButton modifierButton; // Bouton pour modifier un livre
    private JButton supprimerButton; // Bouton pour supprimer un livre

    public InterfaceLivre() {
        // Initialisation de la DAO
        livreDAO = new LivreDAO();

        // Configuration de la fenêtre principale
        setTitle("Gestion des Livres"); // Titre de la fenêtre
        setSize(600, 500); // Taille de la fenêtre
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Action désactiver pour effectuer lors de la fermeture de la fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre
        setLayout(new GridBagLayout()); // Utilisation de GridBagLayout pour une disposition flexible des composants
        GridBagConstraints gbc = new GridBagConstraints(); // Contraintes pour GridBagLayout
        gbc.insets = new Insets(5, 5, 5, 5); // Définir les marges autour des composants
        gbc.fill = GridBagConstraints.HORIZONTAL; // Étirer les composants horizontalement
        gbc.gridwidth = 2; // Par défaut, les composants s'étendent sur 2 colonnes

        // Création des labels
        JLabel titreLabel = new JLabel("Titre :"); // Label pour le titre
        JLabel auteurLabel = new JLabel("Auteur :"); // Label pour l'auteur
        JLabel anneePublicationLabel = new JLabel("Année de publication :"); // Label pour l'année de publication
        JLabel statutLabel = new JLabel("Statut :"); // Label pour le statut
        JLabel nbLivreLabel = new JLabel("Nombre d'exemplaires :"); // Label pour le nombre d'exemplaires

        // Création des champs de texte
        titreTextField = new JTextField(20); // Champ de texte pour le titre (20 colonnes)
        auteurTextField = new JTextField(20); // Champ de texte pour l'auteur (20 colonnes)
        anneePublicationTextField = new JTextField(20); // Champ de texte pour l'année de publication (20 colonnes)
        statutTextField = new JTextField(20); // Champ de texte pour le statut (20 colonnes)
        nbLivreTextField = new JTextField(20); // Champ de texte pour le nombre d'exemplaires (20 colonnes)

        // Création de la liste pour afficher les livres
        livreListModel = new DefaultListModel<>(); // Modèle de liste pour stocker les données des livres
        livreList = new JList<>(livreListModel); // Liste qui utilise le modèle pour afficher les livres
        livreList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permettre une seule sélection à la fois
        JScrollPane listScrollPane = new JScrollPane(livreList); // Ajouter la liste à un panneau déroulant
        listScrollPane.setPreferredSize(new Dimension(700, 150)); // Définir la taille préférée du panneau déroulant

        // Création des boutons
        ajouterButton = new JButton("Ajouter"); // Bouton pour ajouter un livre
        modifierButton = new JButton("Modifier"); // Bouton pour modifier un livre
        supprimerButton = new JButton("Supprimer"); // Bouton pour supprimer un livre

        // Ajout des composants à la fenêtre
        gbc.gridx = 0; // Position en X (colonne)
        gbc.gridy = 0; // Position en Y (ligne)
        add(titreLabel, gbc); // Ajouter le label du titre à la fenêtre

        gbc.gridx = 4; // Position en X (colonne)
        gbc.gridy = 0; // Position en Y (ligne)
        add(titreTextField, gbc); // Ajouter le champ de texte du titre à la fenêtre

        gbc.gridx = 0; // Position en X (colonne)
        gbc.gridy = 1; // Position en Y (ligne)
        add(auteurLabel, gbc); // Ajouter le label de l'auteur à la fenêtre

        gbc.gridx = 4; // Position en X (colonne)
        gbc.gridy = 1; // Position en Y (ligne)
        add(auteurTextField, gbc); // Ajouter le champ de texte de l'auteur à la fenêtre

        gbc.gridx = 0; // Position en X (colonne)
        gbc.gridy = 2; // Position en Y (ligne)
        add(anneePublicationLabel, gbc); // Ajouter le label de l'année de publication à la fenêtre

        gbc.gridx = 4; // Position en X (colonne)
        gbc.gridy = 2; // Position en Y (ligne)
        add(anneePublicationTextField, gbc); // Ajouter le champ de texte de l'année de publication à la fenêtre

        gbc.gridx = 0; // Position en X (colonne)
        gbc.gridy = 3; // Position en Y (ligne)
        add(statutLabel, gbc); // Ajouter le label du statut à la fenêtre

        gbc.gridx = 4; // Position en X (colonne)
        gbc.gridy = 3; // Position en Y (ligne)
        add(statutTextField, gbc); // Ajouter le champ de texte du statut à la fenêtre

        gbc.gridx = 0; // Position en X (colonne)
        gbc.gridy = 4; // Position en Y (ligne)
        add(nbLivreLabel, gbc); // Ajouter le label du nombre d'exemplaires à la fenêtre

        gbc.gridx = 4; // Position en X (colonne)
        gbc.gridy = 4; // Position en Y (ligne)
        add(nbLivreTextField, gbc); // Ajouter le champ de texte du nombre d'exemplaires à la fenêtre

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
                ajouterLivre(); // Appeler la méthode pour ajouter un livre
            }
        });

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierLivre(); // Appeler la méthode pour modifier un livre
            }
        });

        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerLivre(); // Appeler la méthode pour supprimer un livre
            }
        });

        // Listener pour la sélection dans la liste
        livreList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && livreList.getSelectedValue() != null) {
                    // Get the selected string, which contains Livre info
                    String selectedLivreString = livreList.getSelectedValue();
                    // Split the string to extract the Livre's ID (assuming it's the first part)
                    String[] parts = selectedLivreString.split(" - ");
                    if (parts.length > 0) {
                        try {
                            int idLivre = Integer.parseInt(parts[0].substring(4)); // Extracting the ID
                            Livre livre = livreDAO.LivreById(idLivre); // Récupérer le livre par son ID
                            if (livre != null) {
                                titreTextField.setText(livre.getTitre()); // Afficher le titre dans le champ de texte
                                auteurTextField.setText(livre.getAuteur()); // Afficher l'auteur dans le champ de texte
                                anneePublicationTextField.setText(String.valueOf(livre.getAnneePublication())); // Afficher l'année de publication
                                statutTextField.setText(livre.getStatut()); // Afficher le statut
                                nbLivreTextField.setText(String.valueOf(livre.getNbLivre())); // Afficher le nombre d'exemplaires
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(InterfaceLivre.this, "Erreur de format de l'ID du livre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(InterfaceLivre.this, "Erreur lors de la récupération du livre par ID : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        // Initialisation de la liste des livres
        try {
            afficherListeLivres(); // Appeler la méthode pour afficher la liste des livres
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des livres : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        // Affichage de la fenêtre
        setVisible(true); // Rendre la fenêtre visible
    }

    private void ajouterLivre() {
        String titre = titreTextField.getText(); // Récupérer le titre du livre depuis le champ de texte
        String auteur = auteurTextField.getText(); // Récupérer l'auteur du livre depuis le champ de texte
        String anneePublicationStr = anneePublicationTextField.getText(); // Récupérer l'année de publication depuis le champ de texte
        String statut = statutTextField.getText(); // Récupérer le statut du livre depuis le champ de texte
        String nbLivreStr = nbLivreTextField.getText(); // Récupérer le nombre d'exemplaires depuis le champ de texte

        // Vérifier si tous les champs sont remplis
        if (titre.isEmpty() || auteur.isEmpty() || anneePublicationStr.isEmpty() || statut.isEmpty() || nbLivreStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int anneePublication = Integer.parseInt(anneePublicationStr); // Convertir l'année de publication en entier
            int nbLivre = Integer.parseInt(nbLivreStr); // Convertir le nombre d'exemplaires en entier
            Livre livre = new Livre(0, titre, auteur, anneePublication, statut, nbLivre); // Créer un objet Livre
            livreDAO.ajouterLivre(livre); // Ajouter le livre à la base de données
            JOptionPane.showMessageDialog(this, "Livre ajouté avec succès."); // Afficher un message de succès
            afficherListeLivres(); // Mettre à jour la liste des livres
            titreTextField.setText(""); // Effacer le champ de texte du titre
            auteurTextField.setText(""); // Effacer le champ de texte de l'auteur
            anneePublicationTextField.setText(""); // Effacer le champ de texte de l'année de publication
            statutTextField.setText(""); // Effacer le champ de texte du statut
            nbLivreTextField.setText(""); // Effacer le champ de texte du nombre d'exemplaires
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format de nombre invalide pour l'année de publication ou le nombre de livres.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du livre : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierLivre() {
        String selectedLivreString = livreList.getSelectedValue(); // Récupérer la chaîne de caractères sélectionnée dans la liste
        if (selectedLivreString == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] parts = selectedLivreString.split(" - "); // Diviser la chaîne de caractères pour extraire l'ID
        if (parts.length <= 0) {
            JOptionPane.showMessageDialog(this, "Erreur : ID du livre non trouvé dans la sélection.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idLivre;
        try {
            idLivre = Integer.parseInt(parts[0].substring(4)); // Extracting the ID
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erreur : ID du livre invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String titre = titreTextField.getText(); // Récupérer le titre du livre depuis le champ de texte
        String auteur = auteurTextField.getText(); // Récupérer l'auteur du livre depuis le champ de texte
        String anneePublicationStr = anneePublicationTextField.getText(); // Récupérer l'année de publication depuis le champ de texte
        String statut = statutTextField.getText(); // Récupérer le statut du livre depuis le champ de texte
        String nbLivreStr = nbLivreTextField.getText(); // Récupérer le nombre d'exemplaires depuis le champ de texte

        // Vérifier si tous les champs sont remplis
        if (titre.isEmpty() || auteur.isEmpty() || anneePublicationStr.isEmpty() || statut.isEmpty() || nbLivreStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int anneePublication = Integer.parseInt(anneePublicationStr); // Convertir l'année de publication en entier
            int nbLivre = Integer.parseInt(nbLivreStr); // Convertir le nombre d'exemplaires en entier
            Livre livreModifie = new Livre(idLivre, titre, auteur, anneePublication, statut, nbLivre); // Créer un objet Livre avec l'ID
            livreDAO.modifierLivre(livreModifie); // Modifier le livre dans la base de données
            JOptionPane.showMessageDialog(this, "Livre modifié avec succès."); // Afficher un message de succès
            afficherListeLivres(); // Mettre à jour la liste des livres
            titreTextField.setText(""); // Effacer le champ de texte du titre
            auteurTextField.setText(""); // Effacer le champ de texte de l'auteur
            anneePublicationTextField.setText(""); // Effacer le champ de texte de l'année de publication
            statutTextField.setText(""); // Effacer le champ de texte du statut
            nbLivreTextField.setText(""); // Effacer le champ de texte du nombre d'exemplaires
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format de nombre invalide pour l'année de publication ou le nombre de livres.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification du livre : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerLivre() {
        String selectedLivreString = livreList.getSelectedValue(); // Récupérer la chaîne de caractères sélectionnée dans la liste
        if (selectedLivreString == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] parts = selectedLivreString.split(" - "); // Diviser la chaîne de caractères pour extraire l'ID
        if (parts.length <= 0) {
            JOptionPane.showMessageDialog(this, "Erreur : ID du livre non trouvé dans la sélection.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idLivre;
        try {
            idLivre = Integer.parseInt(parts[0].substring(4)); // Extracting the ID
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erreur : ID du livre invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (livreDAO.LivreEmprunte(idLivre)) { // Vérifier si le livre est emprunté
                JOptionPane.showMessageDialog(this, "Impossible de supprimer ce livre car il est actuellement emprunté.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                int confirmation = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce livre ?", "Confirmation", JOptionPane.YES_NO_OPTION); // Boîte de dialogue de confirmation
                if (confirmation == JOptionPane.YES_OPTION) {
                    livreDAO.SupprimerLivre(idLivre); // Supprimer le livre de la base de données
                    JOptionPane.showMessageDialog(this, "Livre supprimé avec succès."); // Afficher un message de succès
                    afficherListeLivres(); // Mettre à jour la liste des livres
                    titreTextField.setText(""); // Effacer le champ de texte du titre
                    auteurTextField.setText(""); // Effacer le champ de texte de l'auteur
                    anneePublicationTextField.setText(""); // Effacer le champ de texte de l'année de publication
                    statutTextField.setText(""); // Effacer le champ de texte du statut
                    nbLivreTextField.setText(""); // Effacer le champ de texte du nombre d'exemplaires
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du livre : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherListeLivres() throws SQLException {
        List<Livre> livres = livreDAO.AllLivres(); // Récupérer tous les livres de la base de données
        livreListModel.clear(); // Effacer le modèle de liste
        for (Livre livre : livres) {
            boolean estEmprunte = livreDAO.LivreEmprunte(livre.getId()); // Vérifier si le livre est emprunté
            String empruntStatus = estEmprunte ? " (Emprunté)" : " (Disponible)"; // Déterminer le statut d'emprunt pour l'affichage
            livreListModel.addElement("ID: " + livre.getId() + " - " + livre.getTitre() + " - " + livre.getAuteur() + " - " + livre.getAnneePublication() + " - " + livre.getStatut() + " - " + livre.getNbLivre() + empruntStatus); // Ajouter le livre au modèle de liste
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfaceLivre(); // Créer et afficher l'interface utilisateur
            }
        });
    }
}
