import java.util.Arrays;
import java.util.List;

public class SaisieChiffre {
    public static Integer computeUserOperations(int[] selectedNumbers) {
        List<Integer> verification = OperationUtils.convertIntArrayIntoIntegerList(selectedNumbers);

        while (!OperationUtils.isEqualsToEndWord(Lire.S())) {
            int operande1 = OperationUtils.getOperande("Donner un premier opérande", verification);

            char operateur = OperationUtils.getOperateur();

            int operande2 = OperationUtils.getOperande("Donner un deuxième opérande", verification);

            OperationUtils.compute(verification, operande1, operateur, operande2);

            System.out.println("Le résultat de ce calcul est " + verification.getLast());
            System.out.println("Voici les nouveaux chiffres disponibles : " + Arrays.toString(verification.toArray()));
            System.out.println("Le résultat à obtenir est " + Utils.getLine(4, Presentateur.comA) + "\n");
            System.out.println("Si vous souhaitez continuer appuyez sur entrée, sinon tapez " + OperationUtils.END);
        }

        Integer finalNumber = selectedNumbers.length == verification.size() ? 0 : verification.getLast();
        System.out.println("Le résultat final est " + finalNumber);
        return finalNumber;
    }

}
