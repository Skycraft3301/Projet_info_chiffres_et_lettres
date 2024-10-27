public class NotInDictionnaryException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotInDictionnaryException(){
        super("Votre réponse n'est pas dans le dictionnaire");
    }
}
