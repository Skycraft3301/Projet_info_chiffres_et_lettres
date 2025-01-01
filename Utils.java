import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utils {
    private final static String comA = "./comA.txt";
    private final static String comB = "./comB.txt";

    public static long getLastUpdate(String fileName) {
        File file = new File(fileName);
        //System.out.print("fileName : " + fileName);
        return file.lastModified();
    }

    public static long updateFile(String fileName, int numeroDeLigne, String nouveauTexte) {
        File file = new File(fileName);
        writeLine(fileName, numeroDeLigne, nouveauTexte);
        return file.lastModified();
    }

    public static long updateFile(String fileName, List<FileLine> fileLines) {
        File file = new File(fileName);
        writeLines(fileName, fileLines);
        return file.lastModified();
    }

    public static String getLine(int n, String fileName) {
        String result = "";

        File com = new File(fileName);

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


    public static void checkFile(String fileName) {
        if (Objects.equals(fileName, "all")) {
            checkFile(comA);
            checkFile(comB);
        } else {
            try {
                // Vérifier si le fichier existe
                File com = new File(fileName);
                if (!com.exists()) {
                    // Créer le fichier
                    if (com.createNewFile()) {
                        System.out.println("Fichier créé avec succès : " + fileName);
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
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                for (int i = 0; i < 10; i++) {
                    writer.newLine();
                }
                writer.close();
                System.out.println("Le fichier a été modifié avec succès !");
            } catch (IOException e) {
                System.err.println("Une erreur est survenue : " + e.getMessage());
            }
        }
    }


    public static void writeLine(String fileName, int ligneASupprimer, String nouveauTexte) {
        if (Objects.equals(fileName, "all")) {
            writeLine(comA, ligneASupprimer, nouveauTexte);
            writeLine(comB, ligneASupprimer, nouveauTexte);
        } else {
            try {
                // Lire tout le fichier dans une liste
                List<String> lignes = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
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

                // Réécrire tout le fichier avec les modifications
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
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

    public static void writeLines(String fileName, List<FileLine> fileLines) {
        try {
            // Lire tout le fichier dans une liste
            List<String> lignes = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                lignes.add(ligne);
            }
            reader.close();

            for (FileLine fileLine : fileLines) {

                // Modifier la ligne spécifique
                if (fileLine.numeroLigne() <= lignes.size()) {
                    lignes.set(fileLine.numeroLigne() - 1, fileLine.valeur());
                } else {
                    System.out.println("La ligne demandée n'existe pas.");
                }
            }

            // Réécrire tout le fichier avec les modifications
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
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

