import java.util.*;

public class OperationUtils {
    public final static int[] CHIFFRES = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 25, 50, 75, 100};
    public final static List<Character> OPERATEURS = List.of('+', '-', '/', '*');
    public final static int LENGTH_SELECTED_NUMBER = 6;
    public static final String END = "end";
    public static final int UPPER_BOUND = 999;
    public static final int LOWER_BOUND = 101;

    public static char getOperateur() {
        char operateur;
        do {
            System.out.println("Donner un opérateur");
            operateur = Lire.c();
        } while (!OPERATEURS.contains(operateur));
        return operateur;
    }

    public static int getOperande(String message, List<Integer> numberList) {
        int operande;
        do {
            System.out.println(message);
            operande = Lire.i();
        } while (!numberList.contains(operande));
        numberList.remove((Integer) operande);
        return operande;
    }

    public static String compute(List<Integer> numberList, int operande1, char operateur, int operande2) {
        int result = switch (operateur) {
            case '+' -> operande1 + operande2;
            case '/' -> operande1 / operande2;
            case '*' -> operande1 * operande2;
            default -> operande1 - operande2;
        };
        if (result == 0) { // pour eviter les operations avec des zeros
            numberList.add(operande1);
            numberList.add(operande2);
        } else {
            numberList.add(result);
            return String.valueOf(operande1) + String.valueOf(operateur) + String.valueOf(operande2) + "=" + result + ", ";
        }
        return "";
    }

    public static boolean isEqualsToEndWord(String saisie) {
        return Objects.equals(saisie, END);
    }

    public static ArrayList<Integer> convertIntArrayIntoIntegerList(int[] selectedNumbers) {
        return new ArrayList<>(Arrays.stream(selectedNumbers).boxed().toList());
    }

    public static Integer pickNumber(List<Integer> operandes) {
        int index = new Random().nextInt(operandes.size());
        Integer number = operandes.get(index);
        operandes.remove(index);
        return number;
    }
}
