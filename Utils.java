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

        File com = new File("./com.txt");

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






    public static void checkFile() {
        try {
            // Étape 2 : Vérifier si le fichier existe
            File com = new File("./com.txt");
            if (!com.exists()) {
                // Étape 3 : Créer le fichier
                if (com.createNewFile()) {
                    System.out.println("Fichier créé avec succès : " + "./com.txt");
                } else {
                    System.out.println("Impossible de créer le fichier.");
                }
            }
        } catch (IOException e) {
            // Étape 5 : Gestion des erreurs
            System.err.println("Une erreur est survenue : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}




/*// Étape 4 : Écrire dans le fichier
            FileWriter writer = new FileWriter(fichier);
            writer.write("Bonjour, ceci est un exemple de texte écrit dans un fichier !");
            writer.close(); // Toujours fermer pour libérer les ressources
            System.out.println("Écriture terminée avec succès !");
        } catch (IOException e) {
            // Étape 5 : Gestion des erreurs
            System.err.println("Une erreur est survenue : " + e.getMessage());
        }*/