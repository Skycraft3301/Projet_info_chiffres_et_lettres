public enum Voyelle {

    A(158), E(304), I(155),
    O(111), U(130), Y(6);

    private int occ;

    // Constructeur :
    Voyelle(int occ) {
        this.occ = occ;
    }

    public int getOcc() {
        return occ;
    }
}