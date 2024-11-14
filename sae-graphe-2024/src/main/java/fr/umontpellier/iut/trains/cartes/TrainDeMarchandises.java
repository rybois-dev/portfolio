package fr.umontpellier.iut.trains.cartes;

import java.util.List;
import java.util.stream.Collectors;

import fr.umontpellier.iut.trains.Joueur;

public class TrainDeMarchandises extends CarteTrain {

    public TrainDeMarchandises() {
        super(4, "Train de marchandises", 1, true);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        while (true) {
            List<String> cartesFerraille = joueur.getMain().stream().filter(c -> c.hasType(TypeCarte.FERRAILLE))
                    .map(Carte::getNom).collect(Collectors.toList());
            String choix = joueur.choisir("Choisissez une carte Ferraille à remettre dans la réserve", cartesFerraille, null, true);
            if (choix.isEmpty()) {
                break;
            } else {
                Carte c = joueur.getMain().getCarte(choix);
                joueur.getMain().remove(c);
                joueur.remettreCarteDansLaReserve(c);
                joueur.incrementerArgent(1);
            }
        }
    }
}
