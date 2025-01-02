import java.util.Arrays;

public class ModeLettres {

    private final static String comA = "./comA.txt";
    private final static String comB = "./comB.txt";

    public static void modeLettres(Joueur joueurA, Joueur joueurB) {

        System.out.println("[ Mode Lettres ]");

        String voyelleFile = joueurB.isVoyellePlayer() ? comB : comA;

        long referenceTime = Utils.getLastUpdate(voyelleFile);

        // Attendre le nombre de voyelles
        int nbrVoyelles = -1;

        while ((LettresUtils.MIN_VOWEL_NUMBER > nbrVoyelles
                || nbrVoyelles > LettresUtils.MAX_VOWEL_NUMBER)
        ) {
            referenceTime = FileChecker.waitForUpdate(referenceTime, voyelleFile);

            String voyelleNumber = Utils.getLine(8, voyelleFile);
            if (!voyelleNumber.isEmpty()) {
                try {
                    nbrVoyelles = Integer.parseInt(voyelleNumber);
                } catch (NumberFormatException e) {
                    nbrVoyelles = -1;
                }
            }
        }
        System.out.println("nbrVoyelles : " + nbrVoyelles);

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
        FileChecker.checkForUpdate(comA, 7, referenceTime);
        FileChecker.checkForUpdate(comB, 7, referenceTime);

        String reponseJoueurA = Utils.getLine(7, comA);
        String reponseJoueurB = Utils.getLine(7, comB);

        // Test des réponses
        boolean erreurMotA = testReponse(listeLettresDeBase, reponseJoueurA);
        boolean erreurMotB = testReponse(listeLettresDeBase, reponseJoueurB);

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
