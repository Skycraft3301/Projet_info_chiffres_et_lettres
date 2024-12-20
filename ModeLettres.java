import java.util.Arrays;

public class ModeLettres {
    public static void modeLettres(String joueurVoyelles, Joueur joueurA, Joueur joueurB) {

        System.out.println("[ Mode Lettres ]");

        System.out.println(joueurVoyelles + ", combien de voyelles voulez vous ?");
        int nbrVoyelles = Lire.entierCompris(LettresUtils.MIN_VOWEL_NUMBER, LettresUtils.MAX_VOWEL_NUMBER);

        // Création de la liste des voyelles
        String listeDesVoyelles = LettresUtils.createListeVoyelle();

        // Création de la liste des consonnes
        String listeDesConsonnes = LettresUtils.createListeConsonne();

        // Génération lettres aléatoires
        char[] listeLettresDeBase = LettresUtils.createRandomLetterList(nbrVoyelles, listeDesVoyelles, listeDesConsonnes);

        // Tri de la liste par ordre alphabétique
        Arrays.sort(listeLettresDeBase);

        //TODO à mettre ailleurs
        //Demande réponses
        /*String reponseJoueurA = SaisieLettres.getReponseJoueur(joueurA, listeLettresDeBase);
        String reponseJoueurB = SaisieLettres.getReponseJoueur(joueurB, listeLettresDeBase);

        // Test réponses
        boolean erreurMotA = false;
        erreurMotA = SaisieLettres.testReponse(listeLettresDeBase, reponseJoueurA);
        boolean erreurMotB = false;
        erreurMotB = SaisieLettres.testReponse(listeLettresDeBase, reponseJoueurB);*/
    }


}
