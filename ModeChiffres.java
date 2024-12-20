import java.util.List;
import java.util.Random;

public class ModeChiffres {
    public static void modeChiffres(Joueur joueurA, Joueur joueurB) {

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
        Integer goal = operandes.getFirst();
        Utils.writeLine(3, ConverterUtils.intArrayToString(selectedNumbers));
        //TODO revoir numéro de ligne
        Utils.writeLine(4, String.valueOf(goal));
    }
}
