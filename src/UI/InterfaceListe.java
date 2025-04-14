package UI;

import DAO.LivreDAO;
import Models.Livre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class InterfaceListe extends JFrame {

    private JComboBox<String> choixListeComboBox;
    private JTextArea resultatTextArea;
    private LivreDAO livreDAO;

    public InterfaceListe() {
        // Initialisation de la fenêtre
        setTitle("Affichage des Livres");
        setSize(600, 500);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  désactiver la fermeture par défaut
        setLocationRelativeTo(null); // Centre la fenêtre

        // Initialisation du LivreDAO
        livreDAO = new LivreDAO();

        // Affichage de la fenêtre
        setVisible(true); // Rendre la fenêtre visible

        // Création des composants
        choixListeComboBox = new JComboBox<>(new String[]{"Tous les livres", "Livres disponibles", "Livres empruntés"});
        resultatTextArea = new JTextArea();
        resultatTextArea.setEditable(false); // Empêche l'édition du texte
        JScrollPane scrollPane = new JScrollPane(resultatTextArea); // Ajoute une barre de défilement

        // Création du layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(choixListeComboBox, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Ajout du panel à la fenêtre
        add(panel);

        // Ajout de l'ActionListener au JComboBox
        choixListeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherLivres();
            }
        });

        // Affichage initial des livres
        afficherLivres();
    }

    private void afficherLivres() {
        String choix = (String) choixListeComboBox.getSelectedItem();
        resultatTextArea.setText(""); // Efface le contenu précédent
        List<Livre> livres = null;

        try {
            switch (choix) {
                case "Tous les livres":
                    livres = livreDAO.AllLivres();
                    break;
                case "Livres disponibles":
                    livres = livreDAO.LivresDisponibles();
                    break;
                case "Livres empruntés":
                    livres = livreDAO.LivresEmpruntes();
                    break;
            }

            if (livres != null && !livres.isEmpty()) {
                for (Livre livre : livres) {
                    resultatTextArea.append(livre.toString() + "\n");
                }
            } else {
                resultatTextArea.append("Aucun livre à afficher.\n");
            }
        } catch (SQLException e) {
            resultatTextArea.append("Erreur lors de la récupération des livres de la base de données : " + e.getMessage() + "\n");

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceListe().setVisible(true);
            }
        });
    }
}


