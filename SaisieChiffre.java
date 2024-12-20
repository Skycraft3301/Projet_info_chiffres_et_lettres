import java.util.Arrays;
import java.util.List;

public class SaisieChiffre {
    public static Integer computeUserOperations(int[] selectedNumbers) {
        List<Integer> verification = OperationUtils.convertIntArrayIntoIntegerList(selectedNumbers);

        System.out.println("Donnez vos étapes de calculs. Les calculs dont le résultat est égal à zéro ne sont pas admis. Indiquez la fin avec " + OperationUtils.END);
        System.out.println("Pour commencer appuyer sur entrée.");

        while (!OperationUtils.isEqualsToEndWord(Lire.S())) {
            int operande1 = OperationUtils.getOperande("Donner un premier opérande", verification);

            char operateur = OperationUtils.getOperateur();

            int operande2 = OperationUtils.getOperande("Donner un deuxième opérande", verification);

            OperationUtils.compute(verification, operande1, operateur, operande2);

            System.out.println("Le résultat de ce calcul est " + verification.getLast());
            System.out.println("Voici les nouveaux chiffres disponibles : " + Arrays.toString(verification.toArray()));
            System.out.println("Si vous souhaitez continuer appuyez sur entrée, sinon tapez " + OperationUtils.END);
        }

        System.out.println("Le résultat final est " + verification.getLast());
        return verification.getLast();
    }

}
