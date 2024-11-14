package fr.umontpellier.iut.trains.auto;

import fr.umontpellier.iut.trains.BaseTestClass;
import fr.umontpellier.iut.trains.Joueur;
import fr.umontpellier.iut.trains.cartes.Carte;
import fr.umontpellier.iut.trains.cartes.ListeDeCartes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BigMoneyStrategy extends Strategy {
    private Joueur joueur;

    public BigMoneyStrategy(AutoJeu jeu) {
        super(jeu);
    }

    @Override
    public void update() {
        AutoJeu jeu = getJeu();
        boolean isNouveauJoueur = joueur != jeu.getJoueurCourant();
        if (isNouveauJoueur) {
            joueur = jeu.getJoueurCourant();
        }

        Map<String, ListeDeCartes> reserve = jeu.getReserve();
        ListeDeCartes main = (ListeDeCartes) BaseTestClass.getAttribute(joueur, "main");

        // recycler si au moins 2 Ferraille en main
        if (isNouveauJoueur && main.count("Ferraille") >= 2) {
            jeu.setInput("Ferraille");
            return;
        }

        // jouer les cartes TRAIN en main
        List<String> noms = Arrays.asList("Train omnibus", "Train express", "Train direct");
        List<String> cartesAJouer = main.stream()
                .filter(carte -> noms.contains(carte.getNom()))
                .map(Carte::getNom)
                .toList();
        if (!cartesAJouer.isEmpty()) {
            jeu.setInput(cartesAJouer);
            return;
        }

        // acheter des cartes
        int argent = (int) BaseTestClass.getAttribute(joueur, "argent");
        if (argent >= 8 && reserve.get("Gratte-ciel").size() > 0) {
            jeu.setInput("ACHAT:Gratte-ciel");
            return;
        }
        if (argent >= 6 && reserve.get("Train direct").size() > 0) {
            jeu.setInput("ACHAT:Train direct");
            return;
        }
        if (argent >= 5 && reserve.get("Immeuble").size() > 0) {
            jeu.setInput("ACHAT:Immeuble");
            return;
        }
        if (argent >= 3 && reserve.get("Train express").size() > 0) {
            jeu.setInput("ACHAT:Train express");
            return;
        }
        if (argent >= 3 && reserve.get("Appartement").size() > 0) {
            jeu.setInput("ACHAT:Appartement");
            return;
        }

        // passer
        jeu.setInput("");
    }
}
