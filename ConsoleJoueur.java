import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConsoleJoueur {
    public static void main(String[] args) {
        String COM_TXT = "./com" + (args.length > 0 ? args[0] : "") + ".txt";

        System.out.println("Joueur " + (args.length > 0 ? args[0] : "") + ", donnez votre nom :");
        Joueur joueur = new Joueur(Lire.S());

        FileChecker.waitForUpdate(COM_TXT, 1, joueur.getNom());

        for (int i = 1; i <= 5; i++) {
            System.out.println("Manche " + i);
            System.out.println("[ Mode Chiffres ]");
            int[] selectedNumbers = ConverterUtils.toArray(Utils.getLine(3, COM_TXT));
            System.out.println("Voici les chiffres sélectionnés : " + Arrays.toString(selectedNumbers) + "\n");
            System.out.println("Le résultat à obtenir est " + Utils.getLine(4, COM_TXT) + "\n");
            timer(40);
            String resultatChiffre = String.valueOf(SaisieChiffre.computeUserOperations(selectedNumbers));
            long referenceTime = FileChecker.waitForUpdate(COM_TXT, 5, resultatChiffre);
            // attendre la modification du score
            System.out.println("Votre score est maintenant de " + Utils.getLine(2, COM_TXT) + " points" + "\n");


            System.out.println("[ Mode Lettres ]");
            referenceTime = FileChecker.waitForUpdate(referenceTime, COM_TXT);
            if (Objects.equals(Utils.getLine(8, COM_TXT), joueur.getNom())) {
                System.out.println(joueur.getNom() + ", combien de voyelles voulez vous ?");
                int nbrVoyelles = Lire.entierCompris(LettresUtils.MIN_VOWEL_NUMBER, LettresUtils.MAX_VOWEL_NUMBER);
                Utils.writeLine(COM_TXT, 8, String.valueOf(nbrVoyelles));
            }
            FileChecker.checkForUpdate(COM_TXT, 6, referenceTime);
            System.out.println("Voici les lettres sélectionnées : " + Utils.getLine(6, COM_TXT) + "\n");
            timer(30);
            String resultatLettre = SaisieLettres.getReponseJoueur(joueur);
            referenceTime = FileChecker.waitForUpdate(COM_TXT, 7, resultatLettre);
            System.out.println("Votre score est maintenant de " + Utils.getLine(2, COM_TXT) + " points" + "\n");
            FileChecker.waitForUpdate(referenceTime, COM_TXT);
        }
        System.out.println("Fin du jeu ! Vous avez " + Utils.getLine(9, COM_TXT));
    }

    public static void timer(int duree) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        Runnable task = new Runnable() {
            private int TempsRestant = duree;

            @Override
            public void run() {
                if (TempsRestant > 0) {
                    System.out.println("Il reste " + TempsRestant + " secondes.");
                    TempsRestant--;
                } else {
                    System.out.println("C'est fini !");
                    scheduler.shutdown();
                }
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }
}
