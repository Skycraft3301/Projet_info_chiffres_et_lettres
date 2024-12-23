public class ConverterUtils {

    public static final String ARRAY_DELIMITER = ",";

    public static String intArrayToString(int[] a) {
        if (a == null)
            return "null";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(a[i]);
            if (i == iMax)
                return b.toString();
            b.append(ARRAY_DELIMITER);
        }
    }

    public static int[] toArray(String s) {
        String[] stringArray = s.split(ARRAY_DELIMITER);
        int[] intArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }
        return intArray;
    }
}
