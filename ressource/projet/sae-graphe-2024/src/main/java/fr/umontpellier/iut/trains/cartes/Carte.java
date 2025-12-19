package fr.umontpellier.iut.trains.cartes;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import fr.umontpellier.iut.trains.Joueur;

public abstract class Carte implements Comparable<Carte> {
    private final int cout;
    private final String nom;
    private final int valeur;
    private final List<TypeCarte> types;

    public Carte(String nom, int cout, int valeur, TypeCarte... types) {
        this.cout = cout;
        this.nom = nom;
        this.valeur = valeur;
        this.types = Arrays.asList(types);
    }

    public int getCout() {
        return cout;
    }

    public String getNom() {
        return nom;
    }

    public int getValeur() {
        return valeur;
    }

    public Collection<TypeCarte> getTypes() {
        return types;
    }

    public boolean hasType(TypeCarte type) {
        return types.contains(type);
    }

    @Override
    public String toString() {
        return nom;
    }

    public String toLog() {
        return String.format("<span class=\"carte %s\">%s</span>", types.get(0), nom);
    }

    /**
     * Toutes les cartes ont une méthode jouer, mais elle ne fait rien par défaut.
     * Par exemple les cartes VICTOIRE n'ont pas d'effet quand elles sont jouées.
     * Les cartes FERRAILLE n'ont un effet que si elles sont jouées au début du tour
     * pour être remise dans la réserve.
     * 
     * @param joueur
     */
    public void jouer(Joueur joueur) {
    }

    public void jouer(Joueur joueur, Carte carteJouee) {
        jouer(joueur);
    }

    public void onAchat(Joueur joueur) {
    }

    public int getNbPointsVictoire() {
        return 0;
    }

    public boolean peutEtreJouee(Joueur joueur) {
        return true;
    }

    public boolean peutEtreAchetee(Joueur joueur) {
        return joueur.getArgent() >= cout;
    }

    @Override
    public int compareTo(Carte o) {
        if (this.cout == o.cout) {
            return this.nom.compareTo(o.nom);
        }
        return this.cout - o.cout;
    };
}