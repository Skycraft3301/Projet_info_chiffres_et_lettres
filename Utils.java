import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utils {
    public static final String COM_TXT = "./com.txt";

    public static boolean isInDictionary(String reponseJoueur) {

        boolean testResult = false;

        File dico = new File("./dictionnaire.txt");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dico), "UTF-8"));
            String line = reader.readLine();

            while ((line != null) && (!line.equals(reponseJoueur))) {
                //System.out.println(line);
                line = reader.readLine();
            }

            if (Objects.equals(line, reponseJoueur)) {
                testResult = true;
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return testResult;
    }

    public static long getLastUpdate(String fileName) {
        File file = new File(fileName);
        return file.lastModified();
    }

    public static long updateFile(String fileName, int numeroDeLigne, String nouveauTexte) {
        File file = new File(fileName);
        writeLine(numeroDeLigne, nouveauTexte);
        return file.lastModified();
    }

    public static String getLine(int n) {
        String result = "";

        File com = new File("./com.txt");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(com), "UTF-8"));
            String line = "";

            for (int i = 0; i < n; i++) {
                //System.out.println(line);
                line = reader.readLine();
            }
            result = line;
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void checkFile() {
        try {
            // Vérifier si le fichier existe
            File com = new File("./com.txt");
            if (!com.exists()) {
                // Créer le fichier
                if (com.createNewFile()) {
                    System.out.println("Fichier créé avec succès : " + "./com.txt");
                } else {
                    System.out.println("Impossible de créer le fichier.");
                }
            }
        } catch (IOException e) {
            // Gestion des erreurs
            System.err.println("Une erreur est survenue : " + e.getMessage());
            throw new RuntimeException(e);
        }

        // Ajouter des sauts de ligne
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./com.txt"));
            for (int i = 0; i < 10; i++) {
                writer.newLine();
            }
            writer.close();
            System.out.println("Le fichier a été modifié avec succès !");
        } catch (IOException e) {
            System.err.println("Une erreur est survenue : " + e.getMessage());
        }
    }


    public static void writeLine(int ligneASupprimer, String nouveauTexte) {
        String nomFichier = "monFichier.txt";

        try {
            // Lire tout le fichier dans une liste
            List<String> lignes = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("./com.txt"));
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                lignes.add(ligne);
            }
            reader.close();

            // Modifier la ligne spécifique
            if (ligneASupprimer <= lignes.size()) {
                lignes.set(ligneASupprimer - 1, nouveauTexte); // Les indices des listes commencent à 0
            } else {
                System.out.println("La ligne demandée n'existe pas.");
            }

            // Étape 3 : Réécrire tout le fichier avec les modifications
            BufferedWriter writer = new BufferedWriter(new FileWriter("./com.txt"));
            for (String l : lignes) {
                writer.write(l);
                writer.newLine(); // Recrée les sauts de ligne
            }
            writer.close();
            System.out.println("Le fichier a été modifié avec succès !");
        } catch (IOException e) {
            System.err.println("Une erreur est survenue : " + e.getMessage());
        }
    }
}

