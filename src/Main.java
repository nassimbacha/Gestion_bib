
import UI.InterfaceBiblio;


import javax.swing.*;

// Class Main pour l'exécution de l'application
public class Main {
    public static void main(String[] args) {
        // Création et affichage de la fenêtre principale dans le thread d'événement Swing
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfaceBiblio(); // Instanciation de la fenêtre principale
            }
        });
    }
}

