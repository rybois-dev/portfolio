package fr.umontpellier.iut.gui;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.websocket.DeploymentException;
import javax.websocket.Session;

import org.glassfish.tyrus.server.Server;

import com.sun.net.httpserver.HttpServer;

import fr.umontpellier.iut.trains.cartes.FabriqueListeDeCartes;
import fr.umontpellier.iut.trains.plateau.Plateau;

public class GameServer {
    /**
     * Liste des clients connectés au serveur
     */
    private static final ArrayList<Session> clients = new ArrayList<>();
    /**
     * Description de l'état du jeu, envoyé aux clients pour la mise à jour de
     * l'interface graphique
     */
    private static String etatJeu = "";
    /**
     * Instance de jeu exécutée par le serveur
     */
    private static JeuWebsocket jeu;

    public static void main(String[] args) throws IOException, DeploymentException {
        // Lancement de la partie
        String[] nomsJoueurs = { "Guybrush", "Largo" };

        // Tirer aléatoirement 8 cartes préparation
        List<String> cartesPreparation = new ArrayList<>(FabriqueListeDeCartes.getNomsCartesPreparation());
        Collections.shuffle(cartesPreparation);
        String[] nomsCartes = cartesPreparation.subList(0, 8).toArray(new String[0]);

        // DEBUG: toutes les cartes sont disponibles
        // String[] nomsCartes = cartesPreparation.subList(0, 30).toArray(new
        // String[0]);

        // On peut aussi donner explicitement les cartes à utiliser :
        // String[] nomsCartes = {"Aiguillage", "Passage en gare", "Salle de contrôle"};
        
        jeu = new JeuWebsocket(nomsJoueurs, nomsCartes, Plateau.OSAKA);

        // DEBUG: placement auto en début de partie
        // jeu.addInput("TUILE:3");
        // jeu.addInput("TUILE:72");

        // SERVEUR HTTP (pour l'interface graphique)
        // Crée un contexte pour les requêtes HTTP, attache le handler et démarre le
        // serveur HTTP
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(4242), 0);
        httpServer.createContext("/", new StaticFileHandler("/", "front/", "index.html"));
        httpServer.start();

        // SERVEUR WEBSOCKET (pour communiquer entre l'interface et le jeu)
        // Prépare le serveur websocket
        Server server = new Server("localhost", 3232, "/", WebSocketClient.class);
        try (Scanner scanner = new Scanner(System.in)) {
            server.start(); // lance le serveur
            new Thread(jeu).start(); // démarre le jeu (exécute la méthode Jeu.run() dans un nouveau thread)

            while (true) {
                // écoute les entrées au clavier
                jeu.addInput(scanner.nextLine());
            }
        } finally {
            server.stop();
        }
    }

    /**
     * Ajoute une nouvelle instruction à la file d'instructions
     * (cette méthode est appelée lorsqu'un message est reçue sur la websocket)
     * 
     * @param message l'instruction à ajouter
     */
    public static void addInput(String message) {
        jeu.addInput(message);
    }

    /**
     * Met à jour l'état de la partie, et envoie le nouvel état à tous les clients
     * connectés
     * 
     * @param etatJeu l'état de la partie
     */
    public static void setEtatJeu(String etatJeu) {
        GameServer.etatJeu = etatJeu;
        // Envoie l'état de la partie à tous les clients
        try {
            for (Session session : clients) {
                session.getBasicRemote().sendText(etatJeu);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajoute un nouveau client à la liste, et lui transmet l'état actuel de la
     * partie
     * (cette méthode est appelée lorsqu'une nouvelle connexion est établie)
     * 
     * @param session la session du nouveau client
     */
    public static void addClient(Session session) {
        GameServer.clients.add(session);
        try {
            session.getBasicRemote().sendText(etatJeu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retire un client de la liste
     * (cette méthode est appelée lorsqu'une connexion est fermée)
     * 
     * @param session la session du client à retirer
     */
    public static void removeClient(Session session) {
        GameServer.clients.remove(session);
    }
}
