// Projet informatique
// des chiffres et des lettres

/* Structure du fichier com.txt
l1  nom joueurA
l2  nom joueurB
l3  score joueurA
l4 score joueurB

 */


import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
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

        CompilerTout();

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


    public static void CompilerTout() {
        String[] ListeProgrammes = {"ConsoleJoueur", "Consonne", "ConverterUtils", "FileLine", "Joueur", "LettresUtils", "Lire", "ModeChiffres", "ModeLettres", "OperationUtils", "SaisieChiffre", "SaisieLettres", "ScoreUtils", "Utils", "Voyelle"};

        for(String nomFichier : ListeProgrammes) {
            // Chemin du fichier source Java à compiler
            String cheminFichier = nomFichier+".java";

            // Obtenir le compilateur Java
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                System.err.println("Le compilateur Java n'est pas disponible. Assurez-vous d'utiliser une JDK et non une JRE.");
                return;
            }

            // Compiler le fichier
            int resultat = compiler.run(null, null, null, cheminFichier);

            // Vérifier si la compilation a réussi
            if (resultat == 0) {
                System.out.println("Compilation réussie !");
            } else {
                System.err.println("Échec de la compilation.");
            }
        }
    }
}
