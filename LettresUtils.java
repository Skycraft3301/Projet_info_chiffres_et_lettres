import java.io.*;
import java.util.Objects;
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

        File dico = new File("./dictionnaire.txt");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dico), "UTF-8"));
            String line = reader.readLine();

            while ((line != null) && (!line.equals(reponseJoueur))) {
                //System.out.println(line);
                line = reader.readLine();
            }

            if (Objects.equals(line, reponseJoueur)) {
                testResult = true;
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return testResult;
    }
}
