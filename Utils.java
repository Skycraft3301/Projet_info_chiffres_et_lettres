import java.io.*;
import java.util.Objects;

public class Utils {
    public static boolean isInDictionary(String reponseJoueur) {

        boolean testResult = false;

        File dico = new File("./dictionnaire.txt");

        // Tester si fichier existe
        /*if (!dico.exists()){

        }*/

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dico), "UTF-8"));
            String line = reader.readLine();

            while ((line != null) && (!line.equals(reponseJoueur))){
                //System.out.println(line);
                line = reader.readLine();
            }

            if (Objects.equals(line, reponseJoueur)) {
                testResult = true;
            }

            reader.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

    return(testResult);
    }

    public static String getLine(int n) {
        String result = "";

        File com = new File("./dictionnaire.txt");

        // Tester si fichier existe
        /*if (!dico.exists()){

        }*/

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(com), "UTF-8"));
            String line = reader.readLine();

            for (int i=0 ; i<n ; i++) {
                System.out.println(line);
                line = reader.readLine();
            }
            result = line;
            reader.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

        return(result);
    }
}
