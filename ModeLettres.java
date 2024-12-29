import java.util.Arrays;
import static java.lang.Math.max;

public class ModeLettres {

    private final static String comA = "./comA.txt";
    private final static String comB = "./comB.txt";

    public static void modeLettres(String joueurVoyelles, Joueur joueurA, Joueur joueurB) {

        System.out.println("[ Mode Lettres ]");

        Utils.writeLine("all", 8, joueurVoyelles);
        long referenceTime = max(Utils.getLastUpdate(comA), Utils.getLastUpdate(comB));
        
        int charA = (int) (Utils.getLine(8, comA)).charAt(0) -48;
        int charB = (int) (Utils.getLine(8, comB)).charAt(0) -48;
        while ((LettresUtils.MIN_VOWEL_NUMBER >= charA
                || charA >= LettresUtils.MAX_VOWEL_NUMBER)
                && (LettresUtils.MIN_VOWEL_NUMBER >= charB
                || charB >= LettresUtils.MAX_VOWEL_NUMBER)){
            referenceTime = ConsoleJoueur.waitForUpdate(referenceTime, comA, comB);
            charA = (int) (Utils.getLine(8, comA)).charAt(0) -48;
            charB = (int) (Utils.getLine(8, comB)).charAt(0) -48;
        }

        /*System.out.println(joueurVoyelles + ", combien de voyelles voulez vous ?");
        int nbrVoyelles = Lire.entierCompris(LettresUtils.MIN_VOWEL_NUMBER, LettresUtils.MAX_VOWEL_NUMBER);*/

        referenceTime = ConsoleJoueur.waitForUpdate(referenceTime, comA, comB);


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

        // Création de la liste des voyelles
        String listeDesVoyelles = LettresUtils.createListeVoyelle();

        // Création de la liste des consonnes
        String listeDesConsonnes = LettresUtils.createListeConsonne();

        // Génération lettres aléatoires
        char[] listeLettresDeBase = LettresUtils.createRandomLetterList(nbrVoyelles, listeDesVoyelles, listeDesConsonnes);

        // Tri de la liste par ordre alphabétique
        Arrays.sort(listeLettresDeBase);

        // ecriture dans le fichier
        //Utils.writeLine(6, ConverterUtils.charArrayToString(listeLettresDeBase));

        //TODO à mettre dans Presentateur pour calculer score
        /*//Test réponses
        boolean erreurMotA = false;
        erreurMotA = SaisieLettres.testReponse(listeLettresDeBase, reponseJoueurA);
        boolean erreurMotB = false;
        erreurMotB = SaisieLettres.testReponse(listeLettresDeBase, reponseJoueurB);*/
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
