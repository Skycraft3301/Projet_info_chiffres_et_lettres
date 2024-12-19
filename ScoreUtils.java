import java.util.Objects;

public class ScoreUtils {
    // TODO revoir méthode
    public static void getScores(Joueur joueurA, Joueur joueurB, Integer resultatJoueurA, Integer resultatJoueurB, Integer goal) {
        scoreChiffre(joueurA, resultatJoueurA, resultatJoueurB, goal);
        scoreChiffre(joueurB, resultatJoueurB, resultatJoueurA, goal);
    }

    private static void scoreChiffre(Joueur joueur, Integer resultatJoueur, Integer resultatAdversaire, Integer goal) {
        System.out.println("\nLe score de " + joueur.getNom() + " était de " + joueur.getScore());
        int points = 0;
        if (Objects.equals(resultatJoueur, goal)) {
            points = 10;
        } else if ((Math.abs(resultatJoueur - goal) < 100) && (Math.abs(resultatJoueur - goal) <= (Math.abs(resultatAdversaire - goal)))) {
            points = 7;
        }
        joueur.setScore(joueur.getScore() + points);
        System.out.println(joueur.getNom() + " a gagné " + points + " points sur ce tour");
        System.out.println("Son score est maintenant de " + joueur.getScore() + "\n");
    }
}
