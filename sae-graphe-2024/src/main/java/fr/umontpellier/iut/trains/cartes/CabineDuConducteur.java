package fr.umontpellier.iut.trains.cartes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.umontpellier.iut.trains.Joueur;

public class CabineDuConducteur extends Carte {

    public CabineDuConducteur() {
        super("Cabine du conducteur", 2, 0, TypeCarte.ACTION);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);
        
        List<Carte> cartesDefaussees = new ArrayList<>();
        while (true) {
            List<String> nomsCartesEnMain = joueur.getMain().stream().map(Carte::getNom).collect(Collectors.toList());
            String nomCarte = joueur.choisir("Choisissez les cartes à défausser", nomsCartesEnMain, null, true);
            if (nomCarte.isEmpty()) {
                break;
            }
            Carte carte = joueur.getMain().retirer(nomCarte);
            cartesDefaussees.add(carte);
        }
        joueur.defausser(cartesDefaussees);
        joueur.piocherEnMain(cartesDefaussees.size());
    }
}
