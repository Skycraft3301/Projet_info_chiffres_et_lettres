public class NotGoodLettersException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotGoodLettersException(){
        super("Votre réponse doit utiliser uniquement les lettres de la liste");
    }
}
