import java.util.Arrays;
import java.util.Random;

public class ModeLettres {

    private final static Integer MAX_LETTER_NUMBER = 10; // Nombres de lettres max pouvant être sélectionnés

    private final static Integer MIN_VOWEL_NUMBER = 0; // Nombre minimum pour les voyelles
    private final static Integer MAX_VOWEL_NUMBER = 10; // Nombre maximum pour les voyelles

    public static void modeLettres(String joueurVoyelles, Joueur joueurA, Joueur joueurB) {

        System.out.println("[ Mode Lettres ]");

        System.out.println(joueurVoyelles + ", combien de voyelles voulez vous ?"); // corriger plusieurs fois (voir Lire)
        int nbrVoyelles = Lire.entierCompris(MIN_VOWEL_NUMBER, MAX_VOWEL_NUMBER);


        // Création de la liste des voyelles
        String listeDesVoyelles = "";

        for (Voyelle letter : Voyelle.values()){
            for (int i=0 ; i<letter.getOcc() ; i++) {
                listeDesVoyelles = listeDesVoyelles.concat(letter.name());
            }
        }
        // Création de la liste des consonnes
        String listeDesConsonnes = "";

        for (Consonne letter : Consonne.values()){
            for (int i=0 ; i<letter.getOcc() ; i++) {
                listeDesConsonnes = listeDesConsonnes.concat(letter.name());
            }
        }


        // Génération lettres aléatoires
        char[] listeLettresDeBase = new char[MAX_LETTER_NUMBER];
        Random rand = new Random();

        for (int i=0 ; i<nbrVoyelles ; i++) {
            listeLettresDeBase[i] = listeDesVoyelles.charAt(rand.nextInt(listeDesVoyelles.length()));
        }
        for (int i=(nbrVoyelles) ; i<MAX_LETTER_NUMBER ; i++) {
            listeLettresDeBase[i] = listeDesConsonnes.charAt(rand.nextInt(listeDesConsonnes.length()));
        }


        // Tri de la liste par ordre alphabétique
        Arrays.sort(listeLettresDeBase);

        System.out.println(listeLettresDeBase);


        // Demande réponse du joueur A
        System.out.println(joueurA.getNom() + " donnez votre résultat");
        String reponseJoueurA = Lire.S();
        //System.out.println(reponseJoueurA);


        // Demande réponse du joueur B
        System.out.println(joueurB.getNom() + " donnez votre résultat");
        String reponseJoueurB = Lire.S();
        //System.out.println(reponseJoueurB);


        // Test réponse du joueur A
        boolean erreurMotA = false;

        erreurMotA = testReponse(listeLettresDeBase, reponseJoueurA);


        // Test réponse du joueur B
        boolean erreurMotB = false;

        erreurMotB = testReponse(listeLettresDeBase, reponseJoueurB);



        /*while (reponseJoueurA.isEmpty()) {
            try {
                getResult(nomJoueurA, listeLettresDeBase, reponseJoueurA);
            } catch (TooLongException | NotGoodLettersException | NotInDictionnaryException e) {
                e.printStackTrace();    // à mettre en commentaire
                erreurMotA = true;
            }
        }*/

        /*while (reponseJoueurB.isEmpty()) {
            try {
                getResult(nomJoueurB, listeLettresDeBase, reponseJoueurB);
            } catch (TooLongException | NotGoodLettersException | NotInDictionnaryException e) {
                //e.printStackTrace();
                erreurMotB = true;
            }
        }*/



        // Déterminer le score de chaque joueur
        if ((reponseJoueurA.length() >= reponseJoueurB.length()) && (!erreurMotA) && (!erreurMotB)){
            joueurA.setScore(joueurA.getScore() + reponseJoueurA.length());
        }
        if ((reponseJoueurB.length() >= reponseJoueurA.length()) && (!erreurMotA) && (!erreurMotB)){
            joueurB.setScore(joueurB.getScore() + reponseJoueurB.length());
        }
        if ((!erreurMotA) && (erreurMotB)){
            joueurA.setScore(joueurA.getScore() + max(reponseJoueurA.length(), reponseJoueurB.length()));
        }
        if ((erreurMotA) && (!erreurMotB)){
            joueurB.setScore(joueurB.getScore() + max(reponseJoueurA.length(), reponseJoueurB.length()));
        }
    }



    // Fonction testReponse
    private static boolean testReponse(char[] listeLettresDeBase, String reponseJoueur){
        boolean erreur = false;

        // Test de longueur
        if (reponseJoueur.length() > 10){
            erreur = true;
            System.out.println("Votre réponse est trop longue");
            return erreur;
        }


        // Test si les lettres utilisées sont toutes dans la liste
        char[] tabReponseJoueur = reponseJoueur.toCharArray();
        //System.out.println(tabReponseJoueur);

        Arrays.sort(tabReponseJoueur);
        //System.out.println(tabReponseJoueur);

        int i=0;
        for (char c : tabReponseJoueur) {
            while ((i < MAX_LETTER_NUMBER) && (!String.valueOf(listeLettresDeBase[i]).equalsIgnoreCase(String.valueOf(c)))) {
                i++;
            }
        }


        //if (reponseJoueur.length() != c){
        if (i == MAX_LETTER_NUMBER){
            erreur = true;
            System.out.println("Les lettres utilisées ne sont pas toutes dans la liste");
            return erreur;
        }


        // Teste si le mot est dans le dictionnaire
        if (!Utils.isInDictionary(reponseJoueur)){
            erreur = true;
            System.out.println("Le mot "+reponseJoueur+" n'est pas dans le dictionnaire");
            return erreur;
        }else {
            System.out.println("Le mot est dans le dico");
        }

        return(erreur);
    }


    // Déterminer la réponse la plus longue (pour le score)
    private static int max(int repA, int repB){
        int result = 0;
        if (repA >= repB){
            result = repA;
        }
        if (repA < repB){
            result = repB;
        }
        return(result);
    }
}
