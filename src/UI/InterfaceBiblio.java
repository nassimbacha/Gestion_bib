package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceBiblio extends JFrame {

    private JLabel imageLabel;
    private JButton gestionAdherantsButton;
    private JButton gestionLivresButton;
    private JButton nouveauEmpruntButton;
    private JButton retourEmpruntButton;
    private JButton detailsLivresButton;

    public InterfaceBiblio() {
        // Configuration de la fenêtre principale
        setTitle("Gestion de la Bibliothèque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST; // Les composants sont positionnés en haut à gauche

        // Ajout de l'image (assurez-vous que "library.jpg" est dans le même dossier)
        ImageIcon imageIcon = new ImageIcon("D:\\\\SCIENCE DES DONNEES APPLIQUEES\\\\ETAPE_2\\\\PROG AVANCEE\\\\UA3\\\\Gest_Biblio\\\\Gest_Biblio\\\\bibliotheque.jpg");
        Image image = imageIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH); // L'image prend la taille de la fenêtre
        imageLabel = new JLabel(new ImageIcon(image));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // L'image n'occupe qu'une colonne
        gbc.gridheight = 6; // L'image s'étend sur 6 lignes
        gbc.weightx = 1.0; // L'image peut s'étirer horizontalement
        gbc.weighty = 1.0; // L'image peut s'étirer verticalement
        gbc.fill = GridBagConstraints.BOTH; // L'image remplit tout l'espace disponible
        add(imageLabel, gbc);

        // Création des boutons
        gestionAdherantsButton = new JButton("Gestion des Adhérents");
        gbc.gridx = 1; // Les boutons sont dans la deuxième colonne
        gbc.gridy = 0;
        gbc.gridwidth = 1; // Chaque bouton occupe une colonne
        gbc.gridheight = 1; // Chaque bouton occupe une ligne
        gbc.weightx = 0.0; // Les boutons ne s'étirent pas horizontalement
        gbc.weighty = 0.0; // Les boutons ne s'étirent pas verticalement
        gbc.fill = GridBagConstraints.HORIZONTAL; // Les boutons remplissent l'espace horizontal disponible
        add(gestionAdherantsButton, gbc);

        gestionLivresButton = new JButton("Gestion des Livres");
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(gestionLivresButton, gbc);

        nouveauEmpruntButton = new JButton("Nouveau Emprunt");
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(nouveauEmpruntButton, gbc);

        retourEmpruntButton = new JButton("Retour Emprunt");
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(retourEmpruntButton, gbc);

        detailsLivresButton = new JButton("Détails Livres");
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(detailsLivresButton, gbc);

        // Configuration des action listeners pour les boutons
        gestionAdherantsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InterfaceAdhérant();
            }
        });

        gestionLivresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InterfaceLivre();
            }
        });

        nouveauEmpruntButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InterfaceEmprunt();
            }
        });

        retourEmpruntButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InterfaceRetourEmprunt();
            }
        });

        detailsLivresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InterfaceListe();
            }
        });

        // Affichage de la fenêtre principale
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfaceBiblio();
            }
        });
    }
}
