import java.util.Arrays;
import java.util.Random;

public class ModeLettres {
    public static void modeLettres(String joueurVoyelles, String nomJoueurA, String nomJoueurB, int scoreJoueurA, int scoreJoueurB) {

        System.out.println("[ Mode Lettres ]");

        System.out.println(joueurVoyelles + ", combien de voyelles voulez vous ?");
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


        // Test réponse du joueur A
        String reponseJoueurA = "";
        boolean erreurMotA = false;

        while (reponseJoueurA.isEmpty()) {
            try {
                reponseJoueurA = getResult(nomJoueurA, listeLettresDeBase);
            } catch (TooLongException | NotGoodLettersException | NotInDictionnaryException e) {
                //e.printStackTrace();
                erreurMotA = true;
            }
        }

        System.out.println(reponseJoueurA);


        // Test réponse du joueur B
        String reponseJoueurB = "";
        boolean erreurMotB = false;

        while (reponseJoueurB.isEmpty()) {
            try {
                reponseJoueurB = getResult(nomJoueurB, listeLettresDeBase);
            } catch (TooLongException | NotGoodLettersException | NotInDictionnaryException e) {
                //e.printStackTrace();
                erreurMotB = true;
            }
        }

        System.out.println(reponseJoueurB);


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
        System.out.println("Score de "+nomJoueurA+" = "+scoreJoueurA);
        System.out.println("Score de "+nomJoueurB+" = "+scoreJoueurB);

    }

    private static String getResult(String nomJoueur, char[] listeLettresDeBase) throws TooLongException , NotGoodLettersException , NotInDictionnaryException  {
        System.out.println(nomJoueur + " donnez votre résultat");
        String reponseJoueur = Lire.S();

        if (reponseJoueur.length() > 10){
            throw new TooLongException();
        }

        char[] tabReponseJoueur = new char[reponseJoueur.length()];
        for (int i=0 ; i<10 ; i++){
            if (reponseJoueur.length() > i){
                tabReponseJoueur[i] = reponseJoueur.charAt(i);
            }
        }
        System.out.println(tabReponseJoueur);

        Arrays.sort(tabReponseJoueur);
        System.out.println(tabReponseJoueur);

        int c = 0;
        while (c < reponseJoueur.length()) {
            for (int i = 0; i < (reponseJoueur.length()); i++) {
                if (listeLettresDeBase[i] == tabReponseJoueur[c]) {
                    c++;
                }
            }
        }
        if (reponseJoueur.length() != c){
            throw new NotGoodLettersException();
        }

        if (IsInDictionary.isInDictionary(reponseJoueur) == false){
            throw new NotInDictionnaryException();
        }

        return(reponseJoueur);
    }

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
