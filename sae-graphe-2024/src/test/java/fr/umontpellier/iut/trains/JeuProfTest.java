package fr.umontpellier.iut.trains;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JeuProfTest extends BaseTestClass {
    @Test
    void test_debut_jeu() {
        setupJeu();

        // Le joueur 1 choisit de placer son premier rail sur la tuile 14 puis le joueur
        // 2 choisit de placer son premier rail sur la tuile 62
        jeu.setInput("TUILE:14", "TUILE:62");
        
        try {
            jeu.run();
        } catch (Exception e) {
            assertEquals("fr.umontpellier.iut.trains.Joueur.jouerTour", getMethodeQuiAttendInput(e));
        }
        checkPlateau(List.of(14), List.of(62), null);
    }
}
