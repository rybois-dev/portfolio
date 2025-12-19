package fr.umontpellier.iut.trains.cartes;

import java.util.HashSet;
import java.util.Set;

import fr.umontpellier.iut.trains.Joueur;

public class AtelierDeMaintenance extends Carte {

    public AtelierDeMaintenance() {
        super("Atelier de maintenance", 5, 0, TypeCarte.ACTION);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        Set<String> nomsCartesTrain = new HashSet<>();
        for (Carte c : joueur.getMain()) {
            if (c.hasType(TypeCarte.TRAIN)) {
                nomsCartesTrain.add(c.getNom());
            }
        }
        String nomCarte = joueur.choisir("Dévoilez une carte TRAIN", nomsCartesTrain, null,
                false);
        if (nomCarte.isEmpty()) {
            // aucune carte TRAIN en main
            return;
        }
        Carte carteDevoilee = joueur.getMain().getCarte(nomCarte);
        joueur.log(String.format("... dévoile %s", carteDevoilee.toLog()));
        joueur.recevoir(nomCarte);
    }
}
