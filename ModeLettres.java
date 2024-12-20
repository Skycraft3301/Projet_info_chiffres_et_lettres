import java.util.Arrays;
import java.util.Random;

public class ModeLettres {

    private final static Integer MAX_LETTER_NUMBER = 10; // Nombres de lettres max pouvant être sélectionnés

    private final static Integer MIN_VOWEL_NUMBER = 0; // Nombre minimum pour les voyelles
    private final static Integer MAX_VOWEL_NUMBER = 10; // Nombre maximum pour les voyelles

    public static void modeLettres(String joueurVoyelles, Joueur joueurA, Joueur joueurB) {

        System.out.println("[ Mode Lettres ]");

        System.out.println(joueurVoyelles + ", combien de voyelles voulez vous ?");
        int nbrVoyelles = Lire.entierCompris(MIN_VOWEL_NUMBER, MAX_VOWEL_NUMBER);


        // Création de la liste des voyelles
        String listeDesVoyelles = createListeVoyelle();

        // Création de la liste des consonnes
        String listeDesConsonnes = createListeConsonne();

        // Génération lettres aléatoires
        char[] listeLettresDeBase = createRandomLetterList(nbrVoyelles, listeDesVoyelles, listeDesConsonnes);

        // Tri de la liste par ordre alphabétique
        Arrays.sort(listeLettresDeBase);
        //TODO revoir message
        System.out.println(listeLettresDeBase);

        // Demande réponses
        String reponseJoueurA = getReponseJoueur(joueurA);
        String reponseJoueurB = getReponseJoueur(joueurB);

        // Test réponses
        boolean erreurMotA = false;
        erreurMotA = testReponse(listeLettresDeBase, reponseJoueurA);
        boolean erreurMotB = false;
        erreurMotB = testReponse(listeLettresDeBase, reponseJoueurB);

        // Déterminer le score de chaque joueur
        scoreLettres(joueurA, reponseJoueurA, reponseJoueurB, erreurMotA, erreurMotB);
        scoreLettres(joueurB, reponseJoueurB, reponseJoueurA, erreurMotB, erreurMotA);
    }

    private static char[] createRandomLetterList(int nbrVoyelles, String listeDesVoyelles, String listeDesConsonnes) {
        char[] listeLettresDeBase = new char[MAX_LETTER_NUMBER];
        Random rand = new Random();

        for (int i = 0; i < nbrVoyelles; i++) {
            listeLettresDeBase[i] = listeDesVoyelles.charAt(rand.nextInt(listeDesVoyelles.length()));
        }
        for (int i = nbrVoyelles; i < MAX_LETTER_NUMBER; i++) {
            listeLettresDeBase[i] = listeDesConsonnes.charAt(rand.nextInt(listeDesConsonnes.length()));
        }
        return listeLettresDeBase;
    }

    private static String createListeConsonne() {
        String listeDesConsonnes = "";

        for (Consonne letter : Consonne.values()) {
            for (int i = 0; i < letter.getOcc(); i++) {
                listeDesConsonnes = listeDesConsonnes.concat(letter.name());
            }
        }
        return listeDesConsonnes;
    }

    private static String createListeVoyelle() {
        String listeDesVoyelles = "";
        for (Voyelle letter : Voyelle.values()) {
            for (int i = 0; i < letter.getOcc(); i++) {
                listeDesVoyelles = listeDesVoyelles.concat(letter.name());
            }
        }
        return listeDesVoyelles;
    }

    private static void scoreLettres(Joueur joueur, String reponseJoueur, String reponseAdversaire, boolean erreurMotJoueur, boolean erreurMotAdversaire) {
        if ((reponseJoueur.length() >= reponseAdversaire.length()) && (!erreurMotJoueur) && (!erreurMotAdversaire)) {
            joueur.setScore(joueur.getScore() + reponseJoueur.length());
        }
        if ((!erreurMotJoueur) && erreurMotAdversaire) {
            joueur.setScore(joueur.getScore() + max(reponseJoueur.length(), reponseAdversaire.length()));
        }
    }

    private static String getReponseJoueur(Joueur joueur) {
        System.out.println(joueur.getNom() + " donnez votre résultat");
        String reponseJoueur = Lire.S();
        return reponseJoueur;
    }


    // Fonction testReponse
    private static boolean testReponse(char[] listeLettresDeBase, String reponseJoueur) {
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
            while ((i < MAX_LETTER_NUMBER) && (!String.valueOf(listeLettresDeBase[i]).equalsIgnoreCase(String.valueOf(c)))) {
                i++;
            }
        }

        if (i == MAX_LETTER_NUMBER) {
            erreur = true;
            System.out.println("Les lettres utilisées ne sont pas toutes dans la liste");
            return erreur;
        }


        // Teste si le mot est dans le dictionnaire
        if (!Utils.isInDictionary(reponseJoueur)) {
            erreur = true;
            System.out.println("Le mot " + reponseJoueur + " n'est pas dans le dictionnaire");
            return erreur;
        } else {
            System.out.println("Le mot est dans le dico");
        }

        return erreur;
    }

    // Déterminer la réponse la plus longue (pour le score)
    private static int max(int repA, int repB) {
        int result = 0;
        if (repA >= repB) {
            result = repA;
        }
        if (repA < repB) {
            result = repB;
        }
        return (result);
    }
}
