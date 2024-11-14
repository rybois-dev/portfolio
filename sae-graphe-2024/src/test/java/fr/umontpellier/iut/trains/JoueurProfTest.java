package fr.umontpellier.iut.trains;

import fr.umontpellier.iut.trains.cartes.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JoueurProfTest extends BaseTestClass {
    @Test
    void test_jouer_tour_passer() {
        setupJeu();
        initialisation();

        Carte omni1 = new TrainOmnibus();
        Carte omni2 = new TrainOmnibus();
        Carte omni3 = new TrainOmnibus();
        Carte omni4 = new TrainOmnibus();
        Carte omni5 = new TrainOmnibus();
        Carte gare1 = new Gare();
        Carte gare2 = new Gare();
        Carte gare3 = new Gare();
        Carte gare4 = new Gare();
        Carte gare5 = new Gare();
        Carte fondPioche = new Ferraille();

        addAll(main, omni1, omni2, omni3, omni4, omni5);
        addAll(pioche, gare1, gare2, gare3, gare4, gare5, fondPioche);

        jeu.setInput("");
        joueur.jouerTour();

        assertTrue(containsReferences(main, gare1, gare2, gare3, gare4, gare5));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, omni1, omni2, omni3, omni4, omni5));
        assertTrue(containsReferences(cartesEnJeu));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_jouer_tour_acheter_gare_pas_assez_d_argent() {
        setupJeu("Dépotoir");
        initialisation();

        Carte direct = new TrainDirect();
        Carte express = new TrainExpress();
        Carte fondPioche = new Ferraille();

        addAll(main, direct, express);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train express", "ACHAT:Gare");

        assertTrue(containsReferences(main, direct));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, express));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_jouer_tour_acheter_gare() {
        setupJeu();
        initialisation();

        Carte direct = new TrainDirect();
        Carte express = new TrainExpress();
        Carte fondPioche = new Ferraille();
        Carte gare = reserve.get("Gare").get(0);
        addAll(main, direct, express);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train direct", "Train express", "ACHAT:Gare");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, direct, express));
        assertTrue(containsReferences(cartesRecues, gare));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_ajouter_rail_plaine() {
        setupJeu();
        initialisation();
        tuiles.get(22).ajouterRail(joueur);

        Carte pose = new PoseDeRails();
        addAll(main, pose);

        jouerTourPartiel("Pose de rails", "TUILE:23");

        checkPlateau(null, List.of(22, 23), null);
    }

    @Test
    void test_ajouter_rail_fleuve_pas_assez_d_argent() {
        setupJeu();
        initialisation();
        tuiles.get(22).ajouterRail(joueur);

        Carte pose = new PoseDeRails();
        addAll(main, pose);

        jouerTourPartiel("Pose de rails", "TUILE:21");

        checkPlateau(null, List.of(22), null);
    }

    @Test
    void test_ajouter_rail_fleuve() {
        setupJeu();
        initialisation();
        tuiles.get(22).ajouterRail(joueur);

        Carte pose = new PoseDeRails();
        Carte omni = new TrainOmnibus();
        addAll(main, pose, omni);

        jouerTourPartiel("Pose de rails", "Train omnibus", "TUILE:21");

        checkPlateau(null, List.of(22, 21), null);
    }

    @Test
    void test_piocher() {
        setupJeu();
        initialisation();

        Carte omni = new TrainOmnibus();
        Carte gare = new Gare();
        Carte fondPioche = new Ferraille();

        addAll(pioche, omni, gare, fondPioche);

        Carte c = joueur.piocher();

        assertTrue(c == omni);
        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, gare, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_piocher_deux_cartes() {
        setupJeu();
        initialisation();

        Carte omni = new TrainOmnibus();
        Carte gare = new Gare();
        Carte fondPioche = new Ferraille();

        addAll(pioche, omni, gare, fondPioche);

        List<Carte> cartes = joueur.piocher(2);

        assertTrue(containsReferences(cartes, omni, gare));
        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_piocher_deux_cartes_avec_melange() {
        setupJeu();
        initialisation();

        Carte omni = new TrainOmnibus();
        Carte gare1 = new Gare();
        Carte gare2 = new Gare();

        addAll(pioche, omni);
        addAll(defausse, gare1, gare2);

        List<Carte> cartes = joueur.piocher(2);

        assertTrue(containsSame(cartes, omni, gare1));
        assertTrue(containsReferences(main));
        assertTrue(containsSame(pioche, gare2));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }
}
