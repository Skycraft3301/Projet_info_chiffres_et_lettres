import java.util.Arrays;

public class SaisieLettres {
    public static String getReponseJoueur(Joueur joueur, char[] listeLettresDeBase) {
        System.out.println("Voici les lettres sélectionnées : " + Arrays.toString(listeLettresDeBase));
        System.out.println(joueur.getNom() + " donnez votre résultat");
        return Lire.S();
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
        if (!Utils.isInDictionary(reponseJoueur)) {
            erreur = true;
            System.out.println("Le mot " + reponseJoueur + " n'est pas dans le dictionnaire");
            return erreur;
        } else {
            System.out.println("Le mot est dans le dico");
        }

        return erreur;
    }
}
