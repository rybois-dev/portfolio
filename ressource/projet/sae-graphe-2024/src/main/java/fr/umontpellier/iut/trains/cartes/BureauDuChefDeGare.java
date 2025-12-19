package fr.umontpellier.iut.trains.cartes;

import java.util.List;
import java.util.stream.Collectors;

import fr.umontpellier.iut.trains.Joueur;

public class BureauDuChefDeGare extends Carte {

    public BureauDuChefDeGare() {
        super("Bureau du chef de gare", 4, 0, TypeCarte.ACTION);
    }

    /**
     * Il est nécessaire d'autoriser le joueur à passer sans choisir de carte action
     * car sinon dans un scénario où il n'a que deux copies de "Bureau du chef de
     * gare" en main il est obligé de copier indéfiniment l'effet de la seconde
     * carte en main.
     */
    @Override
    public void jouer(Joueur joueur, Carte carteJouee) {
        super.jouer(joueur);
        
        List<String> nomsCartesAction = joueur.getMain().stream().filter(c -> c.hasType(TypeCarte.ACTION))
                .map(Carte::getNom).collect(Collectors.toList());
        String nomCarteAction = joueur.choisir("Choisissez une carte ACTION en main", nomsCartesAction, null, true);
        if (nomCarteAction.isEmpty()) {
            // ne copie aucune carte action
            return;
        }

        Carte c = joueur.getMain().getCarte(nomCarteAction);
        if (c != null) {
            c.jouer(joueur, carteJouee);
        }
    }

    @Override
    public void jouer(Joueur joueur) {
        jouer(joueur, this);
    }
}
