import java.sql.Timestamp;
import java.util.Arrays;

public class ConsoleJoueur {
    public static void main(String[] args) {
        System.out.println("Joueur " + (args.length > 0 ? args[0] : "") + ", donnez votre nom :");
        Joueur joueur = new Joueur();
        long referenceTime = waitForUpdate(Utils.COM_TXT, 1, joueur.getNom());

        for (int i = 1; i <= 5; i++) {
            System.out.println("Manche" + i);
            System.out.println("[ Mode Chiffres ]");
            int[] selectedNumbers = ConverterUtils.toArray(Utils.getLine(3));
            System.out.println("Voici les chiffres sélectionnés : " + Arrays.toString(selectedNumbers) + "\n");
            waitForUpdate(referenceTime, Utils.COM_TXT);
            System.out.println("Le résultat à obtenir est " + Utils.getLine(4) + "\n");
            String resultatChiffre = String.valueOf(SaisieChiffre.computeUserOperations(selectedNumbers));
            referenceTime = waitForUpdate(Utils.COM_TXT, 5, resultatChiffre);
            System.out.println("Votre score est maintenant de " + Utils.getLine(2) + " points" + "\n");
            waitForUpdate(referenceTime, Utils.COM_TXT);

            System.out.println("[ Mode Lettres ]");
            System.out.println("Voici les lettres sélectionnées : " + Utils.getLine(6) + "\n");
            String resultatLettre = SaisieLettres.getReponseJoueur(joueur);
            referenceTime = waitForUpdate(Utils.COM_TXT, 7, resultatLettre);
            System.out.println("Votre score est maintenant de " + Utils.getLine(2) + " points" + "\n");
            waitForUpdate(referenceTime, Utils.COM_TXT);
        }
        System.out.println("Fin du jeu ! Vous avez " + Utils.getLine(9));
    }


    private static long waitForUpdate(String file, int numeroDeLigne, String modifiedText) {
        long referenceTime = Utils.updateFile(file, numeroDeLigne, modifiedText);
        return waitForUpdate(referenceTime, file);
    }

    private static long waitForUpdate(long referenceTime, String file) {
        long updateTime = Utils.getLastUpdate(file);
        while (new Timestamp(referenceTime).equals(new Timestamp(updateTime))) {
            updateTime = Utils.getLastUpdate(file);
        }
        return updateTime;
    }

}
