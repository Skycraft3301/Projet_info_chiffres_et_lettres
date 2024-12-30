import java.util.Arrays;
import java.util.Objects;

import static java.lang.Math.max;

public class ModeLettres {

    private final static String comA = "./comA.txt";
    private final static String comB = "./comB.txt";

    public static void modeLettres(String joueurVoyelles, Joueur joueurA, Joueur joueurB) {

        System.out.println("[ Mode Lettres ]");

        long referenceTime = max(Utils.getLastUpdate(comA), Utils.getLastUpdate(comB));

        // Attendre le nombre de voyelles
        int charA;
        int charB;
        if (!Objects.equals(Utils.getLine(8, comA), "")) {
            charA = (int) (Utils.getLine(8, comA)).charAt(0) -48;
        }else {
            charA = -1;
        }
        if (!Objects.equals(Utils.getLine(8, comB), "")) {
            charB = (int) (Utils.getLine(8, comB)).charAt(0) -48;
        }else {
            charB = -1;
        }

        while ((LettresUtils.MIN_VOWEL_NUMBER >= charA
                || charA >= LettresUtils.MAX_VOWEL_NUMBER)
                && (LettresUtils.MIN_VOWEL_NUMBER >= charB
                || charB >= LettresUtils.MAX_VOWEL_NUMBER)){
            referenceTime = ConsoleJoueur.waitForUpdate(referenceTime, comA, comB);

            if (!Objects.equals(Utils.getLine(8, comA), "")) {
                charA = (int) (Utils.getLine(8, comA)).charAt(0) -48;
            }
            if (!Objects.equals(Utils.getLine(8, comB), "")) {
                charB = (int) (Utils.getLine(8, comB)).charAt(0) -48;
            }
        }

        // Récupération du nombre de voyelles
        int nbrVoyelles = -1;
        if (LettresUtils.MIN_VOWEL_NUMBER <= charA && charA <= LettresUtils.MAX_VOWEL_NUMBER
                && (LettresUtils.MIN_VOWEL_NUMBER >= charB || charB >= LettresUtils.MAX_VOWEL_NUMBER)) {
            nbrVoyelles = charA;
        } else if (LettresUtils.MIN_VOWEL_NUMBER <= charB && charB <= LettresUtils.MAX_VOWEL_NUMBER
                && (LettresUtils.MIN_VOWEL_NUMBER >= charA || charA >= LettresUtils.MAX_VOWEL_NUMBER)) {
            nbrVoyelles = charB;
        } else {
            System.out.println("erreur sur nbrVoyelles");
        }
        System.out.println("nbrVoyelles : "+nbrVoyelles);

        // Création de la liste des voyelles
        String listeDesVoyelles = LettresUtils.createListeVoyelle();

        // Création de la liste des consonnes
        String listeDesConsonnes = LettresUtils.createListeConsonne();

        // Génération lettres aléatoires
        char[] listeLettresDeBase = LettresUtils.createRandomLetterList(nbrVoyelles, listeDesVoyelles, listeDesConsonnes);

        // Tri de la liste par ordre alphabétique
        Arrays.sort(listeLettresDeBase);

        // Écriture dans le fichier
        Utils.writeLine("all", 6, ConverterUtils.charArrayToString(listeLettresDeBase));

        // Attendre les réponses des joueurs
        while (Objects.equals(Utils.getLine(7, comA), "")) {
            referenceTime = ConsoleJoueur.waitForUpdate(referenceTime, comA);
        }
        while (Objects.equals(Utils.getLine(7, comB), "")) {
            referenceTime = ConsoleJoueur.waitForUpdate(referenceTime, comB);
        }
        String reponseJoueurA = Utils.getLine(7, comA);
        String reponseJoueurB = Utils.getLine(7, comB);

        // Test des réponses
        boolean erreurMotA = false;
        erreurMotA = testReponse(listeLettresDeBase, reponseJoueurA);
        boolean erreurMotB = false;
        erreurMotB = testReponse(listeLettresDeBase, reponseJoueurB);

        // Calcul du score
        ScoreUtils.scoreLettres(joueurA, comA, reponseJoueurA, reponseJoueurB, erreurMotA, erreurMotB);
        ScoreUtils.scoreLettres(joueurB, comB, reponseJoueurB, reponseJoueurA, erreurMotB, erreurMotA);
    }

    // Fonction testReponse
    public static boolean testReponse(char[] listeLettresDeBase, String reponseJoueur) {
        boolean erreur = false;

        // Test de longueur
        if (reponseJoueur.length() > 10) {
            erreur = true;
            System.out.println("Votre réponse est trop longue");
            return erreur;
        }


        // Test si les lettres utilisées sont toutes dans la liste
        char[] tabReponseJoueur = reponseJoueur.toCharArray();

        Arrays.sort(tabReponseJoueur);

        int i = 0;
        for (char c : tabReponseJoueur) {
            while ((i < LettresUtils.MAX_LETTER_NUMBER) && (!String.valueOf(listeLettresDeBase[i]).equalsIgnoreCase(String.valueOf(c)))) {
                i++;
            }
        }

        if (i == LettresUtils.MAX_LETTER_NUMBER) {
            erreur = true;
            System.out.println("Les lettres utilisées ne sont pas toutes dans la liste");
            return erreur;
        }


        // Teste si le mot est dans le dictionnaire
        if (!LettresUtils.isInDictionary(reponseJoueur)) {
            erreur = true;
            System.out.println("Le mot " + reponseJoueur + " n'est pas dans le dictionnaire");
            return erreur;
        } else {
            System.out.println("Le mot est dans le dico");
        }

        return erreur;
    }
}
