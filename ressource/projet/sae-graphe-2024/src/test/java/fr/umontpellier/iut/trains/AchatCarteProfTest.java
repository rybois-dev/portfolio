package fr.umontpellier.iut.trains;


import fr.umontpellier.iut.trains.cartes.Carte;
import fr.umontpellier.iut.trains.cartes.TrainOmnibus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AchatCarteProfTest extends BaseTestClass {
    private void testAcheterCarte(String nomCarte, int cout) {
        setupJeu(nomCarte);
        initialisation();

        Carte c = reserve.get(nomCarte).get(0);

        List<Carte> omnibus = new ArrayList<>();
        for (int i = 0; i < cout + 1; i++) {
            omnibus.add(new TrainOmnibus());
        }
        main.addAll(omnibus);

        List<String> instructions = new ArrayList<>();
        for (int i = 0; i < cout + 1; i++) {
            instructions.add("Train omnibus");
        }
        instructions.add("ACHAT:" + nomCarte);

        jouerTourPartiel(instructions);

        assertTrue(containsReferences(main));
        assertTrue(containsReferencesInOrder(pioche));
        assertTrue(containsReferences(defausse));
        assertTrue(containsReferences(cartesEnJeu, omnibus));
        assertTrue(containsReferences(cartesRecues, c));
        assertEquals(getArgent(joueur), 1);
        assertEquals(c.getNom(), nomCarte);
    }

    @Test
    void test_acheter_carte() {
        testAcheterCarte("Aiguillage", 5);
        testAcheterCarte("Atelier de maintenance", 5);
        testAcheterCarte("Bureau du chef de gare", 4);
        testAcheterCarte("Cabine du conducteur", 2);
        testAcheterCarte("Centre de contrôle", 3);
        testAcheterCarte("Centre de renseignements", 4);
        testAcheterCarte("Coopération", 5);
        testAcheterCarte("Décharge", 2);
        testAcheterCarte("Dépôt", 3);
        testAcheterCarte("Dépotoir", 5);
        testAcheterCarte("Échangeur", 3);
        testAcheterCarte("Ferronnerie", 4);
        testAcheterCarte("Feu de signalisation", 2);
        testAcheterCarte("Gare", 3);
        testAcheterCarte("Horaires estivaux", 3);
        testAcheterCarte("Horaires temporaires", 5);
        testAcheterCarte("Parc d'attractions", 4);
        testAcheterCarte("Passage en gare", 3);
        testAcheterCarte("Personnel de gare", 2);
        testAcheterCarte("Pont en acier", 4);
        testAcheterCarte("Pose de rails", 3);
        testAcheterCarte("Remorquage", 3);
        testAcheterCarte("Salle de contrôle", 7);
        testAcheterCarte("TGV", 2);
        testAcheterCarte("Train de marchandises", 4);
        testAcheterCarte("Train de tourisme", 4);
        testAcheterCarte("Train direct", 6);
        testAcheterCarte("Train express", 3);
        testAcheterCarte("Train matinal", 5);
        testAcheterCarte("Train postal", 4);
        testAcheterCarte("Tunnel", 5);
        testAcheterCarte("Usine de wagons", 5);
        testAcheterCarte("Viaduc", 5);
        testAcheterCarte("Voie souterraine", 7);
    }
}
