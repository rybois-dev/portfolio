package fr.umontpellier.iut.trains.cartes;

import java.util.Collection;
import java.util.List;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Joueur;

public class CentreDeControle extends Carte {

    public CentreDeControle() {
        super("Centre de contrôle", 3, 0, TypeCarte.ACTION);
    }

    @Override
    public void jouer(Joueur joueur) {
        super.jouer(joueur);

        joueur.piocherEnMain();
        Collection<String> nomsCartes = joueur.getJeu().getListeNomsCartes();
        List<Bouton> boutons = nomsCartes.stream().map(Bouton::new).toList();
        String nomCarte = joueur.choisir("Nommez une carte", null, boutons, false);
        joueur.log(String.format("... annonce %s", nomCarte));
        Carte carteDevoilee = joueur.piocher();
        if (carteDevoilee != null) {
            joueur.log(String.format("... révèle %s", carteDevoilee.toLog()));
            if (carteDevoilee.getNom().equals(nomCarte)) {
                joueur.ajouterMain(carteDevoilee);
            } else {
                joueur.mettreSurPioche(carteDevoilee);
            }
        }
    }
}
