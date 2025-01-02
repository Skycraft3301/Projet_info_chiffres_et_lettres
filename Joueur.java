public class Joueur {
    private final String nom;
    private int score = 0;
    private boolean isVoyellePlayer = false;

    public Joueur(String nom) {
        this.nom = nom;
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

    public boolean isVoyellePlayer() {
        return isVoyellePlayer;
    }

    public void setVoyellePlayer(boolean voyellePlayer) {
        isVoyellePlayer = voyellePlayer;
    }
}