package fr.umontpellier.iut.trains.cartes;

import java.util.List;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

public class Remorquage extends Carte {

    public Remorquage() {
        super("Remorquage", 3, 0, TypeCarte.ACTION);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        List<Bouton> boutons = joueur.getDefausse()
                .stream()
                .filter(c -> c.getTypes().contains(TypeCarte.TRAIN))
                .map(Carte::getNom)
                .map(Bouton::new)
                .toList();
        String choix = joueur.choisir("Choisissez une carte TRAIN de votre défausse", null, boutons, false);

        if (!choix.isEmpty()) {
            Carte c = joueur.getDefausse().retirer(choix);
            joueur.ajouterMain(c);
        }
    }
}
