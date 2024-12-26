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

    private final static String comA = "./comA.txt";
    private final static String comB = "./comB.txt";

    public static void main(String[] args) {

        Utils.checkFile(comA);
        Utils.checkFile(comB);

        long referenceTimeA = Utils.getLastUpdate(comA);
        long referenceTimeB = Utils.getLastUpdate(comB);

        CompilerTout();

        LancerProgramme('A');
        LancerProgramme('B');

        ConsoleJoueur.waitForUpdate(referenceTimeA, comA);
        ConsoleJoueur.waitForUpdate(referenceTimeB, comB);

        //System.out.println("Joueur A, donnez votre nom :");
        Joueur joueurA = new Joueur("A");
        joueurA.setNom(Utils.getLine(1, comA));
        System.out.println("nom joueurA : "+joueurA.getNom());

        //System.out.println("Joueur B, donnez votre nom :");
        Joueur joueurB = new Joueur("B");
        joueurB.setNom(Utils.getLine(1, comB));
        System.out.println("nom joueurB : "+joueurB.getNom());

        //Utils.writeLine(1, joueurA.getNom());
        //Utils.writeLine(2, joueurB.getNom());

        String joueurVoyelles = joueurA.getNom();


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
        Utils.writeLine(comA, 2, String.valueOf(joueurA.getScore()));
        Utils.writeLine(comB, 2, String.valueOf(joueurB.getScore()));
    }


    public static void LancerProgramme(char joueur) {

        // Programme Java à exécuter
        String classeJava = "ConsoleJoueur "+joueur; // Nom de la classe principale à exécuter (sans .class)
        String cheminFichierClasse = ".";  // Répertoire contenant le fichier .class

        // Détecter le système d'exploitation
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                // Commande pour Windows
                String commande = "cmd /c start cmd.exe /k java -cp " + cheminFichierClasse + " " + classeJava;
                commande += " && mode con: cols=100 lines=30"; // Ajuster la taille
                System.out.println("Commande : " + commande);
                Runtime.getRuntime().exec(commande);
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                // Commande pour Linux
                String commande = "xterm -geometry 100x30+200+200 -hold -e java -cp " + cheminFichierClasse + " " + classeJava;
                System.out.println("Commande : " + commande);
                Runtime.getRuntime().exec(new String[] { "bash", "-c", commande });
            } else if (os.contains("mac")) {
                // Commande pour macOS
                String commande = "osascript -e 'tell application \"Terminal\" to do script \"java -cp " + cheminFichierClasse + " " + classeJava + "\"'";
                System.out.println("Commande : " + commande);
                Runtime.getRuntime().exec(new String[] { "bash", "-c", commande });
            } else {
                System.out.println("Système d'exploitation non pris en charge.");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'exécution de la commande : " + e.getMessage());
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
