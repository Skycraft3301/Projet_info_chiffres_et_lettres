// Projet informatique
// des chiffres et des lettres

/* Structure des fichiers comJ.txt
1.	Nom joueur
2.	Score joueur
3.	Liste des chiffres sélectionnés
4.	Objectif de chiffre
5.	Résultat du joueur chiffre
6.	Liste des lettres sélectionnées
7.	Résultat du joueur lettre
8.	Nombre de voyelles
9.  resultat fin de partie (victoire ou défaite)
10. modification du score
11. message d'erreur mode lettres
12. solution optimale chiffre
13. solution optimale lettres
*/


import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Presentateur {

    public final static String comA = "./comA.txt";
    public final static String comB = "./comB.txt";

    private static long referenceTimeA;
    private static long referenceTimeB;

    public static void main(String[] args) throws InterruptedException {

        Utils.checkFile("all");

        referenceTimeA = Utils.getLastUpdate(comA);
        referenceTimeB = Utils.getLastUpdate(comB);

        CompilerTout();

        LancerProgramme('A');
        LancerProgramme('B');

        FileChecker.waitForUpdate(referenceTimeA, comA);
        FileChecker.waitForUpdate(referenceTimeB, comB);


        Joueur joueurA = new Joueur(Utils.getLine(1, comA));

        Joueur joueurB = new Joueur(Utils.getLine(1, comB));

        errorDetector.file();

        for (int i = 1; i <= 5; i++) {
            // Pour changer à chaque tour le joueur qui choisit le nombre de voyelles
            if (joueurA.isVoyellePlayer()) {
                joueurA.setVoyellePlayer(false);
                joueurB.setVoyellePlayer(true);
            } else {
                joueurA.setVoyellePlayer(true);
                joueurB.setVoyellePlayer(false);
            }
            List<FileLine> fileLineList = viderLeFichier();
            fileLineList.add(new FileLine(8, isPlayerVoyelle(joueurA)));
            Utils.writeLines(comA, fileLineList);

            fileLineList.removeLast();
            fileLineList.add(new FileLine(8, isPlayerVoyelle(joueurB)));
            Utils.writeLines(comB, fileLineList);

            ModeChiffres.modeChiffres(joueurA, joueurB);

            Thread.sleep(500);
            //afficherScore(joueurA, joueurB);

            ModeLettres.modeLettres(joueurA, joueurB);

            Thread.sleep(500);

            //afficherScore(joueurA, joueurB);

        }

        // Affichage du résultat
        if (joueurA.getScore() > joueurB.getScore()) {
            Utils.writeLine(comA, 9, "gagnant");
            Utils.writeLine(comB, 9, "perdant");
        } else if (joueurA.getScore() < joueurB.getScore()) {
            Utils.writeLine(comB, 9, "gagnant");
            Utils.writeLine(comA, 9, "perdant");
        } else if (joueurA.getScore() == joueurB.getScore()) {
            Utils.writeLine(comB, 9, "à égalité");
            Utils.writeLine(comA, 9, "à égalité");
        }
    }


    private static String isPlayerVoyelle(Joueur joueur) {
        return joueur.isVoyellePlayer() ? "true" : "false";
    }

    private static void afficherScore(Joueur joueurA, Joueur joueurB) {
        System.out.println("Score " + joueurA.getNom() + " : " + Utils.getLine(2, comA));
        System.out.println("Score " + joueurB.getNom() + " : " + Utils.getLine(2, comB));
    }


    public static void LancerProgramme(char joueur) {

        // Programme Java à exécuter
        String classeJava = "ConsoleJoueur " + joueur; // Nom de la classe principale à exécuter (sans .class)
        String cheminFichierClasse = ".";  // Répertoire contenant le fichier .class
        String nomJoueur = "joueur " + joueur;


        // Détecter le système d'exploitation
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                // Commande pour Windows
                String commande = "\"java -cp " + cheminFichierClasse + " " + classeJava + "\"";
                System.out.println("Commande : " + commande);

                // Créer un ProcessBuilder avec cmd et start pour ouvrir une nouvelle console
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", nomJoueur, "cmd.exe", "/k", commande);
                // Lancer le processus
                pb.start();

            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                // Commande pour Linux
                String commande = "xterm -geometry 100x30+200+200 -hold -e java -cp " + cheminFichierClasse + " " + classeJava;
                System.out.println("Commande : " + commande);
                Runtime.getRuntime().exec(new String[]{"bash", "-c", commande});

            } else if (os.contains("mac")) {
                // Commande pour macOS
                String commande = "osascript -e 'tell application \"Terminal\" to do script \"java -cp " + cheminFichierClasse + " " + classeJava + "\"'";
                System.out.println("Commande : " + commande);
                Runtime.getRuntime().exec(new String[]{"bash", "-c", commande});

            } else {
                System.out.println("Système d'exploitation non pris en charge.");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'exécution de la commande : " + e.getMessage());
        }
    }


    public static void CompilerTout() {
        String[] ListeProgrammes = {"FileChecker", "ConsoleJoueur", "Consonne", "ConverterUtils", "FileLine", "Joueur", "LettresUtils", "Lire", "ModeChiffres", "ModeLettres", "OperationUtils", "SaisieChiffre", "SaisieLettres", "ScoreUtils", "Utils", "Voyelle"};

        for (String nomFichier : ListeProgrammes) {
            // Chemin du fichier source Java à compiler
            String cheminFichier = nomFichier + ".java";

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


    public static List<FileLine> viderLeFichier() {
        List<FileLine> fileLineList = new ArrayList<>();
        for (int i = 3; i <= 9; i++) {
            fileLineList.add(new FileLine(i, ""));
        }
        return fileLineList;
    }
}
