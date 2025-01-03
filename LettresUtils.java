import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class LettresUtils {
    public final static Integer MAX_LETTER_NUMBER = 10; // Nombres de lettres max pouvant être sélectionnés

    public final static Integer MIN_VOWEL_NUMBER = 0; // Nombre minimum pour les voyelles
    public final static Integer MAX_VOWEL_NUMBER = 10; // Nombre maximum pour les voyelles

    public static char[] createRandomLetterList(int nbrVoyelles, String listeDesVoyelles, String listeDesConsonnes) {
        char[] listeLettresDeBase = new char[LettresUtils.MAX_LETTER_NUMBER];
        Random rand = new Random();

        for (int i = 0; i < nbrVoyelles; i++) {
            listeLettresDeBase[i] = listeDesVoyelles.charAt(rand.nextInt(listeDesVoyelles.length()));
        }
        for (int i = nbrVoyelles; i < LettresUtils.MAX_LETTER_NUMBER; i++) {
            listeLettresDeBase[i] = listeDesConsonnes.charAt(rand.nextInt(listeDesConsonnes.length()));
        }
        return listeLettresDeBase;
    }

    public static String createListeConsonne() {
        String listeDesConsonnes = "";

        for (Consonne letter : Consonne.values()) {
            for (int i = 0; i < letter.getOcc(); i++) {
                listeDesConsonnes = listeDesConsonnes.concat(letter.name());
            }
        }
        return listeDesConsonnes;
    }

    public static String createListeVoyelle() {
        String listeDesVoyelles = "";
        for (Voyelle letter : Voyelle.values()) {
            for (int i = 0; i < letter.getOcc(); i++) {
                listeDesVoyelles = listeDesVoyelles.concat(letter.name());
            }
        }
        return listeDesVoyelles;
    }

    public static boolean isInDictionary(String reponseJoueur) {

        boolean testResult = false;

        String dico = "./dictionnaire.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(dico))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                if (ligne.equalsIgnoreCase(reponseJoueur)) {
                    testResult = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (testResult) {
            System.out.println("Le mot \"" + reponseJoueur + "\" a été trouvé dans le fichier.");
        } else {
            System.out.println("Le mot \"" + reponseJoueur + "\" n'a pas été trouvé dans le fichier.");
        }
        return testResult;
    }

    public static String solutionOptimale(char[] listeLettresDeBase){

        String dico = "./dictionnaire.txt";

        for (int i=10 ; i>=1 ; i--) {
            try (BufferedReader br = new BufferedReader(new FileReader(dico))) {
                String ligne;

                while ((ligne = br.readLine()) != null) {
                    if (ligne.length() == i) {
                        if (utiliseLettres(listeLettresDeBase, ligne)) {
                            return ligne;
                            //break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "pas de solution trouvée";
    }

    public static boolean utiliseLettres(char[] listeLettresDeBase, String ligne){
        // Teste si les lettres utilisées sont toutes dans la liste
        char[] tabLigne = ligne.toCharArray();

        Arrays.sort(tabLigne);

        int c = 0;
        for (int i=0 ; i<10 ; i++) {
            if (String.valueOf(listeLettresDeBase[i]).equalsIgnoreCase(String.valueOf(tabLigne[c]))) {
                c++;
                if (c == ligne.length()) {
                    return true;
                }
            }
        }

        //System.out.println("Les lettres utilisées ne sont pas toutes dans la liste : " + ligne);
        return false;

    }
}
