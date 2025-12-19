package fr.umontpellier.iut.trains;

import fr.umontpellier.iut.trains.cartes.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartesProfTest extends BaseTestClass {

    @Test
    void test_aiguillage() {
        setupJeu("Aiguillage");
        initialisation();

        Carte c = new Aiguillage();
        Carte fondPioche = new Ferraille();
        Carte gare1 = new Gare();
        Carte gare2 = new Gare();

        addAll(main, c);
        addAll(pioche, gare1, gare2, fondPioche);

        jouerTourPartiel("Aiguillage");

        assertTrue(containsReferences(main, gare1, gare2));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_atelier_de_maintenance() {
        setupJeu("Atelier de maintenance");
        initialisation();

        Carte c = new AtelierDeMaintenance();
        Carte fondPioche = new Ferraille();
        Carte expr1 = new TrainExpress();
        Carte expr2 = reserve.get("Train express").get(0);

        addAll(main, c, expr1);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Atelier de maintenance", "Train express");

        assertTrue(containsReferences(main, expr1));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues, expr2));
        assertFalse(containsReference(reserve.get("Train express"), expr2));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_bureau_du_chef_de_gare() {
        setupJeu("Bureau du chef de gare");
        initialisation();

        Carte c = new BureauDuChefDeGare();
        Carte fondPioche = new Ferraille();
        Carte aig = new Aiguillage();
        Carte gare1 = new Gare();
        Carte gare2 = new Gare();

        addAll(main, c, aig);
        addAll(pioche, gare1, gare2, fondPioche);

        jouerTourPartiel("Bureau du chef de gare", "Aiguillage");

        assertTrue(containsReferences(main, aig, gare1, gare2));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_cabine_du_conducteur() {
        setupJeu("Cabine du conducteur");
        initialisation();

        Carte c = new CabineDuConducteur();
        Carte fondPioche = new Ferraille();
        Carte omni1 = new TrainOmnibus();
        Carte omni2 = new TrainOmnibus();
        Carte omni3 = new TrainOmnibus();
        Carte gare1 = new Gare();
        Carte gare2 = new Gare();

        addAll(main, c, omni1, omni2, omni3);
        addAll(pioche, gare1, gare2, fondPioche);

        jouerTourPartiel("Cabine du conducteur", "Train omnibus", "Train omnibus", "");

        assertTrue(containsReferences(main, omni3, gare1, gare2));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, omni1, omni2));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_centre_de_controle_perdu() {
        setupJeu("Centre de contrôle");
        initialisation();

        Carte c = new CentreDeControle();
        Carte fondPioche = new Ferraille();
        Carte gare = new Gare();
        Carte omni = new TrainOmnibus();

        addAll(main, c);
        addAll(pioche, gare, omni, fondPioche);

        jouerTourPartiel("Centre de contrôle", "Gare");

        assertTrue(containsReferences(main, gare));
        assertTrue(containsReferencesInOrder(pioche, omni, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_centre_de_controle_gagne() {
        setupJeu("Centre de contrôle");
        initialisation();

        Carte c = new CentreDeControle();
        Carte fondPioche = new Ferraille();
        Carte gare = new Gare();
        Carte omni = new TrainOmnibus();

        addAll(main, c);
        addAll(pioche, gare, omni, fondPioche);

        jouerTourPartiel("Centre de contrôle", "Train omnibus");

        assertTrue(containsReferences(main, gare, omni));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_centre_de_renseignements_prend_carte() {
        setupJeu("Centre de renseignements");
        initialisation();

        Carte c = new CentreDeRenseignements();
        Carte fondPioche = new Ferraille();
        Carte imm = new Immeuble();
        Carte omni = new TrainOmnibus();
        Carte gare = new Gare();
        Carte app = new Appartement();

        addAll(main, c);
        addAll(pioche, imm, omni, gare, app, fondPioche);

        jouerTourPartiel("Centre de renseignements", "Gare", "Appartement", "Train omnibus", "Immeuble");

        assertTrue(containsReferences(main, gare));
        assertTrue(containsReferencesInOrder(pioche, imm, omni, app, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_centre_de_renseignements_passe() {
        setupJeu("Centre de renseignements");
        initialisation();

        Carte c = new CentreDeRenseignements();
        Carte fondPioche = new Ferraille();
        Carte imm = new Immeuble();
        Carte omni = new TrainOmnibus();
        Carte gare = new Gare();
        Carte app = new Appartement();

        addAll(main, c);
        addAll(pioche, imm, omni, gare, app, fondPioche);

        jouerTourPartiel("Centre de renseignements", "", "Gare", "Appartement", "Train omnibus", "Immeuble");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, imm, omni, app, gare, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_cooperation_simple() {
        setupJeu("Coopération");
        initialisation();

        Carte c = new Cooperation();
        Carte fondPioche = new Ferraille();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Coopération");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues, f));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }

    @Test
    void test_decharge() {
        setupJeu("Décharge");
        initialisation();

        Carte c = new Decharge();
        Carte fondPioche = new Ferraille();
        Carte f1 = new Ferraille();
        Carte f2 = new Ferraille();
        Carte omni = new TrainOmnibus();

        addAll(main, c, f1, f2, omni);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Décharge");

        assertTrue(containsReferences(main, omni));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));

        List<Carte> pileFerraille = reserve.get("Ferraille");
        assertTrue(containsReference(pileFerraille, f1));
        assertTrue(containsReference(pileFerraille, f2));
    }

    @Test
    void test_depot() {
        setupJeu("Dépôt");
        initialisation();

        Carte c = new Depot();
        Carte fondPioche = new Ferraille();
        Carte imm = new Immeuble();
        Carte omni = new TrainOmnibus();
        Carte expr = new TrainExpress();

        addAll(main, c, imm);
        addAll(pioche, omni, expr, fondPioche);

        jouerTourPartiel("Dépôt", "Train express", "Immeuble");

        assertTrue(containsReferences(main, omni));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, expr, imm));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_depotoir_simple() {
        setupJeu("Dépotoir");
        initialisation();

        Carte c = new Depotoir();
        Carte fondPioche = new Ferraille();

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Dépotoir");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_echangeur() {
        setupJeu("Échangeur");
        initialisation();

        Carte c = new Echangeur();
        Carte fondPioche = new Ferraille();
        Carte omni = new TrainOmnibus();
        Carte expr = new TrainExpress();

        addAll(main, c, omni, expr);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train omnibus", "Train express", "Échangeur", "Train express");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, expr, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, omni));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(4, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_ferronnerie_simple() {
        setupJeu("Ferronnerie");
        initialisation();

        Carte c = new Ferronnerie();
        Carte fondPioche = new Ferraille();

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Ferronnerie");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_feu_de_signalisation_defausse() {
        setupJeu("Feu de signalisation");
        initialisation();

        Carte c = new FeuDeSignalisation();
        Carte fondPioche = new Ferraille();
        Carte gare = new Gare();
        Carte imm = new Immeuble();

        addAll(main, c);
        addAll(pioche, gare, imm, fondPioche);

        jouerTourPartiel("Feu de signalisation", "oui");

        assertTrue(containsReferences(main, gare));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, imm));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_feu_de_signalisation_replace() {
        setupJeu("Feu de signalisation");
        initialisation();

        Carte c = new FeuDeSignalisation();
        Carte fondPioche = new Ferraille();
        Carte gare = new Gare();
        Carte imm = new Immeuble();

        addAll(main, c);
        addAll(pioche, gare, imm, fondPioche);

        jouerTourPartiel("Feu de signalisation", "non");

        assertTrue(containsReferences(main, gare));
        assertTrue(containsReferencesInOrder(pioche, imm, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_gare() {
        setupJeu("Gare");
        initialisation();

        Carte c = new Gare();
        Carte fondPioche = new Ferraille();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Gare", "TUILE:12");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues, f));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertEquals(1, tuiles.get(12).getNbGares());
    }

    @Test
    void test_horaires_estivaux_ecarte() {
        setupJeu("Horaires estivaux");
        initialisation();

        Carte c = new HorairesEstivaux();
        Carte fondPioche = new Ferraille();

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Horaires estivaux", "oui");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(3, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertTrue(containsReferences(cartesEcartees, c));
    }

    @Test
    void test_horaires_estivaux_n_ecarte_pas() {
        setupJeu("Horaires estivaux");
        initialisation();

        Carte c = new HorairesEstivaux();
        Carte fondPioche = new Ferraille();

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Horaires estivaux", "non");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertTrue(containsReferences(cartesEcartees));
    }

    @Test
    void test_horaires_temporaires() {
        setupJeu("Horaires temporaires");
        initialisation();

        Carte c = new HorairesTemporaires();
        Carte fondPioche = new Ferraille();
        Carte omni = new TrainOmnibus();
        Carte expr = new TrainExpress();
        Carte gare = new Gare();
        Carte imm = new Immeuble();

        addAll(main, c);
        addAll(pioche, omni, gare, imm, expr, fondPioche);

        jouerTourPartiel("Horaires temporaires");

        assertTrue(containsReferences(main, omni, expr));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, gare, imm));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_parc_d_attractions() {
        setupJeu("Parc d'attractions");
        initialisation();

        Carte c = new ParcDAttractions();
        Carte fondPioche = new Ferraille();
        Carte omni = new TrainOmnibus();
        Carte expr = new TrainExpress();

        addAll(main, c, omni, expr);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train omnibus", "Train express", "Parc d'attractions", "Train express");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, omni, expr));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(6, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_passage_en_gare() {
        setupJeu("Passage en gare");
        initialisation();

        Carte c = new PassageEnGare();
        Carte fondPioche = new Ferraille();
        Carte gare = new Gare();

        addAll(main, c);
        addAll(pioche, gare, fondPioche);

        jouerTourPartiel("Passage en gare");

        assertTrue(containsReferences(main, gare));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_personnel_de_gare_piocher() {
        setupJeu("Personnel de gare");
        initialisation();

        Carte c = new PersonnelDeGare();
        Carte fondPioche = new Ferraille();
        Carte ferraille = new Ferraille();
        Carte gare = new Gare();

        addAll(main, c, ferraille);
        addAll(pioche, gare, fondPioche);

        jouerTourPartiel("Personnel de gare", "piocher");

        assertTrue(containsReferences(main, ferraille, gare));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_personnel_de_gare_argent() {
        setupJeu("Personnel de gare");
        initialisation();

        Carte c = new PersonnelDeGare();
        Carte fondPioche = new Ferraille();
        Carte ferraille = new Ferraille();
        Carte gare = new Gare();

        addAll(main, c, ferraille);
        addAll(pioche, gare, fondPioche);

        jouerTourPartiel("Personnel de gare", "argent");

        assertTrue(containsReferences(main, ferraille));
        assertTrue(containsReferencesInOrder(pioche, gare, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_personnel_de_gare_ferraille() {
        setupJeu("Personnel de gare");
        initialisation();

        Carte c = new PersonnelDeGare();
        Carte fondPioche = new Ferraille();
        Carte ferraille = new Ferraille();
        Carte gare = new Gare();

        addAll(main, c, ferraille);
        addAll(pioche, gare, fondPioche);

        jouerTourPartiel("Personnel de gare", "ferraille");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, gare, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertTrue(containsReference(reserve.get("Ferraille"), ferraille));
    }

    @Test
    void test_pont_en_acier_simple() {
        setupJeu("Pont en acier");
        initialisation();

        Carte c = new PontEnAcier();
        Carte fondPioche = new Ferraille();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Pont en acier");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues, f));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }

    @Test
    void test_pose_de_rails() {
        setupJeu("Pose de rails");
        initialisation();

        Carte c = new PoseDeRails();
        Carte fondPioche = new Ferraille();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Pose de rails");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues, f));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }

    @Test
    void test_remorquage() {
        setupJeu("Remorquage");
        initialisation();

        Carte c = new Remorquage();
        Carte fondPioche = new Ferraille();
        Carte gare = new Gare();
        Carte omni = new TrainOmnibus();
        Carte expr = new TrainExpress();
        Carte imm = new Immeuble();

        addAll(main, c);
        addAll(pioche, fondPioche);
        addAll(defausse, gare, omni, expr, imm);

        jouerTourPartiel("Remorquage", "Train express");

        assertTrue(containsReferences(main, expr));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, gare, omni, imm));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_salle_de_controle() {
        setupJeu("Salle de contrôle");
        initialisation();

        Carte c = new SalleDeControle();
        Carte fondPioche = new Ferraille();
        Carte gare1 = new Gare();
        Carte gare2 = new Gare();
        Carte gare3 = new Gare();

        addAll(main, c);
        addAll(pioche, gare1, gare2, gare3, fondPioche);

        jouerTourPartiel("Salle de contrôle");

        assertTrue(containsReferences(main, gare1, gare2, gare3));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_tgv_sans_omnibus() {
        setupJeu("TGV");
        initialisation();

        Carte c = new TGV();
        Carte fondPioche = new Ferraille();
        Carte omni = new TrainOmnibus();

        addAll(main, omni, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("TGV", "Train omnibus");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, omni));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_tgv_avec_omnibus() {
        setupJeu("TGV");
        initialisation();

        Carte c = new TGV();
        Carte fondPioche = new Ferraille();
        Carte omni = new TrainOmnibus();

        addAll(main, omni, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train omnibus", "TGV");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, omni));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(3, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_tgv_avec_2_omnibus() {
        setupJeu("TGV");
        initialisation();

        Carte c = new TGV();
        Carte fondPioche = new Ferraille();
        Carte omni1 = new TrainOmnibus();
        Carte omni2 = new TrainOmnibus();

        addAll(main, omni1, omni2, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train omnibus", "Train omnibus", "TGV");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, omni1, omni2));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(4, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_train_de_marchandises() {
        setupJeu("Train de marchandises");
        initialisation();

        Carte c = new TrainDeMarchandises();
        Carte fondPioche = new Ferraille();
        Carte f1 = new Ferraille();
        Carte f2 = new Ferraille();
        Carte f3 = new Ferraille();
        int nbFerraille = reserve.get("Ferraille").size();

        addAll(main, c, f1, f2, f3);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train de marchandises", "Ferraille", "Ferraille", "");

        assertTrue(containsSame(main, f3));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(3, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertEquals(reserve.get("Ferraille").size(), nbFerraille + 2);
    }

    @Test
    void test_train_de_tourisme() {
        setupJeu("Train de tourisme");
        initialisation();

        Carte c = new TrainDeTourisme();
        Carte fondPioche = new Ferraille();

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train de tourisme");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertEquals(joueur.getScoreTotal(), 1);
    }

    @Test
    void test_train_direct() {
        setupJeu("Train direct");
        initialisation();

        Carte c = new TrainDirect();
        Carte fondPioche = new Ferraille();

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train direct");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(3, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_train_express() {
        setupJeu("Train express");
        initialisation();

        Carte c = new TrainExpress();
        Carte fondPioche = new Ferraille();

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train express");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_train_matinal_simple() {
        setupJeu("Train matinal");
        initialisation();

        Carte c = new TrainMatinal();
        Carte fondPioche = new Ferraille();

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train matinal");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_train_matinal_oui() {
        setupJeu("Train matinal");
        initialisation();

        Carte c = new TrainMatinal();
        Carte fondPioche = new Ferraille();
        Carte gare = reserve.get("Gare").get(0);
        Carte expr = new TrainExpress();

        addAll(main, c, expr);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train matinal", "Train express", "Train express", "ACHAT:Gare", "oui");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, gare, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, expr));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertFalse(containsReference(reserve.get("Gare"), gare));
    }

    @Test
    void test_train_matinal_non() {
        setupJeu("Train matinal");
        initialisation();

        Carte c = new TrainMatinal();
        Carte fondPioche = new Ferraille();
        Carte gare = reserve.get("Gare").get(0);
        Carte expr = new TrainExpress();

        addAll(main, c, expr);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train matinal", "Train express", "Train express", "ACHAT:Gare", "non");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c, expr));
        assertTrue(containsReferences(cartesRecues, gare));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
        assertFalse(containsReference(reserve.get("Gare"), gare));
    }

    @Test
    void test_train_omnibus() {
        setupJeu("Train omnibus");
        initialisation();

        Carte c = new TrainOmnibus();
        Carte fondPioche = new Ferraille();

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train omnibus");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(1, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_train_postal() {
        setupJeu("Train postal");
        initialisation();

        Carte c = new TrainPostal();
        Carte fondPioche = new Ferraille();
        Carte gare = new Gare();
        Carte imm = new Immeuble();

        addAll(main, c, imm, gare);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Train postal", "Immeuble", "");

        assertTrue(containsReferences(main, gare));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse, imm));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(2, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));
    }

    @Test
    void test_tunnel_simple() {
        setupJeu("Tunnel");
        initialisation();

        Carte c = new Tunnel();
        Carte fondPioche = new Ferraille();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Tunnel");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues, f));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }

    @Test
    void test_usine_de_wagons() {
        setupJeu("Usine de wagons");
        initialisation();

        Carte c = new UsineDeWagons();
        Carte fondPioche = new Ferraille();
        Carte omni = new TrainOmnibus();
        Carte expr = new TrainExpress();
        Carte direct = reserve.get("Train direct").get(0);

        addAll(main, c, omni, expr);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Usine de wagons", "Train express", "ACHAT:Train direct");

        assertTrue(containsReferences(main, omni, direct));
        assertFalse(reserve.get("Train direct").contains(direct));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues));
        assertEquals(0, getArgent(joueur));
        assertEquals(0, getPointsRails(joueur));

        assertTrue(containsReferences(cartesEcartees, expr));
    }

    @Test
    void test_viaduc_simple() {
        setupJeu("Viaduc");
        initialisation();

        Carte c = new Viaduc();
        Carte fondPioche = new Ferraille();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Viaduc");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues, f));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }

    @Test
    void test_voie_souterraine_simple() {
        setupJeu("Voie souterraine");
        initialisation();

        Carte c = new VoieSouterraine();
        Carte fondPioche = new Ferraille();
        Carte f = reserve.get("Ferraille").get(0);

        addAll(main, c);
        addAll(pioche, fondPioche);

        jouerTourPartiel("Voie souterraine");

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche, fondPioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, c));
        assertTrue(containsReferences(cartesRecues, f));
        assertFalse(containsReference(reserve.get("Ferraille"), f));
        assertEquals(0, getArgent(joueur));
        assertEquals(1, getPointsRails(joueur));
    }
}
