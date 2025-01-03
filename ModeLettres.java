import java.util.Arrays;
import java.util.List;

public class ModeLettres {

    private final static String comA = "./comA.txt";
    private final static String comB = "./comB.txt";

    public static void modeLettres(Joueur joueurA, Joueur joueurB) throws InterruptedException {

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
        long referenceTimeA = Utils.updateFile(comA, List.of(
                new FileLine(6, ConverterUtils.charArrayToString(listeLettresDeBase)),
                new FileLine(13, LettresUtils.solutionOptimale(listeLettresDeBase))
        ));

        long referenceTimeB = Utils.updateFile(comB, List.of(
                new FileLine(6, ConverterUtils.charArrayToString(listeLettresDeBase)),
                new FileLine(13, LettresUtils.solutionOptimale(listeLettresDeBase))
        ));

        //Utils.writeLine("all", 6, ConverterUtils.charArrayToString(listeLettresDeBase));
        // Écrire la solution optimale dans le fichier
        //Utils.writeLine("all", 13, LettresUtils.solutionOptimale(listeLettresDeBase));

        // Attendre les réponses des joueurs
        FileChecker.checkForUpdate(comA, comB, 7, referenceTimeA, referenceTimeB);
        Thread.sleep(500);
        //FileChecker.checkForUpdate(comA, 7, referenceTime);
        //FileChecker.checkForUpdate(comB, 7, referenceTime);

        String reponseJoueurA = Utils.getLine(7, comA);
        String reponseJoueurB = Utils.getLine(7, comB);

        // Test des réponses
        MessageErreur messageErreurA = testReponse(listeLettresDeBase, reponseJoueurA, comA);
        MessageErreur messageErreurB = testReponse(listeLettresDeBase, reponseJoueurB, comB);
        boolean erreurMotA = messageErreurA.getErreur();
        boolean erreurMotB = messageErreurB.getErreur();

        // Calcul du score
        ScoreUtils.scoreLettres(joueurA, comA, reponseJoueurA, reponseJoueurB, erreurMotA, erreurMotB, messageErreurA);
        ScoreUtils.scoreLettres(joueurB, comB, reponseJoueurB, reponseJoueurA, erreurMotB, erreurMotA, messageErreurB);
    }

    // Fonction testReponse
    public static MessageErreur testReponse(char[] listeLettresDeBase, String reponseJoueur, String file) {
        //boolean erreur = false;

        // Test de longueur
        if (reponseJoueur.length() > 10) {
            //erreur = true;
            System.out.println("Votre réponse est trop longue");
            //Utils.writeLine(file, 11, "Votre réponse est trop longue");
            return new MessageErreur("Votre réponse est trop longue", true);
        }


        // Teste si les lettres utilisées sont toutes dans la liste
        /*char[] tabReponseJoueur = reponseJoueur.toCharArray();

        Arrays.sort(tabReponseJoueur);

        int i = 0;
        for (char c : tabReponseJoueur) {
            while ((i < LettresUtils.MAX_LETTER_NUMBER) && (!String.valueOf(listeLettresDeBase[i]).equalsIgnoreCase(String.valueOf(c)))) {
                i++;
            }
        }

        if (i == LettresUtils.MAX_LETTER_NUMBER) {
            //erreur = true;
            System.out.println("Les lettres utilisées ne sont pas toutes dans la liste");
            //Utils.writeLine(file, 11, "Les lettres utilisées ne sont pas toutes dans la liste");
            return new MessageErreur("Les lettres utilisées ne sont pas toutes dans la liste", true);
        }*/

        // Teste si les lettres utilisées sont toutes dans la liste
        char[] tabReponseJoueur = reponseJoueur.toCharArray();

        Arrays.sort(tabReponseJoueur);

        int c = 0;
        for (int i = 0; i < 10; i++) {
            if (String.valueOf(listeLettresDeBase[i]).equalsIgnoreCase(String.valueOf(tabReponseJoueur[c]))) {
                c++;
                if (c == reponseJoueur.length()) {
                    i = 10;
                }
            }
        }
        if (c != reponseJoueur.length()) {
            //erreur = true;
            System.out.println("Les lettres utilisées ne sont pas toutes dans la liste");
            //Utils.writeLine(file, 11, "Les lettres utilisées ne sont pas toutes dans la liste");
            return new MessageErreur("Les lettres utilisées ne sont pas toutes dans la liste", true);
        }


        // Teste si le mot est dans le dictionnaire
        if (!LettresUtils.isInDictionary(reponseJoueur)) {
            //erreur = true;
            System.out.println("Le mot " + reponseJoueur + " n'est pas dans le dictionnaire");
            //Utils.writeLine(file, 11, "Le mot " + reponseJoueur + " n'est pas dans le dictionnaire");
            return new MessageErreur("Le mot " + reponseJoueur + " n'est pas dans le dictionnaire", true);
        } else {
            System.out.println("Le mot est dans le dictionnaire");
            //Utils.writeLine(file, 11, "Le mot est dans le dictionnaire");
            // Utils.writeLine(file, 11, " "); // pour éviter de ne rien écrire
        }

        return new MessageErreur("Le mot " + reponseJoueur + " est valide", false);
    }
}
