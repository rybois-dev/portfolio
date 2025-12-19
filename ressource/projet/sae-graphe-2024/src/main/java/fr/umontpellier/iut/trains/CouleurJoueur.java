package fr.umontpellier.iut.trains;

public enum CouleurJoueur {
    JAUNE, ROUGE, VERT, BLEU;

    @Override
    public String toString() {
        return switch (this) {
            case JAUNE -> "jaune";
            case ROUGE -> "rouge";
            case VERT -> "vert";
            case BLEU -> "bleu";
        };
    }
}
