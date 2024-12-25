import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConsoleJoueur {
    public static void main(String[] args) {
        String COM_TXT = "./com"+(args.length > 0 ? args[0] : "")+".txt";
        System.out.println("COM_TXT : "+COM_TXT);

        System.out.println("Joueur " + (args.length > 0 ? args[0] : "") + ", donnez votre nom :");
        Joueur joueur = new Joueur();

        waitForUpdate(COM_TXT, 1, joueur.getNom());

        for (int i = 1; i <= 5; i++) {
            System.out.println("Manche " + i);
            System.out.println("[ Mode Chiffres ]");
            int[] selectedNumbers = ConverterUtils.toArray(Utils.getLine(3));
            System.out.println("Voici les chiffres sélectionnés : " + Arrays.toString(selectedNumbers) + "\n");
            System.out.println("Le résultat à obtenir est " + Utils.getLine(4) + "\n");
            timer(40);
            String resultatChiffre = String.valueOf(SaisieChiffre.computeUserOperations(selectedNumbers));
            long referenceTime = waitForUpdate(COM_TXT, 5, resultatChiffre);
            System.out.println("Votre score est maintenant de " + Utils.getLine(2) + " points" + "\n");


            System.out.println("[ Mode Lettres ]");
            waitForUpdate(referenceTime, COM_TXT);
            System.out.println("Voici les lettres sélectionnées : " + Utils.getLine(6) + "\n");
            timer(30);
            String resultatLettre = SaisieLettres.getReponseJoueur(joueur);
            referenceTime = waitForUpdate(COM_TXT, 7, resultatLettre);
            System.out.println("Votre score est maintenant de " + Utils.getLine(2) + " points" + "\n");
            waitForUpdate(referenceTime, COM_TXT);
        }
        System.out.println("Fin du jeu ! Vous avez " + Utils.getLine(9));
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

    private static long waitForUpdate(String file, int numeroDeLigne, String modifiedText) {
        long referenceTime = Utils.updateFile(file, numeroDeLigne, modifiedText);
        return waitForUpdate(referenceTime, file);
    }

    private static long waitForUpdate(long referenceTime, String file) {
        long updateTime = Utils.getLastUpdate(file);
        while (referenceTime == updateTime) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException _) {

            }

            updateTime = Utils.getLastUpdate(file);
        }
        return updateTime;
    }

}
