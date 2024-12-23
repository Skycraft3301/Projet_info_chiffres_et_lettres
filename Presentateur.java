// Projet informatique
// des chiffres et des lettres

/* Structure du fichier com.txt
l1  nom joueurA
l2  nom joueurB
l3  score joueurA
l4 score joueurB

 */


import java.io.IOException;
import java.util.Objects;

public class Presentateur {
    public static void main(String[] args) {

        Utils.checkFile();

        System.out.println("Joueur A, donnez votre nom :");
        Joueur joueurA = new Joueur();

        System.out.println("Joueur B, donnez votre nom :");
        Joueur joueurB = new Joueur();

        String joueurVoyelles = joueurA.getNom();

        Utils.writeLine(1, joueurA.getNom());
        Utils.writeLine(2, joueurB.getNom());

        LancerProgramme('A');
        LancerProgramme('B');


        for (int i = 1; i <= 5; i++) {
            ModeChiffres.modeChiffres(joueurA, joueurB);
            ModeLettres.modeLettres(joueurVoyelles, joueurA, joueurB);

            actualiserCom(joueurA, joueurB);

            // Pour changer à chaque tour le joueur qui choisit le nombre de voyelles
            if (Objects.equals(joueurVoyelles, joueurA.getNom())) {
                joueurVoyelles = joueurB.getNom();
            } else {
                joueurVoyelles = joueurA.getNom();
            }
        }


    }

    private static void actualiserCom(Joueur joueurA, Joueur joueurB) {
        Utils.writeLine(3, String.valueOf(joueurA.getScore()));
        Utils.writeLine(4, String.valueOf(joueurB.getScore()));
    }


    public static void LancerProgramme(char joueur) {
        // Commande à exécuter
        String commande = "cmd /c start cmd.exe /k java ConsoleJoueur " + joueur; // Sous Windows

        try {
            // Lancer la commande avec ProcessBuilder
            ProcessBuilder pb = new ProcessBuilder(commande.split(" "));
            pb.start(); // Démarre le programme
            System.out.println("Programme lancé dans une nouvelle console !");
        } catch (IOException e) {
            System.err.println("Une erreur est survenue : " + e.getMessage());
        }
    }
}
