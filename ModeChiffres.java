import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.max;

public class ModeChiffres {

    private final static String comA = "./comA.txt";
    private final static String comB = "./comB.txt";
    
    public static void modeChiffres(Joueur joueurA, Joueur joueurB) throws InterruptedException {

        int[] selectedNumbers = new int[OperationUtils.LENGTH_SELECTED_NUMBER];

        Random random = new Random();
        // génération des 6 chiffres
        for (int i = 0; i < OperationUtils.LENGTH_SELECTED_NUMBER; i++) {
            selectedNumbers[i] = OperationUtils.CHIFFRES[random.nextInt(OperationUtils.CHIFFRES.length)];
        }
        // calculs aléatoires pour obtenir un resultat
        // donc on sait qu'il est atteignable sans verification necessaire
        List<Integer> operandes;
        do {
            operandes = OperationUtils.convertIntArrayIntoIntegerList(selectedNumbers);
            while (operandes.size() > 1) {
                Integer operande1 = OperationUtils.pickNumber(operandes);
                Integer operande2 = OperationUtils.pickNumber(operandes);

                char operateur = OperationUtils.OPERATEURS.get(random.nextInt(OperationUtils.OPERATEURS.size()));
                OperationUtils.compute(operandes, operande1, operateur, operande2);
            }
        } while (operandes.getFirst() < OperationUtils.LOWER_BOUND || operandes.getFirst() > OperationUtils.UPPER_BOUND);

        Utils.writeLines(Presentateur.comA, List.of(
                new FileLine(3, ConverterUtils.intArrayToString(selectedNumbers)),
                new FileLine(4, String.valueOf(operandes.getFirst()))
        ));
        Utils.writeLines(Presentateur.comB, List.of(
                new FileLine(3, ConverterUtils.intArrayToString(selectedNumbers)),
                new FileLine(4, String.valueOf(operandes.getFirst()))
        ));


        // Attendre les réponses des joueurs
        long referenceTime = max(Utils.getLastUpdate(comA), Utils.getLastUpdate(comB));

        while (Objects.equals(Utils.getLine(5, comA), "") || Objects.equals(Utils.getLine(5, comB), "")){
            Thread.sleep(500);
        }
        int charA = Integer.parseInt(Utils.getLine(5, comA));
        int charB = Integer.parseInt(Utils.getLine(5, comB));
        //int charA = (int) (Utils.getLine(5, comA)).charAt(0) -48;
        //int charB = (int) (Utils.getLine(5, comB)).charAt(0) -48;
        /*while ((OperationUtils.LOWER_BOUND >= charA
                || charA >= OperationUtils.UPPER_BOUND)
                && (OperationUtils.LOWER_BOUND >= charB
                || charB >= OperationUtils.UPPER_BOUND)){
            referenceTime = ConsoleJoueur.waitForUpdate(referenceTime, comA, comB);
            charA = (int) (Utils.getLine(5, comA)).charAt(0) -48;
            charB = (int) (Utils.getLine(5, comB)).charAt(0) -48;
        }*/

        System.out.println("charA : "+charA+" | charB : "+charB);

        ScoreUtils.scoreChiffre(joueurA, Presentateur.comA, charA, charB, Integer.parseInt(Utils.getLine(4, comA)));
        ScoreUtils.scoreChiffre(joueurB, Presentateur.comB, charB, charA, Integer.parseInt(Utils.getLine(4, comB)));
    }
}
