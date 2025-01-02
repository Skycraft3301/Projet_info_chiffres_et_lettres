public class SaisieLettres {
    public static String getReponseJoueur(Joueur joueur) {
        System.out.println("\n" + joueur.getNom() + " donnez votre résultat");
        return Lire.S();
    }
}
