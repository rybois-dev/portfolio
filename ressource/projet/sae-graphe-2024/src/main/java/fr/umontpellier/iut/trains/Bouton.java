package fr.umontpellier.iut.trains;

public record Bouton(
        String label,
        String valeur) {

    public Bouton(String valeur) {
        this(valeur, valeur);
    }

    public String toString() {
        if (label.equals(valeur)) {
            return valeur;
        } else {
            return label + " (" + valeur + ")";
        }
    }
}