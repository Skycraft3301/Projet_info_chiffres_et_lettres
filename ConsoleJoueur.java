public class ConsoleJoueur {
    public static void main(String[] args) {
        System.out.println("Joueur " + args[0] + ", donnez votre nom :");
        Joueur joueur = new Joueur();
        long referenceTime = Utils.updateFile(Utils.COM_TXT, 1, joueur.getNom());
        long updateTime = Utils.getLastUpdate(Utils.COM_TXT);
        while (referenceTime == updateTime) {
            updateTime = Utils.getLastUpdate(Utils.COM_TXT);
        }
        System.out.println("[ Mode Chiffres ]");
        int[] selectedNumbers = ConverterUtils.toArray(Utils.getLine(3));
        System.out.println("Voici les chiffres sélectionnés : " + Utils.getLine(3) + "\n");
        System.out.println("Le résultat à obtenir est " + Utils.getLine(4) + "\n");
        Integer resultatChiffre = SaisieChiffre.computeUserOperations(selectedNumbers);
        referenceTime = Utils.updateFile(Utils.COM_TXT, 5, String.valueOf(resultatChiffre));
        while (referenceTime == updateTime) {
            updateTime = Utils.getLastUpdate(Utils.COM_TXT);
        }
    }
}
