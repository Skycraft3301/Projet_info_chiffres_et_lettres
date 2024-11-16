import java.util.*;

public class ModeChiffres {
    private final static int[] CHIFFRES = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 25, 50, 75, 100};
    private final static List<Character> OPERATEURS = List.of('+', '-', '/', '*');
    private final static int LENGTH_SELECTED_NUMBER = 6;
    public static final String END = "end";

    public static void modeChiffres(Joueur joueurA, Joueur joueurB) {

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
            operandes = convertIntArrayIntoIntegerList(selectedNumbers);
            while (operandes.size() > 1) {
                Integer a = pickNumber(operandes);
                Integer b = pickNumber(operandes);

                char operateur = OPERATEURS.get(random.nextInt(OPERATEURS.size()));
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

        // saisie des calculs du joueur
        List<Integer> verification = convertIntArrayIntoIntegerList(selectedNumbers);

        System.out.println("Donnez vos étapes de calculs. Les calculs dont le résultat est égal à zéro ne sont pas admis. Indiquez la fin avec " + END);
        System.out.println("Pour commencer appuyer sur entrée.");

        while (!isEqualsToEndWord(Lire.S())) {
            int op1;
            do {
                System.out.println("donner une opérande1");
                op1 = Lire.i();
            } while (!verification.contains(op1));
            verification.remove((Integer) op1);

            char op;
            do {
                System.out.println("donner un opérateur");
                op = Lire.c();
            } while (!OPERATEURS.contains(op));

            int op2;
            do {
                System.out.println("donner une opérande2");
                op2 = Lire.i();
            } while (!verification.contains(op2));
            verification.remove((Integer) op2);

            int c = switch (op) {
                case '+' -> op1 + op2;
                case '/' -> op1 / op2;
                case '*' -> op1 * op2;
                default -> op1 - op2;
            };
            if (c == 0) { // pour eviter les operations avec des zeros
                verification.add(op1);
                verification.add(op2);
            } else {
                verification.add(c);
            }

            System.out.println("Si vous souhaitez continuer appuyez sur entrée, sinon tapez " + END);
        }

    }

    private static boolean isEqualsToEndWord(String saisie) {
        return Objects.equals(saisie, END);
    }

    private static ArrayList<Integer> convertIntArrayIntoIntegerList(int[] selectedNumbers) {
        return new ArrayList<>(Arrays.stream(selectedNumbers).boxed().toList());
    }

    private static Integer pickNumber(List<Integer> operandes) {
        int index = new Random().nextInt(operandes.size());
        Integer nb = operandes.get(index);
        operandes.remove(index);
        return nb;
    }
}
