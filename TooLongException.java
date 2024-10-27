public class TooLongException extends Exception {

    private static final long serialVersionUID = 1L;

    public TooLongException(){
      super("Votre réponse doit avoir maximum 10 caractères");
    }
}
