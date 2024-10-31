import java.util.Arrays;
import java.util.Random;

public class ModeChiffres {
    private final static int[] CHIFFRES = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 25, 50, 75, 100};
    private final static int LENGTH_SELECTED_NUMBER = 6;

    public static void modeChiffres() {

        int[] selectedNumbers = new int[LENGTH_SELECTED_NUMBER];

        System.out.println("[ Mode Chiffres ]");

        Random random = new Random();

        for (int i = 0; i < LENGTH_SELECTED_NUMBER; i++) {
            selectedNumbers[i] = CHIFFRES[random.nextInt(CHIFFRES.length)];
        }

        System.out.println("Voici les chiffres sélectionnés :");
        System.out.println(Arrays.toString(selectedNumbers));
    }
}
