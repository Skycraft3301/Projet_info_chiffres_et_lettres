import java.util.List;
import java.util.Random;

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
        StringBuilder operations;
        do {
            operandes = OperationUtils.convertIntArrayIntoIntegerList(selectedNumbers);
            operations = new StringBuilder();
            while (operandes.size() > 1) {
                Integer operande1 = OperationUtils.pickNumber(operandes);
                Integer operande2 = OperationUtils.pickNumber(operandes);

                char operateur = OperationUtils.OPERATEURS.get(random.nextInt(OperationUtils.OPERATEURS.size()));
                operations.append(OperationUtils.compute(operandes, operande1, operateur, operande2));
            }
        } while (operandes.getFirst() < OperationUtils.LOWER_BOUND || operandes.getFirst() > OperationUtils.UPPER_BOUND);

        long referenceTimeA = Utils.updateFile(comA, List.of(
                new FileLine(3, ConverterUtils.intArrayToString(selectedNumbers)),
                new FileLine(4, String.valueOf(operandes.getFirst())),
                new FileLine(12, operations.toString())
        ));
        long referenceTimeB = Utils.updateFile(comB, List.of(
                new FileLine(3, ConverterUtils.intArrayToString(selectedNumbers)),
                new FileLine(4, String.valueOf(operandes.getFirst())),
                new FileLine(12, operations.toString())
        ));


        // Attendre les réponses des joueurs
        FileChecker.checkForUpdate(comA, comB, 5, referenceTimeA, referenceTimeB);
        Thread.sleep(500);

        int charA = Integer.parseInt(Utils.getLine(5, comA));
        int charB = Integer.parseInt(Utils.getLine(5, comB));

        System.out.println("charA : " + charA + " | charB : " + charB);

        ScoreUtils.scoreChiffre(joueurA, comA, charA, charB, Integer.parseInt(Utils.getLine(4, comA)));
        ScoreUtils.scoreChiffre(joueurB, comB, charB, charA, Integer.parseInt(Utils.getLine(4, comB)));
    }
}
