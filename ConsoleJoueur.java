import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.max;

public class ConsoleJoueur {

    private static int timerChiffres = 5;   // 40
    private static int timerLettres = 5;    // 30

    public static void main(String[] args) throws InterruptedException {
        String COM_TXT = "./com" + (args.length > 0 ? args[0] : "") + ".txt";
        //System.out.println("COM_TXT : "+COM_TXT);

        System.out.println("Joueur " + (args.length > 0 ? args[0] : "") + ", donnez votre nom :");
        Joueur joueur = new Joueur(Lire.S());

        long referenceTime = FileChecker.waitForUpdate(COM_TXT, 1, joueur.getNom());

        for (int i = 1; i <= 5; i++) {
            System.out.println("Manche " + i);
            System.out.println("[ Mode Chiffres ]");
            FileChecker.checkForUpdate(COM_TXT, 3, referenceTime);
            int[] selectedNumbers = ConverterUtils.toArray(Utils.getLine(3, COM_TXT));
            System.out.println("Voici les chiffres sélectionnés : " + Arrays.toString(selectedNumbers) + "\n");
            System.out.println("Le résultat à obtenir est " + Utils.getLine(4, COM_TXT) + "\n");
            timer(timerChiffres);
            Thread.sleep(timerChiffres * 1000L);
            System.out.println();
            System.out.println("Donnez vos étapes de calculs. Les calculs dont le résultat est égal à zéro ne sont pas admis. Indiquez la fin avec " + OperationUtils.END);
            System.out.println("Pour commencer appuyer sur entrée.");
            String resultatChiffre = String.valueOf(SaisieChiffre.computeUserOperations(selectedNumbers));
            referenceTime = FileChecker.waitForUpdate(COM_TXT, 5, resultatChiffre);
            // attendre la modification du score
            referenceTime = FileChecker.waitForUpdate(referenceTime, COM_TXT);
            System.out.println("Votre score est maintenant de " + Utils.getLine(2, COM_TXT) + " points" + "\n");


            System.out.println("[ Mode Lettres ]");
            //referenceTime = waitForUpdate(referenceTime, COM_TXT);
            if (Objects.equals(Utils.getLine(8, COM_TXT), joueur.getNom())) {
                System.out.println(joueur.getNom() + ", combien de voyelles voulez vous ?");
                int nbrVoyelles = Lire.entierCompris(LettresUtils.MIN_VOWEL_NUMBER, LettresUtils.MAX_VOWEL_NUMBER);
                Utils.writeLine(COM_TXT, 8, String.valueOf(nbrVoyelles));
            }
            FileChecker.checkForUpdate(COM_TXT, 3, referenceTime);
            System.out.println("Voici les lettres sélectionnées : " + Utils.getLine(6, COM_TXT) + "\n");
            timer(timerLettres);
            Thread.sleep(timerLettres * 1000L);
            String resultatLettre = SaisieLettres.getReponseJoueur(joueur);
            referenceTime = FileChecker.waitForUpdate(COM_TXT, 7, resultatLettre);
            System.out.println("Votre score est maintenant de " + Utils.getLine(2, COM_TXT) + " points" + "\n");
            //FileChecker.waitForUpdate(referenceTime, COM_TXT);
        }
        System.out.println("Fin du jeu ! Vous avez " + Utils.getLine(9, COM_TXT));
    }

    public static void timer(int duree) {
        // Visuel de la barre d'avancement
        String barreIndication = "[";
        for (int i = 0; i < ((duree / 2) - 2); i++) {
            barreIndication = barreIndication.concat(" ");
        }
        barreIndication = barreIndication.concat(duree + "s");
        for (int i = 0; i < ((duree / 2) - 3); i++) {
            barreIndication = barreIndication.concat(" ");
        }
        barreIndication = barreIndication.concat("]");
        System.out.println(barreIndication);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        Runnable task = new Runnable() {
            private int TempsRestant = duree;

            @Override
            public void run() {
                //System.out.println();
                if (TempsRestant > 0) {
                    //System.out.println("Il reste " + TempsRestant + " secondes.");
                    System.out.print("»");
                    TempsRestant--;
                } else {
                    //System.out.println("C'est fini !");
                    scheduler.shutdown();
                }
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }
}
