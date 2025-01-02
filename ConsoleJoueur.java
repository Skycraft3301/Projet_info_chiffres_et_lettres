import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConsoleJoueur {

    private static final int timerChiffres = 4;   // 40
    private static final int timerLettres = 3;    // 30

    public static void main(String[] args) throws InterruptedException {
        String COM_TXT = "./com" + (args.length > 0 ? args[0] : "") + ".txt";

        System.out.println("Joueur " + (args.length > 0 ? args[0] : "") + ", donnez votre nom :");
        Joueur joueur = new Joueur(Lire.S());

        long referenceTime = Utils.updateFile(COM_TXT, 1, joueur.getNom());

        for (int i = 1; i <= 5; i++) {
            System.out.println("\n" + "==================================================" + "\n" + "░░░░░░░░░░░░░░░░░░░░ Manche " + i + " ░░░░░░░░░░░░░░░░░░░░" + "\n" + "==================================================" + "\n");
            System.out.println("\n" + "[ Mode Chiffres ]" + "\n");
            FileChecker.checkForUpdate(COM_TXT, List.of(3, 4), referenceTime);
            int[] selectedNumbers = ConverterUtils.toArray(Utils.getLine(3, COM_TXT));
            System.out.println("Voici les chiffres sélectionnés : " + Arrays.toString(selectedNumbers) + "\n");
            System.out.println("Le résultat à obtenir est " + Utils.getLine(4, COM_TXT) + "\n");
            timer(timerChiffres);
            Thread.sleep(timerChiffres * 1000L);

            Utils.writeLine(COM_TXT, 10, "");
            //Utils.writeLine(COM_TXT, 11, "");

            System.out.println();
            System.out.println("Donnez vos étapes de calculs. Les calculs dont le résultat est égal à zéro ne sont pas admis. Indiquez la fin avec " + OperationUtils.END);
            System.out.println("Pour commencer appuyer sur entrée.");
            String resultatChiffre = String.valueOf(SaisieChiffre.computeUserOperations(selectedNumbers));
            referenceTime = Utils.updateFile(COM_TXT, 5, resultatChiffre);
            FileChecker.checkForUpdate(COM_TXT, 10, referenceTime);
            /*while (Objects.equals(Utils.getLine(10, COM_TXT), "")) {
                System.out.println("l 36");
                FileChecker.waitForUpdate(referenceTime, COM_TXT);
            }*/
            System.out.println("Une solution était :");
            System.out.println(Utils.getLine(12, COM_TXT));
            afficherScore(COM_TXT);


            System.out.println("\n" + "[ Mode Lettres ]" + "\n");
            if (Objects.equals(Utils.getLine(8, COM_TXT), Boolean.TRUE.toString())) {
                System.out.println(joueur.getNom() + ", combien de voyelles voulez vous ?");
                int nbrVoyelles = Lire.entierCompris(LettresUtils.MIN_VOWEL_NUMBER, LettresUtils.MAX_VOWEL_NUMBER);
                referenceTime = Utils.updateFile(COM_TXT, 8, String.valueOf(nbrVoyelles));
            } else {
                referenceTime = Utils.getLastUpdate(COM_TXT);
            }
            FileChecker.checkForUpdate(COM_TXT, 6, referenceTime);
            System.out.println("Voici les lettres sélectionnées : " + Utils.getLine(6, COM_TXT) + "\n");
            timer(timerLettres);
            Thread.sleep(timerLettres * 1000L);

            Utils.writeLine(COM_TXT, 10, "");
            Utils.writeLine(COM_TXT, 11, "");

            String resultatLettre = SaisieLettres.getReponseJoueur(joueur);
            referenceTime = Utils.updateFile(COM_TXT, 7, resultatLettre);
            FileChecker.checkForUpdate(COM_TXT, 11, referenceTime);
            /*while (Objects.equals(Utils.getLine(10, COM_TXT), "")) {
                System.out.println("l 58");
                FileChecker.waitForUpdate(referenceTime, COM_TXT);
            }*/
            System.out.println(Utils.getLine(11, COM_TXT));
            afficherScore(COM_TXT);
            referenceTime = Utils.getLastUpdate(COM_TXT) - 10;
        }
        System.out.println("Fin du jeu ! Vous êtes " + Utils.getLine(9, COM_TXT));
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

    public static void afficherScore(String file) {
        String points = Utils.getLine(10, file);
        String score = Utils.getLine(2, file);

        System.out.println("╔═════════════════════════════════════════╗");
        System.out.println("║ Vous avez gagné +" + points + " points              ║");
        System.out.println("║ Votre score est maintenant de " + score + " points ║");
        System.out.println("╚═════════════════════════════════════════╝" + "\n");
    }
}
