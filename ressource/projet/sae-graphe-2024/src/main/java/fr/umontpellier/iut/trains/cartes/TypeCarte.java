package fr.umontpellier.iut.trains.cartes;

/**
 * Type énuméré des différents types de cartes possibles
 * <p>
 * Une carte peut éventuellement avoir plusieurs types, par exemple
 * Action/Attaque ou Action/Réaction
 */
public enum TypeCarte {
    TRAIN, RAIL, GARE, ACTION, VICTOIRE, FERRAILLE;

    public String toString() {
        return switch (this) {
            case TRAIN -> "train";
            case RAIL -> "rail";
            case GARE -> "gare";
            case ACTION -> "action";
            case VICTOIRE -> "victoire";
            case FERRAILLE -> "ferraille";
        };
    }
}