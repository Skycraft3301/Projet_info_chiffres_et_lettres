import java.util.*;

public class ModeChiffres {
    private final static int[] CHIFFRES = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 25, 50, 75, 100};
    private final static List<Character> OPERATEURS = List.of('+', '-', '/', '*');
    private final static int LENGTH_SELECTED_NUMBER = 6;
    private static final String END = "end";
    private static final int UPPER_BOUND = 999;
    private static final int LOWER_BOUND = 101;

    public static void modeChiffres(Joueur joueurA, Joueur joueurB) {

        int[] selectedNumbers = new int[LENGTH_SELECTED_NUMBER];

        System.out.println("[ Mode Chiffres ]");

        Random random = new Random();
        // génération des 6 chiffres
        for (int i = 0; i < LENGTH_SELECTED_NUMBER; i++) {
            selectedNumbers[i] = CHIFFRES[random.nextInt(CHIFFRES.length)];
        }

        // calculs aléatoires pour obtenir un resultat
        // donc on sait qu'il est atteignable sans verification necessaire
        List<Integer> operandes;
        do {
            operandes = convertIntArrayIntoIntegerList(selectedNumbers);
            while (operandes.size() > 1) {
                Integer operande1 = pickNumber(operandes);
                Integer operande2 = pickNumber(operandes);

                char operateur = OPERATEURS.get(random.nextInt(OPERATEURS.size()));
                compute(operandes, operande1, operateur, operande2);
            }
        } while (operandes.getFirst() < LOWER_BOUND || operandes.getFirst() > UPPER_BOUND);
        Integer goal = operandes.getFirst();

        // saisie des calculs du joueur
        Integer resultatJoueurA = computeUserOperations(selectedNumbers, joueurA, goal);
        Integer resultatJoueurB = computeUserOperations(selectedNumbers, joueurB, goal);

        // Score
        scoreChiffre(joueurA, resultatJoueurA, resultatJoueurB, goal);
        scoreChiffre(joueurB, resultatJoueurB, resultatJoueurA, goal);
    }

    private static Integer computeUserOperations(int[] selectedNumbers, Joueur joueur, Integer goal) {
        List<Integer> verification = convertIntArrayIntoIntegerList(selectedNumbers);

        System.out.println("\nAu tour de " + joueur.getNom() + "\n");

        System.out.println("Voici les chiffres sélectionnés : " + Arrays.toString(selectedNumbers));
        System.out.println("Le résultat à obtenir est " + goal + "\n");

        System.out.println("Donnez vos étapes de calculs. Les calculs dont le résultat est égal à zéro ne sont pas admis. Indiquez la fin avec " + END);
        System.out.println("Pour commencer appuyer sur entrée.");

        while (!isEqualsToEndWord(Lire.S())) {
            int operande1 = getOperande("Donner un premier opérande", verification);

            char operateur = getOperateur();

            int operande2 = getOperande("Donner un deuxième opérande", verification);

            compute(verification, operande1, operateur, operande2);

            System.out.println("Le résultat de ce calcul est " + verification.getLast());
            System.out.println("Voici les nouveaux chiffres disponibles : " + Arrays.toString(verification.toArray()));
            System.out.println("Si vous souhaitez continuer appuyez sur entrée, sinon tapez " + END);
        }

        System.out.println("Le résultat final est " + verification.getLast());
        return verification.getLast();
    }

    private static char getOperateur() {
        char operateur;
        do {
            System.out.println("Donner un opérateur");
            operateur = Lire.c();
        } while (!OPERATEURS.contains(operateur));
        return operateur;
    }

    private static int getOperande(String message, List<Integer> numberList) {
        int operande;
        do {
            System.out.println(message);
            operande = Lire.i();
        } while (!numberList.contains(operande));
        numberList.remove((Integer) operande);
        return operande;
    }

    private static void compute(List<Integer> numberList, int operande1, char operateur, int operande2) {
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
        Integer number = operandes.get(index);
        operandes.remove(index);
        return number;
    }

    private static void scoreChiffre(Joueur joueur, Integer resultatJoueur, Integer resultatAdversaire, Integer goal) {
        System.out.println("\nLe score de " + joueur.getNom() + " était de " + joueur.getScore());
        int points = 0;
        if (Objects.equals(resultatJoueur, goal)) {
            points = 10;
        } else if ((Math.abs(resultatJoueur - goal) < 100) && (Math.abs(resultatJoueur - goal) <= (Math.abs(resultatAdversaire - goal)))) {
            points = 7;
        }
        joueur.setScore(joueur.getScore() + points);
        System.out.println(joueur.getNom() + " a gagné " + points + " points sur ce tour");
        System.out.println("Son score est maintenant de " + joueur.getScore() + "\n");
    }
}
