public class MessageErreur {
    private String message;
    private boolean erreur;

    public MessageErreur(String message, boolean erreur) {
        this.message = message;
        this.erreur = erreur;
    }

    public String getMessage() {
        return message;
    }

    public boolean getErreur() {
        return erreur;
    }

}
