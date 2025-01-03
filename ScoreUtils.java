import java.util.List;
import java.util.Objects;

public class ScoreUtils {
    public static void scoreChiffre(Joueur joueur, String file, Integer resultatJoueur, Integer resultatAdversaire, Integer goal) {
        System.out.println("\nLe score de " + joueur.getNom() + " était de " + joueur.getScore());
        int points = 0;
        if (Objects.equals(resultatJoueur, goal)) {
            points = 10;
        } else if ((Math.abs(resultatJoueur - goal) < 100) && (Math.abs(resultatJoueur - goal) <= (Math.abs(resultatAdversaire - goal)))) {
            points = 7;
        }
        joueur.setScore(joueur.getScore() + points);

        writeLineScore(joueur, file, points);
        System.out.println(joueur.getNom() + " a gagné " + points + " points sur ce tour");
        System.out.println("Son score est maintenant de " + joueur.getScore() + "\n");
    }

    public static void scoreLettres(Joueur joueur, String file, String reponseJoueur, String reponseAdversaire, boolean erreurMotJoueur, boolean erreurMotAdversaire, MessageErreur messageErreur) {
        int points = 0;
        if ((reponseJoueur.length() >= reponseAdversaire.length()) && (!erreurMotJoueur) && (!erreurMotAdversaire)) {
            points = reponseJoueur.length();
        }
        if ((!erreurMotJoueur) && erreurMotAdversaire) {
            points = Math.max(reponseJoueur.length(), reponseAdversaire.length());
        }
        joueur.setScore(joueur.getScore() + points);

        writeLineScore(joueur, file, points, messageErreur);
        System.out.println(joueur.getNom() + " a gagné " + points + " points sur ce tour");
        System.out.println("Son score est maintenant de " + joueur.getScore() + "\n");
    }

    public static void writeLineScore(Joueur joueur, String file, int points) {
        String Score = String.valueOf(joueur.getScore());
        String Points = String.valueOf(points);
        if ((joueur.getScore() < 10) && (points < 10)) {
            Utils.writeLines(file, List.of(
                    new FileLine(2, Score + " "),
                    new FileLine(10, Points + " ")
            ));
        } else if ((joueur.getScore() >= 10) && (points < 10)) {
            Utils.writeLines(file, List.of(
                    new FileLine(2, Score),
                    new FileLine(10, Points + " ")
            ));
        } else if ((joueur.getScore() < 10) && (points >= 10)) {
            Utils.writeLines(file, List.of(
                    new FileLine(2, Score + " "),
                    new FileLine(10, Points)
            ));
        } else if ((joueur.getScore() >= 10) && (points >= 10)) {
            Utils.writeLines(file, List.of(
                    new FileLine(2, Score),
                    new FileLine(10, Points)
            ));
        }
    }

    public static void writeLineScore(Joueur joueur, String file, int points, MessageErreur messageErreur) {
        String Score = String.valueOf(joueur.getScore());
        String Points = String.valueOf(points);
        if ((joueur.getScore() < 10) && (points < 10)) {
            Utils.writeLines(file, List.of(
                    new FileLine(2, Score + " "),
                    new FileLine(11, messageErreur.getMessage()),
                    new FileLine(10, Points + " ")
            ));
        } else if ((joueur.getScore() >= 10) && (points < 10)) {
            Utils.writeLines(file, List.of(
                    new FileLine(2, Score),
                    new FileLine(11, messageErreur.getMessage()),
                    new FileLine(10, Points + " ")
            ));
        } else if ((joueur.getScore() < 10) && (points >= 10)) {
            Utils.writeLines(file, List.of(
                    new FileLine(2, Score + " "),
                    new FileLine(11, messageErreur.getMessage()),
                    new FileLine(10, Points)
            ));
        } else if ((joueur.getScore() >= 10) && (points >= 10)) {
            Utils.writeLines(file, List.of(
                    new FileLine(2, Score),
                    new FileLine(11, messageErreur.getMessage()),
                    new FileLine(10, Points)
            ));
        }
    }
}
