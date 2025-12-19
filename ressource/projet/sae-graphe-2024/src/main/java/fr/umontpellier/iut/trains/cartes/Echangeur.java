package fr.umontpellier.iut.trains.cartes;

import java.util.List;
import java.util.stream.Collectors;

import fr.umontpellier.iut.trains.Joueur;

public class Echangeur extends Carte {

    public Echangeur() {
        super("Échangeur", 3, 1, TypeCarte.ACTION);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        List<String> choixPossibles = joueur.getCartesEnJeu().stream().filter(c -> c.getTypes().contains(TypeCarte.TRAIN)).map(Carte::getNom).collect(Collectors.toList());
        String choix = joueur.choisir("Vous pouvez remettre une carte TRAIN sur votre deck", choixPossibles, null, true);

        if (!choix.isEmpty()) {
            Carte c = joueur.getCartesEnJeu().retirer(choix);
            joueur.getPioche().add(0, c);
        }
    }

    
}
