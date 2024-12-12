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


        for (int i = 1; i <= 5; i++) {
            //ModeChiffres.modeChiffres(joueurA, joueurB);
            ModeLettres.modeLettres(joueurVoyelles, joueurA, joueurB);

            //Afficher le score
            System.out.println("Score "+joueurA.getNom()+" : "+joueurA.getScore());
            System.out.println("Score "+joueurB.getNom()+" : "+joueurB.getScore());


            // Pour changer à chaque tour le joueur qui choisit le nombre de voyelles
            if (Objects.equals(joueurVoyelles, joueurA.getNom())) {
                joueurVoyelles = joueurB.getNom();
            } else {
                joueurVoyelles = joueurA.getNom();
            }
        }


    }
}