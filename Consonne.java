public enum Consonne {

    B(19), C(67), D(76), F(22), G(18), H(15),
    J(11), K(1), L(113), M(61), N(147), P(62), Q(28), R(135),
    S(164), T(150), V(34), W(2), X(8), Z(3);

    private int occ;

    // Constructeur :
    Consonne(int occ) {
        this.occ = occ;
    }

    public int getOcc() {
        return occ;
    }
}