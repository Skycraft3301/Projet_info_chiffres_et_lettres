import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ModeChiffres {
    private final static int[] CHIFFRES = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 25, 50, 75, 100};
    private final static char[] OPERATEURS = new char[]{'+', '-', '/', '*'};
    private final static int LENGTH_SELECTED_NUMBER = 6;

    public static void modeChiffres() {

        int[] selectedNumbers = new int[LENGTH_SELECTED_NUMBER];

        System.out.println("[ Mode Chiffres ]");

        Random random = new Random();
        // génération des 6 chiffres
        for (int i = 0; i < LENGTH_SELECTED_NUMBER; i++) {
            selectedNumbers[i] = CHIFFRES[random.nextInt(CHIFFRES.length)];
        }

        System.out.println("Voici les chiffres sélectionnés :" + Arrays.toString(selectedNumbers));

        // calculs aléatoires pour obtenir un resultat
        // donc on sait qu'il est atteignable sans verification necessaire
        List<Integer> operandes;
        do {
            operandes = new ArrayList<>(Arrays.stream(selectedNumbers).boxed().toList());
            while (operandes.size() > 1) {
                Integer a = pickNumber(operandes);
                Integer b = pickNumber(operandes);

                char operateur = OPERATEURS[random.nextInt(OPERATEURS.length)];
                int c = switch (operateur) {
                    case '+' -> a + b;
                    case '/' -> a / b;
                    case '*' -> a * b;
                    default -> a - b;
                };
                if (c == 0) { // pour eviter les operations avec des zeros
                    operandes.add(a);
                    operandes.add(b);
                } else {
                    operandes.add(c);
                }
            }
        } while (operandes.getFirst() < 101 || operandes.getFirst() > 999);

        System.out.println("Le résultat à obtenir est" + operandes.getFirst());
    }

    private static Integer pickNumber(List<Integer> operandes) {
        int index = new Random().nextInt(operandes.size());
        Integer nb = operandes.get(index);
        operandes.remove(index);
        return nb;
    }
}
