// Projet informatique
// des chiffres et des lettres

import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        System.out.println("Joueur A, donnez votre nom :");
        Joueur joueurA = new Joueur();

        System.out.println("Joueur B, donnez votre nom :");
        Joueur joueurB = new Joueur();

        String joueurVoyelles = joueurA.getNom();


        for (int i = 1; i <= 10; i++) {
            // ModeLettres.modeLettres(joueurVoyelles, nomJoueurA, nomJoueurB, scoreJoueurA, scoreJoueurB);
            ModeChiffres.modeChiffres(joueurA, joueurB);

            // Pour changer à chaque tour le joueur qui choisit le nombre de voyelles
            if (Objects.equals(joueurVoyelles, joueurA.getNom())) {
                joueurVoyelles = joueurB.getNom();
            } else {
                joueurVoyelles = joueurA.getNom();
            }
        }


    }
}
