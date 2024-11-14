package fr.umontpellier.iut.gui;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;

import fr.umontpellier.iut.trains.Bouton;
import fr.umontpellier.iut.trains.Jeu;
import fr.umontpellier.iut.trains.plateau.Plateau;

public class JeuWebsocket extends Jeu {
    /**
     * File d'attente des instructions recues par le serveur
     */
    private final BlockingQueue<String> inputQueue;

    public JeuWebsocket(String[] nomsJoueurs, String[] nomsCartes, Plateau plateau) {
        super(nomsJoueurs, nomsCartes, plateau);
        inputQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public String lireLigne() {
        try {
            return inputQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void prompt(String instruction, List<Bouton> boutons, boolean peutPasser) {
        super.prompt(instruction, boutons, peutPasser);
        GameServer.setEtatJeu(new Gson().toJson(dataMap()));
    }

    public void addInput(String input) {
        inputQueue.add(input);
    }
}
