public class Joueur {
    private final String nom;
    private int score = 0;

    public Joueur() {
        this.nom = Lire.S();
    }

    public String getNom() {
        return nom;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
