import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RechercheMotDansFichier {
    public static void main(String[] args) {
        // Le chemin du fichier et le mot à rechercher
        String fichier = "./dictionnaire.txt";
        String motRecherche = Lire.S();

        boolean trouve = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                if (ligne.equalsIgnoreCase(motRecherche)) {
                    trouve = true;
                    break; // On arrête dès qu'on trouve le mot
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (trouve) {
            System.out.println("Le mot \"" + motRecherche + "\" a été trouvé dans le fichier.");
        } else {
            System.out.println("Le mot \"" + motRecherche + "\" n'a pas été trouvé dans le fichier.");
        }
    }
}
