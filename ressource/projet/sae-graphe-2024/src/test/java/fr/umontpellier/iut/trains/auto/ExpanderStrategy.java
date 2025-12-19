package fr.umontpellier.iut.trains.auto;

import fr.umontpellier.iut.trains.BaseTestClass;
import fr.umontpellier.iut.trains.Joueur;
import fr.umontpellier.iut.trains.cartes.Carte;
import fr.umontpellier.iut.trains.cartes.ListeDeCartes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExpanderStrategy extends Strategy {
    private Joueur joueur;

    public ExpanderStrategy(AutoJeu jeu) {
        super(jeu);
    }

    public List<String> shuffledTuiles() {
        List<String> tuiles = new ArrayList<>();
        for (int i = 0; i < 76; i++) {
            tuiles.add("TUILE:" + i);
        }
        Collections.shuffle(tuiles);
        return tuiles;
    }

    @Override
    public void update() {
        AutoJeu jeu = getJeu();
        boolean isNouveauJoueur = joueur != jeu.getJoueurCourant();
        if (isNouveauJoueur) {
            joueur = jeu.getJoueurCourant();
        }
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

        // jouer les cartes GARE en main
        if (main.count("Gare") > 0) {
            jeu.addInput("Gare");
            jeu.addInput(shuffledTuiles());
            return;
        }

        // jouer les cartes POSE DE RAILS en main
        int nbPoseDeRails = main.count("Pose de rails");
        if (nbPoseDeRails > 0) {
            for (int i = 0; i < nbPoseDeRails; i++) {
                jeu.addInput("Pose de rails");
            }
            jeu.addInput(shuffledTuiles());
            return;
        }

        // passer
        jeu.setInput("");
    }
}
