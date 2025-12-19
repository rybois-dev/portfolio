package fr.umontpellier.iut.trains.cartes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Liste de cartes
 */
public class ListeDeCartes extends ArrayList<Carte> {
    /**
     * Constructeur vide
     */
    public ListeDeCartes() {
        super();
    }

    /**
     * Constructeur à partir d'une liste de cartes
     */
    public ListeDeCartes(List<Carte> l) {
        super(l);
    }

    /**
     * Mélange la liste
     */
    public void melanger() {
        Collections.shuffle(this);
    }

    /**
     * Retire une carte de la liste dont le nom est égal à l'argument passé
     *
     * @param nomCarte le nom de la carte à retirer
     * @return la carte retirée si elle a été trouvée, {@code null} sinon
     */
    public Carte retirer(String nomCarte) {
        for (Carte c : this)
            if (c.getNom().equals(nomCarte)) {
                remove(c);
                return c;
            }
        return null;
    }

    /**
     * Renvoie une carte de la liste dont le nom est égal à l'argument
     *
     * @param nomCarte le nom de la carte à chercher
     * @return une carte de la liste ayant le nom cherché si elle existe,
     *         {@code null} sinon
     */
    public Carte getCarte(String nomCarte) {
        for (Carte c : this)
            if (c.getNom().equals(nomCarte))
                return c;
        return null;
    }

    /**
     * Renvoie le nombre de cartes dans la liste ayant le nom passé en argument
     *
     * @param nomCarte le nom des cartes à compter
     * @return un entier indiquant le nombre de cartes ayant le nom recherché
     */
    public int count(String nomCarte) {
        int total = 0;
        for (Carte c : this)
            if (c.getNom().equals(nomCarte))
                total += 1;
        return total;
    }

    /**
     * Représentation de la liste sous forme d'une chaîne de caractères
     *
     * Cette fonction renvoie une chaîne de caractères constituée des noms des
     * cartes de la liste séparées par ", ".
     * Par exemple, si la liste contient une carte Village et une carte Copper,
     * la fonction renvoie la chaîne "Village, Copper"
     */
    @Override
    public String toString() {
        return this.stream()
                .map(Carte::toString)
                .collect(Collectors.joining(", "));
    }

    public Object dataMap() {
        return this.stream().map(Carte::getNom).toList();
    }
}
