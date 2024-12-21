public class SaisieLettres {
    public static String getReponseJoueur(Joueur joueur) {
        System.out.println(joueur.getNom() + " donnez votre résultat");
        return Lire.S();
    }
}
