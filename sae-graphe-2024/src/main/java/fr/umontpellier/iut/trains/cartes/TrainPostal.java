package fr.umontpellier.iut.trains.cartes;

import java.util.List;
import java.util.stream.Collectors;

import fr.umontpellier.iut.trains.Joueur;

public class TrainPostal extends CarteTrain {

    public TrainPostal() {
        super(4, "Train postal", 1, true);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        while (true) {
            List<String> nomsCartes = joueur.getMain().stream().map(Carte::getNom).collect(Collectors.toList());
            String choix = joueur.choisir("Choisissez une carte à défausser", nomsCartes, null, true);
            if (choix.isEmpty()) {
                break;
            } else {
                Carte c = joueur.getMain().getCarte(choix);
                joueur.getMain().remove(c);
                joueur.defausser(c);
                joueur.incrementerArgent(1);
            }
        }
    }
}
