import java.util.Arrays;
import java.util.Random;

public class ModeLettres {
    public static void modeLettres(String joueurVoyelles, String nomJoueurA, String nomJoueurB) {

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



        System.out.println(nomJoueurA + " donnez votre résultat");
        String reponseJoueurA = Lire.S();

        while (reponseJoueurA.length() <= 10){
            System.out.println("Votre réponse est trop longue, recommencez");
            reponseJoueurA = Lire.S();
        }


        System.out.println(nomJoueurB + " donnez votre résultat");
        String reponseJoueurB = Lire.S();

        while (reponseJoueurB.length() <= 10)


    }
}
