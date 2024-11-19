import java.util.Arrays;
import java.util.Random;

public class ModeLettres {
    public static void modeLettres(String joueurVoyelles, String nomJoueurA, String nomJoueurB, int scoreJoueurA, int scoreJoueurB) {

        System.out.println("[ Mode Lettres ]");

        System.out.println(joueurVoyelles + ", combien de voyelles voulez vous ?"); // corriger plusieurs fois (voir Lire)
        int nbrVoyelles = Lire.i();
        if (0>=nbrVoyelles || nbrVoyelles>=10) {
            System.out.println("donnez un nombre de voyelles entre 0 et 10");
            nbrVoyelles = Lire.i();
        }



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
        char[] listeLettresDeBase = new char[10];

        for (int i=0 ; i<nbrVoyelles ; i++) {
            Random rand = new Random();
            char letter = listeDesVoyelles.charAt(rand.nextInt(listeDesVoyelles.length()));
            listeLettresDeBase[i] = letter ;
        }
        for (int i=(nbrVoyelles) ; i<10 ; i++) {
            Random rand = new Random();
            char letter = listeDesConsonnes.charAt(rand.nextInt(listeDesConsonnes.length()));
            listeLettresDeBase[i] = letter ;
        }


        // Tri de la liste par ordre alphabétique
        Arrays.sort(listeLettresDeBase);

        System.out.println(listeLettresDeBase);


        // Demande réponse du joueur A
        System.out.println(nomJoueurA + " donnez votre résultat");
        String reponseJoueurA = Lire.S();
        //System.out.println(reponseJoueurA);


        // Demande réponse du joueur B
        System.out.println(nomJoueurB + " donnez votre résultat");
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
            scoreJoueurA += reponseJoueurA.length();
        }
        if ((reponseJoueurB.length() >= reponseJoueurA.length()) && (!erreurMotA) && (!erreurMotB)){
            scoreJoueurB += reponseJoueurB.length();
        }
        if ((!erreurMotA) && (erreurMotB)){
            scoreJoueurA += max(reponseJoueurA.length(), reponseJoueurB.length());
        }
        if ((erreurMotA) && (!erreurMotB)){
            scoreJoueurB += max(reponseJoueurA.length(), reponseJoueurB.length());
        }
        //System.out.println("Score de "+nomJoueurA+" = "+scoreJoueurA);
        //System.out.println("Score de "+nomJoueurB+" = "+scoreJoueurB);

    }



    // Fonction testReponse
    private static boolean testReponse(char[] listeLettresDeBase, String reponseJoueur){
        boolean erreur = false;

        System.out.println("hello1");

        // Test de longueur
        if (reponseJoueur.length() > 10){
            System.out.println("hello2");
            erreur = true;
            System.out.println("Votre réponse est trop longue");
            return erreur;
        }


        // Test si les lettres utilisées sont toutes dans la liste
        char[] tabReponseJoueur = new char[reponseJoueur.length()];
        for (int i=0 ; i<10 ; i++){
            if (reponseJoueur.length() > i){
                System.out.println("hello3");
                tabReponseJoueur[i] = reponseJoueur.charAt(i);
            }
        }
        System.out.println(tabReponseJoueur);

        Arrays.sort(tabReponseJoueur);
        System.out.println(tabReponseJoueur);

        System.out.println("hello4");

        // attention boucle infinie + ignoreCase
        int c = 0;
        while (c < reponseJoueur.length()) {
            for (int i = 0; i < 10; i++) {
                if (Character.toLowerCase(listeLettresDeBase[i]) == Character.toLowerCase(tabReponseJoueur[c])) {
                    c++;
                }
            }
        }
        if (reponseJoueur.length() != c){
            System.out.println("hello5");
            erreur = true;
            System.out.println("Les lettres utilisées ne sont pas toutes dans la liste");
            return erreur;
        }


        // Teste si le mot est dans le dictionnaire
        System.out.println("hello6");
        if (!IsInDictionary.isInDictionary(reponseJoueur)){
            System.out.println("hello7");
            erreur = true;
            System.out.println("Le mot n'est pas dans le dictionnaire");
            return erreur;
        }else {
            System.out.println("Le mot est dans le dico");
        }
        System.out.println("hello8");

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
