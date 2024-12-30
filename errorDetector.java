import java.util.Objects;

public class errorDetector {

    private final static String comA = "./comA.txt";
    private final static String comB = "./comB.txt";

    public static void file (){
        try{
            nom(comA, "A");
            nom(comB, "B");
            //score();
            liste("chiffres", 3);
            //objectifChiffre();
            liste("lettres", 6);
        } catch (NameException | ListException e) {
            e.printStackTrace();
        }
    }

    private static void nom(String comJ, String J) throws NameException {
        if (Objects.equals(Utils.getLine(1, comJ), "")){
            throw new NameException("Le nom du joueur "+J+" est vide !");
        }
    }

    private static void score() throws Exception {

    }

    private static void liste(String mode, int line) throws ListException {
        if (!Objects.equals(Utils.getLine(line, comA), Utils.getLine(line, comB))){
            throw new ListException("Les listes de "+mode+" ne sont pas les mêmes dans les deux fichiers");
        }
    }

    private static void objectifChiffre() {
    }

}
