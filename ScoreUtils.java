import java.util.Objects;

public class ScoreUtils {
    // TODO revoir méthode
    public static void getScores(Joueur joueurA, Joueur joueurB, Integer resultatJoueurA, Integer resultatJoueurB, Integer goal, String reponseJoueurA, String reponseJoueurB, boolean erreurMotA, boolean erreurMotB) {
        scoreChiffre(joueurA, Presentateur.comB, resultatJoueurA, resultatJoueurB, goal);
        scoreChiffre(joueurB, Presentateur.comB, resultatJoueurB, resultatJoueurA, goal);

        scoreLettres(joueurA, reponseJoueurA, reponseJoueurB, erreurMotA, erreurMotB);
        scoreLettres(joueurB, reponseJoueurB, reponseJoueurA, erreurMotB, erreurMotA);
    }

    public static void scoreChiffre(Joueur joueur, String file, Integer resultatJoueur, Integer resultatAdversaire, Integer goal) {
        //System.out.println("\nLe score de " + joueur.getNom() + " était de " + joueur.getScore());
        int points = 0;
        if (Objects.equals(resultatJoueur, goal)) {
            points = 10;
        } else if ((Math.abs(resultatJoueur - goal) < 100) && (Math.abs(resultatJoueur - goal) <= (Math.abs(resultatAdversaire - goal)))) {
            points = 7;
        }
        joueur.setScore(joueur.getScore() + points);
        ConsoleJoueur.waitForUpdate(file, 2, String.valueOf(joueur.getScore()));
        /*System.out.println(joueur.getNom() + " a gagné " + points + " points sur ce tour");
        System.out.println("Son score est maintenant de " + joueur.getScore() + "\n");*/
    }

    public static void scoreLettres(Joueur joueur, String reponseJoueur, String reponseAdversaire, boolean erreurMotJoueur, boolean erreurMotAdversaire) {
        if ((reponseJoueur.length() >= reponseAdversaire.length()) && (!erreurMotJoueur) && (!erreurMotAdversaire)) {
            joueur.setScore(joueur.getScore() + reponseJoueur.length());
        }
        if ((!erreurMotJoueur) && erreurMotAdversaire) {
            joueur.setScore(joueur.getScore() + Math.max(reponseJoueur.length(), reponseAdversaire.length()));
        }
    }
}
