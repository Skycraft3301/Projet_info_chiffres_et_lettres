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
        String COM_TXT = "./com"+(args.length > 0 ? args[0] : "")+".txt";
        //System.out.println("COM_TXT : "+COM_TXT);

        System.out.println("Joueur " + (args.length > 0 ? args[0] : "") + ", donnez votre nom :");
        Joueur joueur = new Joueur(Lire.S());

        waitForUpdate(COM_TXT, 1, joueur.getNom());

        for (int i = 1; i <= 5; i++) {
            System.out.println("Manche " + i);
            System.out.println("[ Mode Chiffres ]");
            long referenceTime = Utils.getLastUpdate(COM_TXT);
            while (Objects.equals(Utils.getLine(3, COM_TXT), "")){
                referenceTime = waitForUpdate(referenceTime, COM_TXT);
            }
            int[] selectedNumbers = ConverterUtils.toArray(Utils.getLine(3, COM_TXT));
            System.out.println("Voici les chiffres sélectionnés : " + Arrays.toString(selectedNumbers) + "\n");
            System.out.println("Le résultat à obtenir est " + Utils.getLine(4, COM_TXT) + "\n");
            System.out.println("Donnez vos étapes de calculs. Les calculs dont le résultat est égal à zéro ne sont pas admis. Indiquez la fin avec " + OperationUtils.END);
            timer(timerChiffres);
            Thread.sleep(timerChiffres* 1000L);
            System.out.println();
            System.out.println("Pour commencer appuyer sur entrée.");
            String resultatChiffre = String.valueOf(SaisieChiffre.computeUserOperations(selectedNumbers));
            Utils.writeLine(COM_TXT, 5, resultatChiffre);
            referenceTime = Utils.getLastUpdate(COM_TXT);
            //long referenceTime = waitForUpdate(COM_TXT, 5, resultatChiffre);
            // attendre la modification du score
            referenceTime = waitForUpdate(referenceTime, COM_TXT);
            System.out.println("Votre score est maintenant de " + Utils.getLine(2, COM_TXT) + " points" + "\n");


            System.out.println("[ Mode Lettres ]");
            //referenceTime = waitForUpdate(referenceTime, COM_TXT);
            if (Objects.equals(Utils.getLine(8, COM_TXT), joueur.getNom())) {
                System.out.println(joueur.getNom() + ", combien de voyelles voulez vous ?");
                int nbrVoyelles = Lire.entierCompris(LettresUtils.MIN_VOWEL_NUMBER, LettresUtils.MAX_VOWEL_NUMBER);
                Utils.writeLine(COM_TXT, 8, String.valueOf(nbrVoyelles));
            }
            /*while (LettresUtils.MIN_VOWEL_NUMBER >= Integer.parseInt(Utils.getLine(8, COM_TXT)) || Integer.parseInt(Utils.getLine(8, COM_TXT)) >= LettresUtils.MAX_VOWEL_NUMBER){
                referenceTime = waitForUpdate(referenceTime, COM_TXT);
            }*/
            while (Objects.equals(Utils.getLine(6, COM_TXT), "")) {
                referenceTime = waitForUpdate(referenceTime, COM_TXT);
            }
            System.out.println("Voici les lettres sélectionnées : " + Utils.getLine(6, COM_TXT) + "\n");
            timer(timerLettres);
            Thread.sleep(timerLettres *1000L);
            String resultatLettre = SaisieLettres.getReponseJoueur(joueur);
            referenceTime = waitForUpdate(COM_TXT, 7, resultatLettre);
            System.out.println("Votre score est maintenant de " + Utils.getLine(2, COM_TXT) + " points" + "\n");
            //waitForUpdate(referenceTime, COM_TXT);
        }
        System.out.println("Fin du jeu ! Vous avez " + Utils.getLine(9, COM_TXT));
    }

    public static void timer(int duree) {
        String barreIndication = "[";
        for (int i=0 ; i<((duree/2)-2) ; i++){
            barreIndication = barreIndication.concat(" ");
        }
        barreIndication = barreIndication.concat(duree+"s");
        for (int i=0 ; i<((duree/2)-3) ; i++){
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

    public static long waitForUpdate(String file, int numeroDeLigne, String modifiedText) {
        if (Objects.equals(file, "all")){
            long referenceTime = max(Utils.updateFile(Presentateur.comA, numeroDeLigne, modifiedText), Utils.updateFile(Presentateur.comB, numeroDeLigne, modifiedText));
            return max(waitForUpdate(referenceTime, Presentateur.comA), waitForUpdate(referenceTime, Presentateur.comB));
        }else {
            long referenceTime = Utils.updateFile(file, numeroDeLigne, modifiedText);
            return waitForUpdate(referenceTime, file);
        }
    }

    public static long waitForUpdate(long referenceTime, String file) {
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

    public static long waitForUpdate(long referenceTime, String fileA, String fileB) {
        long updateTimeA = Utils.getLastUpdate(fileA);
        long updateTimeB = Utils.getLastUpdate(fileB);
        while (referenceTime == updateTimeA && referenceTime == updateTimeB) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException _) {

            }
            updateTimeA = Utils.getLastUpdate(fileA);
            updateTimeB = Utils.getLastUpdate(fileB);
        }
        return max(updateTimeA, updateTimeB);
    }

}
